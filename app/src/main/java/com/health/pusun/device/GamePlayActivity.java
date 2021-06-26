package com.health.pusun.device;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.health.pusun.device.application.App;
import com.health.pusun.device.test.FailNoteActivity;
import com.health.pusun.device.test.WinNoteActivity;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.views.ListIjkVideoView;
import com.health.pusun.device.vo.RequestCallVo;
import com.health.pusun.device.vo.ScoreGameInfo;
import com.health.pusun.device.vo.eventbusmsg.EggGameScoreMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowAlertNearMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowFaultMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowNoBallsMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTipEventMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GamePlayActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/";
    //    + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";
    private RelativeLayout start_ball, back_ball;
    private RelativeLayout game_whole;
    private ImageView icon_play;
    private TextView start_ball_tv;
    private int state, type;
    private TextView tip;
    private int alertIn = 0;
    private int noBallsIn = 0;
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;
    private ImageView bat;
    private Button tv1, tv2, tv3, tv4, tv5;
    private RelativeLayout bat_layout;
    private int aimNum, sendNum;
    private TextView num, countdown;
    private TimeCount timeCount;
    private TimeCount1 timeCount1;
    private TextView goal;
    private ZzHorizontalProgressBar pb1, pb2;
    private TextView level_title;
    private TextView count;
    private TextView number;
    private ImageView gif1, gif2,gif3,gif4,gif5,gif6,gif7,gif8,gif9;
    private MediaPlayer music = null;// 播放器引用
    private int isQuit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(ModeSelectActivity.gameLevel == 2 || ModeSelectActivity.gameLevel == 4) setContentView(R.layout.activity_game_layout2);
//        else
            setContentView(R.layout.activity_game_play);
        EventBus.getDefault().register(this);
        bat_layout = findViewById(R.id.bat_layout);
        num = findViewById(R.id.num);
        number = findViewById(R.id.number);
        bat= findViewById(R.id.bat);
        bat.setBackground(getResources().getDrawable(R.drawable.bat_big));
        goal = findViewById(R.id.goal);
        count = findViewById(R.id.count);
        game_whole = findViewById(R.id.game_whole);
        gif1 = findViewById(R.id.gif1);
        gif2 = findViewById(R.id.gif2);
        gif3 = findViewById(R.id.gif3);
        gif4 = findViewById(R.id.gif4);
        gif5 = findViewById(R.id.gif5);
        gif6 = findViewById(R.id.gif6);
        gif7 = findViewById(R.id.gif7);
        gif8 = findViewById(R.id.gif8);
        gif9 = findViewById(R.id.gif9);
        gif1.setOnClickListener(this);
        gif2.setOnClickListener(this);
        gif3.setOnClickListener(this);
        gif4.setOnClickListener(this);
        gif5.setOnClickListener(this);
        gif6.setOnClickListener(this);
        gif7.setOnClickListener(this);
        gif8.setOnClickListener(this);
        gif9.setOnClickListener(this);
        countdown = findViewById(R.id.countdown);
//        bat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                aimNum++;
//                num.setText("" + aimNum + "球");
//                pb2.setProgress(aimNum);
//                animStart();
//            }
//        });

        pb1 = findViewById(R.id.pb1);
        pb2 = findViewById(R.id.pb2);
        level_title = findViewById(R.id.level_title);
        if(ModeSelectActivity.gameLevel == 1){
            level_title.setText("第一关");
            game_whole.setBackground(getResources().getDrawable(R.drawable.bg_launcher));
        }
        if(ModeSelectActivity.gameLevel == 2){
            level_title.setText("第二关");
            game_whole.setBackground(getResources().getDrawable(R.drawable.bg_launcher));
        }
        if(ModeSelectActivity.gameLevel == 3){
            level_title.setText("第三关");
            game_whole.setBackground(getResources().getDrawable(R.drawable.bg_launcher));
        }
        if(ModeSelectActivity.gameLevel == 4){
            level_title.setText("第四关");
            game_whole.setBackground(getResources().getDrawable(R.drawable.bg_launcher));
        }
        if(ModeSelectActivity.gameLevel == 5) level_title.setText("第五关");
        if(ModeSelectActivity.gameLevel == 6){
            level_title.setText("第六关");
            game_whole.setBackground(getResources().getDrawable(R.drawable.bg_launcher));
        }
        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        tv2 = findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        tv3 = findViewById(R.id.tv3);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        tv4 = findViewById(R.id.tv4);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        tv5 = findViewById(R.id.tv5);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        count = findViewById(R.id.count);
        state = getIntent().getIntExtra("state", 0);
        type = getIntent().getIntExtra("type", 0);
        icon_play = findViewById(R.id.icon_play);
        start_ball_tv = findViewById(R.id.start_ball_tv);
//        start_ball =  findViewById(R.id.start_ball);
//        start_ball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                long nowTime = System.currentTimeMillis();
//                if (nowTime - mLastClickTime > TIME_INTERVAL){
//                    mLastClickTime = nowTime;
//                    if(start_ball_tv.getText().toString().equals("暂停游戏")){
//                        SelectModeMsg selectMsg = new SelectModeMsg();
//                        selectMsg.setType(100);
//                        selectMsg.setPosition(1);
//                        EventBus.getDefault().post(selectMsg);
//                        icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
//                        start_ball_tv.setText("继续游戏");
//                    }else{
//                        Intent intent = new Intent(GamePlayActivity.this, CountDownActivity.class);
//                        intent.putExtra("state", state);
//                        intent.putExtra("type", type);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }
//        });
        bat.setBackground(getResources().getDrawable(R.drawable.bat5));
        tip = findViewById(R.id.tip);
        back_ball = findViewById(R.id.back_ball);
        back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        isQuit = 0;
                    }
                }, 3000);
                if(isQuit == 1){
                    SelectModeMsg selectMsg = new SelectModeMsg();
                    selectMsg.setType(100);
                    selectMsg.setPosition(1);
                    EventBus.getDefault().post(selectMsg);
                    finish();
                }else{
                    ShowHelper.toastShort(GamePlayActivity.this, "再按一次退出游戏。");
                }
                isQuit = 1;            }
        });
//        back_ball.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                SelectModeMsg selectMsg = new SelectModeMsg();
//                selectMsg.setType(100);
//                selectMsg.setPosition(1);
//                EventBus.getDefault().post(selectMsg);
//                finish();
//                return true;
//            }
//        });
//        back_ball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                long nowTime = System.currentTimeMillis();
//                if (nowTime - mLastClickTime > TIME_INTERVAL){
//                    mLastClickTime = nowTime;
//                    SelectModeMsg selectMsg = new SelectModeMsg();
//                    selectMsg.setType(100);
//                    selectMsg.setPosition(1);
//                    EventBus.getDefault().post(selectMsg);
//                    finish();
//                }
//            }
//        });
        float curTranslationX = bat_layout.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(bat_layout, "translationY", curTranslationX, 10f, curTranslationX);
        animator.setDuration(3500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(type);
                selectMsg.setPosition(state);
                EventBus.getDefault().post(selectMsg);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count.setVisibility(View.GONE);
                number.setVisibility(View.GONE);
                timeCount = new TimeCount(120 * 1000, 1000);
                timeCount.start();
            }
        },6000);

        timeCount1 = new TimeCount1( 6 * 1000, 1000);
//        timeCount1.start();
    }

    private void animStart() {

        goal.setVisibility(View.VISIBLE);
        Animation a = AnimationUtils.loadAnimation(GamePlayActivity.this, R.anim.scalebig);
//        a.setFillAfter(true);
        goal.startAnimation(a);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goal.setVisibility(View.GONE);
            }
        },1200);

    }
    private void PlayMusic(int MusicId) {

        music = MediaPlayer.create(this, MusicId);
        music.start();

    }
    private void setBat(){
        if(aimNum == 3) bat.setBackground(getResources().getDrawable(R.drawable.bat4));
        if(aimNum == 6) bat.setBackground(getResources().getDrawable(R.drawable.bat3));
        if(aimNum == 9) bat.setBackground(getResources().getDrawable(R.drawable.bat2));
        if(aimNum == 12) bat.setBackground(getResources().getDrawable(R.drawable.bat1));
        if(aimNum == 15) bat.setBackground(getResources().getDrawable(R.drawable.bat_big));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.gif1:
                aimNum++;
                num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                pb2.setProgress(aimNum);
                PlayMusic(R.raw.hit);
                setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif1, 1));
                return;
            case R.id.gif2:
                aimNum++;
                num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                pb2.setProgress(aimNum);
                PlayMusic(R.raw.hit);
                setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif2, 1));
                return;
                case R.id.gif3:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                    Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif3, 1));
                    return;
                case R.id.gif4:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                    Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif4, 1));
                    return;
                case R.id.gif5:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif5, 1));
                    return;
                case R.id.gif6:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif6, 1));
                    return;
                case R.id.gif7:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif7, 1));
                    return;
                case R.id.gif8:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif8, 1));
                    return;
                case R.id.gif9:
                    aimNum++;
                    num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
                    pb2.setProgress(aimNum);
                    PlayMusic(R.raw.hit);
                    setBat();
                Glide.with(GamePlayActivity.this).load(R.drawable.hit_fire).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(new GlideDrawableImageViewTarget(gif9, 1));
                    return;
        }
    }

    RequestListener requestListener = new RequestListener<Integer, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }
        @Override
        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            gif1.setImageDrawable(null);
            gif2.setImageDrawable(null);
            gif3.setImageDrawable(null);
            gif4.setImageDrawable(null);
            gif5.setImageDrawable(null);
            gif6.setImageDrawable(null);
            gif7.setImageDrawable(null);
            gif8.setImageDrawable(null);
            gif9.setImageDrawable(null);
            gif1.setImageDrawable(null);
            gif2.setImageDrawable(null);
            gif3.setImageDrawable(null);
            gif4.setImageDrawable(null);
            gif5.setImageDrawable(null);
            gif6.setImageDrawable(null);
            gif7.setImageDrawable(null);
            gif8.setImageDrawable(null);
            gif9.setImageDrawable(null);
            return false;
        }

    };


    /**
     * 定时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {  // 计时完毕

//            EggGameScoreMsg eggGameScoreMsg = new EggGameScoreMsg();
//            eggGameScoreMsg.score = aimNum;
//            EventBus.getDefault().post(eggGameScoreMsg);1
            countdown.setText("0秒");
            num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
            sendNum = ModeSelectActivity.trainNums;
            getResult();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    SelectModeMsg selectMsg = new SelectModeMsg();
                    selectMsg.setType(100);
                    selectMsg.setPosition(1);
                    EventBus.getDefault().post(selectMsg);
//                    finish();
                    timeCount.cancel();
                }
            }, 300);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            countdown.setText("" + millisUntilFinished/1000 + "秒");
            num.setText("击中" + aimNum + "球/" + ModeSelectActivity.trainNums + "球");
            pb1.setProgress((int)(millisUntilFinished/1200));
            if(millisUntilFinished/1000<30){
                if(ModeSelectActivity.gameLevel == 1 || ModeSelectActivity.gameLevel == 2 || ModeSelectActivity.gameLevel == 3){
                    if(aimNum> 0.5 * ModeSelectActivity.trainNums){
                        bat.setBackground(getResources().getDrawable(R.drawable.bat_crack));
                    }
                }else{
                    if(aimNum> 0.3 * ModeSelectActivity.trainNums){
                        bat.setBackground(getResources().getDrawable(R.drawable.bat_crack));
                    }
                }
            }
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
            timeCount1.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程

            number.setText(millisUntilFinished/1000 + "");

        }

    }

    private void getResult() {

            HashMap<String, String> params = new HashMap<>();
            params.put("UserId", ModeSelectActivity.userId);
            params.put("serveNum", sendNum + "");
            params.put("hitNum", aimNum + "");
            params.put("devNum", ModeSelectActivity.url);
            params.put("level", ModeSelectActivity.gameLevel + "");
            MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetSingleLevelScores", params, new MyJsonCallbalk() {
                @Override
                public void onError(Exception e, int code) {
                    AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                }
                @Override
                public void onResponse(RequestCallVo requestCallVo) {
                    AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                    if (requestCallVo.getType() == 1) {
                        ScoreGameInfo scoreGameInfo = JSON.parseObject(requestCallVo.getData().toString(), ScoreGameInfo.class);

                        ShowHelper.toastLong(GamePlayActivity.this, "信息：Scores：" + scoreGameInfo.getScore() + " serveNum:" +
                                scoreGameInfo.getServeNum() + " hitNum:" + scoreGameInfo.getHitNum() + " hitRate:" + scoreGameInfo.getHitRate() + " isPass:" + scoreGameInfo.getIsPass() + " topLevel:" + scoreGameInfo.getTopLevel() + " currentLevel: " + scoreGameInfo.getLevel());
                        if(scoreGameInfo.getIsPass() == 1){
                            Intent intent = new Intent(GamePlayActivity.this, WinNoteActivity.class);
                            intent.putExtra("score", scoreGameInfo.getScore());
                            startActivity(intent);
                            finish();
                        }else if(scoreGameInfo.getIsPass() == 0){
                            Intent intent = new Intent(GamePlayActivity.this, FailNoteActivity.class);
                            intent.putExtra("score", scoreGameInfo.getScore());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(GamePlayActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

    }

    @Subcriber
    private void dealSelectEvent(StopEventMsg stopEventMsg) {

        if(stopEventMsg.stopMsg.equals("stop")){
            finish();
        }

    }

    @Subcriber
    private void showTipEvent(ShowTipEventMsg showTipEventMsg) {

        if(showTipEventMsg.tipMsg.equals("tip")){
            tip.setVisibility(View.VISIBLE);
        }

    }

    @Subcriber
    private void dealFaultDataEvent(ShowFaultMsg showFaultMsg) {
        if(showFaultMsg.code != 0)
            finish();
    }

    @Subcriber
    private void showTwoMinEvent(ShowTwoMinAlertMsg showTwoMinAlertMsg) {

        if(showTwoMinAlertMsg.tipMsg.equals("two")){
            SelectModeMsg selectMsg = new SelectModeMsg();
            selectMsg.setType(100);
            selectMsg.setPosition(1);
            EventBus.getDefault().post(selectMsg);
            GamePlayActivity.this.finish();
        }

    }
    @Subcriber
    private void showAlertEvent(ShowAlertNearMsg showAlertNearMsg) {

        if(showAlertNearMsg.tipMsg.equals("near")){
            SelectModeMsg selectMsg = new SelectModeMsg();
            selectMsg.setType(100);
            selectMsg.setPosition(1);
            EventBus.getDefault().post(selectMsg);
            ShowHelper.toastShort(GamePlayActivity.this, "指令" + showAlertNearMsg.msgCode);
//            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
//            start_ball_tv.setText("继续游戏");
            if(alertIn == 0)
                startActivity(new Intent(GamePlayActivity.this, PeopleAlertActivity.class));
        }
    }

    @Subcriber
    private void showNoBallsEvent(ShowNoBallsMsg showNoBallsMsg) {

        if(showNoBallsMsg.tipMsg.equals("no")){
//            SelectModeMsg selectMsg = new SelectModeMsg();
//            selectMsg.setType(100);
//            selectMsg.setPosition(1);
//            EventBus.getDefault().post(selectMsg);
//            ShowHelper.toastShort(GamePlayActivity.this, "指令" + showNoBallsMsg.msgCode);
//            icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
//            start_ball_tv.setText("继续游戏");
            if(noBallsIn == 0)
                startActivity(new Intent(GamePlayActivity.this, AlertNoBallsActivity.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        alertIn = 0;
        noBallsIn = 0;
    }
    @Override
    protected void onPause() {
        super.onPause();
        alertIn = 1;
        noBallsIn = 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(null!=timeCount)
            timeCount.cancel();
        if(null!=timeCount1)
            timeCount1.cancel();
    }

//    @Override
//    public boolean onKeyDown(int kCode,KeyEvent kEvent)
//    {
//        ShowHelper.toastShort(GamePlayActivity.this, "Keycode:" + kCode);
//        switch(kCode)
//        {
//            case 0:  //电源键
//                SelectModeMsg selectMsg = new SelectModeMsg();
//                selectMsg.setType(100);
//                selectMsg.setPosition(1);
//                EventBus.getDefault().post(selectMsg);
//                icon_play.setBackground(getResources().getDrawable(R.drawable.continue_play));
//                start_ball_tv.setText("继续练球");
////                startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
////                (new Handler()).postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        StopEventMsg stopEventMsg = new StopEventMsg();
////                        EventBus.getDefault().post(stopEventMsg);
//////                                startGameEgg();
////                    }
////                }, 1000);
//                return true;
//
//            case KeyEvent.KEYCODE_HOME:
////                SelectModeMsg selectMsg2 = new SelectModeMsg();
////                selectMsg2.setType(100);
////                selectMsg2.setPosition(1);
////                EventBus.getDefault().post(selectMsg2);
//////                startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
////                (new Handler()).postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        StopEventMsg stopEventMsg = new StopEventMsg();
////                        EventBus.getDefault().post(stopEventMsg);
//////                                startGameEgg();
////                    }
////                }, 1000);
//                return true;
//
//            case KeyEvent.KEYCODE_MENU:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=menu");
//                return true;
//
//            case KeyEvent.KEYCODE_DPAD_LEFT:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=left");
//                return true;
//
//            case KeyEvent.KEYCODE_DPAD_UP:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=up");
//                return true;
//
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=right");
//                return true;
//
//            case KeyEvent.KEYCODE_DPAD_DOWN:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=down");
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_UP:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=VOLUME_UP");
//                return true;
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                Intent intent = new Intent(GamePlayActivity.this, CountDownActivity.class);
//                intent.putExtra("state", state);
//                intent.putExtra("type", type);
//                startActivity(intent);
//                finish();
//
//                return true;
//            case KeyEvent.KEYCODE_BACK:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=where");
//                SelectModeMsg selectMsg3 = new SelectModeMsg();
//                selectMsg3.setType(100);
//                selectMsg3.setPosition(1);
//                EventBus.getDefault().post(selectMsg3);
////                startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        StopEventMsg stopEventMsg = new StopEventMsg();
//                        EventBus.getDefault().post(stopEventMsg);
////                                startGameEgg();
//                    }
//                }, 1000);
//                return true;
//            case 264:    //鼠标模式
//                return false;
//        }
//        return super.onKeyDown(kCode,kEvent);
//    }

}
