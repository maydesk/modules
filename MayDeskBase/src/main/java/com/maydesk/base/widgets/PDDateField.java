package com.maydesk.base.widgets;

import java.util.Date;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

import echopoint.jquery.DateField;

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
			return; //XXX this is a bug!
		}
		super.setDate(date);
	}

	public Date getValue() {
	    return getDate();
    }

	public void setValue(Date date) {
		setDate(date);
    }

	public PDBinding getChangeSupport() {
    	return changeSupport;
    }

	public void setChangeSupport(PDBinding changeSupport) {
    	this.changeSupport = changeSupport;
    }

	public String getPropertyName() {
	    return "date";
    }
}
