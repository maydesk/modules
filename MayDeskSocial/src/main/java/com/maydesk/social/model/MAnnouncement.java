package com.maydesk.social.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.soplets.SopBean;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.social.sop.SopAnnouncement;

/**
 * @author chrismay
 */
@Entity
@SopBean(sopRef = SopAnnouncement.class)
public class MAnnouncement extends MBase {

}
