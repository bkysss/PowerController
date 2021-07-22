package com.nan.pojo;

public class ServSocket {
    private String ipAddr;
    private int sock;

    public ServSocket() {
    }

    public ServSocket(String ipAddr, int sock) {
        this.ipAddr = ipAddr;
        this.sock = sock;
    }

    @Override
    public String toString() {
        return "ServSocket{" +
                "ipAddr='" + ipAddr + '\'' +
                ", sock=" + sock +
                '}';
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getSock() {
        return sock;
    }

    public void setSock(int sock) {
        this.sock = sock;
    }
}
