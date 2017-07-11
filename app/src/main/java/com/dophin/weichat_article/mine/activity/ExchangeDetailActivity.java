package com.dophin.weichat_article.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.WrapHeightGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeDetailActivity extends BaseActivity {


    @BindView(R.id.ed_isZfb)
    TextView edIsZfb;
    @BindView(R.id.ed_gv)
    WrapHeightGridView edGv;
    @BindView(R.id.ed_useRich)
    TextView edUseRich;
    @BindView(R.id.ed_needRich)
    TextView edNeedRich;
    @BindView(R.id.ed_account)
    TextView edAccount;
    @BindView(R.id.ed_et_account)
    EditText edEtAccount;
    @BindView(R.id.ed_sureaccount)
    TextView edSureaccount;
    @BindView(R.id.ed_et_sureaccount)
    EditText edEtSureaccount;
    @BindView(R.id.ed_submit)
    TextView edSubmit;
    private int type;
    private int[] Num = {30, 50, 100, 200};

    private int selectPostion = 5;
    private String title;
    private SubscriberOnNextListener<Statue> listener;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_detail);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initViews() {
        if (type == 0) {
            edIsZfb.setVisibility(View.GONE);
            edAccount.setText("输入手机号:");
            edSureaccount.setText("确认手机号:");
            title = "兑换话费";
        } else if (type == 2) {
            edIsZfb.setVisibility(View.GONE);
            edAccount.setText("输入QQ号:");
            edSureaccount.setText("确认QQ号:");
            title = "兑换QB";
        } else {
            title = "兑换支付宝";
        }


        new TitleBuilder(this).setTitleText(title).setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();

        listener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"提交兑换成功,请耐心等待");
                }else if(statue.getSTATUS() == 3){
                    ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"账号异常,请联系客服");
                }else if(statue.getSTATUS() == 6){
                    ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"今日已兑换,请明日在次提交");
                }else{
                    ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"兑换失败,请联系客服");
                }
            }
        };

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

        String useMoney = (String) SPUtils.getParam(this, "user", "usepoint", "");
        String nickName = (String) SPUtils.getParam(this, "user", "nickname", "");
        String zfb = (String) SPUtils.getParam(this, "user", "zfb", "");
        String qq = (String) SPUtils.getParam(this, "user", "qq", "");
        String tel = (String) SPUtils.getParam(this, "user", "tel", "");
        String img = (String) SPUtils.getParam(this, "user", "img", "");
        String zfbName = (String) SPUtils.getParam(this, "user", "zfbname", "");
        String username = (String) SPUtils.getParam(this, "user", "username", "");
        String userId = (String) SPUtils.getParam(this, "user", "userid", "");

        edUseRich.setText("您当前可兑换余额为：" + useMoney);

        if (type==0){
            edEtAccount.setText(tel);
            edEtSureaccount.setText(tel);
        }else if(type == 1){
            edEtAccount.setText(zfb);
            edEtSureaccount.setText(zfbName);
        }else if(type ==2){
            edEtAccount.setText(qq);
            edEtSureaccount.setText(qq);
        }


        ExchangeDetailAdapter adapter = new ExchangeDetailAdapter(this, Num);
        edGv.setAdapter(adapter);

        edGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPostion = position;
                for (int i = 0; i < Num.length; i++) {
                    ImageView img = (ImageView) parent.getChildAt(i).findViewById(R.id.item_ed_isSelect);
                    if (position == i) {
                        img.setImageResource(R.mipmap.ed_select);
                    } else {
                        img.setImageResource(R.mipmap.ed_noselect);
                    }
                }

                if (position == 0) {
                    edNeedRich.setText("需要31元才能兑换");
                } else if (position == 1) {
                    edNeedRich.setText("需要50元才能兑换");
                } else if (position == 2) {
                    edNeedRich.setText("需要99元才能兑换");
                } else {
                    edNeedRich.setText("需要197元才能兑换");
                }
            }
        });

    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }



    @OnClick(R.id.ed_submit)
    public void onClick() {
        if (selectPostion != 5){
            check();
        }else{
            ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"请选择您需要兑换的金额");
        }
    }

    private void check(){
        String account = edEtAccount.getText().toString().trim();
        String suerAccount = edEtSureaccount.getText().toString().trim();
        if (type == 1){
            if (TextUtils.isEmpty(account)){
                ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"请输入账号");
                return;
            }

            if (TextUtils.isEmpty(suerAccount)){
                ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"请在支付宝实名");
                return;
            }
        }else{
            if (TextUtils.isEmpty(account)){
                ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"请输入账号");
                return;
            }

            if (TextUtils.isEmpty(suerAccount)){
                ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"请在次输入账号");
                return;
            }

            if (!suerAccount.equals(account)){
                ToastUtils.getInstance().ToastShow(ExchangeDetailActivity.this,"两次输入账号不一致");
                return;
            }
        }


        String orderId = (String) SPUtils.getParam(ExchangeDetailActivity.this, "user", "orderid", "");

        HttpMethods.getInstance().getExchange(new ProgressSubscriber<Statue>(listener,ExchangeDetailActivity.this),orderId,String.valueOf(type),String.valueOf(Num[selectPostion]),account);


    }


    class ExchangeDetailAdapter extends BaseAdapter {

        private Context context;
        private int[] list;

        public ExchangeDetailAdapter(Context context, int[] list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(ExchangeDetailActivity.this, R.layout.item_exchange_detail, null);
//            ImageView isSelect = (ImageView) inflate.findViewById(R.id.item_ed_isSelect);
            TextView tvName = (TextView) inflate.findViewById(R.id.item_ed_name);

            if (type == 0) {
                tvName.setText(list[position] + "元");
            } else if (type == 1) {
                tvName.setText(list[position] + "元");
            } else if (type == 2) {
                tvName.setText(list[position] + "QB");
            }
            return inflate;
        }
    }

}
