package com.maydesk.context.widget;

import nextapp.echo.app.Component;


public class MDCanvasToolbar extends Component {

	private Class<? extends MDAbstractFigure> selectedToolClass;

	public Class<? extends MDAbstractFigure> getSelectedToolClass() {
		return selectedToolClass;
	}
	
	public void setSelectedToolClass(Class<? extends MDAbstractFigure> selectedToolClass) {
		this.selectedToolClass = selectedToolClass;
	}
}