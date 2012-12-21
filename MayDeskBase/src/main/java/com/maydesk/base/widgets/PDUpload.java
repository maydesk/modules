/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import nextapp.echo.app.ApplicationInstance;
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

/**
 * @author Alejandro Salas
 */
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
			@Override
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

		@Override
		public void run() {
			try {
				if (!isValidFile(upload.getFileName())) {
					PDMessageBox.msgBox(StandardTerms.Warning, PDBeanTerms.Invalid_file_format, 210, 110);
					removeAll();
					initGUI();
					return;
				}
				byte[] b = new byte[(int) upload.getSize()];

				file = null;
				try {
					upload.getInputStream().read(b, 0, (int) upload.getSize());
					file = new MMediaFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (file == null)
					return;

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
