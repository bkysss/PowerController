package com.nan.pojo;
public class Exception {
    private String ipaddr;
    private String date;
    private String tOnTime;
    private String tOffTime;

    public String getIp() {
        return ipaddr;
    }

    public void setIp(String ip) {
        this.ipaddr = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String gettOnTime() {
        return tOnTime;
    }

    public void settOnTime(String tOnTime) {
        this.tOnTime = tOnTime;
    }

    public String gettOffTime() {
        return tOffTime;
    }

    public void settOffTime(String tOffTime) {
        this.tOffTime = tOffTime;
    }

    @Override
    public String toString() {
        return "Exception{" +
                "ip='" + ipaddr + '\'' +
                ", date='" + date + '\'' +
                ", tOnTime='" + tOnTime + '\'' +
                ", tOffTime='" + tOffTime + '\'' +
                '}';
    }
}
