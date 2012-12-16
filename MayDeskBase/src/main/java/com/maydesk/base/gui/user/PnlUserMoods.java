package com.maydesk.base.gui.user;

import java.util.List;

import nextapp.echo.app.Column;
import nextapp.echo.app.Component;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ICrud;
import com.maydesk.base.util.PnlImages;


public class PnlUserMoods extends Column implements ICrud {

	private PnlImages pnlImages;
	private int userId;

	public PnlUserMoods() {
		initGUI();
	}

	private void initGUI() {
		pnlImages = new PnlImages(true, 48) {
			@Override
			protected void onAddImage(MMediaFile file) {
				file.setParentClass(MUser.class.getName());
				file.setParentId(userId);	
			}
		};
		add(pnlImages);
	}

	public void readFromModel(MBase model) {
		userId = ((MDataLink)model).getTargetId();
		Criteria criteria = PDHibernateFactory.getSession().createCriteria(MMediaFile.class);
		criteria.add(Restrictions.eq("parentClass", MUser.class.getName()));
		criteria.add(Restrictions.eq("parentId", userId));
		List<MMediaFile> imageList = criteria.list();

		if (imageList != null) {
			pnlImages.setAddImgList(imageList);
		}
	}

	public Component getFocusComponent() {
		return null;
	}

	@Override
    public Class getModelClass() {
	    return MUser.class;
    }
}