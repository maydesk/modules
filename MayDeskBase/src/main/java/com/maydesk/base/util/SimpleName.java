package com.maydesk.base.util;

import java.lang.annotation.Annotation;

import com.maydesk.base.aspects.Translatable;

import lombok.soplets.Beanable;




public class SimpleName implements Translatable, Beanable {

	private String name;
	
	public SimpleName(String name) {
		this.name = name;
	}
	
	//@Override
    public String name() {
	    return name;
    }

	@Override
    public Class<? extends Annotation> annotationType() {
	    return null;
    }

	@Override
    public String textDE() {
	    return name;
    }

	@Override
    public String textEN() {
	    return name;
    }

	@Override
    public String textFR() {
	    return name;
    }

	@Override
    public Class javaType() {
	    return String.class;
    }

	@Override
    public int length() {
	    return 0;
    }
}
