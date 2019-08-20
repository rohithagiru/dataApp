package com.example.dataapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static android.provider.Contacts.SettingsColumns.KEY;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static int COOLIE_ID = 007;
    public static  String KEY = "my_key";
    Bundle specialInstructions;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(COOLIE_ID,specialInstructions,this);
//        ContentResolver contentResolver = getContentResolver();
//        Uri allCalls = Uri.parse("content://call_log/calls");  //TABLE_NAME
//        Uri contacts = ContactsContract.Contacts.CONTENT_URI;
//        Cursor dataCursor = contentResolver.query(contacts,
//                null,null,null,null);
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                // R.layout.my_list_item,
                null,
                new String[]{"body"},
                //new int[]{R.id.textViewTitle,R.id.textViewSubtitle},
                new int[]{android.R.id.text1},
                0 );

        ListView dataListView =  findViewById(R.id.cpListview);
        dataListView.setAdapter(adapter);


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle instructions) {
        if(id == COOLIE_ID){
            Uri uriSms = Uri.parse("content://sms/inbox");  //TABLE_NAME

            return new CursorLoader(this,uriSms,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor dataCursor) {
        adapter.swapCursor(dataCursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public void handleClick(View view) {
//        Intent serviceIntent = new Intent(this,MyIntentService.class);
//        serviceIntent.putExtra(KEY,"url to download data");
//        startService(serviceIntent);
//        stopService(serviceIntent);
        switch (view.getId()){
            case R.id.buttonMusic:
                Intent bindIntent = new Intent(this, BoundService.class);
                bindService(bindIntent, serviceConnection, Service.BIND_AUTO_CREATE);  //1
                //BIND_AUTO_CREATE -- this flag will create the service if its not already created

                break;
            case R.id.ButtonUnbind:
                unbindService(serviceConnection);
                break;
        }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder mBinder) {//4
            BoundService.LocalBinder binder = (BoundService.LocalBinder) mBinder;
            BoundService service =  binder.getService();//5  i am getting the running instance of the service instead of creating a new one
            int cricketScore = service.getScore();//7
            Toast.makeText(service, "latest score = "+cricketScore, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "activity got disconnected", Toast.LENGTH_SHORT).show();

        }
    };
}

