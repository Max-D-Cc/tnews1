package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.Rank;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/3.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {



    private Context context;
    private List<Rank> list;
    private String type;

    public RankAdapter(Context context, List<Rank> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_rank, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rank rank = list.get(position);
        if (position == 0 ){
            holder.itemRankImg.setImageResource(R.mipmap.rank1);
            holder.itemRankImg.setVisibility(View.VISIBLE);
            holder.itemRankRank.setVisibility(View.GONE);
        }else if(position == 1){
            holder.itemRankImg.setVisibility(View.VISIBLE);
            holder.itemRankImg.setImageResource(R.mipmap.rank2);
            holder.itemRankRank.setVisibility(View.GONE);
        }else if(position == 2){
            holder.itemRankImg.setVisibility(View.VISIBLE);
            holder.itemRankImg.setImageResource(R.mipmap.rank3);
            holder.itemRankRank.setVisibility(View.GONE);
        }else{
            holder.itemRankImg.setVisibility(View.GONE);
            holder.itemRankRank.setVisibility(View.VISIBLE);
            holder.itemRankRank.setText(String.valueOf(position + 1));
        }
        if (type.equals("1")) {
            holder.itemRankNum.setText(String.valueOf(rank.getREADHTML_QIDAY()));
        } else if (type.equals("2")) {
            holder.itemRankNum.setText(String.valueOf(rank.getTUIG_QIDAY()));
        } else if (type.equals("3")) {
            holder.itemRankNum.setText(String.valueOf(rank.getPOINTS_QIDAY()));
        }

        if (TextUtils.isEmpty(rank.getNICKNAME())){
            holder.itemRankName.setText(rank.getTEL().substring(0,3) + "****" + rank.getTEL().substring(7,rank.getTEL().length()));
        }else{
            holder.itemRankName.setText(rank.getNICKNAME());
        }


        ImageLoader.displayImage(context,rank.getIMG(),holder.itemRankPhoto,R.mipmap.denglutouxiang);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_rank_img)
        ImageView itemRankImg;
        @BindView(R.id.item_rank_rank)
        TextView itemRankRank;
        @BindView(R.id.item_rank_photo)
        CircleImageView itemRankPhoto;
        @BindView(R.id.item_rank_name)
        TextView itemRankName;
        @BindView(R.id.item_rank_num)
        TextView itemRankNum;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
