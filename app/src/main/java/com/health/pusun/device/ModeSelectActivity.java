package com.health.pusun.device;

import android.app.Dialog;
import android.app.smdt.SmdtManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.adapter.ModeCategaryAdapter;
import com.health.pusun.device.application.App;
import com.health.pusun.device.train.GroupTrainFragment;
import com.health.pusun.device.train.SingleTrainFragment;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.BasicData;
import com.health.pusun.device.utils.DateUtil;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.views.NoScrollViewPager;
import com.health.pusun.device.vo.ActivityInfoVo;
import com.health.pusun.device.vo.RequestCallVo;
import com.health.pusun.device.vo.UserBookInfo;
import com.health.pusun.device.vo.eventbusmsg.BatteryVolumeMsg;
import com.health.pusun.device.vo.eventbusmsg.CameraTwoStartMsg;
import com.health.pusun.device.vo.eventbusmsg.CloseTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.EggGameScoreMsg;
import com.health.pusun.device.vo.eventbusmsg.KeySelectMsg;
import com.health.pusun.device.vo.eventbusmsg.KeyStartMsg;
import com.health.pusun.device.vo.eventbusmsg.OpenDoorMsg;
import com.health.pusun.device.vo.eventbusmsg.PadControlMsg;
import com.health.pusun.device.vo.eventbusmsg.RemoteControllerMsg;
import com.health.pusun.device.vo.eventbusmsg.RequestDataMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectTypeMsg;
import com.health.pusun.device.vo.eventbusmsg.SetFastLMTMsg;
import com.health.pusun.device.vo.eventbusmsg.SetGroupLMTMsg;
import com.health.pusun.device.vo.eventbusmsg.SetVoltageMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowAlertNearMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowChuankouMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowFaultMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowNoBallsMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTipEventMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.StartRecordMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;
import com.health.pusun.device.vo.eventbusmsg.StopRecordMsg;
import com.health.pusun.device.vo.eventbusmsg.ZidingyiMsg;
import com.kyleduo.switchbutton.SwitchButton;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ModeSelectActivity extends SerialPortActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private NoScrollViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private ModeCategaryAdapter modeCategaryAdapter;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    private LinearLayout back_train_single, back_train_group, back_game_group;
//    private UserBookInfo userBookInfo;
//    private ImageView head_img, power;
    private TextView tv_group, tv_single, tv_game;
    private TextView nick_name, time_start, time_end;
    private TimeCount timeCount;
    private TextView activity;
    public static String url;
    Intent intent;
    private TimeCount1 timeCount1;
    private int servingState = 0;
    private int picNum = 0;
//    private SmdtManager smdt;
    private VideoView ijkVideoView;
    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/";
//    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
//            + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";
    public static String selectName = "";
    public static int trainNums;
    public static String userId;
    public static int gameLevel;
    private boolean isTwo = true;
    public static final int TIAOQIU = 1001;
    public static final int PUQIU = 1002;
    public static final int FANGWANG = 1003;
    public static final int GOUQIU = 1004;
    public static final int CUOQIU = 1005;
    public static final int TUIQIU = 1006;
    public static final int JIEFAQIU = 1007;
    public static final int CHOUQIU = 1008;
    public static final int DANGQIU = 1009;
    public static final int SHAQIU_ZHONGCHANG = 1010;
    public static final int SHAQIU_HOUCHANG = 1011;
    public static final int PIDIAO = 1012;
    public static final int GAOYUANQIU = 1013;
    public static final int PINGGAOQIU = 1014;
    public static final int MIZIBU = 1015;
    public static final int SHASHANGWANG = 1016;
    public static final int ZUOXIEXIAN = 1017;
    public static final int YOUXIEXIAN = 1018;
    public static final int DIJIAOCEXIANG = 1019;
    public static final int ZHONGCHANGCEXIANG = 1020;
    public static final int BANCHANGTUJI = 1021;
    public static final int DRIVEBALLBACK = 1022;
    public static final int BLOCKBALLBACK = 1023;
    public static final int SMASHBALLBACK = 1024;
    public static final int FANSHOUGAOYUANQIU = 1025;
    public static final int FANSHOUDIAOQIU = 1026;
    public static final int SHANGSWZUHE = 1027;
    public static final int CHOUQIUZUHE = 1028;
    public static final int TIAOZHENG = 1060;
    public static final int TIAOFAN = 1061;
    public static final int PUHENG = 1062;
    public static final int PUFAN = 1063;
    public static final int CUOZHENG = 1064;
    public static final int CUOFAN = 1065;
    public static final int TUIZHENG = 1066;
    public static final int TUIFAN = 1067;
    public static final int GOUZHENG = 1068;
    public static final int GOUFAN = 1069;
    public static final int SIFANGQIU = 1070;
    public static final int ZUOCHANGJIEFA = 1071;
    public static final int YOUCHANGJIEFA = 1072;
    public static final int SHUANGCESHASHANGWANG = 1073;
    public static final int FANGZHENG = 1050;
    public static final int FANGFAN = 1051;
    public static final int GAOYUANDIAOQIU = 1052;
    public static final int DIAOSHANGZHI = 1053;
    public static final int DIAOSHANGDUIZUO = 1054;
    public static final int DIAOSHANGDUIYOU = 1055;
    public static final int SHASHANGDUIZUO = 1056;
    public static final int SHASHANGDUIYOU = 1057;
    public static final int CAIDAN = 9999;
    public static final int TONGGUAN = 2020;
    public static final int DIYIGUAN = 2021;
    public static final int DIERGUAN = 2022;
    public static final int DISANGUAN = 2023;
    public static final int DISIGUAN = 2024;
    public static final int DIWUGUAN = 2025;
    public static final int DILIUGUAN = 2026;
    public static final int SUIJISIFANGQIU = 3001;
    public static final int DINGDIANPUQIU = 3002;
    public static final int ZUOCHANGJIEFA1 = 1075;
    public static final int ZUOCHANGJIEFA2 = 1076;
    public static final int YOUCHANGJIEFA1 = 1077;
    public static final int YOUCHANGJIEFA2 = 1078;
    public static final int SUIJI1 = 1101;
    public static final int SUIJI2 = 1102;
    public static final int SUIJI3 = 1103;
    public static final int SUIJI4 = 1104;
    public static final int SUIJI5 = 1105;
    public static final int KAQIU = 73;
    public static final int ZIDINGYI = 1107;
    private int type;
    //lob_ball_up, lob_ball_down, catch_ball_up, catch_ball_down, rub_ball_up,
    //rub_ball_down, push_ball_up, push_ball_down, hook_ball_up, hook_ball_down;
    private int isResume = 1;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
    public static int state;   //单项:0,  组合:1,  游戏：2
    public static int veloMode = 1;   //1为快速模式  2为慢速模式
    private TextView info_game;
    private RelativeLayout mute;
    private RelativeLayout back_ball;
    private int volumeInt = 1;
    public static int isRecord = 0;
    //存放倒计时
    public static long duration;
    private SwitchButton switchbtn;
    private int categary = 1;
    private Spinner spinner_fast, spinner_low;
    private  String[] fastnames = {"1.2秒","1.3秒","1.4秒","1.5秒","1.6秒","1.7秒","1.8秒","1.9秒","2.0秒" ,"2.1秒","2.2秒"};
    private int[] fastNums = {19,20,21,10, 11, 12, 0, 1, 2,3,4};
    private  String[] lownames = {"2.3秒","2.4秒","2.5秒","2.6秒","2.7秒","2.8秒","2.9秒","3.0秒","3.1秒","3.2秒","3.3秒","3.4秒","3.5秒","3.6秒","3.7秒","3.8秒","3.9秒","4.0秒"};
    public static int[] mis = {5,26,2,27,7,20,30,11,31,22,14,28,16,29,18};
    public static int fastSelection = 6;
    public static int lowSelection = 5;
    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm;
    private Spinner spinner_nums;
    private Dialog dialog;
    private int fastNum = 5;
    private int showPad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        setContentView(R.layout.activity_mode_select);
        duration = getIntent().getLongExtra("duration", -1l);
        if(duration != -1)App.saveUserPreference("duration", duration);
        else App.saveUserPreference("duration", -1l);
        mViewPager = (NoScrollViewPager) findViewById(R.id.fragmentPager);
        mViewPager.setNoScroll(true);
        switchbtn = findViewById(R.id.switchbtn);
        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        categary = 0;
                        ShowHelper.toastShort(ModeSelectActivity.this, "高级");
                        sendBaseData();
                    }else{
                        categary = 1;
                        ShowHelper.toastShort(ModeSelectActivity.this, "初级");
                        sendBaseData();
                    }
            }
        });
        EventBus.getDefault().register(this);
//        userBookInfo = (UserBookInfo) getIntent().getSerializableExtra("user");
//        userId = userBookInfo.getUserId();
//        AppLog.e("url:" + userBookInfo.getHeadImg());
        intent = new Intent(ModeSelectActivity.this, VideoSerivice.class);
//        url = getIntent().getStringExtra("url");
//        head_img = (ImageView) findViewById(R.id.head_img);
//        power = (ImageView) findViewById(R.id.power);
        nick_name = (TextView) findViewById(R.id.nick_name);
        time_start = (TextView) findViewById(R.id.time_start);
        mute = findViewById(R.id.mute);
        activity = findViewById(R.id.activity);
        time_end = findViewById(R.id.time_end);
        tv_single = findViewById(R.id.tv_single);
        tv_group = findViewById(R.id.tv_group);
        tv_game = findViewById(R.id.tv_game);
        info_game = findViewById(R.id.info_game);
        back_ball = findViewById(R.id.back_ball);
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMicrophoneMute(true);
        mTabs = new ArrayList<Fragment>();
        SingleTrainFragment myFragment1 = new SingleTrainFragment();
        GroupTrainFragment myFragment2 = new GroupTrainFragment();
//        ProFragment gameChallengeFragment = new ProFragment();
        mTabs.add(myFragment1);
        mTabs.add(myFragment2);
//        mTabs.add(gameChallengeFragment);
        modeCategaryAdapter = new ModeCategaryAdapter(this, getSupportFragmentManager(), mTabs);
        mViewPager.setAdapter(modeCategaryAdapter);
//        smdt = SmdtManager.create(this);
        //表示homeFragment左右预加载几个
        mViewPager.setOffscreenPageLimit(2);
//        smdt.smdtSetControl(6,0);
//        String usb0 = smdt.smdtGetUSBPath(this, 0);
//        if(usb0 != null)
//        ShowHelper.toastShort(this,usb0);
//        smdt.smdtSetLcdBackLight(0);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//
//                smdt.smdtSetLcdBackLight(1);
//                smdt.setBrightness(getContentResolver(), 220);
//            }
//        }, 3000);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(volumeInt == 1){
////                    smdt.smdtSetControl(6,1);
//                    smdt.smdtSetVolume(getApplicationContext(), 9);
//                    mute.setBackground(getResources().getDrawable(R.drawable.ic_volume));
//                    volumeInt = 0;
//                }else{
////                    smdt.smdtSetControl(6,0);
//                    smdt.smdtSetVolume(getApplicationContext(), 1);
//                    mute.setBackground(getResources().getDrawable(R.drawable.ic_no_volume));
//                    volumeInt = 1;
//                }
            }
        });
        back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
//                ShowHelper.showAlertDialog(ModeSelectActivity.this, "确定要结束本次训练吗？", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(null != timeCount) timeCount.cancel();
//                        App.saveUserPreference("duration", 0l);
//                        SelectModeMsg selectMsg = new SelectModeMsg();
//                        selectMsg.setType(100);
//                        selectMsg.setPosition(1);
//                        EventBus.getDefault().post(selectMsg);
//                        finish();
//                    }
//                });
////                finish();
            }
        });
        mViewPager.setOnPageChangeListener(this);
        back_train_single = (LinearLayout) findViewById(R.id.back_train_single);
        back_train_single.setOnClickListener(this);
        back_train_group = (LinearLayout) findViewById(R.id.back_train_group);
        back_train_group.setOnClickListener(this);
        back_game_group = findViewById(R.id.back_game_group);
        back_game_group.setOnClickListener(this);
//        if(null != userBookInfo.getHeadImg()){
////            GlideUtil.loadImage(context, App.BASE_URL + data.getImage3(), holder.img3);
//            String urlHead;
//            if(!userBookInfo.getHeadImg().contains("http")) urlHead = App.BASE_URL + userBookInfo.getHeadImg();
//            else urlHead = userBookInfo.getHeadImg();
//            AppLog.e("urlHead:" + urlHead);
//            Glide.with(this).load(urlHead).asBitmap().centerCrop().into(new BitmapImageViewTarget(head_img) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(ModeSelectActivity.this.getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    AppLog.e("setBitmap:" + resource.toString());
//                    head_img.setImageDrawable(circularBitmapDrawable);
//                }
//            });
//        }
//        nick_name.setText(userBookInfo.getNickName());
//        time_start.setText("开始时间：" + userBookInfo.getStartTime().replace("T", " "));
//        time_end.setText("结束时间：" + userBookInfo.getEndTime().replace("T", " "));

//        try {
//            mOutputStream.write(new byte[]{(byte)0xAA});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(duration != -1l){
            timeCount = new TimeCount(duration  * 1000, 10000);
            timeCount.start();
        }

        ijkVideoView = findViewById(R.id.player);
//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        ijkVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels / 16 * 9));
//        ijkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
//        ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + "self_solve.mp4")).toString()); //设置视频地址
//        StandardVideoController controller = new StandardVideoController(this);
//        ijkVideoView.setLooping(true);
//        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
//        controller.setVisibility(View.GONE);
//        MainActivity.isRecording = 0;
//        List<VideoModel> videos = new ArrayList<>();
//        AdController adController = new AdController(this);
//        adController.setControllerListener(new ControllerListener() {
//            @Override
//            public void onAdClick() {
//                Toast.makeText(ModeSelectActivity.this, "广告点击跳转", Toast.LENGTH_SHORT).show();
//            }
//        });
////        videos.add(new VideoModel(URL_AD, "广告", adController, true));
////        videos.add(new VideoModel(Uri.fromFile(new File(URL_LOCAL)).toString(), "广告1", new StandardVideoController(this), true));
//        videos.add(new VideoModel(Uri.fromFile(new File(URL_LOCAL)).toString(), "广告1", new StandardVideoController(this), false));
////        videos.add(new VideoModel(URL_AD, "广告2", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_AD3, "广告3", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_VOD, "这是一个标题", new StandardVideoController(this), false));
//        ijkVideoView.setVideos(videos);
//        videos.get(0).controller.setVisibility(View.GONE);
//        PlayerConfig playerConfig = new PlayerConfig.Builder().build();
//        playerConfig.isLooping = true;
//        playerConfig.isCache = false;
//        ijkVideoView.setPlayerConfig(playerConfig);
//        ijkVideoView.start();
            enterDevice();
//        checkDeviceDistance();
//        getVenueActivityInfo();
//        if(isWifiConnected(this)){
//            timeCount1 = new TimeCount1(12* 60 * 60 * 1000, 600000);
//            timeCount1.start();
//        }
//         (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                //查询电量
//                checkPower();
//            }
//        }, 350);
//
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                //查询电量
//                checkPower();
//            }
//        }, 700);
//
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                //查询电量
//                checkPower();
//            }
//        }, 1100);
//
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                //查询电量
//                checkPower();
//            }
//        }, 1500);

        //彩蛋调试
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                SelectModeMsg selectMsg = new SelectModeMsg();
//                selectMsg.setType(100);
//                selectMsg.setPosition(1);
//                EventBus.getDefault().post(selectMsg);
//                startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        StopEventMsg stopEventMsg = new StopEventMsg();
//                        EventBus.getDefault().post(stopEventMsg);
//                    }
//                }, 1000);
//
//            }
//        }, 60000);

        //测试报错接口
//         testError();
        for(int i = 0; i < BasicData.sets.length; i++){
            if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.sets[i][0] = (short) App.getIntUserPreference("sets" + i + "0");
            if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.sets[i][1] = (short) App.getIntUserPreference("sets" + i + "1");
            if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.sets[i][2] = (short) App.getIntUserPreference("sets" + i + "2");
//            if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.b2[i][0] = (short) App.getIntUserPreference("sets" + i + "0");
//            if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.b2[i][1] = (short) App.getIntUserPreference("sets" + i + "1");
//            if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.b2[i][2] = (short) App.getIntUserPreference("sets" + i + "2");
        }

        spinner_fast = findViewById(R.id.spinner_fast);
        SpinnerAdapter gameKindArray= new SpinnerAdapter(ModeSelectActivity.this,android.R.layout.simple_spinner_item, fastnames);
//        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fast.setAdapter(gameKindArray);
        spinner_fast.setSelection(6);
        fastSelection = 6;
        spinner_fast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setfast(fastNums[i]);
                fastSelection = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_low = findViewById(R.id.spinner_low);
        SpinnerAdapter gameKindArray2= new SpinnerAdapter(ModeSelectActivity.this,android.R.layout.simple_spinner_item, lownames);
//        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_low.setAdapter(gameKindArray2);
        spinner_low.setSelection(5);
        spinner_low.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setlow(i+2);
                lowSelection = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sendBaseData();
        //设定风扇开关时间
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    short a1 = (short)App.getIntUserPreference("on");
                    short a2 = (short)App.getIntUserPreference("stop");
                    byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) 0x01, (byte) 0xA5};
                    mOutputStream.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    short a1 = (short) App.getIntUserPreference("high");
                    short a2 = (short) App.getIntUserPreference("middle");
                    short a3 = 1000;
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                    AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
                    mOutputStream.write(buf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 6000);
        //设定默认快速点位
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {

                try {
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x71, (byte)(0), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x71^(byte)(10)^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
                    mOutputStream.write(buf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1200);
        //设定默认慢速点位
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {

                try {
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x70, (byte)(7), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x70^(byte)(2)^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
                    mOutputStream.write(buf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1300);

    }

    private void setfast(final int i) {

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {

                try {
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x71, (byte)(i), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x71^(byte)(i)^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
                    mOutputStream.write(buf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 10);

    }

    private void setlow(final int i) {

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {

                try {
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x70, (byte)(i), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x70^(byte)(i)^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
                    mOutputStream.write(buf);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 10);
    }

    @Subcriber
    private void dealFastLMTEvent(SetFastLMTMsg setFastLMTMsg) {
        if(setFastLMTMsg.fastMsg.equals("middle")){
            if(fastSelection < 10){
                setfast(4);
                spinner_fast.setSelection(10);
                fastSelection = 10;
            }
        }
    }

    @Subcriber
    private void dealGroupLMTEvent(SetGroupLMTMsg setGroupLMTMsg) {
        if(setGroupLMTMsg.fastMsg.equals("group")){
            if(fastSelection < 3){
                setfast(10);
                spinner_fast.setSelection(3);
                fastSelection = 3;
            }
        }
    }
    private void changeVideo(int typeVideo){

//        ijkVideoView.release();
//        if(typeVideo == 1027 || typeVideo == 1073) ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + 1015 + ".mp4")).toString());
//        else ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + typeVideo + ".mp4")).toString()); //设置视频地址
//        StandardVideoController controller = new StandardVideoController(this);
//        ijkVideoView.setLooping(true);
//        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
//        controller.setVisibility(View.GONE);
//        ijkVideoView.start();

    }

//    private void testError() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("devNum", url);
//        params.put("devresID", "" + 1);
//        params.put("reson", "");
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/FaultReport", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        //更新后台设备状态
//        HashMap<String, String> params2 = new HashMap<>();
//        params2.put("devNum", url);
//        params2.put("DeviceState", "" + 0);
//        params2.put("DeviceBattery", "" + 60);
//        params2.put("PickBattery", "" + 60);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetDevState", params2, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    private void getVenueActivityInfo() {
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("DevNumber", url);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/getVenueActivityInfo", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                //Data=[{"ActivitIinfo":"优惠大酬宾，原价45元/H，现35元/H","AdminID":"47e210e5-75be-4758-8443-c289ac03596a","EndTime":"2018-11-13T18:38:34.373","ID":"6246c7ab-bf93-422a-a3e2-95288cfe54c5","VenueID":"6246c7ab-bf93-422a-a3e2-95277cfe54c4"},
//                if(requestCallVo.getType() == 1) {
//                    List<ActivityInfoVo> activityInfoVos = JSON.parseArray(requestCallVo.getData().toString(), ActivityInfoVo.class);
//                    StringBuffer stringBuffer = new StringBuffer("场馆活动信息：");
//                    for(int i = 0; i < activityInfoVos.size(); i++){
//                        stringBuffer.append(" " + (i + 1) + ":" + activityInfoVos.get(i).getActivitIinfo() );
//                    }
//                    activity.setText(stringBuffer);
//                }
//
//            }
//        });
//    }

    private void sendBaseDataTiaoqiuLeft() {

            byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x01), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x01^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
            try {
                mOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void sendBaseDataTiaoqiuRight() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x03), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x03^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataTiaoqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(8), (byte)(9),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^8^9^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataPuqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(9), (byte)(8),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^9^8^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataShaShangwang() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(31), (byte)(30),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^31^30^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataShaShangwangMansu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(31), (byte)(30),  (byte)(3), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^31^30^3^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataGouqiuAndCuoqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(3), (byte)(1),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^3^1^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataTwoHouchang(int position1, int position2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(position1), (byte)(position2),  (byte)(3), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^position1^position2^3^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataTwoKuaisu(int position1, int position2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(position1), (byte)(position2),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^position1^position2^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataTwoHouchangMansu(int position1, int position2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(position1), (byte)(position2),  (byte)(3), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^position1^position2^3^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSifangqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(1), (byte)(27),  (byte)(29), (byte)(28), (byte)(26), (byte)(0), (byte)(0x92^1^27^29^28^26^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSifangqiuMansu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(3), (byte)(27),  (byte)(29), (byte)(28), (byte)(26), (byte)(0), (byte)(0x92^3^27^29^28^26^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSuijiTwoPosition(int pos1, int pos2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(0), (byte)(pos1),  (byte)(pos2), (byte)(0), (byte)(0), (byte)(0), (byte)(0x90^0^pos1^pos2^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSuijiTwoPositionMansu(int pos1, int pos2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(3), (byte)(pos1),  (byte)(pos2), (byte)(0), (byte)(0), (byte)(0), (byte)(0x90^3^pos1^pos2^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSuijiSifangqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(0), (byte)(27),  (byte)(29), (byte)(28), (byte)(26), (byte)(0), (byte)(0x90^1^27^29^28^26^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSuijiSifangqiuMansu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(3), (byte)(27),  (byte)(29), (byte)(28), (byte)(26), (byte)(0), (byte)(0x90^3^27^29^28^26^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataShuangceSSW() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(1), (byte)(17),  (byte)(7), (byte)(15), (byte)(5), (byte)(0), (byte)(0x92^1^17^8^15^4^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataShuangceSSWMansu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(3), (byte)(17),  (byte)(7), (byte)(15), (byte)(5), (byte)(0), (byte)(0x92^1^17^8^15^4^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataZidingyiKuaisu(int pos1, int pos2, int pos3, int pos4, int pos5) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(0), (byte)(pos1),  (byte)(pos2), (byte)(pos3), (byte)(pos4), (byte)(pos5), (byte)(0x92^0^pos1^pos2^pos3^pos4^pos5), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataZidingyiMansu(int pos1, int pos2, int pos3, int pos4, int pos5) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(3), (byte)(pos1),  (byte)(pos2), (byte)(pos3), (byte)(pos4), (byte)(pos5), (byte)(0x92^3^pos1^pos2^pos3^pos4^pos5), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataZidingyiSuijiKuaisu(int pos1, int pos2, int pos3, int pos4, int pos5) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(0), (byte)(pos1),  (byte)(pos2), (byte)(pos3), (byte)(pos4), (byte)(pos5), (byte)(0x90^0^pos1^pos2^pos3^pos4^pos5), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataZidingyiSuijiMansu(int pos1, int pos2, int pos3, int pos4, int pos5) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(3), (byte)(pos1),  (byte)(pos2), (byte)(pos3), (byte)(pos4), (byte)(pos5), (byte)(0x90^3^pos1^pos2^pos3^pos4^pos5), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataJiefaqiu(int position1, int position2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(1), (byte)(position1),  (byte)(position2), (byte)(0), (byte)(0), (byte)(0), (byte)(0x90^1^position1^position2^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendQianchangOtherqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(3), (byte)(1),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^3^1^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataBanchangtuji() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(12), (byte)(0x01),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^12^0x01^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataZhongchangShaqiuLeft() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x05), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x05^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataZhongchangShaqiuRight() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x07), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x07^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataHouchangShaqiuLeft() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x18), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x18^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendBaseDataHouchangShaqiuRight() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(0x1C), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^0x1C^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseData(int position, int backorfront){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(position), (byte)(backorfront),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^position^backorfront^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMiZiBuData(){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5C, (byte)(0), (byte)(0),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x5C^0^0^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendMiZiBuDataMansu(){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5C, (byte)(3), (byte)(0),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x5C^0^0^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendPositionData(final int front, final int back, final int whole){

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                byte[] buf = new byte[]{(byte)0xAA, (byte)0x9C, (byte)(front), (byte)(back),  (byte)(whole), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9C^front^back^whole^0x00^0x00^0x00), (byte)0xA5};
                try {
                    mOutputStream.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 6000);
    }

    private void sendPosition(final int front){
//        if(front != 0&&0 != App.getIntUserPreference("high") && 0 != App.getIntUserPreference("middle")){
//
//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public synchronized void run() {
//                    try {
//                        short a1 = (short) App.getIntUserPreference("high");
//                        short a2 = (short) App.getIntUserPreference("middle");
//                        short a3 = 1000;
//                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
//                        AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
//                        mOutputStream.write(buf);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, 6000);
//        }
//
//        if(front == 0&&0 != App.getIntUserPreference("highback") && 0 != App.getIntUserPreference("middleback") && 0 != App.getIntUserPreference("lowback")){
//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public synchronized void run() {
//                    try {
//                        short a1 = (short) App.getIntUserPreference("highback");
//                        short a2 = (short) App.getIntUserPreference("middleback");
//                        short a3 = (short) App.getIntUserPreference("lowback");
//                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
//                        AppLog.e("highback:" + a1 + " middleback:" + a2 + " lowback:" + a3);
//                        mOutputStream.write(buf);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, 6000);
//
//        }

    }

//    private void sendMiZiBuData(){
//        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(8), (byte)(4),  (byte)(13), (byte)(9), (byte)(18), (byte)(14), (byte)(0x9B^8^4^13^9^18^14), (byte)0xA5};
//        try {
//            mOutputStream.write(buf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void sendShaShangWangData(){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(18), (byte)(6),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^18^6^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendZuoXieXianData(){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(24), (byte)(6),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^24^6^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendYouXieXianData(){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(14), (byte)(8),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^14^8^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendKaqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x6D, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x6D^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Subcriber
    private void dealSetVoltageMsg(SetVoltageMsg setVoltageMsg) {
        if(veloMode == 1){
            try {
                short value = (short) App.getIntUserPreference("high");
                short a1,a2;
                if(fastSelection > 4){
                      a1 = value; a2 = value;
                  }else{
                    a1= (short)(value+20); a2= (short)(value+20);
//                    if(a1 <0) a1 = 0; if(a2 <0) a2 = 0;
                }
//                short a1 = (short) App.getIntUserPreference("high");
//                short a2 = (short) App.getIntUserPreference("high");
                short a3 = 1000;
                byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
                mOutputStream.write(buf);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //设定风扇开关时间
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        short a1 = (short)App.getIntUserPreference("onfast");
                        short a2 = (short)App.getIntUserPreference("stopfast");
                        if(fastSelection <= 3) a2 = (short)(a2-1);
                        if(a2<=0) a2 = 1;
//                        short a1 = 1;
//                        short a2 = 4;
                        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) 0x01, (byte) 0xA5};
                        mOutputStream.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        }else{
            try {
                short value = (short) App.getIntUserPreference("middle");
                short a1,a2;
                if(lowSelection <= 3){
                    a1 = value; a2 = value;
                }else{
                    a1= (short)(value-30); a2= (short)(value-30);
                    if(a1 <0) a1 = 0; if(a2 <0) a2 = 0;
                }
//                short a1 = (short) App.getIntUserPreference("middle");
//                short a2 = (short) (App.getIntUserPreference("middle"));
                short a3 = 1000;
                byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
                mOutputStream.write(buf);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //设定风扇开关时间
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        short a1 = (short)App.getIntUserPreference("on");
                        short a2 = (short)App.getIntUserPreference("stop");
                        if(lowSelection <= 3) a2 = (short)(a2-2);
                        if(lowSelection >7) a2 = (short)(a2+1);
                        if(a2<=0) a2 = 1;
                        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) 0x01, (byte) 0xA5};
                        mOutputStream.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        }

    }
    @Subcriber
    private void dealOpenDoorEvent(OpenDoorMsg openDoorMsg){

        if(openDoorMsg.openMsg.equals("open")){
            byte[] buf = new byte[]{(byte)0xAA, (byte)0x56, (byte)(0x01), (byte)(0x01),  (byte)(0x01), (byte)(0x01), (byte)(0x01), (byte)(0x01), (byte)0x01, (byte)0xA5};
            AppLog.e("opendoor!");
            try {
                mOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Subcriber
    private void padControlEvent(PadControlMsg padControlMsg) {

        if(showPad == 0){
            showPad++;
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    startActivity(new Intent(ModeSelectActivity.this, AlertPadControlActivity.class));

                    ModeSelectActivity.this.finish();
                }
            }, 2000);
        }

    }
    @Subcriber
    private void dealVVideoPlayEvent(SelectTypeMsg selectTypeMsg) {
//        changeVideo(selectTypeMsg.getType());
    }

//    @Subcriber
//    private void dealStopRecordEvent(StopRecordMsg stopRecordMsg) {

//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                stopService(intent);
//            }
//        }, 3000);

//    }

    @Subcriber
    private void dealStartRecordEvent(StartRecordMsg startRecordMsg) {
        if(startRecordMsg.code == 1){
            isRecord = 1;
            MainActivity.isRecording = 0;
            StopRecordMsg stopRecordMsg = new StopRecordMsg();
            EventBus.getDefault().post(stopRecordMsg);
//            stopService(intent);
//            timeCount1.cancel();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
//                    startService(intent);
                    CameraTwoStartMsg startRecordMsg = new CameraTwoStartMsg();
                    startRecordMsg.code = 1;
                    EventBus.getDefault().post(startRecordMsg);
                }
            }, 5000);

//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public synchronized void run() {
//                    StopRecordMsg stopRecordMsg = new StopRecordMsg();
//                    EventBus.getDefault().post(stopRecordMsg);
//                }
//            }, 75000);

//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public synchronized void run() {
////                    stopService(intent);
//                    isRecord = 0;
//                    StartRecordMsg startRecordMsg = new StartRecordMsg();
//                    startRecordMsg.code = 0;
//                    EventBus.getDefault().post(startRecordMsg);
//                }
//            }, 360000);
        }
    }

//    @Subcriber
//    private void dealEggGameScoreEvent(EggGameScoreMsg eggGameScoreMsg) {
//        updateEggScore(eggGameScoreMsg.score);
//    }

//    private void updateEggScore(int score) {
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("UserId", userBookInfo.getUserId());
//        params.put("gameName", "PoundsEgg");
//        params.put("scores", "" + score);
//            params.put("devNum", url);
////            params.put("techingName", selectName);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetGameScores", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//            }
//        });
//
//    }
public void showDialog() {
    if(dialog!= null) dialog.dismiss();
    dialog = new Dialog(this, R.style.InputDialogStyle);
    inflate = LayoutInflater.from(this).inflate(R.layout.notice_dialog, null);
    cancel = (TextView) inflate.findViewById(R.id.cancel);
    confirm = (TextView) inflate.findViewById(R.id.confirm);
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != timeCount) timeCount.cancel();
            App.saveUserPreference("duration", 0l);
            SelectModeMsg selectMsg = new SelectModeMsg();
            selectMsg.setType(100);
            selectMsg.setPosition(1);
            EventBus.getDefault().post(selectMsg);
            finish();
        }
    });
    dialog.setContentView(inflate);
    Window dialogWindow = dialog.getWindow();
    dialogWindow.setGravity(Gravity.BOTTOM);
    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
    lp.y = 20;
    dialogWindow.setAttributes(lp);
    dialog.show();
}
    @Subcriber
    private void dealSendDataEvent(RequestDataMsg requestDataMsg) {
        sendBaseData();
    }

    @Subcriber
    private void dealBatteryVolumeEvent(BatteryVolumeMsg batteryVolumeMsg) {
        updateDevBattery(batteryVolumeMsg.volume);
    }
    @Subcriber
    private void dealZidingyiEvent(ZidingyiMsg zidingyiMsg) {
        int mode = zidingyiMsg.mode;
        int[] nums;
        String s = zidingyiMsg.listString;
        if(s!=null && !"".equals(s)){
            nums = stringToInts(s);
        } else nums = new int[5];
        sendZidingyi(mode, nums);

    }
    private int getPos(int num){
        int pos = 0;
        for(int i = 0; i < mis.length; i++){
            if(num == (i+1)){
                pos = mis[i];
                return pos;
            }
        }
        return pos;
    }
    private void sendZidingyi(int mode, int[] nums) {
        int pos1= 0, pos2 = 0, pos3 = 0, pos4 = 0, pos5 = 0;
        if(nums.length == 5){
            pos1 = getPos(nums[0]); pos2 = getPos(nums[1]); pos3 = getPos(nums[2]); pos4 = getPos(nums[3]); pos5 = getPos(nums[4]);
        }
        if(nums.length == 4){
            pos1 = getPos(nums[0]); pos2 = getPos(nums[1]); pos3 = getPos(nums[2]); pos4 = getPos(nums[3]); pos5 = 0;
        }
        if(nums.length == 3){
            pos1 = getPos(nums[0]); pos2 = getPos(nums[1]); pos3 = getPos(nums[2]); pos4 = 0; pos5 = 0;
        }
        if(nums.length == 2){
            pos1 = getPos(nums[0]); pos2 = getPos(nums[1]); pos3 = 0; pos4 = 0; pos5 = 0;
        }

        if(mode == 1){
            if(veloMode == 1){
                //快速顺序
                sendBaseDataZidingyiKuaisu(pos1,pos2,pos3,pos4,pos5);
                sendPosition(1);
            }else{
                //慢速顺序
                sendBaseDataZidingyiMansu(pos1,pos2,pos3,pos4,pos5);
                sendPosition(0);
            }
        }else{
            if(veloMode == 1){
                //快速随机
                sendBaseDataZidingyiSuijiKuaisu(pos1,pos2,pos3,pos4,pos5);
                sendPosition(1);
            }else{
                //慢速随机
                sendBaseDataZidingyiSuijiMansu(pos1,pos2,pos3,pos4,pos5);
                sendPosition(0);
            }
        }

    }


    public static int[] stringToInts(String s){
        String[] numS = s.split(",");
        int[] n = new int[numS.length];

        for(int i = 0;i< numS.length;i++){

            n[i] = Integer.parseInt(numS[i]);

        }

        return n;
    }
    @Subcriber
    private void dealFaultDataEvent(ShowFaultMsg showFaultMsg) {
//        if(showFaultMsg.code != 0)
//        uploadFault(showFaultMsg.code);
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShowHelper.toastShort(this, "暂停发球。");
//        if(showFaultMsg.code == 1)
//        startActivity(new Intent(ModeSelectActivity.this, FaultDealActivity.class));
    }

    @Subcriber
    private void dealSelectEvent(SelectModeMsg selectModeMsg) {

        if(selectModeMsg.type != 100){
            this.type = selectModeMsg.type;
//            updateDevState();
        }
        if(selectModeMsg.type == ModeSelectActivity.KAQIU){
            sendKaqiu();
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOQIU) {
            selectName = "挑球";
            trainNums = 0;
            //挑球
//            if(selectModeMsg.position == 1)
//            sendBaseDataTiaoqiuLeft();
//            else sendBaseDataTiaoqiuRight();
            if(veloMode == 1){
                sendBaseDataTiaoqiu();
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(8, 9);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOZHENG) {
            selectName = "正手挑球";
            trainNums = 0;
//            sendBaseData(8, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(9, 1);
                sendPosition(1);
            }else{
                sendBaseData(9, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOFAN) {
            selectName = "反手挑球";
            trainNums = 0;
//            sendBaseData(4, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(8, 1);
                sendPosition(1);
            }else{
                sendBaseData(8, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PUQIU) {
            selectName = "扑球";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseData(0x05);
//            else sendBaseData(0x07);
            if(veloMode == 1){
                sendBaseDataPuqiu();
                sendPosition(1);
            }
            else{
                sendBaseDataTwoHouchang(9, 8);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始扑球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.PUHENG) {
            selectName = "正手扑球";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(9, 4);
                sendPosition(1);
            }else{
                sendBaseData(9, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手扑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PUFAN) {
            selectName = "反手扑球";
            trainNums = 0;
//            sendBaseData(5, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(8, 4);
                sendPosition(1);
            }else{
                sendBaseData(8, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手扑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.DINGDIANPUQIU) {
            selectName = "定点扑球";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(9, 4);
                sendPosition(1);
            }else{
                sendBaseData(9, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始定点扑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.ZUOCHANGJIEFA1) {
            selectName = "左场接发1";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(10, 1);
                sendPosition(1);
            }else{
                sendBaseData(10, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始左场接发1。");
        }
        if (selectModeMsg.type == ModeSelectActivity.ZUOCHANGJIEFA2) {
            selectName = "左场接发2";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(10, 1);
                sendPosition(1);
            }else{
                sendBaseData(10, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始左场接发2。");
        }
        if (selectModeMsg.type == ModeSelectActivity.YOUCHANGJIEFA1) {
            selectName = "右场接发1";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(12, 1);
                sendPosition(1);
            }else{
                sendBaseData(12, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始右场接发1。");
        }
        if (selectModeMsg.type == ModeSelectActivity.YOUCHANGJIEFA2) {
            selectName = "右场接发2";
            trainNums = 0;
//            sendBaseData(7, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(12, 1);
                sendPosition(1);
            }else{
                sendBaseData(12, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始右场接发2。");
        }
        if (selectModeMsg.type == ModeSelectActivity.CHOUQIU) {
            selectName = "正手抽球";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseData(10);
//            else
//            sendBaseData(13, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(15, 4);
                sendPosition(1);
            }else{
                sendBaseData(15, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手抽球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.DRIVEBALLBACK) {
            selectName = "反手抽球";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseData(10);
//            else
//            sendBaseData(9, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(13, 4);
                sendPosition(1);
            }else{
                sendBaseData(13, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手抽球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.SHAQIU_HOUCHANG) {
            selectName = "杀球";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseDataHouchangShaqiuLeft();
//            else sendBaseDataHouchangShaqiuRight();
//            sendBaseData(17, 3);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(23, 1);
                sendPosition(0);
            }else{
                sendBaseData(23, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始杀球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANGWANG) {
            selectName = "放网";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataGouqiuAndCuoqiu();
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(3, 1);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始放网。");
        }
        if (selectModeMsg.type == ModeSelectActivity.GOUQIU) {
            selectName = "勾对角";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseData(1);
//            else sendBaseData(3);

            if(veloMode == 1){
                sendBaseDataGouqiuAndCuoqiu();
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(3, 1);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始勾对角。");
        }

        if (selectModeMsg.type == ModeSelectActivity.GOUZHENG) {
            selectName = "正手勾对角";
            trainNums = 0;
//            sendBaseData(3, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(3, 1);
                sendPosition(1);
            }else{
                sendBaseData(3, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手勾对角。");
        }
        if (selectModeMsg.type == ModeSelectActivity.GOUFAN) {
            selectName = "反手勾对角";
            trainNums = 0;
//            sendBaseData(1, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(1, 1);
                sendPosition(1);
            }else{
                sendBaseData(1, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手勾对角。");
        }

        if (selectModeMsg.type == ModeSelectActivity.CUOQIU) {
            selectName = "搓球";
            trainNums = 0;
//            if(selectModeMsg.position == 1)
//                sendBaseData(4);
//            else sendBaseData(8);
            if(veloMode == 1){
                sendBaseDataGouqiuAndCuoqiu();
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(3, 1);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始搓球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.CUOZHENG) {
            selectName = "正手搓球";
            trainNums = 0;
//            sendBaseData(3, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(3, 1);
                sendPosition(1);
            }else{
                sendBaseData(3, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手搓球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.CUOFAN) {
            selectName = "反手搓球";
            trainNums = 0;
//            sendBaseData(1, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(1, 1);
                sendPosition(1);
            }else{
                sendBaseData(1, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手搓球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.FANGZHENG) {
            selectName = "正手放网";
            trainNums = 0;
//            sendBaseData(3, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(3, 1);
                sendPosition(1);
            }else{
                sendBaseData(3, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手放网。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANGFAN) {
            selectName = "反手放网";
            trainNums = 0;
//            sendBaseData(1, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(1, 1);
                sendPosition(1);
            }else{
                sendBaseData(1, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手放网。");
        }

        if (selectModeMsg.type == ModeSelectActivity.TUIQIU) {
            selectName = "推球";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(5);
//            else sendBaseData(7);
//            sendBaseDataTiaoqiu();
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(9, 8);
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(9, 8);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始推球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.TUIZHENG) {
            selectName = "正手推球";
            trainNums = 0;
//            sendBaseData(8, 1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(9, 1);
                sendPosition(1);
            }else{
                sendBaseData(9, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手推球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TUIFAN) {
            selectName = "反手推球";
            trainNums = 0;
//            sendBaseData(4, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(8, 1);
                sendPosition(1);
            }else{
                sendBaseData(8, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手推球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.JIEFAQIU) {
            //暂未使用
            selectName = "接发球";
            trainNums = 0;
            if (selectModeMsg.position == 1)
                sendBaseData(5, 1);
            else sendBaseData(7, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始接发球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.DANGQIU) {
            selectName = "正手挡网";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(9);
//            else
//            sendBaseData(13, 1);
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(13, 1);
                sendPosition(1);
            }else{
                sendBaseData(13, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手挡网。");
        }

        if (selectModeMsg.type == ModeSelectActivity.BLOCKBALLBACK) {
            selectName = "反手挡网";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(9);
//            else
//            sendBaseData(9,1);
////            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(9, 1);
                sendPosition(1);
            }else{
                sendBaseData(9, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手挡网。");
        }

        if (selectModeMsg.type == ModeSelectActivity.BANCHANGTUJI) {
            selectName = "半场突击";
            trainNums = 0;
//            sendBaseDataBanchangtuji();
//            sendPosition(1);
            if(veloMode == 1){
                sendBaseData(6, 1);
                sendPosition(1);
            }else{
                sendBaseData(6, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始半场突击。");
        }
        if (selectModeMsg.type == ModeSelectActivity.SHAQIU_ZHONGCHANG) {
            selectName = "正手接杀";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(19);
//            else
            sendBaseData(23, 1);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始正手接杀。");
        }
        if (selectModeMsg.type == ModeSelectActivity.SMASHBALLBACK) {
            selectName = "反手接杀";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(19);
//            else
            sendBaseData(19, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手接杀。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PIDIAO) {
            selectName = "正手吊球";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(24);
//            else
//            sendBaseData(18, 2);
////            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(23, 1);
                sendPosition(0);
            }else{
                sendBaseData(23, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手吊球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANSHOUDIAOQIU) {
            selectName = "反手吊球";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(24);
//            else
//            sendBaseData(14, 2);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(21, 1);
                sendPosition(0);
            }else{
                sendBaseData(21, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手吊球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.GAOYUANQIU) {
            selectName = "正手高远球";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(25);
//            else
//            sendBaseData(18, 2);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(23, 1);
                sendPosition(0);
            }else{
                sendBaseData(23, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手高远球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANSHOUGAOYUANQIU) {
            selectName = "反手高远球";
            trainNums = 0;
//            if (selectModeMsg.position == 1)
//                sendBaseData(25);
//            else
//            sendBaseData(14, 2);
////            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(21, 1);
                sendPosition(0);
            }else{
                sendBaseData(21, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手高远球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.PINGGAOQIU) {
            //已无
            selectName = "平高球";
            trainNums = 0;
            if (selectModeMsg.position == 1)
                sendBaseData(14, 1);
            else sendBaseData(18, 1);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始平高球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.MIZIBU) {
            selectName = "米字步";
            trainNums = 0;
            if(veloMode == 1){
            sendMiZiBuData();
                sendPosition(1);}
            else{
                sendMiZiBuDataMansu();
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始米字步。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHASHANGWANG){
            selectName = "正手被动过渡网前";
            trainNums = 0;
//            sendBaseData(28, 2);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(6, 1);
                sendPosition(0);
            }else{
                sendBaseData(6, 3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始正手被动过渡网前。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHANGSWZUHE){
            selectName = "杀上网";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataShaShangwang();
                sendPosition(1);
            }else{
                sendBaseDataShaShangwangMansu();
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始杀上网。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIAOSHANGZHI){
            selectName = "吊上网直线";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(31,30);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(31,30);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始吊上网直线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIAOSHANGDUIZUO){
            selectName = "吊上网左斜线";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(33,32);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(33,32);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始吊上网左斜线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIAOSHANGDUIYOU){
            selectName = "吊上网右斜线";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(35,34);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(35,34);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始吊上网右斜线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHASHANGDUIZUO){
            selectName = "杀上网左斜线";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(33,32);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(33,32);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始杀上网左斜线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHASHANGDUIYOU){
            selectName = "杀上网右斜线";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(35,34);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(35,34);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始杀上网右斜线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIJIAOCEXIANG){
            selectName = "杀球组合";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(23,21);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(23,21);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始杀球组合。");
        }
        if(selectModeMsg.type == ModeSelectActivity.GAOYUANDIAOQIU){
            selectName = "高远球";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(23,21);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(23,21);
                sendPosition(0);
            }

            ShowHelper.toastShort(this, "开始高远球。");
        }

        if(selectModeMsg.type == ModeSelectActivity.ZHONGCHANGCEXIANG){
            selectName = "半场突击(组合)";
            trainNums = 0;
//            sendBaseDataTwoHouchang(9,13);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(6,4);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(6,4);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始半场突击。");
        }
        if(selectModeMsg.type == ModeSelectActivity.CHOUQIUZUHE){
            selectName = "抽球(组合)";
            trainNums = 0;
//            sendBaseDataTwoHouchang(9,13);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(15,13);
                sendPosition(0);
            }else{
                sendBaseDataTwoHouchangMansu(15,13);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始抽球。");
        }
        if(selectModeMsg.type == ModeSelectActivity.CAIDAN){
            selectName = "彩蛋挑战";
//            trainNums = 0;
            sendBaseDataTwoHouchang(9,13);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始彩蛋挑战。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SIFANGQIU){
            selectName = "四方球";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSifangqiu();
                sendPosition(0);
            }else{
                sendBaseDataSifangqiuMansu();
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始四方球。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SUIJISIFANGQIU){
            selectName = "随机四方球";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiSifangqiu();
                sendPosition(1);
            }else{
                sendBaseDataSuijiSifangqiuMansu();
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始随机四方球。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHUANGCESHASHANGWANG){
            //已无
            selectName = "两侧杀上网";
            trainNums = 0;
//            sendBaseDataShuangceSSW();
//            sendPosition(1);
                        if(veloMode == 1){
                            sendBaseDataShuangceSSW();
                sendPosition(1);
            }else{
                sendBaseDataShuangceSSWMansu();
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "两侧杀上网。");
        }
        if(selectModeMsg.type == ModeSelectActivity.ZUOCHANGJIEFA){
            selectName = "左场接发";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(17, 19);
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(17, 19);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始左场接发。");
        }
        if(selectModeMsg.type == ModeSelectActivity.YOUCHANGJIEFA){
            selectName = "右场接发";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataTwoKuaisu(24, 25);
                sendPosition(1);
            }else{
                sendBaseDataTwoHouchang(24, 25);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始右场接发。");
        }

        if(selectModeMsg.type == ModeSelectActivity.SUIJI1){
            selectName = "前场随机";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiTwoPosition(3,1);
                sendPosition(1);
            }else{
                sendBaseDataSuijiTwoPositionMansu(3,1);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始前场随机。");
        }

        if(selectModeMsg.type == ModeSelectActivity.SUIJI2){
            selectName = "中前场随机";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiTwoPosition(8,9);
                sendPosition(1);
            }else{
                sendBaseDataSuijiTwoPositionMansu(8,9);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始中前场随机。");
        }

        if(selectModeMsg.type == ModeSelectActivity.SUIJI3){
            selectName = "中场随机";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiTwoPosition(15, 13);
                sendPosition(1);
            }else{
                sendBaseDataSuijiTwoPositionMansu(15, 13);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始中场随机。");
        }

        if(selectModeMsg.type == ModeSelectActivity.SUIJI4){
            selectName = "中后场随机";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiTwoPosition(4, 6);
                sendPosition(1);
            }else{
                sendBaseDataSuijiTwoPositionMansu(4, 6);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始中后场随机。");
        }

        if(selectModeMsg.type == ModeSelectActivity.SUIJI5){
            selectName = "后场随机";
            trainNums = 0;
            if(veloMode == 1){
                sendBaseDataSuijiTwoPosition(23, 21);
                sendPosition(1);
            }else{
                sendBaseDataSuijiTwoPositionMansu(23, 21);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始后场随机。");
        }

        if(selectModeMsg.type == ModeSelectActivity.ZUOXIEXIAN){
            selectName = "反手被动过渡网前";
            trainNums = 0;
//            sendBaseData(24, 2);
//            sendPosition(0);
            if(veloMode == 1){
                sendBaseData(4,1);
                sendPosition(0);
            }else{
                sendBaseData(4,3);
                sendPosition(0);
            }
            ShowHelper.toastShort(this, "开始反手被动过渡网前。");
        }
        if(selectModeMsg.type == ModeSelectActivity.YOUXIEXIAN){
            sendYouXieXianData();
            sendPosition(0);
            ShowHelper.toastShort(this, "开始右斜线。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIYIGUAN){
            selectName = "第一关";
            trainNums = 0;
            sendBaseData(2, 1);
//            sendBaseDataBanchangtuji();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始第一关。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIERGUAN){
            selectName = "第二关";
            trainNums = 0;
            sendBaseData(7, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始第二关。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DISANGUAN){
            selectName = "第三关";
            trainNums = 0;
            sendBaseDataTwoKuaisu(7,2);
            sendPosition(1);
//            sendBaseData(17, 2);
//            sendPosition(0);
            ShowHelper.toastShort(this, "开始第三关。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DISIGUAN){
            selectName = "第四关";
            trainNums = 0;
            sendBaseData(11, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始第四关。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIWUGUAN){
            selectName = "第五关";
            trainNums = 0;
            sendBaseData(12, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始第五关。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DILIUGUAN){
            selectName = "第六关";
            trainNums = 0;
            sendBaseDataTwoHouchang(12,11);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始第六关。");
        }
        if (selectModeMsg.type == 100) {
//            SaveTeachRecord();
            selectName = "暂停";
            trainNums = 0;
            byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
            try {
                mOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ShowHelper.toastShort(this, "暂停发球。");
            return;
        }
//        startActivity(new Intent(ModeSelectActivity.this, CountDownActivity.class));
    }

    /**
     * 判断当前网络是否为 Wifi 网络连接
     * @param context
     * @return
     */
    private static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    private void updateDevState() {

        //更新后台设备状态
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("devNum", url);
        params2.put("DeviceState", "" + 1);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetDevState", params2, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateDevBattery(final int quantity) {

//        if(quantity> 0 && quantity <= 20) power.setImageResource(R.drawable.power1);
//        if(quantity> 20 && quantity <= 40) power.setImageResource(R.drawable.power2);
//        if(quantity> 40 && quantity <= 60) power.setImageResource(R.drawable.power3);
//        if(quantity> 60 && quantity <= 80) power.setImageResource(R.drawable.power4);
//        if(quantity> 80 && quantity <= 100) power.setImageResource(R.drawable.power5);
//        if(quantity == 0) power.setImageResource(R.drawable.power_zero);
//
//        //更新后台设备状态
//        HashMap<String, String> params2 = new HashMap<>();
//        params2.put("devNum", url);
//        params2.put("DeviceBattery", "" + quantity);
//        ShowHelper.toastShort(ModeSelectActivity.this, "电池电量：" + quantity);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetDevState", params2, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
////                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ModeSelectActivity.this, ""+ quantity, Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void updatePickBattery(int quantity) {

        //更新后台设备状态
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("devNum", url);
        params2.put("PickBattery", "" + quantity);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetDevState", params2, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = 1;
//        ijkVideoView.resume();
        checkPower();
    }
    @Override
    protected void onPause() {
        super.onPause();
        isResume = 0;
//        ijkVideoView.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=timeCount)
        timeCount.cancel();
        //停止发球
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(null!=timeCount1)
//        timeCount1.cancel();
        EventBus.getDefault().unregister(this);
//        ijkVideoView.release();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {

        AppLog.e("bffer1:" + (buffer[1] & 0xFF));
//        ShowChuankouMsg showChuankouMsg = new ShowChuankouMsg();
//        showChuankouMsg.code1 = buffer[1] & 0xFF;
//        showChuankouMsg.code2 = buffer[2] & 0xFF;
//        EventBus.getDefault().post(showChuankouMsg);
        if((buffer[1] & 0xFF) == 0x21){

            byte[] buf = new byte[]{(byte)0xAA, (byte)0x57, (byte)(28), (byte)(0),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x57^0^0^0^0^0^0), (byte)0xA5};

            try {
                mOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if((buffer[1] & 0xFF) == 0x22){
            RequestDataMsg requestDataMsg = new RequestDataMsg();
            EventBus.getDefault().post(requestDataMsg);
//            sendBaseData();
        }

        if((buffer[1] & 0xFF) == 0x10){
            trainNums++;
//            ShowChuankouMsg showChuankouMsg = new ShowChuankouMsg();
//            showChuankouMsg.code1 = buffer[1] & 0xFF;
//            showChuankouMsg.code2 = buffer[2] & 0xFF;
//            EventBus.getDefault().post(showChuankouMsg);
        }

        if((buffer[1] & 0xFF) == 0x23){

            alertMsg(buffer[1] & 0xFF);
        }
        if((buffer[1] & 0xFF) == 0x27){

            remoteControlMsg(buffer[2] & 0xFF);
        }
        if((buffer[1] & 0xFF) == 0x25){
            alertNoBallsMsg(buffer[1] & 0xFF);
        }

        //急停暂停
        if((buffer[1] & 0xFF) == 0x24){
            byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
            try {
                mOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StopEventMsg stopEventMsg = new StopEventMsg();
            EventBus.getDefault().post(stopEventMsg);
        }

        if((buffer[1] & 0xFF) == 0xe5){
            //显示报错信息
//            if((buffer[2] &0xFF) == 0x01)
//            faultMsg(true, 0x01);
//            else faultMsg(false, (buffer[2] &0xFF));
        }

        //主机电量
        if((buffer[1] & 0xFF) == 0x03){
            batteryVolumeMsg(buffer[2] &0xFF);
//            updateDevBattery(buffer[2] &0xFF);
        }
        if((buffer[1] & 0xFF) == 0x60){
            alertPadControlMsg();
        }

        //捡球机电量
        if((buffer[1] & 0xFF) == 0x04){
//            updatePickBattery(buffer[2] &0xFF);
        }

        //超声波距离
        if((buffer[1] & 0xFF) == 0x05){
            int dis = buffer[2] &0xFF;
            if(dis <= 150) enterDevice();
        }
    }

    private void batteryVolumeMsg(int volume) {

        BatteryVolumeMsg batteryVolumeMsg = new BatteryVolumeMsg();
        batteryVolumeMsg.volume = volume;
        EventBus.getDefault().post(batteryVolumeMsg);

    }

    private void faultMsg(boolean isShow, int code) {
        if(isShow){
            ShowFaultMsg showFaultMsg = new ShowFaultMsg();
            showFaultMsg.code = code;
            EventBus.getDefault().post(showFaultMsg);
        }
//        uploadFault(code);
    }
    private void alertPadControlMsg() {

        PadControlMsg padControlMsg = new PadControlMsg();
        EventBus.getDefault().post(padControlMsg);
    }
    private void uploadFault(int code) {

        HashMap<String, String> params = new HashMap<>();
        params.put("devNum", url);
        params.put("devresID", "" + code);
        params.put("reson", "");
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/FaultReport", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //更新后台设备状态
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("devNum", url);
        params2.put("DeviceState", "" + 0);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetDevState", params2, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void alertMsg(int code) {

        ShowAlertNearMsg showAlertNearMsg = new ShowAlertNearMsg();
        showAlertNearMsg.msgCode = code;
        EventBus.getDefault().post(showAlertNearMsg);
    }

    private void remoteControlMsg(int code) {

        RemoteControllerMsg remoteControllerMsg = new RemoteControllerMsg();
        remoteControllerMsg.msgCode = code;
        EventBus.getDefault().post(remoteControllerMsg);
    }

    private void alertNoBallsMsg(int code) {

        ShowNoBallsMsg showNoBallsMsg = new ShowNoBallsMsg();
        showNoBallsMsg.msgCode = code;
        EventBus.getDefault().post(showNoBallsMsg);
    }

    private void sendBaseData() {
//        if(url.equals("SZPUWEI003") || url.equals("SZHONGHL001") || url.equals("SZBENPAO002")|| url.equals("DEV004")){
//            for(int i = 0; i < BasicData.b.length; i ++){
////            for(int i = 0; i < 9; i ++){
//                byte check_sum = (byte)((byte)(i + 1)^(byte)(BasicData.b[i][0] >> 8)^(byte)(BasicData.b[i][0] >> 0)^(byte)(BasicData.b[i][1] >> 8)^(byte)(BasicData.b[i][1] >> 0)^(byte)(BasicData.b[i][2] >> 8)^(byte)(BasicData.b[i][2] >> 0));
//                final byte[] buf = new byte[]{(byte)0xAA, (byte)(i + 1), (byte)(BasicData.b[i][0] >> 8), (byte)(BasicData.b[i][0] >> 0),  (byte)(BasicData.b[i][1] >> 8), (byte)(BasicData.b[i][1] >> 0), (byte)(BasicData.b[i][2] >> 8), (byte)(BasicData.b[i][2] >> 0), check_sum, (byte)0xA5};
//                final int n = i;
//                AppLog.e("左右：" + (int) BasicData.b[i][0]  + "上下：" + (int) BasicData.b[i][1] + "速度：" + (int) BasicData.b[i][2] );
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public synchronized void run() {
//
//                        try {
//                            AppLog.e("第" + n+ "条指令");
//                            mOutputStream.write(buf);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 1*i);
//            }
//        }else if(url.equals("DEV010")){
//            for(int i = 0; i < BasicData.h.length; i ++){
////            for(int i = 0; i < 9; i ++){
//                byte check_sum = (byte)((byte)(i + 1)^(byte)(BasicData.h[i][0] >> 8)^(byte)(BasicData.h[i][0] >> 0)^(byte)(BasicData.h[i][1] >> 8)^(byte)(BasicData.h[i][1] >> 0)^(byte)(BasicData.h[i][2] >> 8)^(byte)(BasicData.h[i][2] >> 0));
//                final byte[] buf = new byte[]{(byte)0xAA, (byte)(i + 1), (byte)(BasicData.h[i][0] >> 8), (byte)(BasicData.h[i][0] >> 0),  (byte)(BasicData.h[i][1] >> 8), (byte)(BasicData.h[i][1] >> 0), (byte)(BasicData.h[i][2] >> 8), (byte)(BasicData.h[i][2] >> 0), check_sum, (byte)0xA5};
//                final int n = i;
//                AppLog.e("左右：" + (int) BasicData.h[i][0]  + "上下：" + (int) BasicData.h[i][1] + "速度：" + (int) BasicData.h[i][2] );
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public synchronized void run() {
//
//                        try {
//                            AppLog.e("第" + n+ "条指令");
//                            mOutputStream.write(buf);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 1*i);
//            }
//        }else {
        if(categary == 1){
            for (int i = 0; i < BasicData.b2.length; i++) {
//            for(int i = 0; i < 9; i ++){
                byte check_sum = (byte) ((byte) (i + 1) ^ (byte) (BasicData.b2[i][0] >> 8) ^ (byte) (BasicData.b2[i][0] >> 0) ^ (byte) (BasicData.b2[i][1] >> 8) ^ (byte) (BasicData.b2[i][1] >> 0) ^ (byte) (BasicData.b2[i][2] >> 8) ^ (byte) (BasicData.b2[i][2] >> 0));
                final byte[] buf = new byte[]{(byte) 0xAA, (byte) (i + 1), (byte) (BasicData.b2[i][0] >> 8), (byte) (BasicData.b2[i][0] >> 0), (byte) (BasicData.b2[i][1] >> 8), (byte) (BasicData.b2[i][1] >> 0), (byte) (BasicData.b2[i][2] >> 8), (byte) (BasicData.b2[i][2] >> 0), check_sum, (byte) 0xA5};
                final int n = i;
                AppLog.e("左右：" + (int) BasicData.b2[i][0] + "上下：" + (int) BasicData.b2[i][1] + "速度：" + (int) BasicData.b2[i][2]);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {

                        try {
                            AppLog.e("第" + n + "条指令");
                            mOutputStream.write(buf);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1 * i);
            }
        }else{
                for (int i = 0; i < BasicData.h.length; i++) {
//            for(int i = 0; i < 9; i ++){
                    byte check_sum = (byte) ((byte) (i + 1) ^ (byte) (BasicData.h[i][0] >> 8) ^ (byte) (BasicData.h[i][0] >> 0) ^ (byte) (BasicData.h[i][1] >> 8) ^ (byte) (BasicData.h[i][1] >> 0) ^ (byte) (BasicData.h[i][2] >> 8) ^ (byte) (BasicData.h[i][2] >> 0));
                    final byte[] buf = new byte[]{(byte) 0xAA, (byte) (i + 1), (byte) (BasicData.h[i][0] >> 8), (byte) (BasicData.h[i][0] >> 0), (byte) (BasicData.h[i][1] >> 8), (byte) (BasicData.h[i][1] >> 0), (byte) (BasicData.h[i][2] >> 8), (byte) (BasicData.h[i][2] >> 0), check_sum, (byte) 0xA5};
                    final int n = i;
                    AppLog.e("左右：" + (int) BasicData.h[i][0] + "上下：" + (int) BasicData.h[i][1] + "速度：" + (int) BasicData.h[i][2]);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {

                            try {
                                AppLog.e("第" + n + "条指令");
                                mOutputStream.write(buf);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1 * i);
                }
            }

//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_train_single:
                back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_selected));
                tv_single.setTextColor(Color.WHITE);
                back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
                tv_group.setTextColor(getResources().getColor(R.color.mode_blue));
//                back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
//                info_game.setVisibility(View.GONE);
                mViewPager.setCurrentItem(0);
                state = 0;
                break;
            case R.id.back_train_group:
                back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_default));
                tv_single.setTextColor(getResources().getColor(R.color.mode_blue));
                back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_selected));
                tv_group.setTextColor(Color.WHITE);
//                back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
                mViewPager.setCurrentItem(1);
                info_game.setVisibility(View.GONE);
                state = 1;
                break;
//            case R.id.back_game_group:
//                back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_selected));
//                tv_game.setTextColor(Color.WHITE);
//                back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_default));
//                tv_single.setTextColor(getResources().getColor(R.color.mode_blue));
//                back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_middle_default));
//                tv_group.setTextColor(getResources().getColor(R.color.mode_blue));
//                mViewPager.setCurrentItem(2);
//                info_game.setVisibility(View.GONE);
//                state = 2;
//                break;
            default: break;
        }
    }

    /**
     * 验证码定时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            App.saveUserPreference("duration", 0l);
//            timeCount.cancel();
            ModeSelectActivity.this.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            App.saveUserPreference("duration", millisUntilFinished/1000);
            if(  millisUntilFinished-600*1000 < 0){
                showTip();
            }
            if( millisUntilFinished-30*1000< 0){
                if(isTwo){
                    isTwo = false;
                    alertShowTwo();
                }else{
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

            if(picNum != 0){
                if(MainActivity.isRecording == 0){
//                    startService(intent);
                    CameraTwoStartMsg startRecordMsg = new CameraTwoStartMsg();
                    startRecordMsg.code = 1;
                    EventBus.getDefault().post(startRecordMsg);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public synchronized void run() {
//                            stopService(intent);
                            StopRecordMsg stopRecordMsg = new StopRecordMsg();
                            EventBus.getDefault().post(stopRecordMsg);
                        }
                    }, 60000);
                }
            }
            picNum ++;
        }

    }

    private void alertShowTwo() {
        backDevice();
        ShowTwoMinAlertMsg showTwoMinAlertMsg = new ShowTwoMinAlertMsg();
        EventBus.getDefault().post(showTwoMinAlertMsg);
        SelectModeMsg selectMsg = new SelectModeMsg();
        selectMsg.setType(100);
        selectMsg.setPosition(1);
        EventBus.getDefault().post(selectMsg);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
//                startActivity(new Intent(ModeSelectActivity.this, TwoMinAlertActivity.class));
                ModeSelectActivity.this.finish();
            }
        }, 800);

    }

    private void showTip() {

        ShowTipEventMsg showTipEventMsg = new ShowTipEventMsg();
        EventBus.getDefault().post(showTipEventMsg);

    }

    private void backDevice() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x6A, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x6A ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterDevice() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x69, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x69 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDeviceDistance() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x6C, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x6C ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void endUseDevice() {
//        CloseTwoMinAlertMsg closeTwoMinAlertMsg = new CloseTwoMinAlertMsg();
//        EventBus.getDefault().post(closeTwoMinAlertMsg);
//        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
//        try {
//            mOutputStream.write(buf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        StopEventMsg stopEventMsg = new StopEventMsg();
//        EventBus.getDefault().post(stopEventMsg);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("devNum", url);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetNoStart", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                timeCount.cancel();
//                finish();
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    timeCount.cancel();
//                    finish();
//                } else {
//                    Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                    timeCount.cancel();
//                    finish();
//                }
//            }
//        });
//    }

    private void checkPower() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x67, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x67 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int kCode,KeyEvent kEvent)
    {
        ShowHelper.toastShort(ModeSelectActivity.this, "Keycode:" + kCode);
        switch(kCode)
        {
            case 0:  //电源键
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(100);
                selectMsg.setPosition(1);
                EventBus.getDefault().post(selectMsg);
//                startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StopEventMsg stopEventMsg = new StopEventMsg();
                        EventBus.getDefault().post(stopEventMsg);
//                                startGameEgg();
                    }
                }, 1000);
                return true;

            case KeyEvent.KEYCODE_BACK:
                SelectModeMsg selectMsg2 = new SelectModeMsg();
                selectMsg2.setType(100);
                selectMsg2.setPosition(1);
                EventBus.getDefault().post(selectMsg2);
                return super.onKeyDown(kCode, kEvent);
//                return true;

            case KeyEvent.KEYCODE_MENU:
//                if(state == 1){
//                    state = 0;
//                    back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_selected));
//                    tv_single.setTextColor(Color.WHITE);
//                    back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_middle_default));
//                    tv_group.setTextColor(getResources().getColor(R.color.mode_blue));
//                    back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                    tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
//                    info_game.setVisibility(View.GONE);
//                    mViewPager.setCurrentItem(0);
//                }else{
//                    state = 1;
//                    back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_default));
//                    tv_single.setTextColor(getResources().getColor(R.color.mode_blue));
//                    back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_middle_selected));
//                    tv_group.setTextColor(Color.WHITE);
//                    back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                    tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
//                    mViewPager.setCurrentItem(1);
//                    info_game.setVisibility(View.GONE);
//            }
//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=menu");
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=left");
                KeySelectMsg keySelectMsg = new KeySelectMsg();
                keySelectMsg.num = -1;
                keySelectMsg.state = state;
                EventBus.getDefault().post(keySelectMsg);
                return true;

            case KeyEvent.KEYCODE_DPAD_UP:
//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=up");
//                KeySelectMsg keySelectMsg2 = new KeySelectMsg();
//                keySelectMsg2.num = -4;
//                keySelectMsg2.state = state;
//                EventBus.getDefault().post(keySelectMsg2);

                if(state == 1){
                    state = 0;
                    back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_selected));
                    tv_single.setTextColor(Color.WHITE);
                    back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
                    tv_group.setTextColor(getResources().getColor(R.color.mode_blue));
//                    back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                    tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
//                    info_game.setVisibility(View.GONE);
                    mViewPager.setCurrentItem(0);
                }else{
                    state = 1;
                    back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_default));
                    tv_single.setTextColor(getResources().getColor(R.color.mode_blue));
                    back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_selected));
                    tv_group.setTextColor(Color.WHITE);
//                    back_game_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_default));
//                    tv_game.setTextColor(getResources().getColor(R.color.mode_blue));
                    mViewPager.setCurrentItem(1);
//                    info_game.setVisibility(View.GONE);
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=right");
                KeySelectMsg keySelectMsg3 = new KeySelectMsg();
                keySelectMsg3.num = 1;
                keySelectMsg3.state = state;
                EventBus.getDefault().post(keySelectMsg3);
                return true;

            case KeyEvent.KEYCODE_DPAD_DOWN:
//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=down");
//                KeySelectMsg keySelectMsg4 = new KeySelectMsg();
//                keySelectMsg4.num = 4;
//                keySelectMsg4.state = state;
//                EventBus.getDefault().post(keySelectMsg4);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:

                KeyStartMsg keyStartMsg = new KeyStartMsg();
                keyStartMsg.state = state;
                EventBus.getDefault().post(keyStartMsg);
//                Intent intent = new Intent(ModeSelectActivity.this, CountDownActivity.class);
//                intent.putExtra("state", 1);
//                intent.putExtra("type", type);
//                startActivity(intent);

//                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=center");
                return true;
//            case KeyEvent.KEYCODE_VOLUME_UP:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=VOLUME_UP");
//                return true;

//            case KeyEvent.KEYCODE_BACK:
////                ShowHelper.toastShort(ModeSelectActivity.this, "onkeydown=where");
//                return false;
            case 264:    //鼠标模式
                return false;
        }
        return super.onKeyDown(kCode,kEvent);
    }

//    private void SaveTeachRecord() {
//
//        if(!selectName.equals("暂停")){
//            HashMap<String, String> params = new HashMap<>();
//            params.put("userId", userBookInfo.getUserId());
//            params.put("shotBallCount", trainNums + "");
//            params.put("devNumber", url);
//            params.put("techingName", selectName);
//            MyHttpUtils.postAsAync(App.BASE_URL + "/api/SaveTeachRecord", params, new MyJsonCallbalk() {
//                @Override
//                public void onError(Exception e, int code) {
//                    AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                }
//                @Override
//                public void onResponse(RequestCallVo requestCallVo) {
//                    AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                    if (requestCallVo.getType() == 1) {
//                        Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//        }
//
//    }

//    private void checkGameEgg() {
//
//            HashMap<String, String> params = new HashMap<>();
//            params.put("UserId", userBookInfo.getUserId());
//            params.put("GameName", "PoundsEgg");
////            params.put("devNumber", url);
////            params.put("techingName", selectName);
//            MyHttpUtils.postAsAync(App.BASE_URL + "/api/IsStandardControl", params, new MyJsonCallbalk() {
//                @Override
//                public void onError(Exception e, int code) {
//                    AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                }
//                @Override
//                public void onResponse(RequestCallVo requestCallVo) {
//                    AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                    if (requestCallVo.getType() == 1) {
//                        SelectModeMsg selectMsg = new SelectModeMsg();
//                        selectMsg.setType(100);
//                        selectMsg.setPosition(1);
//                        EventBus.getDefault().post(selectMsg);
//                        startActivity(new Intent(ModeSelectActivity.this, EggInformActivity.class));
//                        (new Handler()).postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                StopEventMsg stopEventMsg = new StopEventMsg();
//                                EventBus.getDefault().post(stopEventMsg);
////                                startGameEgg();
//                            }
//                        }, 1000);
//                            } else {
//                                Toast.makeText(ModeSelectActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//    }

//    private void startGameEgg() {
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("UserId", userBookInfo.getUserId());
//        params.put("gameName", "PoundsEgg");
////            params.put("devNumber", url);
////            params.put("techingName", selectName);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/StarGame", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//            }
//        });
//
//    }
private class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    String[] items = new String[]{};

    public SpinnerAdapter(final Context context, final int textViewResourceId, final String[] objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(items[position]);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(26);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);
        }         // android.R.id.text1 is default text view in resource of the android.
        // android.R.layout.simple_spinner_item is default layout in resources of android.
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(items[position]);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(28);
        return convertView;
    }
}
}
