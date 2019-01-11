package com.pin.flake.utils;

public class OSUtils {

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        if (osName == null || "".equals(osName)) {
            return false;
        }
        return osName.toLowerCase().startsWith("windows");
    }
}
