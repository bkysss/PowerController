package com.nan.pojo;

public class TimeInfo {
    private String date;
    private String tOnTime;
    private String tOffTime;

    public TimeInfo() {
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "date='" + date + '\'' +
                ", tOnTime='" + tOnTime + '\'' +
                ", tOffTime='" + tOffTime + '\'' +
                '}';
    }

    public TimeInfo(String date, String tOnTime, String tOffTime) {
        this.date = date;
        this.tOnTime = tOnTime;
        this.tOffTime = tOffTime;
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
}
