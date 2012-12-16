package com.maydesk.base.sop.enums;

import com.maydesk.base.util.IImage;

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

public enum EImage16 implements IImage {
	
	person("img/person.png"), 
	
	bulletlist("img/bulletlist16.gif"), 
	
	edite("/img/edite.gif"),
	
	editd("/img/editd.gif"), 
	
	upe("/img/upe.gif"), 
	
	upd("/img/upd.gif"),
	
	dwe("/img/dwe.gif"), 
	
	dwd("/img/dwd.gif"), 
	
	deletee("/img/deletee.gif"), 
	
	deleted("/img/deleted.gif"), 
	
	add("img/add.gif"), 
	
	compare4("img/compare_4.gif"), 
	
	circle2("img/circle2.jpg"), 
	
	recycle_bin("img/recycle_bin.gif"), 
	
	undo("img/undo.gif"), 	
	
	generado("img/generado.gif"), 
	
	confirm("img/confirm16.gif"), 
	
	orangearrow("img/orangearrow.png"), 
	
	textfield_bg("img/textfield_bg.gif"), 
	
	first_white("img/first_white.gif"), 
	
	first_grey("img/first_grey.gif"),
	
	previous_white("img/previous_white.gif"),
	
	previous_grey("img/previous_grey.gif"), 
	
	next_white("img/next_white.gif"), 
	
	next_grey("img/next_grey.gif"), 
	
	last_white("img/last_white.gif"),
	
	last_grey("img/last_grey.gif"), 
	
	spin_up("img/spin_up.gif"),
	
	spin_down("img/spin_down.gif"),

	comment2empty("img/comment2empty.gif"),	

	comment2("img/comment2.gif"),
	
	borderTopLeft("img/border/BorderTopLeft.png"),
	
	borderTop("img/border/BorderTop.png"),
	
	borderTopRight("img/border/BorderTopRight.png"),
	
	borderLeft("img/border/BorderLeft.png"),
	
	borderRight("img/border/BorderRight.png"),
	
	borderBottomLeft("img/border/BorderBottomLeft.png"),
	
	borderBottom("img/border/BorderBottom.png"),
	
	borderBottomRight("img/border/BorderBottomRight.png"),

	restart("img/restart.gif"),
	
	chat("img/chat.png"),
	
	startHere("tango16/places/start-here.png"),
	
	arrowLeft("img/arrowleft.gif"),
	
	login("img/login.gif"),
	
	footerBackground("img/footerBackground.png"),
	
	profideskFooter2("img/profidesk_footer2.png"), 

	shortcut1("img/shortcut1.gif"),

	/**
	 * bla bla
	 */
	options("img/options.gif"),

	semiTrans4("img/semitrans4.png"),

	semiTrans5("img/semitrans5.png"),

	save("/img/save.gif"), 

	cancel("/img/undo.gif"), 

	print("/img/print.gif"), 

	xls("img/xls.gif");
	
	
	private String imagePath;
	
	EImage16(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public ImageReference getImage() {
		ImageReference img = new ResourceImageReference("" + imagePath);
		return img;
	}

}
