/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.FillImageBorder;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.internal.PDExpandableFilter;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDUtil;

/**
 * A gereral purpose floating window
 * 
 * @author chrismay
 */
public class PDWindowPane extends WindowPane {

	public static final String PROPERTY_SIDEBAR_SHOWN = "sidebarShown"; //$NON-NLS-1$
	public static final String PROPERTY_SIDEBAR_EXPANDED = "sidebarExpanded"; //$NON-NLS-1$

	protected Row toolbar;
	protected PDExpandableFilter sidebar;

	public PDWindowPane() {
		setStyleName("GlassBlue");

		setSidebarShown(true);
		setSidebarExpanded(false);
		setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);

		initGUI();

		toolbar = new Row();
		toolbar.setCellSpacing(new Extent(6));
		add(toolbar);

		sidebar = new PDExpandableFilter(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isSidebarExpanded()) {
					setSidebarExpanded(false);
				} else {
					setSidebarExpanded(true);
				}
			}
		});
		add(sidebar);
	}

	private void initGUI() {
		setClosable(true);
		setWidth(new Extent(500));
		setHeight(new Extent(450));
		setIconInsets(new Insets(7, 3, 0, 0));
		setTitleFont(new Font(Font.VERDANA, Font.BOLD, new Extent(16)));
		setTitleForeground(Color.WHITE);
		setTitleHeight(new Extent(28));
		setTitleBackgroundImage(new FillImage(new ResourceImageReference("img/glassblue/Header.png")));
		setTitleInsets(new Insets(25, 5, 0, 0));

		FillImageBorder border = null;
		border = new FillImageBorder(null, new Insets(10), new Insets(6));
		// border.setFillImage(FillImageBorder.TOP_LEFT, new FillImage(EImage16.borderTopLeft.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.TOP, new FillImage(EImage16.borderTop.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.TOP_RIGHT, new FillImage(EImage16.borderTopRight.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.LEFT, new FillImage(EImage16.borderLeft.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.RIGHT, new FillImage(EImage16.borderRight.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.BOTTOM_LEFT, new FillImage(EImage16.borderBottomLeft.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.BOTTOM, new FillImage(EImage16.borderBottom.getImage(), null, null, FillImage.REPEAT));
		// border.setFillImage(FillImageBorder.BOTTOM_RIGHT, new FillImage(EImage16.borderBottomRight.getImage(), null, null, FillImage.REPEAT));

		border.setFillImage(FillImageBorder.TOP_LEFT, new FillImage(new ResourceImageReference("img/glassblue/BorderTopLeft.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.TOP, new FillImage(new ResourceImageReference("img/glassblue/BorderTop.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.TOP_RIGHT, new FillImage(new ResourceImageReference("img/glassblue/BorderTopRight.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.LEFT, new FillImage(new ResourceImageReference("img/glassblue/BorderLeft.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.RIGHT, new FillImage(new ResourceImageReference("img/glassblue/BorderRight.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.BOTTOM_LEFT, new FillImage(new ResourceImageReference("img/glassblue/BorderBottomLeft.png"), new Extent(5), new Extent(0), 0));
		border.setFillImage(FillImageBorder.BOTTOM, new FillImage(new ResourceImageReference("img/glassblue/BorderBottom.png"), null, null, FillImage.REPEAT));
		border.setFillImage(FillImageBorder.BOTTOM_RIGHT, new FillImage(new ResourceImageReference("img/glassblue/BorderBottomRight.png"), null, null, FillImage.REPEAT));

		border.setContentInsets(new Insets(12, 8, 8, 8));

		// titleBackgroundImage: {
		// url: "image/window/glassblue/Header.png",
		// repeat: "repeat-x",
		// y: "100%"
		// },
		// border: {
		// contentInsets: "6px 15px 15px 12px",
		// borderInsets: "34px 20px 20px 20px",
		// topLeft: "image/window/glassblue/BorderTopLeft.png",
		// top: "image/window/glassblue/BorderTop.png",
		// topRight: "image/window/glassblue/BorderTopRight.png",
		// left: "image/window/glassblue/BorderLeft.png",
		// right: "image/window/glassblue/BorderRight.png",
		// bottomLeft: "image/window/glassblue/BorderBottomLeft.png",
		// bottom: "image/window/glassblue/BorderBottom.png",
		// bottomRight: "image/window/glassblue/BorderBottomRight.png"
		// }
		// }
		setBorder(border);
	}

	public Button addToolButton(String text, EImage16 image, boolean showText, String renderId) {
		Button btn = new Button();
		// XXX btn.setRenderId(getClass().getSimpleName() + renderId);
		if (image != null) {
			btn.setIcon(image.getImage());
		}
		btn.setBorder(PDUtil.emptyBorder());
		btn.setRolloverBorder(new Border(1, Color.DARKGRAY, Border.STYLE_DOTTED));
		btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, new Extent(9)));
		btn.setLineWrap(false);
		btn.setForeground(Color.WHITE);
		// btn.setRolloverBackground(PDAppInstance.getActivePD().getLookAndFeel().getBackgroundDark());
		btn.setIconTextMargin(new Extent(2));
		btn.setInsets(new Insets(0, 3, 0, 0));
		if (showText || image == null) {
			btn.setText(text);
		} else {
			btn.setToolTipText(text);
		}
		getToolbar().add(btn);
		return btn; // we return the btn, so it is easy to attach a listener
	}

	// we need to override, as a work-around for adding the toolbar
	@Override
	public boolean isValidChild(Component component) {
		return true;
	}

	public Row getToolbar() {
		return toolbar;
	}

	public boolean isSidebarShown() {
		return (Boolean) get(PROPERTY_SIDEBAR_SHOWN);
	}

	public boolean isSidebarExpanded() {
		return (Boolean) get(PROPERTY_SIDEBAR_EXPANDED);
	}

	public void setSidebarShown(boolean shown) {
		set(PROPERTY_SIDEBAR_SHOWN, shown);
	}

	public void setSidebarExpanded(boolean expanded) {
		set(PROPERTY_SIDEBAR_EXPANDED, expanded);
	}

	public PDExpandableFilter getSidebar() {
		return sidebar;
	}

	public void setLeft(int left) {
		setPositionX(new Extent(left));
	}

	public int getLeft() {
		return getPositionX().getValue();
	}
}