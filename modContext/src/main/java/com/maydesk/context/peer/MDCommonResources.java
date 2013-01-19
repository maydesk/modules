package com.maydesk.context.peer;

import nextapp.echo.webcontainer.WebContainerServlet;

public class MDCommonResources {

	private static boolean installed = false;

	/** Method that must be invoked by all component peers. */
	public static void install() {
		if (!installed) {
			WebContainerServlet.getResourceRegistry().addPackage("MD", "resource/");
			installed = true;
		}
	}

	private MDCommonResources() {
	}
}
