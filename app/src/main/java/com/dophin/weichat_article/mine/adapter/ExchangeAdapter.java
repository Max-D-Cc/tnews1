package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.Exchange;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/3.
 */

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ViewHolder> {


    private Context context;
    private List<Exchange> list;

    public ExchangeAdapter(Context context, List<Exchange> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_exchang, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exchange exchange = list.get(position);
        if (exchange.getEX_WAY() == 0){
            holder.itemExchangType.setText("手机话费");
        }else if(exchange.getEX_WAY() == 1){
            holder.itemExchangType.setText("支付宝");
        }else{
            holder.itemExchangType.setText("QB");
        }

        if (exchange.getFLAG() == 1){
            holder.itemExchangStatue.setText("正在处理");
        }else if(exchange.getFLAG() == 2){
            holder.itemExchangStatue.setText("兑换失败");
        }else if(exchange.getFLAG() == 3){
            holder.itemExchangStatue.setText("兑换成功");
        }

        holder.itemExchangMoney.setText(String.valueOf(exchange.getMONEY()));
        holder.itemExchangTime.setText(exchange.getC_TIME());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_exchang_type)
        TextView itemExchangType;
        @BindView(R.id.item_exchang_money)
        TextView itemExchangMoney;
        @BindView(R.id.item_exchang_time)
        TextView itemExchangTime;
        @BindView(R.id.item_exchang_statue)
        TextView itemExchangStatue;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
