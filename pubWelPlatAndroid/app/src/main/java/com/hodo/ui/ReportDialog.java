package com.hodo.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hodo.R;

public class ReportDialog extends Dialog{
    private Context context;
    private TextView imageButtonCancel;
    private TextView buttonConfirm;
    private String message;

    public ReportDialog( Context context) {
        super(context);
        this.context = context;
    }

    public ReportDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ReportDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.report_detail,null);
        setContentView(view);

//        Window win = getWindow();
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.height = DensityUtil.dip2px(context,250);
//        lp.width = DensityUtil.dip2px(context,200);
//        win.setAttributes(lp);

        view.findViewById(R.id.textwzdl).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.textckxq).setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }






}