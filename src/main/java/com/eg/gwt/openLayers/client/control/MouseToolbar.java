package com.eg.gwt.openLayers.client.control;

import com.eg.gwt.openLayers.client.JSObject;

/**
 * 
 * @author Erdem Gunay
 *
 */
public class MouseToolbar extends Control {

	protected MouseToolbar(JSObject element) {
		super(element);
	}

	public MouseToolbar() {
		this(MouseToolbarImpl.create());
	}
}
