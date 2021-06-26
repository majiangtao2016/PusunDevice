package com.health.pusun.device;

import android.app.smdt.SmdtManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.application.App;
import com.health.pusun.device.train.NextGroupActivity;
import com.health.pusun.device.utils.CountDownTimer;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.PadControlMsg;
import com.health.pusun.device.vo.eventbusmsg.RemoteControllerMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowAlertNearMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowChuankouMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowFaultMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowNoBallsMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTipEventMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.StartRecordMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;
import com.health.pusun.device.vo.eventbusmsg.StopRecordMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.File;

public class VideoPlayActivity extends AppCompatActivity {

//    private VideoView ijkVideoView;
    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/";
//    + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";
    private int alertIn = 0;
    private RelativeLayout start_ball, back_ball, start_ball_left, record_ball;
    private ImageView icon_play, icon_play_left;
    private TextView start_ball_tv, start_ball_tv_left;
    private int state, type;
    private TextView tip;
    private int noBallsIn = 0;
//    private SmdtManager smdt;
    private TextView color_egg;
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 500L;
    private int isPause = 0;
    private TextView record_ball_tv;
    private TimeCount1 timeCount1;
    private TimeCount2 timeCount2;
    private int currentNum = 0;
    private TextView camera_time, stuck_time;
    private Handler hd1;
    private int currentState = 1;
    private RelativeLayout mute;
    private int volumeInt = 0;
    private AudioManager audioManager;
    private int currentVolume;
    private RelativeLayout bg_tip;
    private TextView serve_stop,serve_on;
    private TextView tip_stop;
    private TextView re_start, checkVideo;
    private RelativeLayout seize_layout;
    private int ballNum;
    private Chronometer chronometer;
    private TextView quantity;
    private TextView item_name, ball_nums;
    private RelativeLayout rotate_layout;
    private TextView re_rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(0x80000000, 0x80000000);//关键代码
        setContentView(R.layout.activity_video_play);
        EventBus.getDefault().register(this);
        state = getIntent().getIntExtra("state", 0);
        type = getIntent().getIntExtra("type", 0);
        ballNum = App.getIntUserPreference("ballnum") * 10;
        quantity = findViewById(R.id.quantity);
//        ijkVideoView = findViewById(R.id.player);
        icon_play = findViewById(R.id.icon_play);
        serve_stop = findViewById(R.id.serve_stop);
        serve_on = findViewById(R.id.serve_on);
        camera_time = findViewById(R.id.camera_time);
        chronometer = findViewById(R.id.chronometer);
        stuck_time = findViewById(R.id.stuck_time);
        item_name = findViewById(R.id.item_name);
        ball_nums = findViewById(R.id.ball_nums);
        item_name.setText("" + ModeSelectActivity.selectName);
        icon_play_left = findViewById(R.id.icon_play_left);
        start_ball_tv = findViewById(R.id.start_ball_tv);
        record_ball_tv = findViewById(R.id.record_ball_tv);
        record_ball = findViewById(R.id.record_ball);
        seize_layout = findViewById(R.id.seize_layout);
        re_start = findViewById(R.id.re_start);
        checkVideo = findViewById(R.id.checkVideo);
        rotate_layout= findViewById(R.id.rotate_layout);
        re_rotate = findViewById(R.id.re_rotate);
        mute = findViewById(R.id.mute);
//        smdt = SmdtManager.create(this);
        bg_tip = findViewById(R.id.bg_tip);
        tip_stop = findViewById(R.id.tip_stop);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        ShowHelper.toastShort(VideoPlayActivity.this, "volume:" + currentVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
//        smdt.smdtSetControl(6,0);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(volumeInt == 1){
                    currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
                    mute.setBackground(getResources().getDrawable(R.drawable.ic_no_volume));
                    volumeInt = 0;
                }else{
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_SHOW_UI);
                    mute.setBackground(getResources().getDrawable(R.drawable.ic_volume));
                    volumeInt = 1;
                }
            }
        });
        start_ball =  findViewById(R.id.start_ball);
        start_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL ){
                    if (nowTime - mLastClickTime > 2000L && !start_ball_tv.getText().toString().equals("暂停练球")){
                        Intent intent = new Intent(VideoPlayActivity.this, CountDownActivity.class);
                        intent.putExtra("state", state);
                        intent.putExtra("type", type);
                        startActivity(intent);
                        finish();
                    }
                    mLastClickTime = nowTime;
                    if(start_ball_tv.getText().toString().equals("暂停练球")){
                        //弹出暂停提示
                        bg_tip.setVisibility(View.VISIBLE);
                        serve_stop.setVisibility(View.VISIBLE);
                        serve_on.setVisibility(View.INVISIBLE);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                bg_tip.setVisibility(View.GONE);
                            }
                        }, 1500);
//                        ShowHelper.toastShort(VideoPlayActivity.this, "再按一次暂停。");
//                        (new Handler()).postDelayed(new Runnable() {
//                            @Override
//                            public synchronized void run() {
//                                isPause = 0;
//                            }
//                        }, 3000);
//                        if(isPause == 1){
                            if(null != timeCount1)timeCount1.cancel();
                            SelectModeMsg selectMsg = new SelectModeMsg();
                            selectMsg.setType(100);
                            selectMsg.setPosition(1);
                            EventBus.getDefault().post(selectMsg);
                        SelectModeMsg selectMsg2 = new SelectModeMsg();
                        selectMsg2.setType(100);
                        selectMsg2.setPosition(1);
                        EventBus.getDefault().post(selectMsg2);

                            currentState = 0;
                            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
                            start_ball_tv.setText("继续练球");
                            icon_play_left.setBackground(getResources().getDrawable(R.drawable.continue_play));
                            start_ball_tv_left.setText("继续练球");
//                        }
//                        isPause = 1;
                    }else{
                    }
                }
            }
        });
        start_ball_tv_left = findViewById(R.id.start_ball_tv_left);
        start_ball_left =  findViewById(R.id.start_ball_left);
        start_ball_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL){
                    if (nowTime - mLastClickTime > 2000L && !start_ball_tv.getText().toString().equals("暂停练球")){
                        Intent intent = new Intent(VideoPlayActivity.this, CountDownActivity.class);
                        intent.putExtra("state", state);
                        intent.putExtra("type", type);
                        startActivity(intent);
                        finish();
                    }
                    mLastClickTime = nowTime;
                    if(start_ball_tv_left.getText().toString().equals("暂停练球")){

                        //弹出暂停提示
                        bg_tip.setVisibility(View.VISIBLE);
                        serve_stop.setVisibility(View.VISIBLE);
                        serve_on.setVisibility(View.INVISIBLE);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                bg_tip.setVisibility(View.GONE);
                            }
                        }, 1500);
                            if(null != timeCount1)timeCount1.cancel();
                            SelectModeMsg selectMsg = new SelectModeMsg();
                            selectMsg.setType(100);
                            selectMsg.setPosition(1);
                            EventBus.getDefault().post(selectMsg);
                        SelectModeMsg selectMsg2 = new SelectModeMsg();
                        selectMsg2.setType(100);
                        selectMsg2.setPosition(1);
                        EventBus.getDefault().post(selectMsg2);
                            currentState = 0;
                            icon_play_left.setBackground(getResources().getDrawable(R.drawable.continue_play));
                            start_ball_tv_left.setText("继续练球");
                            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
                            start_ball_tv.setText("继续练球");

                    }else{

                    }
                }
            }
        });
        tip = findViewById(R.id.tip);
        back_ball = findViewById(R.id.back_ball);
        back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL){
                    mLastClickTime = nowTime;
                        if(null != timeCount1)timeCount1.cancel();
//                    if(record_ball_tv.getText().toString().equals("停止录像")){
                        StopRecordMsg stopRecordMsg = new StopRecordMsg();
                        EventBus.getDefault().post(stopRecordMsg);
//                    }
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
            }
        });
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometer.setFormat("00:%s");
//                quantity.setText("本轮已发球数量：" + ModeSelectActivity.trainNums);
                if(ballNum != 0){
                    if(ModeSelectActivity.trainNums >= ballNum){
//                        if(record_ball_tv.getText().toString().equals("停止录像")){
                            StopRecordMsg stopRecordMsg = new StopRecordMsg();
                            EventBus.getDefault().post(stopRecordMsg);
//                        }
                        chronometer.stop();
                        //循环发球
                        Intent intent = new Intent(VideoPlayActivity.this, NextGroupActivity.class);
                        intent.putExtra("state", state);
                        intent.putExtra("type", type);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
            record_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL) {
                    mLastClickTime = nowTime;
                    if(record_ball_tv.getText().toString().equals("点击录像")){

//                        if (!"null".equals(smdt.smdtGetUSBPath(VideoPlayActivity.this, 0))) {
//                            record_ball_tv.setText("录像启动中");
//                            StartRecordMsg startRecordMsg = new StartRecordMsg();
//                            startRecordMsg.code = 1;
//                            EventBus.getDefault().post(startRecordMsg);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public synchronized void run() {
//                                    record_ball_tv.setText("停止录像");
//                                }
//                            }, 10000);
//                        }else{
//                            ShowHelper.toastShort(VideoPlayActivity.this, "请先插入U盘后再录像。");
//                        }

                    }else if(record_ball_tv.getText().toString().equals("停止录像")){

                        StopRecordMsg stopRecordMsg = new StopRecordMsg();
                        EventBus.getDefault().post(stopRecordMsg);
                        ShowHelper.showProgressDialog(VideoPlayActivity.this, "设备录像保存中...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public synchronized void run() {
                                record_ball_tv.setText("点击录像");
                                ShowHelper.dismissProgressDialog();
                            }
                        }, 3000);

                    }else{
                        ShowHelper.toastShort(VideoPlayActivity.this, "设备录像启动中,请稍等");
                    }
                }

            }
        });

        MainActivity.isRecording = 0;
        // 判断SDCard是否存在再录像
//        if (!"null".equals(smdt.smdtGetUSBPath(VideoPlayActivity.this, 0))) {
//            StartRecordMsg startRecordMsg = new StartRecordMsg();
//            startRecordMsg.code = 1;
//            EventBus.getDefault().post(startRecordMsg);
//        }

//        if(type == 3001) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1070 + ".mp4")).toString(),null);
//        else ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + type + ".mp4")).toString(),null); //设置视频地址
//        if(type == 1101) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1003 + ".mp4")).toString(),null);
//            if(type == 1102) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1001 + ".mp4")).toString(),null);
//                if(type == 1103) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1028 + ".mp4")).toString(),null);
//                    if(type == 1104)ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1020 + ".mp4")).toString(),null);
//                        if(type == 1105)ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1052 + ".mp4")).toString(),null);
//        ijkVideoView.start();
//        ijkVideoView.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
////            private int mCurrentVideoPosition;
//            @Override
//            public void onPlayerStateChanged(int playerState) {
//
//            }
//
//            @Override
//            public void onPlayStateChanged(int playState) {
//                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
//
//                    ijkVideoView.release();
//
//                    //重新设置数据
//                    if(type == 3001) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1070 + ".mp4")).toString(),null);
//                    else ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + type + ".mp4")).toString(),null); //设置视频地址
//                    if(type == 1101) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1003 + ".mp4")).toString(),null);
//                    if(type == 1102) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1001 + ".mp4")).toString(),null);
//                    if(type == 1103) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1028 + ".mp4")).toString(),null);
//                    if(type == 1104)ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1020 + ".mp4")).toString(),null);
//                    if(type == 1105)ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1052 + ".mp4")).toString(),null);
//                    ijkVideoView.start();
//
//                }
//            }
//        });
        color_egg = findViewById(R.id.color_egg);
        color_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
            if(type == ModeSelectActivity.SHAQIU_HOUCHANG){

            }

//        }


        if(timeCount1 != null) timeCount1.cancel();
        timeCount1 = new TimeCount1( 6000 * 1000, 15000);
        hd1 = new Handler();
        hd1.postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                timeCount1.start();
//                ijkVideoView.release();
//                ijkVideoView.setVisibility(View.GONE);
            }
        }, 40000);
      if(ModeSelectActivity.isRecord == 1){
//          camera_time.setVisibility(View.VISIBLE);
//          hd2 = new Handler();
//          hd2.postDelayed(new Runnable() {
//              @Override
//              public synchronized void run() {
//                  camera_time.setText("视频录制完成，保存中...");
//                  ModeSelectActivity.isRecord = 0;
//              }
//          }, 60000);
//          hd3 = new Handler();
//          hd3.postDelayed(new Runnable() {
//              @Override
//              public synchronized void run() {
//                  record_ball_tv.setText("点击录像");
//                camera_time.setVisibility(View.GONE);
//              }
//          }, 360000);
      }
//        ijkVideoView.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
//            @Override
//            public void onPlayerStateChanged(int playerState) {
//
//            }
//
//            @Override
//            public void onPlayStateChanged(int playState) {
//                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
//
//                    ijkVideoView.release();
//
//                    //重新设置数据
//                    ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + type + ".mp4")).toString(),null); //设置视频地址
//                    ijkVideoView.start();
//
//                }
//            }
//        });

        checkVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoPlayActivity.this, FaultDealActivity.class);
                    intent.putExtra("state", state);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    finish();
            }
        });

        re_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoPlayActivity.this, CountDownActivity.class);
                intent.putExtra("state", state);
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
            }
        });
        re_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate_layout.setVisibility(View.GONE);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
                VideoPlayActivity.this.finish();
            }
        });
    }

    @Subcriber
    private void dealSelectEvent(StopEventMsg stopEventMsg) {

        if(stopEventMsg.stopMsg.equals("stop")){

            rotate_layout.setVisibility(View.VISIBLE);
            if(null != timeCount1)timeCount1.cancel();
            if(null != timeCount2)timeCount2.cancel();
//            if(!start_ball_tv.getText().toString().equals("继续练球")){
//                if(ModeSelectActivity.trainNums != 0 && (ModeSelectActivity.trainNums <= currentNum)){
//                    stuck_time.setVisibility(View.GONE);
//                    if(timeCount1 != null)timeCount1.cancel();
//                    SelectModeMsg selectMsg = new SelectModeMsg();
//                    selectMsg.setType(100);
//                    selectMsg.setPosition(1);
//                    EventBus.getDefault().post(selectMsg);
//                    seize_layout.setVisibility(View.VISIBLE);
//                    (new Handler()).postDelayed(new Runnable() {
//                        @Override
//                        public synchronized void run() {
//                            SelectModeMsg selectMsg = new SelectModeMsg();
//                            selectMsg.setType(ModeSelectActivity.KAQIU);
//                            selectMsg.setPosition(1);
//                            EventBus.getDefault().post(selectMsg);
//                        }
//                    }, 200);
//                    if(null != timeCount1){
//                        timeCount1.cancel();
//                        timeCount1 = null;
//                    }
//                    if(null != timeCount2){
//                        timeCount2.cancel();
//                        timeCount2 = null;
//                    }
//
//                }else{
//                    stuck_time.setVisibility(View.GONE);
//                    if(timeCount2 != null)timeCount2.cancel();
//                }
//            } else{
//                stuck_time.setVisibility(View.GONE);
//                if(timeCount2 != null)timeCount2.cancel();
//            }
        }

    }

    @Subcriber
    private void dealStartRecordEvent(StartRecordMsg startRecordMsg) {
        if (startRecordMsg.code == 0) {
            ModeSelectActivity.isRecord = 0;

        }
    }
    @Subcriber
    private void padControlEvent(PadControlMsg padControlMsg) {
        if(null != timeCount1)timeCount1.cancel();
        StopRecordMsg stopRecordMsg = new StopRecordMsg();
        EventBus.getDefault().post(stopRecordMsg);
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
    private void showTipEvent(ShowTipEventMsg showTipEventMsg) {

        if(showTipEventMsg.tipMsg.equals("tip")){
            tip.setVisibility(View.VISIBLE);
            if(null!=timeCount1)
                timeCount1.cancel();
        }

    }

    @Subcriber
    private void dealFaultDataEvent(ShowFaultMsg showFaultMsg) {
        if(null!=timeCount1)
            timeCount1.cancel();
        if(showFaultMsg.code != 0)
            finish();
    }

    @Subcriber
    private void showTwoMinEvent(ShowTwoMinAlertMsg showTwoMinAlertMsg) {

        if(showTwoMinAlertMsg.tipMsg.equals("two")){
            if(null!=timeCount1)
                timeCount1.cancel();
            VideoPlayActivity.this.finish();
        }
    }

    @Subcriber
    private void showChuankou(ShowChuankouMsg showChuankouMsg) {

        if(showChuankouMsg.tipMsg.equals("chuankou")){
            ShowHelper.toastShort(VideoPlayActivity.this, "msg1:" + showChuankouMsg.code1 + "msg2:" + showChuankouMsg.code2);
//            ShowHelper.toastShort(VideoPlayActivity.this, "BALL:" +showChuankouMsg.code2);
        }
    }

    @Subcriber
    private void showAlertEvent(ShowAlertNearMsg showAlertNearMsg) {

        if(showAlertNearMsg.tipMsg.equals("near")){
            ShowHelper.toastShort(VideoPlayActivity.this, "有人靠近");
            if(null!=timeCount1)
                timeCount1.cancel();
            SelectModeMsg selectMsg = new SelectModeMsg();
            selectMsg.setType(100);
            selectMsg.setPosition(1);
            EventBus.getDefault().post(selectMsg);
            serve_stop.setVisibility(View.VISIBLE);
            serve_on.setVisibility(View.INVISIBLE);
            ShowHelper.toastShort(VideoPlayActivity.this, "指令" + showAlertNearMsg.msgCode);
            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
            currentState = 0;
            start_ball_tv.setText("继续练球");
            icon_play_left.setBackground(getResources().getDrawable(R.drawable.continue_play));
            start_ball_tv_left.setText("继续练球");
            if(alertIn == 0)
            startActivity(new Intent(VideoPlayActivity.this, PeopleAlertActivity.class));
        }
    }

    @Subcriber
    private void dealRemoteControlEvent(RemoteControllerMsg remoteControllerMsg) {
//       ShowHelper.toastShort(VideoPlayActivity.this, "remoteControl:" + remoteControllerMsg.msgCode);
        if(remoteControllerMsg.tipMsg.equals("remoteControl")){
            if(remoteControllerMsg.msgCode == 0){
//                if(currentState == 1){     //单按钮时使用
                    currentState = 0;
                    if(null != timeCount1)timeCount1.cancel();
                //弹出暂停提示
                bg_tip.setVisibility(View.VISIBLE);
                serve_stop.setVisibility(View.VISIBLE);
                serve_on.setVisibility(View.INVISIBLE);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        bg_tip.setVisibility(View.GONE);
                    }
                }, 1500);
                    SelectModeMsg selectMsg1 = new SelectModeMsg();
                    selectMsg1.setType(100);
                    selectMsg1.setPosition(1);
                    EventBus.getDefault().post(selectMsg1);
                    icon_play_left.setBackground(getResources().getDrawable(R.drawable.continue_play));
                    start_ball_tv_left.setText("继续练球");
                    icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
                    start_ball_tv.setText("继续练球");
                }else{
                    currentState = 1;
                    if(null != timeCount1)timeCount1.cancel();
                    Intent intent = new Intent(VideoPlayActivity.this, CountDownActivity.class);
                    intent.putExtra("state", state);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    finish();
                }

        }
    }

    @Subcriber
    private void showNoBallsEvent(ShowNoBallsMsg showNoBallsMsg) {

        if(showNoBallsMsg.tipMsg.equals("no")){

            ShowHelper.toastShort(VideoPlayActivity.this, "没球了");
            if(null!=timeCount1)
                timeCount1.cancel();
            currentState = 0;
            ShowHelper.toastShort(VideoPlayActivity.this, "指令" + showNoBallsMsg.msgCode);
            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
            serve_stop.setVisibility(View.VISIBLE);
            serve_on.setVisibility(View.INVISIBLE);
            start_ball_tv.setText("继续练球");
            icon_play_left.setBackground(getResources().getDrawable(R.drawable.continue_play));
            start_ball_tv_left.setText("继续练球");
            if(noBallsIn == 0) {
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
                startActivity(new Intent(VideoPlayActivity.this, AlertNoBallsActivity.class));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        ijkVideoView.resume();
        alertIn = 0;
        noBallsIn = 0;
    }
    @Override
    protected void onPause() {
        if(null!=timeCount1)
            timeCount1.cancel();
        if(null!=timeCount2)
            timeCount2.cancel();
        super.onPause();
        chronometer.stop();
//        ijkVideoView.pause();
        alertIn = 1;
        noBallsIn = 1;
    }

    @Override
    protected void onDestroy() {
        if(MainActivity.isRecording == 1){
            ModeSelectActivity.isRecord = 0;
        }

        if(hd1 != null) hd1.removeCallbacksAndMessages(null);
        if(null!=timeCount1){
            timeCount1.cancel();
            timeCount1 = null;
        }
        if(null!=timeCount2){
            timeCount2.cancel();
            timeCount2 = null;
        }
        chronometer.stop();
        EventBus.getDefault().unregister(this);
//        ijkVideoView.release();
        super.onDestroy();

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
            timeCount1.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            if(!start_ball_tv.getText().toString().equals("继续练球")){


                if(ModeSelectActivity.trainNums >= 3){
                    if(ModeSelectActivity.trainNums > currentNum) currentNum = ModeSelectActivity.trainNums;
                    else {
                        stuck_time.setVisibility(View.VISIBLE);
                        if(timeCount2 != null) timeCount2.cancel();
                        timeCount2 = new TimeCount2( 3 * 1000, 1000);
                        timeCount2.start();

//                        timeCount1.cancel();
                    }
                }
            }
        }
    }

    /**
     * 验证码定时器
     */
    class TimeCount2 extends CountDownTimer {

        public TimeCount2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            if(!start_ball_tv.getText().toString().equals("继续练球")){
                if(ModeSelectActivity.trainNums != 0 && (ModeSelectActivity.trainNums <= currentNum)){
                    stuck_time.setVisibility(View.GONE);
                    if(timeCount1 != null)timeCount1.cancel();
                    SelectModeMsg selectMsg = new SelectModeMsg();
                    selectMsg.setType(100);
                    selectMsg.setPosition(1);
                    EventBus.getDefault().post(selectMsg);
                    seize_layout.setVisibility(View.VISIBLE);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {
                            SelectModeMsg selectMsg = new SelectModeMsg();
                            selectMsg.setType(ModeSelectActivity.KAQIU);
                            selectMsg.setPosition(1);
                            EventBus.getDefault().post(selectMsg);
                        }
                    }, 200);
                    if(null != timeCount1){
                        timeCount1.cancel();
                        timeCount1 = null;
                    }
                    if(null != timeCount2){
                        timeCount2.cancel();
                        timeCount2 = null;
                    }

                }else{
                    stuck_time.setVisibility(View.GONE);
                    if(timeCount2 != null)timeCount2.cancel();
                }
            } else{
                stuck_time.setVisibility(View.GONE);
                if(timeCount2 != null)timeCount2.cancel();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            stuck_time.setText("卡球检测中（" + millisUntilFinished/1000 + "S）");

        }

    }

}
