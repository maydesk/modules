/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.Chat;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDWindowPane;
import com.maydesk.base.widgets.PDChat;

/**
 * @author Alejandro Salas <br>
 * Created on Jan 4, 2013
 */
public class FrmChat extends PDWindowPane {
	
	public FrmChat(Chat chat, String initialMessage) {
		setTitle("Talking with " + chat.getParticipant() + "...");
		setTitleFont(new Font(new Typeface("Verdana"), Font.BOLD, new Extent(11)));
		
		setWidth(new Extent(300));
		setHeight(new Extent(300));
		
		final PDChat pdChat = new PDChat(chat, PDUserSession.getInstance().getImage(chat.getParticipant(), 20));
		add(pdChat);
		
		addWindowPaneListener(new WindowPaneListener() {
			@Override
			public void windowPaneClosing(WindowPaneEvent e) {
				pdChat.closeChat();
				PDUserSession.getInstance().removeChatWindowId(pdChat.getChat().getThreadID());
			}
		});

		if (!StringUtils.isEmpty(initialMessage)) {
			Map<String, String> messagesMap = new HashMap<>();
			messagesMap.put("0", initialMessage);
			pdChat.setIncomingMessagesMap(messagesMap);
		}
	}

}