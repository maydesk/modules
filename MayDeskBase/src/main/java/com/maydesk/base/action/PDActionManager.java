package com.maydesk.base.action;

import java.util.List;
import java.util.Vector;

import com.maydesk.base.model.MAction;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;


public class PDActionManager {

	private static PDActionManager INSTANCE;

	private List<MAction> actions = new Vector<MAction>();
	private int currentPosition = -1;
	private List<ActionListener> listeners = new Vector<ActionListener>();
	
	public static PDActionManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PDActionManager(); 
		}
		return INSTANCE;
	}
	
	private PDActionManager() {		
	}

	public void undo() {		
		MAction action = actions.get(currentPosition--);
		action.undoAction();
		fireChange();
    }
	
	public void redo() {		
		MAction action = actions.get(++currentPosition);
		action.redoAction();
		fireChange();
    }

	public void addAction(MAction action) {
		actions.add(action);
		currentPosition = actions.size() - 1;
		fireChange();
//		PDHibernateFactory.getSession().saveOrUpdate(action);
    }

	public void addListener(ActionListener listener) {
	    listeners.add(listener);	    
    }
	
	private void fireChange() {
		ActionEvent e = new ActionEvent(this, ""); //$NON-NLS-1$
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}
	
	public boolean hasNext() {
		return currentPosition < actions.size() - 1;
	}

	public boolean hasPrevious() {
		return actions.size() > 0 && currentPosition >= 0;
	}

	public String getUndoText() {
	    if (hasPrevious()) {
	    	return actions.get(currentPosition) + ""; //$NON-NLS-1$
	    }
	    return null;
    }

	public String getRedoText() {
	    if (hasNext()) {
	    	return actions.get(currentPosition + 1) + ""; //$NON-NLS-1$
	    }
	    return null;
    }

}
