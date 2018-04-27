package com.canyinghao.candialog.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.canyinghao.candialog.CanBaseDialog;
import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.CanManagerDialog;
import com.canyinghao.candialog.manager.DialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


    }


    @OnClick({R.id.fab, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8})
    public void click(View v) {
        switch (v.getId()) {

            case R.id.fab:


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
                                Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.showManager();


                startActivity(new Intent(MainActivity.this,MainActivity.class));


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
                            public void onClick(CanBaseDialog dialog, int position, CharSequence text, boolean[] checkitems) {
                                App.showToast(text.toString());
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)

                        .setFullBackgroundColor(Color.TRANSPARENT)
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.TOP_LEFT)
                        .show();


                break;
            case R.id.button3:

                new CanDialog.Builder(this)
                        .setTitle("Dialog Title")
                        .setSingleChoiceItems(new String[]{"item0", "item1", "item2"}, 1, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanBaseDialog dialog, int position, CharSequence text, boolean[] checkitems) {

                                App.showToast(text.toString());

                            }
                        })
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", true, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanBaseDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
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
                            public void onClick(CanBaseDialog dialog, int position, boolean flag) {

                                App.showToast("item" + position + flag);


                            }
                        })
                        .setNegativeButton("cancel", true, null)
                        .setPositiveButton("sure", true, new CanDialogInterface.OnClickListener() {
                            @Override
                            public void onClick(CanBaseDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {


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
                            public void onClick(CanBaseDialog dialog, int position, CharSequence text, boolean[] checkitems) {


                                dialog.setAnimationMessage(CanDialog.ANIM_INFO_SUCCESS, "Password is " + text.toString());
                                dialog.setPositiveButton("sure", true, null);


                            }
                        })
                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                        .setCancelable(true)
                        .show();

                break;


            case R.id.button6:

                new CustomDialog(this).setLeftRightMargin(0).setDialogHeight(500).setDialogWidth(500).show();

//                View view = LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
//
//                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//                final TextView et_name = (TextView) view.findViewById(R.id.et_name);
//
//                CanDialog dialog=   new CanDialog.Builder(this)
//                        .setTitle("Dialog Title")
//                        .setView(view)
//                        .setIsInput()
//                        .setNegativeButton("cancel", true, new CanDialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(CanBaseDialog dialog, int checkItem, CharSequence text, boolean[] checkItems) {
//
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                                imm.hideSoftInputFromWindow(et_name.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                            }
//                        })
//                        .setPositiveButton("sure", true, new CanDialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(CanBaseDialog dialog, int position, CharSequence text, boolean[] checkitems) {
//
//
//                                Toast.makeText(getApplicationContext(), et_name.getText().toString(), Toast.LENGTH_SHORT).show();
//
//
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                                imm.hideSoftInputFromWindow(et_name.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//
//                            }
//                        })
//                        .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
//                        .setCancelable(true)
//                        .show();
//
//
//
//
//
//                tv_name.setText("标题");

                break;

            case R.id.button7:


                startActivity(new Intent(MainActivity.this, ProgressActivity.class));

                break;

            case R.id.button8:


                startActivity(new Intent(MainActivity.this, ButtonActivity.class));

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


            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Dialog Title")
                    .setMessage("Dialog Message")
                    .setNegativeButton("cancel", null)
                    .setPositiveButton("sure", null)
                    .show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogManager.onDestroy(MainActivity.this);
    }
}
