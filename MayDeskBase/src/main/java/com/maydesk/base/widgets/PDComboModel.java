/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import nextapp.echo.app.list.DefaultListModel;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.SopletsResourceBundle;

/**
 * @author Alejandro Salas
 */
public class PDComboModel<T> extends DefaultListModel {

	public PDComboModel() {
	}

	public PDComboModel(T[] values) {
		super(values);
	}

	public void add(String caption, T value) {
		MyListEntry entry = new MyListEntry();
		entry.caption = caption;
		entry.value = value;
		super.add(entry);
	}

	@Override
	public void add(Object o) {
		if (o == null) {
			return;
		}
		MyListEntry entry = new MyListEntry();
		if (o instanceof String) {
			entry.value = null;
			entry.caption = o.toString();
		} else {
			entry.value = (T) o;
			entry.caption = entry.value.toString();
		}
		super.add(entry);
	}

	class MyListEntry implements Comparable<MyListEntry> {
		T value;
		String caption;

		@Override
		public String toString() {
			return caption;
		}

		@Override
		public int compareTo(MyListEntry other) {
			if (caption == null)
				return 0;
			return caption.compareTo(other.caption);
		}
	}

	public MyListEntry getEntryIndex(Object item) {
		if (item != null) {
			for (int i = 0; i < size(); i++) {
				MyListEntry thisEntry = (MyListEntry) get(i);
				if (item.equals(thisEntry.value)) {
					return thisEntry;
				}
			}
		}
		return null;
	}

	public void setValues(List<T> allValues, boolean doSort) {
		setValues(allValues, null, doSort);
	}

	public void setValues(List<T> allValues, String emptyEntry, boolean doSort) {
		removeAll();
		if (emptyEntry != null) {
			add(emptyEntry + " ", null);
		}
		List<MyListEntry> sortedEntries = new Vector<MyListEntry>();
		for (T value : allValues) {
			MyListEntry entry = new MyListEntry();
			entry.value = value;
			if (value instanceof Translatable) {
				Translatable translatable = (Translatable) value;
				entry.caption = SopletsResourceBundle.nls(translatable);
			} else {
				entry.caption = entry.value.toString();
			}
			sortedEntries.add(entry);
		}
		if (doSort) {
			Collections.sort(sortedEntries);
		}
		for (MyListEntry entry : sortedEntries) {
			super.add(entry);
		}
	}
}
