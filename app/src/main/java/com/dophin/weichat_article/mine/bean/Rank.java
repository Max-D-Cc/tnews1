package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/14.
 */

public class Rank {

    private String TEL;
    private int READHTML_QIDAY;
    private String NICKNAME;
    private String IMG;
    private int TUIG_QIDAY;
    private double POINTS_QIDAY;

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public int getREADHTML_QIDAY() {
        return READHTML_QIDAY;
    }

    public void setREADHTML_QIDAY(int READHTML_QIDAY) {
        this.READHTML_QIDAY = READHTML_QIDAY;
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

    public int getTUIG_QIDAY() {
        return TUIG_QIDAY;
    }

    public void setTUIG_QIDAY(int TUIG_QIDAY) {
        this.TUIG_QIDAY = TUIG_QIDAY;
    }

    public double getPOINTS_QIDAY() {
        return POINTS_QIDAY;
    }

    public void setPOINTS_QIDAY(double POINTS_QIDAY) {
        this.POINTS_QIDAY = POINTS_QIDAY;
    }
}
