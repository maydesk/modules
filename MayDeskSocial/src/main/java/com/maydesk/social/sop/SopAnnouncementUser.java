package com.maydesk.social.sop;

import javax.persistence.ManyToOne;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MUser;
import com.maydesk.social.model.MAnnouncement;

@Sop(aspects={Translatable.class, Beanable.class})
public enum SopAnnouncementUser {
	@ManyToOne(targetEntity=MUser.class)
	@Soplet(
		javaType=MUser.class)
	targetUser,

	@ManyToOne(targetEntity=MAnnouncement.class)
	@Soplet(
		javaType=MAnnouncement.class)
	announcement;
}
