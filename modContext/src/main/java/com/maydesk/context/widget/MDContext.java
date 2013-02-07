/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.context.widget;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.experimental.IInnerContainer;
import com.maydesk.base.model.MShortcut;
import com.maydesk.context.MContext;

public class MDContext extends Component implements IInnerContainer, IPlugTarget {

	public static final String PROPERTY_POSITION_X = "positionX";
	public static final String PROPERTY_POSITION_Y = "positionY";
	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";
	public static final String PROPERTY_ACTION_EVENT = "action";	
	public static final String PROPERTY_ACTION_MOUSE_UP = "mouseUp";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_ICON = "icon";

	protected MContext context;

	public MDContext() {		
	}

	public MDContext(MContext context) {
		this.context = context;
		init2();
	}
	
	private void init2() {
		set(PROPERTY_TITLE, context.getTitle());
		try {
			ResourceImageReference r = new ResourceImageReference(context.getIcon());
			set(PROPERTY_ICON, r);
		} catch (Exception e) {
			System.out.println("Icon for context " + context + " was invalid: " + context.getIcon());
		}
		set(PROPERTY_POSITION_X, new Extent(context.getPositionX()));
		set(PROPERTY_POSITION_Y, new Extent(context.getPositionY()));
		set(PROPERTY_WIDTH, new Extent(context.getWidth()));
		set(PROPERTY_HEIGHT, new Extent(context.getHeight()));
	}

	@Override
	public boolean isValidChild(Component component) {
		return true; // getComponentCount() == 0;
	}

	@Override
	public boolean isValidParent(Component parent) {
		return true; //parent instanceof ContentPane;
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_ACTION_MOUSE_UP.equals(inputName)) {
    		if (context.getPositionX() < 50 && context.getPositionY() < 55) {  //compare with PDShortcut.js line 167
    			setVisible(false);
    			if (context.getId() > 0) {
    				PDHibernateFactory.getSession().delete(context);
    			}
    		}
		} else if (PROPERTY_POSITION_X.equals(inputName)) {
    		context.setPositionX(((Extent) inputValue).getValue());
        } else if (PROPERTY_POSITION_Y.equals(inputName)) {
        	context.setPositionY(((Extent) inputValue).getValue());
		} else if (PROPERTY_WIDTH.equals(inputName)) {
    		context.setWidth(((Extent) inputValue).getValue());
		} else if (PROPERTY_HEIGHT.equals(inputName)) {
    		context.setHeight(((Extent) inputValue).getValue());
        } else if (PROPERTY_ACTION_EVENT.equals(inputName)) {
        	context.executeTask();
        }
    }

	@Override
    public boolean addShortcut(MShortcut mShortcut, boolean addToModels) {
		if (innerContainer == null) return false;
		if (mShortcut.getPositionX() < context.getPositionX()) return false;
		if (mShortcut.getPositionX() > context.getPositionX() + context.getWidth()) return false;
		if (mShortcut.getPositionY() < context.getPositionY()) return false;
		if (mShortcut.getPositionY() > context.getPositionY() + context.getHeight()) return false;
		mShortcut.setPositionX(mShortcut.getPositionX() - context.getPositionX());
		mShortcut.setPositionY(mShortcut.getPositionY() - context.getPositionY());
		return innerContainer.addShortcut(mShortcut, addToModels);
    }

	private IInnerContainer innerContainer;
	
	public void setContainer(IInnerContainer innerContainer) {
	    this.innerContainer = innerContainer;
	    add((Component)innerContainer);
    }

	@Override
	public void initWire(XmlBaseEntry parentWire) {
		this.context = new MContext();
		context.setPositionX(520);
		context.setPositionY(150);
		context.setWidth(350);
		context.setHeight(350);
		context.setIcon("img/ampel_bus.png");
		context.setTitle("My Context - click on me!");
		init2();
		
		MDCanvas canvas = new MDCanvas();
		//XXX work-around for early-loading
//		canvas.add(new MDText());
//		canvas.add(new MDRectangle());
//		canvas.add(new MDArrow());
//		canvas.add(new MDTable());
		MDAvatar avatar = new MDAvatar();
		avatar.setImage(new ResourceImageReference("img/silhouette-male.gif"));
		
		canvas.add(avatar);
//		canvas.add(new MDNewsBox());
//		canvas.add(new MDNewsTicker());
//		canvas.add(new MDImage());
		add(canvas);

		MDToolEntry tool = new MDToolEntry();
		tool.setIcon(new ResourceImageReference("img/rectangle16.png"));
		tool.setTool("MD.MDRectangle");
		canvas.add(tool);
		
		tool = new MDToolEntry();
		tool.setIcon(new ResourceImageReference("img/arrow16.png"));
		tool.setTool("MD.MDArrow");
		canvas.add(tool);

		tool = new MDToolEntry();
		tool.setIcon(new ResourceImageReference("img/table16.png"));
		tool.setTool("MD.MDTable");
		canvas.add(tool);

		tool = new MDToolEntry();
		tool.setIcon(new ResourceImageReference("img/text16.png"));
		tool.setTool("MD.MDText");
		canvas.add(tool);

	}
}
