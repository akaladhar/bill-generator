package com.bjss.assignment.util;

//Logger
//Can be replaced with packages like log4j.

//To keep it simple, outputting messages to console
public class Logger {
    private static boolean loggingEnabled = false;
    public static void log(String message) {
        if (loggingEnabled) {
            System.out.println(message);
        }
    }
}
