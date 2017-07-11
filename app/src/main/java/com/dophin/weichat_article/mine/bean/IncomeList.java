package com.dophin.weichat_article.mine.bean;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/11.
 */

public class IncomeList {

    private List<IncomeLeft> FIND_POINTS;
    private List<IncomeRight> FIND_ZHUANXI;

    public List<IncomeLeft> getFIND_POINTS() {
        return FIND_POINTS;
    }

    public void setFIND_POINTS(List<IncomeLeft> FIND_POINTS) {
        this.FIND_POINTS = FIND_POINTS;
    }

    public List<IncomeRight> getFIND_ZHUANXI() {
        return FIND_ZHUANXI;
    }

    public void setFIND_ZHUANXI(List<IncomeRight> FIND_ZHUANXI) {
        this.FIND_ZHUANXI = FIND_ZHUANXI;
    }
}
