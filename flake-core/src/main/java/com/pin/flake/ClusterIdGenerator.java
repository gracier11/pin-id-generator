package com.pin.flake;

import com.pin.flake.core.ClusterIdMeta;
import com.pin.flake.core.Id;
import com.pin.flake.core.IdMeta;
import com.pin.flake.core.IdResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClusterIdGenerator implements IdGenerator {

    private IdMeta idMeta;

    private IdResolver idResolver;

    private long cluster;

    private long node;

    public ClusterIdGenerator(long cluster, long node) {
        this.idMeta = new ClusterIdMeta();
        this.idResolver = new IdResolver(this.idMeta);
        this.cluster = cluster;
        this.node = node;
    }

    public ClusterIdGenerator(Properties properties) {
        this.idMeta = new ClusterIdMeta();
        this.idResolver = new IdResolver(this.idMeta);
        String cluster = properties.getProperty("cluster");
        if(cluster == null || "".equals(cluster.trim())) {
            throw new IllegalArgumentException("key:cluster not found");
        }
        this.cluster = Long.valueOf(cluster);
        String node = properties.getProperty("node");
        if(node == null || "".equals(node.trim())) {
            throw new IllegalArgumentException("key:node not found");
        }
        this.node = Long.valueOf(node);
    }

    public ClusterIdGenerator(File file) {
        this.idMeta = new ClusterIdMeta();
        this.idResolver = new IdResolver(this.idMeta);
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            properties.load(in);
            String cluster = properties.getProperty("cluster");
            if(cluster == null || "".equals(cluster.trim())) {
                throw new IllegalArgumentException("key:cluster not found");
            }
            this.cluster = Long.valueOf(cluster);
            String node = properties.getProperty("node");
            if(node == null || "".equals(node.trim())) {
                throw new IllegalArgumentException("key:node not found");
            }
            this.node = Long.valueOf(node);
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

    @Override
    public long generate() {
        return idResolver.generate(cluster, node);
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }

    public IdMeta getIdMeta() {
        return idMeta;
    }

    public IdResolver getIdResolver() {
        return idResolver;
    }

    public long getCluster() {
        return cluster;
    }

    public long getNode() {
        return node;
    }

}
