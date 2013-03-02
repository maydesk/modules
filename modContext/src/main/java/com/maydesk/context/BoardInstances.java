package com.maydesk.context;

import java.util.WeakHashMap;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDCanvas;

public class BoardInstances {

	private String id;
	private WeakHashMap<PDApplicationInstance, MDCanvas> instances = new WeakHashMap<PDApplicationInstance, MDCanvas>();
	
	public BoardInstances(String id) {
		this.id = id;
	}
	
	public void addClone(PDApplicationInstance appInst, MDCanvas cloneBoard) {
		instances.put(appInst, cloneBoard);		
	}

	/**
	 * Update the properties on all clone boards
	 */
	public void updateOtherInstances(final MDAbstractFigure fig) {
		for (PDApplicationInstance appInst : instances.keySet()) {
			final MDCanvas otherCanvas = instances.get(appInst);
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					MDAbstractFigure figClone = (MDAbstractFigure)otherCanvas.getComponent(fig.getId());
					if (figClone == null) return;
					if (figClone == fig) return;
					fig.syncClone(figClone);
				}
			});
		}		
	}

	/**
	 * Add a figure to all clone boards
	 */
	public void addFigureToOtherInstances(MDCanvas canvas, final MDAbstractFigure fig) {
		for (PDApplicationInstance appInst : instances.keySet()) {
			final MDCanvas otherCanvas = instances.get(appInst);
			if (otherCanvas == canvas) continue;  //no need to update itself
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					MDAbstractFigure figClone = fig.clone();
					figClone.setId(fig.getId());
					otherCanvas.add(figClone);
				}
			});
		}
	}

	public MDCanvas getMaster() {
		if (instances.isEmpty()) return null;
		//take any other(?) as master, ion theory they should be all the same
		return instances.values().iterator().next();
	}	
}
