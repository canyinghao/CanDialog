package com.canyinghao.candialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;

/**
 * Created by jianyang on 2019/7/17.
 */

public class BaseBottomSheetDialog extends BottomSheetDialog {

    private static int windowType = -1;

    public BaseBottomSheetDialog(Context context) {
        this(context, 0);

    }

    public BaseBottomSheetDialog(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    protected BaseBottomSheetDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
