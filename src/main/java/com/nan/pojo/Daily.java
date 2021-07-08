package com.nan.pojo;

public class Daily {
    private String ddate;
    private String DIPAddr;
    private String switchID;
    private String ifNum;
    private String servCPU;
    private String tOnFirst;
    private String tOffLast;
    private int activeNum;
    private String clientIPs;

    public Daily() {
    }

    @Override
    public String toString() {
        return "Daily{" +
                "ddate='" + ddate + '\'' +
                ", DIPAddr='" + DIPAddr + '\'' +
                ", switchID='" + switchID + '\'' +
                ", ifNum='" + ifNum + '\'' +
                ", servCPU='" + servCPU + '\'' +
                ", tOnFirst='" + tOnFirst + '\'' +
                ", tOffLast='" + tOffLast + '\'' +
                ", activeNum=" + activeNum +
                ", clientIPs='" + clientIPs + '\'' +
                '}';
    }

    public Daily(String ddate, String DIPAddr, String switchID, String ifNum, String servCPU, String tOnFirst, String tOffLast, int activeNum, String clientIPs) {
        this.ddate = ddate;
        this.DIPAddr = DIPAddr;
        this.switchID = switchID;
        this.ifNum = ifNum;
        this.servCPU = servCPU;
        this.tOnFirst = tOnFirst;
        this.tOffLast = tOffLast;
        this.activeNum = activeNum;
        this.clientIPs = clientIPs;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getDIPAddr() {
        return DIPAddr;
    }

    public void setDIPAddr(String DIPAddr) {
        this.DIPAddr = DIPAddr;
    }

    public String getSwitchID() {
        return switchID;
    }

    public void setSwitchID(String switchID) {
        this.switchID = switchID;
    }

    public String getIfNum() {
        return ifNum;
    }

    public void setIfNum(String ifNum) {
        this.ifNum = ifNum;
    }

    public String getServCPU() {
        return servCPU;
    }

    public void setServCPU(String servCPU) {
        this.servCPU = servCPU;
    }

    public String gettOnFirst() {
        return tOnFirst;
    }

    public void settOnFirst(String tOnFirst) {
        this.tOnFirst = tOnFirst;
    }

    public String gettOffLast() {
        return tOffLast;
    }

    public void settOffLast(String tOffLast) {
        this.tOffLast = tOffLast;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public String getClientIPs() {
        return clientIPs;
    }

    public void setClientIPs(String clientIPs) {
        this.clientIPs = clientIPs;
    }
}
