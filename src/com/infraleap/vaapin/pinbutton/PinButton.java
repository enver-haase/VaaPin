package com.infraleap.vaapin.pinbutton;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonServerRpc;
import com.infraleap.vaapin.pinbutton.client.pinbutton.PinButtonState;
import com.infraleap.vaapin.util.Logger;

public class PinButton extends com.vaadin.ui.AbstractComponent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface FlipperListener{
		void leftFlipperDown();
		void leftFlipperUp();
		void rightFlipperDown();
		void rightFlipperUp();
	}
	
	private final Collection<FlipperListener> flipperListeners = Collections.synchronizedCollection(new HashSet<FlipperListener>());
	
	private final PinButtonServerRpc rpc = new PinButtonServerRpc() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void mouseUp(int button) {
			Logger.logDebug("Mouse up. Button '"+button+"'.");
			
			if (button == 1){
				for (FlipperListener l : flipperListeners) {
					l.leftFlipperDown();
				}
			}
			else{
				for (FlipperListener l : flipperListeners) {
					l.rightFlipperDown();
				}
			}
		}
		
		@Override
		public void mouseDown(int button) {
			Logger.logDebug("Mouse down. Button '"+button+"'");
			
			if (button == 1){
				for (FlipperListener l : flipperListeners) {
					l.leftFlipperUp();
				}
			}
			else{
				for (FlipperListener l : flipperListeners) {
					l.rightFlipperUp();
				}
			}
		}
		
	};  

	public PinButton() {
		registerRpc(rpc);
	}

	@Override
	public PinButtonState getState() {
		return (PinButtonState) super.getState();
	}
	
	public void addFlipperListener(FlipperListener l){
		flipperListeners.add(l);
	}
	
	public void removeFlipperListener(FlipperListener l){
		flipperListeners.remove(l);
	}
}
