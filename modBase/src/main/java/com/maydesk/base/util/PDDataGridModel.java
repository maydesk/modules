/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.table.AbstractTableModel;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.table.PDPageableFactory;

/**
 * @author chrismay
 */
public class PDDataGridModel extends AbstractTableModel {

	private PDPageableFactory tableFactory;
	private List<HeaderValue> dataList = new Vector<HeaderValue>();
	private int rowsPerPage;
	private int totalRows;

	public PDDataGridModel(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	@Override
	public Class getColumnClass(int col) {
		return Object.class;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int arg0) {
		return null;
	}

	@Override
	public int getRowCount() {
		return dataList.size();
	}

	public int getTotalRowCount() {
		return totalRows;
	}

	@Override
	public Object getValueAt(int col, int row) {
		return dataList.get(row);
	}

	public void setFactory(PDPageableFactory tableFactory) {
		this.tableFactory = tableFactory;
	}

	public void addItem(HeaderValue headerValue) {
		dataList.add(0, headerValue);
		totalRows++;
	}

	public void reloadData(int position) {
		dataList.clear();

		Criteria criteria = tableFactory.getCriteria(PDHibernateFactory.getSession());
		criteria.setProjection(Projections.countDistinct("id"));
		Long longRows = (Long) criteria.uniqueResult();
		totalRows = longRows.intValue();

		Projection projection = tableFactory.getProjectionList();
		if (projection == null) {
			criteria = tableFactory.getCriteria(PDHibernateFactory.getSession());
		} else {
			criteria.setProjection(projection);
		}
		criteria.setMaxResults(rowsPerPage);
		criteria.setFirstResult(position);
		tableFactory.addOrder(criteria);

		List list = criteria.list();
		for (Object o : list) {
			Object[] data = null;
			if (o instanceof Object[]) {
				data = (Object[]) o;
			} else {
				data = new Object[] { o }; // convert to array
			}
			dataList.add(tableFactory.createHeaderValue(data));
		}
	}
	
	@Override
	public int getTotalColumnCount() {
		return getColumnCount();
	}

	@Override
	public boolean isColumnVisible(int arg0) {
		return true;
	}
}
