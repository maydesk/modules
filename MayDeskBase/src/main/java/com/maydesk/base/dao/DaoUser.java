/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jivesoftware.smack.packet.Presence;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MActivity;
import com.maydesk.base.model.MAvatar;
import com.maydesk.base.model.MMnemonic;
import com.maydesk.base.model.MPresence;
import com.maydesk.base.model.MPresenceAcknowledge;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MTask;
import com.maydesk.base.model.MTaskAssign;
import com.maydesk.base.model.MTenant;
import com.maydesk.base.model.MTenantAssignment;
import com.maydesk.base.model.MUser;

/**
 * The DAO to find/load/save MUser instances
 * 
 * @author chrismay
 */
public class DaoUser implements IDAO {

	public static MUser findUserById(int userId) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		criteria.add(Restrictions.idEq(userId));
		return (MUser) criteria.uniqueResult();
	}

	public static MMnemonic findMnemonic(int serialId) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MMnemonic.class);
		criteria.add(Restrictions.eq("serialId", serialId));
		criteria.add(Restrictions.eq("userRef", PDUserSession.getInstance().getUser()));
		return (MMnemonic) criteria.uniqueResult();
	}

	public static List<MTask> findTask(MUser user) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTaskAssign.class);
		criteria.add(Restrictions.eq("userRef", user));
		criteria.createAlias("task", "t");
		criteria.add(Restrictions.isNull("t.doneDate"));
		criteria.setProjection(Projections.distinct(Projections.property("task")));
		return criteria.list();
	}

	public static List<MUser> findUsersByProject(MTenant tenant) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MTenantAssignment.class);
		criteria.add(Restrictions.eq("tenant", tenant));
		criteria.setProjection(Projections.property("user"));
		return criteria.list();
	}

	public static List<MTenant> findReachableTenants() {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MTenantAssignment.class);
		criteria.add(Restrictions.eq("user", PDUserSession.getInstance().getUser()));
		criteria.add(Restrictions.eq("owner", true));
		criteria.setProjection(Projections.property("tenant"));
		return criteria.list();
	}

	public static void assignTask(MTask task, MTenant project, String authorizationName) {
		// find the project admin(s) for the project
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MTenantAssignment.class);
		criteria.add(Restrictions.eq("tenant", project));
		criteria.add(Restrictions.eq("assignmentActive", true));
		criteria.add(Restrictions.eq(authorizationName, true));
		List<MTenantAssignment> list = criteria.list();
		for (MTenantAssignment pa2 : list) {
			DaoTask.assignUser(session, task, pa2.getUserRef());
		}
	}

	public static MUser findUserByJabberId(String jabberId) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		criteria.add(Restrictions.eq("jabberId", jabberId));
		return (MUser) criteria.uniqueResult();
	}

	public static List<MShortcut> findShortcuts(MUser user) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MShortcut.class);
		criteria.add(Restrictions.eq("owner", user));
		return criteria.list();
	}

	public static List<MAvatar> findAvatars(MUser owner) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MAvatar.class);
		criteria.add(Restrictions.eq("owner", owner));
		return criteria.list();
	}

	public static List<MUser> findAllUsers() {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		criteria.addOrder(Order.asc("jabberId"));
		// criteria.addOrder(Order.asc("firstName"));
		return criteria.list();
	}

	public static List<MUser> findUsersByEmail(String email) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		criteria.add(Restrictions.eq("email", email.trim()));
		criteria.add(Restrictions.isNotNull("jabberId"));
		return criteria.list();
	}

	public static List<MActivity> findActivities(MUser user) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MActivity.class);
		criteria.add(Restrictions.eq("userId", user.getId()));
		criteria.addOrder(Order.desc("date"));
		criteria.setMaxResults(100);
		return criteria.list();
	}

	public static MUser findUserByEmail(String email) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUser.class);
		criteria.add(Restrictions.eq("email", email.trim()));
		return (MUser) criteria.uniqueResult();
	}

	public void updateLatestPresence(String jabberId, Presence presence) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MPresence.class);
		criteria.add(Restrictions.eq("jabberId", jabberId));
		MPresence latestPresence = (MPresence) criteria.uniqueResult();
		if (latestPresence == null) {
			latestPresence = new MPresence();
			latestPresence.setJabberId(jabberId);
			session.save(latestPresence);
		}
		latestPresence.setStatus(presence.getStatus());
		latestPresence.setTime(Calendar.getInstance().getTime());
		session.update(latestPresence);
	}

	public void acknowledgeStatus(MUser person) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MPresenceAcknowledge.class);
		criteria.add(Restrictions.eq("jabberId", person.getJabberId()));
		criteria.add(Restrictions.eq("acknowledgedBy", PDUserSession.getInstance().getUser()));
		MPresenceAcknowledge acknowledgement = (MPresenceAcknowledge) criteria.uniqueResult();
		if (acknowledgement == null) {
			acknowledgement = new MPresenceAcknowledge();
			acknowledgement.setJabberId(person.getJabberId());
			acknowledgement.setAcknowledgedBy(PDUserSession.getInstance().getUser());
			session.save(acknowledgement);
		}
		acknowledgement.setAcknowledgeTime(Calendar.getInstance().getTime());
		session.update(acknowledgement);
	}

	public String findNewestPresenceStatus(String jabberId) {
		// find the time of last acknowledgement
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MPresenceAcknowledge.class);
		criteria.add(Restrictions.eq("jabberId", jabberId));
		criteria.add(Restrictions.eq("acknowledgedBy", PDUserSession.getInstance().getUser()));
		MPresenceAcknowledge acknowledgement = (MPresenceAcknowledge) criteria.uniqueResult();
		Date acknowledgeTime = null;
		if (acknowledgement != null) {
			acknowledgeTime = acknowledgement.getAcknowledgeTime();
		}

		criteria = session.createCriteria(MPresence.class);
		criteria.add(Restrictions.eq("jabberId", jabberId));
		if (acknowledgeTime != null) {
			criteria.add(Restrictions.gt("time", acknowledgeTime));
		}
		MPresence newestPresence = (MPresence) criteria.uniqueResult();
		if (newestPresence == null) {
			return null;
		}
		return newestPresence.getStatus();
	}
}
