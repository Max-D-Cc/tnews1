package com.dophin.weichat_article.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dophin.weichat_article.R;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.app_bg);//通知栏所需颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView();

        initViews();
        initListeners();
        initData();

        addActivity();
    }
    @Override
    protected void onStart() {
//        LogUtils.i("activity",":onstart");
        super.onStart();
    }

    @Override
    protected void onResume() {
//        LogUtils.i("activity",":onResume");
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
//        LogUtils.i("activity",":onDestroy");
        super.onDestroy();
    }


    public abstract void setContentView();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initData();

    public abstract void addActivity();

    //常用适配或提示方法
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }
    public void showShortToast(String text) {
        Toast.makeText(BaseActivity.this,text, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String text) {
        Toast.makeText(BaseActivity.this,text, Toast.LENGTH_LONG).show();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    protected void pre2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
        overridePendingTransition(R.anim.left_center, R.anim.center_right);
    }

    protected void next2Activity(Class<? extends Activity> tarActivity){
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }

    protected void nextToActivity(Class<? extends Activity> tarActivity){
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
        overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_center, R.anim.center_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void finish2Activity(){
        finish();
        overridePendingTransition(R.anim.left_center, R.anim.center_right);
    }
}
