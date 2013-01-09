/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nextapp.echo.app.Component;
import nextapp.echo.app.ImageReference;

import org.hibernate.Session;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.util.IMessageListener;

/**
 * @author Alejandro Salas <br>
 *         Created on Jan 4, 2013
 */
public class PDChat extends Component {

	public static final String PROPERTY_OTHER_USER_ICON = "otherUserIcon";
	public static final String PROPERTY_OUTGOING_MESSAGE = "outgoingMsg";
	public static final String PROPERTY_INCOMING_MESSAGES = "incomingMessages";

	private Chat chat;
	private MessageListener listener;
	private List<Message> incomingMessages = new ArrayList<Message>();
	private IMessageListener pollListener;
	
	// This is necessary to send all messages to the client. 
	// Echo cannot serialize arrays or lists only maps. 
	private Map<String, String> incomingMessagesMap; 

	public PDChat(Chat chat, ImageReference icon) {
		this.chat = chat;
		setIcon(icon);

		listener = new MessageListener() {
			@Override
			public void processMessage(Chat chat, Message message) {
				incomingMessages.add(message);
			}
		};
		chat.addMessageListener(listener);

		pollListener = new IMessageListener() {
			@Override
			public void doPoll(Session session) {
				processIncomingMessages();
			}
		};
		PDApplicationInstance.getActivePD().addListener(pollListener);
	}

	private void processIncomingMessages() {
		Map<String, String> messagesMap = new HashMap<>();
		for (int i = 0; i < incomingMessages.size(); i++) {
			messagesMap.put(Integer.toString(i), incomingMessages.get(i).getBody());
		}
		incomingMessages.clear();

		if (messagesMap.isEmpty()) {
			return;
		}
		
		setIncomingMessagesMap(messagesMap);
	}

	public void setIcon(ImageReference icon) {
		set(PROPERTY_OTHER_USER_ICON, icon);
	}

	public ImageReference getIcon() {
		return (ImageReference) get(PROPERTY_OTHER_USER_ICON);
	}

	public Chat getChat() {
		return chat;
	}

	public Map<String, String> getIncomingMessagesMap() {
		return incomingMessagesMap;
	}

	public void setIncomingMessagesMap(Map<String, String> incomingMessagesMap) {
		Map<String, String> oldVal = this.incomingMessagesMap;
		this.incomingMessagesMap = incomingMessagesMap;
		firePropertyChange(PROPERTY_INCOMING_MESSAGES, oldVal, incomingMessagesMap);
	}
	
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_OUTGOING_MESSAGE.equals(inputName)) {
			sendMessage((String) inputValue);
		}
	}

	private void sendMessage(String msg) {
		try {
			chat.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public void closeChat() {
		chat.removeMessageListener(listener);
	}
}