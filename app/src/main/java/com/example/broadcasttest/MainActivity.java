package com.example.broadcasttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter; //Intent 过滤器
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager =LocalBroadcastManager.getInstance(this); //获取实例
        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                intent.putExtra("XLZH", "This is a localBroadcast!");
                localBroadcastManager.sendBroadcast(intent); //发送本地广播
            }
        });
        intentFilter =new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver =new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter); //注册本地广播检测器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
/*Android
    广播分类
    android的广播类型分为两类：标准广播和有序广播。

    标准广播：异步广播，广播发出后，所有注册了的广播接收器都会同时接收到该广播。
    打个比方：做地铁过程中的语音播报，当列车员(广播发出者)进行语音播报(发送广播)时，
    所有乘客(注册接收该广播的程序)都可以同时听到语音，不分先后顺序。
    在android系统中存在此类的广播有启动完成、电量变化等；

    有序广播：同步发送，广播发出后，按照注册了的广播接收器的优先级顺序广播，
    优先级的范围是-1000~1000,数字越大，优先级越高，最先接收到该广播，接收器的优先级通过android:priority设置。
    打个比方：在接力赛中，接力棒就是广播的内容，需要运动员依次传递，优先级高的运动员位置靠前，优先级的运动员位置靠前，
    运动员接力的过程就是广播的过程。当然，要是有个运动员觉得：哎，这个接力棒当门栓不错，兜里一装回家了，
    后面的运动员自然不能接收到这个广播了。android系统中的短信就是以这种形式进行广播，我们手机上安装的XX卫士，
    XX安全大师的短信拦截功能就是把自身接收短信的优先级高于系统的短信应用，达到拦截短信的目的。

*/