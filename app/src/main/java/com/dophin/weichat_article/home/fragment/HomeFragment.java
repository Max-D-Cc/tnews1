package com.dophin.weichat_article.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.db.ChannelActivity;
import com.dophin.weichat_article.db.ChannelItem;
import com.dophin.weichat_article.db.ChannelManage;
import com.dophin.weichat_article.home.adapter.HomePagerAdapter;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.utils.SharePerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.home_tab)
    TabLayout homeTab;
    @BindView(R.id.home_edit)
    ImageView homeEdit;
    @BindView(R.id.home_vp)
    ViewPager homeVp;
    boolean isLoading;

    private List<String> list = new ArrayList<>();
    private List<String> strs = new ArrayList<>();
    private List<TextView> tvs = new ArrayList<>();
    private List<ChannelItem> channelItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_home, null);
        ButterKnife.bind(this, inflate);

        initView();
        return inflate;
    }

    private void initView() {

        SharePerUtils.putBoolean(mActivity,"isEdite",true);
//        for (int j = 0; j <  8; j++) {
//
//            strs.add("page" + j);
//        }
//
//        for (int j = 0; j < strs.size() ; j++) {
//            TextView tv = new TextView(mActivity);
//            tvs.add(tv);
//        }
//
//        homeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        MainViewPager pager =  new MainViewPager(mActivity,strs,tvs);
//        homeVp.setAdapter(pager);
//        homeTab.setupWithViewPager(homeVp);


        homeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ChannelActivity.class));
                SharePerUtils.putBoolean(mActivity,"isEdite",true);
//                Toast.makeText(mActivity,"点击了",Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
//        Random random =  new Random();
//        int ran = random.nextInt(10);
//        strs.clear();
//        for (int j = 0; j < ran + 8; j++) {
//
//            strs.add("page" + j);
//        }
//
//        for (int j = 0; j < strs.size() ; j++) {
//            TextView tv = new TextView(mActivity);
//            tvs.add(tv);
//        }tv
        boolean isEdite = SharePerUtils.getBoolean(mActivity, "isEdite", false);

        if (isEdite){
            SharePerUtils.putBoolean(mActivity,"isEdite",false);
            List<ChannelItem> userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(
                    Application.getInstance().getSQLHelper()).getUserChannel());

            homeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
            HomePagerAdapter pager =  new HomePagerAdapter(getChildFragmentManager(),userChannelList);
            homeVp.setAdapter(pager);
            homeTab.setupWithViewPager(homeVp);
        }

    }
}
