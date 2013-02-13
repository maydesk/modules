package com.maydesk.context.widget;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;

public class WebcamSender extends Component implements IPlugTarget {
	
	public static final String PROPERTY_VALUE = "value";
	public static final String ACTION_START_SENDING = "startsending";
	public static final String ACTION_ON_CANDIDATE = "onCandidate";
	public static final String ACTION_STOP_SENDING = "stopsending";
	
	private List<ActionListener> startListeners = new ArrayList<ActionListener>();
	private List<ActionListener> candidateListeners = new ArrayList<ActionListener>();

	
	public String getValue() {
		return (String)get(PROPERTY_VALUE);
	}	
	
	@Override
	public void processInput(String inputName, Object inputValue) {
		if (ACTION_START_SENDING.equals(inputName)) {
			ActionEvent e = new ActionEvent(this,  inputValue + "");
			for (ActionListener actionListener : startListeners) {
				actionListener.actionPerformed(e);
			}
		} else if (ACTION_ON_CANDIDATE.equals(inputName)) {
			ActionEvent e = new ActionEvent(this,  inputValue + "");
			for (ActionListener actionListener : candidateListeners) {
				actionListener.actionPerformed(e);
			}
		} else if (PROPERTY_VALUE.equals(inputName)) {
			set(PROPERTY_VALUE, inputValue);
		}
	}
	
	public void addStartListener(ActionListener actionListener) {
		startListeners.add(actionListener);
	}

	public void addCandidateListener(ActionListener actionListener) {
		candidateListeners.add(actionListener);
	}

	public void setValue(String value) {
		set(PROPERTY_VALUE, value);		
	}

	@Override
	public void initWire(XmlBaseEntry parentWire) {
		WebVideoConversation.getInstance().setWebcamLocal(this);
		
	}
}

