/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.maydesk.base.internal.IDeleteMaster;
import com.maydesk.base.internal.PDTitleBar;
import com.maydesk.base.util.HeaderValue;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public abstract class PDPageableFactory {

	private Class modelClass;
	private IDeleteMaster parentForm;

	public PDPageableFactory(IDeleteMaster parentForm, Class modelClass) {
		this.modelClass = modelClass;
		this.parentForm = parentForm;
	}

	public Component getTableCellComponent(HeaderValue headerValues) {
		Label lbl = new Label(PDUtil.isEmpty(headerValues.title) ? "..." : headerValues.title);
		return lbl;
	}

	public PDTitleBar getHeaderComponent(HeaderValue headerValues) {
		PDTitleBar titleBar = new PDTitleBar(parentForm, headerValues, true);
		return titleBar;
	}

	public abstract Criteria getCriteria(Session session);

	public Projection getProjectionList() {
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.id(), "id");
		projectionList.add(Projections.property("cachedTitle"), "title"); // Model must be of type MBaseWithTitle!
		projectionList.add(Projections.property("cachedDescription"), "description"); // Model must be of type MBaseWithTitle!
		return Projections.distinct(projectionList);
	}

	// Override if applicable
	public HeaderValue createHeaderValue(Object[] data) {
		HeaderValue headerValues = new HeaderValue();
		headerValues.id = (Integer) (data)[0];
		headerValues.title = (String) (data)[1];
		headerValues.description = (String) (data)[2];
		return headerValues;
	}

	public Class getModelClass() {
		return modelClass;
	}

	public int getSourceId(HeaderValue headerValue) {
		return headerValue.id;
	}

	public int getRowsPerPage() {
		return 20;
	}

	public void addOrder(Criteria criteria) {
		criteria.addOrder(Order.asc("cachedTitle"));
	}
}
