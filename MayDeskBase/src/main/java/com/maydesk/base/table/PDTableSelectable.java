package com.maydesk.base.table;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.gui.PDCheckBox;
import com.maydesk.base.table.renderer.PDCellRenderer;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.util.SelectableObjectBean;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author chrismay
 */
public class PDTableSelectable extends PDTable {

	private boolean masterSelected = false;
	private PDTableModel3<? extends SelectableObjectBean<?>> tableModel;
	private List<ActionListener> listeners = new Vector<ActionListener>();

	public PDTableSelectable(PDTableModel3<? extends SelectableObjectBean<?>> model) {
		setModel(model);
		this.tableModel = model;
		setDefaultRenderer(Object.class, new MyCellRenderer());
		setDefaultHeaderRenderer(new MyHeaderRenderer());
		setBorder(PDUtil.emptyBorder());
		setInsets(new Insets(0));
	}

	class MyCellRenderer extends PDCellRenderer {
		@Override
		public Component getTableCellRendererComponent(Table table, Object value, int col, int row) {
			if (col == 0 && value instanceof SelectableObjectBean<?>) {
				final SelectableObjectBean<?> bean = (SelectableObjectBean<?>) value;
				final CheckBox chk = new CheckBox();
				chk.setSelected(bean.isSelected());
				String text = bean.getObject() + "";
				chk.setToolTipText(text);
				text = PDUtil.getLimitedText(text, 20);
				chk.setText(text);
				chk.setLineWrap(true);
				chk.setWidth(new Extent(200));
				chk.setForeground(Color.DARKGRAY);
				chk.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						bean.setSelected(chk.isSelected());
						fireSelectionEvent();
					}
				});
				setBackground(chk, row);
				return chk;
			} else {
				PDLabel lbl = new PDLabel(PDLabel.STYLE.FIELD_LABEL);
				if (value instanceof ImageReference) {
					lbl.setIcon((ImageReference) value);
				} else {
					if (value == null)
						value = "";
					lbl.setText(value + "");
				}
				setBackground(lbl, row);
				return lbl;
			}
		}
	}

	class MyHeaderRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
			if (column == 0) {
				final PDCheckBox chk = new PDCheckBox(value + "");
				chk.setSelected(masterSelected);
				chk.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						masterSelected = chk.isSelected();
						for (SelectableObjectBean st : tableModel.getValues()) {
							st.setSelected(masterSelected);
						}
						fireSelectionEvent();
						tableModel.fireTableDataChanged();
					}
				});
				return chk;
			} else {
				return new PDLabel(value + "", PDLabel.STYLE.FIELD_LABEL);
			}
		}
	}

	public void setMasterSelected(boolean masterSelected) {
		this.masterSelected = masterSelected;
	}

	private void fireSelectionEvent() {
		ActionEvent e = new ActionEvent(this, null);
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	public void addSelectionListener(ActionListener l) {
		listeners.add(l);
	}
}
