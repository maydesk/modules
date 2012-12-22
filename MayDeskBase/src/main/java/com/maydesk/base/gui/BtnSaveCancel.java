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

import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.util.PDBinding;

import echopoint.PushButton;

/**
 * @author chrismay
 */
public class BtnSaveCancel extends Row {

	protected PushButton btnSave;
	protected PushButton btnCancel;
	protected List<PDBinding> bindings = new Vector<PDBinding>();

	public BtnSaveCancel() {
		btnCancel = new PushButton("Cancel");
		btnCancel.setHeight(new Extent(20));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (PDBinding binding : bindings) {
					binding.resetChanges();
					if (binding.getTarget() != null) {
						binding.read(binding.getTarget().reload());
					}
				}
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		btnCancel.setVisible(false);
		add(btnCancel);

		btnSave = new PushButton("Save");
		btnSave.setHeight(new Extent(20));
		btnSave.setWidth(new Extent(60));
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (PDBinding binding : bindings) {
					if (binding.applyChanges()) {
						binding.resetChanges();
					} else {
						return;
					}
				}
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		btnSave.setVisible(false);
		add(btnSave);

	}

	public void addBinding(PDBinding binding) {
		bindings.add(binding);
		binding.setSomethingChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
	}

	public void reset() {
		for (PDBinding binding : bindings) {
			binding.setSomethingChangedListener(null);
		}
		bindings.clear();
	}
}
