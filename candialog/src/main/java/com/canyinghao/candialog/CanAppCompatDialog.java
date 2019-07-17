package com.canyinghao.candialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.canyinghao.candialog.manager.DialogManager;
import com.canyinghao.candialog.manager.DialogManagerInterface;

/**
 * Created by jianyang on 2019/7/17.
 */

public class CanAppCompatDialog extends AppCompatDialog implements DialogManagerInterface {
    private Context con;
    public CanAppCompatDialog(Context context) {
        super(context);
        this.con = context;
    }

    public CanAppCompatDialog(Context context, int theme) {
        super(context, theme);
        this.con = context;
    }

    protected CanAppCompatDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
