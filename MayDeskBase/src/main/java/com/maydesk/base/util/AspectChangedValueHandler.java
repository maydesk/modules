/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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
