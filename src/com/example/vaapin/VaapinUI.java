package com.example.vaapin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("vaapin")
public class VaapinUI extends UI implements DetachListener{

	private TelnetConnection telnetConn;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaapinUI.class)
	public static class Servlet extends VaadinServlet {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {

		System.out.println("VaaPin initializing.");
		this.telnetConn = new TelnetConnection();

		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		PinConsole pinCon = new PinConsole(this.telnetConn);
		pinCon.setSizeFull();

		layout.addComponent(pinCon);	
		this.setContent(layout);
		
		pinCon.focus();
	}



	@Override
	public void detach(DetachEvent event) {
		this.telnetConn.close();
	}




}