package com.canyinghao.candialog.demo;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class App extends Application implements
        Thread.UncaughtExceptionHandler {

    private static App app;




    @Override
    public void onCreate() {
        super.onCreate();

        app = this;


        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 全局唯一的Application
     *
     * @return
     */
    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;

    }



    public static  void showToast(String msg){

        Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 处理应用崩溃
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {


        String eStr = getCrashReport(ex);


//        PgyCrashManager.reportCaughtException(this, new Exception(eStr));

        Log.e("CanDialog", eStr);
        try {

            File saveFile = new File(getExternalCacheDir(),"failLog.txt");
            FileOutputStream f = new FileOutputStream(saveFile);
            f.write(eStr.getBytes());
            f.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);

    }

    /**
     * 得到应用崩溃信息
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String expcetionStr = sw.toString();
        try {
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.close();
        return expcetionStr;
    }



}
