package com.dophin.weichat_article.mine.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.mine.fragment.TrLeftFragment;
import com.dophin.weichat_article.mine.fragment.TrRightFragment;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.R;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrActivity extends BaseActivity {

    @BindView(R.id.tr_tab)
    SlidingTabLayout trTab;
    @BindView(R.id.tr_vp)
    ViewPager trVp;
    @BindView(R.id.activity_tr)
    LinearLayout activityTr;

    private final String[] mTitles = new String[]{
            "今日开宝", "累计经验"
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tr);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("经验宝箱").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();

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

                return new TrLeftFragment();
            } else {
                return new TrRightFragment();
            }
        }
    }
}
