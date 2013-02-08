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
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;

import com.maydesk.context.widget.MDAvatar;
import com.maydesk.context.widget.MDCanvas;
import com.maydesk.context.widget.MDToolEntry;

/**
 * 
 * @author chrismay
 */
public class MDServletExternalContext extends WebContainerServlet {

	@Override
	public ApplicationInstance newApplicationInstance() {
		return new ApplicationInstance() {

			@Override
			public Window init() {
				Window w = new Window();
				w.setTitle("External Context");
				ContentPane pane = new ContentPane();
				w.setContent(pane);
				
				
				//pane.add(new Label("HELOO WORLD!"));
				MDCanvas canvas = new MDCanvas();
				pane.add(canvas);

				MDAvatar avatar = new MDAvatar();
				avatar.setImage(new ResourceImageReference("img/silhouette-male.gif"));
				avatar.setText("Hello World!");
				avatar.setPositionX(200);
				avatar.setPositionY(100);
				canvas.add(avatar);
				
				return w;
			}
		};
	}
}
