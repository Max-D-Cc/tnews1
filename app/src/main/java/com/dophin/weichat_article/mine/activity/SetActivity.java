package com.dophin.weichat_article.mine.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.LoginActivity;
import com.dophin.weichat_article.main.activity.MainActivity;
import com.dophin.weichat_article.main.bean.DownLoad;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.UpDialog;
import com.dophin.weichat_article.widget.WaringDialog;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {


    @BindView(R.id.set_user)
    RelativeLayout setUser;
    @BindView(R.id.set_version)
    RelativeLayout setVersion;
    @BindView(R.id.set_loginout)
    TextView setLoginout;
    private SubscriberOnNextListener<DownLoad> subscriberOnNextListener;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("设置").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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

        //                Log.e("cai",downLoad.getVISION());
        subscriberOnNextListener = new SubscriberOnNextListener<DownLoad>() {
            @Override
            public void onNext(final DownLoad downLoad) {
//                Log.e("cai",downLoad.getVISION());
                int versionCode = getVersion();
                int code = Integer.parseInt(downLoad.getVISION());

                if (code > versionCode){

                    final WaringDialog dialog = new WaringDialog(SetActivity.this);
                    dialog.setTitle("版本更新").setContent("检测您的应用不是最新版本,是否更新到最新版本").setAgree("立即更新").setOnAgreeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            UpDialog upDataDialog = new UpDialog(SetActivity.this,downLoad.getADDRESS());
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.show();
                        }
                    }).setDisAgree("以后再说").setOnDisAgreeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    }).show();

                }else{
                    ToastUtils.getInstance().ToastShow(SetActivity.this,"当前已经是最新版本");
                }
            }
        };
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.set_user, R.id.set_version,R.id.set_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_user:
                nextToActivity(UserInfoActivity.class);
                break;
            case R.id.set_version:


                HttpMethods.getInstance().getDownLoad(new ProgressSubscriber<DownLoad>(subscriberOnNextListener,SetActivity.this,1),"1");

                break;
            case R.id.set_loginout:
                SPUtils.setParam(this,"user","islogin",false);
                next2Activity(LoginActivity.class);
                break;
        }
    }


}
