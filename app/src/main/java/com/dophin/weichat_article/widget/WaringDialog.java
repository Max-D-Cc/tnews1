package com.dophin.weichat_article.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dophin.weichat_article.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caiguoqing on 2017/3/1.
 */

public class WaringDialog extends Dialog {

    @BindView(R.id.dialog_w_title)
    TextView dialogWTitle;
    @BindView(R.id.dialog_w_warming)
    TextView dialogWWarming;
    @BindView(R.id.dialog_w_content)
    TextView dialogWContent;
    @BindView(R.id.dialog_w_no)
    TextView dialogWNo;
    @BindView(R.id.dialog_w_yes)
    TextView dialogWYes;
    @BindView(R.id.dialog_w_agree)
    TextView dialogWAgree;
    private Context context;

    public WaringDialog(Context context) {
        this(context, R.style.AlertDialogStyle);
        this.context = context;
        initView();
    }


    public WaringDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void initView() {

        View inflate = View.inflate(context, R.layout.dialog_warming, null);
        setContentView(inflate);
        ButterKnife.bind(this,inflate);

    }

    public WaringDialog setTitle(String title) {
        dialogWTitle.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE
                : View.GONE);
        dialogWTitle.setText(title);
        return this;
    }


    public WaringDialog setWarming(String warming) {
        dialogWWarming.setVisibility(!TextUtils.isEmpty(warming) ? View.VISIBLE
                : View.GONE);
        dialogWWarming.setText(warming);
        return this;
    }

    public WaringDialog setContent(String content) {
        dialogWContent.setVisibility(!TextUtils.isEmpty(content) ? View.VISIBLE
                : View.GONE);
        dialogWContent.setText(content);
        return this;
    }

    public WaringDialog setDisAgree(String str) {
        dialogWNo.setVisibility(!TextUtils.isEmpty(str) ? View.VISIBLE
                : View.GONE);
        dialogWNo.setText(str);

        return this;
    }

    public WaringDialog setAgree(String str) {
        dialogWYes.setVisibility(!TextUtils.isEmpty(str) ? View.VISIBLE
                : View.GONE);
        dialogWYes.setText(str);

        return this;
    }

    public WaringDialog setCenterAgree(String str) {
        dialogWAgree.setVisibility(!TextUtils.isEmpty(str) ? View.VISIBLE
                : View.GONE);
        dialogWAgree.setText(str);
        return this;
    }

    public WaringDialog setOnAgreeListener(View.OnClickListener listener){
        if (dialogWYes.getVisibility() == View.VISIBLE){
            dialogWYes.setOnClickListener(listener);
        }else if(dialogWAgree.getVisibility() == View.VISIBLE){
            dialogWAgree.setOnClickListener(listener);
        }
        return this;
    }
    public WaringDialog setOnDisAgreeListener(View.OnClickListener listener){
        if (dialogWNo.getVisibility() == View.VISIBLE) {
            dialogWNo.setOnClickListener(listener);
        }
        return this;
    }
}
