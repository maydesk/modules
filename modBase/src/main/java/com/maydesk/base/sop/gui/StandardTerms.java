/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.sop.gui;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = Translatable.class)
public enum StandardTerms {

	@Soplet(textEN = "Back", textDE = "Zurück")
	Back,

	/**
	 * @ english Cancel @ german Abbrechen @ context Standard Cancel button for
	 * modal dialogs; Cancel means the dialog is closed without considering
	 * changes might have done Always to be used in combination with BTN.OK
	 * 
	 * @comment Do not use for non-dialog windows, use BTN.Close instead See
	 *          also BTN.Abort for comparison
	 * @usability If you consider important data might got lost when applying
	 *            this button then you should ask for user confirmation (see:
	 *            MSG.Data_have_changed_really_cancel) Please make sure the
	 *            buttons are placed in this order (if applicable):
	 *            [Save/Apply]- [OK]- [Cancel]
	 * @hotkey &-letter or [Esc] button
	 */
	@Soplet(textEN = "Cancel", textDE = "Abbrechen")
	Cancel,

	@Soplet(textEN = "Close", textDE = "Schliessen")
	Close,

	@Soplet(textEN = "Context")
	Context,

	@Soplet(textDE = "Beschreibung", textEN = "Description")
	Description,

	@Soplet(textDE = "Fertig", textEN = "Done")
	Done,

	@Soplet(textDE = " ", textEN = " ")
	EMPTY,

	@Soplet(textDE = "Filter", textEN = "Filter")
	Filter,

	@Soplet(textEN = "Finish", textDE = "Beenden")
	Finish,

	@Soplet(textEN = "Login", textDE = "Login")
	Login,

	@Soplet(textDE = "Nachricht", textEN = "Message")
	Message,

	@Soplet(textDE = "Weiter", textEN = "Next")
	Next,

	@Soplet(textDE = "Kein Eintrag ausgewählt!", textEN = "No items selected!")
	No_items_selected,

	@Soplet(textEN = "OK", textDE = "OK")
	OK,

	@Soplet(textDE = "Passwort", textEN = "Password")
	Password,

	@Soplet(textDE = "Vorschau", textEN = "Preview")
	Preview,

	@Soplet(textDE = "Projekt", textEN = "Project")
	Project,

	@Soplet(textDE = "Betreff", textEN = "Subject")
	Subject,

	@Soplet(textEN = "User", textDE = "Benutzer")
	User,

	@Soplet(textDE = "Benutzer", textEN = "Users")
	Users,

	@Soplet(textEN = "Warning", textDE = "Warnung")
	Warning,

	@Soplet(textDE = "Willkommen", textEN = "Welcome")
	Welcome,

	@Soplet(textDE = "Weiter", textEN = "Continue")
	Continue,

	@Soplet(textDE = "Rolle", textEN = "Role")
	Role,

	@Soplet(textDE = "Lizenz", textEN = "License")
	License,

	@Soplet(textDE = "Wollen Sie die Änderungen speichern?", textEN = "Do you want to save changes?")
	doYouWantToSaveChanges,

	@Soplet(textEN = "Email")
	Email,

	@Soplet(textDE = "Nein", textEN = "No")
	No,

	@Soplet(textEN = "Accept Now!")
	Accept;
}
