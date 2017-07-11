package com.dophin.weichat_article.base;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.ak.android.shell.AKAD;
import com.dophin.weichat_article.db.SQLHelper;
import com.fingermobi.vj.listener.IVJAPI;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by invinjun on 2016/3/2.
 */
public class Application extends android.app.Application {
    private boolean isGPS=false;//是否已经定位选择过城市

    public boolean isGPS() {
        return isGPS;
    }

    private SQLHelper sqlHelper;
    public void setGPS(boolean GPS) {
        isGPS = GPS;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        UMShareAPI.get(this);
        IVJAPI.applicationInit(this);
        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        init();
        instance = this;
        AKAD.initSdk(this,false,false);
        // 初始化参数依次为 this, AppId, AppKey，暂时关闭果聊，第二处splash和quick界面的账户登录
//        AVOSCloud.initialize(this,"AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");
//        MessageHandler msgHandler = new MessageHandler(this);
//        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, msgHandler);

//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    {

        PlatformConfig.setWeixin("wx97aed20e2ca7307a", "7d1d8da76eada94582dbca6c37c8943f");
        PlatformConfig.setQQZone("1105753760", "OBPPtdWvQTB5IvGv");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //
//        MultiDex.install(this);//方法数过多 导致
    }

    private void init(){
//        JPushInterface.init(getApplicationContext());
    }


    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(instance);
        return sqlHelper;
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
    }

    //运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    public static Application instance;
    //构造方法
    public Application(){}
    //实例化一次
    public synchronized static Application getInstance(){
        if (null == instance) {
            instance = new Application();
        }
        return instance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }
    //关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity:mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static Context getAppContext() {
        return instance;
    }
    public static Resources getAppResources() {
        return instance.getResources();
    }
}
