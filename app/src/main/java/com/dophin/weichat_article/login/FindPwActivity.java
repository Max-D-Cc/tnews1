package com.dophin.weichat_article.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPwActivity extends BaseActivity {

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


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_regist);
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

    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.reg_reg, R.id.reg_getCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_reg:
                break;
            case R.id.reg_getCode:
                break;
        }
    }
}
