package com.nan.pojo;

public class PowerCTL {
    private String IP;
    private String POnTime;
    private String POffTime;

    public PowerCTL() {
    }

    public PowerCTL(String IP, String POnTime, String POffTime) {
        this.IP = IP;
        this.POnTime = POnTime;
        this.POffTime = POffTime;
    }

    @Override
    public String toString() {
        return "PowerCTL{" +
                "IP='" + IP + '\'' +
                ", POnTime='" + POnTime + '\'' +
                ", POffTime='" + POffTime + '\'' +
                '}';
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPOnTime() {
        return POnTime;
    }

    public void setPOnTime(String POnTime) {
        this.POnTime = POnTime;
    }

    public String getPOffTime() {
        return POffTime;
    }

    public void setPOffTime(String POffTime) {
        this.POffTime = POffTime;
    }
}
