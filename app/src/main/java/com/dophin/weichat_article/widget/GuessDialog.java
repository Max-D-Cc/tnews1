package com.dophin.weichat_article.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.db.ChannelActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/2/28.
 */

public class GuessDialog extends Dialog implements View.OnClickListener{


    ImageView diglogZJian;
    EditText diglogZNum;
    ImageView diglogZAdd;
    ImageView diglogFJian;
    EditText diglogFNum;
    ImageView diglogFAdd;
    TextView dialogGSum;
    TextView dialogGNmoney;
    TextView dialogGAllmoney;
    TextView dialogGSubmit;
    private Context context;
    private int blueNum = 0;
    private int redNum = 0;
    private String id;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private View.OnClickListener listener;


    public GuessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public GuessDialog(Context context, String id, View.OnClickListener listener) {
        this(context, R.style.AlertDialogStyle);
        this.context = context;
        this.id = id;
        this.listener = listener;

        initView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_guess, null);
        setContentView(view);
        diglogZJian = (ImageView) view.findViewById(R.id.diglog_z_jian);
        diglogZAdd = (ImageView) view.findViewById(R.id.diglog_z_add);
        diglogZNum = (EditText) view.findViewById(R.id.diglog_z_num);
        diglogFJian = (ImageView) view.findViewById(R.id.diglog_f_jian);
        diglogFAdd = (ImageView) view.findViewById(R.id.diglog_f_add);
        diglogFNum = (EditText) view.findViewById(R.id.diglog_f_num);
        dialogGAllmoney = (TextView) view.findViewById(R.id.dialog_g_allmoney);
        dialogGSubmit = (TextView) view.findViewById(R.id.dialog_g_submit);
        dialogGSum = (TextView) view.findViewById(R.id.dialog_g_sum);
        dialogGNmoney = (TextView) view.findViewById(R.id.dialog_g_nmoney);
        diglogZJian.setOnClickListener(this);
        diglogZAdd.setOnClickListener(this);
        diglogFJian.setOnClickListener(this);
        diglogFAdd.setOnClickListener(this);
        dialogGSubmit.setOnClickListener(this);




        initData();
    }

    private void initData() {
//        diglogFNum.setText(String.valueOf(redNum));
//        diglogZNum.setText(String.valueOf(blueNum));


        String uPoint = (String) SPUtils.getParam(context, "user", "usepoint", "");
        dialogGAllmoney.setText(uPoint+"元");

        diglogZNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    blueNum = Integer.parseInt(s.toString());
                    dialogGSum.setText(String.valueOf(redNum+blueNum) + "注");
                    dialogGNmoney.setText(String.valueOf(mul(0.1,(redNum+blueNum)))+"元");
                }
            }
        });

        diglogFNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    redNum = Integer.parseInt(s.toString());
                    dialogGSum.setText(String.valueOf(redNum+blueNum) + "注");
                    dialogGNmoney.setText(String.valueOf(mul(0.1,(redNum+blueNum)))+"元");
                }
            }
        });


        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(context,"投注成功");
                    listener.onClick(dialogGSubmit);
                }else if(statue.getSTATUS() == 2){
                    ToastUtils.getInstance().ToastShow(context,"投注失败,您的余额不足");
                }else if(statue.getSTATUS() == 4){
                    ToastUtils.getInstance().ToastShow(context,"投注失败,不在开放时间");
                }else{
                    ToastUtils.getInstance().ToastShow(context,"投注失败");
                }
                dismiss();
            }
        };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diglog_z_add:
                if (blueNum <9999){
//                    Log.e("cai","蓝投" + blueNum);
                    blueNum++;
                    diglogZNum.setText(String.valueOf(blueNum));
                    diglogZJian.setImageResource(R.mipmap.guess_jianon);
                }else{
                    ToastUtils.getInstance().ToastShow(context,"最大投注数不能超过9999");
                }
                break;
            case R.id.diglog_z_jian:
                if(blueNum>0){
                    blueNum --;
                    if (blueNum == 0){
                        diglogZJian.setImageResource(R.mipmap.guess_jian);
                    }
                    diglogZNum.setText(String.valueOf(blueNum));
                }else{
                    diglogZJian.setImageResource(R.mipmap.guess_jian);
                    ToastUtils.getInstance().ToastShow(context,"不能再少了亲");
                }
                break;
            case R.id.diglog_f_add:
                if (redNum <9999){
                    redNum++;
                    diglogFJian.setImageResource(R.mipmap.guess_jianon);
                    diglogFNum.setText(String.valueOf(redNum));
                }else{
                    ToastUtils.getInstance().ToastShow(context,"最大投注数不能超过9999");
                }
                break;
            case R.id.diglog_f_jian:
                if(redNum>0){
                    redNum --;
                    if (redNum == 0){
                        diglogFJian.setImageResource(R.mipmap.guess_jian);
                    }
                    diglogFNum.setText(String.valueOf(redNum));
                }else{
                    diglogFJian.setImageResource(R.mipmap.guess_jian);
                    ToastUtils.getInstance().ToastShow(context,"不能再少了亲");
                }
                break;
            case R.id.dialog_g_submit:
                if (TextUtils.isEmpty(diglogZNum.getText().toString().trim()) && TextUtils.isEmpty(diglogFNum.getText().toString().trim())){
                    dismiss();
                }else if (diglogZNum.getText().toString().trim().equals("0") && diglogFNum.getText().toString().trim().equals("0")){
                    dismiss();
                }else{
                    String orderId = (String) SPUtils.getParam(context, "user", "orderid", "");
                    HttpMethods.getInstance().getGuess(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,context,1),orderId,id,String.valueOf(blueNum),String.valueOf(redNum));
                }
                break;
        }

    }

    public static double mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
