/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

/**
 * 
 */
public interface IOrdered extends Comparable<IOrdered> {

	public int getOrder();

	public void setOrder(int order);
}