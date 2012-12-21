/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 6, 2007
 */
public class SelectableObjectBean<T> {

	private boolean selected;
	private T object;
	private List<ActionListener> listeners = new Vector<ActionListener>();

	public SelectableObjectBean(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		ActionEvent e = new ActionEvent(this, null);
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}
}
