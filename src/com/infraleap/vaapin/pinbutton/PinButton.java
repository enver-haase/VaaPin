package com.infraleap.vaapin.pinbutton;

import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonClientRpc;
import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonServerRpc;
import com.vaadin.shared.MouseEventDetails;
import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonState;

public class PinButton extends com.vaadin.ui.AbstractComponent {

	private PinButtonServerRpc rpc = new PinButtonServerRpc() {
		private int clickCount = 0;

		public void clicked(MouseEventDetails mouseDetails) {
			// nag every 5:th click using RPC
			if (++clickCount % 5 == 0) {
				getRpcProxy(PinButtonClientRpc.class).alert(
						"Ok, that's enough!");
			}
			// update shared state
			getState().text = "You have clicked " + clickCount + " times";
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
