package com.maydesk.base.model;

import javax.persistence.Entity;


@Entity
public class MDataLink extends MBase {

	private int sourceId;
	private String targetClass;
	private int targetId;
	
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
}
