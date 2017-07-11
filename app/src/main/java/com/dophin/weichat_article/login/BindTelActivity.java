package com.dophin.weichat_article.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.bean.PhoneLoginBean;
import com.dophin.weichat_article.login.bean.YunCode;
import com.dophin.weichat_article.main.activity.MainActivity;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindTelActivity extends BaseActivity {


    @BindView(R.id.bt_back)
    ImageView btBack;
    @BindView(R.id.bt_tel)
    EditText btTel;
    @BindView(R.id.bt_pwd)
    EditText btPwd;
    @BindView(R.id.bt_pwd_again)
    EditText btPwdAgain;
    @BindView(R.id.bt_code)
    EditText btCode;
    @BindView(R.id.bt_sendcode)
    TextView btSendcode;
    @BindView(R.id.bt_ok)
    TextView btOk;
    private TimeCount timeCount;
    private SubscriberOnNextListener<YunCode> yunCodeSubscriberOnNextListener;
    private SubscriberOnNextListener<PhoneLoginBean> regListener;
    private String union;
    private String iamgeUrl;
    private String name;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bind_tel);
        ButterKnife.bind(this);

        union = getIntent().getStringExtra("union");
        iamgeUrl = getIntent().getStringExtra("iamgeUrl");
        name = getIntent().getStringExtra("wxname");
    }

    @Override
    public void initViews() {

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
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "验证码发送成功,请注意查收短信");
                    SPUtils.setParam(BindTelActivity.this, "user", "codeid", yunCode.getSMSMESSAGESID());

                } else if (yunCode.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "当前手机号发送验证码次数过多,请稍后再试");
                } else {
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "服务器异常,请稍后再试");
                }
            }
        };


        regListener = new SubscriberOnNextListener<PhoneLoginBean>() {
            @Override
            public void onNext(PhoneLoginBean phoneLoginBean) {
                if (phoneLoginBean.getSTATUS() == 1) {
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "注册成功");
                    SPUtils.setParam(BindTelActivity.this, "user", "userid", phoneLoginBean.getID());
                    SPUtils.setParam(BindTelActivity.this, "user", "username", btTel.getText().toString().trim());
                    SPUtils.setParam(BindTelActivity.this, "user", "nickname", phoneLoginBean.getNICKNAME());
                    SPUtils.setParam(BindTelActivity.this, "user", "orderid", phoneLoginBean.getORDERID());
                    SPUtils.setParam(BindTelActivity.this, "user", "img", phoneLoginBean.getIMG());
                    SPUtils.setParam(BindTelActivity.this, "user", "code", phoneLoginBean.getYQM());
                    SPUtils.setParam(BindTelActivity.this, "user", "todaypoint", phoneLoginBean.getPOINTS_TODAY());
                    SPUtils.setParam(BindTelActivity.this, "user", "usepoint", String.valueOf(phoneLoginBean.getPOINTS_SURPLUS()));
                    SPUtils.setParam(BindTelActivity.this, "user", "totalpoint", String.valueOf(phoneLoginBean.getPOINTS_TOTAL()));
                    SPUtils.setParam(BindTelActivity.this, "user", "yespoint", String.valueOf(phoneLoginBean.getPOINTS_YESTERDAY()));
                    SPUtils.setParam(BindTelActivity.this, "user", "islogin", true);
                    next2Activity(MainActivity.class);
                } else if (phoneLoginBean.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "注册成功");
                    SPUtils.setParam(BindTelActivity.this, "user", "userid", phoneLoginBean.getID());
                    SPUtils.setParam(BindTelActivity.this, "user", "username", btTel.getText().toString().trim());
                    SPUtils.setParam(BindTelActivity.this, "user", "nickname", phoneLoginBean.getNICKNAME());
                    SPUtils.setParam(BindTelActivity.this, "user", "orderid", phoneLoginBean.getORDERID());
                    SPUtils.setParam(BindTelActivity.this, "user", "img", phoneLoginBean.getIMG());
                    SPUtils.setParam(BindTelActivity.this, "user", "code", phoneLoginBean.getYQM());
                    SPUtils.setParam(BindTelActivity.this, "user", "todaypoint", phoneLoginBean.getPOINTS_TODAY());
                    SPUtils.setParam(BindTelActivity.this, "user", "usepoint", String.valueOf(phoneLoginBean.getPOINTS_SURPLUS()));
                    SPUtils.setParam(BindTelActivity.this, "user", "totalpoint", String.valueOf(phoneLoginBean.getPOINTS_TOTAL()));
                    SPUtils.setParam(BindTelActivity.this, "user", "yespoint", String.valueOf(phoneLoginBean.getPOINTS_YESTERDAY()));
                    SPUtils.setParam(BindTelActivity.this, "user", "islogin", true);
                    next2Activity(MainActivity.class);
                } else if (phoneLoginBean.getSTATUS() == 3) {
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "手机号注册过账号且已绑定微信");
                } else if (phoneLoginBean.getSTATUS() == 7){
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "验证码填写错误");
                }else if (phoneLoginBean.getSTATUS() == 4){
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "微信账号已存在");
                }else{
                    ToastUtils.getInstance().ToastShow(BindTelActivity.this, "服务器异常");
                }

            }
        };



    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.bt_back, R.id.bt_sendcode, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_sendcode:
                submit(0);
                btSendcode.setClickable(false);
                break;
            case R.id.bt_ok:
                submit(1);
                break;
        }
    }

    private void submit(int type){
        String tel = btTel.getText().toString().trim();
        String pwd = btPwd.getText().toString().trim();
        String code = btCode.getText().toString().trim();
        if (tel.length() != 11 || tel.equals("请输入手机号")){
            ToastUtils.getInstance().ToastShow(BindTelActivity.this,"请输入正确的手机号");
            return;
        }

        if (pwd.length() < 6 || pwd.length() > 15 || pwd.equals("请输入密码(6 - 15位)")){
            ToastUtils.getInstance().ToastShow(BindTelActivity.this,"输入的密码格式不对");
            return;
        }
        if (type == 1){
            if (code.length() != 6  ||  code.equals("请输入验证码")){
                ToastUtils.getInstance().ToastShow(BindTelActivity.this,"请输入正确的验证码");
                return;
            }

            String codeid = (String) SPUtils.getParam(this, "user", "codeid", "");

            HttpMethods.getInstance().getNewWx(new ProgressSubscriber<PhoneLoginBean>(regListener,BindTelActivity.this),union,name,iamgeUrl,tel,code,codeid,pwd);
        }else{
            timeCount = new TimeCount(60000,1000);
            timeCount.start();
            HttpMethods.getInstance().getYunCode(new ProgressSubscriber<YunCode>(yunCodeSubscriberOnNextListener,BindTelActivity.this),tel);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btSendcode.setText("获取验证码");
            btSendcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btSendcode.setText(millisUntilFinished / 1000 + "秒");

        }
    }
}
