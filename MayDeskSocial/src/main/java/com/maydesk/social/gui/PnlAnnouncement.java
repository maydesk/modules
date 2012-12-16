package com.maydesk.social.gui;

import java.util.List;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.LayoutDirection;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;

import org.hibernate.Session;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.enums.SopMood;
import com.maydesk.base.util.IMessageListener;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.social.dao.DaoSocial;
import com.maydesk.social.model.MAnnouncement;
import com.maydesk.social.model.MAnnouncementUser;
import com.maydesk.social.sop.SopSocialShortcut;

import echopoint.ContainerEx;
import echopoint.HtmlLabel;
import echopoint.Strut;
import echopoint.able.Positionable;

public class PnlAnnouncement extends ContainerEx implements IPlugTarget{ 

	private Label lblUserIcon = new Label();
	private PDLabel lblHeader;
	private HtmlLabel lblText;
	private MAnnouncementUser intraNewsUser;

	
	public PnlAnnouncement() {
		setPosition(Positionable.ABSOLUTE);
		setWidth(new Extent(304));
		setHeight(new Extent(140));
		setLeft(new Extent(60));
		setBottom(new Extent(60));
		setInsets(new Insets(9));
		setBackgroundImage(new FillImage(new ResourceImageReference("img/semitrans6.png")));
		
		SplitPane sp = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
		add(sp);
		
		sp.add(lblUserIcon);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(6));
		row.setInsets(new Insets(6));
		row.setLayoutDirection(LayoutDirection.RTL);
		RowLayoutData cld = new RowLayoutData();
		row.setLayoutData(cld);
		sp.add(row);
		
		Button btnClose = new Button(new ResourceImageReference("nextapp/echo/webcontainer/resource/resource/WindowPaneClose.gif"));
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PDHibernateFactory.getSession().delete(intraNewsUser);
				PDHibernateFactory.getSession().getTransaction().commit();
				PDHibernateFactory.getSession().beginTransaction();
				setVisible(false);
				
			}
		});
		row.add(btnClose);
		
		Button btnShortcut = new Button(new ResourceImageReference("img/shortcut.png"));
		btnShortcut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MShortcut shortcut = new MShortcut();
				shortcut.setOwner(PDUserSession.getInstance().getUser());
				shortcut.setShortcutType(SopSocialShortcut.openIntraNews);
				shortcut.setPositionX(450);
				shortcut.setPositionY(500);
				shortcut.setModelId(intraNewsUser.getAnnouncement().getId());
				PDHibernateFactory.getSession().save(shortcut);
				PDHibernateFactory.getSession().delete(intraNewsUser);
				PDHibernateFactory.doCommit(); 
				PDDesktop.getInstance().addShortcut(shortcut);
				setVisible(false);
			}
		});
		row.add(btnShortcut);
//		row.add(new Button(new ResourceImageReference("img/thumbs_down.gif")));
//		row.add(new Button(new ResourceImageReference("img/thumbs_up.gif")));
		
		lblHeader = new PDLabel(PDLabel.STYLE.WHITE_BIG);
		lblHeader.getLabel().setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(18)));
		lblHeader.getLabel().setForeground(Color.LIGHTGRAY);
		lblHeader.setLeft(new Extent(32));
		lblHeader.setTop(new Extent(5));
		add(lblHeader);

		add(new Strut(0, 12));
		
		lblText = new HtmlLabel();
		lblText.setForeground(Color.LIGHTGRAY);
		add(lblText);		
	}

	@Override
	public void initWire(MWire parentWire) {
		PDApplicationInstance.getActivePD().addListener(new IMessageListener() {
			@Override
			public void doPoll(Session session) {
				PnlAnnouncement.this.doPoll(session);
			}
		});
	}

	private void doPoll(Session session) {
		
		List<MAnnouncementUser> myNews = DaoSocial.getInstance().findMyNews(session);
		if (myNews.size() == 0) {
			setVisible(false);
			return;
		}
		
		setVisible(true);
		
		MAnnouncementUser newIntraNewsUser = myNews.get(0);
		if (intraNewsUser != null && intraNewsUser.getId() == newIntraNewsUser.getId()) {
			return;
		}
		intraNewsUser = newIntraNewsUser;
		
		setContent(newIntraNewsUser.getAnnouncement());
	}
	
	public void setContent(MAnnouncement intraNews) {
		MUser author = intraNews.getAuthor();
		if (author != null) {
			lblUserIcon.setIcon(PDUserSession.getInstance().getImage(author.getJabberId(), 36));			
		}
		lblHeader.setText(intraNews.getTitle());
		//&nbsp;
		String text = intraNews.getText();
		text = text.replaceAll("\n", "<br/>");
		text = "<div align='justify'>" + text + "</div>";
		lblText.setText(text);
		
	}
}
