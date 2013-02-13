package com.maydesk.context.widget;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;

public class WebcamReceiver extends Component implements IPlugTarget {
	
	public static final String PROPERTY_SIGNAL_SDP = "signalSdp";
	public static final String PROPERTY_SIGNAL_CANDIDATE = "signalCandidate";
	public static final String ACTION_ON_CONNECT = "onConnect";
	public static final String ACTION_ON_CANDIDATE = "onCandidate";
	
	private List<ActionListener> actionListeners = new ArrayList<ActionListener>();
	private List<ActionListener> candidateListeners = new ArrayList<ActionListener>();
	
	public void setSignalSdp(String value) {
		set(PROPERTY_SIGNAL_SDP, value);
	}

	public void setSignalCandidate(String value) {
		set(PROPERTY_SIGNAL_CANDIDATE, value);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (ACTION_ON_CONNECT.equals(inputName)) {
			ActionEvent e = new ActionEvent(this,  inputValue + "");
			for (ActionListener actionListener : actionListeners) {
				actionListener.actionPerformed(e);
			}
		} else if (ACTION_ON_CANDIDATE.equals(inputName)) {
			ActionEvent e = new ActionEvent(this,  inputValue + "");
			for (ActionListener actionListener : candidateListeners) {
				actionListener.actionPerformed(e);
			}
		}
	}
	
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	

	public void addCandidateListener(ActionListener actionListener) {
		candidateListeners.add(actionListener);
	}

	@Override
	public void initWire(XmlBaseEntry parentWire) {
		WebVideoConversation.getInstance().addRemoteViewer(this);

	}
}

