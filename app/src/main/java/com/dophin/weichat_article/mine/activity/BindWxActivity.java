package com.dophin.weichat_article.mine.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.LoginActivity;
import com.dophin.weichat_article.login.bean.PhoneLoginBean;
import com.dophin.weichat_article.login.bean.YunCode;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindWxActivity extends BaseActivity {

    @BindView(R.id.bwx_tel)
    EditText bwxTel;
    @BindView(R.id.bwx_code)
    EditText bwxCode;
    @BindView(R.id.bwx_sendcode)
    TextView bwxSendcode;
    @BindView(R.id.bwx_ok)
    TextView bwxOk;

    private String unionid;
    private String screen_name;
    private String profile_image_url;
    private SubscriberOnNextListener<YunCode> yunCodeSubscriberOnNextListener;
    private SubscriberOnNextListener<Statue> wxListener;
    private String tel;
    private String code;
    private String codeid;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bind_wx);
        ButterKnife.bind(this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(BindWxActivity.this).setShareConfig(config);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("绑定微信").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
        yunCodeSubscriberOnNextListener = new SubscriberOnNextListener<YunCode>() {
             @Override
             public void onNext(YunCode yunCode) {
                 if (yunCode.getSTATUS() == 1) {
                     ToastUtils.getInstance().ToastShow(BindWxActivity.this, "验证码发送成功,请注意查收短信");
                     SPUtils.setParam(BindWxActivity.this, "user", "codeid", yunCode.getSMSMESSAGESID());

                 } else if (yunCode.getSTATUS() == 2) {
                     ToastUtils.getInstance().ToastShow(BindWxActivity.this, "当前手机号发送验证码次数过多,请稍后再试");
                 } else {
                     ToastUtils.getInstance().ToastShow(BindWxActivity.this, "服务器异常,请稍后再试");
                 }
             }
         };

        //                  SPUtils.setParam(LoginActivity.this,"user","username",loginEtPhone.getText().toString().trim());
        wxListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1) {
                    SPUtils.setParam(BindWxActivity.this,"user","isBindWx","0");
                    ToastUtils.getInstance().ToastShow(BindWxActivity.this,"绑定成功");
                    finish2Activity();
                }else if(statue.getSTATUS() == 2){
                    ToastUtils.getInstance().ToastShow(BindWxActivity.this, "绑定失败,该微信号已绑定其他账号");
                }else{
                    ToastUtils.getInstance().ToastShow(BindWxActivity.this, "绑定失败");
                }
            }
        };

    }

    @Override
    public void addActivity() {

    }

    private TimeCount timeCount;


    @OnClick({R.id.bwx_sendcode, R.id.bwx_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bwx_sendcode:
//                submit(0);
                break;
            case R.id.bwx_ok:
                UMShareAPI.get(BindWxActivity.this).getPlatformInfo(BindWxActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
//                submit(1);
                break;
        }
    }

    private void submit(int type){
        tel = bwxTel.getText().toString().trim();
        code = bwxCode.getText().toString().trim();
        if (tel.length() != 11 || tel.equals("请输入手机号")){
            ToastUtils.getInstance().ToastShow(BindWxActivity.this,"请输入正确的手机号");
            return;
        }


        if (type == 1){
            if (code.length() != 6  ||  code.equals("请输入验证码")){
                ToastUtils.getInstance().ToastShow(BindWxActivity.this,"请输入正确的验证码");
                return;
            }

            codeid = (String) SPUtils.getParam(this, "user", "codeid", "");


        }else{
            timeCount = new TimeCount(60000,1000);
            timeCount.start();
            HttpMethods.getInstance().getYunCode(new ProgressSubscriber<YunCode>(yunCodeSubscriberOnNextListener,BindWxActivity.this), tel);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            bwxSendcode.setText("获取验证码");
            bwxSendcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            bwxSendcode.setText(millisUntilFinished / 1000 + "秒");

        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            ToastUtils.getInstance().ToastShow(BindWxActivity.this,"授权开始,请稍后");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            unionid = data.get("unionid");
            screen_name = data.get("screen_name");
            String gender = data.get("gender");
            profile_image_url = data.get("profile_image_url");
            String userName = (String) SPUtils.getParam(BindWxActivity.this, "user", "username", "");

            HttpMethods.getInstance().getTelBindWx(new ProgressSubscriber<Statue>(wxListener,BindWxActivity.this),unionid,screen_name,profile_image_url,userName);
//            HttpMethods.getInstance().getNewWxLogin(new ProgressSubscriber<PhoneLoginBean>(wxListener,BindWxActivity.this,1), unionid, screen_name, profile_image_url);

//            Log.e("cai", unionid + screen_name);


//           Toast.makeText(getApplicationContext(), "Authorize succeed" + unionid  + " --- " + screen_name, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.getInstance().ToastShow(BindWxActivity.this,"授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.getInstance().ToastShow(BindWxActivity.this,"取消授权");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
