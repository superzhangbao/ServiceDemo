package com.hengsheng.lifecycledemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private Button mButton,mMbtnStarService,mStopService,mBindService,mUseService;

    /**
     * ServiceConnection代表与服务的连接，它只有两个方法，
     * onServiceConnected和onServiceDisconnected，
     * 前者是在操作者在连接一个服务成功时被调用，而后者是在服务崩溃或被杀死导致的连接中断时被调用
     */
    private ServiceConnection conn;
    private LocalService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");
        mButton = findViewById(R.id.btn_mainactivity);
        mMbtnStarService = findViewById(R.id.btn_startservice);
        mStopService = findViewById(R.id.btn_stopservice);
        mBindService = findViewById(R.id.btn_bindservice);
        mUseService = findViewById(R.id.btn_useservice);
        mButton.setOnClickListener(this);
        mMbtnStarService.setOnClickListener(this);
        mStopService.setOnClickListener(this);
        mBindService.setOnClickListener(this);
        mUseService.setOnClickListener(this);

        conn = new ServiceConnection() {
            /**
             * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互。
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 获取Binder
                LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
                mService = binder.getService();
            }

            /**
             * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG,"onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG,"onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mainactivity:
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                break;
            case R.id.btn_startservice:
                startService(new Intent(MainActivity.this,SimpleService.class));
                break;
            case R.id.btn_stopservice:
                stopService(new Intent(MainActivity.this,SimpleService.class));
                break;
            case R.id.btn_bindservice:
                bindService(new Intent(MainActivity.this,LocalService.class),conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_useservice:
                int count = mService.getCount();
                Log.e(TAG,"count:---->"+count);
                break;
            case R.id.btn_unbindservice:
                // 解除绑定
                if(mService!=null) {
                    mService = null;
                    unbindService(conn);
                }
                break;
        }
    }
}
