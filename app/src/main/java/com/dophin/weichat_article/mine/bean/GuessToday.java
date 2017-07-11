package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/11.
 */

public class GuessToday {
    private String FAN;
    private String MSG;
    private float PRICE;
    private int COUNT_ZHENG;
    private int COUNT_FAN;
    private String ZHENG;
    private int ID;
    private int STATUS;
    private int FLAG;
    private String TITLE;


    public String getFAN() {
        return FAN;
    }

    public void setFAN(String FAN) {
        this.FAN = FAN;
    }

    public int getCOUNT_FAN() {
        return COUNT_FAN;
    }

    public void setCOUNT_FAN(int COUNT_FAN) {
        this.COUNT_FAN = COUNT_FAN;
    }

    public int getCOUNT_ZHENG() {
        return COUNT_ZHENG;
    }

    public void setCOUNT_ZHENG(int COUNT_ZHENG) {
        this.COUNT_ZHENG = COUNT_ZHENG;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public float getPRICE() {
        return PRICE;
    }

    public void setPRICE(float PRICE) {
        this.PRICE = PRICE;
    }

    public String getZHENG() {
        return ZHENG;
    }

    public void setZHENG(String ZHENG) {
        this.ZHENG = ZHENG;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public int getFLAG() {
        return FLAG;
    }

    public void setFLAG(int FLAG) {
        this.FLAG = FLAG;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
