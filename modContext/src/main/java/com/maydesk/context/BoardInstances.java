package com.maydesk.context;

import java.util.HashMap;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDCanvas;

public class BoardInstances {

	private String id;
	private MDCanvas original;
	private HashMap<PDApplicationInstance, MDCanvas> clones = new HashMap<PDApplicationInstance, MDCanvas>();
	
	public BoardInstances(String id, MDCanvas original) {
		this.id = id;
		this.original = original;		
	}
	
	public MDCanvas getOriginal() {
		return original;
	}

	public void addClone(PDApplicationInstance appInst, MDCanvas cloneBoard) {
		clones.put(appInst, cloneBoard);		
	}

	/**
	 * Update the properties on all clone boards
	 */
	public void updateCloneProps(final MDAbstractFigure fig) {
		for (PDApplicationInstance appInst : clones.keySet()) {
			final MDCanvas canvas = clones.get(appInst);
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					MDAbstractFigure figClone = (MDAbstractFigure)canvas.getComponent(fig.getId());
					if (figClone == null) return;
					fig.syncClone(figClone);
				}
			});
		}		
	}

	/**
	 * Add a figure to all clone boards
	 */
	public void addCloneFigure(final MDAbstractFigure fig) {
		for (PDApplicationInstance appInst : clones.keySet()) {
			final MDCanvas canvas = clones.get(appInst);
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					MDAbstractFigure figClone = fig.clone();
					figClone.setId(fig.getId());
					canvas.add(figClone);
				}
			});
		}
	}	
}
