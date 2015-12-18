package com.example.vaapin;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

public class TelnetConnection {

	private Socket sock;
	
	public TelnetConnection(){
		
	}
	
    String getResponse(String command) {
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
        //System.out.println(resultBuff.length + " bytes read.");
        return new String(resultBuff, Charset.defaultCharset());
    }

    String login() {
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
    
    public void close(){
    	if (sock != null){
			try {
				sock.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
    }
	
}
