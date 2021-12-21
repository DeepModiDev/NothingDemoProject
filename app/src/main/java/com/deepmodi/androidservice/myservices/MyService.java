package com.deepmodi.androidservice.myservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private static final String TAG = "MyService";

    // If onBind method returns the null it means that it is a started service.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // This method is used for starting a started service.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: service started");
        return super.onStartCommand(intent,flags,startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service is stopped.", Toast.LENGTH_SHORT).show();
    }

}
