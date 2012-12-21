/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author chrismay
 */
public class Test {

	static Chat newChat;

	public static void main(String[] args) {

		try {

			// Create a connection to the jabber.org server on a specific port.
			ConnectionConfiguration config = new ConnectionConfiguration("xabber.de", 5222);
			// config.setCompressionEnabled(true);
			// config.setSASLAuthenticationEnabled(true);
			XMPPConnection connection = new XMPPConnection(config);
			connection.connect();

			// Log into the server
			connection.login("chris3", "weissbie", "TEST");

			// connection.getAccountManager().

			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("Fishing");
			connection.sendPacket(presence);

			ChatManager chatmanager = connection.getChatManager();
			newChat = chatmanager.createChat("chrismay@xabber.de", new MessageListener() {
				@Override
				public void processMessage(Chat chat, Message message) {
					System.out.println("Received message: " + message.getBody());
					try {
						newChat.sendMessage("You said: " + message.getBody());
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			newChat.sendMessage("Howdy!");
			Thread.sleep(50000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
