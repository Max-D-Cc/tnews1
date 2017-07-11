package com.dophin.weichat_article.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caiguoqing on 2017/3/18.
 */

public class CommentList implements Serializable{
    private List<Comment>  REPLY;

    public List<Comment> getREPLY() {
        return REPLY;
    }

    public void setREPLY(List<Comment> REPLY) {
        this.REPLY = REPLY;
    }
}
