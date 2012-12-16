/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import java.text.NumberFormat;

import com.maydesk.base.util.PDFormat;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Extent;

public class PDNumericField extends PDTextField {


	protected NumberFormat nf;

	public PDNumericField() {
		this(2);
		setWidth(new Extent(60));
		setAlignment(Alignment.ALIGN_RIGHT);
	}

	public PDNumericField(int digits) {
		nf = PDFormat.getNumberFormat(digits);
	}

	public PDNumericField(NumberFormat nf) {
		this.nf = nf;
	}

	public double getNumber() {
	    try {
	        return nf.parse(getText()).doubleValue();
        } catch (Exception e) {
        	return 0.0;
        }
    }

	public void setNumber(double value) {
		setText(nf.format(value));
    }
	
	public Object getValue() {
		return getNumber();
    }
	
	public void setValue(Object value) {
		setNumber((Double)value);
    }
}