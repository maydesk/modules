/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.jivesoftware.smack.packet.Presence;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.model.StatusMode;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDButton;
import com.maydesk.base.widgets.PDCombo;

/**
 * @author chrismay
 */
public class PnlUserStatus extends Row implements IPlugTarget, ActionListener {

	private TextField txtText;
	private PDButton btnStart;
	private PDButton btnFinish;
	private PDCombo<StatusMode> cboStatus;

	public PnlUserStatus() {
		txtText = new TextField();
		txtText.setBackground(Color.TRANSPARENT);
		txtText.setForeground(Color.WHITE);
		txtText.setText(PDUserSession.getInstance().getCurrentPresence().getStatus());
		txtText.setWidth(new Extent(220));
		txtText.setBorder(PDUtil.emptyBorder());
		txtText.setAlignment(Alignment.ALIGN_CENTER);
		txtText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnFinishClicked();
			}
		});
		add(txtText);

		btnStart = new PDButton("", PDButton.STYLE.TRANSPARENT);
		btnStart.setForeground(Color.WHITE);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEditing(true);
				PDApplicationInstance.getActivePD().setFocusedComponent(txtText);
			}
		});
		btnStart.setText(PDUtil.null2dots(txtText.getText()));
		btnStart.setWidth(new Extent(220));
		btnStart.setToolTipText("Show your current status to other users");
		add(btnStart);

		btnFinish = new PDButton("Go");
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnFinishClicked();
			}
		});
		add(btnFinish);
		setEditing(false);

		cboStatus = new PDCombo<StatusMode>(StatusMode.values());
		StatusMode statusMode = StatusMode.findByPresence(PDUserSession.getInstance().getCurrentPresence());
		cboStatus.setSelectedItem(statusMode);
		cboStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cboClicked();
			}
		});
		add(cboStatus);
	}

	private void btnFinishClicked() {
		PDUserSession.getInstance().setCurrentStatus(txtText.getText());
		setEditing(false);
		btnStart.setText(PDUtil.null2dots(txtText.getText()));
	}

	private void cboClicked() {
		PDUserSession.getInstance().setStatusMode(cboStatus.getSelectedItem().getMode());
	}

	private void setEditing(boolean editing) {
		txtText.setVisible(editing);
		btnStart.setVisible(!editing);
		btnFinish.setVisible(editing);
	}

	@Override
	public void initWire(XmlBaseEntry parentWire) {
		// XXX: Ugly! To update status at login time
		PDUserSession.getInstance().setLoginPresenceListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		Presence presence = (Presence) evt.getSource();
		txtText.setText(presence.getStatus());
		cboStatus.setSelectedItem(StatusMode.findByPresence(presence));
	}
}