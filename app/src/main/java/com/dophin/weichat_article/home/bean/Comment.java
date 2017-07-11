package com.dophin.weichat_article.home.bean;

import java.io.Serializable;

/**
 * Created by caiguoqing on 2017/3/18.
 */

public class Comment implements Serializable{

    private int CLICKZAN;
    private String JLTIME;
    private String NAME;
    private String CONTENT;
    private String UID;
    private int ID;
    private int FLOOR;
    private String IMG;
    private String NICKNAME;
    private String TITLE;
    public boolean isZan = false;

    public int getCLICKZAN() {
        return CLICKZAN;
    }

    public void setCLICKZAN(int CLICKZAN) {
        this.CLICKZAN = CLICKZAN;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFLOOR() {
        return FLOOR;
    }

    public void setFLOOR(int FLOOR) {
        this.FLOOR = FLOOR;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
