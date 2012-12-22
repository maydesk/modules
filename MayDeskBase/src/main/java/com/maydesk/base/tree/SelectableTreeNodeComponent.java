/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.tree;

import java.util.EventListener;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.button.ToggleButton;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

/**
 * @author Alejandro Salas
 */
public class SelectableTreeNodeComponent extends Row {

	private ToggleButton toggleBtn;
	private Label label;
	private Object node;
	private EventListenerList eventListenerList = new EventListenerList();

	public SelectableTreeNodeComponent(Object node, String text) {
		this.node = node;
		initGUI(text);
	}

	private void initGUI(String text) {
		label = new Label();
		add(label);

		toggleBtn = new CheckBox(text);
		toggleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				toggleStateChanged();
			}
		});
		add(toggleBtn);
	}

	private void toggleStateChanged() {
		fireActionEvent(new ActionEvent(node, null));
	}

	public boolean isSelected() {
		return toggleBtn.isSelected();
	}

	public void setSelected(boolean selected) {
		toggleBtn.setSelected(selected);
	}

	public void setText(String text) {
		toggleBtn.setText(text);
	}

	@Override
	public void setEnabled(boolean enabled) {
		toggleBtn.setEnabled(enabled);
	}

	public String getText() {
		return toggleBtn.getText();
	}

	public void setIcon(ImageReference icon) {
		label.setIcon(icon);
	}

	public void addActionListener(ActionListener listener) {
		eventListenerList.addListener(ActionListener.class, listener);
	}

	public void removeActionListener(ActionListener listener) {
		eventListenerList.removeListener(ActionListener.class, listener);
	}

	public EventListener[] getActionListener() {
		return eventListenerList.getListeners(ActionListener.class);
	}

	public void fireActionEvent(ActionEvent evt) {
		EventListener[] eventListeners = getActionListener();

		for (int i = 0; i < eventListeners.length; i++) {
			ActionListener listener = (ActionListener) eventListeners[i];
			listener.actionPerformed(evt);
		}
	}
}