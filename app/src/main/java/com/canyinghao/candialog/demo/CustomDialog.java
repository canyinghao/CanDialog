package com.canyinghao.candialog.demo;

import android.app.Activity;

import android.view.View;
import android.widget.Button;

import com.canyinghao.candialog.CanBaseDialog;



/**
 * Created by canyinghao on 2017/7/7.
 */

public class CustomDialog extends CanBaseDialog {


    public CustomDialog( Activity context) {
        super(context);
    }

    @Override
    protected void onCrate() {
        setContentView(R.layout.dialog_custom);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("CustomAppDialog");
            }
        });
    }



}
