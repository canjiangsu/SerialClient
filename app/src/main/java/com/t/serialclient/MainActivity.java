package com.t.serialclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.t.serialserver.ISerialService;
import com.t.serialserver.ISerialCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SerialClient";
    private ISerialService mSerialService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_connect = findViewById(R.id.button_connect);
        button_connect.setOnClickListener(mButtonClickListener);

        final Button button_getpid = findViewById(R.id.button_getpid);
        button_getpid.setOnClickListener(mButtonClickListener);

    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_connect:
                    Log.d(TAG, "start Connect SerialService");
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    ComponentName component = new ComponentName("com.t.serialserver", "com.t.serialserver.SerialService");
                    intent.setComponent(component);
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    break;
                case R.id.button_getpid:
                    try {
                        int pid = -1;
                        if (mSerialService != null) {
                            pid = mSerialService.getPid();
                            mSerialService.openSerial(mCallback);
                        }
                        Log.d(TAG, "getPid: " + pid);
                    } catch (RemoteException e) {
                        Log.d(TAG, "getPid failed: " + e);
                    }
                    break;
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            mSerialService = ISerialService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            mSerialService = null;
        }
    };

    private ISerialCallback mCallback = new ISerialCallback.Stub() {
        public void RespDataFromSerial(int value) {
            Log.d(TAG, "RespDataFromSerial : " + value);
        }
    };
}