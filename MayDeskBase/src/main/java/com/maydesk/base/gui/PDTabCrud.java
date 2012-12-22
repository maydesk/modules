/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Component;

import com.maydesk.base.model.MBase;
import com.maydesk.base.util.ICrud;

/**
 * @author chrismay
 */
public class PDTabCrud<T extends MBase> extends PDTab implements ICrud<T> {

	protected List<Component> tabs;

	public PDTabCrud() {
		tabs = new Vector<Component>();
	}

	@Override
	public void readFromModel(MBase model) {
		for (Component tab : tabs) {
			if (tab instanceof ICrud) {
				((ICrud) tab).readFromModel(model);
			}
		}
	}

	public void addTab2(String caption, Component tab) {
		tabs.add(tab);
		addTab(caption, tab);
	}

	public void removeTab(int index) {
		getTabPane().remove(index);
		tabs.remove(index);
	}

	@Override
	public Component getFocusComponent() {
		if (tabs.size() > 0) {
			return ((ICrud) tabs.get(0)).getFocusComponent();
		}
		return null;
	}

	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return null;
	}
}