package com.maydesk.social.model;

import javax.persistence.Entity;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MUser;
import lombok.soplets.SopBean;
import javax.persistence.ManyToOne;
import com.maydesk.base.model.MBase;
import com.maydesk.social.sop.SopAnnouncement;

@Entity
@SopBean(sopRef=SopAnnouncement.class)
public class MAnnouncement extends MBase { 

}
