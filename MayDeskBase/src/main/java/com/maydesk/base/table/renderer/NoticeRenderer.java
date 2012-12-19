/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import static com.maydesk.base.util.SopletsResourceBundle.nls;
import static nextapp.echo.app.event.TableModelEvent.UPDATE;
import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.gui.PDDialogRichText;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.table.TableModelEventLog;

/**
 * @author Alejandro Salas
 */
public class NoticeRenderer implements TableCellRenderer {

	private boolean editable;

	public NoticeRenderer(boolean editable) {
		this.editable = editable;
	}

	@Override
	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();

		final String strValue = value == null ? "" : value.toString();

		String strWithoutTags = strValue.replaceAll("<(\\w+)>", "");
		strWithoutTags = strWithoutTags.replaceAll("</(\\w+)>", "");
		strWithoutTags = strWithoutTags.replaceAll("<(\\w+)/>", "");
		strWithoutTags = strWithoutTags.replaceAll("&nbsp;", "");

		EImage16 image = null;
		if (strWithoutTags.length() == 0) {
			image = EImage16.comment2empty;
		} else {
			image = EImage16.comment2;

		}

		Button ret = new Button(image.getImage());
		ret.setToolTipText(nls(PDBeanTerms.Edit_notice));
		ret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnNoticeClicked(tableModel, strValue, col, row, editable);
			}
		});
		return ret;
	}

	private void btnNoticeClicked(final PDTableModel tableModel, final String strValue, final int col, final int row, final boolean editable) {
		PDDialogRichText dlgRichTextArea = new PDDialogRichText(nls(PDBeanTerms.Edit_notice), editable);
		dlgRichTextArea.setText(strValue);
		dlgRichTextArea.setModal(true);

		PDDesktop.getInstance().addWindow(dlgRichTextArea);

		dlgRichTextArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				PDDialogRichText dlgRichTextArea2 = (PDDialogRichText) evt.getSource();
				tableModel.setValueAt(dlgRichTextArea2.getText(), col, row);
				tableModel.fireTableChanged(new TableModelEventLog(tableModel, col, row, row, UPDATE, strValue, dlgRichTextArea2.getText()));
				// tableModel.fireTableDataChanged();
			}
		});
	}
}
