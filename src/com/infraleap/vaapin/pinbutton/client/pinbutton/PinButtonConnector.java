package com.infraleap.vaapin.pinbutton.client.pinbutton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.infraleap.vaapin.pinbutton.PinButton;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(PinButton.class)
public class PinButtonConnector extends AbstractComponentConnector {

	PinButtonServerRpc rpc = RpcProxy
			.create(PinButtonServerRpc.class, this);
	
	public PinButtonConnector() {
		registerRpc(PinButtonClientRpc.class, new PinButtonClientRpc() {
			@Override
			public void alert(String message) {
				// TODO Do something useful
				Window.alert(message);
			}
		});

		getWidget().addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				rpc.mouseUp(event.getNativeButton());
			}
		});
		
		getWidget().addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				rpc.mouseDown(event.getNativeButton());
			}
		});
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(PinButtonWidget.class);
	}

	@Override
	public PinButtonWidget getWidget() {
		return (PinButtonWidget) super.getWidget();
	}

	@Override
	public PinButtonState getState() {
		return (PinButtonState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		// TODO do something useful
		final String text = getState().text;
		getWidget().setText(text);
	}

}

