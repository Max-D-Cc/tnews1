package com.dophin.weichat_article.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by caiguoqing on 2017/2/13.
 */

public class MainViewPager extends PagerAdapter {


    private List<String> list;
    private Context context;
    private List<TextView> tvs;

    public MainViewPager(Context context, List<String> list, List<TextView> tvs){
        this.list = list;
        this.context = context;
        this.tvs = tvs;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tvs.get(position).setText(list.get(position));
        container.addView(tvs.get(position));
        return tvs.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(tvs.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
