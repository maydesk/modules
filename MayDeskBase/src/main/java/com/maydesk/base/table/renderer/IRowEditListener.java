/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;

/**
 * @author Alejandro Salas
 */
public interface IRowEditListener extends EventListener {

	void btnEditClicked(ActionEvent event);

	void btnUpClicked(ActionEvent event);

	void btnDownClicked(ActionEvent event);

	void btnDeleteClicked(ActionEvent event);
}
