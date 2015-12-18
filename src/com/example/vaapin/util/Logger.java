package com.example.vaapin.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {

    private static final boolean INCLUDESTACKTRACE = false;

    private static String generateLogString(String message) {
        long time = System.currentTimeMillis();
        long secs = time / 1000;
        long micros = (time % 1000) * 1000;

        int threadId = System.identityHashCode(Thread.currentThread());

        String ret = String.format("%012d", secs) + "." + String.format("%06d", micros) + " : 0x" + String.format("%016x", threadId) + " -- " + message;

        if (INCLUDESTACKTRACE) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            new RuntimeException().printStackTrace(pw);
            ret += "\n" + sw.toString();
        }

        return ret;
    }

    public static void logDebug(String debug) {
        System.out.println(generateLogString("DEBUG     : " + debug));
    }

    public static void logError(String error) {
        System.out.println(generateLogString("ERROR     : " + error));
    }

    public static void logFatalAndDie(String fatal) {
        System.out.println(generateLogString("FATAL     : " + fatal));
        System.exit(-1);
    }
    
    public static void logException(Throwable ex){
    	StringWriter errors = new StringWriter();
    	ex.printStackTrace(new PrintWriter(errors));
    	System.out.println(generateLogString("EXCEPTION : " + errors.toString()));
    }
}
