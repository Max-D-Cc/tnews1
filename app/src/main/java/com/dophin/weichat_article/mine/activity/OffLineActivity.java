package com.dophin.weichat_article.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffLineActivity extends BaseActivity {

    @BindView(R.id.ol_one)
    RelativeLayout olOne;
    @BindView(R.id.ol_two)
    RelativeLayout olTwo;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_off_line);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("我的学徒").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.ol_one, R.id.ol_two})
    public void onClick(View view) {
        Intent intent = new Intent(OffLineActivity.this,OffLineDetailActivity.class);
        switch (view.getId()) {
            case R.id.ol_one:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.ol_two:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
        }
        overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }
}
