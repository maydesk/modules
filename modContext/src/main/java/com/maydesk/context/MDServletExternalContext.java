/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.context;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.ApplicationWebSocket;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.WebSocketConnectionHandler;

import com.maydesk.base.JettyWebSocket;
import com.maydesk.base.PDApplicationInstance;
import com.maydesk.context.widget.MDCanvas;
import com.maydesk.context.widget.MDContext;
import com.maydesk.context.widget.MDRectangle;
import com.maydesk.context.widget.WebcamReceiver;

/**
 * 
 * @author chrismay
 */
public class MDServletExternalContext extends WebContainerServlet {

	public static PDApplicationInstance TEST_APP_INSTANCE;
	public static MDRectangle RECTANGLE;
	public static Window window;
	public static String WEBCAM_URL;
	
	
	public static final WebSocketConnectionHandler wsHandler = new WebSocketConnectionHandler() {
		@Override
		public ApplicationWebSocket newApplicationWebSocket(ApplicationInstance applicationInstance) {
			return new JettyWebSocket(applicationInstance);
		}
	};
	
	public MDServletExternalContext() {
		setWebSocketConnectionHandler(wsHandler);
	}
	

	@Override
	public ApplicationInstance newApplicationInstance() {
		TEST_APP_INSTANCE = new PDApplicationInstance() {
			@Override
			public Window init() {
				window = new Window();
				window.setTitle("External Context");
				ContentPane pane = new ContentPane();
				window.setContent(pane);
				
				MDCanvas canvas = MDContext.TEST_SINGLETON_CANVAS = new MDCanvas();
				pane.add(canvas);

				MDRectangle rect = RECTANGLE = new MDRectangle();
				rect.setPositionX(300);
				rect.setPositionY(100);
				canvas.add(rect);

				WebcamReceiver webcam = new WebcamReceiver(TEST_APP_INSTANCE);
				webcam.setPositionX(10);
				webcam.setPositionY(50);
				canvas.add(webcam);

				return window;
			}
		};
		return TEST_APP_INSTANCE;
	}
}
