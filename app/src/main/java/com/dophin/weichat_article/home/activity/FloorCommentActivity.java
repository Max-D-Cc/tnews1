package com.dophin.weichat_article.home.activity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.home.adapter.CommentActivityAdapter;
import com.dophin.weichat_article.home.adapter.FloorCommentAdapter;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.home.bean.FloorComment;
import com.dophin.weichat_article.home.bean.FloorCommentList;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.CircleImageView;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloorCommentActivity extends BaseActivity implements View.OnLayoutChangeListener{


    @BindView(R.id.floor_img)
    CircleImageView floorImg;
    @BindView(R.id.floor_name)
    TextView floorName;
    @BindView(R.id.floor_content)
    TextView floorContent;
    @BindView(R.id.floor_num)
    TextView floorNum;
    @BindView(R.id.floor_zan_img)
    ImageView floorZanImg;
    @BindView(R.id.floor_zan)
    RelativeLayout floorZan;
    @BindView(R.id.activity_floor_comment)
    RelativeLayout activityFloorComment;
    @BindView(R.id.floor_time)
    TextView floorTime;
    @BindView(R.id.floor_rl)
    RelativeLayout floorRl;
    @BindView(R.id.floor_lalal)
    TextView floorLalal;
    @BindView(R.id.floor_send)
    TextView floorSend;
    @BindView(R.id.floor_rv)
    RecyclerView floorRv;
    @BindView(R.id.floor_comment)
    EditText floorComment;

    private boolean isZan =false;
    private Comment comment;
    private List<FloorComment> list = new ArrayList<>();
    private FloorCommentAdapter adapter;
    private String type;
    private String id;
    private String floor;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private SubscriberOnNextListener<FloorCommentList> commentListSubscriberOnNextListener;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener1;

    private InputMethodManager inputMethodManager;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_floor_comment);
        ButterKnife.bind(this);
        comment = (Comment) getIntent().getSerializableExtra("comment");
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        floor = getIntent().getStringExtra("floor");
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("评论回复").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
        activityFloorComment.addOnLayoutChangeListener(this);
    }

    @Override
    public void initData() {
        if (comment != null){
            ImageLoader.displayImage(FloorCommentActivity.this,comment.getIMG(),floorImg,R.mipmap.denglutouxiang);
            floorName.setText(comment.getNICKNAME());
            floorContent.setText(comment.getCONTENT());
            floorNum.setText(String.valueOf(comment.getCLICKZAN()));
            floorZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isZan){
                        floorZanImg.setImageResource(R.mipmap.huifudianzandianji);
                        floorNum.setText(String.valueOf(comment.getCLICKZAN()+1));
                    }
                }
            });

        adapter = new FloorCommentAdapter(this,list);
        floorRv.setLayoutManager(new LinearLayoutManager(this));
        floorRv.setItemAnimator(new DefaultItemAnimator());
        floorRv.setAdapter(adapter);
            final String userId = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "userid", "");

            statueSubscriberOnNextListener1 = new SubscriberOnNextListener<Statue>() {
                @Override
                public void onNext(Statue statue) {
                    ToastUtils.getInstance().ToastShow(FloorCommentActivity.this,"点赞成功");
                }
            };

            adapter.setOnLikeClickListener(new FloorCommentAdapter.OnLikeAndReortClickListener() {
                @Override
                public void onLike(int position) {
                    if (!list.get(position).isFloorZan){
                    HttpMethods.getInstance().getFloorCommnetLike(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener1,FloorCommentActivity.this,1),String.valueOf(type),String.valueOf(id),userId,String.valueOf(comment.getFLOOR()),String.valueOf(position));
                    list.get(position).isFloorZan = true;
                    list.get(position).setCLICKZAN(list.get(position).getCLICKZAN() + 1);
                    adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onReport(int position) {

                }
            });

            commentListSubscriberOnNextListener = new SubscriberOnNextListener<FloorCommentList>() {
                @Override
                public void onNext(FloorCommentList floorCommentList) {
                    if (adapter != null && floorCommentList.getREPLYFLOOR() != null){
                        list.clear();
                        list.addAll(floorCommentList.getREPLYFLOOR());
                        adapter.notifyDataSetChanged();
                    }

                }
            };
            HttpMethods.getInstance().getFloorCommnetList(new ProgressSubscriber<FloorCommentList>(commentListSubscriberOnNextListener,FloorCommentActivity.this,1),type,id,"1",floor);


            statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
                @Override
                public void onNext(Statue statue) {
                    if (statue.getSTATUS() == 1){
                        floorComment.setText("");
                        ToastUtils.getInstance().ToastShow(FloorCommentActivity.this,"评论成功");
                        HttpMethods.getInstance().getFloorCommnetList(new ProgressSubscriber<FloorCommentList>(commentListSubscriberOnNextListener,FloorCommentActivity.this,1),type,id,"1",floor);

                    }else{
                        ToastUtils.getInstance().ToastShow(FloorCommentActivity.this,"评论失败");
                    }
                }
            };

        }

        floorSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment();
                inputMethodManager.hideSoftInputFromWindow(FloorCommentActivity.this.getCurrentFocus().getWindowToken(), 0);
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
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(FloorCommentActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            if (comment !=null){
                comment();
            }else{
                ToastUtils.getInstance().ToastShow(FloorCommentActivity.this,"评论失败");
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    private void comment() {
        String trim = floorComment.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            ToastUtils.getInstance().ToastShow(FloorCommentActivity.this,"评论内容不能为空");
            return;
        }

        String nickName = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "nickname", "");
        String username = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "username", "");
        String orderId = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "orderid", "");
        String img = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "img", "");
        String userId = (String) SPUtils.getParam(FloorCommentActivity.this, "user", "userid", "");
        if (StringUtils.isEmptyString(nickName)){
            HttpMethods.getInstance().getFloorCommnet(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,FloorCommentActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),username.substring(0,3)+"****"+username.substring(7,username.length()),img,trim,floor,String.valueOf(comment.getNAME()),comment.getNICKNAME(),comment.getIMG(),"1");

        }else{
            HttpMethods.getInstance().getFloorCommnet(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,FloorCommentActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),nickName,img,trim,floor,String.valueOf(comment.getNAME()),comment.getNICKNAME(),comment.getIMG(),"1");

//            HttpMethods.getInstance().getCommnet(new ProgressSubscriber<Statue>(commentListener,FloorCommentActivity.this,1),String.valueOf(type),String.valueOf(id),String.valueOf(userId),nickName,img,trim,"");
        }

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
//            ToastUtils.getInstance().ToastShow(this,"监听到软键盘弹起...");
                floorSend.setVisibility(View.VISIBLE);
//            articleComment.setVisibility(View.INVISIBLE);
//            articleSend.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

            }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
                floorSend.setVisibility(View.GONE);
//            articleComment.setVisibility(View.VISIBLE);
//            articleSend.setVisibility(View.GONE);
//            ToastUtils.getInstance().ToastShow(this,"监听到软件盘关闭...");
//            Toast.makeText(MainActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

            }
        }
}
