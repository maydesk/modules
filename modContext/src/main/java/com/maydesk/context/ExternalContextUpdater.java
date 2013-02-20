/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.context;

import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDArrow;
import com.maydesk.context.widget.MDRectangle;
import com.maydesk.context.widget.MDText;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 16, 2013
 */
public class ExternalContextUpdater {

	private ExternalContextUpdater() {
		// Empty
	}

	public static void addFigure(final MDAbstractFigure figure) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDAbstractFigure externalFig = figure.clone();
				externalFig.setId(figure.getId());
				MDServletExternalContext.CANVAS.add(externalFig);
			}
		});
	}

	private static void updatePosAndSize(final MDAbstractFigure src, MDAbstractFigure dest) {
		dest.setPositionX(src.getPositionX());
		dest.setPositionY(src.getPositionY());
		dest.setWidth(src.getWidth());
		dest.setHeight(src.getHeight());
	}

	public static void updateRectProps(final MDRectangle rect) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDRectangle fig = (MDRectangle) MDServletExternalContext.CANVAS.getComponent(rect.getId());
				updatePosAndSize(rect, fig);
				fig.setBorder(rect.getBorder());
				fig.setBackground(rect.getBackground());
			}
		});
	}

	public static void updateArrowProps(final MDArrow arrow) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDArrow fig = (MDArrow) MDServletExternalContext.CANVAS.getComponent(arrow.getId());

				updatePosAndSize(arrow, fig);
				fig.setSize(arrow.getSize());
				
				fig.setStartPosX(arrow.getStartPosX());
				fig.setStartPosY(arrow.getStartPosY());
				
				fig.setEndPosX(arrow.getEndPosX());
				fig.setEndPosY(arrow.getEndPosY());
			}
		});
	}

	public static void updateTextProps(final MDText text) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDText fig = (MDText) MDServletExternalContext.CANVAS.getComponent(text.getId());

				updatePosAndSize(text, fig);
				fig.setSize(text.getSize());
				fig.setText(text.getText());
				fig.setType(text.getType());
			}
		});		
	}
}