package com.health.pusun.device;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.DateUtil;
import com.health.pusun.device.vo.eventbusmsg.EggGameScoreMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;

import org.simple.eventbus.EventBus;

import java.util.Date;

public class EggPlayActivity extends AppCompatActivity {

    private Button egg1;
    private int aimNum;
    private TextView num, countdown;
    private TimeCount timeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_play);
        EventBus.getDefault().register(this);
        num = findViewById(R.id.num);
        egg1= findViewById(R.id.egg1);
        countdown = findViewById(R.id.countdown);
        egg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aimNum++;
                num.setText("" + aimNum);
            }
        });
//        egg1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                aimNum++;
//                num.setText("" + aimNum);
//                return false;
//            }
//        });

//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                SelectModeMsg selectMsg = new SelectModeMsg();
//                selectMsg.setType(100);
//                selectMsg.setPosition(1);
//                EventBus.getDefault().post(selectMsg);
//                StopEventMsg stopEventMsg = new StopEventMsg();
//                EventBus.getDefault().post(stopEventMsg);
//                finish();
//            }
//        }, 60000);
        timeCount = new TimeCount(60 * 1000, 1000);
        timeCount.start();
    }

    /**
     * 定时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕

            EggGameScoreMsg eggGameScoreMsg = new EggGameScoreMsg();
            eggGameScoreMsg.score = aimNum;
            EventBus.getDefault().post(eggGameScoreMsg);

          (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                finish();
                timeCount.cancel();
            }
        }, 300);

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            countdown.setText("倒计时：" + millisUntilFinished/1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
