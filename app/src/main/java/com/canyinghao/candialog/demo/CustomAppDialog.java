package com.canyinghao.candialog.demo;

import android.content.Context;
import android.view.View;

import com.canyinghao.candialog.CanAppCompatDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ButterKnife.bind(this, this);
    }


    @OnClick({R.id.btn})
    public void click(View v){

        App.showToast("CustomAppDialog");
    }
}
