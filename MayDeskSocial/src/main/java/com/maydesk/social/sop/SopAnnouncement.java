package com.maydesk.social.sop;

import javax.persistence.ManyToOne;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MUser;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class, Beanable.class })
public enum SopAnnouncement {

	@ManyToOne(targetEntity = MUser.class)
	@Soplet(textEN = "Author", javaType = MUser.class)
	author,

	@Soplet(textEN = "Title")
	title,

	@Soplet(textEN = "Text", length = 2000)
	text;
}
