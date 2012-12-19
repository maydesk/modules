/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SelectField;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.Table;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.app.table.TableCellRenderer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.action.PDActionManager;
import com.maydesk.base.internal.IDeleteMaster;
import com.maydesk.base.internal.PDTitleBar;
import com.maydesk.base.model.MActionSelectRow;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MBaseWithTitle;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.table.PDNavigationBar;
import com.maydesk.base.table.PDPageableFactory;
import com.maydesk.base.util.HeaderValue;
import com.maydesk.base.util.IBindable;
import com.maydesk.base.util.ICrud;
import com.maydesk.base.util.PDDataGridModel;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.util.PDUtil;

import echopoint.ContainerEx;
import echopoint.Strut;

/**
 * This class is a high-level component to display editable data in a convenient way. Features include: - Built-in support to display a pageable list of expandable sections - Very light-way loading of
 * large data sets - Built-in slidable filter - Configurable toolbar
 * 
 * @author chrismay
 */
public abstract class PDMasterDataView<T extends MBaseWithTitle> extends PDWindowPane implements IDeleteMaster {

	public static enum DISPLAY_MODE {
		SIDE_SCROLL, TOP_SCROLL;
	}

	protected Column colHeaderAndTable;
	protected DISPLAY_MODE displayMode = DISPLAY_MODE.SIDE_SCROLL;
	protected Column headerComponent;
	protected boolean ignoreSliderUpdate = false;
	protected String lastFilterText; // cache the last filter text; this avoids filtering for the same filter text twice
	protected boolean showQuickFilter = false;
	protected SplitPane splitListAndDetails;
	protected TextField txtQuickSearch;
	protected Table table;
	protected PDDataGridModel tableModel2;
	protected PDPageableFactory tableFactory;
	protected SplitPane footerSplit;
	protected int tabIndex;
	protected Component colHeaderContainer;
	protected SplitPane splitHeaderAndEditor;
	protected SplitPane splitTopRowAndBody;
	protected Row topRow;
	protected PDNavigationBar navigationBar;
	protected int rowsPerPage = 20;
	protected SplitPaneLayoutData layoutDataLeftColumn;
	protected PDTitleBar titleBar;
	protected Row rowFooterLeft;
	protected Row rowFooterRight;
	private int selectedRow;
	private BtnSaveCancel btnSaveCancel;
	protected PDTab editorTab;
	protected List<ICrud<?>> editors;
	protected Class modelClass;
	protected ActionListener titleChangeListener;

	public PDMasterDataView(boolean showQuickFilter, DISPLAY_MODE displayMode, Class modelClass2, Object... baseModel) {
		this.displayMode = displayMode;
		this.modelClass = modelClass2;
		assignBaseModel(baseModel);
		editors = new Vector<ICrud<?>>();
		titleChangeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedRow = table.getSelectionModel().getMinSelectedIndex();
				if (selectedRow < 0 || tableModel2.getRowCount() == 0) {
					return;
				}
				HeaderValue headerValue = (HeaderValue) tableModel2.getValueAt(0, selectedRow);
				if (!(e.getSource() instanceof MBaseWithTitle)) {
					return;
				}
				MBaseWithTitle model = (MBaseWithTitle) e.getSource();
				headerValue.title = model.getCachedTitle();
				tableModel2.fireTableDataChanged();
			}
		};
		init(showQuickFilter);
	}

	protected void assignBaseModel(Object[] baseModel) {
		// override if applicable
	}

	public void addFilter(String caption, Component filterComponent) {
		if (!sidebar.isInitialized()) {
			ActionListener okListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadData();
				}
			};
			sidebar.setOkListener(okListener);
			ActionListener resetListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					resetFilter();
					loadData();
				}
			};
			sidebar.setResetListener(resetListener);
			layoutDataLeftColumn.setInsets(new Insets(24, 6, 6, 6));
		}
		sidebar.addFilter(caption, filterComponent);
	}

	protected void addHeaderComponent(Component child) {
		headerComponent.add(child);
	}

	protected void initTable() {
		tableModel2 = new PDDataGridModel(rowsPerPage);
		tableModel2.setFactory(tableFactory);
		table = new Table(tableModel2);
		table.setBackground(Color.WHITE);
		table.setSelectionEnabled(true);
		table.setSelectionBackground(PDLookAndFeel.HIGHLIGHT_COLOR);
		table.setInsets(new Insets(6, 2, 6, 2));
		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(Table table, Object value, int col, int row) {
				HeaderValue headerValue = (HeaderValue) value;
				if (displayMode == DISPLAY_MODE.SIDE_SCROLL) {
					return tableFactory.getTableCellComponent(headerValue);
				} else {
					return tableFactory.getHeaderComponent(headerValue);
				}
			}
		});
		table.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// apply the last data
				// if (binding.hasChanges()) {
				// PDYesNoCancelDialog msg = new
				// PDYesNoCancelDialog("Soplet Studio", "Save changes?") {
				// @Override
				// protected boolean onOkClicked() {
				// binding.applyChanges();
				// readFromModel2();
				// return true;
				// }
				// @Override
				// protected boolean onNoClicked() {
				// binding.resetChanges();
				// //HeaderValue headerValue =
				// (HeaderValue)tableModel2.getValueAt(0, selectedRow) ;
				// //currentModel = (T)tableFactory.getModel(headerValue);
				// refreshTitle();
				// readFromModel();
				// return true;
				// }
				// @Override
				// protected boolean onCancelClicked() {
				// table.getSelectionModel().setSelectedIndex(selectedRow,
				// true);
				// return true;
				// }
				// };
				// msg.setVisible(true);
				// PDDesktop.getInstance().addWindow(msg);
				// } else {
				readFromModel2();
				// }
			}
		});
	}

	protected void readFromModel2() {
		int row = selectedRow;
		readFromModel();
		setFocus();

		// log the action
		MActionSelectRow action = new MActionSelectRow();
		action.setMasterDataView(PDMasterDataView.this);
		action.setOldIntValue(row);
		action.setNewIntValue(table.getSelectionModel().getMinSelectedIndex());
		PDActionManager.getInstance().addAction(action);
	}

	protected abstract PDPageableFactory getFactory(PDMasterDataView masterDataView);

	public String getFilterText() {
		if (txtQuickSearch == null)
			return null;
		return txtQuickSearch.getText();
	}

	protected Column getHeaderComponent() {
		return headerComponent;
	}

	protected void init(boolean showQuickFilter) {
		this.showQuickFilter = showQuickFilter;
		initGUI();
		initGUI2();
		loadData();
	}

	protected final void initGUI() {
		SplitPane mainSplit = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		mainSplit.setSeparatorPosition(new Extent(20)); // hight of the footer
		add(mainSplit);

		footerSplit = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
		footerSplit.setSeparatorPosition(new Extent(200));
		// footerSplit.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		SplitPaneLayoutData spld = new SplitPaneLayoutData();
		spld.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		spld.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		// spld.setBackgroundImage(new FillImage(new ResourceImageReference("img/glassblue/Header.png")));

		footerSplit.setLayoutData(spld);
		mainSplit.add(footerSplit);

		rowFooterRight = new Row();
		rowFooterRight.setAlignment(Alignment.ALIGN_RIGHT);
		rowFooterRight.setInsets(new Insets(0, 0, 12, 0));
		RowLayoutData rld = new RowLayoutData();
		rld.setAlignment(Alignment.ALIGN_RIGHT);
		rowFooterRight.setLayoutData(rld);
		footerSplit.add(rowFooterRight);

		btnSaveCancel = new BtnSaveCancel();
		rowFooterRight.add(btnSaveCancel);

		rowFooterLeft = new Row();
		rowFooterLeft.setCellSpacing(new Extent(12));
		footerSplit.add(rowFooterLeft);

		tableFactory = getFactory(this);
		rowsPerPage = tableFactory.getRowsPerPage();
		navigationBar = new PDNavigationBar(rowsPerPage);
		navigationBar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadData(false);
			}
		});
		rowFooterLeft.add(navigationBar);

		// left side of the footer bar
		if (showQuickFilter) {
			mainSplit.setSeparatorPosition(new Extent(25)); // hight of the footer
			rowFooterLeft.add(new Strut(12, 1));

			Label lblFilter = new Label(nls(PDBeanTerms.Quicksearch));
			lblFilter.setForeground(Color.WHITE);
			lblFilter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, new Extent(11)));
			rowFooterLeft.add(lblFilter);

			txtQuickSearch = new TextField();
			txtQuickSearch.setRenderId(getClass().getSimpleName() + "txtQuickSearch"); //$NON-NLS-1$
			txtQuickSearch.setBackgroundImage(new FillImage(EImage16.textfield_bg.getImage(), null, null, FillImage.NO_REPEAT));
			txtQuickSearch.setBackground(PDLookAndFeel.BACKGROUND_COLOR); // colorScheme.getBackgroundDark());
			txtQuickSearch.setWidth(new Extent(175));
			txtQuickSearch.setHeight(new Extent(18));
			txtQuickSearch.setInsets(new Insets(30, 0, 0, 0));
			txtQuickSearch.setBorder(PDUtil.emptyBorder());
			txtQuickSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent action) {
					if (getFilterText().equals(lastFilterText)) {
						return;
					}
					loadData();
					lastFilterText = getFilterText();
				}
			});
			RowLayoutData rld2 = new RowLayoutData();
			rld2.setInsets(new Insets(0, 2, 0, 0));
			txtQuickSearch.setLayoutData(rld2);
			rowFooterLeft.add(txtQuickSearch);
		}

		// Header Row
		splitTopRowAndBody = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitTopRowAndBody.setSeparatorPosition(new Extent(0));
		splitTopRowAndBody.add(topRow = new Row());
		mainSplit.add(splitTopRowAndBody);

		initTable();

		layoutDataLeftColumn = new SplitPaneLayoutData();
		layoutDataLeftColumn.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		layoutDataLeftColumn.setInsets(new Insets(6, 6, 6, 6));

		if (displayMode == DISPLAY_MODE.SIDE_SCROLL) {
			colHeaderAndTable = new Column();
			headerComponent = new Column();
			colHeaderAndTable.add(headerComponent);
			colHeaderAndTable.add(table);

			splitListAndDetails = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
			splitListAndDetails.setSeparatorPosition(new Extent(230));
			splitListAndDetails.setSeparatorColor(Color.DARKGRAY);
			splitListAndDetails.setSeparatorWidth(new Extent(2));
			splitTopRowAndBody.add(splitListAndDetails);

			colHeaderAndTable.setLayoutData(layoutDataLeftColumn);

			splitListAndDetails.add(colHeaderAndTable);

			splitHeaderAndEditor = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
			splitHeaderAndEditor.setSeparatorPosition(new Extent(30));
			colHeaderContainer = new Column();
			splitHeaderAndEditor.add(colHeaderContainer);

			splitListAndDetails.add(splitHeaderAndEditor);
		} else {
			splitListAndDetails = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
			splitListAndDetails.setSeparatorPosition(new Extent(0));
			splitListAndDetails.setSeparatorWidth(new Extent(0));
			splitTopRowAndBody.add(splitListAndDetails);

			headerComponent = new Column();
			splitListAndDetails.add(headerComponent);

			splitHeaderAndEditor = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
			splitHeaderAndEditor.setSeparatorPosition(new Extent(244));
			splitHeaderAndEditor.setSeparatorColor(Color.DARKGRAY);
			splitHeaderAndEditor.setSeparatorHeight(new Extent(1));
			splitListAndDetails.add(splitHeaderAndEditor);

			table.setWidth(new Extent(100, Extent.PERCENT));
			table.setLayoutData(layoutDataLeftColumn);
			splitHeaderAndEditor.add(table);
		}
		spld = new SplitPaneLayoutData();
		spld.setInsets(new Insets(6));

		editorTab = new PDTab();
		editorTab.setLayoutData(spld);
		splitHeaderAndEditor.add(editorTab);
	}

	protected void initGUI2() {
	}

	public void loadData() {
		loadData(true);
	}

	/*
	 * Override this method if you do not use byCriteria
	 */
	protected void loadData(boolean resetPosition) {
		tableModel2.reloadData(navigationBar.getCurrentPos());
		tableModel2.fireTableDataChanged();
		table.getSelectionModel().setSelectedIndex(0, true);
		if (resetPosition) {
			navigationBar.updateNavigation(tableModel2.getTotalRowCount(), 0);
		}
		readFromModel();
	}

	protected void resetFilter() {
		// resetComponent(sidebar.getGrid());
	}

	private void resetComponent(Component c) {
		if (c instanceof TextField) {
			((TextField) c).setText(""); //$NON-NLS-1$
		} else if (c instanceof SelectField) {
			((SelectField) c).setSelectedIndex(0);
		} else {
			for (Component child : c.getComponents()) {
				resetComponent(child);
			}
		}
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void readFromModel() {
		selectedRow = table.getSelectionModel().getMinSelectedIndex();
		if (colHeaderContainer != null && displayMode == DISPLAY_MODE.SIDE_SCROLL) {
			colHeaderContainer.removeAll();
		}
		if (selectedRow < 0 || tableModel2.getRowCount() == 0) {
			editorTab.setVisible(false);
		} else {
			editorTab.setVisible(true);
			HeaderValue headerValue = (HeaderValue) tableModel2.getValueAt(0, selectedRow);
			int sourceId = tableFactory.getSourceId(headerValue);
			Session session = PDHibernateFactory.getSession();
			for (ICrud<?> editor : editors) {
				MDataLink dataLink = new MDataLink();
				dataLink.setSourceId(sourceId);
				dataLink.setTargetClass(editor.getModelClass().getName());

				if (modelClass.equals(editor.getModelClass())) {
					// no need for a real datalink, its the same model class
					dataLink.setTargetId(sourceId);
				} else {
					Criteria c = session.createCriteria(MDataLink.class);
					c.add(Restrictions.eq("sourceId", dataLink.getSourceId()));
					c.add(Restrictions.eq("targetClass", dataLink.getTargetClass()));
					MDataLink dl = (MDataLink) c.uniqueResult();
					if (dl != null) {
						dataLink = dl;
					} else {
						dataLink.setTargetId(-1);
					}
				}
				editor.readFromModel(dataLink);
			}
			if (displayMode == DISPLAY_MODE.SIDE_SCROLL) {
				colHeaderContainer.add(new Strut(0, 9));
				titleBar = tableFactory.getHeaderComponent(headerValue);
				colHeaderContainer.add(titleBar);
			}
		}
	}

	protected void setFocus() {
		if (editors.size() == 0)
			return;
		Component focusComponent = editors.get(0).getFocusComponent();
		if (focusComponent != null) {
			ApplicationInstance.getActive().setFocusedComponent(focusComponent);
		}
	}

	public void refreshTitle() {
		// if (currentModel instanceof MBaseWithTitle) {
		// ((MBaseWithTitle) currentModel).updateCachedTitle();
		// String newTitle = ((MBaseWithTitle) currentModel).getCachedTitle();
		// titleBar.setTitle(newTitle);
		// tableModel2.fireTableDataChanged();
		// }
		// lblSavingStatus.setLineWrap(!lblSavingStatus.isLineWrap());
	}

	protected void addNewItem(HeaderValue headerValue) {
		tableModel2.addItem(headerValue);
		tableModel2.fireTableRowsInserted(0, 1);
		table.getSelectionModel().setSelectedIndex(0, true);
		navigationBar.updateNavigation(tableModel2.getTotalRowCount(), 0);
		readFromModel();
		if (editors.size() > 0) {
			Component focusComponent = editors.get(0).getFocusComponent();
			if (focusComponent != null) {
				ApplicationInstance.getActive().setFocusedComponent(focusComponent);
			}
		}
	}

	public void addNewItem(MBaseWithTitle item) {
		item.updateCachedValues();
		Object[] rowData = new Object[3];
		rowData[0] = item.getId();
		rowData[1] = item.getCachedTitle();
		rowData[2] = item.getCachedDescription();
		addNewItem(tableFactory.createHeaderValue(rowData));
	}

	@Override
	public void deleteItem() {
		Session session = PDHibernateFactory.getSession();
		try {

			HeaderValue headerValue = (HeaderValue) tableModel2.getValueAt(0, selectedRow);
			int sourceId = tableFactory.getSourceId(headerValue);
			MBase mbase = (MBase) session.load(modelClass, sourceId);
			session.delete(mbase);

			session.flush();
			session.getTransaction().commit();
			loadData();
		} catch (Exception e) {
			session.getTransaction().rollback();
			Object param = null;
			if (e instanceof ConstraintViolationException) {
				try {
					String err = e.getCause().getMessage();
					err = err.substring(err.indexOf("table") + 10); //$NON-NLS-1$
					err = err.substring(0, err.indexOf("caused") - 3); //$NON-NLS-1$
					System.out.println(err);
					param = nls(PDBeanTerms.Dependent_dataset_could_not_be_deleted, err);
				} catch (Exception e2) {
					param = nls(PDBeanTerms.Dependent_dataset_could_not_be_deleted);
				}
			} else {
				param = e.getMessage();
			}
			PDMessageBox.msgBox(StandardTerms.Warning, PDBeanTerms.Item_could_not_be_deleted_reason£$, 320, 160, param);
		} finally {
			session.beginTransaction();
		}
	}

	public void setSelectedRow(int row) {
		table.getSelectionModel().setSelectedIndex(row, true);
		readFromModel();
	}

	protected ContainerEx addEditor(String caption, ICrud<?> editor) {
		ContainerEx tab = editorTab.addTab(caption, (Component) editor);
		editors.add(editor);
		if (editor instanceof IBindable) {
			btnSaveCancel.addBinding(((IBindable) editor).getBinding());
			if (editors.size() <= 1) {
				// just listen to the first tab!
				((IBindable) editor).getBinding().setTitleChangeListener(titleChangeListener);
			}
		}
		return tab;
	}
}