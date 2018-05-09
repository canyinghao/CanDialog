package com.canyinghao.candialog.manager;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by jianyang on 2018/5/9.
 */

public class DialogActivityAgent implements DialogManagerInterface {

    private Activity activity;
    private Intent intent;
    private int enterAnim;
    private int exitAnim;


    public DialogActivityAgent(Activity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;
    }

    public DialogActivityAgent(Activity activity, Intent intent, int enterAnim, int exitAnim) {
        this(activity, intent);
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
    }

    @Override
    public void showManager() {
        DialogManager.show(this);
    }

    @Override
    public Activity getActivity() {
        return activity;
    }


    public void showActivity() {

        activity.startActivity(intent);
        if (enterAnim != 0 && exitAnim != 0) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }


}
