package com.health.pusun.device;
import android.app.smdt.SmdtManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.application.App;
import com.health.pusun.device.test.TestPasswordActivity;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.BasicData;
import com.health.pusun.device.utils.DateUtil;
import com.health.pusun.device.utils.GlideImageLoader;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.utils.Update;
import com.health.pusun.device.views.AdController;
import com.health.pusun.device.views.ControllerListener;
import com.health.pusun.device.views.ListIjkVideoView;
import com.health.pusun.device.views.MyNumberPicker;
import com.health.pusun.device.vo.NewsVo;
import com.health.pusun.device.vo.RequestCallVo;
import com.health.pusun.device.vo.ThreePricesVo;
import com.health.pusun.device.vo.UserBaseVo;
import com.health.pusun.device.vo.UserBookInfo;
import com.health.pusun.device.vo.VideoModel;
import com.health.pusun.device.vo.eventbusmsg.CameraPhotoMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.StartRecordMsg;
import com.maning.pswedittextlibrary.MNPasswordEditText;
import com.sh.shvideolibrary.compression.CompressListener;
import com.sh.shvideolibrary.compression.CompressorUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends SerialPortActivity implements OnBannerListener,View.OnClickListener, ViewPager.OnPageChangeListener {

    private RelativeLayout start_ball, login_code_layout, start_ball_scan, pay_wechat_layout, scan_code_layout;
    private String url, url1, url2;
    private TimeCount timeCount;
    private TimeCount1 timeCount1;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    private Banner banner;
    private ImageView pay_code;
    private List<NewsVo> newsVos = new ArrayList<>();
    private List<NewsVo> newsPagesVos = new ArrayList<>();
    private UserBookInfo userBookInfo;
    private Button camera, stopcamera, compress;
    Intent intent;
    public static final int RECORD_SYSTEM_VIDEO = 1;
    String path;
    UserBaseVo userBaseVo;
    Button buttonSetup, buttonConsole;
    private Update update;
    private boolean isSeen = true;
//    private SmdtManager smdt;
    private List<String> list = new ArrayList<>();
    String path1 = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/" + "VID_20180526_144309.mp4";//视频录制输出地址
    //视频压缩数据地址
    private String currentOutputVideoPath;
//    private String currentOutputVideoPath = Environment.getExternalStorageDirectory() + File.separator
//            + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";

    private VideoView ijkVideoView;
    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
                + "/MyXingCheCamera3GP/" + "VID_20180526_144310.mp4";
    private static final String URL_NAME = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/" + "name.txt";
    private static final String RECORD_PATH = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/" + "record/";
    private static final String RECORD_FILENAME = "record.txt";
//    private static final String URL_AD = App.BASE_URL + "MicroVideoDir/VID_20190122_145433.mp4";
    private static final String URL_AD = "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0";
    private static final String URL_AD2 = "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0";
    private static final String URL_AD3 = "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0";
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 350L;
    public static int isRecording = 0;
    private int isUpdating = 0;
    private MNPasswordEditText password;
    private Button btn_password,btn_quit;
    private RelativeLayout select_time_layout;
    private MyNumberPicker np1, np2;
    private Button time1, time2, time3, btn_back;
    private TextView select_time_start, select_long_start;
    private String otherPay = "";
    private String[] minutesStrs = {"00","10","20","30","40","50"};
    private long scanDuration = 0l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_code_layout = findViewById(R.id.login_code_layout);
        start_ball =  findViewById(R.id.start_ball);
        start_ball_scan = findViewById(R.id.start_ball_scan);
        scan_code_layout = findViewById(R.id.scan_code_layout);
        pay_wechat_layout = findViewById(R.id.pay_wechat_layout);
        EventBus.getDefault().register(this);
        banner = (Banner) findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setDelayTime(5000);
        banner.setOnBannerListener(this);
        pay_code = findViewById(R.id.pay_code);
        if(null != App.getStringUserPreference("paycode"))otherPay = App.getStringUserPreference("paycode");
//        smdt = SmdtManager.create(this);
        password = findViewById(R.id.password);
        btn_password = findViewById(R.id.btn_password);
        btn_quit = findViewById(R.id.btn_quit);
        select_time_layout = findViewById(R.id.select_time_layout);
        np1 = findViewById(R.id.np1);
        np2 = findViewById(R.id.np2);
        initNp();
        select_time_start = findViewById(R.id.select_time_start);
        select_long_start = findViewById(R.id.select_long_start);
        ijkVideoView = findViewById(R.id.player);
        password.setOnTextChangeListener(new MNPasswordEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String s, boolean b) {
                if(b){
                    ShowHelper.toastShort(MainActivity.this, s);
                }
            }
        });

        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null == App.getStringUserPreference("passball") || App.getStringUserPreference("passball").equals("")){
                    if(password.getText().toString().equals("8888"))
                        select_time_layout.setVisibility(View.VISIBLE);
                    else ShowHelper.toastShort(MainActivity.this, "密码错误，请管理员检查！");
                }else if(password.getText().toString().equals(App.getStringUserPreference("passball"))){
                    select_time_layout.setVisibility(View.VISIBLE);
                }
                else ShowHelper.toastShort(MainActivity.this, "密码错误，请管理员检查！");
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_code_layout.setVisibility(View.GONE);
            }
        });
        time1 = findViewById(R.id.time1);time1.setOnClickListener(this);
        time2 = findViewById(R.id.time2);time2.setOnClickListener(this);
        time3 = findViewById(R.id.time3);time3.setOnClickListener(this);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan_code_layout.setVisibility(View.GONE);
            }
        });
//        type = getIntent().getIntExtra("type", 0);
//        list = (ArrayList)getIntent().getSerializableExtra("list");

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
//                list = getVideoFilesAllName(smdt.smdtGetUSBPath(MainActivity.this, 0));
//                if(list != null && list.size() != 0 ) {
//                    banner.setVisibility(View.GONE);
//                    ijkVideoView.setVisibility(View.VISIBLE);
//                    ijkVideoView.setUrl(list.get(0),null); //设置视频地址
////            ijkVideoView.setLooping(true);
//                    ijkVideoView.start();
//                }else{
//                    list = getPicFilesAllName(smdt.smdtGetUSBPath(MainActivity.this, 0));
//                    if(list != null && list.size() != 0 ) {
//                        getBannerList(list);
//                    }else{
//                        list = new ArrayList<>();
//                        list.add((new File(URL_LOCAL).getAbsolutePath()));
//                        banner.setVisibility(View.GONE);
//                        ijkVideoView.setVisibility(View.VISIBLE);
//                        ijkVideoView.setUrl(list.get(0),null); //设置视频地址
//                        ijkVideoView.start(); //开始播放，不调用则不自动播放
//                    }
//                }
            }
        }, 3000);
        ijkVideoView.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
            private int mCurrentVideoPosition;
            @Override
            public void onPlayerStateChanged(int playerState) {

            }

            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {

                    mCurrentVideoPosition++;

                    if (mCurrentVideoPosition >= list.size()) mCurrentVideoPosition = 0;

                    ijkVideoView.release();

                    //重新设置数据
                    ijkVideoView.setUrl(list.get(mCurrentVideoPosition),null); //设置视频地址
                    ijkVideoView.start();

                }
            }
        });

        url = replaceBlank(getFileContent(new File(URL_NAME)));
        if(null != url && !"".equals(url)){
            App.saveUserPreference("name", url);
////            url = "SZXINGDONG001";  //需测试注释
        }else ShowHelper.showAlertDialog(this,"网络异常，请设置好网络！");
        url1 = "http://www.pusuntech.com/Download.html";
        url2 = "https://open.weixin.qq.com/sns/getexpappinfo?appid=wx1b478543baf67517&path=pages%2Findex%2Findex.html#wechat-redirect";
        intent = new Intent(MainActivity.this, VideoSerivice.class);
       for(int i = 0; i <BasicData.sets.length; i++){
           if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.sets[i][0] = (short)App.getIntUserPreference("sets" + i + "0");
           if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.sets[i][1] = (short)App.getIntUserPreference("sets" + i + "1");
           if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.sets[i][2] = (short)App.getIntUserPreference("sets" + i + "2");
//           if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.b2[i][0] = (short)App.getIntUserPreference("sets" + i + "0");
//           if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.b2[i][1] = (short)App.getIntUserPreference("sets" + i + "1");
//           if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.b2[i][2] = (short)App.getIntUserPreference("sets" + i + "2");
       }
//        Bitmap mBitmap1 = CodeUtils.createImage(url1, 120, 120, null);
//        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//        mBitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos1);
//        bytes1=baos1.toByteArray();
//        Glide.with(MainActivity.this).load(bytes1).into(app_code);

//        Bitmap mBitmap2 = CodeUtils.createImage(url2, 180, 180, null);
//        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//        mBitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
//        bytes2 = baos2.toByteArray();
//        Glide.with(MainActivity.this).load(bytes2).into(micro_code);

        start_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( App.getLongUserPreference("duration") < 120l || App.getLongUserPreference("duration") == -1l){
                    login_code_layout.setVisibility(View.VISIBLE);
                    password.setText("");

                }

                else{
                    Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
                    intent.putExtra("duration", App.getLongUserPreference("duration"));
                    startActivity(intent);
                }
            }
        });
        start_ball_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( App.getLongUserPreference("duration") < 120l || App.getLongUserPreference("duration") == -1l){
                    login_code_layout.setVisibility(View.VISIBLE);
                    password.setText("");

                }

                else{
                    Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
                    intent.putExtra("duration", App.getLongUserPreference("duration"));
                    startActivity(intent);
                }
                //扫码
//                if( App.getLongUserPreference("duration") < 120l || App.getLongUserPreference("duration") == -1l){
//                    scan_code_layout.setVisibility(View.VISIBLE);
//                    setPriceTest();
//
//                }
//
//                else{
//                    Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
//                    intent.putExtra("duration", App.getLongUserPreference("duration"));
//                    startActivity(intent);
//                }

            }
        });
       select_time_start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(np1.getValue() * 60 + np2.getValue()*10 > 0){
                   writeTxtToFile((DateUtil.getCurrentDateTime() + ":选择使用时长(分钟):" + (np1.getValue() * 60 + np2.getValue()*10)), RECORD_PATH, RECORD_FILENAME);
                   Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
                   intent.putExtra("duration", (long)(np1.getValue() * 60*60 + np2.getValue()*10*60));
                   startActivity(intent);
                   login_code_layout.setVisibility(View.GONE);
                   select_time_layout.setVisibility(View.GONE);
               }else ShowHelper.toastShort(MainActivity.this, "使用时长需至少10分钟。");

           }
       });
       select_long_start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               writeTxtToFile((DateUtil.getCurrentDateTime() + ":选择不限时使用一次."), RECORD_PATH, RECORD_FILENAME);
               Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
               intent.putExtra("duration", -1l);
               startActivity(intent);
               login_code_layout.setVisibility(View.GONE);
               select_time_layout.setVisibility(View.GONE);
           }
       });
        timeCount = new TimeCount(180000, 3000);
         buttonSetup = (Button) findViewById(R.id.ButtonSetup);
        buttonSetup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SerialPortPreferences.class));
                Intent intent = new Intent(MainActivity.this, TestPasswordActivity.class);
//                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

         buttonConsole = (Button) findViewById(R.id.ButtonConsole);
         buttonConsole.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View view) {
                 startActivity(new Intent(MainActivity.this, TestPasswordActivity.class));
                 sendBaseData();
                 return true;
             }
         });
        compress = (Button) findViewById(R.id.compress);
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                compress(path,path);
            }
        });
        camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 开始摄像 **/
                startService(intent);
//                reconverIntent(camera);
            }
        });
        stopcamera = (Button) findViewById(R.id.stopcamera);
        stopcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 停止摄像 **/
                stopService(intent);
//                reconverIntent(camera);
            }
        });

//        ijkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
//        ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL)).toString()); //设置视频地址
//        StandardVideoController controller = new StandardVideoController(this);
//        ijkVideoView.setLooping(true);
//        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
//        controller.setVisibility(View.GONE);

//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                ijkVideoView.setVisibility(View.VISIBLE);
//                ijkVideoView.start();
//            }
//        }, 50000);
//        ijkVideoView = findViewById(R.id.player);
//        ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL)).toString(),null); //设置视频地址
//        ijkVideoView.setLooping(true);
//        ijkVideoView.start(); //开始播放，不调用则不自动播放
//        List<VideoModel> videos = new ArrayList<>();
//        AdController adController = new AdController(this);
//        adController.setControllerListener(new ControllerListener() {
//            @Override
//            public void onAdClick() {
//                Toast.makeText(MainActivity.this, "广告点击跳转", Toast.LENGTH_SHORT).show();
//            }
//        });
////        videos.add(new VideoModel(URL_AD, "广告", adController, true));
//        videos.add(new VideoModel(Uri.fromFile(new File(URL_LOCAL)).toString(), "广告1", new StandardVideoController(this), false));
////        videos.add(new VideoModel(URL_AD2, "广告1", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_AD, "广告2", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_AD3, "广告3", new StandardVideoController(this), true));
////        videos.add(new VideoModel(URL_VOD, "这是一个标题", new StandardVideoController(this), false));
//        ijkVideoView.setVideos(videos);
//        videos.get(0).controller.setVisibility(View.GONE);
//
//        PlayerConfig playerConfig = new PlayerConfig.Builder().build();
//        playerConfig.isLooping = true;
//        playerConfig.isCache = false;
//        ijkVideoView.setPlayerConfig(playerConfig);

//        timeCount1 = new TimeCount1(15* 60 * 60 * 1000, 180000);
//        timeCount1.start();

//        ijkVideoView.start();
//        uploadFile("4484792C-6C10-4848-BDAA-7ADABED3E480");
        //串口基础点位发送
        sendBaseData();
        if(0== App.getIntUserPreference("onfast")){
            App.saveUserPreference("onfast", 1);
        }
        if(0== App.getIntUserPreference("stopfast")){
            App.saveUserPreference("stopfast", 3);
        }
//        //设定风扇开关时间
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
//                try {
//                    short a1 = (short)App.getIntUserPreference("onfast");
//                    short a2 = (short)App.getIntUserPreference("stopfast");
//                    byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (0), (byte) (0), (byte) (0), (byte) (0), (byte) 0x01, (byte) 0xA5};
//                    mOutputStream.write(buf);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1200);
        if(0== App.getIntUserPreference("on")){
            App.saveUserPreference("on", 1);
        }
        if(0== App.getIntUserPreference("stop")){
            App.saveUserPreference("stop", 7);
        }
        //设定风扇开关时间
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    short a1 = (short)App.getIntUserPreference("on");
                    short a2 = (short)App.getIntUserPreference("stop");
                    byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (0), (byte) (0), (byte) (0), (byte) (0), (byte) 0x01, (byte) 0xA5};
                        mOutputStream.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1800);

        if(0 == App.getIntUserPreference("high")){
            App.saveUserPreference("high",1200);
        };
        if(0 == App.getIntUserPreference("middle")){
            App.saveUserPreference("middle",1180);
        }
        if(0 != App.getIntUserPreference("high") && 0 != App.getIntUserPreference("middle")){
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        short a1 = (short)App.getIntUserPreference("high");
                        short a2 = (short)App.getIntUserPreference("middle");
                        short a3 = 1000;
                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                        AppLog.e("high:" + a1 + " middle:" + a2 + " low:" + a3);
                        mOutputStream.write(buf);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1500);
        }
//        if(0 != App.getIntUserPreference("highback") && 0 != App.getIntUserPreference("middleback") && 0 != App.getIntUserPreference("lowback")){
//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public synchronized void run() {
//
//                    try {
//                        short a1 = (short)App.getIntUserPreference("highback");
//                        short a2 = (short)App.getIntUserPreference("middleback");
//                        short a3 = (short)App.getIntUserPreference("lowback");
//                        byte[] buf = new byte[]{(byte)0xAA, (byte)0x59, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
//                        AppLog.e("highback:" + a1 + " middleback:" + a2 + " lowback:" + a3);
//                        mOutputStream.write(buf);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, 2500);
//        }
//        checkAppVersion();
//        getMetrics();

//        HashMap<String, String> params2 = new HashMap<>();
//        params2.put("devNum", url);
//        MyHttpUtils.getAsAync(App.BASE_URL + "/api/ScanAndStartGetPrice", params2, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                ShowHelper.toastLong(MainActivity.this, "okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    Toast.makeText(MainActivity.this, "获得价格成功！", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "设备联网异常！", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }

    private void initNp() {
        np1.setMaxValue(5);
        np1.setMinValue(0);
        np1.setValue(1);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                String toast = "oldVal：" + i + "   newVal：" + i1;
                ShowHelper.toastShort(MainActivity.this, toast);
            }
        });
        np1.setNumberPickerDividerColor(getResources().getColor(R.color.colorPrimaryDark));
        np2.setDisplayedValues(minutesStrs);
        np2.setMinValue(0);
        np2.setMaxValue(minutesStrs.length - 1);
        np2.setValue(1);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                String toast = "oldVal：" + i + "   newVal：" + i1;
                ShowHelper.toastShort(MainActivity.this, toast);
            }
        });
        np2.setNumberPickerDividerColor(getResources().getColor(R.color.colorPrimaryDark));
        np1.setWrapSelectorWheel(true); //设置循环滚动
        np2.setWrapSelectorWheel(true); //设置循环滚动
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入
    }

    private void getMetrics(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        ShowHelper.toastShort(MainActivity.this, "wicth:" + width + "height:" + height + "density:" + density + "densityDpi:" + densityDpi);
    }



    private void checkAppVersion() {

        HashMap<String, String> params = new HashMap<>();
        params.put("DevNumber", url);
        params.put("Version", App.getVersionCode() + "");
        MyHttpUtils.postAsAync(App.BASE_URL + "api/GetDeviceVersion", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                isUpdating = 0;
            }

            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    isUpdating = 1;
                    update = JSON.parseObject(requestCallVo.getData().toString(), Update.class);
                    if (update != null) {
                        if (update.getVersion() > App.getVersionCode()) {
                            if(update.getIsUpdate() != 0){
                                DownloadBuilder builder = AllenVersionChecker
                                        .getInstance()
                                        .downloadOnly(UIData.create().setDownloadUrl("http://" + update.getDownLoad()));
                                builder.setApkDownloadListener(new APKDownloadListener() {
                                    @Override
                                    public void onDownloading(int progress) {

                                    }

                                    @Override
                                    public void onDownloadSuccess(File file) {
                                        SelectModeMsg selectMsg = new SelectModeMsg();
                                        selectMsg.setType(100);
                                        selectMsg.setPosition(1);
                                        EventBus.getDefault().post(selectMsg);
                                    }

                                    @Override
                                    public void onDownloadFail() {
                                        isUpdating = 0;
                                    }
                                });
                                builder.setDirectDownload(true);
                                builder.setShowNotification(true);
                                builder.setShowDownloadingDialog(false);
                                builder.setShowDownloadFailDialog(false);
                                builder.setForceRedownload(true);
                                builder.executeMission(MainActivity.this);
                            }
                            }
                    }
                }
            }
        });

    }

    private void testInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DevNum", url);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/GetDevBookByOrder", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                ShowHelper.toastLong(MainActivity.this, "网络异常，请联系前台处理！");
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                //{"Type":1,"Title":null,"Message":null,"Data":{"ID":"4484792c-6c10-4848-bdaa-7adabed3e480","UserID":"4484792c-6c10-4848-bdaa-7adabed3e480","NickName":"小马","Gender":"男","Phone":"15013739670","HeadPortrait":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIk3GP5NzIUiaxFN7S1zaIO8w8oLe5n6w1xiaoIU0k7us7ia5ibTa3CBCE8ZcXCXG5YeLzc9CsfpqVqLg/132","WeiXinOpenID":"o0X881RJyINE26f6Sk1n-lWUdD-o","CreateTime":"2018-06-07T17:57:11.353","AliOpenUserID":"20880087770829055238296450917373","AliPayUserID":"2088802681218738","WeiXinUnionID":"oI_--0nUbrapwgATSYBb9yLlsxQU","MicroAppOpenID":"o3qMu5QmwXQ2b1uSuNP8-ZU84qWQ"},"Other":null}
                //"Type":0,"Title":null,"Message":"当前设备未被预订"
                if(requestCallVo.getType() == 1) {

                    userBaseVo = JSON.parseObject(requestCallVo.getData().toString(), UserBaseVo.class);
                }else{
                }
                if(isUpdating == 0){
//                    checkAppVersion();
                }
            }
        });

    }

    private void checkError() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DevNum", url);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/GetDevIsState", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                ShowHelper.toastLong(MainActivity.this, "网络异常，请联系前台处理！");
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                //{"Type":1,"Title":null,"Message":null,"Data":{"ID":"4484792c-6c10-4848-bdaa-7adabed3e480","UserID":"4484792c-6c10-4848-bdaa-7adabed3e480","NickName":"小马","Gender":"男","Phone":"15013739670","HeadPortrait":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIk3GP5NzIUiaxFN7S1zaIO8w8oLe5n6w1xiaoIU0k7us7ia5ibTa3CBCE8ZcXCXG5YeLzc9CsfpqVqLg/132","WeiXinOpenID":"o0X881RJyINE26f6Sk1n-lWUdD-o","CreateTime":"2018-06-07T17:57:11.353","AliOpenUserID":"20880087770829055238296450917373","AliPayUserID":"2088802681218738","WeiXinUnionID":"oI_--0nUbrapwgATSYBb9yLlsxQU","MicroAppOpenID":"o3qMu5QmwXQ2b1uSuNP8-ZU84qWQ"},"Other":null}
                //"Type":0,"Title":null,"Message":"当前设备未被预订"
                if(requestCallVo.getType() == 1) {
                }else if(requestCallVo.getType() == 0){
                }
            }
        });

    }
    public static List<String> getPicFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if(!files[i].isDirectory() && (files[i].getAbsolutePath().toString().contains("jpg")|| files[i].getAbsolutePath().toString().contains("png")))
                s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    private void endUseDevice() {

        HashMap<String, String> params = new HashMap<>();
        params.put("devNum", url);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/SetNoStart", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                Toast.makeText(MainActivity.this, "网络异常，请检查网络！", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
//                    scan_code_layout.setVisibility(View.VISIBLE);
//                    setPriceTest();
//                    timeCount.start();
                } else {
                    Toast.makeText(MainActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendBaseData() {

//        if(url.equals("SZPUWEI003")){
//            for(int i = 0; i < BasicData.b.length; i ++){
////            for(int i = 0; i < 9; i ++){
//                byte check_sum = (byte)((byte)(i + 1)^(byte)(BasicData.b[i][0] >> 8)^(byte)(BasicData.b[i][0] >> 0)^(byte)(BasicData.b[i][1] >> 8)^(byte)(BasicData.b[i][1] >> 0)^(byte)(BasicData.b[i][2] >> 8)^(byte)(BasicData.b[i][2] >> 0));
//                final byte[] buf = new byte[]{(byte)0xAA, (byte)(i + 1), (byte)(BasicData.b[i][0] >> 8), (byte)(BasicData.b[i][0] >> 0),  (byte)(BasicData.b[i][1] >> 8), (byte)(BasicData.b[i][1] >> 0), (byte)(BasicData.b[i][2] >> 8), (byte)(BasicData.b[i][2] >> 0), check_sum, (byte)0xA5};
//                final int n = i;
//                AppLog.e("左右：" + (int)BasicData.b[i][0]  + "上下：" + (int)BasicData.b[i][1] + "速度：" + (int)BasicData.b[i][2] );
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
//        }else if(url.equals("DEV020") ){
//            for(int i = 0; i < BasicData.h.length; i ++){
////            for(int i = 0; i < 9; i ++){
//                byte check_sum = (byte)((byte)(i + 1)^(byte)(BasicData.h[i][0] >> 8)^(byte)(BasicData.h[i][0] >> 0)^(byte)(BasicData.h[i][1] >> 8)^(byte)(BasicData.h[i][1] >> 0)^(byte)(BasicData.h[i][2] >> 8)^(byte)(BasicData.h[i][2] >> 0));
//                final byte[] buf = new byte[]{(byte)0xAA, (byte)(i + 1), (byte)(BasicData.h[i][0] >> 8), (byte)(BasicData.h[i][0] >> 0),  (byte)(BasicData.h[i][1] >> 8), (byte)(BasicData.h[i][1] >> 0), (byte)(BasicData.h[i][2] >> 8), (byte)(BasicData.h[i][2] >> 0), check_sum, (byte)0xA5};
//                final int n = i;
//                AppLog.e("左右：" + (int)BasicData.h[i][0]  + "上下：" + (int)BasicData.h[i][1] + "速度：" + (int)BasicData.h[i][2] );
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
//        } else{
            for(int i = 0; i < BasicData.b2.length; i ++){
//            for(int i = 0; i < 9; i ++){
                byte check_sum = (byte)((byte)(i + 1)^(byte)(BasicData.b2[i][0] >> 8)^(byte)(BasicData.b2[i][0] >> 0)^(byte)(BasicData.b2[i][1] >> 8)^(byte)(BasicData.b2[i][1] >> 0)^(byte)(BasicData.b2[i][2] >> 8)^(byte)(BasicData.b2[i][2] >> 0));
                final byte[] buf = new byte[]{(byte)0xAA, (byte)(i + 1), (byte)(BasicData.b2[i][0] >> 8), (byte)(BasicData.b2[i][0] >> 0),  (byte)(BasicData.b2[i][1] >> 8), (byte)(BasicData.b2[i][1] >> 0), (byte)(BasicData.b2[i][2] >> 8), (byte)(BasicData.b2[i][2] >> 0), check_sum, (byte)0xA5};
                final int n = i;
                AppLog.e("左右：" + (int)BasicData.b2[i][0]  + "上下：" + (int)BasicData.b2[i][1] + "速度：" + (int)BasicData.b2[i][2] );
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {

                        try {
                            AppLog.e("第" + n+ "条指令");
                            mOutputStream.write(buf);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1*i);
            }
//        }

    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {

    }


    public static List<String> getVideoFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if(!files[i].isDirectory() && (files[i].getAbsolutePath().toString().contains("mp4")))
                s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    @Subcriber
    private void uploadVideoEvent(CameraPhotoMsg cameraPhotoMsg) {
        if (cameraPhotoMsg.type.equals("path")) {
            MainActivity.isRecording = 0;
            StartRecordMsg startRecordMsg = new StartRecordMsg();
            startRecordMsg.code = 0;
            EventBus.getDefault().post(startRecordMsg);
//             path = cameraPhotoMsg.filePath;
//             uploadFile(userBookInfo.getUserId());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
        isSeen = true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
        isSeen = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(intent);
        EventBus.getDefault().unregister(this);
        ijkVideoView.release();
    }



    private void compress(String inpath, String outpath){
        /**
         * 压缩视频
         */
        CompressorUtils compressorUtils = new CompressorUtils(inpath,outpath,MainActivity.this);
        compressorUtils.execCommand(new CompressListener() {
            @Override
            public void onExecSuccess(String message) {
                AppLog.e("success " + message);
//                uploadFile(userBookInfo.getUserId());
            }

            @Override
            public void onExecFail(String reason) {
                AppLog.e("fail " + reason);
            }

            @Override
            public void onExecProgress(String message) {
                AppLog.e("ongoing: " + message);
            }
        });
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    /**
     * 启用系统相机录制
     *
     * @param view
     */
    public void reconverIntent(View view) {
        Uri fileUri = Uri.fromFile(getOutputMediaFile());
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10); //限制的录制时长 以秒为单位
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1); //设置拍摄的质量最小是0，最大是1（建议不要设置中间值，不同手机似乎效果不同。。。）
        //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024);//限制视频文件大小 以字节为单位
        startActivityForResult(intent, RECORD_SYSTEM_VIDEO);
    }

    private File getOutputMediaFile() {
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

            Toast.makeText(this, "请检查SDCard！", Toast.LENGTH_SHORT).show();
            return null;
        }
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + File.separator
                        + "/MyXingCheCamera3GP/");
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory
//                (Environment.DIRECTORY_DCIM), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

    private void getBannerList(List<String> list) {
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){

            titles.add("广告" + i);
        }
        banner.setBannerTitles(titles).setDelayTime(6000).setImages(list).setImageLoader(new GlideImageLoader()).start();
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
            case R.id.time1:
                pay_wechat_layout.setVisibility(View.VISIBLE);
                scanDuration = 30*60;
                refreshCode(1);
                break;
            case R.id.time2:
                pay_wechat_layout.setVisibility(View.VISIBLE);
                scanDuration = 60*60;
                refreshCode(2);
                break;
            case R.id.time3:
                pay_wechat_layout.setVisibility(View.VISIBLE);
                scanDuration = 120*60;
                refreshCode(3);
                break;
            default: break;
        }

    }

    private void refreshCode(int i) {

            HashMap<String, String> params = new HashMap<>();
            params.put("devNum", url);
            if(i == 1)
            params.put("min", "30");
            if(i == 2)
            params.put("min", "60");
            if(i == 3)
            params.put("min", "120");
            MyHttpUtils.postAsAync(App.BASE_URL + "/api/ScanCodeAndStart", params, new MyJsonCallbalk() {
                @Override
                public void onError(Exception e, int code) {
                    AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                    Toast.makeText(MainActivity.this, "设备联网异常！", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onResponse(RequestCallVo requestCallVo) {
//                    ShowHelper.toastLong(MainActivity.this, "okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                    if (requestCallVo.getType() == 1) {
                        String codePay = requestCallVo.getData().toString();
                        Bitmap mBitmap = CodeUtils.createImage(codePay, 350, 350, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes=baos.toByteArray();
                        Glide.with(MainActivity.this).load(bytes).into(pay_code);
                        otherPay = requestCallVo.getTitle();
                        App.saveUserPreference("paycode", otherPay);
                        timeCount.start();
                    } else {
                    Toast.makeText(MainActivity.this, "设备联网异常！", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    private void setPriceTest(){
        HashMap<String, String> params = new HashMap<>();
        params.put("devNum", url);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/ScanAndStartGetPrice", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                ShowHelper.toastLong(MainActivity.this, "网络连接异常，请检查网络！");
                scan_code_layout.setVisibility(View.GONE);
                pay_wechat_layout.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
//                ShowHelper.toastLong(MainActivity.this, "okHttpUtil.postAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    ThreePricesVo threePricesVo = JSON.parseObject(requestCallVo.getData().toString(), ThreePricesVo.class);
                    time1.setText("半小时（" + (double)(threePricesVo.getPrice1())/100 + "元）");
                    time2.setText("一小时（" + (double)(threePricesVo.getPrice2())/100 + "元）");
                    time3.setText("二小时（" + (double)(threePricesVo.getPrice3())/100 + "元）");
//                    Toast.makeText(MainActivity.this, "获取价格成功！", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(MainActivity.this, "设备联网异常！", Toast.LENGTH_LONG).show();
                }
            }
        });

        //testsetPrice
//        HashMap<String, String> params = new HashMap<>();
//        params.put("devNum", url);
//        params.put("price1", "150");
//        params.put("price2", "1");
//        params.put("price3", "1");
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/ScanAndStartSetPrice", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                ShowHelper.toastLong(MainActivity.this, "okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    Toast.makeText(MainActivity.this, "设定价格成功！", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "设备联网异常！", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
    }

    @Override
    public void OnBannerClick(int position) {

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
            scan_code_layout.setVisibility(View.GONE);
            pay_wechat_layout.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            startTest(otherPay);
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
            if(isSeen){
                banner.setVisibility(View.GONE);
                ijkVideoView.setVisibility(View.VISIBLE);
                ijkVideoView.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ijkVideoView.setVisibility(View.GONE);
                        banner.setVisibility(View.VISIBLE);
                        ijkVideoView.pause();
                    }
                }, 90000);
            }
        }

    }

    private void startTest(String result) {

        HashMap<String, String> params = new HashMap<>();
        params.put("devNum", url);
        params.put("orderNum",result);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/ScanAndStartCheck", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
//                    userBookInfo = JSON.parseObject(requestCallVo.getData().toString(), UserBookInfo.class);
//                    uploadFile(userBookInfo.getUserId());
//                    App.saveUserPreference("endtime", userBookInfo.getEndTime().replace("T", " "));
                    Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
                    intent.putExtra("duration", scanDuration);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("user", userBookInfo);
//                    bundle.putString("url", url);
//                    intent.putExtras(bundle);
                    startActivity(intent);
                    App.saveUserPreference("duration", scanDuration);
                    timeCount.cancel();
                    scan_code_layout.setVisibility(View.GONE);
                    pay_wechat_layout.setVisibility(View.GONE);
//                    buttonSetup.setVisibility(View.GONE);
//                    buttonConsole.setVisibility(View.GONE);
                } else {
//                    Toast.makeText(MainActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


//        HashMap<String, String> params = new HashMap<>();
//        params.put("devNum", result);
//        MyHttpUtils.postAsAync(App.BASE_URL + "/api/CheckIsStart", params, new MyJsonCallbalk() {
//            @Override
//            public void onError(Exception e, int code) {
//                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//            }
//            @Override
//            public void onResponse(RequestCallVo requestCallVo) {
//                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                if (requestCallVo.getType() == 1) {
//                    userBookInfo = JSON.parseObject(requestCallVo.getData().toString(), UserBookInfo.class);
////                    uploadFile(userBookInfo.getUserId());
//                    App.saveUserPreference("endtime", userBookInfo.getEndTime().replace("T", " "));
//                    Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
//                    intent.putExtra("duration", scanDuration);
////                    Bundle bundle = new Bundle();
////                    bundle.putSerializable("user", userBookInfo);
////                    bundle.putString("url", url);
////                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    timeCount.cancel();
//                    scan_code_layout.setVisibility(View.GONE);
//                    pay_wechat_layout.setVisibility(View.GONE);
////                    buttonSetup.setVisibility(View.GONE);
////                    buttonConsole.setVisibility(View.GONE);
//                } else {
////                    Toast.makeText(MainActivity.this, requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void uploadFile(String userId){
//        Toast.makeText(MainActivity.this, "upload video, userId:" + userId + " devNum:" + url , Toast.LENGTH_SHORT).show();
        final File file = new File(path);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("devNumber", url );
        MyHttpUtils.postFileAsAync(App.BASE_URL + "/api/SaveMicroVideo", params,file,"file", new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                MainActivity.isRecording = 0;
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                StartRecordMsg startRecordMsg = new StartRecordMsg();
                startRecordMsg.code = 0;
                EventBus.getDefault().post(startRecordMsg);
                Toast.makeText(MainActivity.this, "length:" + file.length(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                MainActivity.isRecording = 0;
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                StartRecordMsg startRecordMsg = new StartRecordMsg();
                startRecordMsg.code = 0;
                EventBus.getDefault().post(startRecordMsg);
                if (requestCallVo.getType() == 1) {
                    Toast.makeText(MainActivity.this, requestCallVo.getMessage() + file.length(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, requestCallVo.getMessage() + file.length(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //读取指定目录下的所有TXT文件的文件内容
    protected String getFileContent(File file) {
        String content  = "";
        if (file.isDirectory() ) {	//检查此路径名的文件是否是一个目录(文件夹)
            AppLog.e("The File doesn't not exist "
                    +file.getName().toString()+file.getPath().toString());
        } else {
            if (file.getName().endsWith(".txt")) {//文件格式为txt文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                =new InputStreamReader(instream, "GBK");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line="";
                        //分行读取
                        while (( line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();		//关闭输入流
                    }
                }
                catch (java.io.FileNotFoundException e) {
                    AppLog.e("The File doesn't not exist.");
                }
                catch (IOException e)  {
                    AppLog.e( e.getMessage());
                }
            }
        }
        return content ;
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    //生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

}
