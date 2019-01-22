package com.canyinghao.candialog.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.canyinghao.candialog.CanDialogInterface;
import com.canyinghao.candialog.CanManagerDialog;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class DialogManager {


    private static final Map<Context, Queue<DialogManagerInterface>> map = new ArrayMap<>();
    private static final Map<Context, DialogManagerInterface> currentMap = new ArrayMap<>();


    private static DialogManagerInterface currentActivityDialog;

    public static void show(DialogManagerInterface dialog) {

        try {

            if (dialog != null) {

                Context context = getCurrentContext(dialog);


                if (map.containsKey(context)) {
                    Queue<DialogManagerInterface> queues = map.get(context);
                    queues.offer(dialog);
                } else {
                    Queue<DialogManagerInterface> queues = new LinkedList<>();
                    queues.offer(dialog);
                    map.put(context, queues);
                }

            }

            Set<Context> set = map.keySet();


            for (Context context : set) {

                if (!currentMap.containsKey(context) || currentMap.get(context) == null) {

                    Queue<DialogManagerInterface> dialogs = map.get(context);

                    if (dialogs != null && !dialogs.isEmpty()) {

                        boolean isFinishing = false;
                        if(context instanceof Activity){
                            Activity act = (Activity) context;
                            if(act.isFinishing()){
                                isFinishing = true;
                            }
                        }

                        if(isFinishing){
                            map.remove(context);
                        }else{
                            DialogManagerInterface currentDialog = dialogs.poll();

                            if (currentDialog != null) {

                                currentMap.put(context, currentDialog);

                                if (dialogs.contains(currentDialog)) {
                                    dialogs.remove(currentDialog);
                                }

                                if (dialogs.isEmpty()) {
                                    map.remove(context);
                                }
                                currentDialog.showManager();

                                if (currentDialog instanceof Dialog) {

                                    Dialog dia = (Dialog) currentDialog;

                                    dia.show();

                                } else if (currentDialog instanceof CanManagerDialog) {

                                    CanManagerDialog canBaseDialog = ((CanManagerDialog) currentDialog);

                                    canBaseDialog.addOnDismissListener(new CanDialogInterface.OnDismissListener() {

                                        @Override
                                        public void onDismiss(CanManagerDialog dialog) {
                                            showNext(dialog);

                                        }
                                    });

                                    canBaseDialog.show();


                                } else if (currentDialog instanceof DialogActivityAgent) {
                                    if (currentActivityDialog == null) {
                                        DialogActivityAgent canBaseDialog = ((DialogActivityAgent) currentDialog);
                                        canBaseDialog.showActivity();
                                        currentActivityDialog = currentDialog;
                                    }
                                }


                            }

                        }



                    } else {

                        map.remove(context);
                    }


                }


            }


        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    public static void showNext(DialogManagerInterface dialog) {

        Context context = getCurrentContext(dialog);


        if (currentMap.containsKey(context)) {
            currentMap.remove(context);
        }

        if (map.containsKey(context)) {
            Queue<DialogManagerInterface> dialogs = map.get(context);
            if (dialogs != null && dialogs.contains(dialog)) {
                dialogs.remove(dialog);
            }

            if (dialogs == null || dialogs.isEmpty()) {
                map.remove(context);
            }
        }


        show(null);
    }


    public static void onDestroy(Context context) {


        if (map.containsKey(context)) {
            map.remove(context);
        }

        if (currentMap.containsKey(context)) {
            currentMap.remove(context);
        }


    }


    public static Activity getCurrentContext(DialogManagerInterface dialog) {

        return dialog.getActivity();
    }

    public static Map<Context, DialogManagerInterface> getCurrentMap() {
        return currentMap;
    }

    public static Map<Context, Queue<DialogManagerInterface>> getMap() {
        return map;
    }


    public static void activityDestroy() {

        if (currentActivityDialog != null) {

            Context context = getCurrentContext(currentActivityDialog);
            if (currentMap.containsKey(context)) {
                currentMap.remove(context);
            }
            if (map.containsKey(context)) {
                Queue<DialogManagerInterface> dialogs = map.get(context);

                if (dialogs != null && dialogs.contains(currentActivityDialog)) {
                    dialogs.remove(currentActivityDialog);
                }
                if (dialogs == null || dialogs.isEmpty()) {
                    map.remove(context);
                }
            }
            currentActivityDialog = null;

            show(null);
        }


    }
}
