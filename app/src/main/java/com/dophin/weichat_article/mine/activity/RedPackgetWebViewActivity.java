package com.dophin.weichat_article.mine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.RedPackage;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.dophin.weichat_article.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedPackgetWebViewActivity extends BaseActivity {


    @BindView(R.id.rp_webView)
    WebView rpWebView;
    private SubscriberOnNextListener<RedPackage> listener;
    private String orderId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_red_packget_web_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        rpWebView.getSettings().setJavaScriptEnabled(true);
        rpWebView.setWebViewClient(new WebViewClient());
        rpWebView.setWebChromeClient(new WebChromeClient());
        rpWebView.getSettings().setLoadWithOverviewMode(true);
        rpWebView.getSettings().setSupportZoom(true);
        rpWebView.getSettings().setBuiltInZoomControls(true);
        rpWebView.getSettings().setUseWideViewPort(true);
        rpWebView.loadUrl("http://www.baidu.com");

        listener = new SubscriberOnNextListener<RedPackage>() {
            @Override
            public void onNext(RedPackage redPackage) {
                if (redPackage.getSTATUS() == 1){
                    SharePerUtils.putLong(RedPackgetWebViewActivity.this, "getTime", System.currentTimeMillis());
                    ToastUtils.getInstance().ToastShow(RedPackgetWebViewActivity.this,"恭喜您获得" + redPackage.getSCORE() + "星币");
                }else if(redPackage.getSTATUS() == 3){
                    ToastUtils.getInstance().ToastShow(RedPackgetWebViewActivity.this,"今日领取次数已达上限,请明日再来");
                }
            }
        };

        long getTime = SharePerUtils.getLong(this, "getTime", 0);
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
        long curTime = System.currentTimeMillis();

        if (curTime - getTime > 1000 * 3600){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    HttpMethods.getInstance().getRedPackage(new ProgressSubscriber<RedPackage>(listener,RedPackgetWebViewActivity.this,1), orderId);
                }
            },5000);
        }

    }

    @Override
    public void addActivity() {

    }

}
