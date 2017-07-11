package com.dophin.weichat_article.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.bean.PhoneLoginBean;
import com.dophin.weichat_article.main.activity.MainActivity;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_forgetpass)
    TextView loginForgetpass;
    @BindView(R.id.login_login)
    TextView loginLogin;
    @BindView(R.id.login_wx)
    TextView loginWx;
    @BindView(R.id.login_regist)
    TextView loginRegist;
    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(R.id.login_et_pwd)
    EditText loginEtPwd;
    private SubscriberOnNextListener<PhoneLoginBean> listener;
    private SubscriberOnNextListener<PhoneLoginBean> wxListener;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private String unionid;
    private String screen_name;
    private String profile_image_url;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
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
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(LoginActivity.this).setShareConfig(config);


        listener = new SubscriberOnNextListener<PhoneLoginBean>() {
            @Override
            public void onNext(PhoneLoginBean phoneLoginBean) {
                Log.e("cai",phoneLoginBean.getSTATUS() + "");
               if (phoneLoginBean.getSTATUS() == 1){
                    SPUtils.setParam(LoginActivity.this,"user","userid",String.valueOf(phoneLoginBean.getID()));
                    SPUtils.setParam(LoginActivity.this,"user","username",loginEtPhone.getText().toString().trim());
                    SPUtils.setParam(LoginActivity.this,"user","nickname",phoneLoginBean.getNICKNAME());
                    SPUtils.setParam(LoginActivity.this,"user","orderid",phoneLoginBean.getORDERID());
                    SPUtils.setParam(LoginActivity.this,"user","img",phoneLoginBean.getIMG());
                    SPUtils.setParam(LoginActivity.this,"user","code",String.valueOf(phoneLoginBean.getYQM()));
                    SPUtils.setParam(LoginActivity.this,"user","todaypoint",String.valueOf(phoneLoginBean.getPOINTS_TODAY()));
                    SPUtils.setParam(LoginActivity.this,"user","usepoint",String.valueOf(phoneLoginBean.getPOINTS_SURPLUS()));
                    SPUtils.setParam(LoginActivity.this,"user","totalpoint",String.valueOf(phoneLoginBean.getPOINTS_TOTAL()));
                    SPUtils.setParam(LoginActivity.this,"user","yespoint",String.valueOf(phoneLoginBean.getPOINTS_YESTERDAY()));
                    SPUtils.setParam(LoginActivity.this,"user","islogin",true);
                   if (phoneLoginBean.getUNIONID().equals("") || phoneLoginBean.getUNIONID().equals("null") || phoneLoginBean.getUNIONID() == null){
                       SPUtils.setParam(LoginActivity.this,"user","isBindWx","1");
                       Log.e("cai","unin1: " + phoneLoginBean.getUNIONID());
                   }else{
                       SPUtils.setParam(LoginActivity.this,"user","isBindWx","0");
                       Log.e("cai","unin2: " + phoneLoginBean.getUNIONID());
                   }
                    next2Activity(MainActivity.class);
                }else if(phoneLoginBean.getSTATUS() == 2){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"用户不存在");
                }else if(phoneLoginBean.getSTATUS() == 3){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"密码错误");
                }else{
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"服务器异常");
                }
            }
        };
        wxListener = new SubscriberOnNextListener<PhoneLoginBean>() {
            @Override
            public void onNext(PhoneLoginBean phoneLoginBean) {
//                if(phoneLoginBean.getSTATUS() == 1) {
//                    Intent intent = new Intent(LoginActivity.this, BindTelActivity.class);
//                    intent.putExtra("union", unionid);
//                    intent.putExtra("wxname", screen_name);
//                    intent.putExtra("imageUrl", profile_image_url);
//                    startActivity(intent);
//                    finish();
//                }else if(phoneLoginBean.getSTATUS() == 2){
                    SPUtils.setParam(LoginActivity.this,"user","userid",String.valueOf(phoneLoginBean.getID()));
//                  SPUtils.setParam(LoginActivity.this,"user","username",loginEtPhone.getText().toString().trim());
                    SPUtils.setParam(LoginActivity.this,"user","nickname",phoneLoginBean.getNICKNAME());
                    SPUtils.setParam(LoginActivity.this,"user","orderid",phoneLoginBean.getORDERID());
                    SPUtils.setParam(LoginActivity.this,"user","img",phoneLoginBean.getIMG());
                    SPUtils.setParam(LoginActivity.this,"user","code",String.valueOf(phoneLoginBean.getYQM()));
                    SPUtils.setParam(LoginActivity.this,"user","todaypoint",String.valueOf(phoneLoginBean.getPOINTS_TODAY()));
                    SPUtils.setParam(LoginActivity.this,"user","usepoint",String.valueOf(phoneLoginBean.getPOINTS_SURPLUS()));
                    SPUtils.setParam(LoginActivity.this,"user","totalpoint",String.valueOf(phoneLoginBean.getPOINTS_TOTAL()));
                    SPUtils.setParam(LoginActivity.this,"user","yespoint",String.valueOf(phoneLoginBean.getPOINTS_YESTERDAY()));
                    SPUtils.setParam(LoginActivity.this,"user","islogin",true);
                    SPUtils.setParam(LoginActivity.this,"user","isBindWx","");
                    next2Activity(MainActivity.class);
//                }
            }
        };

        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){

                }else if(statue.getSTATUS() == 2){
                    next2Activity(MainActivity.class);
                }
            }
        };


    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }


    @OnClick({R.id.login_forgetpass, R.id.login_login, R.id.login_wx, R.id.login_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forgetpass:
                Intent intent = new Intent(this,RegistActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                overridePendingTransition(R.anim.right_center, R.anim.center_left);
                break;
            case R.id.login_login:
                if (TextUtils.isEmpty(loginEtPhone.getText().toString().trim())){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(loginEtPwd.getText().toString().trim())){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"请输入密码");
                    return;
                }
                if (loginEtPhone.getText().toString().trim().length() != 11){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"账号格式不对");
                    return;
                }

                if (loginEtPwd.getText().toString().length() < 6 || loginEtPwd.getText().toString().length() > 16 ){
                    ToastUtils.getInstance().ToastShow(LoginActivity.this,"密码格式不对");
                    return;
                }

                HttpMethods.getInstance().getPhoneLogin(new ProgressSubscriber<PhoneLoginBean>(listener, LoginActivity.this), loginEtPhone.getText().toString().trim(), loginEtPwd.getText().toString());
                break;
            case R.id.login_wx:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.login_regist:
                next2Activity(RegistActivity.class);
                break;
        }
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            ToastUtils.getInstance().ToastShow(LoginActivity.this,"授权开始,请稍后");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            unionid = data.get("unionid");
            screen_name = data.get("screen_name");
            String gender = data.get("gender");
            profile_image_url = data.get("profile_image_url");
//            ToastUtils.getInstance().ToastShow(LoginActivity.this,"unionid: " + unionid  + "  screen_name:" + screen_name);

            HttpMethods.getInstance().getWxLogin(new ProgressSubscriber<PhoneLoginBean>(wxListener,LoginActivity.this,1), unionid, screen_name, profile_image_url);

//            Log.e("cai", unionid + screen_name);


//           Toast.makeText(getApplicationContext(), "Authorize succeed" + unionid  + " --- " + screen_name, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.getInstance().ToastShow(LoginActivity.this,"授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.getInstance().ToastShow(LoginActivity.this,"取消授权");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
//            Application.getInstance().exit();;
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }
}
