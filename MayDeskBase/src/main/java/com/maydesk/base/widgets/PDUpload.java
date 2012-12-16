package com.maydesk.base.widgets;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.filetransfer.app.UploadSelect;
import nextapp.echo.filetransfer.app.event.UploadEvent;
import nextapp.echo.filetransfer.app.event.UploadListener;
import nextapp.echo.filetransfer.model.Upload;

import com.maydesk.base.gui.PDMessageBox;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;

import echopoint.ContainerEx;
import echopoint.able.Scrollable;

public class PDUpload extends ContainerEx {

	private UploadSelect uploadSelect;
	private TaskQueueHandle taskQueueHandle;
	private ApplicationInstance appInstance;
	private MMediaFile file;
	private List<ActionListener> listeners = new Vector<ActionListener>();
	
	public PDUpload() {
		appInstance = ApplicationInstance.getActive();
		taskQueueHandle = appInstance.createTaskQueue();
		
		initGUI();
	}
	
	private void initGUI() {
		uploadSelect = new UploadSelect();
		uploadSelect.setLocale(Locale.ENGLISH);
		
		uploadSelect.addUploadListener(new UploadListener() {
			public void uploadComplete(UploadEvent e) {
				appInstance.enqueueTask(taskQueueHandle, new MyUploadFinished(e));
			}
		});
		add(uploadSelect);
	}

	class MyUploadFinished implements Runnable {
		private Upload upload;
		
		MyUploadFinished(UploadEvent uploadEvent) {
			upload = uploadEvent.getUpload();
		}
			
		public void run() {
			try {
				if (!isValidFile(upload.getFileName())) {
					PDMessageBox.msgBox(StandardTerms.Warning, PDBeanTerms.Invalid_file_format, 210, 110);
					removeAll();
					initGUI();
					return;
				}
				byte[] b = new byte[(int)upload.getSize()];

				file = null;
				try {
					upload.getInputStream().read(b, 0, (int)upload.getSize());
					file = new MMediaFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (file == null) return;
				
				file.setFileBytes(b);
				file.setFileName(upload.getFileName());
				file.setContentType(upload.getContentType());
				file.setFileSize(upload.getSize());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (upload.getInputStream() != null) {
						upload.getInputStream().close();
					}
	            } catch (IOException e) {
		            e.printStackTrace();
	            }	
	            removeAll();
	            initGUI();
			}
			
			ActionEvent e = new ActionEvent(this, "");
			for (ActionListener l : listeners) {
				l.actionPerformed(e);
			}
		}
	}

	public MMediaFile getFile() {
		return file;
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
    }

	protected boolean isValidFile(String fileName) {
		return true;
	}
}
