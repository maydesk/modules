/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListSelectionModel;
import nextapp.echo.app.list.ListSelectionModel;
import nextapp.echo.app.table.DefaultTableColumnModel;
import nextapp.echo.app.table.TableCellRenderer;
import nextapp.echo.app.table.TableColumn;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MAvatar;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.logical.SopUser;
import com.maydesk.base.table.BaseTableModel;
import com.maydesk.base.table.PDTable;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDButton;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author Alejandro Salas <br>
 *         Created on Oct 11, 2012
 */
public class DlgAddFriend extends PDOkCancelDialog {

	private PDTextField txtJabberId;
	private PDTextField txtFirstName;
	private PDTextField txtLastName;
	private PDButton btnSearch;
	private PDTable tblFriends;

	public DlgAddFriend() {
		super(PDBeanTerms.Add_friend, 350, 410);
		initGUI();
	}

	private void initGUI() {
		PDGrid grid = new PDGrid(2);
		pnlMainContainer.add(grid);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnSearchClicked();
			}
		};

		grid.addLabel(SopUser.jabberId);
		txtJabberId = new PDTextField();
		PDApplicationInstance.getActivePD().setFocusedComponent(txtJabberId);
		txtJabberId.addActionListener(listener);
		grid.addFill(txtJabberId);

		grid.addLabel(SopUser.firstName);
		txtFirstName = new PDTextField();
		txtFirstName.addActionListener(listener);
		grid.addFill(txtFirstName);

		grid.addLabel(SopUser.lastName);
		txtLastName = new PDTextField();
		txtLastName.addActionListener(listener);
		grid.addFill(txtLastName);

		grid.addLabel("");
		btnSearch = new PDButton(nls(PDBeanTerms.Search));
		btnSearch.addActionListener(listener);
		grid.addFill(btnSearch);

		UserTableModel userTableModel = new UserTableModel();
		tblFriends = new PDTable(userTableModel, userTableModel.getColumnModel());
		tblFriends.setSelectionEnabled(true);

		DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblFriends.setSelectionModel(selectionModel);
		pnlMainContainer.add(tblFriends);
	}

	@Override
	protected Component getMainContainer() {
		SplitPane splitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(100));
		return splitPane;
	}

	private void btnSearchClicked() {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);

		if (!PDUtil.isEmpty(txtJabberId.getText())) {
			criteria.add(Restrictions.like("jabberId", ("%" + txtJabberId.getText() + "%")));
		}

		List<MUser> userList = criteria.list();
		List<UserEntry> entryList = new ArrayList<UserEntry>();
		for (MUser user : userList) {
			VCard vCard = PDUserSession.getInstance().getVCard(user.getJabberId());

			String txt = txtFirstName.getText();
			if (!PDUtil.isEmpty(txt) && !vCard.getFirstName().contains(txt)) {
				continue;
			}

			txt = txtLastName.getText();
			if (!PDUtil.isEmpty(txt) && !vCard.getLastName().contains(txt)) {
				continue;
			}

			entryList.add(new UserEntry(user, vCard));
		}
		((UserTableModel) tblFriends.getModel()).setItemList(entryList);
	}

	@Override
	protected boolean onOkClicked() {
		if (tblFriends.getSelectionModel().isSelectionEmpty()) {
			return true;
		}

		int selectedIndex = tblFriends.getSelectionModel().getMinSelectedIndex();
		UserEntry entry = ((UserTableModel) tblFriends.getModel()).getItem(selectedIndex);

		MAvatar avatar = new MAvatar();
		avatar.setPositionX(350);
		avatar.setPositionY(40);
		avatar.setPerson(entry.user);
		avatar.setOwner(PDUserSession.getInstance().getUser());
		PDHibernateFactory.getSession().save(avatar);
		PDDesktop.getInstance().addPerson(avatar);

		return true;
	}

	class UserEntry {
		private MUser user;
		private VCard vCard;

		private UserEntry(MUser user, VCard vCard) {
			this.user = user;
			this.vCard = vCard;
		}
	}

	class UserTableModel extends BaseTableModel<UserEntry> {

		@Override
		protected void initColumnModel() {
			columnModel = new DefaultTableColumnModel();
			TableColumn column;

			int i = -1;

			column = new TableColumn(++i);
			column.setHeaderValue("");
			column.setWidth(new Extent(100, Extent.PERCENT));
			column.setCellRenderer(new TableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(Table arg0, Object obj, int arg2, int arg3) {
					VCard vCard = ((UserEntry) obj).vCard;
					String txt = null;
					if (PDUtil.isEmpty(vCard.getFirstName())) {
						txt = vCard.getJabberId();
					} else {
						txt = vCard.getFirstName();
						txt += !PDUtil.isEmpty(vCard.getLastName()) ? " " + vCard.getLastName() : "";
						txt += !PDUtil.isEmpty(vCard.getJabberId()) ? "/" + vCard.getJabberId() : "";
					}

					ImageReference img = PDUserSession.getInstance().getImage(vCard, vCard.getJabberId(), 60);
					return new Label(txt, img);
				}
			});
			columnModel.addColumn(column);
		}

		@Override
		public Object getValueAt(int col, int row) {
			return itemList.get(row);
		}
	}
}