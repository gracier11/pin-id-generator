package com.pin.flake.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
    private final static Pattern ipPattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    public static boolean verifyIp(String ip) {
        if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip))
            return false;
        return ipPattern.matcher(ip).matches();
    }

    public static long[] segments(String ip) {
        long[] array = new long[4];
        int position1 = ip.indexOf(".");
        int position2 = ip.indexOf(".", position1 + 1);
        int position3 = ip.indexOf(".", position2 + 1);
        array[0] = Long.parseLong(ip.substring(0, position1));
        array[1] = Long.parseLong(ip.substring(position1 + 1, position2));
        array[2] = Long.parseLong(ip.substring(position2 + 1, position3));
        array[3] = Long.parseLong(ip.substring(position3 + 1));
        return array;
    }

    public static long ipToLong(String ip) {
        long[] array = segments(ip);
        return (array[0] << 24) + (array[1] << 16) + (array[2] << 8) + array[3];
    }

    public static String ipToString(long ip) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((ip >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x000000FF)));
        return sb.toString();
    }

    public static String getLocalHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            logger.error("获取本机ip地址失败", e);
        }
        return null;
    }

}
