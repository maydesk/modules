/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


import org.hibernate.CallbackException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

import com.maydesk.base.PDHibernateFactory;


//@org.hibernate.annotations.Entity(
//        selectBeforeUpdate = true,
//dynamicInsert = true, dynamicUpdate = true)


//Could not synchronize database state with session
//org.hibernate.StaleStateException: Batch update returned unexpected
//row count from update [0]; actual row count: 0; expected: 1
//        at org.hibernate.jdbc.Expectations$BasicExpectation.check
//@SQLInsert(
//		  sql="INSERT INTO accounts (abalance, bid, filler, aid)VALUES (?, ?, ?, ?)", 
//		check=ResultCheckStyle.NONE)


/**
 * @author Alejandro Salas
 */
@MappedSuperclass
public abstract class MBase implements Lifecycle {

	protected int id;
	private boolean markForDelete = false; 

	public MBase() {
	}

	@Id
	//@GeneratedValue(generator="increment")
	//@GenericGenerator(name="increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean onSave(Session session) throws CallbackException {
		return false;
	}

	public boolean onUpdate(Session session) throws CallbackException {
		return false;
	}

	public boolean onDelete(Session session) throws CallbackException {
		return false;
	}

	public void onLoad(Session session, Serializable id) {
		// Empty
	}

	@Transient
	public String getIdAsString() {
		return Integer.toString(id);
	}

	public String truncateString(String string, int length) {
		if (string == null) {
			return null;
		}

		length = string.length() < length ? string.length() : length;
		return string.substring(0, length);
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MBase))
			return false;
		if (id == 0) {
			return super.equals(other);
		}
		return id == ((MBase) other).getId();
	}

	@Transient
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	@Deprecated
	public MBase reload() throws ObjectNotFoundException {
		if (id != 0) {
			Session session = PDHibernateFactory.getSession();
			return (MBase) session.load(getClass(), id);
		}
		return this;
	}

	public static MBase loadByDataLink(MDataLink dataLink)  {
		if (dataLink.getTargetClass() == null) return null;
        try {
    		Class clazz = Class.forName(dataLink.getTargetClass());
    		if (dataLink.getTargetId() == 0) {
    			return (MBase)clazz.newInstance();
    		}
			return loadByField(clazz, "id", dataLink.getTargetId() + "");
        } catch (Exception e) {
	        e.printStackTrace();
        }
        return null;
	}
	
	@Deprecated
	public static <T extends MBase> T loadById(Class<T> clazz, int id) {
		Session session = PDHibernateFactory.getSession();
		return (T)session.load(clazz, id);
	}

	@Deprecated
	public static <T extends MBase> T loadByField(Class<T> clazz, String fieldKey, String fieldVal) {
		Session session = PDHibernateFactory.getSession();
		String hql = "FROM {0} AS item";

		if (fieldKey != null) {
			hql += " WHERE {1}=:fieldVal";
		}

		hql = MessageFormat.format(hql, new Object[] { clazz.getName(), fieldKey });
		Query query = session.createQuery(hql);

		if (fieldKey != null) {
			query.setString("fieldVal", fieldVal);
		}
		return (T)query.uniqueResult();
	}

	@Deprecated
	public static List listByField(Class clazz, String fieldKey, String fieldVal, String orderBy) {
		Session session = PDHibernateFactory.getSession();
		String hql = "FROM {0} AS item";

		if (fieldKey != null) {
			hql += " WHERE {1}=:fieldVal";
		}

		if (orderBy != null) {
			hql += " ORDER BY ";
			hql += orderBy;
		}

		hql = MessageFormat.format(hql, new Object[] { clazz.getName(), fieldKey });
		Query query = session.createQuery(hql);

		if (fieldKey != null) {
			query.setString("fieldVal", fieldVal);
		}
		return query.list();
	}

	@Deprecated
	public static List listByField(Class clazz) {
		return MBase.listByField(clazz, null, null, null);
	}

	@Transient
	public boolean isDeleteable() {
		return true;
	}

	@Transient
	public String getUniqueId() {
		return getClass().getSimpleName() + "@" + id;
	}
}