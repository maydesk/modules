/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;



/**
 * @author chrismay
 */
public class MDMailUtil {


	public static void sendMail(String rcpt, String subj, String mess, List<Attachment> attachments) throws Exception {

		Context initCtx = new InitialContext();
        Session mailSession = (Session) initCtx.lookup("java:comp/env/mail/SendGrid");

		Message msg = new MimeMessage(mailSession);
		msg.setFrom(new InternetAddress("mail@chrismay.de"));  //TODO make configurable
		rcpt = rcpt.trim();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rcpt, false));
		msg.setSubject(subj);

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(mess, "text/plain;CHARSET=iso-8859-1");

		MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(bodyPart);

		if (attachments != null) {
			for (Attachment attachment : attachments) {
				DataSource source = new MyByteArrayDataSource(attachment.getLabel(), attachment.getData(), "application/octet-stream"); // "application/pdf";?
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setDataHandler(new DataHandler(source));
				mimeBodyPart.setFileName(attachment.getLabel());
				multiPart.addBodyPart(mimeBodyPart);
			}
		}

		msg.setContent(multiPart); // , "text/plain;CHARSET=iso-8859-1");

		Transport.send(msg);
	}

	static class MyByteArrayDataSource implements DataSource {
		byte[] bytes;
		String contentType;
		String name;

		MyByteArrayDataSource(String name, byte[] bytes, String contentType) {
			this.name = name;
			this.bytes = bytes;
			this.contentType = contentType;
		}

		@Override
		public String getContentType() {
			return contentType;
		}

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(bytes);
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			throw new FileNotFoundException();
		}
	}
}