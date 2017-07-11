package com.dophin.weichat_article.http;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/6/1.
 */

public class HttpResult<T> {
    private String MESSAGE;
    private int CODE;
    private T DATA;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public T getDATA() {
        return DATA;
    }

    public void setDATA(T DATA) {
        this.DATA = DATA;
    }
}
