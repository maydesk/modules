/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.Cookie;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.webcontainer.ClientProperties;
import nextapp.echo.webcontainer.ContainerContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.MDC;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.gui.user.FrmChat;
import com.maydesk.base.model.MActivity;
import com.maydesk.base.model.MChatHistory;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.enums.SopGender;
import com.maydesk.base.util.ByteArrayImageReference;
import com.maydesk.base.util.IMessageListener;
import com.maydesk.base.util.PDFormat;
import com.maydesk.base.util.SimpleName;
import com.maydesk.base.widgets.PDAvatar;

/**
 * @author chrismay
 */
public class PDUserSession implements Serializable {

	public final static String LOGIN_COOKIE_NAME = "SOPLETS_LOGIN";

	protected MUser user;
	protected DaoUser daoUser;
	protected List<ActionListener> actionListeners = new Vector<ActionListener>();
	protected XMPPConnection connection;
	private List<Presence> presenceChanges = new ArrayList<Presence>();
	private List<Message> incomingMessages = new ArrayList<Message>();
	private HashSet<String> openChatWindowIds = new HashSet<String>();

	public PDUserSession() {
		daoUser = new DaoUser();

		PDApplicationInstance.getActivePD().addListener(new IMessageListener() {
			@Override
			public void doPoll(Session session) {
				updateBubbles();
			}
		});
	}

	public void doLogin(String jabberId, String password) {
		
		if (jabberId.indexOf('@') == -1) {
			throw new IllegalArgumentException("Invalid Jabber ID!");
		}
		StringTokenizer st = new StringTokenizer(jabberId, "@");
		String login = st.nextToken();
		String server = st.nextToken();

		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
		initConnection(server);

		// XXX: Ugly! Listener to update the status of the user at login time
		addLoginPresenceListener();

		// Log into the server
		try {
			connection.login(login, password, "MayDesk");
		} catch (XMPPException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Invalid password!");
		}

		// PacketCollector myCollector = connection.createPacketCollector(null);
		PacketListener myListener = new PacketListener() {
			@Override
			public void processPacket(Packet packet) {
				// Do something with the incoming packet here.
				if (packet instanceof Message) {
					incomingMessages.add((Message) packet);
				}
			}
		};
		connection.addPacketListener(myListener, null);

		user = DaoUser.findUserByJabberId(jabberId);
		if (user == null) {
			// create new CD user
			user = new MUser();
			user.setJabberId(jabberId);
			PDHibernateFactory.getSession().save(user);
		}

		System.out.println(PDFormat.formatLong(Calendar.getInstance().getTime()) + " ::: " + login + " ::: " + (user != null ? "OK" : "Err")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		logAction("login", "Login", 0, null, null, null, null); //$NON-NLS-1$ //$NON-NLS-2$
		fireAction();

		connection.getRoster().addRosterListener(new RosterListener() {
			@Override
			public void presenceChanged(Presence presence) {
				doPresenceChanged(presence);
			}

			@Override
			public void entriesUpdated(Collection<String> addresses) {
			}

			@Override
			public void entriesDeleted(Collection<String> addresses) {
			}

			@Override
			public void entriesAdded(Collection<String> addresses) {
			}
		});
		PDUserSession.getInstance().setCookie(LOGIN_COOKIE_NAME, user.getJabberId());
	}

	// ////////////////////////////////////////
	// //////// XXX: Ugly! To update status at login time
	// ////////////////////////////////////////
	private ActionListener loginPresenceListener;

	public void setLoginPresenceListener(ActionListener loginPresenceListener) {
		this.loginPresenceListener = loginPresenceListener;
	}

	private void addLoginPresenceListener() {
		final PDApplicationInstance appInstance = PDApplicationInstance.getActivePD();
		RosterListener rosterListener = new RosterListener() {
			@Override
			public void entriesAdded(Collection<String> addresses) {
			}

			@Override
			public void entriesUpdated(Collection<String> addresses) {
			}

			@Override
			public void entriesDeleted(Collection<String> addresses) {
			}

			@Override
			public void presenceChanged(final Presence presence) {
				final TaskQueueHandle handle = appInstance.createTaskQueue();

				Runnable r = new Runnable() {
					@Override
					public void run() {
						ActionEvent evt = new ActionEvent(presence, "");
						if (loginPresenceListener != null) loginPresenceListener.actionPerformed(evt);
						// We remove it on the first call, it's just a one time thing
						appInstance.removeTaskQueue(handle);
					}
				};
				appInstance.enqueueTask(handle, r);
				connection.getRoster().removeRosterListener(this);
			}
		};
		connection.getRoster().addRosterListener(rosterListener);
	}

	// ////////////////////////////////////////
	// // End of Update Status at login time
	// ////////////////////////////////////////

	private void initConnection(String server) {
		ConnectionConfiguration config = new ConnectionConfiguration(server, 5222);
		connection = new XMPPConnection(config);
		try {
			connection.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("No connection to " + server);
		}
	}

	private synchronized void updateBubbles() {
		for (Presence presence : presenceChanges) {
			StringTokenizer st = new StringTokenizer(presence.getFrom(), "/");
			String jabberId = st.nextToken();
			PDAvatar avatar = PDDesktop.getInstance().findAvatarByJabberId(jabberId);
			if (avatar != null) {
				avatar.checkPresenceStatus();
			}
			daoUser.updateLatestPresence(jabberId, presence);
		}
		presenceChanges.clear();

		for (Message message : incomingMessages) {
			StringTokenizer st = new StringTokenizer(message.getFrom(), "/");
			String jabberId = st.nextToken();
			if (message.getThread() != null) {
				// chatting, do not show as bubble
				if (!openChatWindowIds.contains(message.getThread())) {
					createNewChatWindow(jabberId, message.getBody());
				}
				continue;
			}
			PDAvatar avatar = PDDesktop.getInstance().findAvatarByJabberId(jabberId);
			if (avatar != null && message.getBody() != null) {
				avatar.setBubbleMessage(message.getBody());
			}
		}
		incomingMessages.clear();
	}

	private void doPresenceChanged(Presence presence) {
		presenceChanges.add(presence);
	}

	public void doLogout() {
		user = null;
		connection.disconnect();
		fireAction();
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public MUser getUser() {
		return user;
	}

	public void setUser(MUser user) {
		boolean userChanged = this.user != user;
		this.user = user;
		if (userChanged) {
			fireAction();
		}
	}

	public static synchronized PDUserSession getInstance() {
		PDUserSession userSession = (PDUserSession) ApplicationInstance.getActive().getContextProperty(PDUserSession.class.getName());
		if (userSession == null) {
			userSession = new PDUserSession();
			ApplicationInstance.getActive().setContextProperty(PDUserSession.class.getName(), userSession);
		}
		return userSession;
	}

	public void logAction(String activityType, String contextTex, int contextId, String oldValue, String newValue, String textGerman, String textEnglish) {
		MActivity activity = new MActivity();
		ContainerContext context = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		ClientProperties props = context.getClientProperties();
		Object ip = props.get(ClientProperties.REMOTE_HOST);
		activity.setUserLogin(ip + ""); //$NON-NLS-1$
		if (user != null) {
			activity.setUserId(user.getId());
		}
		activity.setActivityString(activityType);
		activity.setContextText(contextTex);
		activity.setContextId(contextId);
		activity.setOldValue(oldValue);
		activity.setNewValue(newValue);
		activity.setDate(Calendar.getInstance().getTime());
		activity.setTextGerman(textGerman);
		activity.setTextEnglish(textEnglish);
		Session session = PDHibernateFactory.getSession();
		session.save(activity);
	}

	protected void fireAction() {
		ActionEvent e = new ActionEvent(this, null);
		for (ActionListener l : actionListeners) {
			l.actionPerformed(e);
		}
	}

	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}

	public String getCookie(String cookieName) {
		ContainerContext ctx = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		Cookie[] cookies = ctx.getCookies();
		for (Cookie cookie : cookies) {
			if (StringUtils.equals(cookie.getName(), cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public void setCookie(String cookieName, String value) {
		Cookie userCookie = new Cookie(cookieName, value);
		ContainerContext ctx = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		userCookie.setMaxAge(60 * 60 * 24 * 360); // expires after 360 days
		ctx.addCookie(userCookie);
	}

	public void changePassword(String newPassword) throws Exception {
		connection.getAccountManager().changePassword(newPassword);
	}

	public void addToRoster(MUser user) {
		try {
			connection.getRoster().createEntry(user.getJabberId(), user.getJabberId(), null);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public ImageReference getImage(String jabberId) {
		return getImage(jabberId, 60);
	}

	public ImageReference getImage(VCard vCard, String jabberId, int size) {
		if (vCard == null) {
			return null;
		}

		byte[] bytes = vCard.getAvatar();
		if (bytes != null) {
			return new ByteArrayImageReference(bytes, "image/png", size);
		}

//		if (jabberUser.getGender() == SopGender.female) {
//			return new ResourceImageReference("img/silhouette-female.gif");
//		}

		return new ResourceImageReference("img/silhouette-male.gif");
	}

	public ImageReference getImage(String jabberId, int size) {
		VCard vCard = getVCard(jabberId);
		return getImage(vCard, jabberId, size);
	}

	// find the last chat thread if it is no older than 1 hour
	// otherwise create a new chat
	private Chat findLastChat(String otherUserId) {
		ChatManager chatmanager = connection.getChatManager();
		String threadId = user.getJabberId() + "|" + otherUserId + "|";
		Calendar calOneHourBefore = Calendar.getInstance();
		calOneHourBefore.add(Calendar.MINUTE, -60);

		Criteria c = PDHibernateFactory.getSession().createCriteria(MChatHistory.class);
		c.add(Restrictions.eq("user", user));
		c.add(Restrictions.eq("otherUser", otherUserId));
		c.add(Restrictions.ge("lastActivity", calOneHourBefore.getTime()));
		c.addOrder(Order.desc("lastActivity"));
		c.setMaxResults(1);
		MChatHistory chatHistory = (MChatHistory) c.uniqueResult();
		Chat chat = null;
		if (chatHistory != null) {
			chat = chatmanager.getThreadChat(threadId + chatHistory.getId());
			if (chat != null) {
				return chat;
			}
		}
		chatHistory = new MChatHistory();
		chatHistory.setUser(user);
		chatHistory.setOtherUser(otherUserId);
		chatHistory.setLastActivity(new Date());
		PDHibernateFactory.getSession().save(chatHistory);

		return chatmanager.createChat(otherUserId, threadId + chatHistory.getId(), null);

	}

	public void createNewChatWindow(String targetJabberId, String initialMessage) {
		Chat chat = findLastChat(targetJabberId);
		PDDesktop.getInstance().addWindow(new FrmChat(chat, initialMessage));
		openChatWindowIds.add(chat.getThreadID());
	}

	public void removeChatWindowId(String threadId) {
		openChatWindowIds.remove(threadId);
	}

	public void setCurrentStatus(String status) {
		updateStatus(status, getCurrentPresence().getMode());
	}

	public void setStatusMode(Mode statusMode) {
		updateStatus(getCurrentPresence().getStatus(), statusMode);
	}

	private void updateStatus(String statusStr, Mode statusMode) {
		Presence presence = new Presence(Type.available);
		presence.setMode(statusMode);
		presence.setFrom(user.getJabberId());
		presence.setStatus(statusStr);
		connection.sendPacket(presence);
	}

	public Translatable registerUser(String username, String server, String password, VCard vcard) {
		try {
			initConnection(server);
			connection.getAccountManager().createAccount(username, password);
		} catch (Exception e) {
			if (e.getMessage().contains("409")) {
				connection.disconnect();
				return new SimpleName("User name already taken");
			}
			e.printStackTrace();
			connection.disconnect();
			return new SimpleName(e.getMessage());
		}

		try {
			String jabberId = username + "@" + server;
			connection.login(jabberId, password, "MayDesk");
			vcard.setJabberId(jabberId);
			vcard.save(connection);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateAvatar(MMediaFile imageFile) {
		if (imageFile == null)
			return;
		VCard vCard = new VCard();
		try {
			vCard.load(connection, user.getJabberId());
			vCard.setAvatar(imageFile.getFileBytes(), imageFile.getContentType());
			vCard.save(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VCard getVCard(String jabberId) {
		VCard vCard = new VCard();
		try {
			vCard.load(connection, jabberId);
			vCard.setJabberId(jabberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vCard;
	}

	public void updateVCard(VCard vcard) {
		try {
			vcard.save(connection);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public Presence getCurrentPresence(String jabberId) {
		return connection.getRoster().getPresence(jabberId);
	}

	public Presence getCurrentPresence() {
		return getCurrentPresence(getUser().getJabberId());
	}
}