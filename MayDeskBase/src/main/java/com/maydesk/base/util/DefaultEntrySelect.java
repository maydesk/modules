package com.maydesk.base.util;

import echopoint.model.AutoLookupSelectModel.EntrySelect;

/**
 * @author chrismay
 */
public class DefaultEntrySelect implements EntrySelect {

	private int id;
	private String text;

	public DefaultEntrySelect(int id, String text) {
		this.id = id;
		this.text = text;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getSearchVal() {
		return text;
	}

	@Override
	public String getValue() {
		return text;
	}

}
