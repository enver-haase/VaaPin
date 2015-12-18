package com.example.vaapin;

import javax.servlet.annotation.WebServlet;

import com.example.vaapin.util.OnEnterKeyHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("vaapin")
public class VaapinUI extends UI implements DetachListener{

	private TelnetConnection telnetConn;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = VaapinUI.class)
    public static class Servlet extends VaadinServlet {
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PropertyId("userInput")
    private final TextField userInputField = new TextField("");

    @Override
    protected void init(VaadinRequest request) {

        System.out.println("VaaPin initializing.");
        this.telnetConn = new TelnetConnection();
        
        
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);

        Label l1 = new Label("VaaPin: Reindeer Goes Pinball.");
        l1.addStyleName("header1");
        layout.addComponent(l1);
        Label l2 = new Label("Copyright ©2014-2015 Vaadin Oy.");
        l2.addStyleName("header2");
        layout.addComponent(l2);

        BrowserFrame frame = new BrowserFrame("High Scores:", new ExternalResource(Configuration.HIGH_SCORES_URL));
        frame.setSizeFull();

        Panel panel = new Panel();
        panel.setSizeFull();

        VerticalLayout content = new VerticalLayout();
        panel.setContent(content);
        content.setWidth("100%");
        content.setHeight(null); // WE WANT IT TO BE UNDEFINED (default anyway)
        content.setStyleName("content");
        layout.addComponent(frame);
        layout.setExpandRatio(frame, 1);
        layout.addComponent(panel);
        layout.setExpandRatio(panel, 1);

        userInputField.setImmediate(true);
        userInputField.setWidth("100%");
        userInputField.setStyleName("content");
        OnEnterKeyHandler onEnter = new OnEnterKeyHandler() {
            @Override
            public void onEnterKeyPressed() {
                if (userInputField.getValue().length() > 0) {
                    content.addComponent(new Label(""));
                    content.addComponent(new Label("> " + userInputField.getValue()));
                    Label l = new Label(telnetConn.getResponse(userInputField.getValue()));
                    content.addComponent(l);
                    UI.getCurrent().scrollIntoView(l);
                    userInputField.setValue("");
                }
            }
        };
        onEnter.installOn(userInputField);
        layout.addComponent(userInputField);
        layout.setComponentAlignment(userInputField, Alignment.BOTTOM_LEFT);

        Label l = new Label(telnetConn.login());
        content.addComponent(l);

        userInputField.focus();
    }



	@Override
	public void detach(DetachEvent event) {
		this.telnetConn.close();
	}
    
    
    
    
}