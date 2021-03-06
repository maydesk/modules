/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas
 */
public class PDIntegerSpinner extends Row implements IChangeSupportableWithAction<Integer> {

	private PDNumericField txtValue;
	private List<ActionListener> actionListeners = new Vector<ActionListener>();
	private PDBinding changeSupport;

	public PDIntegerSpinner() {
		initGUI();
	}

	private void initGUI() {
		setCellSpacing(new Extent(2));

		NumberFormat nf = PDFormat.getNumberFormat(0);
		nf.setGroupingUsed(false);
		txtValue = new PDNumericField(nf);
		txtValue.setNumber(0);
		txtValue.setWidth(new Extent(50));
		txtValue.setAlignment(Alignment.ALIGN_RIGHT);
		txtValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireActionPerformed();
			}
		});
		add(txtValue);

		Column col = new Column();
		add(col);

		Button btnUp = new Button(EImage16.spin_up.getImage());
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnUpClicked();
			}
		});
		btnUp.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnUp.setWidth(new Extent(10));
		btnUp.setHeight(new Extent(5));
		col.add(btnUp);

		Button btnDown = new Button(EImage16.spin_down.getImage());
		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnDownClicked();
			}
		});
		btnDown.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnDown.setWidth(new Extent(10));
		btnDown.setHeight(new Extent(5));
		col.add(btnDown);
	}

	protected void spnDownClicked() {
		int value = getValue() - 1;
		if (value < 0)
			value = 0;
		txtValue.setNumber(value);
		fireActionPerformed();
	}

	protected void spnUpClicked() {
		int value = getValue() + 1;
		txtValue.setNumber(value);
		fireActionPerformed();
	}

	@Override
	public Integer getValue() {
		return (int) txtValue.getNumber();
	}

	@Override
	public void setValue(Integer value) {
		if (value == null) {
			txtValue.setText("");
		} else {
			txtValue.setNumber(value);
		}
	}

	@Override
	public void addActionListener(ActionListener listener) {
		// txtValue.setActionCausedOnChange(true);
		actionListeners.add(listener);
	}

	private void fireActionPerformed() {
		ActionEvent e = new ActionEvent(this, "");
		for (ActionListener listener : actionListeners) {
			listener.actionPerformed(e);
		}
	}

	@Override
	public void setBorder(Border b) {
		txtValue.setBorder(b);
	}

	public PDNumericField getTextField() {
		return txtValue;
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public String getPropertyName() {
		return "value";
	}

	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
}
