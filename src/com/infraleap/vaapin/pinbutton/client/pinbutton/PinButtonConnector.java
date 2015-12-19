package com.infraleap.vaapin.pinbutton.client.pinbutton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.infraleap.vaapin.pinbutton.PinButton;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
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

		// TODO ServerRpc usage example, do something useful instead
		getWidget().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
					.buildMouseEventDetails(event.getNativeEvent(),
								getWidget().getElement());
				rpc.clicked(mouseDetails);
			}
		});

		getWidget().addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				rpc.mouseUp(event.getNativeButton());
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

