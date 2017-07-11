package com.dophin.weichat_article.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.mine.fragment.CodeLeftFragment;
import com.dophin.weichat_article.mine.fragment.CodeRightFragment;
import com.dophin.weichat_article.mine.fragment.RankFragment;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.widget.SharePopu;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.WaringDialog;
import com.flyco.tablayout.SlidingTabLayout;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {



    @BindView(R.id.tr_tab)
    SlidingTabLayout trTab;
    @BindView(R.id.tr_vp)
    ViewPager trVp;
    @BindView(R.id.activity_tr)
    LinearLayout activityTr;
    @BindView(R.id.tab_ll)
    LinearLayout tabLl;

    private final String[] mTitles = new String[]{
            "邀请学徒", "我的学徒"
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tr);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("邀请好友").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();
//        tabLl.setVisibility(View.VISIBLE);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        trVp.setAdapter(mAdapter);
        trTab.setViewPager(trVp, mTitles);
        trTab.setCurrentTab(0);

    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new CodeLeftFragment();
            } else if (position == 1){
                return new CodeRightFragment();
            }else{
                return RankFragment.getInstance("3");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
