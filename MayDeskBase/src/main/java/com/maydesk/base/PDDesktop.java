/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import java.util.HashMap;
import java.util.List;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.MenuBarPane;
import nextapp.echo.extras.app.menu.MenuModel;
import nextapp.echo.webcontainer.ContainerContext;

import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.internal.PnlLogin;
import com.maydesk.base.model.MAvatar;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MWire;
import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDAvatar;
import com.maydesk.base.widgets.PDButton;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDShortcut;
import com.maydesk.base.widgets.PDStatusLabel;

import echopoint.ContainerEx;
import echopoint.Strut;
import echopoint.able.Positionable;

/**
 * This class is the centerpiece of the PDWorkspace framework; it provides the
 * desktop where the menu, windows, tasks etc. are displayed
 * 
 */
public final class PDDesktop extends ContentPane {

	private static final String DESKTOP_INSTANCE = "DESKTOP_INSTANCE"; //$NON-NLS-1$
	
	public static PDDesktop getInstance() {
		ContainerContext context = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		if (context == null) return null;
		PDDesktop desktop = (PDDesktop)context.getSession().getAttribute(DESKTOP_INSTANCE);
		return desktop;
	}

	private Row rowFooterLeft;
	private Row rowFooterRight;
	private Row rowTopRight;

	private ContainerEx contentPane;
	private SplitPane splitFooterLeftRight;
	private SplitPane splitFooterMain;
	private MenuBarPane menuBar;
	private PDMenuProvider menuProvider;
	private PDButton btnReload;
	private ILookAndFeel lookAndFeel;
	private PDStatusLabel lblStatus;
	private ContainerEx rowMenu;
	private MWire desktopWire;
	private MWire footerLeftWire;
	private MWire footerRightWire;
	private MWire topRightWire;
	private SplitPane splitHeaderMain;
	private PDLabel	lblUser;
	
	
	public Row getRowTopRight() {
    	return rowTopRight;
    }

	public ContainerEx getRowMenu() {
    	return rowMenu;
    }

	public PDDesktop(PDMenuProvider menuProvider, ILookAndFeel lookAndFeel) {
		this.menuProvider = menuProvider;
		this.lookAndFeel = lookAndFeel;
		ContainerContext context = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		context.getSession().setAttribute(DESKTOP_INSTANCE, this);
		PDUserSession.getInstance().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userChanged();
            }			
		});
	}

	public void initDesktop() {
		
		String img = "img/CloudDeskBackground.jpg";
		setBackgroundImage(new FillImage(new ResourceImageReference(img)));
		
		try {
			splitFooterMain = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
			splitFooterMain.setSeparatorPosition(new Extent(25));
			super.add(splitFooterMain);
			
			splitFooterLeftRight = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
			splitFooterLeftRight.setSeparatorPosition(new Extent(220));
			SplitPaneLayoutData spld = new SplitPaneLayoutData();
			spld.setBackgroundImage(new FillImage(PDUtil.getImg("img/semitrans6.png")));
			splitFooterLeftRight.setLayoutData(spld);
			splitFooterMain.add(splitFooterLeftRight);

			rowFooterLeft = new Row();
			rowFooterRight = new Row();
			splitFooterLeftRight.add(rowFooterRight);
			splitFooterLeftRight.add(rowFooterLeft);
			
			btnReload = new PDButton("Reload", PDButton.STYLE.TRANSPARENT);
			btnReload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					PDApplicationInstance appInst = (PDApplicationInstance)ApplicationInstance.getActive();
					appInst.reset();
				}
			});
			rowFooterRight.add(btnReload);			
			
			lblUser = new PDLabel(PDLabel.STYLE.FIELD_VALUE);
			lblUser.getLabel().setForeground(Color.LIGHTGRAY);
			rowFooterLeft.add(lblUser);
			
			//**********************************************************************************
			splitHeaderMain = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
			splitHeaderMain.setSeparatorPosition(new Extent(25));
			splitFooterMain.add(splitHeaderMain);

			SplitPane splitHeader = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
			splitHeader.setSeparatorPosition(new Extent(380));
			spld = new SplitPaneLayoutData();
			spld.setBackgroundImage(new FillImage(PDUtil.getImg("img/semitrans6.png")));
			splitHeader.setLayoutData(spld);			
			splitHeaderMain.add(splitHeader);
			
			rowTopRight = new Row();
			rowTopRight.setCellSpacing(new Extent(12));
			spld.setInsets(new Insets(5, 2, 0, 0));
			rowTopRight.setLayoutData(spld);
			splitHeader.add(rowTopRight);
			
//			lblStatus = new PDStatusLabel();
//			lblStatus.setText(nls(PDBeanTerms.Change));
//			lblStatus.setFormatWhitespace(true);
//			lblStatus.setForeground(Color.WHITE);
//			rowTopRight.add(lblStatus);

			contentPane = new ContainerEx() {
			    public boolean isValidChild(Component child) {
			    	return true;			    
			    }
			};
			
			contentPane.setHeight(new Extent(100, Extent.PERCENT));
			splitHeaderMain.add(contentPane);

			rowMenu = new ContainerEx();
			rowMenu.setLayoutStyle(ContainerEx.ROW_LAYOUT);
			//rowMenu.setBackground(Color.ORANGE);
			//rowMenu.setLeft(new Extent(6));
			rowMenu.setTop(new Extent(3));
			splitHeader.add(rowMenu);
			rowMenu.add(new Strut(6, 0));
			
			MenuModel menuModel = menuProvider.updateMenu();
			if (menuModel != null) {
				menuBar = new MenuBarPane();
				menuBar.setAnimationTime(600);
				menuBar.addActionListener(menuProvider);
				menuBar.setModel(menuModel);
				menuBar.setBackground(lookAndFeel.getBackgroundDark());
				menuBar.setBorder(PDUtil.emptyBorder());
				rowMenu.add(menuBar);
			}
						
			initBasePlugs();
			
			userChanged();
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}
	
	private void initBasePlugs() {
		List<MWire> wires = PDUtil.findWires(null);
		for (MWire wire : wires) {
			if ("desktop".equals(wire.getPlug().getName())) {
				desktopWire = wire;
			} else if ("footerLeft".equals(wire.getPlug().getName())) {
				footerLeftWire = wire;
			} else if ("footerRight".equals(wire.getPlug().getName())) {
				footerRightWire = wire;
			} else if ("topRight".equals(wire.getPlug().getName())) {
				topRightWire = wire;
			}			
		}
	}
	
	public void userChanged() {
		contentPane.removeAll();
		
		if (PDUserSession.getInstance().isLoggedIn()) {
			splitHeaderMain.setSeparatorPosition(new Extent(25));
			splitFooterMain.setSeparatorPosition(new Extent(25));
			
			PDBasePlugLoader.loadItems(contentPane, desktopWire);
			PDBasePlugLoader.loadItems(rowFooterLeft, footerLeftWire);
			PDBasePlugLoader.loadItems(rowFooterRight, footerRightWire);
			PDBasePlugLoader.loadItems(rowTopRight, topRightWire);
			//rowFooterRight.add(btnReload);	
			
			loadShortcuts();			
			refreshTaskDisplay(false);
			menuBar.setModel(menuProvider.updateMenu());
			menuProvider.onInit();
			lblUser.setText("Your are logged in as " + PDUserSession.getInstance().getUser().getJabberId());
		} else {
			//show initial screen
			splitHeaderMain.setSeparatorPosition(new Extent(0));
			splitFooterMain.setSeparatorPosition(new Extent(0));
			//show register/login panel
			PnlLogin pnlLogin = new PnlLogin();
			pnlLogin.setLeft(new Extent(50));
			pnlLogin.setBottom(new Extent(90));
			contentPane.add(pnlLogin);			
			pnlLogin.initialize();
		}
		
		Label  lbl = new Label(new ResourceImageReference("img/CloudDeskLogo.png"));
		ContainerEx c2 = new ContainerEx();
		c2.setBottom(new Extent(30));
		c2.setRight(new Extent(50));
		c2.setPosition(Positionable.ABSOLUTE);
		c2.add(lbl);
		contentPane.add(c2);
	}
	
	private void loadShortcuts() {
		List<MShortcut> shortcuts = DaoUser.findShortcuts(PDUserSession.getInstance().getUser());
		for (MShortcut shortcut : shortcuts) {
			addShortcut(shortcut);
		}
		List<MAvatar> avatars = DaoUser.findAvatars(PDUserSession.getInstance().getUser());
		for (MAvatar avatar : avatars) {
			addPerson(avatar);
		}
	}
	
	private HashMap<String, PDAvatar> allAvatars = new HashMap<String, PDAvatar>();

	public void addPerson(final MAvatar mAvatar) {
		final PDAvatar avatar = new PDAvatar(mAvatar);
		allAvatars.put(avatar.getModel().getPerson().getJabberId(), avatar);		
		PDUserSession.getInstance().addToRoster(mAvatar.getPerson());		
		contentPane.add(avatar.getMenuedComponent());
	}

	public void addShortcut(final MShortcut mShortcut) {
		final PDShortcut shortcut = new PDShortcut(mShortcut);
		contentPane.add(shortcut);
	}

	public void addWindow(final Component c) {
		if (c instanceof WindowPane) {
			add(c);
		} else {
			contentPane.add(c);
		}
	}

	public ContainerEx getContentPane() {
	    return contentPane;
    }

	public MenuBarPane getMenuBar() {
	    return menuBar;
    }
	
	public void showSaving() {
		//lblStatus.setLineWrap(!lblStatus.isLineWrap());	    
    }
	
	public void refreshTaskDisplay(boolean b) {
    }

	public PDAvatar findAvatarByJabberId(String jabberId) {
	    return allAvatars.get(jabberId);
    }	
}