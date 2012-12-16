/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.util.ByteArrayImageReference;

import echopoint.ImageIcon;

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
