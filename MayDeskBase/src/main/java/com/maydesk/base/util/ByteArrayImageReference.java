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
import java.io.IOException;
import java.io.OutputStream;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Extent;
import nextapp.echo.app.StreamImageReference;

import org.apache.commons.io.IOUtils;

/**
 * @author Alejandro Salas
 */
public class ByteArrayImageReference extends StreamImageReference {

	private byte[] data;
	private String contentType;
	private String id = ApplicationInstance.generateSystemId();
	private int size;

	public ByteArrayImageReference(byte[] data, String contentType, int size) {
		this.data = data;
		this.contentType = contentType;
		this.size = size;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void render(OutputStream out) throws IOException {
		try {
			IOUtils.copy(new ByteArrayInputStream(data), out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getRenderId() {
		return id;
	}

	@Override
	public Extent getHeight() {
		return new Extent(size);
	}

	@Override
	public Extent getWidth() {
		return new Extent(size);
	}
}
