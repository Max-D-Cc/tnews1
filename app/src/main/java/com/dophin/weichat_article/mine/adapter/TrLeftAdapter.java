package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/3.
 */

public class TrLeftAdapter extends RecyclerView.Adapter<TrLeftAdapter.ViewHolder> {



    private Context context;
    private OnTaskClickListener listener;
    private String strs1[] = {"任务广场", "文章阅读", "玩一次趣味猜", "邀请学徒", "使用时长"};
    private String strs2[] = {"经验 +10", "经验 +1", "经验 +20", "经验 +30", "经验 +10"};
    private String strs3[] = {"完成任务广场一次 （限100经验/天）", "阅读文章一篇 （限100经验/天）", "玩一次趣味猜 （限20经验/天）", "邀请学徒1位  （限1500经验/天）", "使用时长  （限50经验/天）"};

    private String str4[] = {"去完成","去完成","去完成","去完成","去完成",};

    public TrLeftAdapter(Context context) {
        this.context = context;
    }

    public interface OnTaskClickListener{
        void onClick(int position);
    }

    public void setOnTaskClickListener(OnTaskClickListener listener){
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

        holder.itemTrlImg.setVisibility(View.GONE);
        holder.itemTrlName.setText(strs1[position]);
        holder.itemTrlTv1.setText(strs2[position]);
        holder.itemTrlStatue.setText(str4[position]);
        holder.itemTrlTv2.setText(strs3[position]);

        holder.itemTrlStatue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return strs1.length;
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
