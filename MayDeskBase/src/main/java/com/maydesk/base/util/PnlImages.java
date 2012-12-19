package com.maydesk.base.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.RowLayoutData;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.gui.DlgImageViewer;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.sop.enums.SopMood;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDUpload;

import echopoint.Strut;

/**
 * @author Alejandro Salas
 */
public class PnlImages extends Column {

	private PDUpload uploadSelect;
	private Row rowIcons;
	private List<MMediaFile> addImgList = new ArrayList<MMediaFile>();
	private List<MMediaFile> delImgList = new ArrayList<MMediaFile>();
	private boolean showFileSelector;
	private int previewSize;
	private List<Button> btnDeleteList = new ArrayList<Button>();

	public PnlImages(boolean showFileSelector, int previewSize) {
		this.showFileSelector = showFileSelector;
		this.previewSize = previewSize;
		initGUI();
	}

	private void initGUI() {
		setCellSpacing(new Extent(6));

		if (showFileSelector) {
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
					MMediaFile file = uploadSelect.getFile();
					if (file == null)
						return;
					try {
						byte[] preview = ImageResizer.resize(file.getFileBytes(), previewSize, 1);
						file.setPreviewBytes(preview);
						file.setFileName(SopMood.normal.name());
						onAddImage(file);
						addIcon(file);
						addImgList.add(file);
						PDHibernateFactory.getSession().save(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			add(uploadSelect);
		}

		rowIcons = new Row();
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		rowIcons.setLayoutData(cld);
		add(rowIcons);
	}

	private void addIcon(final MMediaFile mediaFile) throws IOException, SQLException {

		Column col = new Column();
		rowIcons.add(col);

		Button lbl2 = new Button();
		lbl2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DlgImageViewer dlg = new DlgImageViewer(mediaFile);
				PDDesktop.getInstance().addWindow(dlg);
			}
		});
		lbl2.setIcon(new ByteArrayImageReference(mediaFile.getPreviewBytes(), mediaFile.getContentType(), 48));
		col.add(lbl2);

		final PDCombo<SopMood> cboMood = new PDCombo<SopMood>(SopMood.values());
		cboMood.setWidth(new Extent(80));
		cboMood.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediaFile.setFileName(cboMood.getSelectedItem().name());
				PDHibernateFactory.getSession().update(mediaFile);
			}
		});
		try {
			SopMood mood = SopMood.valueOf(mediaFile.getFileName());
			cboMood.setSelectedItem(mood);
		} catch (Exception e) {
		}

		col.add(cboMood);

		if (showFileSelector) {
			Button btnDelete = new Button(PDUtil.getImg("img/DefaultCloseButton.gif"));
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnDeleteClicked(mediaFile);
				}
			});
			RowLayoutData rld = new RowLayoutData();
			rld.setAlignment(Alignment.ALIGN_TOP);
			btnDelete.setLayoutData(rld);
			rowIcons.add(btnDelete);
			btnDeleteList.add(btnDelete);
		}

		rowIcons.add(new Strut(20, 0));
	}

	protected void btnDeleteClicked(MMediaFile mediaFile) {
		addImgList.remove(mediaFile);
		delImgList.add(mediaFile);
		PDHibernateFactory.getSession().delete(mediaFile);

		fillFields();
	}

	public List<MMediaFile> getAddImgList() {
		return addImgList;
	}

	public void setAddImgList(List<MMediaFile> images) {
		this.addImgList = images;
		fillFields();
	}

	public List<MMediaFile> getDelImgList() {
		return delImgList;
	}

	public void setDelImgList(List<MMediaFile> delImgList) {
		this.delImgList = delImgList;
	}

	protected void onAddImage(MMediaFile file) {
		// override to store image
	}

	private void fillFields() {
		rowIcons.removeAll();
		btnDeleteList.clear();
		for (MMediaFile mediaFile : addImgList) {
			try {
				addIcon(mediaFile);
			} catch (Exception e) {
				rowIcons.add(new Label("Error"));
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		uploadSelect.setVisible(enabled);
		for (Button button : btnDeleteList) {
			button.setVisible(enabled);
		}
	}
}
