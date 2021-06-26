package com.health.pusun.device.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.health.pusun.device.utils.AppLog;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by majiangtao on 2018/3/12.
 */

public class App extends Application {

    public static Context context;
    public static final String USER_INFO_SHARED_PREFERENCE = "userInfo";
    //服务器地址
//    public static String BASE_URL = "http://192.168.0.107/";
    public static String BASE_URL = "http://www.pusuntech.com:8030/";
    public static final String APP_ID = "wx1d2c63e69a96e5c5";
    public static String deviceId = "";

    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.e("App application onCreate...");
        context = getApplicationContext();//返回应用的上下文
//        if(null == getStringUserPreference("token")){
//            saveUserPreference("token","");
//        }
        ZXingLibrary.initDisplayOpinion(this);
        if (null != App.getStringUserPreference("BASE_URL") && !"".equals(App.getStringUserPreference("BASE_URL"))) {

            App.BASE_URL = App.getStringUserPreference("BASE_URL");

        }else App.saveUserPreference("BASE_URL", App.BASE_URL);
        //460解码
//        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
//                //使用使用IjkPlayer解码
//                .setPlayerFactory(IjkPlayerFactory.create())
//                //使用ExoPlayer解码
////                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                //使用MediaPlayer解码
////                .setPlayerFactory(AndroidMediaPlayerFactory.create())
//                .build());
    }

    /**
     * 数据存储
     * 用户信息存储
     *
     * @param key
     * @param value
     */
    public static void saveUserPreference(String key, String value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putString(key, value).commit();
    }

    public static void saveUserPreference(String key, boolean value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putBoolean(key, value).commit();
    }

    public static void saveUserPreference(String key, int value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putInt(key, value).commit();
    }

    public static void saveUserPreference(String key, long value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putLong(key, value).commit();
    }

    public static void saveUserPreference(String key, float value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putFloat(key, value).commit();
    }

    public static String getStringUserPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getString(key, "");
    }

    public static String getStringUserPreference(String key, String value) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getString(key, "");
    }

    public static int getIntUserPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getInt(key, 0);
    }

    public static long getLongUserPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getLong(key, 0l);
    }

    public static float getFloatUserPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getFloat(key, 0f);
    }

    public static boolean getBooleanUserPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).
                getBoolean(key, false);
    }


    /**
     * 数据存储
     * 设备等用户无关信息存储
     *
     * @param key
     * @param value
     */
    public static void savePreference(String key, String value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putString(key, value).commit();
    }

    public static void savePreference(String key, int value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putInt(key, value).commit();
    }

    public static void savePreference(String key, long value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putLong(key, value).commit();
    }

    public static void savePreference(String key, Boolean value) {
        context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).edit()
                .putBoolean(key, value).commit();
    }

    public static boolean getPreferenceBoolean(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getBoolean(key, false);
    }

    public static String getPreference(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getString(key, "");
    }

    public static int getPreferenceInt(String key) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getInt(
                key, 0);
    }

    public static int getPreferenceInt(String key, int def) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getInt(
                key, def);
    }

    public static long getPreferenceLong(String key, long def) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getLong(
                key, def);
    }

    public static boolean getPreferenceBoolean(String key, boolean def) {
        return context.getSharedPreferences(USER_INFO_SHARED_PREFERENCE, Activity.MODE_PRIVATE).getBoolean(key, def);
    }


    /**
     * 获取应用版本号
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
    /**
     * 获取应用版本号
     * @return
     */
    public static String getVersionName() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort()
            throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            /* Read serial port parameters */

//            String packageName = getPackageName();
//            SharedPreferences sp = getSharedPreferences(packageName + "_preferences", MODE_PRIVATE);
//            String path = sp.getString("DEVICE", "");
//            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));

//            String packageName = getPackageName();
//            SharedPreferences sp = getSharedPreferences(packageName + "_preferences", MODE_PRIVATE);
//            String path = "/dev/ttyS3";  //370 460 大板
            String path = "/dev/ttyS3";  //260小板
//            int baudrate = 115200;
            int baudrate = 9600;
			/* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

}
