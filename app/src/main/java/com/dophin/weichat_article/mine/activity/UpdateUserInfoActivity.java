package com.dophin.weichat_article.mine.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.RegistActivity;
import com.dophin.weichat_article.login.bean.YunCode;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateUserInfoActivity extends BaseActivity {

    @BindView(R.id.uui_account)
    EditText uuiAccount;
    @BindView(R.id.uui_getcode)
    TextView uuiGetcode;
    @BindView(R.id.uui_code)
    EditText uuiCode;
    @BindView(R.id.uii_attention)
    TextView uiiAttention;
    @BindView(R.id.uui_sureAccout)
    EditText uuiSureAccout;
    private String type;
    private TimeCount timeCount;
    private SubscriberOnNextListener yunCodeSubscriberOnNextListener;
    private SubscriberOnNextListener<Statue> listener;
    private String orderId;
    private SubscriberOnNextListener<Statue> mimaListener;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("修改信息").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).setRightText("修改保存").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("name")){
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写昵称");
                        return;
                    }

                    if (uuiAccount.getText().toString().length() > 7){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"昵称长度过长");
                        return;
                    }

                    HttpMethods.getInstance().getUpdateUserInfo(new ProgressSubscriber<Statue>(listener,UpdateUserInfoActivity.this),orderId,"2","","",uuiAccount.getText().toString().trim(),"","","","");

                }else if(type.equals("phone")){
//                    if (TextUtils.isEmpty(uuiCode.getText().toString().trim())){
//                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写验证码");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(uuiSureAccout.getText().toString().trim())){
//                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写新绑定的手机号");
//                        return;
//                    }
//
//                    if (uuiSureAccout.getText().toString().trim().length() != 11){
//                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写正确的手机号");
//                        return;
//                    }
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写手机号");
                        return;
                    }

                    if (uuiAccount.getText().toString().length() != 11){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写正确的手机号");
                        return;
                    }

                    String sms = (String) SPUtils.getParam(UpdateUserInfoActivity.this, "user", "codeid", "");
                    HttpMethods.getInstance().getUpdateUserInfo(new ProgressSubscriber<Statue>(listener,UpdateUserInfoActivity.this),orderId,"1","","","",uuiAccount.getText().toString().trim(),"","","");


                }else if(type.equals("qq")){
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写qq");
                        return;
                    }
                    HttpMethods.getInstance().getUpdateUserInfo(new ProgressSubscriber<Statue>(listener,UpdateUserInfoActivity.this),orderId,"2","","","","","","",uuiAccount.getText().toString().trim());

                }else if(type.equals("zfb")){
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写支付宝账号");
                        return;
                    }

                    HttpMethods.getInstance().getUpdateUserInfo(new ProgressSubscriber<Statue>(listener,UpdateUserInfoActivity.this),orderId,"2","","","","","",uuiAccount.getText().toString().trim(),"");

                }else if(type.equals("zfbname")){
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请填写支付宝实名");
                        return;
                    }

                    HttpMethods.getInstance().getUpdateUserInfo(new ProgressSubscriber<Statue>(listener,UpdateUserInfoActivity.this),orderId,"2","","","","",uuiAccount.getText().toString().trim(),"","");

                }else if(type.equals("pwd")){
                    if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请输入密码");
                        return;
                    }

                    if (TextUtils.isEmpty(uuiSureAccout.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"请再次输入密码");
                        return;
                    }

                    if (!uuiAccount.getText().toString().trim().equals(uuiSureAccout.getText().toString().trim())){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"两次密码输入不一致");
                        return;
                    }
                    String tel = (String) SPUtils.getParam(UpdateUserInfoActivity.this, "user", "username", "");
                    HttpMethods.getInstance().getUpdatePwd(new ProgressSubscriber<Statue>(mimaListener,UpdateUserInfoActivity.this),tel,"",uuiAccount.getText().toString().trim());

                }
            }
        }).build();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        if (type.equals("name")){
            uuiAccount.setHint("请输入要修改的昵称(7个字符以内)");
        }else if(type.equals("phone")){
            uuiAccount.setHint("请输入要绑定的手机号");
//            uuiCode.setVisibility(View.VISIBLE);
//            uuiGetcode.setVisibility(View.VISIBLE);
//            uuiSureAccout.setVisibility(View.VISIBLE);
//            uuiSureAccout.setHint("请输入要绑定的手机号");
//            uuiCode.setHint("请输入验证码");
        }else if(type.equals("qq")){
            uuiAccount.setHint("请输入要修改的QQ");
        }else if(type.equals("zfb")){
            uuiAccount.setHint("请输入要修改的支付宝账号");
        }else if(type.equals("zfbname")){
            uuiAccount.setHint("请输入和当前支付宝绑定的实名");
            uiiAttention.setVisibility(View.VISIBLE);
        }else if(type.equals("pwd")){
            uuiAccount.setHint("请输入新的密码");
            uuiSureAccout.setVisibility(View.VISIBLE);
            uuiSureAccout.setHint("请再次输入密码");
        }


        listener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"修改成功");
                    if (type.equals("name")){
                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","nickname",uuiAccount.getText().toString().trim());

//                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","img",userInfo.getIMG());

                        uuiAccount.setHint("请输入要修改的昵称");
                    }else if(type.equals("phone")){
                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","tel",uuiAccount.getText().toString().trim());
                        SPUtils.setParam(UpdateUserInfoActivity.this, "user", "username", uuiAccount.getText().toString().trim());

                    }else if(type.equals("qq")){
                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","qq",uuiAccount.getText().toString().trim());
                    }else if(type.equals("zfb")){
                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","zfb",uuiAccount.getText().toString().trim());
                    }else if(type.equals("zfbname")){
                        SPUtils.setParam(UpdateUserInfoActivity.this,"user","zfbname",uuiAccount.getText().toString().trim());
                    }
                    finish2Activity();
                }else{
                    if (statue.getSTATUS() == 3 && type.equals("phone")){
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"修改失败,该手机已经绑定过其他账号");
                    }else{
                        ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this,"修改失败");
                    }
                }




            }
        };


        yunCodeSubscriberOnNextListener = new SubscriberOnNextListener<YunCode>() {
            @Override
            public void onNext(YunCode yunCode) {
                if (yunCode.getSTATUS() == 1) {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "验证码发送成功,请注意查收短信");
                    SPUtils.setParam(UpdateUserInfoActivity.this, "user", "codeid", yunCode.getSMSMESSAGESID());

                } else if (yunCode.getSTATUS() == 2) {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "当前手机号发送验证码次数过多,请稍后再试");
                } else {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "服务器异常,请稍后再试");
                }
            }
        };


        uuiGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(uuiAccount.getText().toString().trim())) {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "请输入手机号");
                    return;
                }

                if (uuiAccount.getText().toString().trim().length() != 11) {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "手机号格式不对");
                    return;
                }

                HttpMethods.getInstance().getYunCode(new ProgressSubscriber<YunCode>(yunCodeSubscriberOnNextListener, UpdateUserInfoActivity.this, 1), uuiAccount.getText().toString().trim());

                uuiGetcode.setClickable(false);
                timeCount = new TimeCount(60000, 1000);
                timeCount.start();
            }
        });


        mimaListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "修改成功");
                    finish2Activity();
                }else {
                    ToastUtils.getInstance().ToastShow(UpdateUserInfoActivity.this, "修改失败");
                }
            }
        };

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            uuiGetcode.setText("验证码");
            uuiGetcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            uuiGetcode.setText(millisUntilFinished / 1000 + "秒");

        }
    }

    @Override
    public void addActivity() {

    }

}
