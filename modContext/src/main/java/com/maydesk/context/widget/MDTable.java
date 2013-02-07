package com.maydesk.context.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MUser;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDTable extends MDAbstractFigure {

	public static final String PROPERTY_DATA = "data";
	private Map<String, Map<String, String>> dataMap;

	public MDTable() {
		initDBTable();
	}

	private void initDBTable() {
		List<MUser> list = PDHibernateFactory.getSession().createCriteria(MUser.class).list();
		Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();

		for (int i = 0; i < list.size(); i++) {
			MUser user = list.get(i);

			int count = 0;
			Map<String, String> userMap = new HashMap<String, String>();

			userMap.put(Integer.toString(count++), user.getIdAsString());
			userMap.put(Integer.toString(count++), user.getJabberId());

			dataMap.put(Integer.toString(i), userMap);
		}

		setDataMap(dataMap);
	}

	public void setDataMap(Map<String, Map<String, String>> dataMap) {
		Map<String, Map<String, String>> oldVal = this.dataMap;
		this.dataMap = dataMap;
		firePropertyChange(PROPERTY_DATA, oldVal, dataMap);
	}

	public Map<String, Map<String, String>> getDataMap() {
		return dataMap;
	}
}