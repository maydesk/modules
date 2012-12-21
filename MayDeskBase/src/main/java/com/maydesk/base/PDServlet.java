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
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;

import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.util.KeepAliveThread;
import com.maydesk.base.util.PDLookAndFeel;

/**
 * Basic Profidesk servlet, provides the key of the session
 * 
 * @author chrismay
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
					@Override
					public String getLogo() {
						return "img/CloudDeskLogo.png";
					}

					@Override
					public Color getBackgroundClear() {
						return new Color(215, 220, 217);
					}

					@Override
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
