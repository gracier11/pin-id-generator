package com.pin.flake;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.pin.flake.utils.OSUtils.isWindows;

public class DefaultIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator getIdGenerator() {
        String flakePropertiesFilePath = getDefaultPropertiesFile();
        File flakePropertiesFile = new File(flakePropertiesFilePath);
        if(flakePropertiesFile.exists() && !flakePropertiesFile.isDirectory()) {
            Properties properties = new Properties();
            InputStream in = null;
            try {
                in = new FileInputStream(flakePropertiesFile);
                properties.load(in);
                String cluster = properties.getProperty("cluster");
                if(cluster == null || "".equals(cluster.trim())) {
                    throw new IllegalArgumentException("key:cluster not found");
                }
                String node = properties.getProperty("node");
                if(node == null || "".equals(node.trim())) {
                    throw new IllegalArgumentException("key:node not found");
                }
                return new ClusterIdGenerator(Long.valueOf(cluster), Long.valueOf(node));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {}
                }
            }
        }
        return new HostIdGenerator();
    }

    private String getDefaultPropertiesFile() {
        return isWindows() ? "C:\\opt\\flake.properties" : "/opt/flake.properties";
    }
}
