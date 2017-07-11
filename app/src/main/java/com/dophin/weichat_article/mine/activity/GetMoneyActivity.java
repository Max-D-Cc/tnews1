package com.dophin.weichat_article.mine.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.widget.TitleBuilder;

public class GetMoneyActivity extends BaseActivity {



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_get_money);

    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("如何赚钱").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
}
