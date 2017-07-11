package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.Guess;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/23.
 */

public class YesGuessRankAdapter extends BaseAdapter {

    private Context context;
    private List<Guess> list;

    public YesGuessRankAdapter(Context context,List<Guess> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(context, R.layout.item_yesguess_rank, null);
        TextView rank = (TextView) inflate.findViewById(R.id.item_yg_rank);
        TextView id = (TextView) inflate.findViewById(R.id.item_yg_id);
        TextView money = (TextView) inflate.findViewById(R.id.item_yg_money);

        Guess guess = list.get(position);
        rank.setText(String.valueOf(position+1));
        id.setText(String.valueOf(guess.getUSERID()));
        money.setText(String.valueOf(guess.getMONEY()));

        return inflate;
    }
}
