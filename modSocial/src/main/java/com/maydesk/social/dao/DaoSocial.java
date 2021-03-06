/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MUser;
import com.maydesk.social.model.MAnnouncement;
import com.maydesk.social.model.MAnnouncementUser;
import com.maydesk.social.sop.SopAnnouncementUser;

/**
 * @author chrismay
 */
public class DaoSocial {

	private static DaoSocial INSTANCE;

	public static DaoSocial getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DaoSocial();
		}
		return INSTANCE;
	}

	public List<MAnnouncementUser> findMyNews(Session session) {
		Criteria c = session.createCriteria(MAnnouncementUser.class);
		c.add(Restrictions.eq(SopAnnouncementUser.targetUser.name(), PDUserSession.getInstance().getUser()));
		return c.list();
	}

	public void createAnnouncement(MAnnouncement announcement) {
		Session session = PDHibernateFactory.getSession();
		session.save(announcement);
		Criteria c = session.createCriteria(MUser.class);
		List<MUser> users = c.list();
		for (MUser user : users) {
			MAnnouncementUser newsUser = new MAnnouncementUser();
			newsUser.setAnnouncement(announcement);
			newsUser.setTargetUser(user);
			session.save(newsUser);
		}
	}
}
