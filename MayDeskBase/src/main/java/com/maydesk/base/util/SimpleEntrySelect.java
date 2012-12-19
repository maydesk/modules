package com.maydesk.base.util;

import echopoint.model.AutoLookupSelectModel.EntrySelect;

/**
 * @author chrismay
 */
public class SimpleEntrySelect implements EntrySelect {

	String idAsString;
	String value;
	String searchVal;

	public SimpleEntrySelect(String idAsString, String value, String searchVal) {
		this.idAsString = idAsString;
		this.value = value;
		this.searchVal = searchVal;
	}

	@Override
	public String getKey() {
		return idAsString;
	}

	@Override
	public String getSearchVal() {
		return searchVal;
	}

	@Override
	public String getValue() {
		return value;
	}

}