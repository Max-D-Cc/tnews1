package com.dophin.weichat_article.mine.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.RankAdapter;
import com.dophin.weichat_article.mine.bean.Rank;
import com.dophin.weichat_article.mine.bean.RankList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends BaseFragment {


    @BindView(R.id.rank_rv)
    RecyclerView rankRv;
    private String mTitle;
    private List<Rank>  list = new ArrayList<>();

    public static RankFragment getInstance(String title) {
        RankFragment fft = new RankFragment();
        fft.mTitle = title;
        return fft;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_rank, null);
        ButterKnife.bind(this, inflate);

        initData();
        return inflate;
    }

    private void initData() {
        final RankAdapter adapter = new RankAdapter(mActivity,list,mTitle);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rankRv.setLayoutManager(manager);
        rankRv.setItemAnimator(new DefaultItemAnimator());
        rankRv.setAdapter(adapter);

        SubscriberOnNextListener<RankList> listener  = new SubscriberOnNextListener<RankList>() {
            @Override
            public void onNext(RankList rankList) {
                list.clear();
                if (mTitle.equals("1")){
                    if (rankList.getREAD() != null){
                        list.addAll(rankList.getREAD());
                    }
                }else if(mTitle.equals("2")){
                    if (rankList.getTUIG() != null){
                        list.addAll(rankList.getTUIG());
                    }
                }else{
                    if (rankList.getZHUANJ() != null){
                        list.addAll(rankList.getZHUANJ());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        };

        HttpMethods.getInstance().getRank(new ProgressSubscriber<RankList>(listener,mActivity,1),mTitle);

    }

}
