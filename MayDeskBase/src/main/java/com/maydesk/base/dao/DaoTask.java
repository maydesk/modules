/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.FrmTasks.TASK_FILTER;
import com.maydesk.base.model.MTask;
import com.maydesk.base.model.MTaskAssign;
import com.maydesk.base.model.MUser;

/**
 * The DAO to find/load/save MUser instances
 * 
 * @author chrismay
 */
public class DaoTask implements IDAO {

	public static void assignUser(Session session, MTask task, MUser user) {
		// assign the task to the project admin(s)
		MTaskAssign ut = new MTaskAssign();
		ut.setUserRef(user);
		ut.setTask(task);
		session.save(ut);
	}

	public static List<MTaskAssign> findTasksByUser(MUser user) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTaskAssign.class);
		criteria.add(Restrictions.eq("userRef", user));
		// criteria.setProjection(Projections.property("task"));
		List<MTaskAssign> list = criteria.list();
		return list;
	}

	public static List<MUser> findUsersByTask(MTask task) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTaskAssign.class);
		criteria.add(Restrictions.eq("task", task));
		criteria.setProjection(Projections.property("userRef"));
		List<MUser> list = criteria.list();
		return list;
	}

	public static List<MTask> findTasksByIssue(int modelId) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTask.class);
		criteria.add(Restrictions.eq("modelId", modelId));
		List<MTask> list = criteria.list();
		return list;
	}

	public static List<MTask> findTasks(TASK_FILTER filter) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTask.class);
		switch (filter) {
		case OPEN:
			criteria.add(Restrictions.isNull("doneDate"));
			break;
		case UNASSIGNED:
			criteria.add(Restrictions.isNull("doneDate"));
			DetachedCriteria userTasks = DetachedCriteria.forClass(MTaskAssign.class);
			userTasks.createAlias("task", "t2");
			userTasks.setProjection(Projections.property("t2.id"));
			criteria.add(Subqueries.propertyNotIn("id", userTasks));
			break;
		case ASSIGNED:
			criteria.add(Restrictions.isNull("doneDate"));
			userTasks = DetachedCriteria.forClass(MTaskAssign.class);
			userTasks.createAlias("task", "t2");
			userTasks.setProjection(Projections.property("t2.id"));
			criteria.add(Subqueries.propertyIn("id", userTasks));
			break;
		case DONE:
			criteria.add(Restrictions.isNotNull("doneDate"));
			break;
		}

		// //limit to reachable tasks
		// DetachedCriteria assignments =
		// DetachedCriteria.forClass(MTenantAssignment.class, "ta");
		// assignments.add(Restrictions.eq("user", PDDesktop.getCurrentUser()));
		// assignments.add(Restrictions.eq("owner", true));
		// assignments.setProjection(Projections.property("ta.tenant"));
		// criteria.add(Subqueries.propertyIn("tenant", assignments));

		criteria.addOrder(Order.asc("id"));
		List<MTask> list = criteria.list();
		return list;
	}

	public static boolean hasUnassignedTasks() {
		Session session = PDHibernateFactory.getSession();
		String hql = "select count(task) from MTask task where task not in (select task from MUserTask) "
				+ "and task.tenant in (select tenant from MTenantAssignment pa where pa.userRef = :user and owner = true) " + "and doneDate is null";
		Query query = session.createQuery(hql);
		query.setEntity("user", PDUserSession.getInstance().getUser());
		Long i = (Long) query.uniqueResult();
		return i > 0;
	}

	public static List<MTaskAssign> findUserTasks(MTask task) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTaskAssign.class);
		criteria.add(Restrictions.eq("task", task));
		return criteria.list();
	}
}
