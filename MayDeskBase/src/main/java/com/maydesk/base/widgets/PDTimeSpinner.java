/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas
 */
public class PDTimeSpinner extends Row {

	private static final DateFormat DATE_FORMAT = PDFormat.getDefaultTimeOnlyFormat();
	private boolean editable;
	private Label lblView;
	private PDTextField txtValue;
	private Calendar model;
	private EventListenerList eventListenerList = new EventListenerList();

	public PDTimeSpinner(boolean editable) {
		this.editable = editable;
		initGUI();
	}

	public void addActionListener(ActionListener listener) {
		eventListenerList.addListener(ActionListener.class, listener);
	}

	public void delActionListener(ActionListener listener) {
		eventListenerList.removeListener(ActionListener.class, listener);
	}

	protected void displayHour() {
		String s = model == null ? "" : DATE_FORMAT.format(model.getTime());
		if (!editable) {
			lblView.setText(s);
		} else {
			txtValue.setText(s);
		}
	}

	public void fireActionEvent(ActionEvent evt) {
		EventListener[] eventListeners = getActionListener();

		for (int i = 0; i < eventListeners.length; i++) {
			ActionListener listener = (ActionListener) eventListeners[i];
			listener.actionPerformed(evt);
		}
	}

	public EventListener[] getActionListener() {
		return eventListenerList.getListeners(ActionListener.class);
	}

	public Calendar getModel() {
		return model;
	}

	private void initGUI() {
		if (!editable) {
			lblView = new Label("");
			add(lblView);

			return;
		}

		setCellSpacing(new Extent(2));

		Button btnUp;
		Button btnDown;
		Column col;

		col = new Column();
		add(col);

		btnUp = new Button(EImage16.spin_up.getImage());
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnHourUpClicked();
			}
		});
		btnUp.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnUp.setWidth(new Extent(8));
		btnUp.setHeight(new Extent(6));
		col.add(btnUp);

		btnDown = new Button(EImage16.spin_down.getImage());
		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnHourDownClicked();
			}
		});
		btnDown.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnDown.setWidth(new Extent(8));
		btnDown.setHeight(new Extent(6));
		col.add(btnDown);

		txtValue = new PDTextField();
		txtValue.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		txtValue.setWidth(new Extent(40));
		txtValue.setKeyAction(true);
		txtValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				txtValueChanged();
			}
		});
		add(txtValue);

		col = new Column();
		add(col);

		btnUp = new Button(EImage16.spin_up.getImage());
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnMinsUpClicked();
			}
		});
		btnUp.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnUp.setWidth(new Extent(8));
		btnUp.setHeight(new Extent(6));
		col.add(btnUp);

		btnDown = new Button(EImage16.spin_down.getImage());
		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spnMinsDownClicked();
			}
		});
		btnDown.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnDown.setWidth(new Extent(8));
		btnDown.setHeight(new Extent(6));
		col.add(btnDown);
	}

	public void setModel(Calendar model) {
		this.model = model;
		displayHour();
	}

	protected void spnHourDownClicked() {
		if (model == null)
			model = Calendar.getInstance();

		if (model.get(Calendar.HOUR_OF_DAY) == 0) {
			model.set(Calendar.HOUR_OF_DAY, 23);
		} else {
			model.add(Calendar.HOUR_OF_DAY, -1);
		}
		displayHour();
		fireActionEvent(new ActionEvent(this, null));
	}

	protected void spnHourUpClicked() {
		if (model == null)
			model = Calendar.getInstance();

		if (model.get(Calendar.HOUR_OF_DAY) == 23) {
			model.set(Calendar.HOUR_OF_DAY, 0);
		} else {
			model.add(Calendar.HOUR_OF_DAY, 1);
		}
		displayHour();
		fireActionEvent(new ActionEvent(this, null));
	}

	protected void spnMinsDownClicked() {
		if (model == null)
			model = Calendar.getInstance();

		if (model.get(Calendar.MINUTE) == 0) {
			model.set(Calendar.MINUTE, 59);
		} else {
			model.add(Calendar.MINUTE, -1);
		}
		displayHour();
		fireActionEvent(new ActionEvent(this, null));
	}

	protected void spnMinsUpClicked() {
		if (model == null)
			model = Calendar.getInstance();

		if (model.get(Calendar.MINUTE) == 59) {
			model.set(Calendar.MINUTE, 0);
		} else {
			model.add(Calendar.MINUTE, 1);
		}
		displayHour();
		fireActionEvent(new ActionEvent(this, null));
	}

	protected void txtValueChanged() {
		try {
			if (txtValue.getText().length() == 0) {
				// user emptied the field, set value to null
				model = null;
			} else {
				if (model == null)
					model = Calendar.getInstance();
				Date date = DATE_FORMAT.parse(txtValue.getText());
				model.setTime(date);
			}
			fireActionEvent(new ActionEvent(this, null));
		} catch (Exception e) {
			// do nothing, the model stays at the previous value
			// just display what was before displayHour();
			model = null;
		}
	}
}
