/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.action;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.model.MAction;

/**
 * @author chrismay
 */
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
		// PDHibernateFactory.getSession().saveOrUpdate(action);
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
