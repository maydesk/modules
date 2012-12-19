package com.maydesk.base.model;

import javax.persistence.Entity;

import com.maydesk.base.sop.plug.Plugable;
import com.maydesk.base.sop.plug.SopSocketType;

/**
 * @author chrismay
 */
@Entity
public class MPlug extends MBase {

	private String caption;
	private String configEditorClassName;
	private String editorClassName;
	private String icon;
	private String modelClassName;
	private String name;
	private SopSocketType plugType;

	public String getCaption() {
		return caption;
	}

	public String getConfigEditorClassName() {
		return configEditorClassName;
	}

	public String getEditorClassName() {
		return editorClassName;
	}

	public String getIcon() {
		return icon;
	}

	public String getModelClassName() {
		return modelClassName;
	}

	public String getName() {
		return name;
	}

	public SopSocketType getPlugType() {
		return plugType;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setConfigEditorClassName(String configEditorClassName) {
		this.configEditorClassName = configEditorClassName;
	}

	public void setEditorClassName(String editorClassName) {
		this.editorClassName = editorClassName;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlugType(SopSocketType plugType) {
		this.plugType = plugType;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setClassNames(Plugable p) {
		modelClassName = p.modelClass() == null ? null : p.modelClass().getCanonicalName();
		editorClassName = p.editorClass() == null ? null : p.editorClass().getCanonicalName();
		configEditorClassName = p.configEditorClass() == null ? null : p.configEditorClass().getCanonicalName();
	}
}
