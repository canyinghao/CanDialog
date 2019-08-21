package com.canyinghao.candialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;

/**
 * Created by jianyang on 2019/7/17.
 */

public class BaseBottomSheetDialog extends BottomSheetDialog {

    public BaseBottomSheetDialog(Context context) {
        super(context, 0);

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

            if (BaseAppCompatDialog.getWindowType() > 0) {
                getWindow().setType(BaseAppCompatDialog.getWindowType());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


}
