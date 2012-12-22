/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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
