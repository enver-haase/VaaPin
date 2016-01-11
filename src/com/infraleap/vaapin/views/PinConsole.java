package com.infraleap.vaapin.views;

import com.infraleap.vaapin.TelnetConnection;
import com.infraleap.vaapin.config.Configuration;
import com.infraleap.vaapin.util.OnEnterKeyHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PinConsole extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -610500057953132550L;
	
	private final TextField userInputField = new TextField("");
	
	public PinConsole(final TelnetConnection telnetConn){
		
		Label l1 = new Label("VaaPin: Reindeer Goes Pinball.");
		l1.addStyleName("header1");
		this.addComponent(l1);
		Label l2 = new Label("Copyright ©2014-2016 Vaadin Oy.");
		l2.addStyleName("header2");
		this.addComponent(l2);

		BrowserFrame frame = new BrowserFrame("High Scores:", new ExternalResource(Configuration.HIGH_SCORES_URL));
		frame.setSizeFull();

		Panel panel = new Panel();
		panel.setSizeFull();

		VerticalLayout content = new VerticalLayout();
		panel.setContent(content);
		content.setWidth("100%");
		content.setHeight(null); // WE WANT IT TO BE UNDEFINED (default anyway)
		content.setStyleName("content");
		this.addComponent(frame);
		this.setExpandRatio(frame, 1);
		this.addComponent(panel);
		this.setExpandRatio(panel, 1);

		userInputField.setImmediate(true);
		userInputField.setWidth("100%");
		userInputField.setStyleName("content");
		OnEnterKeyHandler onEnter = new OnEnterKeyHandler() {
			@Override
			public void onEnterKeyPressed() {
				//if (userInputField.getValue().length() > 0) {
				content.addComponent(new Label(""));
				content.addComponent(new Label("> " + userInputField.getValue()));
				Label l = new Label(telnetConn.getResponse(userInputField.getValue()));
				content.addComponent(l);
				UI.getCurrent().scrollIntoView(l);
				userInputField.setValue("");
			}
			//}
		};
		onEnter.installOn(userInputField);
		this.addComponent(userInputField);
		this.setComponentAlignment(userInputField, Alignment.BOTTOM_LEFT);

		Label l = new Label("Pinball2000 console.");
		content.addComponent(l);

		userInputField.focus();
	}
	
	@Override
	public void focus() {
		userInputField.focus();
	}
	
}
