package com.ojh.www.uncaughtexceptionsample;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by JaeHwan Oh on 2016-06-03.
 */
public class MyApplication extends Application {

    private UncaughtExceptionHandler mHandler;
    Context mContext;

    @Override
    public void onCreate() {
//        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        mContext = this;
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughExceptionHandler());
        super.onCreate();
    }

    class MyUncaughExceptionHandler implements UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Intent intent = new Intent(mContext, ErrorActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(mContext, 123456, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis()+10, mPendingIntent);
            System.exit(1);
//            mHandler.uncaughtException(thread, ex);
        }
    }
}