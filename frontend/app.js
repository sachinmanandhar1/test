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

// Administrative Boundaries Layer
const sahelAdmLayerInfo = {
  id: 'sahel_adm',
  title: 'Sahel Administrative Boundaries',
  typeName: 'sahel:sahel_adm',
  color: 'rgba(255, 0, 0, 0.5)'
};

const sahelAdmLayer = new ol.layer.Vector({
  title: sahelAdmLayerInfo.title,
  source: new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    url: function(extent) {
      return `http://localhost:8080/geoserver/sahel/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=${sahelAdmLayerInfo.typeName}&outputFormat=application%2Fjson&srsName=EPSG:3857&bbox=${extent.join(',')},EPSG:3857`;
    },
    strategy: ol.loadingstrategy.bbox
  }),
  style: new ol.style.Style({
    fill: new ol.style.Fill({ color: sahelAdmLayerInfo.color }),
    stroke: new ol.style.Stroke({ color: '#000', width: 1 })
  })
});
map.addLayer(sahelAdmLayer);


// Water Points Layer
const waterPointTypes = {
  'PUITS': { title: 'Puits', color: 'blue' },
  'FORAGE': { title: 'Forage', color: 'green' },
  'MARE': { title: 'Mare', color: 'purple' }
};

const waterPointsTypeName = 'sahel:waterpoints';

// Style function for water points
const waterPointStyle = function(feature) {
  const type = feature.get('type');
  const style = waterPointTypes[type];
  if (style) {
    return new ol.style.Style({
      image: new ol.style.Circle({
        radius: 5,
        fill: new ol.style.Fill({ color: style.color })
      })
    });
  }
  return null;
};

const waterPointsSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: function(extent) {
    const visibleTypes = Object.keys(waterPointTypes).filter(type => {
        const checkbox = document.getElementById(`filter-${type}`);
        return checkbox && checkbox.checked;
    });

    if (visibleTypes.length === 0) {
        // If no types are selected, don't request any features.
        // We can't return an empty URL, so we create a filter that returns nothing.
        return `http://localhost:8080/geoserver/sahel/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=${waterPointsTypeName}&outputFormat=application%2Fjson&srsName=EPSG:3857&bbox=${extent.join(',')},EPSG:3857&cql_filter=type%20IS%20NULL`;
    }

    const cql_filter = `type IN ('${visibleTypes.join("','")}')`;
    return `http://localhost:8080/geoserver/sahel/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=${waterPointsTypeName}&outputFormat=application%2Fjson&srsName=EPSG:3857&bbox=${extent.join(',')},EPSG:3857&cql_filter=${encodeURIComponent(cql_filter)}`;
  },
  strategy: ol.loadingstrategy.bbox
});

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
    waterPointsSource.refresh();
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