package com.dophin.weichat_article.home.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.home.adapter.CommentAdaprer;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.home.bean.CommentList;
import com.dophin.weichat_article.home.bean.News;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.login.SplashActivity;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.mine.fragment.IncomeFragment;
import com.dophin.weichat_article.utils.DbUtils;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.ArticleScrollView;
import com.dophin.weichat_article.widget.EzhuanWebView;
import com.dophin.weichat_article.widget.SharePopu;
import com.dophin.weichat_article.widget.WrapHeightListView;
import com.snmi.sdk.BannerListener;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleActivity extends BaseActivity implements View.OnLayoutChangeListener{


    @BindView(R.id.article_web)
    WebView webView;
    @BindView(R.id.activity_article)
    RelativeLayout activityArticle;
    @BindView(R.id.article_share)
    ImageView articleShare;
    @BindView(R.id.article_collect)
    ImageView articleCollect;
    @BindView(R.id.article_comment)
    RelativeLayout articleComment;
    @BindView(R.id.article_comnum)
    TextView articleComnum;
    @BindView(R.id.article_back)
    ImageView articleBack;
    @BindView(R.id.article_title)
    TextView articleTitle;
    @BindView(R.id.article_sv)
    ArticleScrollView articleSv;
    @BindView(R.id.article_up)
    TextView articleUp;
    @BindView(R.id.article_down)
    TextView articleDown;
    @BindView(R.id.article_lv)
    WrapHeightListView articleLv;
    @BindView(R.id.article_pb)
    ProgressBar articlePb;
    @BindView(R.id.article_rl)
    LinearLayout articleRl;
    @BindView(R.id.article_view)
    View articleView;
    @BindView(R.id.article_tv)
    TextView articleTv;
    @BindView(R.id.article_send)
    TextView articleSend;
    @BindView(R.id.article_et_comment)
    EditText articleEtComment;
    private String url;
    private String id;
    private String title;
    private int type;
    private boolean isKan = true;

    private List<Comment> list = new ArrayList<>();
    private CommentAdaprer commentAdaprer;
    private SubscriberOnNextListener<Statue> commentListener;
    private SubscriberOnNextListener<CommentList> subscriberOnNextListener;
    private String image;
    private String num;
    private SubscriberOnNextListener<Statue> commentLikeListener;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private String orderId;
    private InputMethodManager imm;
    private Timer timer;
    private int ding;
    private int cai;
    private SubscriberOnNextListener<Statue> downListener;
    private SubscriberOnNextListener<Statue> upListener;
    private boolean existId;

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private InputMethodManager inputMethodManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type",0);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        image = getIntent().getStringExtra("img");
        num = getIntent().getStringExtra("num");
        orderId = (String) SPUtils.getParam(ArticleActivity.this, "user", "orderid", "");
        ding = getIntent().getIntExtra("ding", 0);
        cai = getIntent().getIntExtra("cai", 0);
        DbUtils dbUtils = new DbUtils(this);
        Log.e("uid","uid是"  + id);
        existId = dbUtils.isExistId(id);
        if (!existId){
            dbUtils.addNews(id);
        }
        Log.e("cai","getNewsClassify" + id + "boolean: " +existId);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

        Log.e("是否存在","---" + existId);
//        imm = (InputMethodManager) articleEtComment.getContext().getSystemService(
//                Context.INPUT_METHOD_SERVICE);
////        articleEtComment.clearFocus();
//        imm.showSoftInput(articleEtComment,0);
    }

    @Override
    public void initViews() {
        String smgappid = SharePerUtils.getString(this, "smgappid", "");
        String smgadid = SharePerUtils.getString(this, "smgadid", "");
        articleTitle.setText(title);
        LinearLayout ad = (LinearLayout) findViewById(R.id.article_ad);
//        smBanner(this,ad);
        com.zk.bannersdk.AdView.setAdInfo(this,"3bda14599d2a4a35847f0370b66b20d7","5010");
        com.zk.bannersdk.AdView adView = new com.zk.bannersdk.AdView(this);
        ad.addView(adView);
    }

    @Override
    public void initListeners() {

    }

    /**
     * 申米
     *
     * @param context
     * @param mainView
     */
    public static void smBanner(Context context, LinearLayout mainView) {
        try {
            com.snmi.sdk.AdView adView = new com.snmi.sdk.AdView(
                    "00168E7E66884E6994B0B15781DCF908", context,  //1591F59C403C436CA7FBDA6EDBB1EE8E //ce8e808baee727377bd0285c95917f8a
                    "E7FD4F41F5F940CF87B11F6D8E44F168");
            adView.setAdListener(new BannerListener() {
                @Override
                public void noAdFound() {
                }

                @Override
                public void bannerShown(String arg0) {
                }

                @Override
                public void bannerClosed() {
                }

                @Override
                public void bannerClicked() {
                }

                @Override
                public void adpageClosed() {
                }
            });
            mainView.addView(adView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
//        AdView adView = new AdView(this);

        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
//                Log.e("cai",statue.getMSG());
//                Log.e("cai","---" + statue.getSTATUS());
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastWzShow(ArticleActivity.this,"+" + statue.getSCORE() + "星币");
                }
            }
        };


        initWeb();
        int height = articleSv.getChildAt(0).getHeight();
        Log.e("cai","height: " + height);
        articleSv.setOnScrollListener(new ArticleScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
//                Log.e("cai", scrollY + "");
//                if (checkIsVisible(ArticleActivity.this,articleDown)) {
//                    Log.e("cai", "可见");
                    if (isKan){
                        runTime();
                    }
//                } else {
////                    Log.e("cai", "不可见");
//                }
            }
        });

        commentAdaprer = new CommentAdaprer(this,list);
        articleLv.setAdapter(commentAdaprer);

        commentLikeListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"点赞成功");

                }else{
//                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"点赞失败");
                }

            }
        };
        final String userId = (String) SPUtils.getParam(ArticleActivity.this, "user", "userid", "");
        commentAdaprer.setOnLikeClickListener(new CommentAdaprer.OnLikeClickListener() {
            @Override
            public void onLike(int position) {
                if (!list.get(position).isZan){
                    HttpMethods.getInstance().getCommnetLike(new ProgressSubscriber<Statue>(commentLikeListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id),userId,String.valueOf(position));
                    list.get(position).isZan = true;
                    list.get(position).setCLICKZAN(list.get(position).getCLICKZAN() + 1);
                    commentAdaprer.notifyDataSetChanged();
                }
            }

            @Override
            public void onReport(int position) {
                Comment comment = list.get(position);
                Intent intent = new Intent(ArticleActivity.this,FloorCommentActivity.class);
                intent.putExtra("comment",comment);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra("type",String.valueOf(type));
                intent.putExtra("floor",String.valueOf(comment.getFLOOR()));
                startActivity(intent);
                overridePendingTransition(R.anim.right_center, R.anim.center_left);
            }
        });



        subscriberOnNextListener = new SubscriberOnNextListener<CommentList>() {
            @Override
            public void onNext(CommentList commentList) {
                if (commentAdaprer != null && commentList.getREPLY() != null){
                    list.clear();
                    list.addAll(commentList.getREPLY());
                    commentAdaprer.notifyDataSetChanged();
                }
            }
        };

        HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id),"1");

        commentListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    articleEtComment.setText("");
                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"评论成功");
                    HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id),"1");
                }else{
                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"评论失败");
                }
            }
        };

        SubscriberOnNextListener<Statue> statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {

            }
        };

        HttpMethods.getInstance().getTimes(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,ArticleActivity.this,1),orderId,String.valueOf(type));

        downListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    articleDown.setText(String.valueOf(cai+1));
//                    ToastUtils.getInstance().ToastShow(Ar);
                }
            }
        };

        upListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                articleUp.setText(String.valueOf(ding+1));
            }
        };
    }


    private void runTime(){
        isKan = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!existId){
                            HttpMethods.getInstance().getAddMoney(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,ArticleActivity.this,1),orderId);
                        }
                    }
                });
            }
        }, 8000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        activityArticle.addOnLayoutChangeListener(this);
    }

    private void initWeb() {

        articleUp.setText(String.valueOf(ding));
        articleDown.setText(String.valueOf(cai))    ;
        articleLv.setVisibility(View.INVISIBLE);
        articleRl.setVisibility(View.INVISIBLE);
        articleView.setVisibility(View.INVISIBLE);
        articleTv.setVisibility(View.INVISIBLE);
        articleComnum.setText(num);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new JSAD(), "ad");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                articlePb.setVisibility(View.VISIBLE);
                articlePb.setProgress(i);
                if (i > 70){
                    articlePb.setVisibility(View.GONE);
                    articleLv.setVisibility(View.VISIBLE);
                    articleRl.setVisibility(View.VISIBLE);
                    articleView.setVisibility(View.VISIBLE);
                    articleTv.setVisibility(View.VISIBLE);

                }
            }
        });
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setLayoutAlgorithm(
//                WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(url);
//        webView.loadUrl("http://www.tengxungame.com/bzy/tengxungame/shipin/2017/2/7/1486422853800shipin1872.html");


    }

    @Override
    public void addActivity() {
        Application.getInstance().addActivity(this);
    }


    @OnClick({R.id.article_back, R.id.article_share, R.id.article_collect, R.id.article_comment,R.id.article_up, R.id.article_down,R.id.article_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_back:
                finish2Activity();
                break;
            case R.id.article_share:
                UMImage imagee = new UMImage(ArticleActivity.this,image);
                SharePopu popu = new SharePopu(ArticleActivity.this,imagee,title,title,url,image);
                popu.showAtLocation(View.inflate(this,
                        R.layout.activity_code, null), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.article_collect:
                break;
            case R.id.article_comment:
                Intent intent = new Intent(ArticleActivity.this,CommentActivity.class);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra("type",String.valueOf(type));
                startActivity(intent);
                overridePendingTransition(R.anim.right_center, R.anim.center_left);
                break;
            case R.id.article_up:
                HttpMethods.getInstance().getUp(new ProgressSubscriber<Statue>(upListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id));

                break;
            case R.id.article_down:
                HttpMethods.getInstance().getDown(new ProgressSubscriber<Statue>(downListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id));
                break;
            case R.id.article_send:
//                articleEtComment.setFocusable(false);
                comment();
                inputMethodManager.hideSoftInputFromWindow(ArticleActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
        }
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }


    public static Boolean checkIsVisible(Context context, View view) {
        // 如果已经加载了，判断广告view是否显示出来，然后曝光
        int screenWidth = getScreenMetrics(context).x;
        int screenHeight = getScreenMetrics(context).y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            //view已不在屏幕可见区域;
            return false;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            /*隐藏软键盘*/

            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(ArticleActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

//            articleEtComment.setFocusable(false);
            Log.e("cai","走了");
            comment();

            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void comment() {
        String trim = articleEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            ToastUtils.getInstance().ToastShow(ArticleActivity.this,"评论内容不能为空");
            return;
        }

        String nickName = (String) SPUtils.getParam(ArticleActivity.this, "user", "nickname", "");
        String username = (String) SPUtils.getParam(ArticleActivity.this, "user", "username", "");

        String img = (String) SPUtils.getParam(ArticleActivity.this, "user", "img", "");
        String userId = (String) SPUtils.getParam(ArticleActivity.this, "user", "userid", "");
        if (StringUtils.isEmptyString(nickName)){
            HttpMethods.getInstance().getCommnet(new ProgressSubscriber<Statue>(commentListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),username.substring(0,3)+"****"+username.substring(7,username.length()),img,trim,"");
        }else{
            HttpMethods.getInstance().getCommnet(new ProgressSubscriber<Statue>(commentListener,ArticleActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),nickName,img,trim,"");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
        }


        if (webView != null){
            try {
                webView.reload();
                webView.destroy();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
//            ToastUtils.getInstance().ToastShow(this,"监听到软键盘弹起...");
            articleShare.setVisibility(View.INVISIBLE);
                    articleComment.setVisibility(View.INVISIBLE);
                    articleSend.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
            Log.e("cai","键盘弹起");

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            articleShare.setVisibility(View.VISIBLE);
                    articleComment.setVisibility(View.VISIBLE);
                    articleSend.setVisibility(View.GONE);
//            ToastUtils.getInstance().ToastShow(this,"监听到软件盘关闭...");
//            Toast.makeText(MainActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
            Log.e("cai","键盘隐藏");
        }

    }
}
