package com.dophin.weichat_article.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.home.adapter.CommentActivityAdapter;
import com.dophin.weichat_article.home.adapter.CommentAdaprer;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.home.bean.CommentList;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.EndLessOnScrollListener;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends BaseActivity implements View.OnLayoutChangeListener{

    @BindView(R.id.comment_rv)
    RecyclerView commentRv;
    @BindView(R.id.comment_swipe)
    SwipeRefreshLayout commentSwipe;
    @BindView(R.id.comment_comment)
    EditText commentComment;
    @BindView(R.id.activity_comment)
    RelativeLayout activityComment;
    @BindView(R.id.comment_send)
    TextView commentSend;

    private List<Comment> list = new ArrayList<>();
    private SubscriberOnNextListener<Statue> commentListener;
    private SubscriberOnNextListener<CommentList> subscriberOnNextListener;
    private String type;
    private String id;
    private CommentActivityAdapter commentAdaprer;
    private SubscriberOnNextListener commentLikeListener;
    private InputMethodManager inputMethodManager;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("评论列表").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
    protected void onResume() {
        super.onResume();
        activityComment.addOnLayoutChangeListener(this);
    }

    @Override
    public void initData() {
        commentAdaprer = new CommentActivityAdapter(this,list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        commentRv.setLayoutManager(manager);
        commentRv.setItemAnimator(new DefaultItemAnimator());
        commentRv.setAdapter(commentAdaprer);
        final String  userId = (String) SPUtils.getParam(CommentActivity.this, "user", "userid", "");
        commentLikeListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    ToastUtils.getInstance().ToastShow(CommentActivity.this,"点赞成功");

                }else{
//                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"点赞失败");
                }

            }
        };

        commentRv.setOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),"1");

            }
        });

        commentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),"1");
            }
        });


        commentAdaprer.setOnLikeClickListener(new CommentActivityAdapter.OnLikeAndReortClickListener() {
            @Override
            public void onLike(int position) {
                if (!list.get(position).isZan){
                    HttpMethods.getInstance().getCommnetLike(new ProgressSubscriber<Statue>(commentLikeListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),userId,String.valueOf(position));
                    list.get(position).isZan = true;
                    list.get(position).setCLICKZAN(list.get(position).getCLICKZAN() + 1);
                    commentAdaprer.notifyDataSetChanged();
                }
            }

            @Override
            public void onReport(int position) {
                Comment comment = list.get(position);
                Intent intent = new Intent(CommentActivity.this,FloorCommentActivity.class);
                intent.putExtra("comment",comment);
                intent.putExtra("id",id);
                intent.putExtra("type",type);
                intent.putExtra("floor",String.valueOf(comment.getFLOOR()));
                startActivity(intent);
                overridePendingTransition(R.anim.right_center, R.anim.center_left);
            }
        });


        //                    ToastUtils.getInstance().ToastShow(ArticleActivity.this,"点赞失败");

        subscriberOnNextListener = new SubscriberOnNextListener<CommentList>() {
            @Override
            public void onNext(CommentList commentList) {
                if (commentAdaprer != null && commentList.getREPLY() != null){
                    commentSwipe.setRefreshing(false);
                    list.clear();
                    list.addAll(commentList.getREPLY());
                    commentAdaprer.notifyDataSetChanged();
                }
            }
        };

        HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),"1");

        commentListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                if (statue.getSTATUS() == 1){
                    commentComment.setText("");
                    ToastUtils.getInstance().ToastShow(CommentActivity.this,"评论成功");
                    HttpMethods.getInstance().getCommnetList(new ProgressSubscriber<CommentList>(subscriberOnNextListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),"1");
                }else{
                    ToastUtils.getInstance().ToastShow(CommentActivity.this,"评论失败");
                }
            }
        };


        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment();
                inputMethodManager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });


    }

    @Override
    public void addActivity() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            /*隐藏软键盘*/

            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(CommentActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            Log.e("cai","走了");
            comment();

            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    private void comment() {
        String trim = commentComment.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            ToastUtils.getInstance().ToastShow(CommentActivity.this,"评论内容不能为空");
            return;
        }

        String nickName = (String) SPUtils.getParam(CommentActivity.this, "user", "nickname", "");
        String username = (String) SPUtils.getParam(CommentActivity.this, "user", "username", "");
        String orderId = (String) SPUtils.getParam(CommentActivity.this, "user", "orderid", "");
        String img = (String) SPUtils.getParam(CommentActivity.this, "user", "img", "");
        String  userId = (String) SPUtils.getParam(CommentActivity.this, "user", "userid", "");
        if (StringUtils.isEmptyString(nickName)){
            HttpMethods.getInstance().getCommnet(new ProgressSubscriber<Statue>(commentListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),username.substring(0,3)+"****"+username.substring(7,username.length()),img,trim,"");
        }else{
            HttpMethods.getInstance().getCommnet(new ProgressSubscriber<Statue>(commentListener,CommentActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),nickName,img,trim,"");
        }

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
//            ToastUtils.getInstance().ToastShow(this,"监听到软键盘弹起...");
            commentSend.setVisibility(View.VISIBLE);
//            articleComment.setVisibility(View.INVISIBLE);
//            articleSend.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            commentSend.setVisibility(View.GONE);
//            articleComment.setVisibility(View.VISIBLE);
//            articleSend.setVisibility(View.GONE);
//            ToastUtils.getInstance().ToastShow(this,"监听到软件盘关闭...");
//            Toast.makeText(MainActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }
    }
}
