/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.hibernate.Query;
import org.hibernate.Session;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MMailSettings;

/**
 * @author Demián Gutierrez
 */
public class PDMailBean {

	protected String host;
	protected String port;
	protected String authUser;
	protected String authPassword;
	protected String from;
	protected String recipient;

	// ----------------------------------------

	public PDMailBean() {
		// Empty
	}

	// ----------------------------------------

	protected Properties getPropertyFromBean(MMailSettings mailSettings) {
		Properties settings = new Properties();

		host = mailSettings.getServerName();
		port = mailSettings.getPortStr();

		from = mailSettings.getFromUser();
		recipient = mailSettings.getTestRecipient();
		authUser = mailSettings.getAuthUser();
		authPassword = mailSettings.getAuthPassword();

		// ----------------------------------------
		// Basic settings
		// ----------------------------------------

		settings.put("mail.from", from);
		settings.put("mail.smtp.host", host);
		settings.put("mail.smtp.port", port);
		settings.put("mail.transport.protocol", "smtp");

		// ----------------------------------------
		// Debug settings
		// ----------------------------------------

		if (mailSettings.isDebug()) {
			settings.put("mail.debug", "true");
		}

		// ----------------------------------------
		// tls settings
		// ----------------------------------------

		if (mailSettings.isStarttls()) {
			settings.put("mail.smtp.starttls.enable", "true");
		}

		if (mailSettings.isSecureSocket()) {
			settings.put("mail.smtp.ssl", "true");
			settings.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			settings.put("mail.smtp.socketFactory.port", mailSettings.getPortStr());
			settings.put("mail.smtp.socketFactory.fallback", "false");
		}

		// ----------------------------------------
		// Authentication settings
		// ----------------------------------------

		if (mailSettings.isAuth()) {
			settings.put("mail.user", authUser);
			settings.put("mail.smtp.auth", "true");
		}

		return settings;
	}

	public static void main(String[] args) {
		PDMailBean b = new PDMailBean();
		try {
	        b.sendMail("mail@chrismay.de", "Test", "test", null);
        } catch (Exception e) {
	        e.printStackTrace();
        }
		
	}
	
	
	//	 ----------------------------------------
	public void sendMail(String rcpt, String subj, String mess, List<Attachment> attachments) throws Exception {

		Session session = PDHibernateFactory.getSession();
		// Get mail settings
		String hql = MessageFormat.format("FROM {0}", new Object[] { MMailSettings.class.getName() });
		Query query = session.createQuery(hql);
		MMailSettings mailSettings = (MMailSettings) query.uniqueResult();
		

		if (mailSettings == null) {
			throw new Exception("Mail settings do not exist");
		} else if (!mailSettings.isEnabled()) {
			return;
		}

		// Get properties from mail settings
		Properties settings = getPropertyFromBean(mailSettings);

		// Get mailSession
		javax.mail.Session mailSession;
		if (mailSettings.isAuth()) {
			Authenticator loAuthenticator = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(authUser, authPassword);
				}
			};
			mailSession = javax.mail.Session.getInstance(settings, loAuthenticator);
		} else {
			mailSession = javax.mail.Session.getInstance(settings);
		}

		mailSession.setDebug(mailSettings.isDebug());

		Message msg = new MimeMessage(mailSession);
		msg.setFrom(new InternetAddress(from));
		rcpt = rcpt.trim();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rcpt, false));
		msg.setSubject(subj);

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(mess, "text/plain;CHARSET=iso-8859-1");
		
		MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(bodyPart);

		if (attachments != null) {
			for (Attachment attachment : attachments) {
				DataSource source = new MyByteArrayDataSource(attachment.getLabel(), attachment.getData(), "application/octet-stream");  //"application/pdf";?
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(attachment.getLabel());
				multiPart.addBodyPart(mimeBodyPart);
			}
		}

		msg.setContent(multiPart); //, "text/plain;CHARSET=iso-8859-1");
		
		Transport.send(msg);
	}

	class MyByteArrayDataSource implements DataSource {
		byte[] bytes;
		String contentType;
		String name;

		MyByteArrayDataSource(String name, byte[] bytes, String contentType) {
			this.name = name;
			this.bytes = bytes;
			this.contentType = contentType;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() {
			return new ByteArrayInputStream(bytes);
		}

		public String getName() {
			return name;
		}

		public OutputStream getOutputStream() throws IOException {
			throw new FileNotFoundException();
		}
	}
}