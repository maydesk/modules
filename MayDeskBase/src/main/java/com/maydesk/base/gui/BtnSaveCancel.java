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
