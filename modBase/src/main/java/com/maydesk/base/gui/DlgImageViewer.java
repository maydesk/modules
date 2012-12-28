/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.util.ByteArrayImageReference;

import echopoint.ImageIcon;

/**
 * @author Alejandro Salas
 */
public class DlgImageViewer extends PDSimpleDialog {

	private MMediaFile mediaFile;

	public DlgImageViewer(MMediaFile mediaFile) {
		super(nls(PDBeanTerms.Image_Viewer), 700, 700);
		this.mediaFile = mediaFile;
		initGUI2();
	}

	private void initGUI2() {
		ImageIcon lbl = new ImageIcon();
		try {
			lbl.setIcon(new ByteArrayImageReference(mediaFile.getFileBytes(), mediaFile.getContentType(), 48));
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMainComponent(lbl);
	}
}
