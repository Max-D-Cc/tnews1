package com.dophin.weichat_article.login.bean;


/**
 * Created by caiguoqing on 2017/3/9.
 */

public class YunCode {

    private int STATUS;
    private String SMSMESSAGESID;
    private String MSG;

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getSMSMESSAGESID() {
        return SMSMESSAGESID;
    }

    public void setSMSMESSAGESID(String SMSMESSAGESID) {
        this.SMSMESSAGESID = SMSMESSAGESID;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
