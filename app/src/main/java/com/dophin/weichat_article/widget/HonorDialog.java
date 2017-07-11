package com.dophin.weichat_article.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;

/**
 * Created by caiguoqing on 2017/3/8.
 */

public class HonorDialog extends Dialog implements View.OnClickListener{


    private Context context;
    private String str;
    private ImageView img;
    private TextView get;
    private TextView condition;
    private int topOrBottom;
    private ImageView close;
    private SubscriberOnNextListener<Statue> listener;
    private Handler handler;


    public HonorDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public HonorDialog(Context context , String type, int topOrBottom, Handler handler) {
        this(context, R.style.AlertDialogStyle);
        this.context = context;
        this.str = type;
        this.topOrBottom = topOrBottom;
        this.handler = handler;
        initView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_honor, null);
        setContentView(view);
        img = (ImageView) view.findViewById(R.id.dialog_honor_img);
        close = (ImageView) view.findViewById(R.id.dialog_honor_close);
        get = (TextView) view.findViewById(R.id.dialog_honor_get);
        TextView title = (TextView) view.findViewById(R.id.dialog_honor_title);
        condition = (TextView) view.findViewById(R.id.dialog_honor_condition);
        TextView award = (TextView) view.findViewById(R.id.dialog_honor_award);
        close.setOnClickListener(this);
        get.setOnClickListener(this);

        if (topOrBottom == 1){
            get.setText("确定");
            title.setText("已获得");
        }


        if (str.equals("QTDS")){
            img.setImageResource(R.mipmap.qingtingdianshuihuode);
            condition.setText("获取条件：累计阅读20篇");
            award.setText("奖励：20经验");
        }else if(str.equals("CHHY")){
            img.setImageResource(R.mipmap.caihuahengyihuode);
            condition.setText("获取条件：累计阅读5000篇");
            award.setText("奖励：100经验");
        }else if(str.equals("XYMQ")){
            img.setImageResource(R.mipmap.xiaoyoumingqihuode);
            condition.setText("获取条件：邀请20名好友");
            award.setText("奖励：80经验");
        }else if(str.equals("FKDG")){
            img.setImageResource(R.mipmap.fukediguohuode);
            condition.setText("获取条件：累计获得收益5000元");
            award.setText("奖励：500经验");
        }else if(str.equals("CLFM")){
            img.setImageResource(R.mipmap.chulufengmanghuode);
            condition.setText("获取条件：邀请3名好友");
            award.setText("奖励：30经验");
        }else if(str.equals("DGQB")){
            img.setImageResource(R.mipmap.duguqiubaihuode);
            condition.setText("获取条件：同时完成朋满天下、富可敌国、满腹经纶");
            award.setText("奖励：1000经验");
        }else if(str.equals("DBYF")){
            img.setImageResource(R.mipmap.dubayifanghuode);
            condition.setText("获取条件：同时完成一呼百应、锦衣玉食、出口成章");
            award.setText("奖励：200经验");
        }else if(str.equals("MFJL")){
            img.setImageResource(R.mipmap.manfujinglunhuode);
            condition.setText("获取条件：累计阅读10000篇");
            award.setText("奖励：300经验");
        }else if(str.equals("XBXK")){
            img.setImageResource(R.mipmap.xibenxiaokanghuode);
            condition.setText("获取条件：累计获得收益100元");
            award.setText("奖励：50经验");
        }else if(str.equals("FJYF")){
            img.setImageResource(R.mipmap.fujiayifanghuode);
            condition.setText("获取条件：累计获得收益1000元");
            award.setText("奖励：100经验");
        }else if(str.equals("JYYS")){
            img.setImageResource(R.mipmap.jinyiyushihuode);
            condition.setText("获取条件：累计获得收益300元");
            award.setText("奖励：80经验");
        }else if(str.equals("NZXS")){
            img.setImageResource(R.mipmap.nangzhongxiusehuode);
            condition.setText("获取条件：累计获得收益30元");
            award.setText("奖励：20经验");
        }else if(str.equals("SBSJ")){
            img.setImageResource(R.mipmap.shoubushijuanhuode);
            condition.setText("获取条件：累计阅读500篇");
            award.setText("奖励：50经验");
        }else if(str.equals("YZBF")){
            img.setImageResource(R.mipmap.yingzibofahuode);
            condition.setText("获取条件：同时完成初露锋芒、囊中羞涩、蜻蜓点水");
            award.setText("奖励：30经验");
        }else if(str.equals("CKCZ")){
            img.setImageResource(R.mipmap.chukouchengzhanghuode);
            condition.setText("获取条件：累计阅读1000篇");
            award.setText("奖励：80经验");
        }else if(str.equals("CLBC")){
            img.setImageResource(R.mipmap.chuleibacuihuode);
            condition.setText("获取条件：同时完成小有名气、喜奔小康、手不释卷");
            award.setText("奖励：80经验");
        }else if(str.equals("PMTX")){
            img.setImageResource(R.mipmap.pengmantianxiahuode);
            condition.setText("获取条件：邀请3000名好友");
            award.setText("奖励：1000经验");
        }else if(str.equals("GJSY")){
            img.setImageResource(R.mipmap.guangjieshanyuanhuode);
            condition.setText("获取条件：邀请1000名好友");
            award.setText("奖励：500经验");
        }else if(str.equals("SXPM")){
            img.setImageResource(R.mipmap.suoxiangpimihuode);
            condition.setText("获取条件：同时完成广结善缘、富甲一方、才华横溢");
            award.setText("奖励：500经验");
        }else if(str.equals("YHBY")){
            img.setImageResource(R.mipmap.yihubaiyinghuode);
            condition.setText("获取条件：邀请100名好友");
            award.setText("奖励：200经验");
        }

        listener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    dismiss();
                }else if(statue.getSTATUS() == 2){
                    ToastUtils.getInstance().ToastShow(context,"未达到领取条件");
                    dismiss();
                }else{
                    ToastUtils.getInstance().ToastShow(context,"服务器异常");
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_honor_close:
                this.dismiss();
                break;
            case R.id.dialog_honor_get:
                if (topOrBottom != 1){
                    String orderId = (String) SPUtils.getParam(context, "user", "orderid", "");
                    HttpMethods.getInstance().getGetHonor(new ProgressSubscriber<Statue>(listener,context,1),orderId,str);
                }else{
                    this.dismiss();
                }
                break;
        }
    }
}
