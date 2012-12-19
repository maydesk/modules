/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import nextapp.echo.app.Color;

import com.maydesk.base.sop.enums.ECountry;
import com.maydesk.base.sop.enums.SopGender;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_user", uniqueConstraints = @UniqueConstraint(columnNames = { "jabberId" }))
public class MUser extends MBaseWithTitle {

	private Integer colorShade;
	private ECountry country;
	protected String displayName;
	protected String email;
	private SopGender gender;
	protected String jabberId;
	protected Locale locale = Locale.ENGLISH;

	public MUser() {
		// Empty
	}

	@Override
	public String createCachedTitle() {
		return toString();
	}

	public Integer getColorShade() {
		if (colorShade == null) {
			colorShade = Color.BLUE.getRgb();
		}
		return colorShade;
	}

	@Enumerated(javax.persistence.EnumType.STRING)
	public ECountry getCountry() {
		return country;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	@Enumerated(EnumType.STRING)
	public SopGender getGender() {
		return gender;
	}

	public String getJabberId() {
		return jabberId;
	}

	@Transient
	public Locale getLocale() {
		return locale;
	}

	public void setColorShade(Integer colorShade) {
		this.colorShade = colorShade;
	}

	public void setCountry(ECountry country) {
		this.country = country;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(SopGender gender) {
		this.gender = gender;
	}

	public void setJabberId(String jabberId) {
		this.jabberId = jabberId;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		if (!PDUtil.isEmpty(displayName)) {
			return displayName;
		}
		return jabberId;
	}
}