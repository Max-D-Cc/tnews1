package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/11.
 */

public class IncomeRight {

    private String CHANNEL;
    private String JLTIME;
    private String ORDERID;
    private float MONEY;
    private int ID;
    private int POINTS;
    private int USERID;

    public String getCHANNEL() {
        return CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    public int getUSERID() {
        return USERID;
    }

    public void setUSERID(int USERID) {
        this.USERID = USERID;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(int POINTS) {
        this.POINTS = POINTS;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getMONEY() {
        return MONEY;
    }

    public void setMONEY(float MONEY) {
        this.MONEY = MONEY;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getJLTIME() {
        return JLTIME;
    }

    public void setJLTIME(String JLTIME) {
        this.JLTIME = JLTIME;
    }
}
