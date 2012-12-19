/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Demian Guitierrez
 */
public class CledaNamingStrategy extends ImprovedNamingStrategy {

	protected Configuration configuration;

	public CledaNamingStrategy(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public String tableName(String tableName) {
		String url = (String) configuration.getProperties().get("hibernate.connection.url");

		// Oracle should use a "username"."tablename" schema or we will have
		// problems with different unrelated databases with common table names
		if (url.contains("oracle")) {
			return configuration.getProperty("hibernate.connection.username") + "." + super.tableName(tableName);
		}

		return super.tableName(tableName);
	}
}