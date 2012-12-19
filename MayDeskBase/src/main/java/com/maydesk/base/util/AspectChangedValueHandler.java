/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import nextapp.echo.app.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/*
 * Eclipse:
 * -Daj.weaving.verbose=false 
 * -Dorg.aspectj.weaver.showWeaveInfo=false
 * -javaagent:C:\\libs\\aspectjweaver.jar
 */

/**
 * @author chrismay
 */
@Aspect
public class AspectChangedValueHandler {

	@Around(value = "execution(* storeInputProperty(..))")
	public void handleStoreInputProperty(ProceedingJoinPoint point) throws Throwable {
		point.proceed();
		if (point.getArgs()[1] instanceof IChangeSupportable) {
			IChangeSupportable cs = (IChangeSupportable) point.getArgs()[1];
			String propertyName = (String) point.getArgs()[2];
			System.out.println("XXX " + propertyName + " XXXXXXXXXXXXXXXXXXXXXXXXX");
			if (propertyName.equals(cs.getPropertyName())) {
				Object newInput = point.getArgs()[4];
				((Component) cs).processInput(propertyName, newInput);
				Object newValue = cs.getValue();
				PDBinding changeSupport = cs.getChangeSupport();
				if (changeSupport != null) {
					changeSupport.doChange(cs, newValue);
				}
			}
		}
	}
}
