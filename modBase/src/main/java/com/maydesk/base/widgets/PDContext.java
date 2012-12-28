/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.experimental.IInnerContainer;
import com.maydesk.base.model.MContext;
import com.maydesk.base.model.MShortcut;

/**
 * @author chrismay
 */
public class PDContext extends Component implements IInnerContainer {

	public static final String PROPERTY_POSITION_X = "positionX";
	public static final String PROPERTY_POSITION_Y = "positionY";
	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";
	public static final String PROPERTY_ACTION_EVENT = "action";
	public static final String PROPERTY_ACTION_MOUSE_UP = "mouseUp";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_ICON = "icon";

	protected MContext context;

	public PDContext() {
	}

	public PDContext(MContext context) {
		this.context = context;
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
		return getComponentCount() == 0;
	}

	@Override
	public boolean isValidParent(Component parent) {
		return true; // parent instanceof ContentPane;
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_ACTION_MOUSE_UP.equals(inputName)) {
			if (context.getPositionX() < 50 && context.getPositionY() < 55) { // compare with PDShortcut.js line 167
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
		if (innerContainer == null)
			return false;
		if (mShortcut.getPositionX() < context.getPositionX())
			return false;
		if (mShortcut.getPositionX() > context.getPositionX() + context.getWidth())
			return false;
		if (mShortcut.getPositionY() < context.getPositionY())
			return false;
		if (mShortcut.getPositionY() > context.getPositionY() + context.getHeight())
			return false;
		mShortcut.setPositionX(mShortcut.getPositionX() - context.getPositionX());
		mShortcut.setPositionY(mShortcut.getPositionY() - context.getPositionY());
		return innerContainer.addShortcut(mShortcut, addToModels);
	}

	private IInnerContainer innerContainer;

	public void setContainer(IInnerContainer innerContainer) {
		this.innerContainer = innerContainer;
		add((Component) innerContainer);
	}
}