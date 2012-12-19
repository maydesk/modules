package com.maydesk.base.table;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDLookAndFeel;

import echopoint.Strut;

/**
 * @author Alejandro Salas
 */
public class PDNavigationBar extends Row {

	protected Label lblPosition;
	protected Button btnFirst;
	protected Button btnPrevious;
	protected Button btnNext;
	protected Button btnLast;
	private int currentPage;
	private int rowsPerPage;
	private int totalRowCount;

	private int maxPage;
	private List<ActionListener> listeners = new Vector<ActionListener>();

	public PDNavigationBar(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		initGUI();
	}

	public void moveFirst() {
		updateNavigation(totalRowCount, 0);
		fireAction();
	}

	public void moveNext() {
		currentPage++;
		if (currentPage > maxPage) {
			currentPage = maxPage;
		}
		updateNavigation(totalRowCount, currentPage);
		fireAction();
	}

	private void initGUI() {
		setCellSpacing(new Extent(6));
		add(new Strut(6, 0));

		btnFirst = new Button(EImage16.first_white.getImage());
		btnFirst.setDisabledIcon(EImage16.first_grey.getImage());
		btnFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveFirst();
			}
		});
		add(btnFirst);

		btnPrevious = new Button(EImage16.previous_white.getImage());
		btnPrevious.setDisabledIcon(EImage16.previous_grey.getImage());
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPage--;
				if (currentPage < 0)
					currentPage = 0;
				updateNavigation(totalRowCount, currentPage);
				fireAction();
			}
		});
		add(btnPrevious);

		// add(new Strut(6, 0));

		lblPosition = new Label("");
		lblPosition.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, new Extent(11)));
		lblPosition.setForeground(Color.WHITE);
		lblPosition.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		lblPosition.setLineWrap(false);
		add(lblPosition);

		// add(new Strut(6, 0));

		btnNext = new Button(EImage16.next_white.getImage());
		btnNext.setDisabledIcon(EImage16.next_grey.getImage());
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveNext();
			}
		});
		add(btnNext);

		btnLast = new Button(EImage16.last_white.getImage());
		btnLast.setDisabledIcon(EImage16.last_grey.getImage());
		btnLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateNavigation(totalRowCount, maxPage);
				fireAction();
			}
		});
		add(btnLast);
	}

	public void updateNavigation(int totalRowCount, int currentPage) {
		this.totalRowCount = totalRowCount;
		this.currentPage = currentPage;
		maxPage = (totalRowCount - 1) / rowsPerPage;
		if (totalRowCount <= rowsPerPage) {
			btnFirst.setVisible(false);
			btnPrevious.setVisible(false);
			btnNext.setVisible(false);
			btnLast.setVisible(false);
		} else {
			btnFirst.setVisible(true);
			btnPrevious.setVisible(true);
			btnNext.setVisible(true);
			btnLast.setVisible(true);

			btnFirst.setEnabled(currentPage > 0);
			btnPrevious.setEnabled(currentPage > 0);
			boolean hasMore = currentPage < maxPage;
			btnNext.setEnabled(hasMore);
			btnLast.setEnabled(hasMore);
		}
		updateNavigationLabel();
	}

	private void fireAction() {
		ActionEvent e = new ActionEvent(this, "");
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	public void updateNavigationLabel() {
		String s = "";
		if (totalRowCount == 0) {
			s = "(No data)";
		} else {
			s = "Page " + (currentPage + 1) + " / " + (maxPage + 1);
		}
		lblPosition.setText(s);
	}

	public int getCurrentPos() {
		return currentPage * rowsPerPage;
	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}
}
