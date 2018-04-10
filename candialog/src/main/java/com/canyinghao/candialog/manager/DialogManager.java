package com.canyinghao.candialog.manager;

import android.app.Activity;
import android.app.Dialog;

import com.canyinghao.candialog.CanBaseDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.CanManagerDialog;

import java.util.LinkedList;
import java.util.Queue;


public class DialogManager {


    private static Queue<DialogManagerInterface> dialogs = new LinkedList<>();

    private static DialogManagerInterface currentDialog;


    public static void show(DialogManagerInterface dialog) {

        try{
            if (dialog != null) {
                dialogs.offer(dialog);
            }

            if (currentDialog == null) {
                currentDialog = dialogs.poll();
                if (currentDialog != null) {

                    currentDialog.showManager();

                    if (currentDialog instanceof Dialog) {

                        Dialog dia = (Dialog) currentDialog;
                        dia.show();

                    }else if (currentDialog instanceof CanManagerDialog) {

                        CanManagerDialog canBaseDialog = ((CanManagerDialog) currentDialog);

                        canBaseDialog.addOnDismissListener(new CanDialogInterface.OnDismissListener() {

                            @Override
                            public void onDismiss(CanManagerDialog dialog) {
                                showNext(dialog);

                            }
                        });

                        canBaseDialog.show();


                    }


                }

            }
        }catch (Throwable e){
            e.printStackTrace();
        }



    }

    public static void showNext(DialogManagerInterface dialog) {
        dismiss(dialog);
        currentDialog = null;
        show(null);
    }

    public static void dismiss(DialogManagerInterface dialog) {
        if(dialog==null){
            return;
        }
        dialogs.remove(dialog);

    }

    public static void onDestroy() {

        if(dialogs==null||dialogs.isEmpty()){
            return;
        }

        Queue<DialogManagerInterface> temp = new LinkedList<>();
        temp.addAll(dialogs);
        for (DialogManagerInterface q : temp) {

            Activity activity = null;
            if (q instanceof Dialog) {
                activity = ((Dialog) q).getOwnerActivity();

            } else if (q instanceof CanBaseDialog) {

                activity = (Activity) ((CanBaseDialog) q).getContext();

            }

            if (activity == null || activity.isFinishing()) {

                dialogs.remove(q);

            }
        }

    }


    public static DialogManagerInterface getCurrentDialog() {
        return currentDialog;
    }

    public static Queue<DialogManagerInterface> getDialogs() {
        return dialogs;
    }
}
