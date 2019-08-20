package com.example.dataapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public static String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService(){
        super("MyIntentService");
    }
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getExtras().getString(MainActivity.KEY);
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
        Log.i(TAG,"on START");

    }
}
