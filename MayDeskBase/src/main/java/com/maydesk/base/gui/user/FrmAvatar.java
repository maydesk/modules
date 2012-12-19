package com.maydesk.base.gui.user;

import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDSimpleDialog;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ByteArrayImageReference;
import com.maydesk.base.widgets.PDAvatar;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDUpload;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class FrmAvatar extends PDSimpleDialog {

	private MUser user;
	private PDUpload uploadSelect;
	private Label lblImage;
	private PDGrid grid;
	private ContainerEx ctnPersons;
	private PDCombo<ColorEntry> cboColor;
	private ImageReference imgUser;
	private MMediaFile imageFile;

	public FrmAvatar() {
		super("Avatar Image", 410, 390);
		user = PDUserSession.getInstance().getUser();
		imgUser = PDUserSession.getInstance().getImage(user.getJabberId());

		String msg = "Please upload your personal avatar image";
		addMainComponent(new PDLabel(msg, PDLabel.STYLE.FIELD_VALUE));

		msg = "The image should be 60x60 pixel and in black/white";
		addMainComponent(new PDLabel(msg, PDLabel.STYLE.FIELD_VALUE));

		msg = "You may change your default color shade below";
		addMainComponent(new PDLabel(msg, PDLabel.STYLE.FIELD_VALUE));

		uploadSelect = new PDUpload() {
			@Override
			protected boolean isValidFile(String fileName) {
				String ext = fileName.substring(fileName.length() - 3, fileName.length());
				ext = ext.toUpperCase();
				return ext.equals("GIF") || ext.equals("JPG") || ext.equals("PNG");
			}
		};
		uploadSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				uploadSelectClicked();
			}
		});
		addMainComponent(uploadSelect);

		grid = new PDGrid(4);
		addMainComponent(grid);

		grid.addEmpty();
		grid.addLabel("Your icon");
		grid.addLabel("Example 1");
		grid.addLabel("Example 2");

		grid.addLabel("Image");
		lblImage = new Label(imgUser);
		grid.add(lblImage);

		grid.add(new Label(new ResourceImageReference("img/person1.png")));
		grid.add(new Label(new ResourceImageReference("img/person2.png")));

		grid.addLabel("Icon Preview");

		ctnPersons = new ContainerEx();
		ctnPersons.setHeight(new Extent(70));
		grid.add(ctnPersons);

		PDAvatar person = new PDAvatar(imgUser, 0, 0, new Color(user.getColorShade()));
		ctnPersons.add(person);

		ResourceImageReference img1 = new ResourceImageReference("img/person1.png");
		PDAvatar person1 = new PDAvatar(img1, 0, 0, Color.RED);
		ContainerEx ctnPerson1 = new ContainerEx();
		ctnPerson1.setHeight(new Extent(70));
		ctnPerson1.add(person1);
		grid.add(ctnPerson1);

		ResourceImageReference img2 = new ResourceImageReference("img/person2.png");
		PDAvatar person2 = new PDAvatar(img2, 0, 0, Color.BLUE);
		ContainerEx ctnPerson2 = new ContainerEx();
		ctnPerson2.setHeight(new Extent(70));
		ctnPerson2.add(person2);
		grid.add(ctnPerson2);

		grid.addLabel("Color shade");
		ColorEntry[] colors = new ColorEntry[] { new ColorEntry(Color.BLUE, "blue"), new ColorEntry(Color.CYAN, "cyan"), new ColorEntry(Color.GREEN, "green"),
				new ColorEntry(Color.MAGENTA, "magenta"), new ColorEntry(Color.ORANGE, "orange"), new ColorEntry(Color.PINK, "pink"), new ColorEntry(Color.RED, "red"),
				new ColorEntry(Color.YELLOW, "yellow") };
		cboColor = new PDCombo<ColorEntry>(colors);
		cboColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				user.setColorShade(cboColor.getSelectedItem().getColor().getRgb());
				PDHibernateFactory.getSession().update(user);
				updatePersonIcon();
			}
		});
		for (ColorEntry colorEntry : colors) {
			if (user.getColorShade() == colorEntry.getColor().getRgb()) {
				cboColor.setSelectedItem(colorEntry);
				break;
			}
		}
		cboColor.setWidth(new Extent(100));
		grid.addFill(cboColor);

	}

	private static class ColorEntry {
		private Color color;
		private String caption;

		ColorEntry(Color color, String caption) {
			this.color = color;
			this.caption = caption;
		}

		@Override
		public String toString() {
			return caption;
		}

		Color getColor() {
			return color;
		}
	}

	private void uploadSelectClicked() {
		imageFile = uploadSelect.getFile();
		if (imageFile == null)
			return;
		try {
			// DaoUser.deleteUserIcon(user.getId());
			//
			// //byte[] preview = ImageResizer.resize(file.getFileBytes(), previewSize, 1);
			// //file.setPreviewBytes(preview);
			// file.setFileName(SopMood.normal.name());
			// file.setParentId(user.getId());
			// PDHibernateFactory.getSession().save(file);
			// PDHibernateFactory.doCommit();

			imgUser = new ByteArrayImageReference(imageFile.getFileBytes(), imageFile.getContentType(), 60);
			lblImage.setIcon(imgUser);

			updatePersonIcon();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void updatePersonIcon() {
		ctnPersons.removeAll();
		PDAvatar avatar = new PDAvatar(imgUser, 0, 0, cboColor.getSelectedItem().getColor());
		ctnPersons.add(avatar);
	}

	@Override
	protected boolean onOkClicked() {
		PDUserSession.getInstance().updateAvatar(imageFile);
		return true;
	}
}