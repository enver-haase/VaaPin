package com.infraleap.vaapin.config;

public interface Configuration {

    public final static int TELNET_PORT = 9999;
    public final static int HIGHSCORES_PORT = 10000;
    
    public final static String MACHINE_NAME = "www.hacenet";
    public final static String HIGH_SCORES_URL = "http://" + MACHINE_NAME + ":" + HIGHSCORES_PORT;

    public final static String LOGIN_NAME = "Pin2000";
    public final static String LOGIN_PASS = "Manager";
    
    
    public final static String CAMERA_URL = "http://tenvis:7777/videostream.cgi?user=admin&pwd=admin";
    
    public final static String TELNET_LEFT_FLIPPER_UP = "flip on 0";
    public final static String TELNET_LEFT_FLIPPER_DOWN = "flip off 0";
    public final static String TELNET_RIGHT_FLIPPER_UP = "flip on 1";
    public final static String TELNET_RIGHT_FLIPPER_DOWN = "flip off 1";
    
    public final static String TELNET_REBOOT = "reboot";
    public final static String TELNET_GAME_START = "game start";
    public final static String TELNET_GAME_TILT = "game tilt";
    public final static String TELNET_SCENEMGR_START_2 = "scenemgr start 2";
}
