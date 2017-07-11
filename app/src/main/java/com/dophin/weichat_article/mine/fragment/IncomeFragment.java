package com.dophin.weichat_article.mine.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.IncomeAdapter;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.IncomeLeft;
import com.dophin.weichat_article.mine.bean.IncomeList;
import com.dophin.weichat_article.mine.bean.IncomeRight;
import com.dophin.weichat_article.utils.EndLessOnScrollListener;
import com.dophin.weichat_article.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends BaseFragment {


    @BindView(R.id.income_rv)
    RecyclerView incomeRv;
    private String mTitle;

    private List<IncomeLeft>  leftList = new ArrayList<>();
    private List<IncomeRight>  rightList = new ArrayList<>();
    private IncomeAdapter adapter;
    private String orderId;
    private SubscriberOnNextListener<IncomeList> subscriberOnNextListener;


    public static IncomeFragment getInstance(String title) {
        IncomeFragment fft = new IncomeFragment();
        fft.mTitle = title;
        return fft;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_income_right, null);
        ButterKnife.bind(this, view);
        orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");
        initView();
        return view;
    }

    private void initView() {

        adapter = new IncomeAdapter(mActivity,leftList,rightList,mTitle);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        incomeRv.setLayoutManager(manager);
        incomeRv.setItemAnimator(new DefaultItemAnimator());
        incomeRv.setAdapter(adapter);


        subscriberOnNextListener = new SubscriberOnNextListener<IncomeList>() {
            @Override
            public void onNext(IncomeList incomeList) {
                if (mTitle.equals("1")){
                    if (adapter != null){
                        List<IncomeLeft> find_points = incomeList.getFIND_POINTS();
                        leftList.addAll(find_points);
                        adapter.notifyDataSetChanged();
                    }

                }else if(mTitle.equals("2")){
                    if (adapter != null){
                        List<IncomeRight> find_zhuanxi = incomeList.getFIND_ZHUANXI();
                        rightList.addAll(find_zhuanxi);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };


        if (mTitle.equals("1")){
            HttpMethods.getInstance().getIncomeLeft(new ProgressSubscriber<IncomeList>(subscriberOnNextListener,mActivity,1),orderId,String.valueOf(1));
        }else if(mTitle.equals("2")){
            HttpMethods.getInstance().getIncomeRight(new ProgressSubscriber<IncomeList>(subscriberOnNextListener,mActivity,1),orderId,String.valueOf(1));
        }

        incomeRv.setOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.e("cai","到底部了");
                if (mTitle.equals("1")){
                    HttpMethods.getInstance().getIncomeLeft(new ProgressSubscriber<IncomeList>(subscriberOnNextListener,mActivity,1),orderId,String.valueOf(currentPage));
                }else if(mTitle.equals("2")){
                    HttpMethods.getInstance().getIncomeRight(new ProgressSubscriber<IncomeList>(subscriberOnNextListener,mActivity,1),orderId,String.valueOf(currentPage));
                }
            }
        });


//        Sub

    }

}
