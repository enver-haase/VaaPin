package com.infraleap.vaapin.pinbutton;

import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonClientRpc;
import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonServerRpc;
import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonState;
import com.infraleap.vaapin.util.Logger;
import com.vaadin.shared.MouseEventDetails;

public class PinButton extends com.vaadin.ui.AbstractComponent {

	private final PinButtonServerRpc rpc = new PinButtonServerRpc() {
		private int clickCount = 0;

		@Override
		public void clicked(MouseEventDetails mouseDetails) {
			// nag every 5:th click using RPC
			if (++clickCount % 5 == 0) {
				getRpcProxy(PinButtonClientRpc.class).alert(
						"Ok, that's enough!");
			}
			// update shared state
			getState().text = "You have clicked " + clickCount + " times";
		}
		
		@Override
		public void mouseUp(int button) {
			Logger.logDebug("Mouse up. Button '"+button+"'.");
		}
	};  

	public PinButton() {
		registerRpc(rpc);
	}

	@Override
	public PinButtonState getState() {
		return (PinButtonState) super.getState();
	}
}
