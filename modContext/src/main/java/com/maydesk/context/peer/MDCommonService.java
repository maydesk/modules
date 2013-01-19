package com.maydesk.context.peer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

public class MDCommonService {

	public static final Service[] SERVICES = {
		JavaScriptService.forResource("clazz", "js/draw2d/Class.js"),
		JavaScriptService.forResource("canvg", "js/draw2d/canvg.js"),
		JavaScriptService.forResource("json2", "js/draw2d/json2.js"),
		JavaScriptService.forResource("rgbcolor", "js/draw2d/rgbcolor.js"),
		JavaScriptService.forResource("shifty", "js/draw2d/shifty.js"),
		JavaScriptService.forResource("jq", "js/draw2d/jquery-1.8.1.js"),
//		JavaScriptService.forResource("js/draw2d/jquery-1.8.1.min.js"), //ILLEGAL
		JavaScriptService.forResource("jqtp", "js/draw2d/jquery-touch_punch.js"),
		JavaScriptService.forResource("jqui", "js/draw2d/jquery-ui-1.8.23.custom.min.js"),
		JavaScriptService.forResource("jqar", "js/draw2d/jquery.autoresize.js"),
		JavaScriptService.forResource("jqcm", "js/draw2d/jquery.contextmenu.js"),
		JavaScriptService.forResource("jql", "js/draw2d/jquery.layout.js"),
		JavaScriptService.forResource("raphael", "js/draw2d/raphael.js"),		
		JavaScriptService.forResource("draw2d", "js/draw2d/draw2d.js")		
	};
	
//	private static List<String> files = new ArrayList<String>();
//	public static Service DRAW2D;
//	
//	private static void addFile(File parentFile) {
//		for (File file : parentFile.listFiles()) {
//			if (file.isDirectory()) {
//				addFile(file);
//			} else {
//				String path = file.getAbsolutePath();
//				path = path.substring(path.indexOf("drawJS") - 3);
//				System.out.println(path);
//				files.add(path);
//			}			
//		}		
//	}
	
	
	static {
		for (Service service : SERVICES) {
			WebContainerServlet.getServiceRegistry().add(service);
		}
//		//addFile(new File("js/src"));
//		URL uri = MDCommonResources.class.getClassLoader().getResource("js/drawJS/adraw2d.js");
//		File f = new File(uri.getFile());
//		addFile(f.getParentFile());
//
//		DRAW2D = JavaScriptService.forResources("draw2d", files.toArray(new String[files.size()]));
//		WebContainerServlet.getServiceRegistry().add(DRAW2D);

	}

	private MDCommonService() {
	}
}
