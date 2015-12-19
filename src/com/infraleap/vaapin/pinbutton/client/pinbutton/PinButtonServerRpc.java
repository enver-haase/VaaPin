package com.infraleap.vaapin.pinbutton.client.pinbutton;

import com.vaadin.shared.communication.ServerRpc;

public interface PinButtonServerRpc extends ServerRpc {

	public void mouseUp(int button);
	
	public void mouseDown(int button);
}
