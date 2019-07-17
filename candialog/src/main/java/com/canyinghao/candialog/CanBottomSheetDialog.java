package com.canyinghao.candialog;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;

import com.canyinghao.candialog.manager.DialogManager;
import com.canyinghao.candialog.manager.DialogManagerInterface;

/**
 * Created by jianyang on 2019/7/17.
 */

public class CanBottomSheetDialog extends BottomSheetDialog implements DialogManagerInterface {
    private Context con;
    public CanBottomSheetDialog(Context context) {
        super(context);
        this.con = context;
    }

    public CanBottomSheetDialog(Context context, int theme) {
        super(context, theme);
        this.con = context;
    }

    protected CanBottomSheetDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.con = context;
    }


    @Override
    public void showManager() {
        DialogManager.show(this);
    }

    @Override
    public Activity getActivity() {
        if(con instanceof Activity){
            return (Activity)con;
        }
        return null;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        DialogManager.showNext(this);
    }
}
