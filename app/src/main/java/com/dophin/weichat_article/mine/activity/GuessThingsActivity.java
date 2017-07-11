package com.dophin.weichat_article.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.YesGuessRankAdapter;
import com.dophin.weichat_article.mine.bean.Guess;
import com.dophin.weichat_article.mine.bean.GuessList;
import com.dophin.weichat_article.mine.bean.GuessResult;
import com.dophin.weichat_article.mine.bean.GuessToday;
import com.dophin.weichat_article.mine.bean.GuessYesResult;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.GuessDialog;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.WaringDialog;
import com.dophin.weichat_article.widget.WrapHeightListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuessThingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.guess_rule)
    TextView guessRule;
    @BindView(R.id.guess_title)
    TextView guessTitle;
    @BindView(R.id.guess_bluetv)
    TextView guessBluetv;
    @BindView(R.id.guess_redtv)
    TextView guessRedtv;
    @BindView(R.id.guess_isguess)
    TextView guessIsguess;
    @BindView(R.id.guess_bluenum)
    TextView guessBluenum;
    @BindView(R.id.guess_rednum)
    TextView guessRednum;
    @BindView(R.id.guess_result)
    TextView guessResult;
    @BindView(R.id.guess_guess)
    TextView guessGuess;
    @BindView(R.id.guess_yesterday)
    TextView guessYesterday;
    @BindView(R.id.guess_yesterday_detail)
    TextView guessYesterdayDetail;
    @BindView(R.id.guess_yesterday_blue)
    TextView guessYesterdayBlue;
    @BindView(R.id.guess_yesterday_red)
    TextView guessYesterdayRed;
    @BindView(R.id.guess_yesterday_blue_num)
    TextView guessYesterdayBlueNum;
    @BindView(R.id.guess_yesterday_red_num)
    TextView guessYesterdayRedNum;
    @BindView(R.id.guess_yesterday_lv)
    WrapHeightListView guessYesterdayLv;
    private String orderId;
    private SubscriberOnNextListener<GuessResult> resultListener;
    private int id;
    private SubscriberOnNextListener<GuessToday> toadyListener;

    private List<Guess> list = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_guess_things);
        ButterKnife.bind(this);

        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("趣味猜").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();

    }

    @Override
    public void initListeners() {

        guessGuess.setOnClickListener(this);
        guessResult.setOnClickListener(this);
        guessRule.setOnClickListener(this);

    }

    @Override
    public void initData() {

        toadyListener = new SubscriberOnNextListener<GuessToday>() {

            @Override
            public void onNext(GuessToday guessToday) {
                id = guessToday.getID();
                guessTitle.setText(guessToday.getTITLE());
                guessBluetv.setText(guessToday.getZHENG());
                guessRedtv.setText(guessToday.getFAN());
                if (guessToday.getCOUNT_ZHENG() + guessToday.getCOUNT_FAN() != 0) {
                    guessIsguess.setText("当前您已押注");
                } else {
                    guessIsguess.setText("当前您未押注");
                }
                guessBluenum.setText("正方：" + guessToday.getCOUNT_ZHENG() + "注");
                guessRednum.setText("反方：" + guessToday.getCOUNT_FAN() + "注");

            }
        };

        HttpMethods.getInstance().getGuessToady(new ProgressSubscriber<GuessToday>(toadyListener, GuessThingsActivity.this, 1), orderId);

        SubscriberOnNextListener<GuessYesResult> yesListener = new SubscriberOnNextListener<GuessYesResult>() {
            @Override
            public void onNext(GuessYesResult guessYesResult) {
                if (guessYesResult.getSTATUS() != 2){


                if (!StringUtils.isEmptyString(guessYesResult.getTITLE())){
                    guessYesterday.setText("昨日话题："+guessYesResult.getTITLE());
                }else{
                    guessYesterday.setText("昨日话题：无");
                }

                if (guessYesResult.getTYPE() == 0){
                    guessYesterdayDetail.setText("昨日详情：你共计押注" + guessYesResult.getCOUNT() + "注,收益" + mul(0.1,guessYesResult.getCOUNT_ZHENG()) + "元" );
                    guessYesterdayBlue.setText("正方" + guessYesResult.getCOUNT_ZHENG() + "注：" + guessYesResult.getCOUNT_ZHENG() + " * 0.1 = " + mul(0.1,guessYesResult.getCOUNT_ZHENG()) + "元" );
                    guessYesterdayRed.setText("反方" + guessYesResult.getCOUNT_FAN() + "注：" + guessYesResult.getCOUNT_FAN() + " * 0 = " + (guessYesResult.getCOUNT_FAN()*0) + "元" );
                }else{
                    guessYesterdayDetail.setText("昨日详情：你共计押注" + guessYesResult.getCOUNT() + "注,收益" + mul(0.1,guessYesResult.getCOUNT_FAN()) + "元"  );
                    guessYesterdayBlue.setText("正方" + guessYesResult.getCOUNT_ZHENG() + "注：" + guessYesResult.getCOUNT_ZHENG() + " * 0 = " + (guessYesResult.getCOUNT_ZHENG()*0) + "元" );
                    guessYesterdayRed.setText("反方" + guessYesResult.getCOUNT_FAN() + "注：" + guessYesResult.getCOUNT_FAN() + " * 0.1 = " + mul(0.1,guessYesResult.getCOUNT_FAN()) + "元" );
                }

                guessYesterdayBlueNum.setText("正方：" + guessYesResult.getZHENG_NUM() + "     V");
                guessYesterdayRedNum.setText("S     " + "反方：" + guessYesResult.getFAN_NUM());
                }
            }
        };

        HttpMethods.getInstance().getGuessYesResult(new ProgressSubscriber<GuessYesResult>(yesListener, GuessThingsActivity.this, 1), orderId);

        resultListener = new SubscriberOnNextListener<GuessResult>() {
            @Override
            public void onNext(GuessResult guessResult) {

                final WaringDialog dialog = new WaringDialog(GuessThingsActivity.this);
                dialog.setTitle("当前投注结果").setWarming("不要偷偷告诉别人哦").setContent("正方投注数为 " + guessResult.getZHENG() + "注\n" + "反方投注数为 " + guessResult.getFAN() + "注").setCenterAgree("了解").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();
            }
        };


        final YesGuessRankAdapter rankAdapter = new YesGuessRankAdapter(this,list);
        guessYesterdayLv.setAdapter(rankAdapter);
        SubscriberOnNextListener<GuessList> guessListSubscriberOnNextListener = new SubscriberOnNextListener<GuessList>() {
            @Override
            public void onNext(GuessList guessList) {
                list.clear();
                if (guessList.getRESULT_MD() != null){
                    list.addAll(guessList.getRESULT_MD());
                    rankAdapter.notifyDataSetChanged();
                }

            }
        };

        HttpMethods.getInstance().getGuessList(new ProgressSubscriber<GuessList>(guessListSubscriberOnNextListener,GuessThingsActivity.this,1));

    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.guess_rule, R.id.guess_result, R.id.guess_guess})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guess_rule:
//                Toast.makeText(GuessThingsActivity.this,"rule",Toast.LENGTH_SHORT).show();
//                ToastUtils.getInstance().ToastShow(GuessThingsActivity.this,"查看规则");

                final WaringDialog dialog = new WaringDialog(GuessThingsActivity.this);
                dialog.setTitle("游戏规则").setWarming("账户余额大于5元方可参与").setContent("1.每个话题分为正反两方，可分别押注，每注0.1元\n2.成功押注后会扣减账户余额，并不能取消\n3.每个话题开奖时，押注量少的一方中奖\n4.中奖一方的押注，每注可获得0.2元，奖励开奖后，次日发放\n5.每位用户可以在截止押注前对每个话题多次押注，但不能修改之前的押注\n6.选择“偷看结果”，需要花费0.05元，看到当前话题在2小时前的投票结果").setCenterAgree("我知道了").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.guess_result:
                Date date = new Date();
                int hours = date.getHours();
                if (hours<6 || hours >21){
                    ToastUtils.getInstance().ToastShow(GuessThingsActivity.this,"当前不是投注时间，不能查看结果");
                    return;
                }
                final WaringDialog dialog1 = new WaringDialog(GuessThingsActivity.this);
                dialog1.setTitle("偷看结果").setContent("截止今日" + (hours-2) +":00的押注情况刚刚统计完成，你可以选择花费0.05元可以偷看最新统计情况").setAgree("取消").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                }).setDisAgree("花费0.05元").setOnDisAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        HttpMethods.getInstance().getGuessResult(new ProgressSubscriber<GuessResult>(resultListener,GuessThingsActivity.this,1),orderId,String.valueOf(id));
                    }
                }).show();
//                ToastUtils.getInstance().ToastShow(GuessThingsActivity.this,"result");
                break;
            case R.id.guess_guess:
                GuessDialog guessDialog = new GuessDialog(GuessThingsActivity.this, String.valueOf(id), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpMethods.getInstance().getGuessToady(new ProgressSubscriber<GuessToday>(toadyListener, GuessThingsActivity.this, 1), orderId);
                    }
                });
                guessDialog.show();
                break;
        }
    }


    public static double mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }



}
