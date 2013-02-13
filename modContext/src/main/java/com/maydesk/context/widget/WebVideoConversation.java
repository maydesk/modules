package com.maydesk.context.widget;

import java.util.Hashtable;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDApplicationInstance;

public class WebVideoConversation {

	private static WebVideoConversation INSTANCE;
	
	public static WebVideoConversation getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new WebVideoConversation();
		}
		return INSTANCE;
	}

	private WebcamSender localWebcam;	
	private Hashtable<WebcamReceiver, PDApplicationInstance> remoteWebcams = new Hashtable<WebcamReceiver, PDApplicationInstance>();	

	private ApplicationInstance localAppInst;	
	private TaskQueueHandle localTQH;	

	
	public void setWebcamLocal(WebcamSender webcam) {
		localWebcam = webcam;		
		webcam.addStartListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sdp = e.getActionCommand();
				sendSDPToRemotes(sdp);
			}
		});
		webcam.addCandidateListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String candidate = e.getActionCommand();
				sendCandidateToRemotes(candidate);
			}
		});
		localAppInst = ApplicationInstance.getActive();
		localTQH = localAppInst.createTaskQueue();
	}
	
	public void addRemoteViewer(final WebcamReceiver webcam) {
		remoteWebcams.put(webcam, (PDApplicationInstance)ApplicationInstance.getActive());
		webcam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String desc = ae.getActionCommand();
				System.out.println("received action from remote: " + desc);
				sendAcknowledgmentToLocal(desc);
			}
		});
		webcam.addCandidateListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String candidate = e.getActionCommand();
				sendCandidateToLocal(candidate);
			}
		});
	}

	public void sendCandidateToLocal(final String value) {
		localAppInst.enqueueTask(localTQH, new Runnable() {
			@Override
			public void run() {
				System.out.println("sending cand to local:" + value);
				localWebcam.setValue(value);
			}
		});
	}

	public void sendAcknowledgmentToLocal(final String value) {
		localAppInst.enqueueTask(localTQH, new Runnable() {
			@Override
			public void run() {
				System.out.println("sending ack! to local:" + value);
				localWebcam.setValue(value);
			}
		});
	}
	
	public void sendCandidateToRemotes(final String candidate) {
		for (final WebcamReceiver remoteWebcam: remoteWebcams.keySet()) {
			PDApplicationInstance appInst = remoteWebcams.get(remoteWebcam);
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					System.out.println("send CAND to remote: " + candidate);
					remoteWebcam.setSignalCandidate(candidate);
				}
			});			
		}
	}	
	
	public void sendSDPToRemotes(final String sdp) {
		for (final WebcamReceiver remoteWebcam: remoteWebcams.keySet()) {
			PDApplicationInstance appInst = remoteWebcams.get(remoteWebcam);
			System.out.println("enque SDP");
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					System.out.println("send SDP to remote: " + sdp);
					remoteWebcam.setSignalSdp(sdp);
				}
			});
		};
	}
}