package com.maydesk.base.sop.logical;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.enums.SopGender;


@Sop(aspects={Translatable.class, Beanable.class})
public enum SopUser {
	 
	@Soplet( 
		textDE = "Ort", 
		textEN = "City")
	city, 
		
	@Soplet( 
		textDE = "Firma", 
		textEN = "Company")
	company, 
		
	@Soplet( 
		textEN = "Country", 
		textDE = "Land")
	country, 
		
	@Soplet( 
		textDE = "Email", 
		textEN = "Email")
	email, 
		
	@Soplet( 
		textDE = "Fax", 
		textEN = "Fax")
	fax, 
	
	@Soplet( 
		textDE = "Jabber ID", 
		textEN = "Jabber ID")
	jabberId, 
		
	@Soplet( 
		textDE = "Vorname", 
		textEN = "First name")
	firstName, 
		
	@Soplet( 
		textDE = "Nachname", 
		textEN = "Last name")
	lastName, 

	@Soplet( 
		textDE = "Geschlecht", 
		textEN = "Gender",
		javaType = SopGender.class)
	gender, 

	@Soplet( 
		textDE = "Login", 
		textEN = "Login")
	login, 
		
	@Soplet( 
		textDE = "Telefon", 
		textEN = "Phone")
	phone, 
		
	@Soplet( 
		textDE = "Strasse", 
		textEN = "Street")
	street, 
		
	@Soplet( 
		textEN = "Zip code", 
		textDE = "PLZ")
	zipCode,
	
	@Soplet( 
		textEN = "Organization")
	organization;
}
