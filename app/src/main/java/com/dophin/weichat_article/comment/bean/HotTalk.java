package com.dophin.weichat_article.comment.bean;

import com.dophin.weichat_article.home.bean.InnerNews;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caiguoqing on 2017/3/17.
 */

public class HotTalk implements Serializable{

    private List<InnerNews> ADD_REYI;

    public List<InnerNews> getADD_REYI() {
        return ADD_REYI;
    }

    public void setADD_REYI(List<InnerNews> ADD_REYI) {
        this.ADD_REYI = ADD_REYI;
    }
}
