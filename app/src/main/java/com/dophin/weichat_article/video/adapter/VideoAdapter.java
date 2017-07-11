package com.dophin.weichat_article.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.home.bean.InnerNews;
import com.dophin.weichat_article.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/2/28.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<InnerNews> list;
    private View view;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public VideoAdapter(Context context, List<InnerNews> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.item_video, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loadmore, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            InnerNews innerNews = list.get(position);
            if (innerNews.isBdNative) {
                innerNews.bdNative.recordImpression(view);
                ((ItemViewHolder) holder).itemVideoIshot.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).itemVideoIshot.setText(innerNews.bdNative.isDownloadApp() ? "下载" : "查看");
                ((ItemViewHolder) holder).itemVideoTv.setText("");
            } else if (innerNews.is360Native) {
                innerNews.nativeAd360.onAdShowed(view);
                ((ItemViewHolder) holder).itemVideoIshot.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).itemVideoIshot.setText("查看");
                ((ItemViewHolder) holder).itemVideoTv.setText("");

            } else {
                ((ItemViewHolder) holder).itemVideoIshot.setVisibility(View.GONE);
                ((ItemViewHolder) holder).itemVideoTv.setText("视频 " + innerNews.getTIME());
            }

            ImageLoader.displayImage(context, innerNews.getIMAGE1(), ((ItemViewHolder) holder).itemVideoImg, R.mipmap.test);
            ((ItemViewHolder) holder).itemVideoTitle.setText(innerNews.getTITLE());
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_video_img)
        ImageView itemVideoImg;
        @BindView(R.id.item_video_title)
        TextView itemVideoTitle;
        @BindView(R.id.item_video_ishot)
        TextView itemVideoIshot;
        @BindView(R.id.item_video_tv)
        TextView itemVideoTv;
//        private Context context;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        view = View.inflate(context, R.layout.item_video, null);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        InnerNews innerNews = list.get(position);
//        if (innerNews.isBdNative){
//            innerNews.bdNative.recordImpression(view);
//            holder.itemVideoIshot.setVisibility(View.VISIBLE);
//            holder.itemVideoIshot.setText(innerNews.bdNative.isDownloadApp() ? "下载" : "查看");
//            holder.itemVideoTv.setText("");
//        }else if(innerNews.is360Native){
//            innerNews.nativeAd360.onAdShowed(view);
//            holder.itemVideoIshot.setVisibility(View.VISIBLE);
//            holder.itemVideoIshot.setText("查看");
//            holder.itemVideoTv.setText("");
//
//        }else{
//            holder.itemVideoIshot.setVisibility(View.GONE);
//            holder.itemVideoTv.setText("视频 " + innerNews.getTIME());
//        }
//
//        ImageLoader.displayImage(context,innerNews.getIMAGE1(),holder.itemVideoImg,R.mipmap.test);
//        holder.itemVideoTitle.setText(innerNews.getTITLE());
//
////        holder.
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//
//        @BindView(R.id.item_video_img)
//        ImageView itemVideoImg;
//        @BindView(R.id.item_video_title)
//        TextView itemVideoTitle;
//        @BindView(R.id.item_video_ishot)
//        TextView itemVideoIshot;
//        @BindView(R.id.item_video_tv)
//        TextView itemVideoTv;
////        private Context context;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
