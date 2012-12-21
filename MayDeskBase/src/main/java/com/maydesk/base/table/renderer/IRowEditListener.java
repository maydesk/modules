/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;

/**
 * @author Alejandro Salas
 */
public interface IRowEditListener extends EventListener {

	void btnEditClicked(ActionEvent event);

	void btnUpClicked(ActionEvent event);

	void btnDownClicked(ActionEvent event);

	void btnDeleteClicked(ActionEvent event);
}
