package com.example.vaapin;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import javax.servlet.annotation.WebServlet;

import com.example.vaapin.util.OnEnterKeyHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.PropertyId;
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
public class VaapinUI extends UI {

    Socket sock;

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
                    Label l = new Label(getResponse(userInputField.getValue()));
                    content.addComponent(l);
                    UI.getCurrent().scrollIntoView(l);
                    userInputField.setValue("");
                }
            }
        };
        onEnter.installOn(userInputField);
        layout.addComponent(userInputField);
        layout.setComponentAlignment(userInputField, Alignment.BOTTOM_LEFT);

        Label l = new Label(login());
        content.addComponent(l);

        userInputField.focus();
    }

    private String getResponse(String command) {
        try {
            if (sock != null) {
                writeStringToSocket(command + "\n", this.sock);

                return readStringFromSocket(this.sock);
            }
        } catch (IOException e) {
            System.err.println(e); // TODO
        }
        return "<Communication error>";
    }

    private static void writeStringToSocket(String command, Socket sock) throws IOException {
        sock.getOutputStream().write(command.getBytes(Charset.defaultCharset()));
    }

    private static String readStringFromSocket(Socket sock) throws IOException {
        byte[] resultBuff = new byte[0];
        byte[] buff = new byte[1024];
        int k = -1;
        try {
            while ((k = sock.getInputStream().read(buff, 0, buff.length)) > 0) {
                // System.out.println("Read " + k + " bytes...");
                byte[] tbuff = new byte[resultBuff.length + k]; // temp buffer size = bytes already read + bytes last read
                System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length); // copy previous bytes
                System.arraycopy(buff, 0, tbuff, resultBuff.length, k); // copy current lot
                resultBuff = tbuff; // call the temp buffer as your result buff
            }
        } catch (SocketTimeoutException timeoutEx) {
            // normal behaviour to land here, when response to command is sent and TCP line is kept open.
        }
        System.out.println(resultBuff.length + " bytes read.");
        return new String(resultBuff, Charset.defaultCharset());
    }

    private String login() {
        try {
            this.sock = new Socket(Configuration.MACHINE_NAME, Configuration.TELNET_PORT);
            this.sock.setSoTimeout(30); // 30 ms timeout for reading from socket
            // System.out.println(readStringFromSocket(this.sock));
            writeStringToSocket(Configuration.LOGIN_NAME + "\n", sock);
            // System.out.println(readStringFromSocket(this.sock));
            writeStringToSocket(Configuration.LOGIN_PASS + "\n", sock);
            return (readStringFromSocket(this.sock));
        } catch (IOException e) {
            System.err.println(e); // TODO
            return "<login error>";
        }
    }
}