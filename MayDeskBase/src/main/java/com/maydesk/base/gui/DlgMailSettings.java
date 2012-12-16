/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import javax.mail.AuthenticationFailedException;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.lang.StringUtils;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MMailSettings;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.PDMailBean;
import com.maydesk.base.widgets.PDButton;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDNumericField;
import com.maydesk.base.widgets.PDTextField;

/** 
 * A dialog to set the server mail settings
 * 
 * @author Daniel
 * Created on Jun 20, 2007
 */
public class DlgMailSettings extends PDOkCancelDialog {

	private CheckBox chkAuth;
	private CheckBox chkDebug;
	private CheckBox chkEnabled;
	private CheckBox chkStarttls;
	private CheckBox chkSsl;
	private MMailSettings mailSettings;
	private PDTextField txtFrom;
	private PDTextField txtAuthUser;
	private PasswordField txtAuthPassword;
	private PDTextField txtTestRecipient;
	private PDNumericField txtPort;
	private PDTextField txtServerName;
	

	public DlgMailSettings() {
		super(PDBeanTerms.Mail_Setup, 350, 410);
		initGUI();
		loadData();
		updateGUIFromData();
		enableGUI();
	}

	@Override
	protected boolean onOkClicked() {
		if (!validateGUI()) {
			PDMessageBox.msgBox(StandardTerms.Warning, PDBeanTerms.You_must_fill_all_fields_in_the_form, 220, 130);
			return false;
		}
		updateDataFromGUI();
		PDHibernateFactory.getSession().saveOrUpdate(mailSettings);
		return true;
	}
	
	private void btnTestClicked() {
		try {
			updateDataFromGUI();
			PDHibernateFactory.getSession().saveOrUpdate(mailSettings);
						
			PDMailBean mailBean = new PDMailBean();
			mailBean.sendMail(mailSettings.getTestRecipient(), "Test", "Testmail", null);
		} catch (AuthenticationFailedException afe) {
			PDMessageBox.msgBox("Mail Error", "Authentication failed!", 280, 150);
			afe.printStackTrace();
			return;
		} catch (Exception e) {
			PDMessageBox.msgBox("Mail Error", e.getMessage(), 400, 220);
			e.printStackTrace();
			return;
		}
		PDMessageBox.msgBox("Mail sent", "A test mail has been succesfully sent to " + txtTestRecipient.getText(), 200, 200);
	}

	protected void enableGUI() {
		boolean isEnabled = chkEnabled.isSelected();

		txtServerName.setEnabled(isEnabled);
		txtFrom.setEnabled(isEnabled);
		txtAuthUser.setEnabled(isEnabled);
		txtTestRecipient.setEnabled(isEnabled);
		txtAuthPassword.setEnabled(isEnabled);
		txtPort.setEnabled(isEnabled);
		chkAuth.setEnabled(isEnabled);
		chkDebug.setEnabled(isEnabled);
		chkStarttls.setEnabled(isEnabled);
		chkSsl.setEnabled(isEnabled);
	}

	protected void initGUI() {
		PDGrid grid = new PDGrid(2);
		addMainComponent(grid);

		grid.add(new PDLabel(PDBeanTerms.Enable£));
		chkEnabled = new CheckBox();
		chkEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				enableGUI();
			}
		});

		grid.add(chkEnabled);

		grid.add(new PDLabel(PDBeanTerms.Server_name));
		txtServerName = new PDTextField(getClass().getSimpleName() + "txtServerName");
		grid.add(txtServerName);

		grid.add(new PDLabel(PDBeanTerms.Port));
		txtPort = new PDNumericField(0);
		grid.add(txtPort);

		grid.add(new PDLabel(PDBeanTerms.From));
		txtFrom = new PDTextField(getClass().getSimpleName() + "txtFrom");
		grid.add(txtFrom);

		grid.add(new PDLabel(PDBeanTerms.Sender_ID));
		txtAuthUser = new PDTextField(getClass().getSimpleName() + "txtAuthUser");
		grid.add(txtAuthUser);

		grid.add(new PDLabel(StandardTerms.Password));
		txtAuthPassword = new PasswordField();
		grid.add(txtAuthPassword);

		grid.add(new PDLabel(PDBeanTerms.Test_recipient));
		txtTestRecipient = new PDTextField(getClass().getSimpleName() + "txtTestRecipient");
		grid.add(txtTestRecipient);

		grid.add(new PDLabel(PDBeanTerms.auth));
		chkAuth = new CheckBox();
		grid.add(chkAuth);

		grid.add(new PDLabel(PDBeanTerms.Start_TLS));
		chkStarttls = new CheckBox();
		grid.add(chkStarttls);

		grid.add(new PDLabel(PDBeanTerms.SSL));
		chkSsl = new CheckBox();
		grid.add(chkSsl);

		grid.add(new PDLabel(PDBeanTerms.Debug£));
		chkDebug = new CheckBox();
		grid.add(chkDebug);

		grid.add(new Label(""));
		PDButton btnTest = new PDButton("Test Mail");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTestClicked();
			}
		});
		grid.add(btnTest);
		
		grid.add(new Label(""));
		PDButton btnException = new PDButton("Test Exception");
		btnException.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//test exception handler, a dialog should appear,
				//and an email sent to the admin
				throw new IllegalArgumentException("Test exception");
			}
		});
		grid.add(btnException);
	}

	private void loadData() {
		mailSettings = (MMailSettings)PDHibernateFactory.getSession().createCriteria(MMailSettings.class).uniqueResult();
		if (mailSettings == null) {
			mailSettings = new MMailSettings();
		}
	}

	protected void updateDataFromGUI() {
		mailSettings.setEnabled(chkEnabled.isSelected());
		mailSettings.setServerName(txtServerName.getText());
		mailSettings.setPort((int) txtPort.getNumber());
		mailSettings.setFromUser(txtFrom.getText());
		mailSettings.setAuthUser(txtAuthUser.getText());
		mailSettings.setTestRecipient(txtTestRecipient.getText());
		mailSettings.setAuthPassword(txtAuthPassword.getText());
		mailSettings.setAuth(chkAuth.isSelected());
		mailSettings.setDebug(chkDebug.isSelected());
		mailSettings.setStarttls(chkStarttls.isSelected());
		mailSettings.setSecureSocket(chkSsl.isSelected());
	}

	protected void updateGUIFromData() {
		chkEnabled.setSelected(mailSettings.isEnabled());
		txtServerName.setText(mailSettings.getServerName());
		txtPort.setNumber(mailSettings.getPort());
		txtFrom.setText(mailSettings.getFromUser());
		txtAuthUser.setText(mailSettings.getAuthUser());
		txtTestRecipient.setText(mailSettings.getTestRecipient());
		txtAuthPassword.setText(mailSettings.getAuthPassword());
		chkAuth.setSelected(mailSettings.isAuth());
		chkDebug.setSelected(mailSettings.isDebug());
		chkStarttls.setSelected(mailSettings.isStarttls());
		chkSsl.setSelected(mailSettings.isSecureSocket());
	}

	private boolean validateGUI() {
		boolean b = true;
		b = !StringUtils.isEmpty(txtServerName.getText());
		b &= !(txtPort.getNumber() == 0);
		b &= !StringUtils.isEmpty(txtFrom.getText());
		b &= !StringUtils.isEmpty(txtAuthUser.getText());
		b &= !StringUtils.isEmpty(txtTestRecipient.getText());
		b &= !StringUtils.isEmpty(txtAuthPassword.getText());
		return b;
	}
}
