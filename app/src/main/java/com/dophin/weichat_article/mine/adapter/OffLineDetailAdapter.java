package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.OffLine;
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

public class OffLineDetailAdapter extends RecyclerView.Adapter<OffLineDetailAdapter.ViewHolder> {



    private Context context;
    private List<OffLine> list;
    private int type;

    public OffLineDetailAdapter(Context context, List<OffLine> list,int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_offline_detail, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OffLine offLine = list.get(position);
        ImageLoader.displayImage(context,offLine.getIMG(),holder.itemOfImg,R.mipmap.denglutouxiang);
        if(StringUtils.isEmptyString(offLine.getNICKNAME())){
            holder.itemOfName.setText(offLine.getTEL().substring(0,3) + "****" + offLine.getTEL().substring(7,offLine.getTEL().length()));
        }else{
            holder.itemOfName.setText(offLine.getNICKNAME());
        }

        holder.itemOfId.setText(String.valueOf(offLine.getID()));
        if (type == 1){
            holder.itemOfNum.setText(String.valueOf(offLine.getLINE_SJ1()));
        }else if(type == 2){
            holder.itemOfNum.setText(String.valueOf(offLine.getLINE_SJ2()));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_of_img)
        CircleImageView itemOfImg;
        @BindView(R.id.item_of_name)
        TextView itemOfName;
        @BindView(R.id.item_of_id)
        TextView itemOfId;
        @BindView(R.id.item_of_num)
        TextView itemOfNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
