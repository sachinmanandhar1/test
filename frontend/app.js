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
  'PUITS': { title: 'Puits', color: 'blue' },
  'FORAGE': { title: 'Forage', color: 'green' },
  'MARE': { title: 'Mare', color: 'purple' }
};

const waterPointsSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: '/api/waterpoints'
});

// Style function for water points with client-side filtering
const waterPointStyle = function(feature) {
  const type = feature.get('type');
  const checkbox = document.getElementById(`filter-${type}`);

  // If checkbox exists and is unchecked, don't display the feature
  if (checkbox && !checkbox.checked) {
    return null;
  }

  const styleInfo = waterPointTypes[type];
  if (styleInfo) {
    return new ol.style.Style({
      image: new ol.style.Circle({
        radius: 5,
        fill: new ol.style.Fill({ color: styleInfo.color })
      })
    });
  }
  return null;
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

// Add control for Administrative Boundaries
const admLabel = document.createElement('label');
admLabel.innerHTML = `<input type="checkbox" checked id="layer-sahel_adm"> ${sahelAdmLayerInfo.title}`;
layerSwitcherContainer.appendChild(admLabel);
document.getElementById('layer-sahel_adm').addEventListener('change', (event) => {
  sahelAdmLayer.setVisible(event.target.checked);
});

// Add controls for each water point type
Object.keys(waterPointTypes).forEach(type => {
  const typeInfo = waterPointTypes[type];
  const label = document.createElement('label');
  label.innerHTML = `<input type="checkbox" checked id="filter-${type}"> ${typeInfo.title}`;
  layerSwitcherContainer.appendChild(label);

  document.getElementById(`filter-${type}`).addEventListener('change', () => {
    // When a filter changes, we just need to tell the source to re-render
    waterPointsSource.changed();
  });
});


// --- Legend ---

// Add legend for Administrative Boundaries
const admLegendItem = document.createElement('div');
admLegendItem.className = 'legend-item';
admLegendItem.innerHTML = `<div class="legend-color" style="background-color: ${sahelAdmLayerInfo.color};"></div> <span>${sahelAdmLayerInfo.title}</span>`;
legendContainer.appendChild(admLegendItem);

// Add legend for each water point type
Object.keys(waterPointTypes).forEach(type => {
  const typeInfo = waterPointTypes[type];
  const legendItem = document.createElement('div');
  legendItem.className = 'legend-item';
  legendItem.innerHTML = `<div class="legend-color" style="background-color: ${typeInfo.color};"></div> <span>${typeInfo.title}</span>`;
  legendContainer.appendChild(legendItem);
});