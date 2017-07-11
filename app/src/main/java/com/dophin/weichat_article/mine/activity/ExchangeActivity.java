package com.dophin.weichat_article.mine.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.ExchangeAdapter;
import com.dophin.weichat_article.mine.bean.Exchange;
import com.dophin.weichat_article.mine.bean.ExchangeList;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeActivity extends BaseActivity {

    @BindView(R.id.exchang_borad)
    TextView exchangBorad;
    @BindView(R.id.exchang_alipay)
    TextView exchangAlipay;
    @BindView(R.id.exchang_phone)
    TextView exchangPhone;
    @BindView(R.id.exchang_qq)
    TextView exchangQq;
    @BindView(R.id.exchang_rv)
    RecyclerView exchangRv;
    @BindView(R.id.activity_exchange)
    LinearLayout activityExchange;

    private List<Exchange> list = new ArrayList<>();
    private SubscriberOnNextListener<ExchangeList> listener;
    private ExchangeAdapter adapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("提现").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
        adapter = new ExchangeAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        exchangRv.setLayoutManager(manager);
        exchangRv.setItemAnimator(new DefaultItemAnimator());
        exchangRv.setAdapter(adapter);


        listener = new SubscriberOnNextListener<ExchangeList>() {
            @Override
            public void onNext(ExchangeList exchangeList) {
                list.clear();
                if (exchangeList.getFIND_EXCHANGE() != null && adapter != null){
                    list.addAll(exchangeList.getFIND_EXCHANGE());
                    adapter.notifyDataSetChanged();
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (listener != null){
            String orderId = (String) SPUtils.getParam(ExchangeActivity.this, "user", "orderid", "");
            HttpMethods.getInstance().getExchangeList(new ProgressSubscriber<ExchangeList>(listener,ExchangeActivity.this,1),orderId);
        }
    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.exchang_alipay, R.id.exchang_phone, R.id.exchang_qq})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(ExchangeActivity.this,ExchangeDetailActivity.class);
        switch (view.getId()) {
            case R.id.exchang_alipay:
                intent.putExtra("type",1);
                break;
            case R.id.exchang_phone:
                intent.putExtra("type",0);
                break;
            case R.id.exchang_qq:
                intent.putExtra("type",2);
                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }
}
