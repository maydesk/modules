/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.ContainerContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;

import com.maydesk.base.MDServlet;
import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDHibernateMasterFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDMessageBox;

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
public class AspectExceptionHandler {

	@Around(value = "execution(* actionPerformed(..))")
	public void handleUnexpectedExceptionInActionListeners(ProceedingJoinPoint point) throws Throwable {
		try {
			long time = System.currentTimeMillis();
			point.proceed();
			time = System.currentTimeMillis() - time;
			if (time > 3000) {
				String msg = "Long operation " + time + ": " + PDUserSession.getInstance().getUser() + " at " + point;
				System.out.println(msg);
			}
		} catch (Exception e) {
			try {
				e.printStackTrace();

				// construct the message text
				String stage = "";
				if (PDDesktop.getInstance() != null) {
					stage = PDApplicationInstance.getActivePD().getEnvironment();
				}

				String msg = "An error has occurred: \n\n";
				msg += "User: " + PDUserSession.getInstance().getUser() + "\n";
				msg += "Stage: " + stage + "\n\n";
				msg += "Error: " + ExceptionUtils.getStackTrace(e);

				// send an email
				try {
					MDMailUtil.sendMail("mail@chrismay.de", "Error in MayDesk " + stage, msg, null);
				} catch (Exception ex) {
					System.out.println("Mail not configured!");
					ex.printStackTrace();
				}

				// show error dialog
				if (PDDesktop.getInstance() != null) {
					String msg2 = "An unexpected programm error occurred!\n\n";
					msg2 += "Error type: " + e.getMessage() + "\n\n";
					msg2 += "An email has been sent automatically to the admin; we will fix the error as soon as possible - sorry for the inconvenience";
					PDMessageBox.msgBox("Unexpected Error", msg2, 360, 240);
				}

				// rollback hibernate transaction
				ContainerContext context = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
				PDHibernateMasterFactory factory = (PDHibernateMasterFactory) context.getSession().getAttribute(MDServlet.HIBERNATE_FACTORY);
				if (factory.hasOpenSession()) {
					Session session = PDHibernateFactory.getSession();
					session.getTransaction().rollback();
					session.close();
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}
}
