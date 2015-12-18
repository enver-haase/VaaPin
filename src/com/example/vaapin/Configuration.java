package com.example.vaapin;

public interface Configuration {

    public final static int TELNET_PORT = 9999;
    public final static int HIGHSCORES_PORT = 10000;
    
	public final static String MACHINE_NAME = "www.hacenet";
    public final static String HIGH_SCORES_URL = "http://" + MACHINE_NAME + ":" + HIGHSCORES_PORT;

    public final static String LOGIN_NAME = "Pin2000";
    public final static String LOGIN_PASS = "Manager";
}
