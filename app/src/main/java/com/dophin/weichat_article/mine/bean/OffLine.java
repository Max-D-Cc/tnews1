package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/14.
 */

public class OffLine {

    private String TEL;
    private int LINE_SJ1;
    private int ID;
    private int LINE_SJ2;
    private String NICKNAME;
    private String IMG;

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public int getLINE_SJ1() {
        return LINE_SJ1;
    }

    public void setLINE_SJ1(int LINE_SJ1) {
        this.LINE_SJ1 = LINE_SJ1;
    }

    public int getLINE_SJ2() {
        return LINE_SJ2;
    }

    public void setLINE_SJ2(int LINE_SJ2) {
        this.LINE_SJ2 = LINE_SJ2;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }
}
