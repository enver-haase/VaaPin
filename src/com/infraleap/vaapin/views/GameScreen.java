package com.infraleap.vaapin.views;

import com.infraleap.vaapin.TelnetConnection;
import com.infraleap.vaapin.config.Configuration;
import com.infraleap.vaapin.pinbutton.PinButton;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

public class GameScreen extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344567455727505911L;

	public GameScreen(TelnetConnection telnetConn){
		
		HorizontalLayout buttonsHeader = new HorizontalLayout();
		Button rebootButton = new Button("REBOOT", new Button.ClickListener(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				telnetConn.getResponse(Configuration.TELNET_REBOOT);
			}
		});
		
		Button startButton = new Button("START GAME", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				telnetConn.getResponse(Configuration.TELNET_GAME_START);
				telnetConn.getResponse(Configuration.TELNET_SCENEMGR_START_2);
			}
		});
		
		Button tiltButton = new Button("TILT", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				telnetConn.getResponse(Configuration.TELNET_GAME_TILT);
			}
		});
		buttonsHeader.setMargin(true);
		buttonsHeader.addComponent(rebootButton);
		buttonsHeader.addComponent(startButton);
		buttonsHeader.addComponent(tiltButton);
		buttonsHeader.setWidth("100%");
		buttonsHeader.setHeightUndefined();
		
		buttonsHeader.setComponentAlignment(rebootButton, Alignment.MIDDLE_LEFT);
		buttonsHeader.setComponentAlignment(startButton, Alignment.MIDDLE_CENTER);
		buttonsHeader.setComponentAlignment(tiltButton, Alignment.MIDDLE_RIGHT);

		this.addComponent(buttonsHeader);
		this.setComponentAlignment(buttonsHeader, Alignment.TOP_CENTER);
		this.setExpandRatio(buttonsHeader, 0.1f);
		
		ExternalResource res = new ExternalResource(Configuration.CAMERA_URL);
		Image image = new Image("Pinball 2000 Camera", res);
		image.setWidth("100%");
		image.setHeightUndefined();
		this.addComponent(image);
		this.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		this.setExpandRatio(image, 0.8f);
		
		
		PinButton pinButton = new PinButton();
		pinButton.addFlipperListener(new PinButton.FlipperListener() {
			
			@Override
			public void rightFlipperUp() {
				telnetConn.getResponse(Configuration.TELNET_RIGHT_FLIPPER_UP);
			}
			
			@Override
			public void rightFlipperDown() {
				telnetConn.getResponse(Configuration.TELNET_RIGHT_FLIPPER_DOWN);
			}
			
			@Override
			public void leftFlipperUp() {
				telnetConn.getResponse(Configuration.TELNET_LEFT_FLIPPER_UP);
			}
			
			@Override
			public void leftFlipperDown() {
				telnetConn.getResponse(Configuration.TELNET_LEFT_FLIPPER_DOWN);
			}
		});
		
		
		this.addComponent(pinButton);
		this.setComponentAlignment(pinButton, Alignment.BOTTOM_CENTER);
		pinButton.setWidth("100%");
		pinButton.setHeight("125px");
		
		this.setExpandRatio(pinButton, 0.1f);
	}	
}
