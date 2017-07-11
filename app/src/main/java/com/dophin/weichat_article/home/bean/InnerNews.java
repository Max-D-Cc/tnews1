package com.dophin.weichat_article.home.bean;

import com.ak.android.engine.nav.NativeAd;
import com.baidu.mobad.feeds.NativeResponse;

import java.io.Serializable;

/**
 * Created by caiguoqing on 2017/3/10.
 */

public class InnerNews  implements Serializable{
    private String TIME;
    private int SHOWTYPE;
    private String IMAGE1;
    private String UID; //文章唯一标示
    private String IMAGE2;
    private String IMAGE3;
    private int ID;
    private String COMMENTNUM;
    private String CREATTIME;
    private int STATUE;
    private String URL;
    private int ADTYPE;
    private String TITLE;
    private int CAINUM;
    private int HCLICKZAN;
    public boolean isBdNative = false;
    public NativeResponse bdNative;
    public boolean is360Native = false;
    public Native360 native360;
    public NativeAd nativeAd360;


    public int getHCLICKZAN() {
        return HCLICKZAN;
    }

    public void setHCLICKZAN(int HCLICKZAN) {
        this.HCLICKZAN = HCLICKZAN;
    }

    public int getCAINUM() {
        return CAINUM;
    }

    public void setCAINUM(int CAINUM) {
        this.CAINUM = CAINUM;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getSHOWTYPE() {
        return SHOWTYPE;
    }

    public void setSHOWTYPE(int SHOWTYPE) {
        this.SHOWTYPE = SHOWTYPE;
    }

    public String getIMAGE1() {
        return IMAGE1;
    }

    public void setIMAGE1(String IMAGE1) {
        this.IMAGE1 = IMAGE1;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getIMAGE2() {
        return IMAGE2;
    }

    public void setIMAGE2(String IMAGE2) {
        this.IMAGE2 = IMAGE2;
    }

    public String getIMAGE3() {
        return IMAGE3;
    }

    public void setIMAGE3(String IMAGE3) {
        this.IMAGE3 = IMAGE3;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCOMMENTNUM() {
        return COMMENTNUM;
    }

    public void setCOMMENTNUM(String COMMENTNUM) {
        this.COMMENTNUM = COMMENTNUM;
    }

    public int getSTATUE() {
        return STATUE;
    }

    public void setSTATUE(int STATUE) {
        this.STATUE = STATUE;
    }

    public String getCREATTIME() {
        return CREATTIME;
    }

    public void setCREATTIME(String CREATTIME) {
        this.CREATTIME = CREATTIME;
    }

    public int getADTYPE() {
        return ADTYPE;
    }

    public void setADTYPE(int ADTYPE) {
        this.ADTYPE = ADTYPE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
