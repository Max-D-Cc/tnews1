package com.dophin.weichat_article.main.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.comment.fragment.CommentFragment;
import com.dophin.weichat_article.home.fragment.HomeFragment;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.main.bean.DownLoad;
import com.dophin.weichat_article.main.bean.TabEntity;
import com.dophin.weichat_article.mine.fragment.MineFragment;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.video.fragment.VideoFragment;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.widget.UpDialog;
import com.dophin.weichat_article.widget.WaringDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_body)
    FrameLayout flBody;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"首页", "视频", "热议", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.shouye, R.mipmap.shipin, R.mipmap.reyi, R.mipmap.wode};
    private int[] mIconSelectIds = {
            R.mipmap.shouyedianji, R.mipmap.shipindianji, R.mipmap.reyidianji, R.mipmap.wodedianji};

    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private CommentFragment commentFragment;
    private MineFragment mineFragment;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private Receicer receicer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
        initFragment(savedInstanceState);

        receicer = new Receicer();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.read");
        registerReceiver(receicer, filter);

        SubscriberOnNextListener<DownLoad> subscriberOnNextListener = new SubscriberOnNextListener<DownLoad>() {
            @Override
            public void onNext(final DownLoad downLoad) {
//                Log.e("cai",downLoad.getVISION());
                int versionCode = getVersion();
                int code = Integer.parseInt(downLoad.getVISION());
                SharePerUtils.putString(MainActivity.this,"appurl",downLoad.getADDRESS());

                if (code > versionCode) {

                    final WaringDialog dialog = new WaringDialog(MainActivity.this);
                    dialog.setTitle("版本更新").setContent("检测您的应用不是最新版本,是否更新到最新版本").setAgree("立即更新").setOnAgreeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            UpDialog upDataDialog = new UpDialog(MainActivity.this, downLoad.getADDRESS());
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.setCanceledOnTouchOutside(false);
                            upDataDialog.show();
                        }
                    }).setDisAgree("以后再说").setOnDisAgreeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    }).show();

                }
            }
        };

        HttpMethods.getInstance().getDownLoad(new ProgressSubscriber<DownLoad>(subscriberOnNextListener, MainActivity.this, 1), "1");

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void addActivity() {

    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag("videoFragment");
            commentFragment = (CommentFragment) getSupportFragmentManager().findFragmentByTag("commentFragment");
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
//            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            homeFragment = new HomeFragment();
            videoFragment = new VideoFragment();
            commentFragment = new CommentFragment();
            mineFragment = new MineFragment();

            transaction.add(R.id.fl_body, homeFragment, "homeFragment");
            transaction.add(R.id.fl_body, videoFragment, "videoFragment");
            transaction.add(R.id.fl_body, commentFragment, "commentFragment");
            transaction.add(R.id.fl_body, mineFragment, "mineFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
//        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(videoFragment);
                transaction.hide(commentFragment);
                transaction.hide(mineFragment);
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(homeFragment);
                transaction.hide(commentFragment);
                transaction.hide(mineFragment);
                transaction.show(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(videoFragment);
                transaction.hide(mineFragment);
                transaction.show(commentFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(videoFragment);
                transaction.hide(commentFragment);
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.getInstance().ToastShow(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class Receicer extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (tabLayout != null) {
                SwitchTo(0);
                tabLayout.setCurrentTab(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (receicer != null) {
            unregisterReceiver(receicer);
        }
        super.onDestroy();
    }
}
