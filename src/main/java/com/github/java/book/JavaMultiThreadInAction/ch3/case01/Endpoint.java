package com.github.java.book.JavaMultiThreadInAction.ch3.case01;

import java.util.Objects;

/**
 * 表示下游部件的节点
 *
 * @author pengfei.zhao
 * @date 2020/10/21 19:07
 */
public class Endpoint {
    public final String host;
    public final int port;
    public final int weight;
    private volatile boolean online = true;

    public Endpoint(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return port == endpoint.port &&
                weight == endpoint.weight &&
                Objects.equals(host, endpoint.host);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result = prime * result + (());
        return result;
    }
}
