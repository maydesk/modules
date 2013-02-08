package com.maydesk.context;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.maydesk.base.MDServlet;

public class EchoWebSocketServlet extends WebSocketServlet {

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return (WebSocket) MDServletExternalContext.wsHandler.process(this, request, protocol);
	}
}
