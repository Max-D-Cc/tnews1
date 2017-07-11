package com.dophin.weichat_article.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.bean.PhoneLoginBean;
import com.dophin.weichat_article.login.bean.YunCode;
import com.dophin.weichat_article.main.activity.MainActivity;
import com.dophin.weichat_article.mine.fragment.IncomeFragment;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {


    @BindView(R.id.reg_phone)
    EditText regPhone;
    @BindView(R.id.reg_code)
    EditText regCode;
    @BindView(R.id.reg_pwd)
    EditText regPwd;
    @BindView(R.id.reg_suerpwd)
    EditText regSuerpwd;
    @BindView(R.id.reg_reg)
    TextView regReg;
    @BindView(R.id.reg_getCode)
    TextView regGetCode;
    @BindView(R.id.reg_yqm)
    EditText regYqm;
    @BindView(R.id.reg_v4)
    View regV4;
    @BindView(R.id.reg_v6)
    View regV6;
    private TimeCount timeCount;
    private SubscriberOnNextListener<YunCode> yunCodeSubscriberOnNextListener;
    private SubscriberOnNextListener<PhoneLoginBean> regListener;
    private SubscriberOnNextListener<PhoneLoginBean> forgetPwdListener;
    private String smsmessagesid;
    private int type;
    private String codeid;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initViews() {
        if (type == 1) {
            regYqm.setVisibility(View.GONE);
            regV4.setVisibility(View.GONE);
            regReg.setText("确定");
            new TitleBuilder(this).setTitleText("忘记密码").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish2Activity();
                }
            }).build();
        }else{
            new TitleBuilder(this).setTitleText("注册 ").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next2Activity(LoginActivity.class);
                }
            }).build();
        }
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
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "验证码发送成功,请注意查收短信");
                    SPUtils.setParam(RegistActivity.this, "user", "codeid", yunCode.getSMSMESSAGESID());

                } else if (yunCode.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "当前手机号发送验证码次数过多,请稍后再试");
                } else {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "服务器异常,请稍后再试");
                }
            }
        };


        regListener = new SubscriberOnNextListener<PhoneLoginBean>() {
            @Override
            public void onNext(PhoneLoginBean phoneLoginBean) {
                if (phoneLoginBean.getSTATUS() == 1) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "注册成功");
                    SPUtils.setParam(RegistActivity.this, "user", "userid", phoneLoginBean.getID());
                    SPUtils.setParam(RegistActivity.this, "user", "username", regPhone.getText().toString().trim());
                    SPUtils.setParam(RegistActivity.this, "user", "nickname", phoneLoginBean.getNICKNAME());
                    SPUtils.setParam(RegistActivity.this, "user", "orderid", phoneLoginBean.getORDERID());
                    SPUtils.setParam(RegistActivity.this, "user", "img", phoneLoginBean.getIMG());
                    SPUtils.setParam(RegistActivity.this, "user", "code", phoneLoginBean.getYQM());
                    SPUtils.setParam(RegistActivity.this, "user", "todaypoint", phoneLoginBean.getPOINTS_TODAY());
                    SPUtils.setParam(RegistActivity.this, "user", "usepoint", String.valueOf(phoneLoginBean.getPOINTS_SURPLUS()));
                    SPUtils.setParam(RegistActivity.this, "user", "totalpoint", String.valueOf(phoneLoginBean.getPOINTS_TOTAL()));
                    SPUtils.setParam(RegistActivity.this, "user", "yespoint", String.valueOf(phoneLoginBean.getPOINTS_YESTERDAY()));
                    SPUtils.setParam(RegistActivity.this, "user", "islogin", true);
                    next2Activity(MainActivity.class);
                } else if (phoneLoginBean.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "验证码填写错误");
                } else if (phoneLoginBean.getSTATUS() == 3) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "该手机号已存在");
                } else {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "服务器异常");
                }

            }
        };
        forgetPwdListener = new SubscriberOnNextListener<PhoneLoginBean>() {
            @Override
            public void onNext(PhoneLoginBean phoneLoginBean) {
                if (phoneLoginBean.getSTATUS() == 1) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "修改成功");
                    finish2Activity();
                } else if (phoneLoginBean.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "修改失败,该手机号不存在");
                } else if (phoneLoginBean.getSTATUS() == 3) {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "修改失败,验证码错误");
                } else {
                    ToastUtils.getInstance().ToastShow(RegistActivity.this, "修改失败");
                }

            }
        };

    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            regGetCode.setText("验证码");
            regGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            regGetCode.setText(millisUntilFinished / 1000 + "秒");

        }
    }


    @OnClick({R.id.reg_reg, R.id.reg_getCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_reg:
                check();
                codeid = (String) SPUtils.getParam(this, "user", "codeid", "");
                if (type == 1){
                    HttpMethods.getInstance().getForgetPwd(new ProgressSubscriber<PhoneLoginBean>(forgetPwdListener, RegistActivity.this),
                            regPhone.getText().toString().trim(),
                            regPwd.getText().toString().trim(),
                            regCode.getText().toString().trim(),
                            codeid
                            );}
                else{
                HttpMethods.getInstance().getPhoneRegist(new ProgressSubscriber<PhoneLoginBean>(regListener, RegistActivity.this),
                        regPhone.getText().toString().trim(),
                        regPwd.getText().toString().trim(),
                        regYqm.getText().toString().trim(),
                        codeid,
                        regCode.getText().toString().trim());
                         }
                break;
            case R.id.reg_getCode:
                if (TextUtils.isEmpty(regPhone.getText().toString().trim())) {
                    ToastUtils.getInstance().ToastShow(this, "请输入手机号");
                    return;
                }

                if (regPhone.getText().toString().trim().length() != 11) {
                    ToastUtils.getInstance().ToastShow(this, "手机号格式不对");
                    return;
                }

                HttpMethods.getInstance().getYunCode(new ProgressSubscriber<YunCode>(yunCodeSubscriberOnNextListener, RegistActivity.this, 1), regPhone.getText().toString().trim());

                regGetCode.setClickable(false);
                timeCount = new TimeCount(60000, 1000);
                timeCount.start();
                break;
        }
    }

    private void check() {

        if (TextUtils.isEmpty(regPhone.getText().toString().trim()) || regPhone.getText().toString().trim().equals("请输入手机号")) {
            ToastUtils.getInstance().ToastShow(this, "请输入手机号");
            return;
        }

        if (TextUtils.isEmpty(regCode.getText().toString().trim()) || regCode.getText().toString().trim().equals("请输入验证码")) {
            ToastUtils.getInstance().ToastShow(this, "请输入验证码");
            return;
        }

        if (TextUtils.isEmpty(regPwd.getText().toString().trim()) || regPwd.getText().toString().trim().equals("请设置密码（6-16位）")) {
            ToastUtils.getInstance().ToastShow(this, "请输入密码");
            return;
        }

        if (TextUtils.isEmpty(regSuerpwd.getText().toString().trim()) || regSuerpwd.getText().toString().trim().equals("请再次输入密码")) {
            ToastUtils.getInstance().ToastShow(this, "请再次输入密码");
            return;
        }


        if (regPhone.getText().toString().trim().length() != 11) {
            ToastUtils.getInstance().ToastShow(this, "手机号格式不对");
            return;
        }

        if (!regPwd.getText().toString().trim().equals(regSuerpwd.getText().toString().trim())) {
            ToastUtils.getInstance().ToastShow(this, "两次输入密码不一致");
            return;
        }

    }


    @Override
    protected void onDestroy() {
        if (timeCount != null) {
            timeCount.cancel();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (type == 1){
                finish2Activity();
            }else{
                next2Activity(LoginActivity.class);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
