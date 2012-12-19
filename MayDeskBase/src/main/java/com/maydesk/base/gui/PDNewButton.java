package com.maydesk.base.gui;

import java.util.Hashtable;
import java.util.List;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.ContextMenu;
import nextapp.echo.extras.app.menu.DefaultMenuModel;
import nextapp.echo.extras.app.menu.DefaultOptionModel;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.model.MWire;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.util.PDUtil;

import echopoint.ContainerEx;
import echopoint.able.Positionable;

/**
 * @author chrismay
 */
public class PDNewButton extends ContainerEx implements IPlugTarget {

	@Override
	public void initWire(MWire parentWire) {

		setPosition(Positionable.ABSOLUTE);
		setTop(new Extent(5));
		setLeft(new Extent(190));
		setWidth(new Extent(30));
		setHeight(new Extent(20));
		// PDDesktop.getInstance().addWindow(c4);

		Label lbl2 = new Label(new ResourceImageReference("img/new.gif"));

		lbl2.setForeground(Color.WHITE);
		DefaultMenuModel model2 = new DefaultMenuModel();

		final Hashtable<String, MWire> wireMap = new Hashtable<String, MWire>();
		List<MWire> wires = PDUtil.findWires(parentWire);
		for (MWire wire : wires) {
			model2.addItem(new DefaultOptionModel(wire.getIdAsString(), wire.getCaption(), null));
			wireMap.put(wire.getIdAsString(), wire);
		}
		ContextMenu mnu = new ContextMenu(lbl2, model2);
		mnu.setBackground(Color.BLACK);
		mnu.setForeground(Color.WHITE);
		add(mnu);

		mnu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg) {
				String id = arg.getActionCommand();
				MWire wire = wireMap.get(id);
				String className = wire.getPlug().getEditorClassName();
				try {
					Class clazz = Class.forName(className);
					Object instance = clazz.newInstance();
					((IPlugTarget) instance).initWire(wire);
					PDDesktop.getInstance().addWindow((Component) instance);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// MNotice notice = new MNotice();
				// PDHibernateFactory.getSession().save(notice);
				//
				// MShortcut2 textIcon = new MShortcut2();
				// textIcon.setShortcutType(SopAction.openNotice);
				// textIcon.setPositionX(250);
				// textIcon.setPositionY(50);
				// textIcon.setOwner(PDUserSession.getInstance().getUser());
				// textIcon.setModelId(notice.getId());
				// PDDesktop.getInstance().addShortcut(textIcon);
			}
		});
	}

}
