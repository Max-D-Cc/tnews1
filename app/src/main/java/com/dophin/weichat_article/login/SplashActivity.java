package com.dophin.weichat_article.login;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.home.bean.BdId;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.main.activity.MainActivity;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;
    private SplashAD splashAD;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadSplashAd();
            }
        }, 2000);
    }

    private void loadSplashAd(){
        SharePerUtils.putBoolean(this, "isEdite", true);
        final boolean isLogin = (boolean) SPUtils.getParam(this, "user", "islogin", false);

        splashAD = new SplashAD(this, activitySplash, "1106079814", "2070827275717976", new SplashADListener() {
            @Override
            public void onADDismissed() {
                if (isLogin) {
                    next2Activity(MainActivity.class);
                } else {
                    next2Activity(LoginActivity.class);
                }
            }

            @Override
            public void onNoAD(int i) {
                Log.e("cai","error" + i);
                if (isLogin) {
                    next2Activity(MainActivity.class);
                } else {
                    next2Activity(LoginActivity.class);
                }
            }

            @Override
            public void onADPresent() {

            }

            @Override
            public void onADClicked() {
                if (isLogin) {
                    next2Activity(MainActivity.class);
                } else {
                    next2Activity(LoginActivity.class);
                }
            }

            @Override
            public void onADTick(long l) {

            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

        SubscriberOnNextListener<BdId> subscriberOnNextListener = new SubscriberOnNextListener<BdId>() {
            @Override
            public void onNext(BdId bdId) {
                SharePerUtils.putString(SplashActivity.this, "appid", bdId.getAppid());
                SharePerUtils.putString(SplashActivity.this, "adid", bdId.getAdid());
                SharePerUtils.putString(SplashActivity.this, "smgappid", bdId.getShenmgappid());
                SharePerUtils.putString(SplashActivity.this, "smgadid", bdId.getShenmgadid());

            }
        };

        HttpMethods.getInstance().getBdid(new ProgressSubscriber<BdId>(subscriberOnNextListener, SplashActivity.this, 1));

    }

    @Override
    public void addActivity() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
