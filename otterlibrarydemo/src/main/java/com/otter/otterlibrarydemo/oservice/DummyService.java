package com.otter.otterlibrarydemo.oservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.otter.otterlibrarydemo.R;

public class DummyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        StringBuilder text = new StringBuilder("Be ForegroundService");

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(DummyService.class.getSimpleName())
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(R.mipmap.ic_launcher, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
