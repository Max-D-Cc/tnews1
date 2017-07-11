package com.dophin.weichat_article.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.OffLineDetailAdapter;
import com.dophin.weichat_article.mine.bean.OffLine;
import com.dophin.weichat_article.mine.bean.OffLineList;
import com.dophin.weichat_article.utils.EndLessOnScrollListener;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffLineDetailActivity extends BaseActivity {


    @BindView(R.id.old_rv)
    RecyclerView oldRv;

    private List<OffLine> list = new ArrayList<>();
    private int type;
    private String userId;
    private String orderId;
    private SubscriberOnNextListener<OffLineList> listener;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_off_line_detail);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("下线详情").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
        userId = (String) SPUtils.getParam(this, "user", "userid", "");
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");

        final OffLineDetailAdapter adapter =  new OffLineDetailAdapter(this,list,type);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        oldRv.setLayoutManager(manager);
        oldRv.setItemAnimator(new DefaultItemAnimator());
        oldRv.setAdapter(adapter);

        listener = new SubscriberOnNextListener<OffLineList>() {
            @Override
            public void onNext(OffLineList offLineList) {
                if (offLineList.getFIND_LINE() != null){
                    list.addAll(offLineList.getFIND_LINE());
                }
                adapter.notifyDataSetChanged();
            }
        };

        oldRv.setOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                HttpMethods.getInstance().getOffLine(new ProgressSubscriber<OffLineList>(listener,OffLineDetailActivity.this,1),String.valueOf(userId),String.valueOf(currentPage),String.valueOf(type), orderId);
            }
        });

        HttpMethods.getInstance().getOffLine(new ProgressSubscriber<OffLineList>(listener,OffLineDetailActivity.this,1),String.valueOf(userId),String.valueOf(1),String.valueOf(type), orderId);

    }

    @Override
    public void addActivity() {

    }

}
