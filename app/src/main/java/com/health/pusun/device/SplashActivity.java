package com.health.pusun.device;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jorgecastillo.FillableLoader;
import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.vo.ActivityInfoVo;
import com.health.pusun.device.vo.RequestCallVo;
import com.jaeger.library.StatusBarUtil;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class SplashActivity extends SerialPortActivity {

    //private WebView webview;
    private Intent intent;
    private long startSplashTime;
    private long endSplashTime;
    private static final long SPLASH_TIME = 1 * 200;
    private ImageView icon_img;

    private FrameLayout splash_center;
    private RelativeLayout mRlReveal;
    private ImageView mTxtTitle, splash_logo;
    private FillableLoader mPathLogo;

    private boolean hasAnimationStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.colorPrimary), 0);
        EventBus.getDefault().register(this);

        startSplashTime = System.currentTimeMillis();
        initUI();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {

    }


    public void initUI() {
//        splash_center = (FrameLayout) findViewById(R.id.splash_center);
//        mRlReveal = (RelativeLayout) findViewById(R.id.rlColor);
//        mTxtTitle = (ImageView) findViewById(R.id.txtTitle);
//        splash_logo = (ImageView) findViewById(R.id.splash_logo);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                doSplash();
            }
        }, 1000);
    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && !hasAnimationStarted) {
//            startCircularReveal();
//        }
//    }

    private void doSplash() {

        intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void animationsFinished() {
        startSplashTime = System.currentTimeMillis();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                doSplash();
            }
        }, 2500);

    }
}
