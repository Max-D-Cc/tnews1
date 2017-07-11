package com.dophin.weichat_article.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.adapter.TaskAdapter;
import com.dophin.weichat_article.mine.bean.Task;
import com.dophin.weichat_article.mine.bean.TaskList;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.fingermobi.vj.activity.QdiActivity;
import com.fingermobi.vj.listener.IVJAPI;
import com.fingermobi.vj.listener.IVJAppidStatus;
import com.zy.phone.SDKInit;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.yqzq.sharelib.ShareManager;

public class TaskActivity extends BaseActivity {

    @BindView(R.id.task_lv)
    ListView taskLv;
    private List<Task> list = new ArrayList<>();
    private String orderId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

    }

    @Override
    public void initViews() {
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
        new TitleBuilder(this).setTitleText("任务广场").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();
//        DevInit.initGoogleContext(TaskActivity.this,"c8c3c60b43cbaa24b16466dd9dff9713");
//        DevInit.setCurrentUserID(TaskActivity.this, orderId);
//        DevInit.getTotalMoney(this, new GetTotalMoneyListener() {
//
//            @Override
//            public void getTotalMoneySuccessed(String name0, final long amount) {
//            }
//            @Override
//            public void getTotalMoneyFailed(String error) {
////                Toast.makeText(Ad_dianleActivity.this, "获取积分失败！"+error, Toast.LENGTH_SHORT).show();
//            }
//
//        });
//        AdManager.getInstance(TaskActivity.this).init("7d8f02140c2b3c49", "770fc061993dc0da", false);
//        OffersManager.getInstance(TaskActivity.this).onAppLaunch();
//        OffersManager.getInstance(TaskActivity.this).setCustomUserId(orderId);

        SDKInit.initAd(TaskActivity.this,"56a27fd67327cb2c", orderId);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        final TaskAdapter adapter = new TaskAdapter(this,list);
        taskLv.setAdapter(adapter);

        SubscriberOnNextListener<TaskList> taskListSubscriberOnNextListener = new SubscriberOnNextListener<TaskList>() {
            @Override
            public void onNext(TaskList taskList) {
                if (taskList.getTask() != null && adapter != null){
                    list.clear();
                    list.addAll(taskList.getTask());
                    adapter.notifyDataSetChanged();
                }
            }
        };

        HttpMethods.getInstance().getTaskList(new ProgressSubscriber<TaskList>(taskListSubscriberOnNextListener,TaskActivity.this,1));


        taskLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getWEB_URL().equals("weijia")){
                    IVJAPI ivjapi = new IVJAPI();
                    ivjapi.setAppid("a614adfaf5c899d3052a83404db9ec6e");
                    ivjapi.setGameid(orderId);
                    ivjapi.init(TaskActivity.this, new IVJAppidStatus() {

                        @Override
                        public void appidStatus(int status) {
                            switch (status) {
                                case IVJAPI.VJ_ERROR:
                                    // 失败
                                    break;
                                case IVJAPI.VJ_APPID_INVALID:
                                    // 广告位关闭
                                    break;
                                case IVJAPI.VJ_SUCCESS:
                                    // 广告位打开
                                    startActivity(new Intent(TaskActivity .this,
                                            QdiActivity.class));
                                    break;
                                case IVJAPI.VJ_CLOSE:
                                    // 界面被关闭
                                    break;
                                case IVJAPI.VJ_ON_PRESENT:
                                    // 界面被展示出来
                                    break;
                            }
                        }
                    });
                }else if(list.get(position).getWEB_URL().equals("wukong")){
                    ShareManager.getInstance(TaskActivity.this, "9feeec0519ffc6f68c31c82d2c80fcdc", orderId).setQQAppId("1105753760").show();;

                }else if(list.get(position).getWEB_URL().equals("dianjoy")){
//                    DevInit.initGoogleContext(TaskActivity.this,"c8c3c60b43cbaa24b16466dd9dff9713");
//                    DevInit.setCurrentUserID(TaskActivity.this, orderId);
//                    DevInit.showOffers(TaskActivity.this);
                }else if(list.get(position).getWEB_URL().equals("zhongyi")){

                    SDKInit.initAdList(TaskActivity.this);
                }else if(list.get(position).getWEB_URL().equals("youmi")){

//                    OffersManager.getInstance(TaskActivity.this).showOffersWall();
//                    OffersManager.getInstance(TaskActivity.this).showOffersWall();
                }else{
                    ToastUtils.getInstance().ToastShow(TaskActivity.this,"该渠道暂未开放");
                }
            }
        });



    }


    @Override
    public void addActivity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OffersManager.getInstance(this).onAppExit();
    }
}
