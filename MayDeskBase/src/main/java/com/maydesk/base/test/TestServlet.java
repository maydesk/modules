package com.maydesk.base.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.webcontainer.WebContainerServlet;
import echopoint.jquery.DateField;

public class TestServlet extends WebContainerServlet {
	
	@Override
	public ApplicationInstance newApplicationInstance() {
	    
		return new ApplicationInstance() {

			@Override
            public Window init() {
				Window w = new Window();
				
				final ContentPane contentPane = new ContentPane(){
				    public boolean isValidChild(Component child) {				    	
				    	//work-around, otherwise it would not allow just one child 
				    	return true;			 
				    }
				};
				w.setContent(contentPane);
				
				Column col = new Column();
				col.setCellSpacing(new Extent(12));
				contentPane.add(col);
		
//				Group group = new Group();
//				group.setTitle("Hallo Group");
//				group.setInsets(new Insets(11));
//				group.setForeground(Color.DARKGRAY);
//				group.setBackground(Color.LIGHTGRAY);
//				col.add(group);
//				
//				PDTextField txt = new PDTextField();
//				group.add(txt);
//
//				group.add(new Strut(10, 10));
//				
//				txt = new PDTextField();
//				txt.setText("333");
//				group.add(txt);
//
//				txt = new PDTextField();
//				group.add(txt);
//				
//				MShortcut model = new MShortcut("Hello World") {
//					@Override
//                    protected void executeTaskInternal() {
//						WindowPane wp = new WindowPane();
//						wp.setTitle("Hello World");
//						contentPane.add(wp);
//                    }					
//				};
//				model.setPositionX(200);
//				model.setPositionY(200);
//				PDShortcut shortcut = new PDShortcut(model, "img/shortcut1.gif");
//				contentPane.add(shortcut);
//				

				final Label lbl1 = new Label();
				lbl1.setText("111");
				col.add(lbl1);
				
				
				final DateField df = new DateField();
				df.setWidth(new Extent(200));
				df.setHeight(new Extent(20));
				df.addPropertyChangeListener(new PropertyChangeListener(){
					public void propertyChange(PropertyChangeEvent arg0) {
						System.out.println("sss");
                    }					
				});
				df.addPropertyChangeListener(DateField.DATE_CHANGED_PROPERTY, new PropertyChangeListener(){
					public void propertyChange(PropertyChangeEvent arg0) {
						System.out.println("ssees");
                    }					
				});
				col.add(df);

				
//				final PDStatusLabel lbl = new PDStatusLabel();
//				lbl.setToolTipText("TTT");
//				col.add(lbl);
//
				final Button btn = new Button("Push");
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
	                    lbl1.setText(df.getDateStr());
                    }					
				});
				col.add(btn);
//				
				
	            return w;
            }
		};
	}
}
