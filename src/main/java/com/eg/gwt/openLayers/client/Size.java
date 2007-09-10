package com.eg.gwt.openLayers.client;

/**
 * 
 * @author Erdem Gunay
 *
 */
public class Size extends OpenLayersWidget {

	protected Size(JSObject element) {
		super(element);
	}
	
	public Size(int w, int h) {
		this(SizeImpl.create(w, h));
	}

}
