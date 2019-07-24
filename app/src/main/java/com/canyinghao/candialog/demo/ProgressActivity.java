package com.canyinghao.candialog.demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.canyinghao.candialog.CanBaseDialog;
import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.manager.DialogManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


/**
 * Created by canyinghao on 16/3/3.
 */
public class ProgressActivity extends AppCompatActivity {


    Toolbar toolbar;

    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);


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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("fab");
            }
        });


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogManager.activityDestroy();
    }
}
