package com.infraleap.vaapin.views;

import com.infraleap.vaapin.TelnetConnection;
import com.infraleap.vaapin.config.Configuration;
import com.infraleap.vaapin.pinbutton.PinButton;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

public class GameScreen extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344567455727505911L;

	public GameScreen(TelnetConnection telnetConn){
		ExternalResource res = new ExternalResource(Configuration.CAMERA_URL);
		Image image = new Image("Pinball 2000 Camera", res);
		image.setSizeFull();
		
		this.addComponent(image);
		
		HorizontalLayout buttonsFooter = new HorizontalLayout();
		buttonsFooter.setMargin(true);
		buttonsFooter.setWidth("100%");
		buttonsFooter.setHeightUndefined();
		
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
		
		
		buttonsFooter.addComponent(pinButton);
		buttonsFooter.setComponentAlignment(pinButton, Alignment.MIDDLE_CENTER);
		pinButton.setWidth("100%");
		pinButton.setHeightUndefined();
		
		this.addComponent(buttonsFooter);
		
	}	
}
