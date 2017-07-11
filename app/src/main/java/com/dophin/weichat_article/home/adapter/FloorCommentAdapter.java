package com.dophin.weichat_article.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.home.bean.FloorComment;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/2/20.
 */

public class FloorCommentAdapter extends RecyclerView.Adapter<FloorCommentAdapter.ViewHolder> {



    private Context context;
    private List<FloorComment> list;
    private OnLikeAndReortClickListener listener;

    public FloorCommentAdapter(Context context, List<FloorComment> list) {
        this.context = context;
        this.list = list;
    }


    public interface OnLikeAndReortClickListener{
        void onLike(int position);
        void onReport(int position);
    }

    public void setOnLikeClickListener(OnLikeAndReortClickListener listener){
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_comment, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemCommentRl.setBackgroundColor(context.getResources().getColor(R.color.app_bg));
        FloorComment comment = list.get(position);
        ImageLoader.displayImage(context,comment.getIMG(),holder.itemCommentImg,R.mipmap.denglutouxiang);
        holder.itemCommentName.setText(comment.getNICKNAME());
        holder.itemCommentContent.setText(comment.getCONTENT());
        holder.itemCommentNum.setText(String.valueOf(comment.getCLICKZAN()));
        holder.itemCommentZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onLike(position);
                }
            }
        });

        holder.itemCommentTime.setText(comment.getJLTIME() + "");
        holder.itemCommentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onReport(position);
                }
            }
        });

        if (comment.isFloorZan){
            holder.itemCommentZanImg.setImageResource(R.mipmap.huifudianzandianji);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_comment_img)
        CircleImageView itemCommentImg;
        @BindView(R.id.item_comment_name)
        TextView itemCommentName;
        @BindView(R.id.item_comment_content)
        TextView itemCommentContent;
        @BindView(R.id.item_comment_num)
        TextView itemCommentNum;
        @BindView(R.id.item_comment_zan_img)
        ImageView itemCommentZanImg;
        @BindView(R.id.item_comment_zan)
        RelativeLayout itemCommentZan;
        @BindView(R.id.item_comment_rl)
        RelativeLayout itemCommentRl;
        @BindView(R.id.item_comment_time)
        TextView itemCommentTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
