package com.canyinghao.candialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.canyinghao.candialog.manager.DialogManager;
import com.canyinghao.candialog.manager.DialogManagerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianyang on 2018/4/10.
 */

public class CanManagerDialog extends FrameLayout implements DialogManagerInterface {

    //   消失时监听
    protected List<CanDialogInterface.OnDismissListener> mOnDismissListeners;

    protected Activity currentActivity;

    public CanManagerDialog(@NonNull Context context) {
        super(context);
    }

    public CanManagerDialog(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanManagerDialog(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void show(){

    }


    public void dismiss(){
        DialogManager.showNext(this);
    }

    public void addOnDismissListener(CanDialogInterface.OnDismissListener onDismissListener) {
        if(this.mOnDismissListeners==null){
            this.mOnDismissListeners = new ArrayList<>();
        }
        this.mOnDismissListeners.add(onDismissListener);
    }


    public void showManager() {
        DialogManager.show(this);
    }

    @Override
    public Activity getActivity() {
        return this.currentActivity;
    }


    public void setActivity(Activity act) {
        this.currentActivity = act;
    }


}
