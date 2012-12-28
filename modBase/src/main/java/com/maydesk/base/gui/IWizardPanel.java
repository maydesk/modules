/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Component;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.ICrud;

/**
 * @author chrismay
 */
public interface IWizardPanel extends ICrud {

	public String getNextCaption();

	public String getBackCaption();

	public boolean doBackAction();

	public void applyToModel2();

	public Translatable getError();

	public boolean isApplicable();

	public int getNextButtonWidth();

	public int getBackButtonWidth();

	public void readFromModel();

	public boolean doNextAction();

	public void applyToModel();

	public Component getComponent();
}
