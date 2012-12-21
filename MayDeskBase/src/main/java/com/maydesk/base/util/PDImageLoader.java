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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demián Gutierrez
 */
public class PDImageLoader {

	private static PDImageLoader instance;

	private Map imageMap = new HashMap();

	private PDImageLoader() {
		// Empty
	}

	public static PDImageLoader getInstance() {
		if (instance == null) {
			instance = new PDImageLoader();
		}

		return instance;
	}

	public InputStream getAsInputStream(String name) throws IOException {
		return new ByteArrayInputStream(getAsByteArray(name));
	}

	public URL getAsURL(String name) throws IOException {
		return getClass().getResource(name);
	}

	public byte[] getAsByteArray(String name) throws IOException {
		byte[] image = (byte[]) imageMap.get(name);

		if (image == null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ClassLoader cl = getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream(name);
			byte[] buffer = new byte[512];

			int count;
			while ((count = is.read(buffer)) > 0) {
				baos.write(buffer, 0, count);
			}

			image = baos.toByteArray();
			imageMap.put(name, image);
		}

		return image;
	}
}