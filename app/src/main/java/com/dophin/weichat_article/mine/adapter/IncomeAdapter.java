package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.IncomeLeft;
import com.dophin.weichat_article.mine.bean.IncomeRight;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/3.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {


    private Context context;
    private List<IncomeLeft> leftList;
    private List<IncomeRight> rightList;
    private String type;

    public IncomeAdapter(Context context, List<IncomeLeft> leftList,List<IncomeRight> rightList,String type) {
        this.context = context;
        this.leftList = leftList;
        this.rightList = rightList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_incom, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (type.equals("1")){
            IncomeLeft incomeLeft = leftList.get(position);
            holder.itemIncomeMoney.setText(incomeLeft.getPOINTS()+"星币");
            holder.itemIncomeName.setText(incomeLeft.getCHANNEL());
            holder.itemIncomeTime.setText(incomeLeft.getC_TIME());
        }else if(type.equals("2")){
            IncomeRight incomeRight = rightList.get(position);
            holder.itemIncomeMoney.setText(String.valueOf(incomeRight.getMONEY()));
            holder.itemIncomeName.setText(incomeRight.getCHANNEL());
            holder.itemIncomeTime.setText(incomeRight.getJLTIME());
        }

    }

    @Override
    public int getItemCount() {
        if (type.equals("1")){
            return leftList.size();
        }else if(type.equals("2")){
            return rightList.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_income_name)
        TextView itemIncomeName;
        @BindView(R.id.item_income_money)
        TextView itemIncomeMoney;
        @BindView(R.id.item_income_time)
        TextView itemIncomeTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
