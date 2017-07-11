package com.dophin.weichat_article.mine.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.mine.fragment.IncomeFragment;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.widget.WaringDialog;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeActivity extends BaseActivity {

    @BindView(R.id.income_star)
    TextView incomeStar;
    @BindView(R.id.income_hl)
    TextView incomeHl;
    @BindView(R.id.income_money)
    TextView incomeMoney;
    @BindView(R.id.income_detail)
    TextView incomeDetail;
    @BindView(R.id.income_tab)
    SlidingTabLayout incomeTab;
    @BindView(R.id.income_vp)
    ViewPager incomeVp;
    @BindView(R.id.activity_income)
    LinearLayout activityIncome;
    private final String[] mTitles = new String[]{
            "星币", "现金"
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_income);
        ButterKnife.bind(this);

    }

    @Override
    public void initViews() {

        new TitleBuilder(this).setTitleText("收入明细").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
        IncomePagerAdapter mAdapter = new IncomePagerAdapter(getSupportFragmentManager());
        incomeVp.setAdapter(mAdapter);
        incomeTab.setViewPager(incomeVp, mTitles);
        incomeTab.setCurrentTab(0);

        String yesXb = (String) SPUtils.getParam(this, "user", "yesxb", "");
        String bili = (String) SPUtils.getParam(this, "user", "bili", "");
        String yesPoint = (String) SPUtils.getParam(this, "user", "yespoint", "");
        incomeHl.setText(bili);
        incomeMoney.setText(yesPoint);
        incomeStar.setText(yesXb);

        incomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WaringDialog dialog = new WaringDialog(IncomeActivity.this);
                dialog.setTitle("汇率详情").setContent("金币转换汇率受每日广告收益影响，上下会有浮动").setCenterAgree("了解").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });


    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }

    private class IncomePagerAdapter extends FragmentPagerAdapter {

        public IncomePagerAdapter(FragmentManager fm) {
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
                return new IncomeFragment().getInstance("1");
            } else {
                return new IncomeFragment().getInstance("2");
            }
        }
    }

}
