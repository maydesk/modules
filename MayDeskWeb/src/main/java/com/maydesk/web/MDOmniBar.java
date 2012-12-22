/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sun.misc.BASE64Encoder;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MAvatar;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.sop.enums.SopMood;
import com.maydesk.base.util.CledaConnector;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.util.ImageResizer;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.util.SimpleEntrySelect;
import com.maydesk.base.widgets.PDAutoLookupField;

import echopoint.model.AutoLookupSelectFieldModel;

/**
 * @author chrismay
 */
public class MDOmniBar extends PDAutoLookupField<MUser> implements IPlugTarget {

	public MDOmniBar() {
		super(MUser.class);

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (getKey() == null)
					return;
				int userId = Integer.parseInt(getKey());
				MAvatar person = new MAvatar();
				person.setPositionX(350);
				person.setPositionY(40);
				person.setPerson(MBase.loadById(MUser.class, userId));
				person.setOwner(PDUserSession.getInstance().getUser());
				PDHibernateFactory.getSession().save(person);
				PDDesktop.getInstance().addPerson(person);
				setText("");
			}
		});
		setBackgroundImage(new FillImage(EImage16.textfield_bg.getImage(), null, null, FillImage.NO_REPEAT));
		setBackground(PDLookAndFeel.BACKGROUND_COLOR); // colorScheme.getBackgroundDark());
		setWidth(new Extent(152));
		setHeight(new Extent(18));
		setInsets(new Insets(30, 0, 0, 0));
		setBorder(PDUtil.emptyBorder());
		setAutoLookupFieldModel(new UserLookupModel());
		setSelectedOptionBackground(new Color(222, 222, 222));
		setActionClick(true);
		setAutoSelect(true);

	}

	static class UserLookupModel implements AutoLookupSelectFieldModel {

		@Override
		public List<EntrySelect> getAllEntries() {
			return null;
		}

		@Override
		public List<EntrySelect> searchEntries(String partialSearchValue) {

			List<EntrySelect> userList = new ArrayList<EntrySelect>();
			Session session = CledaConnector.getInstance().createSession();
			Criteria criteria = session.createCriteria(MUser.class);
			criteria.add(Restrictions.like("jabberId", partialSearchValue + "%"));
			List<MUser> list = criteria.list();
			int i = 0;
			BASE64Encoder encoder = new BASE64Encoder();

			for (MUser user : list) {
				Criteria criteria2 = session.createCriteria(MMediaFile.class);
				criteria2.add(Restrictions.eq("fileName", SopMood.normal.name()));
				criteria2.add(Restrictions.eq("parentId", user.getId()));
				MMediaFile img = (MMediaFile) criteria2.uniqueResult();

				try {
					byte[] bytes = ImageResizer.convertToDropDownEntry(img, user.getJabberId(), user.getDisplayName());
					String byteString = encoder.encode(bytes);
					String imgHtml = "<img style=\"z-index:0;pointer-events:none;\" src=\"data:image/gif;base64," + byteString + "\" width=\"180\" height=\"32\">";
					userList.add(new SimpleEntrySelect(user.getIdAsString(), imgHtml, user.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			session.getTransaction().commit();
			session.close();
			return userList;
		}
	}

	@Override
	public void initWire(MWire parentWire) {
	}
}
