/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import java.util.List;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.DefaultTableModel;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.dao.DaoRole;
import com.maydesk.base.gui.PDMessageBox;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.table.PDTable;
import com.maydesk.base.util.ICrud;
import com.maydesk.base.util.IUserRoleFactory;

import echopoint.PushButton;

/**
 * @author chrismay
 */
public class PnlUserRoles extends Column implements ICrud {

	private MUser user;
	private PushButton btnAddRole;
	private PDTable table;
	private IUserRoleFactory userRoleFactory;

	public PnlUserRoles(Class<? extends IUserRoleFactory> factoryClass) {
		try {
			userRoleFactory = factoryClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initGUI();
	}

	private void initGUI() {
		setInsets(new Insets(6));
		setCellSpacing(new Extent(6));

		btnAddRole = new PushButton("Neue Rolle hinzu");
		btnAddRole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAddRoleClicked();
			}
		});
		add(btnAddRole);

		table = new PDTable();
		table.setWidth(new Extent(100, Extent.PERCENT));
		table.addColumn("Rolle", 40);
		table.addColumn("Kontext", 40);
		table.addColumn("Aktion", 20);
		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(Table table, Object value, int col, int r) {
				if (col == 2) {
					Row row = new Row();
					row.setCellSpacing(new Extent(6));

					Button btnDelete = new Button(EImage16.deletee.getImage());
					btnDelete.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							// btnDeleteClicked(userRole);
						}
					});
					row.add(btnDelete);
					return row;
				} else {
					return new Label(value + "");
				}
			}
		});
		add(table);
	}

	private void btnDeleteClicked(final MUserRole userRole) {
		PDMessageBox.confirmDeletion(userRole.toString()).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnDeleteClickedBis(userRole);
			}
		});
	}

	private void btnDeleteClickedBis(MUserRole userRole) {
		PDHibernateFactory.getSession().delete(userRole);
		PDHibernateFactory.getSession().flush();
		readFromModel(user);
	}

	private void btnAddRoleClicked() {
		final DlgAddRole dlg = new DlgAddRole(user, "com.maydesk.dvratio.DVRUserRoleFactory"); // XXX
		dlg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MUserRole userRole = dlg.getUserRole();
				if (userRole == null)
					return;
				PDHibernateFactory.getSession().save(userRole);
				PDHibernateFactory.getSession().flush();
				readFromModel(user);
			}
		});
		PDDesktop.getInstance().addWindow(dlg);
	}

	@Override
	public void readFromModel(MBase model) {
		if (model instanceof MUser) {
			this.user = (MUser) model;
		} else {
			int userId = ((MDataLink) model).getTargetId();
			this.user = MBase.loadById(MUser.class, userId);
		}
		List<MUserRole> userRoles = DaoRole.findRoles(user);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (MUserRole userRole : userRoles) {
			Object[] rowData = new Object[3];
			rowData[0] = userRoleFactory.getRoleCaption(userRole);
			rowData[1] = userRoleFactory.getContextDescription(userRole);
			rowData[2] = userRole;
			tableModel.addRow(rowData);
		}
		tableModel.fireTableDataChanged();
	}

	public void setEditing(boolean isEditing) {
	}

	public void applyToModel() {
	}

	public String getError() {
		return null;
	}

	@Override
	public Component getFocusComponent() {
		return null;
	}

	@Override
	public Class getModelClass() {
		return MUser.class;
	}
}