/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.templates;

import nextapp.echo.app.Component;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.logical.SopUser;
import com.maydesk.base.util.ICrud;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author chrismay
 */
public class PnlTemplate extends PDGrid implements ICrud<MUser> {

	private PDBinding binding;

	@SuppressWarnings("unchecked")
	public PnlTemplate(PDBinding binding) {
		super(2);
		this.binding = binding;
		initGUI();
	}

	protected void initGUI() {
		addLabel(SopUser.firstName);
		add(binding.register(new PDTextField(), SopUser.firstName));

		addLabel(SopUser.lastName);
		add(binding.register(new PDTextField(), SopUser.lastName));
	}

	@Override
	public void readFromModel(MBase model) {
		binding.read(model);
	}

	@Override
	public Component getFocusComponent() {
		return null;
	}

	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return null;
	}
}