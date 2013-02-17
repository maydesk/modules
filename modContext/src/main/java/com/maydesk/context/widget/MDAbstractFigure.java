/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.context.widget;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

/**
 * @author chrismay
 */
public abstract class MDAbstractFigure extends Component {

	public static final String PROPERTY_POSITION_X = "positionX";
	public static final String PROPERTY_POSITION_Y = "positionY";
	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";
	public static final String ACTION_MOVE = "move";
	public static final String ACTION_RESIZE = "async_resize";
	
	private static int id = 1;

	public static String getNextId() {
		return String.valueOf(id++);
	}
	
	public MDAbstractFigure() {
		this(0, 0);
	}

	public MDAbstractFigure(int posX, int posY) {
		set(PROPERTY_POSITION_X, new Extent(posX));
		set(PROPERTY_POSITION_Y, new Extent(posY));
		setWidth(new Extent(60));
		setHeight(new Extent(100));
	}

	public int getPositionX() {
		return (Integer) get(PROPERTY_POSITION_X);
	}

	public void setPositionX(int positionX) {
		set(PROPERTY_POSITION_X, positionX);
	}

	public int getPositionY() {
		return (Integer) get(PROPERTY_POSITION_Y);
	}

	public void setPositionY(int positionY) {
		set(PROPERTY_POSITION_Y, positionY);
	}

	public Extent getWidth() {
		return (Extent) get(PROPERTY_WIDTH);
	}

	public void setWidth(Extent width) {
		set(PROPERTY_WIDTH, width);
	}

	public Extent getHeight() {
		return (Extent) get(PROPERTY_HEIGHT);
	}

	public void setHeight(Extent height) {
		set(PROPERTY_HEIGHT, height);
	}

	@Override
	public MDAbstractFigure clone() {
		MDAbstractFigure clone = null;
		try {
			clone = getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clone.setPositionX(getPositionX());
		clone.setPositionY(getPositionY());
		clone.setWidth(getWidth());
		clone.setHeight(getHeight());
		
		return clone;
	}
	
	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_POSITION_X.equals(inputName)) {
			setPositionX(((Extent) inputValue).getValue());
		} else if (PROPERTY_POSITION_Y.equals(inputName)) {
			setPositionY(((Extent) inputValue).getValue());
		} else if (PROPERTY_WIDTH.equals(inputName)) {
			setWidth((Extent) inputValue);
		} else if (PROPERTY_HEIGHT.equals(inputName)) {
			setHeight((Extent) inputValue);
		} else if (ACTION_RESIZE.equals(inputName)) {
				// The event is just to invoke the server call.
		}
		
		//ExternalContextUpdater.updatePosAndSize(this);

//		// EXPERIMENTAL!
//		if (MDServletExternalContext.TEST_APP_INSTANCE != null) {
//			if (tqh == null) {
//				tqh = MDServletExternalContext.TEST_APP_INSTANCE.createTaskQueue();
//			}
//			MDServletExternalContext.TEST_APP_INSTANCE.enqueueTask(tqh, new Runnable() {
//				@Override
//				public void run() {
//					MDServletExternalContext.RECTANGLE.setPositionX(getPositionX());
//					MDServletExternalContext.RECTANGLE.setPositionY(getPositionY());
//				}
//			});
//		}
	}
}