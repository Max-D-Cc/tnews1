package com.dophin.weichat_article.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caiguoqing on 2017/3/10.
 */

public class News implements Serializable{

    private List<InnerNews> FINDBZYHTML_XGTJ;
    private String MSG;
    private int STATUS;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public List<InnerNews> getFINDBZYHTML_XGTJ() {
        return FINDBZYHTML_XGTJ;
    }

    public void setFINDBZYHTML_XGTJ(List<InnerNews> FINDBZYHTML_XGTJ) {
        this.FINDBZYHTML_XGTJ = FINDBZYHTML_XGTJ;
    }


}
