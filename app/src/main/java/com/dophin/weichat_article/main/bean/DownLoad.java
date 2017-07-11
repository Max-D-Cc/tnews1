package com.dophin.weichat_article.main.bean;

/**
 * Created by caiguoqing on 2017/3/20.
 */

public class DownLoad {

    private String MSG;
    private String VISION;
    private String ADDRESS;
    private int ID;
    private int STATUS;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getVISION() {
        return VISION;
    }

    public void setVISION(String VISION) {
        this.VISION = VISION;
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
}
