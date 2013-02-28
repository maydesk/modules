package com.maydesk.context;

import java.util.HashMap;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDArrow;
import com.maydesk.context.widget.MDAvatar;
import com.maydesk.context.widget.MDCanvas;
import com.maydesk.context.widget.MDImage;
import com.maydesk.context.widget.MDNewsBox;
import com.maydesk.context.widget.MDNewsTicker;
import com.maydesk.context.widget.MDRectangle;
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

		MDText text = new MDText(30, 45, "Reception", 1, 20);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);

		MDImage img = new MDImage(30, 115, 240, 183, "img/demo/receptionist_full.jpg");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		text = new MDText(340, 45, "Welcome to Barco Karlsruhe R&D", 1, 58);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		text = new MDText(20, 335, "Press here for info!", 2, 24);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		img = new MDImage(160, 490, 520, 362, "img/demo/barco_untitled-3.gif");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		MDNewsTicker newsTicker = new MDNewsTicker();
		newsTicker.setId(BoardManager.getNextId());
		newsTicker.setPositionX(60);
		newsTicker.setPositionY(970);
		newsTicker.setText("Aquariva presentation at AV Fair Amsterdam was a big success!");
		newsTicker.setAuthor("Robert, 2 hours ago");
		demoCanvas.add(newsTicker);
		
		
		text = new MDText(1110, 190, "Community Area", 1, 22);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
		
		MDRectangle rect = new MDRectangle();
		rect.setPositionX(950);
		rect.setPositionY(250);
		rect.setWidth(new Extent(600));
		rect.setHeight(new Extent(750));
		rect.setBackground(Color.WHITE);
		rect.setAlpha(0.3);
		rect.setId(BoardManager.getNextId());
		demoCanvas.add(rect);
		
		MDAvatar avatar = new MDAvatar();
		avatar.setId(BoardManager.getNextId());
		avatar.setPositionX(1200);
		avatar.setPositionY(500);
		avatar.setText("I have birthday today! join breakfast at 9 o'clock at the tee kitchen");
		avatar.setImage(new ResourceImageReference("img/demo/petra.png"));
		demoCanvas.add(avatar);

		img = new MDImage(1260, 510, 140, 100, "img/demo/birthday.jpg");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		img = new MDImage(1200, 850, 260, 90, "img/demo/dilbert.png");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		
		MDArrow arrow = new MDArrow(1180, 900, -80, -50, 16);
		arrow.setId(BoardManager.getNextId());
		demoCanvas.add(arrow);
		
		avatar = new MDAvatar();
		avatar.setId(BoardManager.getNextId());
		avatar.setPositionX(1050);
		avatar.setPositionY(790);
		avatar.setText("why 'Joke'? - we call that 'Release Planning' ... ;)");
		avatar.setImage(new ResourceImageReference("img/demo/alex.png"));
		demoCanvas.add(avatar);
		
		text = new MDText(1560, 190, "What's up at Barco?", 1, 22);
		text.setId(BoardManager.getNextId());
		demoCanvas.add(text);
				
		MDNewsBox newsBox = new MDNewsBox(1640, 255, 200, 120, "img/demo/dept_manag.png", "Management", "Working on the next generation random buzzword generator");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(1640, 385, 200, 120, "img/demo/dept_tech.png", "Research", "Pushing hard for MVD release, targeting mid of February for delivery");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(1640, 515, 200, 120, "img/demo/dept_sales.png", "Sales", "We are receiving many pre-orders for 2x2 MVD panels, so lets just get them delivered");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		newsBox = new MDNewsBox(1640, 645, 200, 120, "img/demo/dept_dev.png", "Service", "The liquid cooling problem seems to be under control - thanks God!");
		newsBox.setId(BoardManager.getNextId());
		demoCanvas.add(newsBox);
		
		img = new MDImage(1701, 860, 209, 210, "img/demo/barco.png");
		img.setId(BoardManager.getNextId());
		demoCanvas.add(img);
		

////
////		WebcamReceiver webcam = new WebcamReceiver();
////		webcam.setPositionX(10);
////		webcam.setPositionY(50);
////		TEST_SINGLETON_CANVAS.add(webcam);
//

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