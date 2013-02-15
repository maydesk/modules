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

	private Webcam2 localWebcam;	
	private Hashtable<Webcam2Receiver, PDApplicationInstance> remoteWebcams = new Hashtable<Webcam2Receiver, PDApplicationInstance>();	

	private ApplicationInstance localAppInst;	
	private TaskQueueHandle localTQH;	

	
	public void setWebcamLocal(Webcam2 webcam) {
		localWebcam = webcam;		
		webcam.addStartListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sdp = e.getActionCommand();
				sendSDPToRemotes(sdp);
			}
		});
		localAppInst = ApplicationInstance.getActive();
		localTQH = localAppInst.createTaskQueue();
	}
	
	private void sendSDPToRemotes(final String sdp) {
		for (final Webcam2Receiver remoteWebcam: remoteWebcams.keySet()) {
			PDApplicationInstance appInst = remoteWebcams.get(remoteWebcam);
			appInst.enqueueTask(new Runnable() {
				@Override
				public void run() {
					System.out.println("send SDP to receiver: " + sdp);
					remoteWebcam.setValue(sdp);
				}
			});
		};
	}
	public void addRemoteViewer(final Webcam2Receiver webcam, PDApplicationInstance appInst) {
		remoteWebcams.put(webcam, appInst);
		webcam.addStartListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String desc = ae.getActionCommand();
				System.out.println("received action from sender: " + desc);
				sendAcknowledgmentToLocal(desc);
			}
		});
	}

	private void sendAcknowledgmentToLocal(final String value) {
		localAppInst.enqueueTask(localTQH, new Runnable() {
			@Override
			public void run() {
				System.out.println("sending ack! to sender:" + value);
				localWebcam.setValue(value);
			}
		});
	}

}