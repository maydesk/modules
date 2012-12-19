package com.maydesk.base.widgets;

import java.util.Date;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

import echopoint.jquery.DateField;

/**
 * @author Alejandro Salas
 */
public class PDDateField extends DateField implements IChangeSupportable<Date> {

	private PDBinding changeSupport;

	public PDDateField() {
		setDateFormat("dd.MM.yyyy");
	}

	public PDDateField(String renderId) {
		this();
		setRenderId(renderId);
	}

	@Override
	public void setDate(Date date) {
		if (date == null) {
			return; // XXX this is a bug!
		}
		super.setDate(date);
	}

	@Override
	public Date getValue() {
		return getDate();
	}

	@Override
	public void setValue(Date date) {
		setDate(date);
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public String getPropertyName() {
		return "date";
	}
}
