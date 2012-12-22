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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import nextapp.echo.app.ImageReference;

import org.hibernate.annotations.IndexColumn;

import com.maydesk.base.util.ShortcutType;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_shortcut")
public class MShortcut extends MBase {

	private int modelId;
	private MUser owner;
	private int positionX;
	private int positionY;
	private String shortcutTypeClass;
	private String shortcutTypeName;

	public void executeTask() {
		executeTaskInternal();
	}

	protected void executeTaskInternal() {
		getShortcutType().openShortcut(this);
	}

	@Transient
	public ImageReference getIcon() {
		return getShortcutType().getIcon(this);
	}

	public int getModelId() {
		return modelId;
	}

	@ManyToOne(targetEntity = MUser.class, optional = false)
	@IndexColumn(name = "idxShortcutOwner")
	public MUser getOwner() {
		return owner;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	@Transient
	private ShortcutType getShortcutType() {
		try {
			Class clazz = Class.forName(shortcutTypeClass);
			return (ShortcutType) Enum.valueOf(clazz, shortcutTypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getShortcutTypeClass() {
		return shortcutTypeClass;
	}

	public String getShortcutTypeName() {
		return shortcutTypeName;
	}

	@Transient
	public String getTitle() {
		return getShortcutType().getTitle(this);
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public void setOwner(MUser owner) {
		this.owner = owner;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setShortcutType(Enum<?> shortcutType) {
		shortcutTypeClass = shortcutType.getClass().getSuperclass().getName();
		shortcutTypeName = shortcutType.name();
	}

	public void setShortcutTypeClass(String shortcutTypeClass) {
		this.shortcutTypeClass = shortcutTypeClass;
	}

	public void setShortcutTypeName(String shortcutTypeName) {
		this.shortcutTypeName = shortcutTypeName;
	}

	@Override
	public String toString() {
		return getTitle();
	}
}