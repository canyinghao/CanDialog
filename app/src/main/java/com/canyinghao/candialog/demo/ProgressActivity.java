package com.canyinghao.candialog.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.candialog.CanDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangjian on 16/3/3.
 */
public class ProgressActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);


        toolbar.setTitle("Progress");

        final CanDialog dialog=   new CanDialog.Builder(this)
                .setProgress("CD","loading")
                .show();


        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

            }
        }, 5000);





    }


    @OnClick(R.id.fab)
    public void click(View v){

        App.showToast("fab");
    }
}
