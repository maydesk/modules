package com.maydesk.context.widget;

import java.util.EventListener;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;

public class MDCanvas extends Component implements IPlugTarget {

	public static final String INPUT_CLICK = "async_click";
	public static final String ACTION_LISTENERS_CHANGED_PROPERTY = "actionListeners";
	public static final String PROPERTY_CLICK_X = "clickX";
	public static final String PROPERTY_CLICK_Y = "clickY";
	public static final String PROPERTY_ZOOMABLE = "zoomable";

	/**
	 * Only to be used to obtain X coordinate of last click
	 * @return
	 */
	public double getClickX() {
		return (Double) get(PROPERTY_CLICK_X);
	}
	
	public void setClickX(double clickX) {
		set(PROPERTY_CLICK_X, clickX);
	}
	
	/**
	 * Only to be used to obtain Y coordinate of last click
	 * @return
	 */
	public double getClickY() {
		return (Double) get(PROPERTY_CLICK_Y);
	}
	
	public void setClickY(double clickY) {
		set(PROPERTY_CLICK_Y, clickY);
	}
	
	@Override
	public void initWire(XmlBaseEntry parentWire) {
	}

	public void addActionListener(ActionListener l) {
		getEventListenerList().addListener(ActionListener.class, l);
		firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, null, l);
	}

	public void removeActionListener(ActionListener l) {
		getEventListenerList().removeListener(ActionListener.class, l);
		firePropertyChange(ACTION_LISTENERS_CHANGED_PROPERTY, l, null);
	}

	public boolean hasActionListeners() {
		return hasEventListenerList() && getEventListenerList().getListenerCount(ActionListener.class) > 0;
	}

	private void fireAction() {
		EventListener[] eventListeners = getEventListenerList().getListeners(ActionListener.class);
		ActionEvent evt = new ActionEvent(this, "");
		for (EventListener eventListener : eventListeners) {
			((ActionListener) eventListener).actionPerformed(evt);
		}
	}

	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (INPUT_CLICK.equals(inputName)) {
			fireAction();
		} else if (PROPERTY_CLICK_X.equals(inputName)) {
			setClickX(((Number) inputValue).doubleValue());
		} else if (PROPERTY_CLICK_Y.equals(inputName)) {
			setClickY(((Number) inputValue).doubleValue());
		}
	}
	
	public void setZoomable(boolean zoomable) {
		set(PROPERTY_ZOOMABLE, zoomable);
	}
}