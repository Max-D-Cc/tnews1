package com.dophin.weichat_article.login.bean;


import java.io.Serializable;

/**
 * Created by caiguoqing on 2017/3/9.
 */

public class PhoneLoginBean implements Serializable{

    private double POINTS_QIDAY;
    private String ALIPAY;
    private int POINTS_TODAY;
    private int STATUS;
    private String UNIONID;
    private String NICKNAME;
    private double POINTS_TOTAL;
    private String ALIPAY_NAME;
    private String MSG;
    private String YQM;
    private String ORDERID;
    private String QQ;
    private String TEL;
    private double POINTS_SURPLUS;
    private double POINTS_YESTERDAY;
    private int ID;
    private String PASSWORD;
    private int READHTML;
    private String IMG;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPOINTS_QIDAY() {
        return POINTS_QIDAY;
    }

    public void setPOINTS_QIDAY(double POINTS_QIDAY) {
        this.POINTS_QIDAY = POINTS_QIDAY;
    }

    public String getALIPAY() {
        return ALIPAY;
    }

    public void setALIPAY(String ALIPAY) {
        this.ALIPAY = ALIPAY;
    }

    public int getPOINTS_TODAY() {
        return POINTS_TODAY;
    }

    public void setPOINTS_TODAY(int POINTS_TODAY) {
        this.POINTS_TODAY = POINTS_TODAY;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getUNIONID() {
        return UNIONID;
    }

    public void setUNIONID(String UNIONID) {
        this.UNIONID = UNIONID;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public double getPOINTS_TOTAL() {
        return POINTS_TOTAL;
    }

    public void setPOINTS_TOTAL(double POINTS_TOTAL) {
        this.POINTS_TOTAL = POINTS_TOTAL;
    }

    public String getALIPAY_NAME() {
        return ALIPAY_NAME;
    }

    public void setALIPAY_NAME(String ALIPAY_NAME) {
        this.ALIPAY_NAME = ALIPAY_NAME;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getYQM() {
        return YQM;
    }

    public void setYQM(String YQM) {
        this.YQM = YQM;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public double getPOINTS_SURPLUS() {
        return POINTS_SURPLUS;
    }

    public void setPOINTS_SURPLUS(double POINTS_SURPLUS) {
        this.POINTS_SURPLUS = POINTS_SURPLUS;
    }

    public double getPOINTS_YESTERDAY() {
        return POINTS_YESTERDAY;
    }

    public void setPOINTS_YESTERDAY(double POINTS_YESTERDAY) {
        this.POINTS_YESTERDAY = POINTS_YESTERDAY;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getREADHTML() {
        return READHTML;
    }

    public void setREADHTML(int READHTML) {
        this.READHTML = READHTML;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }
}
