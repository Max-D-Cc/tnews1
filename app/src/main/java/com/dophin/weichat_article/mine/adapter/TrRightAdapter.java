package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/3.
 */

public class TrRightAdapter extends RecyclerView.Adapter<TrRightAdapter.ViewHolder> {



    private Context context;
    private int[] ints = {R.mipmap.tubaoxiang,R.mipmap.mubaoxiang,R.mipmap.tiebaoxiang,R.mipmap.tongbaoxiang,R.mipmap.yinbaoxiang,R.mipmap.jinbaoxiang,R.mipmap.zuanbaoxiang};
    private String strs1[] = {"土宝箱", "木宝箱", "铁宝箱", "铜宝箱", "银宝箱","金宝箱","钻宝箱"};
    private String strs2[] = {"要求：1000点经验", "要求：5000点经验", "要求：20000点经验", "要求：50000点经验", "要求：100000点经验","要求：500000点经验","要求：1000000点经验"};
    private String strs3[] = {"奖励：200-1000星币", "奖励：1000-5000星币", "奖励：5000-10000星币","奖励：10000-20000星币", "奖励：20000-50000星币","奖励：50000-100000星币","奖励：100000-1000000星币",};

    private OnGetClickListener listener;
    private List<Integer> list;

    public TrRightAdapter(Context context,List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnGetClickListener{
        void onGet(int position);
    }

    public void setOnGetClickListener(OnGetClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_tr_left, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemTrlImg.setImageResource(ints[position]);
        holder.itemTrlName.setText(strs1[position]);
        holder.itemTrlTv1.setText(strs2[position]);
        holder.itemTrlTv2.setText(strs3[position]);
        int integer = list.get(position);

        if (integer == 0){
            holder.itemTrlStatue.setText("未完成");
        }else if(integer == 1){
            holder.itemTrlStatue.setText("已领取");
        }else if(integer == 2){
            holder.itemTrlStatue.setText("可领取");
        }

        holder.itemTrlStatue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onGet(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_trl_img)
        ImageView itemTrlImg;
        @BindView(R.id.item_trl_name)
        TextView itemTrlName;
        @BindView(R.id.item_trl_tv1)
        TextView itemTrlTv1;
        @BindView(R.id.item_trl_tv2)
        TextView itemTrlTv2;
        @BindView(R.id.item_trl_statue)
        TextView itemTrlStatue;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
