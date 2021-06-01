package com.canyinghao.candialog.demo;

import android.content.Context;
import android.view.View;

import com.canyinghao.candialog.CanAppCompatDialog;



/**
 * Created by jianyang on 2019/7/17.
 */

public class CustomAppDialog extends CanAppCompatDialog {
    public CustomAppDialog(Context context) {
        super(context);
        init();
    }

    public CustomAppDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected CustomAppDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    private void init(){
        setContentView(R.layout.dialog_custom);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("CustomAppDialog");
            }
        });
    }



}
