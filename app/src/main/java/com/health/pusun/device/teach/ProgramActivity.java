package com.health.pusun.device.teach;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.health.pusun.device.AlertNoBallsActivity;
import com.health.pusun.device.FaultDealActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.PeopleAlertActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.VideoPlayActivity;
import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.CountDownTimer;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.BleDisconnectMsg;
import com.health.pusun.device.vo.eventbusmsg.PadControlMsg;
import com.health.pusun.device.vo.eventbusmsg.RemoteControllerMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.SetFastLMTMsg;
import com.health.pusun.device.vo.eventbusmsg.SetGroupLMTMsg;
import com.health.pusun.device.vo.eventbusmsg.SetVoltageMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowAlertNearMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowChuankouMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowNoBallsMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;
import com.health.pusun.device.vo.eventbusmsg.StopRecordMsg;
import com.health.pusun.device.vo.eventbusmsg.ZidingyiMsg;
import com.kyleduo.switchbutton.SwitchButton;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.ArrayList;
import java.util.List;

public class ProgramActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView num1, num2, num3, num4, num5, num6, num7, num8, num9,num10, num11, num12, num13, num14, num15, select_dianwei, tip_on;
    private Button clear;
    private TextView btn_ball;
    private RelativeLayout stop_ball, back_ball, stop_ball_left;
    private SwitchButton switchbtn;
    private List<Integer> selectPoints = new ArrayList<Integer>();
    private RelativeLayout bg_tip_start, bg_tip_stop;
    private int mode = 1;
    private int alertIn = 0;
    private int noBallsIn = 0;
    private int lineA = 0, lineB = 0, lineC = 0;
    private TextView hintVelo;
    private TextView re_rotate;
    private RelativeLayout rotate_layout;
    private TextView re_start, checkVideo;
    private RelativeLayout seize_layout;
    private ImageView next_group;
    private Chronometer chronometer;
    private int ballNum;
    private TimeCount1 timeCount1;
    private int currentNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        setContentView(R.layout.activity_program);
        EventBus.getDefault().register(this);
        ballNum = App.getIntUserPreference("ballnum") * 10;
        rotate_layout = findViewById(R.id.rotate_layout);
        re_rotate = findViewById(R.id.re_rotate);
        seize_layout = findViewById(R.id.seize_layout);
        re_start = findViewById(R.id.re_start);
        checkVideo = findViewById(R.id.checkVideo);
        next_group = findViewById(R.id.next_group);
        chronometer = findViewById(R.id.chronometer);
        num1 = findViewById(R.id.num1);num1.setOnClickListener(this);
        num2 = findViewById(R.id.num2);num2.setOnClickListener(this);
        num3 = findViewById(R.id.num3);num3.setOnClickListener(this);
        num4 = findViewById(R.id.num4);num4.setOnClickListener(this);
        num5 = findViewById(R.id.num5);num5.setOnClickListener(this);
        num6 = findViewById(R.id.num6);num6.setOnClickListener(this);
        num7 = findViewById(R.id.num7);num7.setOnClickListener(this);
        num8 = findViewById(R.id.num8);num8.setOnClickListener(this);
        num9 = findViewById(R.id.num9);num9.setOnClickListener(this);
        num10 = findViewById(R.id.num10);num10.setOnClickListener(this);
        num11 = findViewById(R.id.num11);num11.setOnClickListener(this);
        num12 = findViewById(R.id.num12);num12.setOnClickListener(this);
        num13 = findViewById(R.id.num13);num13.setOnClickListener(this);
        num14 = findViewById(R.id.num14);num14.setOnClickListener(this);
        num15 = findViewById(R.id.num15);num15.setOnClickListener(this);
        //设定振动电压
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                SetVoltageMsg setVoltageMsg = new SetVoltageMsg();
                EventBus.getDefault().post(setVoltageMsg);
            }
        }, 200);
        hintVelo = findViewById(R.id.hintVelo);
        bg_tip_start = findViewById(R.id.bg_tip_start);
        bg_tip_stop = findViewById(R.id.bg_tip_stop);
        tip_on = findViewById(R.id.tip_on);
        btn_ball = findViewById(R.id.btn_ball);btn_ball.setOnClickListener(this);
        clear = findViewById(R.id.clear);clear.setOnClickListener(this);
        stop_ball = findViewById(R.id.stop_ball);
        stop_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bg_tip_start.setVisibility(View.GONE);
                tip_on.setVisibility(View.GONE);
                currentNum = 0;
                bg_tip_stop.setVisibility(View.VISIBLE);
                if(timeCount1 != null) timeCount1.cancel();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        bg_tip_stop.setVisibility(View.GONE);
                    }
                }, 1500);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
            }
        });
        stop_ball_left = findViewById(R.id.stop_ball_left);
        stop_ball_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bg_tip_start.setVisibility(View.GONE);
                tip_on.setVisibility(View.GONE);
                bg_tip_stop.setVisibility(View.VISIBLE);
                currentNum = 0;
                if(timeCount1 != null) timeCount1.cancel();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        bg_tip_stop.setVisibility(View.GONE);
                    }
                }, 1500);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
            }
        });
        back_ball = findViewById(R.id.back_ball);
        back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeCount1 != null) timeCount1.cancel();
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                currentNum = 0;
                EventBus.getDefault().post(selectMsg);
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
                finish();
            }
        });
        select_dianwei = findViewById(R.id.select_dianwei);
        switchbtn = findViewById(R.id.switchbtn);
        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mode = 0;
                    ShowHelper.toastShort(ProgramActivity.this, "随机");
                }else{
                    mode = 1;
                    ShowHelper.toastShort(ProgramActivity.this, "顺序");
                }
            }
        });
        re_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeCount1 != null) timeCount1.cancel();
                rotate_layout.setVisibility(View.GONE);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                ProgramActivity.this.finish();
            }
        });
        re_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seize_layout.setVisibility(View.GONE);
            }
        });
        checkVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgramActivity.this, FaultDealActivity.class);
                intent.putExtra("state", 0);
                intent.putExtra("type", 0);
                startActivity(intent);
                seize_layout.setVisibility(View.GONE);
            }
        });
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometer.setFormat("%s");
                if(ballNum != 0){
                    if(ModeSelectActivity.trainNums >= ballNum){
                        ModeSelectActivity.trainNums = 0;
                        currentNum = 0;
                        //弹出下一组提示
                        next_group.setVisibility(View.VISIBLE);
                        SelectModeMsg selectMsg = new SelectModeMsg();
                        selectMsg.setType(100);
                        selectMsg.setPosition(1);
                        EventBus.getDefault().post(selectMsg);
                        if(timeCount1 != null) timeCount1.cancel();
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                next_group.setVisibility(View.GONE);
                            }
                        }, 3600);
                        //启动下一组发球
                        bg_tip_start.setVisibility(View.VISIBLE);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                if(bg_tip_start.getVisibility() == View.VISIBLE)
                                    tip_on.setVisibility(View.VISIBLE);
                                if(timeCount1 != null) timeCount1.cancel();
//                                timeCount1 = new TimeCount1( 6000 * 1000, 15000);
//                                timeCount1.start();
                            }
                        }, 6000);
                        final StringBuffer stringBuffer = new StringBuffer();
                        for(int i = 0; i < selectPoints.size(); i++){
                            if(i != selectPoints.size()-1)
                                stringBuffer.append(selectPoints.get(i) + ",");
                            else stringBuffer.append(selectPoints.get(i));
                        }
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                ZidingyiMsg zidingyiMsg = new ZidingyiMsg();
                                zidingyiMsg.mode = mode;
                                zidingyiMsg.listString = stringBuffer.toString();
                                EventBus.getDefault().post(zidingyiMsg);
                            }
                        }, 300);

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.num1:
                if(selectPoints.size() < 5) selectPoints.add(1); else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num2:
                if(selectPoints.size() < 5) selectPoints.add(2);else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num3:
                if(selectPoints.size() < 5) selectPoints.add(3);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num4:
                if(selectPoints.size() < 5) selectPoints.add(4);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num5:
                if(selectPoints.size() < 5) selectPoints.add(5);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num6:
                if(selectPoints.size() < 5) selectPoints.add(6);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num7:
                if(selectPoints.size() < 5) selectPoints.add(7);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num8:
                if(selectPoints.size() < 5) selectPoints.add(8);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num9:
                if(selectPoints.size() < 5) selectPoints.add(9);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num10:
                if(selectPoints.size() < 5) selectPoints.add(10);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num11:
                if(selectPoints.size() < 5) selectPoints.add(11); else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num12:
                if(selectPoints.size() < 5) selectPoints.add(12);else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num13:
                if(selectPoints.size() < 5) selectPoints.add(13);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num14:
                if(selectPoints.size() < 5) selectPoints.add(14);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.num15:
                if(selectPoints.size() < 5) selectPoints.add(15);
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持最多五个点位。");
                showPoints();
                break;
            case R.id.clear:
                if(selectPoints.size() >0) selectPoints.remove(selectPoints.get(selectPoints.size() - 1));
                else
                    ShowHelper.toastLong(ProgramActivity.this, "自定义支持二到五个点位。");
                showPoints();
                break;
            case R.id.btn_ball:
                if(selectPoints.size() < 2){
                    ShowHelper.toastLong(ProgramActivity.this,"请选择至少两个点位");
                }else{
                    if(timeCount1 != null) timeCount1.cancel();
                    if(hintVelo.getVisibility() == View.VISIBLE){
                        SetFastLMTMsg setFastLMTMsg = new SetFastLMTMsg();
                        EventBus.getDefault().post(setFastLMTMsg);
                    }else if(ModeSelectActivity.fastSelection<3){
                        SetGroupLMTMsg setGroupLMTMsg = new SetGroupLMTMsg();
                        EventBus.getDefault().post(setGroupLMTMsg);
                    }
                    bg_tip_start.setVisibility(View.VISIBLE);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {
                            if(bg_tip_start.getVisibility() == View.VISIBLE)
                                tip_on.setVisibility(View.VISIBLE);
                            if(timeCount1 != null) timeCount1.cancel();
//                            timeCount1 = new TimeCount1( 6000 * 1000, 15000);
//                            timeCount1.start();
                        }
                    }, 6000);
                    final StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 0; i < selectPoints.size(); i++){
                        if(i != selectPoints.size()-1)
                            stringBuffer.append(selectPoints.get(i) + ",");
                        else stringBuffer.append(selectPoints.get(i));
                    }
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {
                            ZidingyiMsg zidingyiMsg = new ZidingyiMsg();
                            zidingyiMsg.mode = mode;
                            zidingyiMsg.listString = stringBuffer.toString();
                            EventBus.getDefault().post(zidingyiMsg);
                        }
                    }, 300);

                }
                break;
            default:break;
        }
    }

    private void showPoints() {
        lineA = 0; lineB = 0; lineC = 0;
        if(selectPoints.size() != 0){
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < selectPoints.size(); i++){
                if(i != selectPoints.size()-1)
                    stringBuffer.append(selectPoints.get(i) + ",");
                else stringBuffer.append(selectPoints.get(i));

                if(selectPoints.get(i) == 1 || selectPoints.get(i) == 2 || selectPoints.get(i) == 3 || selectPoints.get(i) == 4 || selectPoints.get(i) == 5)
                    lineA = 1;
                if(selectPoints.get(i) == 6 || selectPoints.get(i) == 7 || selectPoints.get(i) == 8 || selectPoints.get(i) == 9 || selectPoints.get(i) == 10)
                    lineB = 2;
                if(selectPoints.get(i) == 11 || selectPoints.get(i) == 12 || selectPoints.get(i) == 13 || selectPoints.get(i) == 14 || selectPoints.get(i) == 15)
                    lineC = 3;
            }
            select_dianwei.setText(stringBuffer);
        }else
            select_dianwei.setText("");
        if(!((lineA == 1 && lineB == 0 && lineC == 0)||(lineA == 0 && lineB == 2 && lineC == 0)||(lineA == 0 && lineB == 0 && lineC == 3))){
            if(ModeSelectActivity.veloMode == 1 && ModeSelectActivity.fastSelection < 12) hintVelo.setVisibility(View.VISIBLE);
        }else hintVelo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        noBallsIn = 0;
        alertIn = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        noBallsIn = 1;
        alertIn = 1;
    }
    @Subcriber
    private void dealSelectEvent(StopEventMsg stopEventMsg) {

        if(stopEventMsg.stopMsg.equals("stop")) {
            rotate_layout.setVisibility(View.VISIBLE);
            bg_tip_start.setVisibility(View.GONE);
            tip_on.setVisibility(View.GONE);
            bg_tip_stop.setVisibility(View.GONE);
            if(null != timeCount1)timeCount1.cancel();

        }

    }
    @Subcriber
    private void dealRemoteControlEvent(RemoteControllerMsg remoteControllerMsg) {
//       ShowHelper.toastShort(VideoPlayActivity.this, "remoteControl:" + remoteControllerMsg.msgCode);
        if(remoteControllerMsg.tipMsg.equals("remoteControl")){
            if(remoteControllerMsg.msgCode == 0){
                //弹出暂停提示
                tip_on.setVisibility(View.GONE);
                bg_tip_start.setVisibility(View.GONE);
                bg_tip_stop.setVisibility(View.VISIBLE);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        bg_tip_stop.setVisibility(View.GONE);
                    }
                }, 1500);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
            }else{
                if(selectPoints.size() < 2){
                    ShowHelper.toastLong(ProgramActivity.this,"请选择至少两个点位");
                }else{
                    bg_tip_start.setVisibility(View.VISIBLE);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {
                            if(bg_tip_start.getVisibility() == View.VISIBLE)
                                tip_on.setVisibility(View.VISIBLE);
                        }
                    }, 6000);
                    StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 0; i < selectPoints.size(); i++){
                        if(i != selectPoints.size()-1)
                            stringBuffer.append(selectPoints.get(i) + ",");
                        else stringBuffer.append(selectPoints.get(i));
                    }
                    ZidingyiMsg zidingyiMsg = new ZidingyiMsg();
                    zidingyiMsg.mode = mode;
                    zidingyiMsg.listString = stringBuffer.toString();
                    EventBus.getDefault().post(zidingyiMsg);
                }
            }

        }
    }
    @Subcriber
    private void showChuankou(ShowChuankouMsg showChuankouMsg) {

        if(showChuankouMsg.tipMsg.equals("chuankou")){
            ShowHelper.toastShort(ProgramActivity.this, "msg1:" + showChuankouMsg.code1 + "msg2:" + showChuankouMsg.code2);
//            ShowHelper.toastShort(VideoPlayActivity.this, "BALL:" +showChuankouMsg.code2);
        }
    }
    @Subcriber
    private void dealBleMsgEvent(BleDisconnectMsg bleDisconnectMsg) {
        if(bleDisconnectMsg.msg == 1){
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    ProgramActivity.this.finish();
                }
            }, 500);
        }
    }
    @Subcriber
    private void padControlEvent(PadControlMsg padControlMsg) {
//        StopRecordMsg stopRecordMsg = new StopRecordMsg();
//        EventBus.getDefault().post(stopRecordMsg);
        SelectModeMsg selectMsg = new SelectModeMsg();
        selectMsg.setType(100);
        selectMsg.setPosition(1);
        EventBus.getDefault().post(selectMsg);
        SelectModeMsg selectMsg2 = new SelectModeMsg();
        selectMsg2.setType(100);
        selectMsg2.setPosition(1);
        EventBus.getDefault().post(selectMsg2);
        finish();
    }
    @Subcriber
    private void showNoBallsEvent(ShowNoBallsMsg showNoBallsMsg) {

        if(showNoBallsMsg.tipMsg.equals("no")){
            bg_tip_start.setVisibility(View.GONE);
            tip_on.setVisibility(View.GONE);
            ShowHelper.toastShort(ProgramActivity.this, "没球了");
            ShowHelper.toastShort(ProgramActivity.this, "指令" + showNoBallsMsg.msgCode);
            if(noBallsIn == 0) {
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                startActivity(new Intent(ProgramActivity.this, AlertNoBallsActivity.class));
            }
        }

    }


    @Subcriber
    private void showAlertEvent(ShowAlertNearMsg showAlertNearMsg) {

        if(showAlertNearMsg.tipMsg.equals("near")){
            bg_tip_start.setVisibility(View.GONE);
            tip_on.setVisibility(View.GONE);
            ShowHelper.toastShort(ProgramActivity.this, "有人靠近");
            SelectModeMsg selectMsg = new SelectModeMsg();
            selectMsg.setType(100);
            selectMsg.setPosition(1);
            EventBus.getDefault().post(selectMsg);
//            ShowHelper.toastShort(ProgramActivity.this, "指令" + showAlertNearMsg.msgCode);

            if(alertIn == 0)
                startActivity(new Intent(ProgramActivity.this, PeopleAlertActivity.class));
        }
    }

    @Subcriber
    private void showTwoMinEvent(ShowTwoMinAlertMsg showTwoMinAlertMsg) {

        if(showTwoMinAlertMsg.tipMsg.equals("two")){
            ProgramActivity.this.finish();
        }
    }

    /**
     * 验证码定时器
     */
    class TimeCount1 extends CountDownTimer {

        public TimeCount1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            if(null != timeCount1)timeCount1.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            if(tip_on.getVisibility()== View.VISIBLE){

                if(ModeSelectActivity.trainNums >= 3){
                    if(ModeSelectActivity.trainNums > currentNum) currentNum = ModeSelectActivity.trainNums;
                    else {
                        currentNum = 0;
                        bg_tip_start.setVisibility(View.GONE);
                        tip_on.setVisibility(View.GONE);
                        seize_layout.setVisibility(View.VISIBLE);
                        SelectModeMsg selectMsg = new SelectModeMsg();
                        selectMsg.setType(100);
                        selectMsg.setPosition(1);
                        EventBus.getDefault().post(selectMsg);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                SelectModeMsg selectMsg = new SelectModeMsg();
                                selectMsg.setType(ModeSelectActivity.KAQIU);
                                selectMsg.setPosition(1);
                                EventBus.getDefault().post(selectMsg);
                            }
                        }, 200);
                        if(null != timeCount1)timeCount1.cancel();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chronometer.stop();
        if(timeCount1 != null) timeCount1.cancel();
        EventBus.getDefault().unregister(this);
    }
}