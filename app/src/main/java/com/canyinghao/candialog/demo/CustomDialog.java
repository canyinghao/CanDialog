package com.canyinghao.candialog.demo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.canyinghao.candialog.CanBaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by canyinghao on 2017/7/7.
 */

public class CustomDialog extends CanBaseDialog {
    @BindView(R.id.btn)
    Button btn;

    public CustomDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected void onCrate() {
        setContentView(R.layout.dialog_custom);
        ButterKnife.bind(this, this);


    }

    @OnClick({R.id.btn})
    public void click(View v){

        App.showToast("hello world");
    }

}
