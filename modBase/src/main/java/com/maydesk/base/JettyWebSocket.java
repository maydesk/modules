package com.maydesk.base;

import java.io.IOException;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.ApplicationWebSocket;

import org.eclipse.jetty.websocket.WebSocket;

public class JettyWebSocket extends ApplicationWebSocket implements WebSocket {
	/**
	 * 
	 * @author sieskei
	 */
	private final class EchoConnection implements ApplicationWebSocket.Connection {
		private final org.eclipse.jetty.websocket.WebSocket.Connection conn;

		public EchoConnection(WebSocket.Connection conn) {
			this.conn = conn;
		}

		@Override
		public void close() {
			this.conn.close();
		}

		@Override
		public void close(int i, String string) {
			this.conn.close(i, string);
		}

		@Override
		public boolean isOpen() {
			return this.conn.isOpen();
		}

		@Override
		public void sendMessage(String string) throws IOException {
			this.conn.sendMessage(string);
		}
	}

	private final ApplicationInstance client;

	public JettyWebSocket(ApplicationInstance client) {
		this.client = client;
	}

	@Override
	public void onOpen(org.eclipse.jetty.websocket.WebSocket.Connection connection) {
		processOpen(new EchoConnection(connection));
	}

	@Override
	public void onClose(int i, String string) {
		this.processClose(i, string);
	}
}
