package com.maydesk.web;

import com.maydesk.base.PDMenuProvider;
import com.maydesk.base.PDServlet;
import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public class MDServlet extends PDServlet {

	@Override
	protected ILookAndFeel getLookAndFeel() {
		return new MDLookAndFeel();
	}

	@Override
	protected PDMenuProvider getPerspective() {
		PDUtil.initConfig(super.getServletContext());
		MDInit.resetAll();

		return new MDPerspective();
	}
}