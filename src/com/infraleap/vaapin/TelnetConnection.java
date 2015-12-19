package com.infraleap.vaapin;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import com.infraleap.vaapin.config.Configuration;
import com.infraleap.vaapin.util.Logger;

public class TelnetConnection {

	private Socket sock;

	public TelnetConnection(){
		login();
	}

	public String getResponse(String command) {
		try {
			if (sock != null) {
				writeStringToSocket(command + "\n", this.sock);

				return readStringFromSocket(this.sock);
			}
		} catch (IOException e) {
			Logger.logException(e);
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
			//Pretty 'normal' condition. TODO: refactor the control flow. This is stupid.
			//Logger.logException(timeoutEx);
		}
		//System.out.println(resultBuff.length + " bytes read.");
		return new String(resultBuff, Charset.defaultCharset());
	}

	private void login() {
		try{
			if (this.sock != null){
				sock.close();
			}

			this.sock = new Socket(Configuration.MACHINE_NAME, Configuration.TELNET_PORT);
			this.sock.setSoTimeout(30); // 30 ms timeout for reading from socket

			writeStringToSocket(Configuration.LOGIN_NAME + "\n", sock);
			writeStringToSocket(Configuration.LOGIN_PASS + "\n", sock);

			Logger.logDebug(readStringFromSocket(this.sock));
		}
		catch (IOException ioe){
			Logger.logException(ioe);
		}
	}

	public void close(){
		if (sock != null){
			try {
				sock.close();
			} catch (IOException e) {
				Logger.logException(e);
			}
		}
	}

}
