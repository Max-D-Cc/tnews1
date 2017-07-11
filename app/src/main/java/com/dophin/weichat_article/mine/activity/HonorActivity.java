package com.dophin.weichat_article.mine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.HonorBottomAdapter;
import com.dophin.weichat_article.mine.adapter.HonorTopAdapter;
import com.dophin.weichat_article.mine.bean.Honor;
import com.dophin.weichat_article.mine.bean.HonorType;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.HonorDialog;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.WrapHeightGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HonorActivity extends BaseActivity {

//
//    private int[] topImgs = {R.mipmap.caihuahengyihuode,R.mipmap.caihuahengyihuode,R.mipmap.caihuahengyihuode,R.mipmap.caihuahengyihuode};
//    private int[] bottomImgs = {R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi,R.mipmap.caihuahengyi};

    @BindView(R.id.honor_top_gv)
    WrapHeightGridView honorTopGv;
    @BindView(R.id.honor_bottom_gv)
    WrapHeightGridView honorBottomGv;
    @BindView(R.id.honor_tv1)
    TextView honorTv1;
    @BindView(R.id.honor_tv2)
    TextView honorTv2;
    private String orderId;
    private List<String> topImgs = new ArrayList<>();
    private List<HonorType> bottomImgs = new ArrayList<>();
    private Honor thisHonor;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what = 1) {
                case 1:
                    ToastUtils.getInstance().ToastShow(HonorActivity.this, "恭喜您领取成功");
                    HttpMethods.getInstance().getHonor(new ProgressSubscriber<Honor>(listener, HonorActivity.this), orderId);
                    break;
            }
        }
    };
    private SubscriberOnNextListener<Honor> listener;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_honor);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("荣誉勋章").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
        final HonorTopAdapter topAdapter = new HonorTopAdapter(this, topImgs);
        final HonorBottomAdapter bottomAdapter = new HonorBottomAdapter(this, bottomImgs);
        honorTopGv.setAdapter(topAdapter);
        honorBottomGv.setAdapter(bottomAdapter);

        honorTopGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HonorDialog dialog = new HonorDialog(HonorActivity.this, topImgs.get(position), 1, handler);
                dialog.show();
            }
        });

        honorBottomGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HonorDialog dialog = new HonorDialog(HonorActivity.this, bottomImgs.get(position).getImg(), 2, handler);
                dialog.show();
            }
        });

        listener = new SubscriberOnNextListener<Honor>() {
            @Override
            public void onNext(Honor honor) {
                topImgs.clear();
                bottomImgs.clear();
                thisHonor = honor;

                if (honor.getCLFM() == 0 || honor.getCLFM() == 2 ) {
                    HonorType honorType = new HonorType("CLFM",honor.getCLFM());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("CLFM");
                }

                if (honor.getXYMQ() == 0 || honor.getXYMQ() == 2) {
                    HonorType honorType = new HonorType("XYMQ",honor.getXYMQ());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("XYMQ");
                }


                if (honor.getYHBY() == 0 || honor.getYHBY() == 2) {
                    HonorType honorType = new HonorType("YHBY",honor.getYHBY());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("YHBY");
                }


                if (honor.getGJSY() == 0 || honor.getGJSY() == 2) {
                    HonorType honorType = new HonorType("GJSY",honor.getGJSY());
                    bottomImgs.add(honorType);

                } else {
                    topImgs.add("GJSY");
                }


                if (honor.getPMTX() == 0 || honor.getPMTX() == 2) {
                    HonorType honorType = new HonorType("PMTX",honor.getPMTX());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("PMTX");
                }
//

                if (honor.getNZXS() == 0 || honor.getNZXS() == 2) {
                    HonorType honorType = new HonorType("NZXS",honor.getNZXS());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("NZXS");
                }
                //
                if (honor.getXBXK() == 0 || honor.getXBXK() == 2) {
                    HonorType honorType = new HonorType("XBXK",honor.getXBXK());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("XBXK");
                }
                //
                if (honor.getJYYS() == 0 || honor.getJYYS() == 2) {
                    HonorType honorType = new HonorType("JYYS",honor.getJYYS());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("JYYS");
                }
                //

                if (honor.getFJYF() == 0 || honor.getFJYF() == 2) {
                    HonorType honorType = new HonorType("FJYF",honor.getFJYF());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("FJYF");
                }
                //
                if (honor.getFKDG() == 0 || honor.getFKDG() == 2) {
                    HonorType honorType = new HonorType("FKDG",honor.getFKDG());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("FKDG");
                }
                //

                if (honor.getQTDS() == 0 || honor.getQTDS() == 2) {
                    HonorType honorType = new HonorType("QTDS",honor.getQTDS());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("QTDS");
                }

                //
                if (honor.getSBSJ() == 0 || honor.getSBSJ() == 2) {
                    HonorType honorType = new HonorType("SBSJ",honor.getSBSJ());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("SBSJ");
                }
                //
                if (honor.getCKCZ() == 0 || honor.getCKCZ() == 2) {
                    HonorType honorType = new HonorType("CKCZ",honor.getCKCZ());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("CKCZ");
                }
                //
                if (honor.getCHHY() == 0 || honor.getCHHY() == 2) {
                        HonorType honorType = new HonorType("CHHY",honor.getCHHY());
                        bottomImgs.add(honorType);
                } else {
                    topImgs.add("CHHY");
                }
                //
                if (honor.getMFJL() == 0 || honor.getMFJL() == 2) {
                    HonorType honorType = new HonorType("MFJL",honor.getMFJL());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("MFJL");
                }
                //
                if (honor.getYZBF() == 0 || honor.getYZBF() == 2) {
                    HonorType honorType = new HonorType("YZBF",honor.getYZBF());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("YZBF");
                }
                //
                if (honor.getCLBC() == 0 || honor.getCLBC() == 2) {
                    HonorType honorType = new HonorType("CLBC",honor.getCLBC());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("CLBC");
                }
                //
                if (honor.getDBYF() == 0 || honor.getDBYF() == 2)   {
                    HonorType honorType = new HonorType("DBYF",honor.getDBYF());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("DBYF");
                }
                //
                if (honor.getSXPM() == 0 || honor.getSXPM() == 2) {
                    HonorType honorType = new HonorType("SXPM",honor.getSXPM());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("SXPM");
                }//

                if (honor.getDGQB() == 0 || honor.getDGQB() == 2) {
                    HonorType honorType = new HonorType("DGQB",honor.getDGQB());
                    bottomImgs.add(honorType);
                } else {
                    topImgs.add("DGQB");
                }

                bottomAdapter.notifyDataSetChanged();
                topAdapter.notifyDataSetChanged();

                honorTv1.setText("已获得：" + topImgs.size() + "个");
                honorTv2.setText("未获得：" + bottomImgs.size() + "个");

            }
        };


        HttpMethods.getInstance().getHonor(new ProgressSubscriber<Honor>(listener, HonorActivity.this), orderId);

    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }


}
