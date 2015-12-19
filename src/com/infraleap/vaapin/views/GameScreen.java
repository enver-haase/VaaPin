package com.infraleap.vaapin.views;

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

	public GameScreen(){
		ExternalResource res = new ExternalResource(Configuration.CAMERA_URL);
		Image image = new Image("Pinball 2000 Camera", res);
		image.setSizeFull();
		
		this.addComponent(image);
		
		HorizontalLayout buttonsFooter = new HorizontalLayout();
		buttonsFooter.setMargin(true);
		buttonsFooter.setWidth("100%");
		buttonsFooter.setHeightUndefined();
		
		PinButton leftButton = new PinButton();
		buttonsFooter.addComponent(leftButton);
		buttonsFooter.setComponentAlignment(leftButton, Alignment.MIDDLE_LEFT);
		leftButton.setWidth("30%");
		leftButton.setHeightUndefined();
		
		PinButton rightButton = new PinButton();
		buttonsFooter.addComponent(rightButton);
		buttonsFooter.setComponentAlignment(rightButton, Alignment.MIDDLE_RIGHT);
		rightButton.setWidth("30%");
		rightButton.setHeightUndefined();
		
		this.addComponent(buttonsFooter);
		
	}	
}
