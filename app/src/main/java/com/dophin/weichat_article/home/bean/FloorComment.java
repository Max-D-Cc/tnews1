package com.dophin.weichat_article.home.bean;

/**
 * Created by caiguoqing on 2017/3/18.
 */

public class FloorComment {

    private int CLICKZAN;
    private int REPLY_FLOOR;
    private String NAME;
    private String JLTIME;
    private String REPLY_NICKNAME;
    private String NICKNAME;
    private String CONTENT;
    private String UID;
    private int ID;
    private String REPLY_IMG;
    private int FLOOR;
    private String IMG;
    private String TITLE;
    private String REPLY_NAME;
    public boolean isFloorZan = false;

    public int getCLICKZAN() {
        return CLICKZAN;
    }

    public void setCLICKZAN(int CLICKZAN) {
        this.CLICKZAN = CLICKZAN;
    }

    public int getREPLY_FLOOR() {
        return REPLY_FLOOR;
    }

    public void setREPLY_FLOOR(int REPLY_FLOOR) {
        this.REPLY_FLOOR = REPLY_FLOOR;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getJLTIME() {
        return JLTIME;
    }

    public void setJLTIME(String JLTIME) {
        this.JLTIME = JLTIME;
    }

    public String getREPLY_NICKNAME() {
        return REPLY_NICKNAME;
    }

    public void setREPLY_NICKNAME(String REPLY_NICKNAME) {
        this.REPLY_NICKNAME = REPLY_NICKNAME;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getREPLY_IMG() {
        return REPLY_IMG;
    }

    public void setREPLY_IMG(String REPLY_IMG) {
        this.REPLY_IMG = REPLY_IMG;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public int getFLOOR() {
        return FLOOR;
    }

    public void setFLOOR(int FLOOR) {
        this.FLOOR = FLOOR;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getREPLY_NAME() {
        return REPLY_NAME;
    }

    public void setREPLY_NAME(String REPLY_NAME) {
        this.REPLY_NAME = REPLY_NAME;
    }
}
