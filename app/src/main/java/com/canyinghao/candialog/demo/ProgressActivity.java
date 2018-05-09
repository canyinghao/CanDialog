package com.canyinghao.candialog.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.canyinghao.candialog.CanBaseDialog;
import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.manager.DialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by canyinghao on 16/3/3.
 */
public class ProgressActivity extends AppCompatActivity  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);


        toolbar.setTitle("Progress");

        final CanDialog dialog = new CanDialog.Builder(this)
                .setProgress("loading")
                .show();
        dialog.setOnKeyListener(new CanDialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(CanBaseDialog dialog, int code, KeyEvent event) {
                dialog.dismiss();
                return false;
            }
        });




    }


    @OnClick(R.id.fab)
    public void click(View v) {

        App.showToast("fab");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogManager.activityDestroy();
    }
}
