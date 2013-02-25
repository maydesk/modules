package com.maydesk.context.widget;

import com.maydesk.context.MDServletExternalContext;



public class MDVideo extends MDAbstractFigure {

	public static final String ACTION_CONNECT = "connect";
	public static final String PROPERTY_URL = "url";

	public MDVideo() {
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_URL.equals(inputName)) {
			set(PROPERTY_URL, inputValue);
		}

		//EXPERIMENTAL!
		if (MDServletExternalContext.TEST_APP_INSTANCE != null) {
		}
	}

	public void setUrl(String url) {
		set(PROPERTY_URL, url);		
	}
}
