package com.canyinghao.candialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

/**
 * Created by jianyang on 2019/7/17.
 */

public class BaseAppCompatDialog extends AppCompatDialog {

    private static int windowType = -1;

    public BaseAppCompatDialog(Context context) {
        super(context, 0);

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
                getWindow().setType(windowType);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static void setWindowType(int type) {
        windowType = type;

    }

    public static int getWindowType() {
        return windowType;
    }
}
