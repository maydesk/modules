package com.maydesk.base.sop.gui;

import com.maydesk.base.aspects.Translatable;

import lombok.soplets.Sop;


@Sop(aspects=Translatable.class)
public enum PDBeanTerms { 

	@Soplet(
		textEN = "Action")
	Action,
	
	@Soplet(
		textEN = "Add Friend/collegue")
	Add_friend,

	@Soplet(
		textEN = "Add role")
	Add_role,

	@Soplet(
		textEN = "Alias Login")
	Alias_Login,

	@Soplet(
		textEN = "All reachable users")
	All_reachable_users,

	@Soplet(
		textDE = "Zuweisen!",
		textEN = "Assign!")
	Assign£,

	@Soplet(
		textDE = "User zuordnen",
		textEN = "Assign_to_user")
	Assign_to_user,

	@Soplet(
		textEN = "Assign task")
	Assign_task,

	@Soplet(
		textEN = "Assign this task to a user")
	Assign_this_task_to_a_user,

	@Soplet(
		textEN = "Auth",
		textDE = "Auth")
	auth,

	@Soplet(
		textEN = "By related project")
	By_related_project,

	@Soplet(
		textEN = "Change password",
		textDE = "Passwort Ändern")
	Change_password,

	@Soplet(
		textEN = "Debug:",
		textDE = "Debug:")
	Debug£,

	@Soplet(
		textEN = "Default")
	Default,

	@Soplet(
		textDE = "Eintrag löschen",
		textEN = "Delete item")
	Delete_item,

	@Soplet(
		textEN = "Dependent dataset could not be deleted")
	Dependent_dataset_could_not_be_deleted,

	@Soplet(
		textEN = "Dependent dataset in table {0} could not be deleted")
	Dependent_dataset_in_table_$0_could_not_be_deleted,

	@Soplet(
		textDE = "Notiz bearbeiten",
		textEN = "Edit notice")
	Edit_notice,

	@Soplet(
		textEN = "(empty)")
	empty£,

	@Soplet(
		textEN = "Enable:",
		textDE = "Aktiv:")
	Enable£,

	@Soplet(
		textDE = "Fehler",
		textEN = "Error")
	Error,

	@Soplet(
		textEN = "Fill-in for")
	Fill_in_for,

	@Soplet(
		textEN = "First name")
	First_name,

	@Soplet(
		textDE = "Allgemeine Info über diese Aufgabe",
		textEN = "General info about this task")
	General_info_about_this_task,

	@Soplet(
		textEN = "Hello")
	Hello,

	@Soplet(
		textEN = "Image Viewer")
	Image_Viewer,

	@Soplet(
		textEN = "Information",
		textDE = "Information")
	information,

	@Soplet(
		textDE = "Ungültiges Dateiformat",
		textEN = "Invalid file format")
	Invalid_file_format,

	@Soplet(
		textEN = "Invitation code")
	Invitation_code,

	@Soplet(
		textEN = "Item could not be deleted. Reason: \r\n\r\n{0}")
	Item_could_not_be_deleted_reason£$,

	@Soplet(
		textEN = "Last name")
	Last_name,

	@Soplet(
		textDE = "Login",
		textEN = "Login")
	Login,

	@Soplet(
		textDE = "Ein User mit dieser Email ist bereits registriert!", 
		textEN = "A user with this email is already registered!")
	A_user_with_this_email_is_already_registered,

	@Soplet(
		textEN = "Login:     ")
	Login$,

	@Soplet(
		textDE = "Passwort:  ",
		textEN = "Password:  ")
	Password$,
		
	@Soplet(
		textEN = "Mail Setup",
		textDE = "Mail-Einstellungen")
	Mail_Setup,

	@Soplet(
		textEN = "You must fill all fields in the form",
		textDE = "Bitte füllen Sie alle Felder in dieser Form")
	You_must_fill_all_fields_in_the_form,

	@Soplet(
		textDE = "Neu {0}",
		textEN = "New {0}")
	New_$,

	@Soplet(
		textDE = "Neue RepoID",
		textEN = "New Repo-ID")
	New_RepoID,

	@Soplet(
		textEN = "New password:",
		textDE = "Neues Passwort:")
	New_password,

	@Soplet(
		textDE = "Kein User unter dieser Email-Adresse registriert!", 
		textEN = "No user registered at that email address!")
	No_user_registered_with_this_email_address,

	@Soplet(
		textDE = "(Keine User verfügbar)",
		textEN = "(No users available)")
	No_users_available,

	@Soplet(
		textDE = "Passwort wurde erfolgreich an Ihre Email-Adresse versendet", 
		textEN = "Password has been succesfully sent to your email address")
	Password_has_been_sent_to_your_email_address,

	@Soplet(
		textDE = "Password (wiederholen)",
		textEN = "Password (repeat)")
	Password_repeat,

	@Soplet(
		textEN = "{0} password recovery")
	Password_recovery,

	@Soplet(
		textDE = "Passwörter stimmen nicht überein",
		textEN = "Passwords do not match")
	Passwords_do_not_match,

	@Soplet(
		textDE = "Bitte w�hlen Sie hier ein Username und ein Passwort, mit welchem Sie sich in Zukunft anmelden k�nnen",
		textEN = "Please select a user name and a password")
	Please_select_a_login_and_a_password,

	@Soplet(
		textDE = "Bitte geben Sie ein Passwort an!", 
		textEN = "Please enter a password!")
	Please_select_password,

	@Soplet(
		textDE = "Bitte geben Sie einen Vornamen an!", 
		textEN = "Please enter a first name!")
	Please_specify_a_first_name,

	@Soplet(
		textDE = "Bitte geben Sie einen Nachnamen an!", 
		textEN = "Please enter a last name!")
	Please_specify_a_last_name,

	@Soplet(
		textDE = "Bitte geben Sie ein Login an!", 
		textEN = "Please enter a login!")
	Please_specify_a_login,

	@Soplet(
		textDE = "Bitte geben Sie eine Email-Adresse an",
		textEN = "Please enter an email address")
	Please_specify_an_email,

	@Soplet(
		textDE = "Bitte geben Sie Ihre Email-Adresse oder Login an",
		textEN = "Please enter your email address or your user login")
	Please_specify_your_email_or_login,

	
	@Soplet(
		textEN = "Port",
		textDE = "Port")
	Port,

	@Soplet(
		textEN = " - provided by ")
	Provided_by,

	@Soplet(
		textDE = "Schnellsuche",
		textEN = "Quicksearch")
	Quicksearch,

	@Soplet(
		textEN = "This will delete the entity permamently. Do you wish to proceed?",
		textDE = "Achtung, wollen Sie diesen Eintrag dauerhaft löschen?")
	Really_delete_object,

	@Soplet(
		textDE = "Wollen Sie den Eintrag '{0}' jetzt wirklich löschen?",
		textEN = "Do you really want to delete '{0}'?")
	Really_delete_object_x,

	@Soplet(
		textEN = "Recover Password")
	Recover_Password,

	@Soplet(
		textEN = "Repeat password:",
		textDE = "Passwort wiederholen:")
	Repeat_password,

	@Soplet(
		textDE = "Rolle",
		textEN = "Role")
	Role,

	@Soplet(
		textEN = "  Change  ")
	Change,

	@Soplet(
		textEN = "Search")
	Search,

	@Soplet(
		textEN = "Select User")
	Select_user,

	@Soplet(
		textEN = "Send password")
	Send_password,

	@Soplet(
		textDE = "Servername:",
		textEN = "Server name:")
	Server_name,

	@Soplet(
		textDE = "* = Pflichtfelder",
		textEN = "* = mandatory fields")
	Star_is_mandatory,

	@Soplet(
		textEN = "Start-TLS:",
		textDE = "Start-TLS:")
	Start_TLS,

	@Soplet(
		textDE = "Aufgabe:",
		textEN = "Task:")
	Task,

	@Soplet(
		textDE = "Aufgaben Übersicht",
		textEN = "Task Overview")
	Task_Overview,

	@Soplet(
		textEN = "Task Managment")
	Task_Managment,

	@Soplet(
		textEN = "Tasks")
	Tasks,

	@Soplet(
		textEN = "Unassigned Tasks!")
	Unassigned_Tasks,

	@Soplet(
		textEN = "Sender ID:",
		textDE = "Sender ID:")
	Sender_ID,

	@Soplet(
		textDE = "Benutzer erfolgreich zugewiesen",
		textEN = "Users succesfully assigned")
	Users_succesfully_assigned,

	@Soplet(
		textDE = "Die User wurden erfolgreich zugewiesen",
		textEN = "Users have been succesfully assigned")
	Users_have_been_succesfully_assigned,

	@Soplet(
		textEN = "your login data are as follows:")
	your_login_data_are_as_follows,
	
	@Soplet(
		textEN = "Test recipient email:")
	Test_recipient,
	
	@Soplet(
		textEN = "Invalid email address!")
	Invalid_email_address,
	
	@Soplet(
		textEN = "Please specify a role!")
	Please_specify_a_role,
	
	@Soplet(
		textEN = "SSL")
	SSL,
	
	@Soplet(
		textEN = "From")
	From,
	
	@Soplet(
		textEN = "You must agree to the terms!")
	You_must_agree_to_the_terms,
	
	@Soplet(
		textEN = "Your Jabber-ID")
	Your_Jabber_ID,
	
	@Soplet(
		textEN = "Register now!")
	Register_Now,
	
	@Soplet(
		textEN = "Jabber-ID")
	Jabber_ID, 
	
	@Soplet(
		textEN = "Publish Now!")
	Publish_Now,
	
	@Soplet(
		textEN = "Preview")
	Preview;	
}