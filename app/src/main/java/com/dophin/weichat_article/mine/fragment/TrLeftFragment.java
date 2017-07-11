package com.dophin.weichat_article.mine.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.activity.CodeActivity;
import com.dophin.weichat_article.mine.activity.GuessThingsActivity;
import com.dophin.weichat_article.mine.activity.TaskActivity;
import com.dophin.weichat_article.mine.adapter.TrLeftAdapter;
import com.dophin.weichat_article.mine.bean.DayTr;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrLeftFragment extends BaseFragment {


    @BindView(R.id.trl_rv)
    RecyclerView trlRv;
    @BindView(R.id.trl_tv1)
    TextView trlTv1;
    @BindView(R.id.trl_exp)
    TextView trlExp;
    @BindView(R.id.trl_img1)
    ImageView trlImg1;
    @BindView(R.id.trl_img2)
    ImageView trlImg2;
    @BindView(R.id.trl_img3)
    ImageView trlImg3;
    @BindView(R.id.trl_img4)
    ImageView trlImg4;
    @BindView(R.id.trl_img5)
    ImageView trlImg5;
    @BindView(R.id.trl_pb)
    ProgressBar trlPb;
    @BindView(R.id.trl_dian1)
    ImageView trlDian1;
    @BindView(R.id.trl_dian2)
    ImageView trlDian2;
    @BindView(R.id.trl_dian3)
    ImageView trlDian3;
    @BindView(R.id.trl_dian4)
    ImageView trlDian4;
    @BindView(R.id.trl_dian5)
    ImageView trlDian5;
    private String orderId;
    private DayTr tr;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private int curPostion;
    private SubscriberOnNextListener<DayTr> subscriberOnNextListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_tr_left, null);
        ButterKnife.bind(this, inflate);
        orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");
        initData();
        return inflate;
    }

    private void initData() {

        TrLeftAdapter adapter = new TrLeftAdapter(mActivity);
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        trlRv.setLayoutManager(manager);
        trlRv.setItemAnimator(new DefaultItemAnimator());
        trlRv.setAdapter(adapter);

        adapter.setOnTaskClickListener(new TrLeftAdapter.OnTaskClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0){
                    nextToActivity(mActivity, TaskActivity.class);
                    getActivity().finish();
                }else if (position == 1){
//                    nextToActivity(mActivity, TaskActivity.class);
                    Intent intent = new Intent("com.read");
                    mActivity.sendBroadcast(intent);
                    getActivity().finish();
                }else if(position == 2){
                    nextToActivity(mActivity, GuessThingsActivity.class);
                    getActivity().finish();
                }else if(position == 3){
                    nextToActivity(mActivity, CodeActivity.class);
                    getActivity().finish();
                }else {
//                    nextToActivity(mActivity, TaskActivity.class);
//                    getActivity().finish();
                }
            }
        });
        subscriberOnNextListener = new SubscriberOnNextListener<DayTr>() {
            @Override
            public void onNext(DayTr dayTr) {
                trlExp.setText(String.valueOf(dayTr.getEXP_TODAY()));
                tr = dayTr;
                if (dayTr.getONE() == 0) {
                    trlImg1.setImageResource(R.mipmap.weilingqu);
                } else if (dayTr.getONE() == 1) {
                    trlImg1.setImageResource(R.mipmap.lingqu333);
                } else if (dayTr.getONE() == 2) {
                    trlImg1.setImageResource(R.mipmap.baoxiang11);
                }

                if (dayTr.getTWO() == 0) {
                    trlImg2.setImageResource(R.mipmap.weilingqu);
                } else if (dayTr.getTWO() == 1) {
                    trlImg2.setImageResource(R.mipmap.lingqu333);
                } else if (dayTr.getTWO() == 2) {
                    trlImg2.setImageResource(R.mipmap.baoxiang11);
                }

                if (dayTr.getTHREE() == 0) {
                    trlImg3.setImageResource(R.mipmap.weilingqu);
                } else if (dayTr.getTHREE() == 1) {
                    trlImg3.setImageResource(R.mipmap.lingqu333);
                } else if (dayTr.getTHREE() == 2) {
                    trlImg3.setImageResource(R.mipmap.baoxiang11);
                }

                if (dayTr.getFOUR() == 0) {
                    trlImg4.setImageResource(R.mipmap.weilingqu);
                } else if (dayTr.getFOUR() == 1) {
                    trlImg4.setImageResource(R.mipmap.lingqu333);
                } else if (dayTr.getFOUR() == 2) {
                    trlImg4.setImageResource(R.mipmap.baoxiang11);
                }


                if (dayTr.getFIVE() == 0) {
                    trlImg5.setImageResource(R.mipmap.weilingqu);
                } else if (dayTr.getFIVE() == 1) {
                    trlImg5.setImageResource(R.mipmap.lingqu333);
                } else if (dayTr.getFIVE() == 2) {
                    trlImg5.setImageResource(R.mipmap.baoxiang11);
                }

                int exp_today = dayTr.getEXP_TODAY();
                if (exp_today <=10  && exp_today >0){
                    trlPb.setProgress(4);
                }else if (exp_today>10 && exp_today<30){
                    trlPb.setProgress(7);
                }else if (exp_today == 30){
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlPb.setProgress(10);
                }else if (exp_today>30 && exp_today<=37){
                    trlPb.setProgress(17);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>37 && exp_today<50){
                    trlPb.setProgress(26);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                }else if (exp_today == 50){
                    trlPb.setProgress(30);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>50 && exp_today<=75){
                    trlPb.setProgress(37);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>75 && exp_today<100){
                    trlPb.setProgress(46);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                }else if (exp_today == 100){
                    trlPb.setProgress(50);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>100 && exp_today<=130){
                    trlPb.setProgress(55);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                }
                else if (exp_today>130 && exp_today<=165){
                    trlPb.setProgress(60);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>165 && exp_today<200){
                    trlPb.setProgress(65);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                }else if (exp_today == 200){
                    trlPb.setProgress(70);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>200 && exp_today<=250){
                    trlPb.setProgress(73);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>250 && exp_today<=300){
                    trlPb.setProgress(76);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>300 && exp_today<=400){
                    trlPb.setProgress(80);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                }else if (exp_today>400 && exp_today<500){
                    trlPb.setProgress(85);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                }else if (exp_today == 500){
                    trlPb.setProgress(90);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                    trlDian5.setImageResource(R.mipmap.hongdian);
                }
                else if (exp_today>500){
                    trlPb.setProgress(100);
                    trlDian1.setImageResource(R.mipmap.hongdian);
                    trlDian2.setImageResource(R.mipmap.hongdian);
                    trlDian3.setImageResource(R.mipmap.hongdian);
                    trlDian4.setImageResource(R.mipmap.hongdian);
                    trlDian5.setImageResource(R.mipmap.hongdian);
                }

            }
        };

        HttpMethods.getInstance().getDayTr(new ProgressSubscriber<DayTr>(subscriberOnNextListener, mActivity, 1), orderId);

        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(mActivity,"恭喜您成功领取奖励,请前往今日收益页面查看领取详情");
                    HttpMethods.getInstance().getDayTr(new ProgressSubscriber<DayTr>(subscriberOnNextListener, mActivity, 1), orderId);
                }else{
                    ToastUtils.getInstance().ToastShow(mActivity,"领取失败");
                }
            }
        };


    }

    @OnClick({R.id.trl_img1, R.id.trl_img2, R.id.trl_img3, R.id.trl_img4, R.id.trl_img5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_img1:
                if (tr != null){

                    curPostion = 1;
                    Log.e("cai",tr.getONE() + "1");
                    if (tr.getONE() == 0){
                        ToastUtils.getInstance().ToastShow(mActivity,"您还没达到领取条件,快去做任务吧");
                    }else if(tr.getONE() == 1){
                        ToastUtils.getInstance().ToastShow(mActivity,"您已经领取奖励了");
                    }else if(tr.getONE() == 2){
                        HttpMethods.getInstance().getGetTrLeft(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"one");
                    }
                }

                break;
            case R.id.trl_img2:
                Log.e("cai",tr.getTWO() + "2");
                if (tr != null){
                    curPostion = 2;
                    if (tr.getTWO() == 0){
                        ToastUtils.getInstance().ToastShow(mActivity,"您还没达到领取条件,快去做任务吧");
                    }else if(tr.getTWO() == 1){
                        ToastUtils.getInstance().ToastShow(mActivity,"您已经领取奖励了");
                    }else if(tr.getTWO() == 2){
                        HttpMethods.getInstance().getGetTrLeft(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"two");
                    }
                }
                break;
            case R.id.trl_img3:
                Log.e("cai",tr.getTHREE() + "3");
                if (tr != null){
                    curPostion = 3;
                    if (tr.getTHREE() == 0){
                        ToastUtils.getInstance().ToastShow(mActivity,"您还没达到领取条件,快去做任务吧");
                    }else if(tr.getTHREE() == 1){
                        ToastUtils.getInstance().ToastShow(mActivity,"您已经领取奖励了");
                    }else if(tr.getTHREE() == 2){
                        HttpMethods.getInstance().getGetTrLeft(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"three");
                    }
                }
                break;
            case R.id.trl_img4:
                Log.e("cai",tr.getFOUR() + "4");
                if (tr != null){
                    curPostion = 4;
                    if (tr.getFOUR() == 0){
                        ToastUtils.getInstance().ToastShow(mActivity,"您还没达到领取条件,快去做任务吧");
                    }else if(tr.getFOUR() == 1){
                        ToastUtils.getInstance().ToastShow(mActivity,"您已经领取奖励了");
                    }else if(tr.getFOUR() == 2){
                        HttpMethods.getInstance().getGetTrLeft(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"four");
                    }
                }
                break;
            case R.id.trl_img5:
                Log.e("cai",tr.getFIVE() + "5");
                if (tr != null){
                    curPostion = 5;
                    if (tr.getFIVE() == 0){
                        ToastUtils.getInstance().ToastShow(mActivity,"您还没达到领取条件,快去做任务吧");
                    }else if(tr.getFIVE() == 1){
                        ToastUtils.getInstance().ToastShow(mActivity,"您已经领取奖励了");
                    }else if(tr.getFIVE() == 2){
                        HttpMethods.getInstance().getGetTrLeft(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"five");
                    }
                }
                break;
        }
    }
}
