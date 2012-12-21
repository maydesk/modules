/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.base;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chrismay
 */
public class DownloadServlet extends HttpServlet {

	private static Hashtable<String, Document> documents = new Hashtable<String, Document>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String docId = request.getParameter("docId"); //$NON-NLS-1$
		if (docId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Document doc = getDocument(docId);
			if (doc == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				response.setHeader("Content-Disposition", "inline; filename=\"" + doc.getName() + "\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				response.setContentType(doc.getMimeType());
				response.setContentLength(doc.getContentLength());
				response.getOutputStream().write(doc.getContents());
			}

			// delete from cache after 1 minute
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					documents.remove(docId);
					// System.out.println("Doc " + docId +
					// " removed. Docs in cache: " + documents.size());
				}
			};
			t.start();
		}
	}

	public static void addDocument(Document doc) {
		documents.put(doc.getId(), doc);
	}

	private static Document getDocument(String id) {
		return documents.get(id);
	}

	public static class Document {

		private String id;
		private String name;
		private String mimeType;
		private int contentLength;
		private byte[] contents;

		public int getContentLength() {
			return contentLength;
		}

		public void setContentLength(int contentLength) {
			this.contentLength = contentLength;
		}

		public byte[] getContents() {
			return contents;
		}

		public void setContents(byte[] contents) {
			this.contents = contents;
		}

		public String getMimeType() {
			return mimeType;
		}

		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
}