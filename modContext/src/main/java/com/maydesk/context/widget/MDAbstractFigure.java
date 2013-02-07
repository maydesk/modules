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
}
