/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDWindowPane;
import com.maydesk.base.util.IMessageListener;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author chrismay
 */
public class FrmChat extends PDWindowPane {

	private TextField txtMessage;
	private Chat chat;
	private Column colMessages;
	private DateFormat df = new SimpleDateFormat("HH:mm");
	private Label lblOtherUser;
	private MessageListener listener;
	private IMessageListener pollListener;
	private List<Message> incomingMessages = new ArrayList<Message>();

	public FrmChat(Chat chat2, String initialMessage) {
		this.chat = chat2;
		setTitle("Talking with " + chat.getParticipant() + "...");
		setTitleFont(new Font(new Typeface("Verdana"), Font.BOLD, new Extent(11)));

		listener = new MessageListener() {
			@Override
			public void processMessage(Chat chat, Message message) {
				incomingMessages.add(message);
			}
		};
		chat.addMessageListener(listener);

		setWidth(new Extent(300));
		setHeight(new Extent(300));

		SplitPane split = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		split.setSeparatorPosition(new Extent(30));
		add(split);

		Row rowMessage = new Row();
		rowMessage.setInsets(new Insets(0, 0, 6, 0));
		split.add(rowMessage);

		lblOtherUser = new Label("");
		lblOtherUser.setIcon(PDUserSession.getInstance().getImage(chat.getParticipant(), 24));
		rowMessage.add(lblOtherUser);

		txtMessage = new TextField();
		txtMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		txtMessage.setWidth(new Extent(100, Extent.PERCENT));
		RowLayoutData rld = new RowLayoutData();
		rld.setWidth(new Extent(100, Extent.PERCENT));
		txtMessage.setLayoutData(rld);
		rowMessage.add(txtMessage);

		colMessages = new Column();
		SplitPaneLayoutData spld = new SplitPaneLayoutData();
		spld.setAlignment(Alignment.ALIGN_BOTTOM);
		colMessages.setLayoutData(spld);
		split.add(colMessages);

		addWindowPaneListener(new WindowPaneListener() {
			@Override
			public void windowPaneClosing(WindowPaneEvent e) {
				chat.removeMessageListener(listener);
				PDUserSession.getInstance().removeChatWindowId(chat.getThreadID());
			}
		});

		if (!StringUtils.isEmpty(initialMessage)) {
			appendMessage(initialMessage, true);
		}
		PDApplicationInstance.getActivePD().setFocusedComponent(txtMessage);

		pollListener = new IMessageListener() {
			@Override
			public void doPoll(Session session) {
				showIncomingMessages();
			}
		};
		PDApplicationInstance.getActivePD().addListener(pollListener);
	}

	private void showIncomingMessages() {
		for (Message message : incomingMessages) {
			appendMessage(message.getBody(), true);
		}
		incomingMessages.clear();
	}

	protected void sendMessage() {
		try {
			chat.sendMessage(txtMessage.getText());
			appendMessage(txtMessage.getText(), false);
			PDApplicationInstance.getActivePD().setFocusedComponent(txtMessage);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	private void appendMessage(String msg, boolean receiving) {
		Row row = new Row();
		row.setInsets(new Insets(1));
		row.setCellSpacing(new Extent(6));
		if (receiving) {
			row.setBackground(new Color(230, 230, 230));
		}
		colMessages.add(row);

		PDLabel lblTime = new PDLabel(PDLabel.STYLE.SMALL);
		lblTime.setText(df.format(Calendar.getInstance().getTime()));
		RowLayoutData rld = new RowLayoutData();
		rld.setAlignment(Alignment.ALIGN_TOP);
		lblTime.setLayoutData(rld);
		row.add(lblTime);

		Label lblMessage = new Label(msg);
		lblMessage.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(11)));
		lblMessage.setLineWrap(true);
		row.add(lblMessage);
		txtMessage.setText("");
	}
}