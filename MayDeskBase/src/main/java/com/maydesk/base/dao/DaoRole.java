/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.util.IRole;

/**
 */
public class DaoRole implements IDAO {


	public static List<MUser> findUsersByRole(IRole roleName) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUserRole.class);
		criteria.add(eq("roleName", roleName.name()));
		criteria.add(eq("roleClass", roleName.getClass().getCanonicalName()));
		criteria.setProjection(Projections.property("userRef"));
		return criteria.list();
	}
	
	public static List<MUserRole> findRoles(MUser user) {
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MUserRole.class);
		criteria.add(eq("userRef", user));
		List userRoles = criteria.list();
		return userRoles;
	}
	
	public static List<MUserRole> getRoles(MUser user, MBase context, Session... session2) {
		Session session = null;
		if (session2 != null && session2.length > 0) {
			session = session2[0];
		} else {
			session = PDHibernateFactory.getSession();
		}
		Criteria criteria = session.createCriteria(MUserRole.class);
		criteria.add(eq("userRef", user));
		if (context == null) {
			criteria.add(Restrictions.isNull("contextClass"));
		} else {
			Disjunction orCondition = Expression.disjunction();
			criteria.add(orCondition);
			Conjunction andCondition = Expression.conjunction();
			orCondition.add(andCondition);
			andCondition.add(eq("contextId", context.getId()));
			andCondition.add(eq("contextClass", context.getClass().getCanonicalName()));
			orCondition.add(Restrictions.isNull("contextClass"));

		}
		return criteria.list();
	}

//	public static List<MUserRole> getRole(MUser user, IRole role, MBase context, Session session) {
//		Criteria criteria = session.createCriteria(MUserRole.class);
//		criteria.add(eq("userRef", user));
//		if (context != null) {
//			Disjunction orCondition = Expression.disjunction();
//			criteria.add(orCondition);
//			Conjunction andCondition = Expression.conjunction();
//			orCondition.add(andCondition);
//			andCondition.add(eq("contextId", context.getId()));
//			andCondition.add(eq("contextClass", context.getClass().getCanonicalName()));
//			orCondition.add(Restrictions.isNull("contextClass"));
//		}
//		if (role != null) {
//			criteria.add(eq("roleName", role.name()));
//		}
//		return criteria.list();
//    }

	

	public static List<MUserRole> findUserRoles(MBase context) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MUserRole.class);
		criteria.add(eq("contextId", context.getId()));
		criteria.add(eq("contextClass", context.getClass().getCanonicalName()));
	    return criteria.list();
    }

	public static boolean hasUserRole(MUser user, IRole role, MBase context) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MUserRole.class);
		criteria.add(eq("userRef", user));
		criteria.add(eq("roleName", role.name()));
		criteria.add(eq("roleClass", role.getClass().getCanonicalName()));
		criteria.add(eq("contextId", context.getId()));
		criteria.add(eq("contextClass", context.getClass()));
		criteria.setMaxResults(1);
		return criteria.list().size() > 0;
	    
    }

	public static List<MUserRole> findDeputyRoles(MUser user) {
	    // TODO Auto-generated method stub
	    return null;
    }

	public static boolean hasUserRole(MUser user, MBase context) {
		Session session = PDHibernateFactory.getSession();
		Criteria criteria = session.createCriteria(MUserRole.class);
		criteria.add(eq("userRef", user));
		criteria.add(eq("contextId", context.getId()));
		criteria.add(eq("contextClass", context.getClass().getCanonicalName()));
		criteria.setMaxResults(1);
		return criteria.list().size() > 0;
    }

}
