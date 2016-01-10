package com.infraleap.vaapin;

import javax.servlet.annotation.WebServlet;

import com.infraleap.vaapin.util.Logger;
import com.infraleap.vaapin.views.GameScreen;
import com.infraleap.vaapin.views.PinConsole;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("vaapin")
public class VaapinUI extends UI implements DetachListener{

	private TelnetConnection telnetConn;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaapinUI.class, widgetset = "com.infraleap.vaapin.pinbutton.VaapinWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {

		Logger.logDebug("VaaPin initializing.");
		this.telnetConn = new TelnetConnection();

		PinConsole pinCon = new PinConsole(this.telnetConn);
		pinCon.setSizeFull();

		GameScreen gameScreen = new GameScreen(this.telnetConn);
		gameScreen.setSizeFull();
		
		TabSheet tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		
		
		tabsheet.addTab(pinCon, "Pinball 2000 Console", FontAwesome.KEYBOARD_O);
		tabsheet.addTab(gameScreen, "Game Screen", FontAwesome.GAMEPAD);
		
		TabSheet.SelectedTabChangeListener l = new TabSheet.SelectedTabChangeListener() {
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if (tabsheet.getSelectedTab() == pinCon)
				{
					pinCon.focus();
				}
			}
		};
		tabsheet.addSelectedTabChangeListener(l);
		
		this.setContent(tabsheet);

		tabsheet.setSelectedTab(gameScreen);
	}



	@Override
	public void detach(DetachEvent event) {
		this.telnetConn.close();
	}




}