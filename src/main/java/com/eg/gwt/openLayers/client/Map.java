package com.eg.gwt.openLayers.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.eg.gwt.openLayers.client.control.Control;
import com.eg.gwt.openLayers.client.event.EventHandler;
import com.eg.gwt.openLayers.client.event.EventListener;
import com.eg.gwt.openLayers.client.event.EventListenerCollection;
import com.eg.gwt.openLayers.client.event.EventType;
import com.eg.gwt.openLayers.client.event.MapBaseLayerChangedListener;
import com.eg.gwt.openLayers.client.event.MapClickListener;
import com.eg.gwt.openLayers.client.event.MapLayerAddedListener;
import com.eg.gwt.openLayers.client.event.MapLayerChangedListener;
import com.eg.gwt.openLayers.client.event.MapLayerRemovedListener;
import com.eg.gwt.openLayers.client.event.MapMarkerAddedListener;
import com.eg.gwt.openLayers.client.event.MapMarkerRemovedListener;
import com.eg.gwt.openLayers.client.event.MapMoveListener;
import com.eg.gwt.openLayers.client.event.MapPopupClosedListener;
import com.eg.gwt.openLayers.client.event.MapPopupOpenedListener;
import com.eg.gwt.openLayers.client.event.MapZoomListener;
import com.eg.gwt.openLayers.client.event.MapBaseLayerChangedListener.MapBaseLayerChangedEvent;
import com.eg.gwt.openLayers.client.event.MapClickListener.MapClickEvent;
import com.eg.gwt.openLayers.client.event.MapLayerAddedListener.MapLayerAddedEvent;
import com.eg.gwt.openLayers.client.event.MapLayerChangedListener.MapLayerChangedEvent;
import com.eg.gwt.openLayers.client.event.MapLayerRemovedListener.MapLayerRemovedEvent;
import com.eg.gwt.openLayers.client.event.MapMarkerAddedListener.MapMarkerAddedEvent;
import com.eg.gwt.openLayers.client.event.MapMarkerRemovedListener.MapMarkerRemovedEvent;
import com.eg.gwt.openLayers.client.event.MapMoveListener.MapMoveEvent;
import com.eg.gwt.openLayers.client.event.MapPopupClosedListener.MapPopupClosedEvent;
import com.eg.gwt.openLayers.client.event.MapPopupOpenedListener.MapPopupOpenedEvent;
import com.eg.gwt.openLayers.client.event.MapZoomListener.MapZoomEvent;
import com.eg.gwt.openLayers.client.layer.Layer;
import com.eg.gwt.openLayers.client.popup.Popup;
import com.eg.gwt.openLayers.client.util.JObjectArray;
import com.eg.gwt.openLayers.client.util.JSObject;
import com.google.gwt.user.client.Element;

/**
 * <p>
 * The Map class is the hub of OpenLayers. Instances of the Map class
 * are "interactive maps embedded in a webpage". In GWT-OpenLayers the map is
 * wrapped in a MapWidget which is a GWT Widget. GWT Widgets can receive
 * events from the browser and can be added to GWT Panels.
 * </p>
 * <pre>
 *    MapWidget mapWidget = new MapWidget("100%", "100%");
 *    Map map = mapWidget.getMap(); //instantiates map
 *    map.setControls(new LayerSwitcher());
 * </pre>
 * <em>Or</em>
 * <pre>
 *    MapOptions options = new MapOptions();
 *    options.setControls(new LayerSwitcher());
 *    MapWidget mapWidget = new MapWidget("100%", "100%", options);
 *    Map map = mapWidget.getMap(); //instantiates map
 * </pre>
 * <p>
 * Adjusted from OL API docs:
 * On their own maps do not provide much functionality.  To extend a map
 * it�s necessary to add controls, see {@link com.eg.gwt.openLayers.client.control.Control}
 * and layers, see {@link com.eg.gwt.openLayers.client.layer.Layer}
 * to the map.
 *
 * </p>
 * @author Erdem Gunay
 * @author Amr Alam - Refractions Research
 * @author Aaron Novstrup - Stottler Henke Associates, Inc.
 * @author Edwin Commandeur - Atlis Information Systems
 * @author Curtis Jensen - Integrity Applications Inc.
 */
public class Map extends OpenLayersObjectWrapper {

	private EventListenerCollection eventListeners = new EventListenerCollection();

	Map(Element e)
	{
		super(MapImpl.create(e));
	}

	Map(Element e, MapOptions options)
	{
		super(MapImpl.create(e, options.getJSObject()));
	}

	protected Map(JSObject e)
	{
		super(e);
	}

	public static Map narrowToMap(JSObject element)
	{
		return (element == null)? null: new Map(element);
	}

	/**
	 * @return LonLat - the coordinates of the center
	 */
	public LonLat getCenter(){
		return new LonLat(MapImpl.getCenter(getJSObject()));
	}

	public void setCenter(LonLat lonlat) {
		MapImpl.setCenter(getJSObject(), lonlat.getJSObject());
	}

	public void setCenter(LonLat lonlat, int zoom) {
		MapImpl.setCenter(getJSObject(), lonlat.getJSObject(), zoom);
	}

	public void addControl(Control control) {
		MapImpl.addControl(getJSObject(), control.getJSObject());
	}

	public void addLayer(Layer layer) {
		MapImpl.addLayer(getJSObject(), layer.getJSObject());
	}

	public void addLayers(Layer[] layers) {
		JSObject[] jsObjects = new JSObject[layers.length];
		for (int i = 0; i < layers.length; i++)
		{
			jsObjects[i] = layers[i].getJSObject();
		}

		JObjectArray array = new JObjectArray(jsObjects);
		MapImpl.addLayers(getJSObject(), array.getJSObject());
	}

	public void addPopup(Popup popup) {
		MapImpl.addPopup(getJSObject(), popup.getJSObject());
	}

	public void removePopup(Popup popup) {
		MapImpl.removePopup(getJSObject(), popup.getJSObject());
	}

	public int getNumLayers() {
		return MapImpl.getNumLayers(getJSObject());
	}

	/**
	 * Get a layer based on the id assigned by OpenLayers.
	 * A {@link com.eg.gwt.openLayers.client.layer.Layer} can be asked for it's id. This id can be used
	 * to get at the layer within the application.
	 *
	 * @param id - String identifier assigned by OpenLayers
	 * @return Layer - the layer on the map or null if there is no layer with the given id
	 */
	public Layer getLayer(String id) {
		JSObject jsObject = MapImpl.getLayer(id, getJSObject());
		Layer layer = new Layer(jsObject);
		return layer;
	}

	public Layer[] getLayers() {
		JSObject jsObjects = MapImpl.getLayers(getJSObject());
		JObjectArray jObjectArray = JObjectArray.narrowToJObjectArray(jsObjects);
		Layer[] layers = new Layer[jObjectArray.length()];
		for(int i = 0; i < jObjectArray.length(); i++) {
			layers[i] = new Layer(jObjectArray.get(i));
		}
		return layers;
	}

	public void removeLayer(Layer layer) {
		MapImpl.removeLayer(getJSObject(), layer.getJSObject());
	}

	public void removeControl(Control control) {
		MapImpl.removeControl(getJSObject(), control.getJSObject());
	}

	public int getLayerIndex(Layer layer) {
		return MapImpl.getLayerIndex(getJSObject(), layer.getJSObject());
	}

	public void setLayerIndex(Layer layer, int index) {
		MapImpl.setLayerIndex(getJSObject(), layer.getJSObject(), index);
	}

	/**
	 *  This is not an OpenLayers native function. OpenLayers has getLayersByName
	 *  that returns all layers matching the name.
	 *
	 * @param name - the name of a layer or part of it
	 * @return Layer - the first layer that matches the name (so, if there are more layers
	 *    matching the name then only one is returned) or null if no layer matches
	 */
	public Layer getLayerByName(String name){
		//FIXME: this function goes wrong if getLayersByName returns null
		// fix - let getLayersByName return empty array + update javadoc
		Layer[] layers = this.getLayersByName(name);
		if(layers != null && layers.length > 0){return layers[0];}else{return null;}
	}

	/**
	 *
	 * @param name - the name of a layer or part of it
	 * @return Layer[] - all layers with a name that fully or partially
	 *    matches the input name or null if no layer matches
	 */
	public Layer[] getLayersByName(String name){
		String regex= ".*" + name + ".*";
		Layer[] layers = this.getLayers();
		List layersFound = new ArrayList();
		for(int i = 0, max = layers.length; i < max;i++){
			if(layers[i].getName().matches(regex)){
				layersFound.add(layers[i]);
			}
		}
		if(layersFound.size()>0){
			int max = layersFound.size();
			Layer[] found = new Layer[max];
			for(int j = 0; j < max;j++ ){
				found[j] = (Layer) layersFound.get(j);
			}
			return found;
		}else{
			return null;
		}
	}

	/**
	 * This is not an OpenLayers native function. Will remove all overlays
	 * from the this Map.
	 */
	public void removeOverlayLayers() {
		Layer[] layers = getLayers();
		for (int i = 0; i < layers.length; i++ ) {
			if(layers[i].isBaseLayer() == false ) {
				removeLayer(layers[i]);
			}
		}
	}

	public void zoomToExtent(Bounds bounds) {
		MapImpl.zoomToExtent(getJSObject(), bounds.getJSObject());
	}

	public int getZoomForExtent(Bounds bounds, boolean closest) {
		return MapImpl.getZoomForExtent(getJSObject(), bounds.getJSObject(), closest);
	}

	/**
	 * Returns current zoom level of map object.
	 * @return current zoom level
	 */
	public int getZoom(){
		return MapImpl.getZoom(getJSObject());
	}

	public LonLat getLonLatFromPixel(Pixel pixel) {
		return new LonLat( MapImpl.getLonLatFromPixel(getJSObject(), pixel.getJSObject()) );
	}

	public Pixel getPixelFromLonLat (LonLat lonlat) {
		return new Pixel(MapImpl.getPixelFromLonLat(getJSObject(), lonlat.getJSObject()));
	}

	public String getProjection() {
		return MapImpl.getProjection(getJSObject());
	}

	/**
	 * See also {@link MapUnits}.
	 *
	 * @return String - the units used by the projection
	 */
	public String getUnits(){
		return MapImpl.getUnits(getJSObject());
	}

	public Bounds getExtent(){
		return new Bounds(MapImpl.getExtent(getJSObject()));
	}

	public Bounds getMaxExtent(){
		return new Bounds(MapImpl.getMaxExtent(getJSObject()));
	}

	public double getResolution(){
		return MapImpl.getResolution(getJSObject());
	}

	public double getScale(){
		return MapImpl.getScale(getJSObject());
	}

	public void raiseLayer(Layer layer, int delta){
		MapImpl.raiseLayer(getJSObject(), layer.getJSObject(), delta);
	}

	public void zoomTo(int zoomLevel){
		MapImpl.zoomTo(getJSObject(), zoomLevel);
	}

	public void zoomToMaxExtent(){
		MapImpl.zoomToMaxExtent(getJSObject());
	}

	public void setOptions(MapOptions mapOptions){
		MapImpl.setOptions(getJSObject(), mapOptions.getJSObject());
	}

	public void panTo(LonLat lonlat){
		MapImpl.panTo(getJSObject(), lonlat.getJSObject());
	}

	public Size getTileSize(){
		return new Size(MapImpl.getTileSize(getJSObject()));
	}

	public Size getSize(){
		return new Size(MapImpl.getSize(getJSObject()));
	}

	public double getMaxResolution(){
		return MapImpl.getMaxResolution(getJSObject());
	}

	public double getResolutionForZoom(){
		return getJSObject().getPropertyAsDouble("fractionalZoom");
	}

	/**
	 * <p>
	 * Somehow this is not an API property in OL, but it seems it would
	 * make sense as an API property and it is well documented:
	 * </p>
	 * <p>
	 * For a base layer that supports it, allow the map resolution
	 * to be set to a value between one of the values in the resolutions
	 * array.  Default is false.
	 *
	 * When fractionalZoom is set to true, it is possible to zoom to
	 * an arbitrary extent.  This requires a base layer from a source
	 * that supports requests for arbitrary extents (i.e. not cached
	 * tiles on a regular lattice).  This means that fractionalZoom
	 * will not work with commercial layers (Google, Yahoo, VE), layers
	 * using TileCache, or any other pre-cached data sources.
	 *
	 * If you are using fractionalZoom, then you should also use
	 * getResolutionForZoom instead of layer.resolutions[zoom] as the
	 * former works for non-integer zoom levels.
	 * </p>
	 *
	 */
	public void setFractionalZoom(boolean fractionalZoom){
		getJSObject().setProperty("fractionalZoom", fractionalZoom);
	}

	public void setMinExtent(Bounds extent){
		getJSObject().setProperty("minExtent", extent.getJSObject());
	}

	public void setMaxExtent(Bounds extent){
		getJSObject().setProperty("maxExtent", extent.getJSObject());
	}

	public void setRestrictedExtent(Bounds extent){
		getJSObject().setProperty("restrictedExtent", extent.getJSObject());
	}


	public void destroy(){
		MapImpl.destroy(getJSObject());
	}

	public void addMapBaseLayerChangedListener(final MapBaseLayerChangedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_BASE_LAYER_CHANGED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject){
				Map map = Map.narrowToMap(source);
				MapBaseLayerChangedEvent e = new MapBaseLayerChangedEvent(eventObject);
				listener.onBaseLayerChanged(map, e);
			}
		});

	}

	public void addMapLayerAddedListener(final MapLayerAddedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_LAYER_ADDED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject){
				Map map = Map.narrowToMap(source);
				MapLayerAddedEvent e = new MapLayerAddedEvent(eventObject);
				listener.onLayerAdded(map, e);
			}
		});
	};

	public void addMapLayerChangedListener(final MapLayerChangedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_LAYER_CHANGED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject){
				Map map = Map.narrowToMap(source);
				MapLayerChangedEvent e = new MapLayerChangedEvent(eventObject);
				listener.onLayerChanged(map, e);
			}
		});

	}

	public void addMapLayerRemovedListener(final MapLayerRemovedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_LAYER_REMOVED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject){
				Map map = Map.narrowToMap(source);
				MapLayerRemovedEvent e = new MapLayerRemovedEvent(eventObject);
				listener.onLayerRemoved(map, e);
			}
		});

	}

	public void addMapMoveListener(final MapMoveListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_MOVE, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapMoveEvent e = new MapMoveEvent(eventObject);
				listener.onMapMove(map, e);
			}
		});
	};

	public void addMapZoomListener(final MapZoomListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_ZOOMEND, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapZoomEvent e = new MapZoomEvent(eventObject);
				listener.onMapZoom(map, e);
			}
		});
	};

	public void addMapMarkerAddedListener(final MapMarkerAddedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_MARKER_ADDED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapMarkerAddedEvent e = new MapMarkerAddedEvent(eventObject);
				listener.onMarkerAdded(map, e);
			}
		});
	};

	public void addMapMarkerRemovedListener(final MapMarkerRemovedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_MARKER_REMOVED, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapMarkerRemovedEvent e = new MapMarkerRemovedEvent(eventObject);
				listener.onMarkerRemoved(map, e);
			}
		});
	};

	public void addMapPopupOpenedListener(final MapPopupOpenedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_POPUP_OPEN, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapPopupOpenedEvent e = new MapPopupOpenedEvent(eventObject);
				listener.onPopupOpened(map, e);
			}
		});
	};

	public void addMapPopupClosedListener(final MapPopupClosedListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_POPUP_CLOSE, new EventHandler(){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapPopupClosedEvent e = new MapPopupClosedEvent(eventObject);
				listener.onPopupClosed(map, e);
			}
		});
	};

	public void addMapClickListener(final MapClickListener listener){
		eventListeners.addListener(this, listener, EventType.MAP_CLICK, new EventHandler (){
			public void onHandle(JSObject source, JSObject eventObject) {
				Map map = Map.narrowToMap(source);
				MapClickEvent e = new MapClickEvent(eventObject);
				listener.onClick(map, e);
			}
		});
	};

	public void removeListener(EventListener listener){
		eventListeners.removeListener(this, listener);
	};

	public Set getListeners(){
		return eventListeners.getListeners();
	}

}
