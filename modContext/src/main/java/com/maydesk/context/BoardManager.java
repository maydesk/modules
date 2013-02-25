package com.maydesk.context;

import java.util.HashMap;

import nextapp.echo.app.Component;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDArrow;
import com.maydesk.context.widget.MDAvatar;
import com.maydesk.context.widget.MDCanvas;
import com.maydesk.context.widget.MDImage;
import com.maydesk.context.widget.MDNewsBox;
import com.maydesk.context.widget.MDNewsTicker;
import com.maydesk.context.widget.MDText;

public class BoardManager {

	private static BoardManager INSTANCE;
	
	public static BoardManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BoardManager();
		}
		return INSTANCE;
	}
	
	private HashMap<String, BoardInstances> boards = new HashMap<String, BoardInstances>();
	
	private BoardManager(){
		//private constructor
		createDemoBoard();
	}
	
	public MDCanvas createBoard(String boardId, boolean clone, PDApplicationInstance appInst) {
		BoardInstances boardInstances = boards.get(boardId);
		if (boardInstances == null) {
			boardInstances = new BoardInstances(boardId, new MDCanvas());
			boards.put(boardId, boardInstances);
		}
		MDCanvas original = boardInstances.getOriginal();
		if (clone) {
			//create a clone from the original
			MDCanvas cloneBoard = new MDCanvas();
			for (Component c : original.getComponents()) {
				MDAbstractFigure fig = (MDAbstractFigure)c;
				MDAbstractFigure cloneFig = fig.clone();
				cloneBoard.add(cloneFig);				
			}
			boardInstances.addClone(appInst, cloneBoard);
			return cloneBoard;
		}
		return original;
	}
	
	private static int id = 1;

	public static String getNextId() {
		return String.valueOf(id++);
	}
	
	/**
	 * just for demo...
	 */
	private void createDemoBoard() {
		MDCanvas demoCanvas = createBoard("demo1", false, PDApplicationInstance.getActivePD());
		
		MDText text = new MDText(40, 20, "Welcome to Barco Karlsruhe R&D", 1, 40);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		text = new MDText(20, 65, "Joke of the day", 0, 16);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		MDImage img = new MDImage(10, 90, 260, 90, "img/demo/dilbert.png");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		MDArrow arrow = new MDArrow(270, 110, 50, 20, 2);
		arrow.setId(BoardManager.getNextId());
		demoCanvas.add(arrow);
		
		MDAvatar avatar = new MDAvatar();
		avatar.setId(BoardManager.getNextId());
		avatar.setPositionX(310);
		avatar.setPositionY(140);
		avatar.setText("why 'Joke'? - we call that 'Release Planning' ... ;)");
		avatar.setImage(new ResourceImageReference("img/demo/alex.png"));
		demoCanvas.add(avatar);
		
		avatar = new MDAvatar();
		avatar.setId(BoardManager.getNextId());
		avatar.setPositionX(200);
		avatar.setPositionY(300);
		avatar.setText("I have birthday today! join breakfast at 9 o'clock at the tee kitchen");
		avatar.setImage(new ResourceImageReference("img/demo/petra.png"));
		demoCanvas.add(avatar);
		
		img = new MDImage(250, 300, 80, 80, "img/demo/birthday.png");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);

		img = new MDImage(1711, 870, 209, 210, "img/demo/barco.png");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		text = new MDText(860, 170, "Team Activities", 2, 14);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		MDNewsBox newsBox = new MDNewsBox(790, 205, 120, 80, "img/demo/dept_manag.png", "Management", "Working on the next generation random buzzword generator");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(915, 205, 120, 80, "img/demo/dept_manag.png", "Management", "Pushing hard for MVD release, targeting mid of February for delivery");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(790, 290, 120, 80, "img/demo/dept_sales.png", "Sales", "We are receiving many pre-orders for 2x2 MVD panels, so lets just get them delivered");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(915, 290, 120, 80, "img/demo/dept_dev.png", "Service", "The liquid cooling problem seems to be under control - thanks God!");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);

		MDNewsTicker newsTicker = new MDNewsTicker();
		newsTicker.setId(BoardManager.getNextId());
		newsTicker.setPositionX(30);
		newsTicker.setPositionY(670);
		newsTicker.setText("Aquariva presentation at AV Fair Amsterdam was a big success!");
		newsTicker.setAuthor("Robert, 2 hours ago");
		demoCanvas.add(newsTicker);
		
				/*
		{
			positionX: 30,
			positionY: 670,
			text: "Aquariva presentation at AV Fair Amsterdam was a big success!",
			author: "Robert, 2 hours ago"
		})*/
		
//		MDAvatar avatar = new MDAvatar();
//		avatar.setImage(new ResourceImageReference("img/silhouette-male.gif"));
//		avatar.setText("Hello World!");
//		avatar.setPositionX(220);
//		avatar.setPositionY(260);
//		demoCanvas.add(avatar);
//
//		MDRectangle rect = new MDRectangle();
//		rect.setId(getNextId());
//		rect.setPositionX(200);
//		rect.setPositionY(200);
//		demoCanvas.add(rect);
////
////		WebcamReceiver webcam = new WebcamReceiver();
////		webcam.setPositionX(10);
////		webcam.setPositionY(50);
////		TEST_SINGLETON_CANVAS.add(webcam);
//
//		MDImage img = new MDImage();
//		img.setPositionX(1711);
//		img.setPositionY(870);
//		img.setImage(new ResourceImageReference("img/barco.png"));
//		img.setWidth(new Extent(209));
//		img.setHeight(new Extent(210));
//		demoCanvas.add(img);
	}
	
	public void addFigure(final MDAbstractFigure fig) {
		BoardInstances boardInstances = boards.get("demo1");
		boardInstances.addCloneFigure(fig);
	}


	public void updateProps(MDAbstractFigure fig) {
		BoardInstances boardInstances = boards.get("demo1");
		boardInstances.updateCloneProps(fig);
	}
}