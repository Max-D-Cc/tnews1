package com.dophin.weichat_article.mine.bean;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/14.
 */

public class RankList {

    private String MSG;
    private List<Rank> TUIG;
    private List<Rank> ZHUANJ;
    private List<Rank> READ;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public List<Rank> getZHUANJ() {
        return ZHUANJ;
    }

    public void setZHUANJ(List<Rank> ZHUANJ) {
        this.ZHUANJ = ZHUANJ;
    }

    public List<Rank> getREAD() {
        return READ;
    }

    public void setREAD(List<Rank> READ) {
        this.READ = READ;
    }

    public List<Rank> getTUIG() {
        return TUIG;
    }

    public void setTUIG(List<Rank> TUIG) {
        this.TUIG = TUIG;
    }
}
