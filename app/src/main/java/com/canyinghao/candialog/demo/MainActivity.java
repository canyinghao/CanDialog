package com.canyinghao.candialog.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


    }


    @OnClick({R.id.fab, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void click(View v) {
        switch (v.getId()) {

            case R.id.fab:


                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")
                        .setMessage("Dialog Message")
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", true, null)
                        .show();


                break;

            case R.id.button1:

                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")
                        .setMessage("Dialog Message")
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.TOP_RIGHT)
                        .show();
                break;
            case R.id.button2:

                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")

                        .setItems(new String[]{"item0", "item1", "item2"}, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int position, CharSequence text, boolean[] checkitems) {
                                App.showToast(text.toString());
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.TOP_LEFT)
                        .show();


                break;
            case R.id.button3:

                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")
                        .setSingleChoiceItems(new String[]{"item0", "item1", "item2"}, 1, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int position, CharSequence text, boolean[] checkitems) {

                                App.showToast(text.toString());

                            }
                        })
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", true, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
                                App.showToast("select " + checkItem);

                            }
                        })
                        .setTileAnimator()
                        .setCancelable(true)
                        .show();

                break;
            case R.id.button4:


                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")
                        .setMultiChoiceItems(new String[]{"item0", "item1", "item2"}, new boolean[]{false, false, false}, new CanDialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int position, boolean flag) {

                                App.showToast("item" + position + flag);


                            }
                        })
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", true, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {


                                String msg = "";
                                for (boolean check : checkItems) {
                                    msg += check + "  ";
                                }
                                App.showToast(msg);
                            }
                        })
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_LEFT)
                        .setCancelable(false)
                        .show();


                break;

            case R.id.button5:


                new CanDialog.Builder(this)
                        .setIconType(CanDialog.ICON_INFO)
                        .setTitle("Dialog Title")
                        .setEditDialog("input pwd", true, 8, 0)
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", false, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanDialog dialog, int position, CharSequence text, boolean[] checkitems) {


                                dialog.setAnimationMessage(CanDialog.ANIM_INFO_SUCCESS, "Password is " + text.toString());
                                dialog.setPositiveButton("sure", true, null);


                            }
                        })
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                        .setCancelable(true)
                        .show();

                break;


            case R.id.button6:


                startActivity(new Intent(MainActivity.this, ProgressActivity.class));

                break;


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
