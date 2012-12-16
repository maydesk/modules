/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.util.KeepAliveThread;
import com.maydesk.base.util.PDLookAndFeel;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * Basic Profidesk servlet, provides the key of the session
 * 
 */
public abstract class PDServlet extends WebContainerServlet {

	private static final String USER_INSTANCE_SESSION_KEY_PREFIX = "echoUserInstance"; //$NON-NLS-1$
	private static String SERVLET_NAME = null;
	public final static String HIBERNATE_FACTORY = "HIBERNATE_FACTORY"; //$NON-NLS-1$
	public final static String BROWSER_SESSION = "BROWSER_SESSION"; //$NON-NLS-1$
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (SERVLET_NAME == null) {
			SERVLET_NAME = getServletName();
		}
		
		if ("Echo.Sync".equals(request.getParameter(SERVICE_ID_PARAMETER))) { //$NON-NLS-1$
			
			HttpSession session = request.getSession();
			PDHibernateMasterFactory factory = new PDHibernateMasterFactory();
			session.setAttribute(HIBERNATE_FACTORY, factory);
			
			super.process(request, response);
			
			if (factory.hasOpenSession()) {
				factory.closeSession();
			}
			session.setAttribute(HIBERNATE_FACTORY, null);

		} else {
			super.process(request, response);
		}
	}

	public String getSessionKey() {
		return USER_INSTANCE_SESSION_KEY_PREFIX + ":" + SERVLET_NAME; //$NON-NLS-1$
	}
	
	private static boolean initialized = false;
	
	private void initialize() {
		KeepAliveThread.startThread();
	}
	
	@Override
	public ApplicationInstance newApplicationInstance() {

		if (!initialized) {
			initialize();
			initialized = true;
		}				

		return new PDApplicationInstance() {
			
			@Override
			public Window init() {
				return super.init();
			}

			@Override
			protected PDDesktop getDesktop() {
				Locale.setDefault(Locale.ENGLISH);
				ILookAndFeel lookAndFeel = new PDLookAndFeel() {
					public String getLogo() {
						return "img/CloudDeskLogo.png"; 
					}
					public Color getBackgroundClear() {
						return new Color(215, 220, 217);
					}
					
					public String getVersionInfo() {
						return "Demo";
					}
					@Override
                    public String getApplicationName() {
	                    return "CloudDesk";
                    }
				};

				PDDesktop desktop = new PDDesktop(getPerspective(), lookAndFeel);
				desktop.initDesktop();
				return desktop;
			}

			@Override
			protected String getTitle() {
				return "CloudDesk"; 
			}
		};
	}

	protected abstract PDMenuProvider getPerspective();

	protected abstract ILookAndFeel getLookAndFeel();

}
