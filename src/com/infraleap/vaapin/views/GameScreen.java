package com.infraleap.vaapin.views;

import com.infraleap.vaapin.config.Configuration;
import com.vaadin.server.ExternalResource;
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
	}	
}
