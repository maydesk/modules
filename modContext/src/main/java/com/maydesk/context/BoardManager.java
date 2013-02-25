package com.maydesk.context;

import java.util.HashMap;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDAvatar;
import com.maydesk.context.widget.MDCanvas;
import com.maydesk.context.widget.MDImage;
import com.maydesk.context.widget.MDRectangle;

public class BoardManager {

	private static BoardManager INSTANCE;
	
	public static BoardManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BoardManager();
		}
		return INSTANCE;
	}
	
	private HashMap<String, MDCanvas> boards = new HashMap<String, MDCanvas>();
	
	private BoardManager(){
		//private constructor
		createDemoBoard();
	}
	
	public MDCanvas getBoard(String boardId, boolean clone) {
		MDCanvas board = boards.get(boardId);
		if (clone) {
			MDCanvas cloneBoard = new MDCanvas();
			for (Component c : board.getComponents()) {
				MDAbstractFigure fig = (MDAbstractFigure)c;
				MDAbstractFigure cloneFig = fig.clone();
				cloneBoard.add(cloneFig);				
			}
			return cloneBoard;
		}
		return board;
	}
	
	private static int id = 1;

	public static String getNextId() {
		return String.valueOf(id++);
	}
	
	/**
	 * just for demo...
	 */
	private void createDemoBoard() {
		MDCanvas demoCanvas = new MDCanvas();
		boards.put("demo1", demoCanvas);
		
		MDAvatar avatar = new MDAvatar();
		avatar.setImage(new ResourceImageReference("img/silhouette-male.gif"));
		avatar.setText("Hello World!");
		avatar.setPositionX(220);
		avatar.setPositionY(260);
		demoCanvas.add(avatar);

		MDRectangle rect = new MDRectangle();
		rect.setId(getNextId());
		rect.setPositionX(200);
		rect.setPositionY(200);
		demoCanvas.add(rect);
//
//		WebcamReceiver webcam = new WebcamReceiver();
//		webcam.setPositionX(10);
//		webcam.setPositionY(50);
//		TEST_SINGLETON_CANVAS.add(webcam);

		MDImage img = new MDImage();
		img.setPositionX(1711);
		img.setPositionY(870);
		img.setImage(new ResourceImageReference("img/barco.png"));
		img.setWidth(new Extent(209));
		img.setHeight(new Extent(210));
		demoCanvas.add(img);
	}
	
	public void addFigure(final MDAbstractFigure figure) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDAbstractFigure externalFig = figure.clone();
				externalFig.setId(figure.getId());
				MDServletExternalContext.CANVAS.add(externalFig);
			}
		});
	}


	public void updateProps(final MDAbstractFigure fig) {
		MDServletExternalContext.runTask(new Runnable() {
			@Override
			public void run() {
				MDAbstractFigure figClone = (MDAbstractFigure) MDServletExternalContext.CANVAS.getComponent(fig.getId());
				if (figClone == null) return;
				fig.syncClone(figClone);
			}
		});
	}
}
