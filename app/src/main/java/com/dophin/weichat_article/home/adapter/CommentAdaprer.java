package com.dophin.weichat_article.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.widget.CircleImageView;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/18.
 */

public class CommentAdaprer extends BaseAdapter{

    private Context context;
    private List<Comment> list;
    private OnLikeClickListener listener;


    public CommentAdaprer(Context contex,List<Comment> list){
        this.context = contex;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public interface OnLikeClickListener{
        void onLike(int position);
        void onReport(int position);
    }

    public void setOnLikeClickListener(OnLikeClickListener listener){
        this.listener = listener;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_comment,null);
            holder = new ViewHolder();
            holder.img = (CircleImageView) convertView.findViewById(R.id.item_comment_img);
            holder.content = (TextView) convertView.findViewById(R.id.item_comment_content);
            holder.name = (TextView) convertView.findViewById(R.id.item_comment_name);
            holder.zanNum = (TextView) convertView.findViewById(R.id.item_comment_num);
            holder.rl_zan = (RelativeLayout) convertView.findViewById(R.id.item_comment_zan);
            holder.zanImg = (ImageView) convertView.findViewById(R.id.item_comment_zan_img);
            holder.timeAndReport = (TextView) convertView.findViewById(R.id.item_comment_time);
            convertView.setTag(holder);
        }else{
           holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = list.get(position);
        ImageLoader.displayImage(context,comment.getIMG(),holder.img,R.mipmap.denglutouxiang);
        holder.name.setText(comment.getNICKNAME());
        holder.content.setText(comment.getCONTENT());
        holder.zanNum.setText(String.valueOf(comment.getCLICKZAN()));
        holder.rl_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onLike(position);
                }
            }
        });
        if (comment.isZan){
            holder.zanImg.setImageResource(R.mipmap.huifudianzandianji);
        }

        holder.timeAndReport.setText(comment.getJLTIME() + " 回复>");
        holder.timeAndReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onReport(position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder{
        public CircleImageView img;
        public RelativeLayout rl_zan;
        public ImageView zanImg;
        public TextView name;
        public TextView content;
        public TextView zanNum;
        public TextView timeAndReport;
    }

}
