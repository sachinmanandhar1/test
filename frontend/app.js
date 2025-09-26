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

// Layer definitions
const layers = [
  {
    id: 'sahel_adm',
    title: 'Sahel Administrative Boundaries',
    typeName: 'sahel:sahel_adm',
    color: 'rgba(255, 0, 0, 0.5)'
  },
  {
    id: 'wells',
    title: 'Wells',
    typeName: 'sahel:wells',
    color: 'blue'
  },
  {
    id: 'boreholes',
    title: 'Boreholes',
    typeName: 'sahel:boreholes',
    color: 'green'
  },
  {
    id: 'ponds',
    title: 'Ponds',
    typeName: 'sahel:ponds',
    color: 'purple'
  }
];

// Create and add layers to the map
layers.forEach(layerInfo => {
  const wfsUrl = `http://localhost:8080/geoserver/sahel/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=${layerInfo.typeName}&outputFormat=application%2Fjson&srsName=EPSG:3857`;

  const layer = new ol.layer.Vector({
    title: layerInfo.title,
    source: new ol.source.Vector({
      format: new ol.format.GeoJSON(),
      url: function(extent) {
        return wfsUrl + '&bbox=' + extent.join(',') + ',EPSG:3857';
      },
      strategy: ol.loadingstrategy.bbox
    }),
    style: new ol.style.Style({
      fill: new ol.style.Fill({
        color: layerInfo.color
      }),
      stroke: new ol.style.Stroke({
        color: '#000',
        width: 1
      }),
      image: new ol.style.Circle({
        radius: 5,
        fill: new ol.style.Fill({
          color: layerInfo.color
        })
      })
    })
  });
  map.addLayer(layer);
});

// Custom Layer Switcher
const layerSwitcherContainer = document.getElementById('layer-switcher');
map.getLayers().forEach(layer => {
  if (layer.get('title') !== 'OpenStreetMap') {
    const layerId = layer.get('title').replace(/\s/g, '-').toLowerCase();
    const label = document.createElement('label');
    label.innerHTML = `
      <input type="checkbox" checked id="${layerId}">
      ${layer.get('title')}
    `;
    layerSwitcherContainer.appendChild(label);

    document.getElementById(layerId).addEventListener('change', (event) => {
      layer.setVisible(event.target.checked);
    });
  }
});

// Custom Legend
const legendContainer = document.getElementById('legend');
layers.forEach(layerInfo => {
  const legendItem = document.createElement('div');
  legendItem.className = 'legend-item';
  legendItem.innerHTML = `
    <div class="legend-color" style="background-color: ${layerInfo.color};"></div>
    <span>${layerInfo.title}</span>
  `;
  legendContainer.appendChild(legendItem);
});