package com.dophin.weichat_article.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dophin.weichat_article.db.ChannelItem;
import com.dophin.weichat_article.home.fragment.NewsFragment;

import java.util.List;

/**
 * Created by caiguoqing on 2017/2/22.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<ChannelItem> list;

    public HomePagerAdapter(FragmentManager fm,List<ChannelItem> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.getInstance(list.get(position).getName());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
