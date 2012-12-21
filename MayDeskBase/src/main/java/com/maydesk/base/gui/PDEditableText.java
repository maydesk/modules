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

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author chrismay
 */
public class PDEditableText extends Row {

	private Button btn;
	private PDTextField txt;
	private List<ActionListener> listeners = new Vector<ActionListener>();
	private Button btnOk;

	public PDEditableText(String renderId) {
		btn = new Button();
		btn.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(11)));
		btn.setForeground(Color.DARKGRAY);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txt.setVisible(true);
				btn.setVisible(false);
				btnOk.setVisible(true);
				ApplicationInstance.getActive().setFocusedComponent(txt);
			}
		});
		add(btn);

		txt = new PDTextField(renderId + "txt");
		txt.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(11)));
		txt.setForeground(Color.DARKGRAY);
		txt.setVisible(false);
		add(txt);

		btnOk = new Button(EImage16.confirm.getImage());
		btnOk.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(11)));
		btnOk.setForeground(Color.DARKGRAY);
		btnOk.setVisible(false);
		btnOk.setInsets(new Insets(0, 0, 6, 0));
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txt.setVisible(false);
				btn.setVisible(true);
				btnOk.setVisible(false);
				setText(txt.getText());
				ActionEvent ae = new ActionEvent(this, "");
				for (ActionListener l : listeners) {
					l.actionPerformed(ae);
				}
			}
		});
		add(btnOk);
	}

	public void setText(String text) {
		if (PDUtil.isEmpty(text)) {
			btn.setText("...");
		} else {
			btn.setText(text);
		}
		txt.setText(text);
	}

	public void setWidth(int width) {
		btn.setWidth(new Extent(width));
		txt.setWidth(new Extent(width - 31));
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	public String getText() {
		return txt.getText();
	}

	@Override
	public void setEnabled(boolean newValue) {
		btn.setEnabled(newValue);
	}
}
