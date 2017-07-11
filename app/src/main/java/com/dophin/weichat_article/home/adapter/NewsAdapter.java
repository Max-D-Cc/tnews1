package com.dophin.weichat_article.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.home.bean.InnerNews;
import com.dophin.weichat_article.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/2/27.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private List<InnerNews> list;
    private View view;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    public NewsAdapter(Context context,List<InnerNews> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            View view = View.inflate(context, R.layout.item_all_type, null);
            return new ItemViewHolder(view);
        }else if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.item_loadmore, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){

        InnerNews innerNews = list.get(position);
        if(list.get(position).getSTATUE() == 2){
            ((ItemViewHolder) holder).itemType1Rl.setVisibility(View.VISIBLE);
            ((ItemViewHolder) holder).itemType2Rl.setVisibility(View.GONE);
            ((ItemViewHolder) holder).itemName1.setText(innerNews.getTITLE().trim());
//            holder.itemType1T

            ImageLoader.displayImage(context,innerNews.getIMAGE1(),((ItemViewHolder) holder).itemType1Img1,R.mipmap.test);
            ImageLoader.displayImage(context,innerNews.getIMAGE2(),((ItemViewHolder) holder).itemType1Img2,R.mipmap.test);
            ImageLoader.displayImage(context,innerNews.getIMAGE3(),((ItemViewHolder) holder).itemType1Img3,R.mipmap.test);
            if (innerNews.getADTYPE() == 1){
                ((ItemViewHolder) holder).itemType1Tv.setText("娱乐 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 2){
                ((ItemViewHolder) holder).itemType1Tv.setText("新闻 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 3){
                ((ItemViewHolder) holder).itemType1Tv.setText("财经 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 4){
                ((ItemViewHolder) holder).itemType1Tv.setText("搞笑 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 5){
                ((ItemViewHolder) holder).itemType1Tv.setText("图片 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 6){
                ((ItemViewHolder) holder).itemType1Tv.setText("笑话 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 7){
                ((ItemViewHolder) holder).itemType1Tv.setText("体育 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 8){
                ((ItemViewHolder) holder).itemType1Tv.setText("手机 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 9){
                ((ItemViewHolder) holder).itemType1Tv.setText("视频 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 10){
                ((ItemViewHolder) holder).itemType1Tv.setText("二次元 " +innerNews.getTIME());
            }
        }else{
            if (innerNews.isBdNative){
                ((ItemViewHolder) holder).itemIshot2.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).itemIshot2.setText(innerNews.bdNative.isDownloadApp() ? "下载" : "查看");
            }else if (innerNews.is360Native){
                ((ItemViewHolder) holder).itemIshot2.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).itemIshot2.setText("查看");
            }else{
                ((ItemViewHolder) holder).itemIshot2.setVisibility(View.GONE);
            }

//            if (innerNews.is360Native){
//                holder.itemIshot2.setVisibility(View.VISIBLE);
//                holder.itemIshot2.setText("查看");
//            }else{
//
//            }

            ((ItemViewHolder) holder).itemType1Rl.setVisibility(View.GONE);
            ((ItemViewHolder) holder).itemType2Rl.setVisibility(View.VISIBLE);
            ((ItemViewHolder) holder).itemName2.setText(innerNews.getTITLE().trim());
            ImageLoader.displayImage(context,innerNews.getIMAGE1(),((ItemViewHolder) holder).itemType2Img,R.mipmap.test);
            if (innerNews.getADTYPE() == 1){
                ((ItemViewHolder) holder).itemType2Tv.setText("娱乐 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 2){
                ((ItemViewHolder) holder).itemType2Tv.setText("新闻 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 3){
                ((ItemViewHolder) holder).itemType2Tv.setText("财经 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 4){
                ((ItemViewHolder) holder).itemType2Tv.setText("搞笑 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 5){
                ((ItemViewHolder) holder).itemType2Tv.setText("图片 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 6){
                ((ItemViewHolder) holder).itemType2Tv.setText("笑话 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 7){
                ((ItemViewHolder) holder).itemType2Tv.setText("体育 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 8){
                ((ItemViewHolder) holder).itemType2Tv.setText("手机 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 9){
                ((ItemViewHolder) holder).itemType2Tv.setText("视频 " +innerNews.getTIME());
            }else if(innerNews.getADTYPE() == 10){
                ((ItemViewHolder) holder).itemType2Tv.setText("二次元 " +innerNews.getTIME());
            }else{

            }
        }
        if (innerNews.isBdNative){
            ((ItemViewHolder) holder).itemType2Tv.setVisibility(View.GONE);
            innerNews.bdNative.recordImpression(view);
        }

        if (innerNews.is360Native){
            ((ItemViewHolder) holder).itemType2Tv.setVisibility(View.GONE);
            innerNews.nativeAd360.onAdShowed(view);
        }
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

        @BindView(R.id.item_name1)
        TextView itemName1;
        @BindView(R.id.item_name2)
        TextView itemName2;
        @BindView(R.id.item_ishot1)
        TextView itemIshot1;
        @BindView(R.id.item_type1_img1)
        ImageView itemType1Img1;
        @BindView(R.id.item_type1_img2)
        ImageView itemType1Img2;
        @BindView(R.id.item_type1_img3)
        ImageView itemType1Img3;
        @BindView(R.id.item_ll1)
        LinearLayout itemLl1;
        @BindView(R.id.item_type1_tv)
        TextView itemType1Tv;
        @BindView(R.id.item_type1_rl)
        RelativeLayout itemType1Rl;
        @BindView(R.id.item_type2_img)
        ImageView itemType2Img;
        @BindView(R.id.item_ishot2)
        TextView itemIshot2;
        @BindView(R.id.item_type2_tv)
        TextView itemType2Tv;
        @BindView(R.id.item_type2_rl)
        RelativeLayout itemType2Rl;

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

}
