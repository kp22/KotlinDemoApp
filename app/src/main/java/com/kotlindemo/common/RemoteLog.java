package com.kotlindemo.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RemoteLog {

    private static RemoteLog remoteLog;
    BufferedWriter bw = null;
    FileWriter fw = null;
    Context context;
    String TAG="RemoteLog";
    File logDir;
    File logFile;
    private String FOLDER_NAME  = "AapLog";
    private String FILE_NAME = "Log";
    public RemoteLog(Context context) {
        this.context = context;
//        FILE_NAME = "log_"+new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        try {
            logDir = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
            if (!logDir.exists()) {
                Log.e(TAG,"logDir.mkdir() : "+logDir.mkdir());
            }
        } catch (Exception e) {
            Log.e(TAG, "ex  -: " + e.getMessage());
        }
    }
    private String getFullDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
        Date date = Calendar.getInstance().getTime();
        String getDate = sdf.format(date);
        return getDate;
    }

    public void addDebugLog(String log) {
        try {
            logDir = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
            if (!logDir.exists()) {
                Log.e(TAG,"logDir.mkdir() : "+logDir.mkdir());
            }

            String name = FILE_NAME;
            logFile = new File(logDir.getAbsolutePath(), name+".txt");

            if (!logFile.exists()) {
                Log.e(TAG,"logFile.createNewFile() : "+logFile.createNewFile());
            }

            if (fw == null)
                fw = new FileWriter(logFile.getAbsoluteFile(), true);

            if (bw == null)
                bw = new BufferedWriter(fw);

            bw.append(getFullDate() + " " + log + "\n");
            bw.flush();
        } catch (Exception e) {
            Log.d(TAG, "exe --: " + e.getMessage());
        }
    }

    public static RemoteLog getInstance(Context context){
        if(remoteLog==null)
            remoteLog  = new RemoteLog(context);
        return remoteLog;
    }
}
