package com.maydesk.base.util;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.table.AbstractTableModel;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.table.PDPageableFactory;

public class PDDataGridModel extends AbstractTableModel {

	private PDPageableFactory tableFactory;
	private List<HeaderValue> dataList = new Vector<HeaderValue>();	
	private int rowsPerPage;
	private int totalRows;
	
	public PDDataGridModel(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	public Class getColumnClass(int col) {
	    return Object.class;
    }

	public int getColumnCount() {
	    return 1;
    }

	public String getColumnName(int arg0) {
	    return null;
    }

	public int getRowCount() {
	    return dataList.size();
    }

	public int getTotalRowCount() {
	    return totalRows;
    }
	
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
	    Long longRows = (Long)criteria.uniqueResult();
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
	    		data = (Object[])o;
	    	} else {
	    		data = new Object[]{o};  //convert to array
	    	}
	    	dataList.add(tableFactory.createHeaderValue(data)); 
	    }
	}
}
