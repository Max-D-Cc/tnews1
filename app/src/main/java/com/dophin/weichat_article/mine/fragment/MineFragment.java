package com.dophin.weichat_article.mine.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.BindTelActivity;
import com.dophin.weichat_article.login.LoginActivity;
import com.dophin.weichat_article.mine.activity.BindWxActivity;
import com.dophin.weichat_article.mine.activity.CodeActivity;
import com.dophin.weichat_article.mine.activity.ExchangeActivity;
import com.dophin.weichat_article.mine.activity.GetMoneyActivity;
import com.dophin.weichat_article.mine.activity.GuessThingsActivity;
import com.dophin.weichat_article.mine.activity.HonorActivity;
import com.dophin.weichat_article.mine.activity.IncomeActivity;
import com.dophin.weichat_article.mine.activity.OffLineActivity;
import com.dophin.weichat_article.mine.activity.RankActivity;
import com.dophin.weichat_article.mine.activity.RedPackageActivity;
import com.dophin.weichat_article.mine.activity.SetActivity;
import com.dophin.weichat_article.mine.activity.TaskActivity;
import com.dophin.weichat_article.mine.activity.TrActivity;
import com.dophin.weichat_article.mine.activity.UserInfoActivity;
import com.dophin.weichat_article.mine.bean.UserInfo;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.widget.CircleImageView;
import com.dophin.weichat_article.widget.WrapHeightGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.mine_circleImg)
    CircleImageView mineCircleImg;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_id)
    TextView mineId;
    @BindView(R.id.mine_set)
    TextView mineSet;
    @BindView(R.id.mine_exchange)
    TextView mineExchange;
    @BindView(R.id.mine_uerrich)
    TextView mineUerrich;
    @BindView(R.id.mine_yesrich)
    TextView mineYesrich;
    @BindView(R.id.mine_allrich)
    TextView mineAllrich;
    @BindView(R.id.mine_howGetMoney)
    TextView mineHowGetMoney;
    @BindView(R.id.mine_iv1)
    ImageView mineIv1;
    @BindView(R.id.mine_friend)
    RelativeLayout mineFriend;
    @BindView(R.id.mine_score)
    RelativeLayout mineScore;
    @BindView(R.id.mine_leftid)
    TextView mineLeftid;
    private int[] ids = {R.mipmap.renwuguangchang, R.mipmap.chaihongbao_2, R.mipmap.quweicai, R.mipmap.rongyuxunzhang, R.mipmap.wodechengjiu, R.mipmap.guangrongbang};
    private String[] strs = {"任务广场", "拆红包", "趣味猜", "荣誉勋章", "今日宝箱", "光荣榜"};


    @BindView(R.id.mine_gv)
    WrapHeightGridView mineGv;
    private SubscriberOnNextListener<UserInfo> listener;
    private String orderId;
    private SubscriberOnNextListener<UserInfo> userInfoSubscriberOnNextListener;
    private String isBindWx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_mine, null);
        ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            String img = (String) SPUtils.getParam(mActivity, "user", "img", "");
            ImageLoader.displayImage(mActivity, img, mineCircleImg, R.mipmap.denglutouxiang);
            HttpMethods.getInstance().getUserInfo(new ProgressSubscriber<UserInfo>(listener,mActivity,1),orderId);


        }catch (Exception e){
            e.printStackTrace();
        }
        isBindWx = (String) SPUtils.getParam(mActivity,"user","isBindWx","");

        Log.e("cai","isBind: " + isBindWx);
        if (isBindWx.equals("1")){
            mineFriend.setVisibility(View.VISIBLE);
        }else{
            mineFriend.setVisibility(View.GONE);
        }

    }

    private void initView() {
        MineGvAdapter adapter = new MineGvAdapter();
        mineGv.setAdapter(adapter);
//        SpannableString ss = new SpannableString(circle.getTime());
//        ss.setSpan(new TextAppearanceSpan(context,R.style.circle_date_date), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(new TextAppearanceSpan(context,R.style.circle_date_month), 2, circle.getTime().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        holder.date.setText(ss, TextView.BufferType.SPANNABLE);

        mineGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    nextToActivity(mActivity, GuessThingsActivity.class);
                } else if (position == 4) {
                    nextToActivity(mActivity, TrActivity.class);
                } else if (position == 3) {
                    nextToActivity(mActivity, HonorActivity.class);
                }else if(position == 5){
                    nextToActivity(mActivity, RankActivity.class);
                }else if(position == 1){
                    nextToActivity(mActivity, RedPackageActivity.class);
                }else if(position == 0){
                    nextToActivity(mActivity, TaskActivity.class);
                }
            }
        });

        mineCircleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextToActivity(mActivity, UserInfoActivity.class);
            }
        });


        final String userName = (String) SPUtils.getParam(mActivity, "user", "username", "");
        String nickName = (String) SPUtils.getParam(mActivity, "user", "nickname", "");
        String yPoint = (String) SPUtils.getParam(mActivity, "user", "yespoint", "");
        String totalPoint = (String) SPUtils.getParam(mActivity, "user", "totalpoint", "");
        String uPoint = (String) SPUtils.getParam(mActivity, "user", "usepoint", "");
        orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");
        String img = (String) SPUtils.getParam(mActivity, "user", "img", "");

//        final int userId = (int) SPUtils.getParam(mActivity, "user", "userid", 0);
//        int tPoint = (int) SPUtils.getParam(mActivity, "user", "todaypoint", 0);


        listener = new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo userInfo) {
                if (TextUtils.isEmpty(userInfo.getNICKNAME())) {
                    mineName.setText(userName);
                } else {
                    mineName.setText(userInfo.getNICKNAME());
                }
                mineAllrich.setText("¥" + userInfo.getPOINTS_TOTAL());
                mineYesrich.setText("¥" + userInfo.getPOINTS_YESTERDAY());
                mineUerrich.setText("¥" + userInfo.getPOINTS_SURPLUS());
                mineId.setText("今日星币：" + userInfo.getPOINTS_TODAY());
                ImageLoader.displayImage(mActivity, userInfo.getIMG(), mineCircleImg, R.mipmap.denglutouxiang);
                mineLeftid.setText("ID："+userInfo.getID());

                SPUtils.setParam(mActivity,"user","yespoint",String.valueOf(userInfo.getPOINTS_YESTERDAY()));
                SPUtils.setParam(mActivity,"user","usepoint",String.valueOf(userInfo.getPOINTS_SURPLUS()));
                SPUtils.setParam(mActivity,"user","bili",String.valueOf(userInfo.getBILI()));
                SPUtils.setParam(mActivity,"user","code",String.valueOf(userInfo.getYQM()));
                SPUtils.setParam(mActivity,"user","yesxb",String.valueOf(userInfo.getPOINTS_YESTERDAY_XB()));

                SPUtils.setParam(mActivity,"user","nickname",userInfo.getNICKNAME());
                SPUtils.setParam(mActivity,"user","zfb",userInfo.getALIPAY());
                SPUtils.setParam(mActivity,"user","qq",userInfo.getQQ());
                SPUtils.setParam(mActivity,"user","tel",userInfo.getTEL());
                SPUtils.setParam(mActivity,"user","img",userInfo.getIMG());
                SPUtils.setParam(mActivity,"user","zfbname",userInfo.getALIPAY_NAME());
                SPUtils.setParam(mActivity,"user","idid",String.valueOf(userInfo.getID()));


            }
        };

//        userInfoSubscriberOnNextListener = new SubscriberOnNextListener<UserInfo>() {
//            @Override
//            public void onNext(UserInfo userInfo) {
//                SPUtils.setParam(mActivity,"user","yespoint",String.valueOf(userInfo.getPOINTS_YESTERDAY()));
//                SPUtils.setParam(mActivity,"user","usepoint",String.valueOf(userInfo.getPOINTS_SURPLUS()));
//                SPUtils.setParam(mActivity,"user","bili",String.valueOf(userInfo.getBILI()));
//                SPUtils.setParam(mActivity,"user","code",String.valueOf(userInfo.getYQM()));
//                SPUtils.setParam(mActivity,"user","yesxb",String.valueOf(userInfo.getPOINTS_YESTERDAY_XB()));
//            }
//        };


    }

    @OnClick({R.id.mine_set, R.id.mine_exchange, R.id.mine_howGetMoney, R.id.mine_friend,R.id.mine_yqm,R.id.mine_id,R.id.mine_score})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_set:
                nextToActivity(mActivity, SetActivity.class);
                break;
            case R.id.mine_exchange:
                nextToActivity(mActivity, ExchangeActivity.class);
                break;
            case R.id.mine_howGetMoney:
                nextToActivity(mActivity, GetMoneyActivity.class);
                break;
            case R.id.mine_friend:
                nextToActivity(mActivity, BindWxActivity.class);
                break;
            case R.id.mine_yqm:
                nextToActivity(mActivity, CodeActivity.class);
                break;
            case R.id.mine_id:
                nextToActivity(mActivity, IncomeActivity.class);
                break;
            case R.id.mine_score:
                nextToActivity(mActivity, IncomeActivity.class);
                break;

        }
    }


    class MineGvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strs.length;
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
            View view = View.inflate(mActivity, R.layout.item_mine_gv, null);
            ImageView img = (ImageView) view.findViewById(R.id.item_gv_img);
            TextView tv = (TextView) view.findViewById(R.id.item_gv_tv);
            img.setImageResource(ids[position]);
            tv.setText(strs[position]);
            return view;
        }
    }




}
