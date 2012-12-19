package com.maydesk.base.gui;

import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.layout.GridLayoutData;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author Alejandro Salas
 */
public class DlgPassword extends PDOkCancelDialog {

	private MUser user;
	private PasswordField txtPassword1;
	private PasswordField txtPassword2;

	public DlgPassword() {
		super(PDBeanTerms.Change_password, 300, 180);
		this.user = PDUserSession.getInstance().getUser();
		setModal(true);
		initGUI();
	}

	private void initGUI() {

		setLeft(200);

		Grid grid = new Grid(2);

		GridLayoutData gld = new GridLayoutData();
		gld.setInsets(new Insets(6, 6, 3, 3));

		addMainComponent(grid);

		PDLabel lblPassword1 = new PDLabel(PDBeanTerms.New_password);
		lblPassword1.setLayoutData(gld);
		grid.add(lblPassword1);

		txtPassword1 = new PasswordField();
		txtPassword1.setWidth(new Extent(100));
		gld = new GridLayoutData();
		gld.setInsets(new Insets(3, 6, 6, 3));
		txtPassword1.setLayoutData(gld);
		grid.add(txtPassword1);

		PDLabel lblPassword2 = new PDLabel(PDBeanTerms.Repeat_password);
		lblPassword2.setLayoutData(gld);
		gld = new GridLayoutData();
		gld.setInsets(new Insets(6, 3, 3, 6));
		grid.add(lblPassword2);

		txtPassword2 = new PasswordField();
		txtPassword2.setWidth(new Extent(100));
		gld = new GridLayoutData();
		gld.setInsets(new Insets(3, 3, 6, 6));
		txtPassword2.setLayoutData(gld);
		grid.add(txtPassword2);
	}

	@Override
	protected boolean onOkClicked() {
		if (!txtPassword1.getText().equals(txtPassword2.getText())) {
			PDMessageBox.msgBox(PDBeanTerms.Error, PDBeanTerms.Passwords_do_not_match, 250, 120);
			return false;
		}
		try {
			PDUserSession.getInstance().changePassword(txtPassword1.getText());
		} catch (Exception e) {
			PDMessageBox.msgBox("Error", "Password could not be changed: " + e.getMessage(), 300, 120);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}