package com.maydesk.base.gui.user;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.logical.SopUser;
import com.maydesk.base.table.PDTable;
import com.maydesk.base.table.PDTableModel2;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDGrid;

import echopoint.PushButton;

public class DlgSelectUser extends PDOkCancelDialog {

	private PDTableModel2 tableModel;
	private PDTable table;
	private MUser selectedUser;
	private TextField txtLastName;
	private TextField txtFirstName;
	private TextField txtLogin;
	
	public DlgSelectUser() {
	    super("Search Facebook User", 450, 520);
	    initGUI();
	    readFromModel();
    }

	private void readFromModel() {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		boolean hasRestriction = false;
		if (!PDUtil.isEmpty(txtLastName.getText())) {
			criteria.add(Restrictions.like("lastName", txtLastName.getText().trim() + "%")); //$NON-NLS-1$ //$NON-NLS-2$
			hasRestriction = true;
		}
		if (!PDUtil.isEmpty(txtFirstName.getText())) {
			criteria.add(Restrictions.like("firstName", txtFirstName.getText().trim() + "%")); //$NON-NLS-1$ //$NON-NLS-2$
			hasRestriction = true;
		}
		if (!PDUtil.isEmpty(txtLogin.getText())) {
			criteria.add(Restrictions.like("login", txtLogin.getText().trim() + "%")); //$NON-NLS-1$ //$NON-NLS-2$
			hasRestriction = true;
		} 
		if (!hasRestriction) {
			criteria.add(Restrictions.isNotNull("lastName")); //$NON-NLS-1$
			criteria.add(Restrictions.not(Restrictions.eq("lastName", ""))); //$NON-NLS-1$ //$NON-NLS-2$
		}
		criteria.addOrder(Order.asc("lastName")); //$NON-NLS-1$
		criteria.addOrder(Order.asc("firstName"));		 //$NON-NLS-1$
		criteria.setMaxResults(20);
		List<MUser> users = criteria.list();
		tableModel.setValues(users);
		tableModel.fireTableDataChanged();
		if (users.size() > 0) {
			table.getSelectionModel().setSelectedIndex(0, true);
		}
	}

	private void initGUI() {
		PDGrid grdFilter = new PDGrid(4);
		addMainComponent(grdFilter);
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            readFromModel();
            }
		};
		
		grdFilter.addLabel(SopUser.lastName);
		grdFilter.add(txtLastName = new TextField());
		txtLastName.addActionListener(listener);

		grdFilter.addLabel(SopUser.firstName);
		grdFilter.add(txtFirstName = new TextField());
		txtFirstName.addActionListener(listener);

		grdFilter.addLabel(SopUser.city);
		grdFilter.add(txtLogin = new TextField());
		txtLogin.addActionListener(listener);

		grdFilter.addEmpty();
		PushButton btnSearch = new PushButton(nls(PDBeanTerms.Search));
		grdFilter.add(btnSearch);
		btnSearch.addActionListener(listener);
		
		
		tableModel = new PDTableModel2() {
			@Override
			public Object getValueAt(int col, int row) {
				MUser user = (MUser)values.get(row);
				switch (col) {
				case 0: return user.getJabberId();
				case 1: return user.getDisplayName();
//				case 2: return user.getCity();
				}
				return user;
			}
		};
		table = new PDTable(tableModel);
		table.addColumn(nls(PDBeanTerms.Last_name), 10);
		table.addColumn(nls(PDBeanTerms.First_name), 10);
		table.addColumn("City", 10);
		table.setSelectionEnabled(true);
		table.setWidth(new Extent(100, Extent.PERCENT));
		addMainComponent(table);
	}
	
	protected Component getMainContainer() {
		SplitPane split = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		split.setAutoPositioned(true);
		split.setSeparatorColor(Color.LIGHTGRAY);
		return split;
	}
	
	protected boolean onOkClicked() {
		int row = table.getSelectionModel().getMinSelectedIndex();
		if (row < 0) return false;
		selectedUser = (MUser)tableModel.getValueAt(3, row);
		return true;
	}
	
	public MUser getSelectedUser() {
		return selectedUser;
	}
}
