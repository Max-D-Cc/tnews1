package com.dophin.weichat_article.mine.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.mine.bean.Rank;
import com.dophin.weichat_article.mine.fragment.RankFragment;
import com.dophin.weichat_article.mine.fragment.TrLeftFragment;
import com.dophin.weichat_article.mine.fragment.TrRightFragment;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.widget.WaringDialog;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankActivity extends BaseActivity {

    @BindView(R.id.tr_tab)
    SlidingTabLayout trTab;
    @BindView(R.id.tr_vp)
    ViewPager trVp;
    @BindView(R.id.activity_tr)
    LinearLayout activityTr;
    @BindView(R.id.tab_ll)
    LinearLayout tabLl;

    private final String[] mTitles = new String[]{
            "阅读达人", "推广大神","赚金圣手"
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tr);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("光荣榜").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).setRightImage(R.mipmap.guize).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WaringDialog dialog = new WaringDialog(RankActivity.this);
                dialog.setTitle("光荣榜规则").setContent("阅读达人：周阅读次数前30名\n1-3名：奖励3000星币\n4-10名：奖励2000星币\n11-20名：奖励1000星币 \n\n" +
                        "推广大神：周推广人数前30名\n1-3名：奖励10000星币\n4-10名：奖励5000星币\n11-20名：奖励2000星币\n\n赚金圣手：周赚取奖励前30名\n1-3名：奖励3000星币\n4-10名：奖励2000星币\n11-20名：奖励1000星币" ).setCenterAgree("确定").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }).build();
        tabLl.setVisibility(View.VISIBLE);

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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return RankFragment.getInstance("1");
            } else if (position == 1){
                return RankFragment.getInstance("2");
            }else{
                return RankFragment.getInstance("3");
            }
        }
    }
}
