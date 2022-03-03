package com.canyinghao.candialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;


/**
 * Created by jianyang on 2019/7/17.
 */

public class BaseAppCompatDialog extends AppCompatDialog {

    private static int windowType = -1;

    public BaseAppCompatDialog(Context context) {
        this(context, 0);

    }

    public BaseAppCompatDialog(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    protected BaseAppCompatDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog();
    }

    private void initDialog() {
        try {
            if (windowType > 0) {
                Window dialogWindow = getWindow();
                if (null != dialogWindow) {
                    dialogWindow.setType(windowType);
                }
            }
            if (isFullDialog()) {
                fullDialog();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    private void fullDialog() {
        try {
            Window dialogWindow = getWindow();
            if (null != dialogWindow) {
                dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
                dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // 延伸显示区域到刘海
                    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                }
                dialogWindow.setAttributes(lp);
                //设置导航栏颜

                dialogWindow.getDecorView().
                        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                dialogWindow.setStatusBarColor(Color.TRANSPARENT);
                dialogWindow.setNavigationBarColor(Color.TRANSPARENT);

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected boolean isFullDialog() {
        return false;
    }


    public static void setWindowType(int type) {
        windowType = type;

    }

    public static int getWindowType() {
        return windowType;
    }


}
