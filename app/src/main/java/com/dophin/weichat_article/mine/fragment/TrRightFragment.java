package com.dophin.weichat_article.mine.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.TrRightAdapter;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.mine.bean.Tr;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrRightFragment extends BaseFragment {

    @BindView(R.id.trr_rv)
    RecyclerView trlRv;
    @BindView(R.id.trr_exp)
    TextView trrExp;
    List<Integer> list = new ArrayList<>();
    private String orderId;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private SubscriberOnNextListener<Tr> subscriberOnNextListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_tr_right2, null);
        ButterKnife.bind(this, inflate);
        orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");

        initData();
        return inflate;
    }

    private void initData() {

        final TrRightAdapter adapter = new TrRightAdapter(mActivity,list);
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        trlRv.setLayoutManager(manager);
        trlRv.setItemAnimator(new DefaultItemAnimator());
        trlRv.setAdapter(adapter);




        adapter.setOnGetClickListener(new TrRightAdapter.OnGetClickListener() {
            @Override
            public void onGet(int position) {
                int integer = list.get(position);
                if (integer == 0){
                    ToastUtils.getInstance().ToastShow(mActivity,"您还未达到领取奖励条件");
                }else if(integer == 1){
                    ToastUtils.getInstance().ToastShow(mActivity,"您已经领取当前奖励了");
                }else if(integer == 2){
                    if (position == 0){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"tu");
                    }else if(position == 1){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"mu");
                    }
                    else if(position == 2){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"tie");
                    }
                    else if(position == 3){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"tong");
                    }
                    else if(position == 4){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"yin");
                    }
                    else if(position == 5){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"jin");
                    }
                    else if(position == 6){
                        HttpMethods.getInstance().getGetTrRight(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,mActivity,1),orderId,"zuan");
                    }


                }
            }
        });


        subscriberOnNextListener = new SubscriberOnNextListener<Tr>() {
            @Override
            public void onNext(Tr tr) {

                trrExp.setText("累计获得经验："+String.valueOf(tr.getEXP_TOTAL()));

                list.clear();
                list.add(tr.getTU());
                list.add(tr.getMU());
                list.add(tr.getTIE());
                list.add(tr.getTONG());
                list.add(tr.getYIN());
                list.add(tr.getJIN());
                list.add(tr.getZUAN());

                adapter.notifyDataSetChanged();

            }
        };

        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(mActivity,"恭喜您成功领取奖励,请前往今日收益页面查看领取详情");
                    HttpMethods.getInstance().getTr(new ProgressSubscriber<Tr>(subscriberOnNextListener,mActivity,1),orderId);
                }else{
                    ToastUtils.getInstance().ToastShow(mActivity,"领取失败");
                }
            }
        };

        HttpMethods.getInstance().getTr(new ProgressSubscriber<Tr>(subscriberOnNextListener,mActivity,1),orderId);
    }

}
