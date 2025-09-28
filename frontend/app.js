// Initialize the map
const map = new ol.Map({
  target: 'map',
  layers: [
    new ol.layer.Tile({
      title: 'OpenStreetMap',
      type: 'base',
      source: new ol.source.OSM()
    })
  ],
  view: new ol.View({
    center: ol.proj.fromLonLat([15, 15]),
    zoom: 5
  })
});


// --- Popup Overlay ---
const popupContainer = document.getElementById('popup');
const popupContent = document.getElementById('popup-content');
const popupCloser = document.getElementById('popup-closer');

const overlay = new ol.Overlay({
  element: popupContainer,
  autoPan: true,
  autoPanAnimation: {
    duration: 250,
  },
});
map.addOverlay(overlay);

popupCloser.onclick = function () {
  overlay.setPosition(undefined);
  popupCloser.blur();
  return false;
};


// --- Layer Definitions ---

// Administrative Boundaries Layer (from static GeoJSON)
const sahelAdmLayerInfo = {
  id: 'sahel_adm',
  title: 'Sahel Administrative Boundaries',
  url: '../data/sahel_adm.geojson',
  color: 'rgba(255, 0, 0, 0.5)'
};

const sahelAdmLayer = new ol.layer.Vector({
  title: sahelAdmLayerInfo.title,
  source: new ol.source.Vector({
    url: sahelAdmLayerInfo.url,
    format: new ol.format.GeoJSON()
  }),
  style: new ol.style.Style({
    fill: new ol.style.Fill({ color: sahelAdmLayerInfo.color }),
    stroke: new ol.style.Stroke({ color: '#000', width: 1 })
  })
});
map.addLayer(sahelAdmLayer);


// Water Points Layer (from REST API)
const waterPointTypes = {
  'WELL': { title: 'Well', color: 'blue' },
  'BOREHOLE': { title: 'Borehole', color: 'green' },
  'POND': { title: 'Pond', color: 'purple' }
};

const waterPointsSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: '/api/waterpoints'
});

const waterPointStyle = function(feature) {
  const type = feature.get('typeOpen'); // Use typeOpen from the new entity
  const styleInfo = Object.values(waterPointTypes).find(t => type && t.title.toUpperCase() === type.toUpperCase());

  if (styleInfo) {
    return new ol.style.Style({
      image: new ol.style.Circle({
        radius: 5,
        fill: new ol.style.Fill({ color: styleInfo.color })
      })
    });
  }
  // Default style for unknown types
  return new ol.style.Style({
    image: new ol.style.Circle({
      radius: 5,
      fill: new ol.style.Fill({ color: 'gray' })
    })
  });
};

const waterPointsLayer = new ol.layer.Vector({
  title: 'Water Points',
  source: waterPointsSource,
  style: waterPointStyle
});
map.addLayer(waterPointsLayer);


// --- UI Controls (Sidebar) ---
const layerSwitcherContainer = document.getElementById('layer-switcher');
const legendContainer = document.getElementById('legend');

// ... (Sidebar logic for toggling layers - This is simplified as filtering is now part of the popup logic)
// For this version, we will rely on the popup for details and adding.


// --- Map Interaction (Viewing and Adding Points) ---
const typeDialog = document.getElementById('type-dialog');
const detailsDialog = document.getElementById('details-dialog');
const detailsForm = document.getElementById('details-form');
const nameInput = document.getElementById('name-input');
const latitudeInput = document.getElementById('latitude-input');
const longitudeInput = document.getElementById('longitude-input');
const typeInput = document.getElementById('type-input');

map.on('singleclick', function(evt) {
  overlay.setPosition(undefined);
  const feature = map.forEachFeatureAtPixel(evt.pixel, f => f);

  if (feature && feature.get('gid')) { // Check if it's a waterpoint feature
    const properties = feature.getProperties();
    let content = '<h4>Waterpoint Details</h4><ul>';
    Object.keys(properties).forEach(key => {
        if (key !== 'geometry') {
            content += `<li><strong>${key}:</strong> ${properties[key]}</li>`;
        }
    });
    content += '</ul>';
    popupContent.innerHTML = content;
    overlay.setPosition(evt.coordinate);
  } else {
    const coords = ol.proj.toLonLat(evt.coordinate);
    typeDialog.style.left = `${evt.pixel[0]}px`;
    typeDialog.style.top = `${evt.pixel[1]}px`;
    typeDialog.style.display = 'block';
    latitudeInput.value = coords[1];
    longitudeInput.value = coords[0];
  }
});

typeDialog.addEventListener('click', function(evt) {
    if (evt.target.tagName === 'BUTTON' && evt.target.dataset.type) {
        typeInput.value = evt.target.dataset.type;
        typeDialog.style.display = 'none';
        detailsDialog.style.left = typeDialog.style.left;
        detailsDialog.style.top = typeDialog.style.top;
        detailsDialog.style.display = 'block';
        nameInput.focus();
    }
});

detailsForm.addEventListener('submit', function(evt) {
    evt.preventDefault();
    const newWaterpoint = {
        noBook: nameInput.value, // Map form 'name' to 'noBook'
        typeOpen: typeInput.value, // Map form 'type' to 'typeOpen'
        latitude: parseFloat(latitudeInput.value),
        longitude: parseFloat(longitudeInput.value)
    };

    fetch('/api/waterpoints', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newWaterpoint)
    }).then(response => {
        if (response.ok) {
            detailsDialog.style.display = 'none';
            detailsForm.reset();
            waterPointsSource.refresh();
        } else {
            alert('Error saving waterpoint!');
        }
    }).catch(error => {
        console.error('Submission failed:', error);
        alert('Submission failed due to a network error. Please try again.');
    });
});

// Cancellation logic
document.getElementById('cancel-type-dialog').addEventListener('click', () => {
    document.getElementById('type-dialog').style.display = 'none';
});
document.getElementById('cancel-details-dialog').addEventListener('click', () => {
    document.getElementById('details-dialog').style.display = 'none';
    document.getElementById('type-dialog').style.display = 'none';
});