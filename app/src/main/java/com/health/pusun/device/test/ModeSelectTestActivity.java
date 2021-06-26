package com.health.pusun.device.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.PeopleAlertActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.SerialPortActivity;
import com.health.pusun.device.TwoMinAlertActivity;
import com.health.pusun.device.VideoPlayActivity;
import com.health.pusun.device.VideoSerivice;
import com.health.pusun.device.adapter.ModeCategaryAdapter;
import com.health.pusun.device.adapter.ModeCategaryTestAdapter;
import com.health.pusun.device.application.App;
import com.health.pusun.device.train.GroupTrainFragment;
import com.health.pusun.device.train.SingleTrainFragment;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.BasicData;
import com.health.pusun.device.utils.DateUtil;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.views.AdController;
import com.health.pusun.device.views.ControllerListener;
import com.health.pusun.device.views.ListIjkVideoView;
import com.health.pusun.device.views.NoScrollViewPager;
import com.health.pusun.device.vo.ActivityInfoVo;
import com.health.pusun.device.vo.RequestCallVo;
import com.health.pusun.device.vo.UserBookInfo;
import com.health.pusun.device.vo.VideoModel;
import com.health.pusun.device.vo.eventbusmsg.RequestDataMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowAlertNearMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowNoBallsMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTipEventMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;
import com.health.pusun.device.vo.eventbusmsg.StopEventMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ModeSelectTestActivity extends SerialPortActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private NoScrollViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private ModeCategaryTestAdapter modeCategaryAdapter;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    private LinearLayout back_train_single, back_train_group;
//    private UserBookInfo userBookInfo;
    private ImageView head_img;
    private TextView tv_group, tv_single;
    private TextView nick_name, time_start, time_end;
    private TimeCount timeCount;
    private TextView activity;
    private String url;
    Intent intent;
    private VideoView ijkVideoView;
    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";

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
    public static final int WANGQIANCEXIANG = 1020;
    public static final int BANCHANGTUJI = 1021;
    public static final int DRIVEBALLBACK = 1022;
    public static final int BLOCKBALLBACK = 1023;
    public static final int SMASHBALLBACK = 1024;
    public static final int FANSHOUGAOYUANQIU = 1025;
    public static final int FANSHOUDIAOQIU = 1026;
    public static final int SHANGSWZUHE = 1027;
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
    public static final int CAIDAN = 9999;
    private int isResume = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select_test);
        mViewPager = (NoScrollViewPager) findViewById(R.id.fragmentPager);
        mViewPager.setNoScroll(true);
        EventBus.getDefault().register(this);
//        userBookInfo = (UserBookInfo) getIntent().getSerializableExtra("user");
//        AppLog.e("url:" + userBookInfo.getHeadImg());
        intent = new Intent(ModeSelectTestActivity.this, VideoSerivice.class);
        url = App.getStringUserPreference("name");
        head_img = (ImageView) findViewById(R.id.head_img);
        nick_name = (TextView) findViewById(R.id.nick_name);
        time_start = (TextView) findViewById(R.id.time_start);
        activity = findViewById(R.id.activity);
        time_end = findViewById(R.id.time_end);
        tv_single = findViewById(R.id.tv_single);
        tv_group = findViewById(R.id.tv_group);
        mTabs = new ArrayList<Fragment>();
        SingleTrainFragment myFragment1 = new SingleTrainFragment();
        GroupTrainFragment myFragment2 = new GroupTrainFragment();
        mTabs.add(myFragment1);
        mTabs.add(myFragment2);
        modeCategaryAdapter = new ModeCategaryTestAdapter(this, getSupportFragmentManager(), mTabs);
        mViewPager.setAdapter(modeCategaryAdapter);

        //表示homeFragment左右预加载几个
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setOnPageChangeListener(this);
        back_train_single = (LinearLayout) findViewById(R.id.back_train_single);
        back_train_single.setOnClickListener(this);
        back_train_group = (LinearLayout) findViewById(R.id.back_train_group);
        back_train_group.setOnClickListener(this);
//        if(null != userBookInfo.getHeadImg()){
//            String url;
//            if(!userBookInfo.getHeadImg().contains("http")) url = App.BASE_URL + userBookInfo.getHeadImg();
//            else url = userBookInfo.getHeadImg();
//            AppLog.e("url:" + url);
//            Glide.with(this).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(head_img) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(ModeSelectTestActivity.this.getResources(), resource);
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

//        timeCount = new TimeCount(20* 60 * 60 * 1000, 10000);
//        timeCount.start();


        ijkVideoView = findViewById(R.id.player);
//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        ijkVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels / 16 * 9));

        List<VideoModel> videos = new ArrayList<>();
        AdController adController = new AdController(this);
        adController.setControllerListener(new ControllerListener() {
            @Override
            public void onAdClick() {
                Toast.makeText(ModeSelectTestActivity.this, "广告点击跳转", Toast.LENGTH_SHORT).show();
            }
        });
//        videos.add(new VideoModel(URL_AD, "广告", adController, true));
//        videos.add(new VideoModel(Uri.fromFile(new File(URL_LOCAL)).toString(), "广告1", new StandardVideoController(this), true));
//        videos.add(new VideoModel(Uri.fromFile(new File(URL_LOCAL)).toString(), "广告1", new StandardVideoController(this), false));
////        videos.add(new VideoModel(URL_AD, "广告2", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_AD3, "广告3", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_VOD, "这是一个标题", new StandardVideoController(this), false));
//        ijkVideoView.setVideos(videos);
//        videos.get(0).controller.setVisibility(View.GONE);
//        ijkVideoView.setLooping(true);
//        ijkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
////        PlayerConfig playerConfig = new PlayerConfig.Builder().build();
////        playerConfig.isLooping = true;
////        ijkVideoView.setPlayerConfig(playerConfig);
//        ijkVideoView.start();
        enterDevice();
//        getVenueActivityInfo();
    }

    private void getVenueActivityInfo() {

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

    }

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

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(8), (byte)(4),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^8^4^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataPuqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(7), (byte)(5),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^7^5^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendQianchangOtherqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(3), (byte)(1),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^8^4^0^0^0^0), (byte)0xA5};
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

    private void sendPosition(final int front){
        if(front != 0&&0 != App.getIntUserPreference("high") && 0 != App.getIntUserPreference("middle") && 0 != App.getIntUserPreference("low")){

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        short a1 = (short)App.getIntUserPreference("high");
                        short a2 = (short)App.getIntUserPreference("middle");
                        short a3 = (short)App.getIntUserPreference("low");
                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                        AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
                        mOutputStream.write(buf);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 6000);
        }

        if(front == 0&&0 != App.getIntUserPreference("highback") && 0 != App.getIntUserPreference("middleback") && 0 != App.getIntUserPreference("lowback")){
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        short a1 = (short)App.getIntUserPreference("highback");
                        short a2 = (short)App.getIntUserPreference("middleback");
                        short a3 = (short)App.getIntUserPreference("lowback");
                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                        AppLog.e("highback:" + a1 + " middleback:" + a2 + " lowback:" + a3);
                        mOutputStream.write(buf);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 6000);

        }

    }

    private void sendBaseData(int position){
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9A, (byte)(position), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x9A^position^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
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

    private void sendBaseDataGouqiuAndCuoqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(3), (byte)(1),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^3^1^0^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataTwoHouchang(int position1, int position2) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(position1), (byte)(position2),  (byte)(2), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^position1^position2^2^0^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataSifangqiu() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(2), (byte)(4),  (byte)(14), (byte)(18), (byte)(8), (byte)(0), (byte)(0x92^2^4^14^18^8^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataShuangceSSW() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x92, (byte)(1), (byte)(17),  (byte)(8), (byte)(15), (byte)(4), (byte)(0), (byte)(0x92^1^17^8^15^4^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBaseDataJiefaqiu(int position1, int position2, int position3) {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x90, (byte)(1), (byte)(position1),  (byte)(position2), (byte)(position3), (byte)(0), (byte)(0), (byte)(0x90^1^position1^position2^position3^0^0), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void sendBaseDataShaShangwang() {

        byte[] buf = new byte[]{(byte)0xAA, (byte)0x9B, (byte)(17), (byte)(8),  (byte)(0), (byte)(0), (byte)(0), (byte)(0), (byte)(0x9B^17^8^0^0^0^0), (byte)0xA5};
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

    @Subcriber
    private void dealSelectEvent(SelectModeMsg selectModeMsg) {

        if(selectModeMsg.type != 100){
//            updateDevState();
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOQIU) {
            //挑球
//            if(selectModeMsg.position == 1)
//            sendBaseDataTiaoqiuLeft();
//            else sendBaseDataTiaoqiuRight();
            sendBaseDataTiaoqiu();
//            sendPositionData(0,4,1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOZHENG) {
            sendBaseData(8, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TIAOFAN) {
            sendBaseData(4, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手挑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PUQIU) {
//            if(selectModeMsg.position == 1)
//                sendBaseData(0x05);
//            else sendBaseData(0x07);
            sendBaseDataPuqiu();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始扑球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.PUHENG) {
            sendBaseData(7, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手扑球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PUFAN) {
            sendBaseData(5, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手扑球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.CHOUQIU) {
//            if(selectModeMsg.position == 1)
//                sendBaseData(10);
//            else
            sendBaseData(13, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手抽球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.DRIVEBALLBACK) {
//            if(selectModeMsg.position == 1)
//                sendBaseData(10);
//            else
            sendBaseData(9, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手抽球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.SHAQIU_HOUCHANG) {
//            if(selectModeMsg.position == 1)
//                sendBaseDataHouchangShaqiuLeft();
//            else sendBaseDataHouchangShaqiuRight();
            sendBaseData(17, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始杀球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANGWANG) {
            //暂未使用
            if(selectModeMsg.position == 1)
                sendBaseData(4, 1);
            else sendBaseData(8, 1);
            ShowHelper.toastShort(this, "开始放网。");
        }
        if (selectModeMsg.type == ModeSelectActivity.GOUQIU) {
//            if(selectModeMsg.position == 1)
//                sendBaseData(1);
//            else sendBaseData(3);
            sendBaseDataGouqiuAndCuoqiu();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始勾对角。");
        }

        if (selectModeMsg.type == ModeSelectActivity.GOUZHENG) {
            sendBaseData(3, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手勾对角。");
        }
        if (selectModeMsg.type == ModeSelectActivity.GOUFAN) {
            sendBaseData(1, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手勾对角。");
        }

        if (selectModeMsg.type == ModeSelectActivity.CUOQIU) {
//            if(selectModeMsg.position == 1)
//                sendBaseData(4);
//            else sendBaseData(8);
            sendBaseDataGouqiuAndCuoqiu();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始搓球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.CUOZHENG) {
            sendBaseData(3, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手搓球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.CUOFAN) {
            sendBaseData(1, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手搓球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.TUIQIU) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(5);
//            else sendBaseData(7);
            sendBaseDataPuqiu();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始推球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.TUIZHENG) {
            sendBaseData(7, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手推球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.TUIFAN) {
            sendBaseData(5, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手推球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.JIEFAQIU) {
            //暂未使用
            if (selectModeMsg.position == 1)
                sendBaseData(5, 1);
            else sendBaseData(7, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始接发球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.DANGQIU) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(9);
//            else
            sendBaseData(13, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始正手挡网。");
        }

        if (selectModeMsg.type == ModeSelectActivity.BLOCKBALLBACK) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(9);
//            else
            sendBaseData(9,1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手挡网。");
        }

        if (selectModeMsg.type == ModeSelectActivity.BANCHANGTUJI) {
            sendBaseDataBanchangtuji();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始半场突击。");
        }
        if (selectModeMsg.type == ModeSelectActivity.SHAQIU_ZHONGCHANG) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(19);
//            else
            sendBaseData(23, 1);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始正手接杀。");
        }
        if (selectModeMsg.type == ModeSelectActivity.SMASHBALLBACK) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(19);
//            else
            sendBaseData(19, 1);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始反手接杀。");
        }
        if (selectModeMsg.type == ModeSelectActivity.PIDIAO) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(24);
//            else
            sendBaseData(18, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始正手吊球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANSHOUDIAOQIU) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(24);
//            else
            sendBaseData(14, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始反手吊球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.GAOYUANQIU) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(25);
//            else
            sendBaseData(18, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始正手高远球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.FANSHOUGAOYUANQIU) {
//            if (selectModeMsg.position == 1)
//                sendBaseData(25);
//            else
            sendBaseData(14, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始反手高远球。");
        }

        if (selectModeMsg.type == ModeSelectActivity.PINGGAOQIU) {
            if (selectModeMsg.position == 1)
                sendBaseData(25, 1);
            else sendBaseData(27, 1);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始平高球。");
        }
        if (selectModeMsg.type == ModeSelectActivity.MIZIBU) {
            sendMiZiBuData();
            sendPosition(0);
            ShowHelper.toastShort(this, "开始米字步。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHASHANGWANG){
            sendBaseData(28, 2);
//            sendBaseDataShaShangwang();
            sendPosition(0);
            ShowHelper.toastShort(this, "开始正手被动过渡网前。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHANGSWZUHE){
            sendBaseDataShaShangwang();
            sendPosition(1);
            ShowHelper.toastShort(this, "开始杀上网。");
        }
        if(selectModeMsg.type == ModeSelectActivity.DIJIAOCEXIANG){
            sendBaseDataTwoHouchang(14,18);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始底角侧向。");
        }
        if(selectModeMsg.type == ModeSelectActivity.ZHONGCHANGCEXIANG){
            sendBaseDataTwoHouchang(9,13);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始中场侧向。");
        }

        if(selectModeMsg.type == ModeSelectActivity.CAIDAN){
            sendBaseDataTwoHouchang(9,13);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始彩蛋挑战。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SIFANGQIU){
            sendBaseDataSifangqiu();
            sendPosition(0);
            ShowHelper.toastShort(this, "开始四方球。");
        }
        if(selectModeMsg.type == ModeSelectActivity.SHUANGCESHASHANGWANG){
            sendBaseDataShuangceSSW();
            sendPosition(1);
            ShowHelper.toastShort(this, "两侧杀上网。");
        }
        if(selectModeMsg.type == ModeSelectActivity.ZUOCHANGJIEFA){
            sendBaseDataJiefaqiu(6, 19, 20);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始左场接发。");
        }
        if(selectModeMsg.type == ModeSelectActivity.YOUCHANGJIEFA){
            sendBaseDataJiefaqiu(21, 22, 23);
            sendPosition(1);
            ShowHelper.toastShort(this, "开始右场接发。");
        }

        if(selectModeMsg.type == ModeSelectActivity.ZUOXIEXIAN){
            sendBaseData(24, 2);
            sendPosition(0);
            ShowHelper.toastShort(this, "开始反手被动过渡网前。");
        }
        if(selectModeMsg.type == ModeSelectActivity.YOUXIEXIAN){
            sendYouXieXianData();
            sendPosition(0);
            ShowHelper.toastShort(this, "开始右斜线。");
        }

        if (selectModeMsg.type == 100) {
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

    private void sendPointData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subcriber
    private void dealSendDataEvent(RequestDataMsg requestDataMsg) {
        sendPointData();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size){

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
//            trainNums++;
        }

        if((buffer[1] & 0xFF) == 0x23){
            alertMsg(buffer[1] & 0xFF);
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
//            if((buffer[2] &0xFF) == 0x01)
//                faultMsg(true, 0x01);
//            else faultMsg(false, (buffer[2] &0xFF));
        }

        //主机电量
        if((buffer[1] & 0xFF) == 0x03){
//            batteryVolumeMsg(buffer[2] &0xFF);
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
    private void alertMsg(int code) {

        ShowAlertNearMsg showAlertNearMsg = new ShowAlertNearMsg();
        showAlertNearMsg.msgCode = code;
        EventBus.getDefault().post(showAlertNearMsg);
    }
    private void alertMsg() {

        ShowAlertNearMsg showAlertNearMsg = new ShowAlertNearMsg();
        EventBus.getDefault().post(showAlertNearMsg);
    }
    private void alertNoBallsMsg(int code) {

        ShowNoBallsMsg showNoBallsMsg = new ShowNoBallsMsg();
        showNoBallsMsg.msgCode = code;
        EventBus.getDefault().post(showNoBallsMsg);
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
                mViewPager.setCurrentItem(0);
                break;
            case R.id.back_train_group:
                back_train_single.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_left_default));
                tv_single.setTextColor(getResources().getColor(R.color.mode_blue));
                back_train_group.setBackground(getResources().getDrawable(R.drawable.switch_button_bg_right_selected));
                tv_group.setTextColor(Color.WHITE);
                mViewPager.setCurrentItem(1);
                break;
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
            timeCount.cancel();

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            if(  DateUtil.getStringToDate(App.getStringUserPreference("endtime"))-600 < new Date().getTime()/1000){
                showTip();
            }
            if(  DateUtil.getStringToDate(App.getStringUserPreference("endtime"))-120 < new Date().getTime()/1000){
                if(isTwo){
                    isTwo = false;
                    alertShowTwo();
                }else{

                }
            }
            if(DateUtil.getStringToDate(App.getStringUserPreference("endtime")) < new Date().getTime()/1000){
//                endUseDevice();
            }
        }
    }

    private void alertShowTwo() {
        backDevice();
        ShowTwoMinAlertMsg showTwoMinAlertMsg = new ShowTwoMinAlertMsg();
        EventBus.getDefault().post(showTwoMinAlertMsg);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                startActivity(new Intent(ModeSelectTestActivity.this, TwoMinAlertActivity.class));
            }
        }, 500);

    }

    private void showTip() {

        ShowTipEventMsg showTipEventMsg = new ShowTipEventMsg();
        EventBus.getDefault().post(showTipEventMsg);

    }

    private void backDevice() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x59, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x59 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterDevice() {
        byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x58, (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x00), (byte) (0x58 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00 ^ 0x00), (byte) 0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endUseDevice() {
        byte[] buf = new byte[]{(byte)0xAA, (byte)0x5A, (byte)(0x00), (byte)(0x00),  (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x00), (byte)(0x5A^0x00^0x00^0x00^0x00^0x00^0x00), (byte)0xA5};
        try {
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StopEventMsg stopEventMsg = new StopEventMsg();
        EventBus.getDefault().post(stopEventMsg);
        HashMap<String, String> params = new HashMap<>();
        params.put("devNum", "Test001");
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetNoStart", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    timeCount.cancel();
                    finish();
                } else {
                    Toast.makeText(ModeSelectTestActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void SaveTeachRecord() {
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("userId", userBookInfo.getUserId());
//        params.put("shotBallCount", "2");
//        params.put("devNumber", "DEV123456789");
//        params.put("techingName", "高远球");
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SaveTeachRecord", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    Toast.makeText(ModeSelectTestActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ModeSelectTestActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

}
