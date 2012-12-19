package com.maydesk.base.widgets;

import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;

import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.IPlugTarget;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDRecycleBin extends ContainerEx implements IPlugTarget {

	public PDRecycleBin() {
		setTop(new Extent(20));
		setLeft(new Extent(20));
		setWidth(new Extent(25));
		setHeight(new Extent(25));

		Label lblRecycleBin = new Label(EImage16.recycle_bin.getImage());
		add(lblRecycleBin);
	}

	@Override
	public void initWire(MWire parentWire) {
	}
}
