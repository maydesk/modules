/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.context.widget;

import java.util.Random;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.TaskQueueHandle;

import com.maydesk.context.MDServletExternalContext;

/**
 * @author chrismay
 */
public abstract class MDAbstractFigure extends Component {

	public static final String PROPERTY_POSITION_X = "positionX";
	public static final String PROPERTY_POSITION_Y = "positionY";
	public static final String ACTION_MOVE = "move";

	public MDAbstractFigure() {		
	}

	public MDAbstractFigure(int posX, int posY) {
		set(PROPERTY_POSITION_X, new Extent(posX));
		set(PROPERTY_POSITION_Y, new Extent(posY));
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
	
	static TaskQueueHandle tqh;
	
	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_POSITION_X.equals(inputName)) {
			setPositionX(((Extent) inputValue).getValue());
		} else if (PROPERTY_POSITION_Y.equals(inputName)) {
			setPositionY(((Extent) inputValue).getValue());
		}

		//EXPERIMENTAL!
		if (MDServletExternalContext.TEST_APP_INSTANCE != null) {
			if (tqh == null) {
				tqh = MDServletExternalContext.TEST_APP_INSTANCE.createTaskQueue();
			}
			MDServletExternalContext.TEST_APP_INSTANCE.enqueueTask(tqh, new Runnable() {			
				@Override
				public void run() {
					MDServletExternalContext.RECTANGLE.setPositionX(getPositionX());
					MDServletExternalContext.RECTANGLE.setPositionY(getPositionY());
				}
			});
		}
	}
}
