/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_media_file")
public class MMediaFile extends MBase {

	private Calendar date;
	private String fileName;
	private String contentType;
	private long fileSize;
	private byte[] data;
	private byte[] previewData;
	private String parentClass;
	private int parentId;

	public MMediaFile() {
	}

	public String getContentType() {
		return contentType;
	}

	public Calendar getDate() {
		return date;
	}

	@Transient
	public String getDateStr() {
		DateFormat timeFormat = new SimpleDateFormat("dd.MM kk:mm:ss");
		String date = timeFormat.format(getDate().getTime());
		return date;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return fileName;
	}

	@Lob
	public byte[] getFileBytes() {
		return data;
	}

	public void setFileBytes(byte[] data) {
		this.data = data;
	}

	@Lob
	public byte[] getPreviewBytes() {
		return previewData;
	}

	public void setPreviewBytes(byte[] data) {
		this.previewData = data;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}