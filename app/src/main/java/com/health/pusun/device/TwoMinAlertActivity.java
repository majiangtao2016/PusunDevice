package com.health.pusun.device;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.health.pusun.device.vo.eventbusmsg.CloseTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

public class TwoMinAlertActivity extends Activity {

    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_min_alert);
        EventBus.getDefault().register(this);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                if(!TwoMinAlertActivity.this.isFinishing())
                TwoMinAlertActivity.this.finish();
            }
        }, 20000);
    }
    @Subcriber
    private void closeTwoMinEvent(CloseTwoMinAlertMsg showTwoMinAlertMsg) {

        if(showTwoMinAlertMsg.tipMsg.equals("two")){
            if(!TwoMinAlertActivity.this.isFinishing())
                TwoMinAlertActivity.this.finish();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
