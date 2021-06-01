package com.canyinghao.candialog.demo;

import android.os.Bundle;
import android.widget.Toast;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.CanManagerDialog;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by canyinghao on 16/9/13.
 */
public class ButtonActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);



        new CanDialog.Builder(this)
                .setSystemDialog(true)
                .setIconType(CanDialog.ICON_WARNING)
                .setTitle("System Title 1")
                .setMessage("Dialog Message")
                .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                .setNegativeButton("cancel", true, null)
                .setPositiveButton("sure", true, null)
                .create()
                .showManager();


        new CanDialog.Builder(this)
                .setIconType(CanDialog.ICON_WARNING)
                .setTitle("Dialog Title 2")
                .setMessage("Dialog Message")
                .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                .setNegativeButton("cancel", true, null)
                .setPositiveButton("sure", true, null)
                .create()
                .showManager();


        CanDialog dialog = new CanDialog.Builder(this)
                .setIconType(CanDialog.ICON_WARNING)
                .setTitle("Dialog Title 3")
                .setMessage("Dialog Message")
                .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                .setNegativeButton("cancel", true, null)
                .setPositiveButton("sure", true, null)
                .create();
        dialog.setOnDismissListener(new CanDialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(CanManagerDialog dialog) {
                Toast.makeText(ButtonActivity.this,"test",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.showManager();





    }
}
