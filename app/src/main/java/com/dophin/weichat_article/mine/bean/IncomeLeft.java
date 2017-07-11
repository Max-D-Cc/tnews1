package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/11.
 */

public class IncomeLeft {

    private String CHANNEL;
    private String ORDERID;
    private String IP;
    private int ID;
    private String LINE_I;
    private int POINTS;
    private String C_TIME;
    private int USERID;
    private int C_DAY;


    public String getCHANNEL() {
        return CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(int POINTS) {
        this.POINTS = POINTS;
    }

    public String getLINE_I() {
        return LINE_I;
    }

    public void setLINE_I(String LINE_I) {
        this.LINE_I = LINE_I;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public int getUSERID() {
        return USERID;
    }

    public void setUSERID(int USERID) {
        this.USERID = USERID;
    }

    public String getC_TIME() {
        return C_TIME;
    }

    public void setC_TIME(String c_TIME) {
        C_TIME = c_TIME;
    }

    public int getC_DAY() {
        return C_DAY;
    }

    public void setC_DAY(int c_DAY) {
        C_DAY = c_DAY;
    }
}
