package com.dophin.weichat_article.mine.bean;

/**
 * Created by caiguoqing on 2017/3/27.
 */

public class HonorType {

    private String img;
    private int type;

    public HonorType(String img, int type) {
        this.img = img;
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
