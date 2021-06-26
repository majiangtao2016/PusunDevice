package com.health.pusun.device;

import android.app.smdt.SmdtManager;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.CameraPhotoMsg;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by majiangtao on 2018/5/26.
 */

public class CramerThread extends Thread{

    private MediaRecorder mediarecorder;// 录制视频的类private long
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceview;// 显示视频的控件
    private Camera mCamera;
    private long recordTime;
    private long startTime = Long.MIN_VALUE;
    private long endTime = Long.MIN_VALUE;
    private HashMap<String, String> map = new HashMap<String, String>();
    private static final String TAG = "SEDs508EG";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static String filePath;
    private SmdtManager smdtManager;
    private String usbPath;

    public CramerThread(long recordTime, SurfaceView surfaceview,
                        SurfaceHolder surfaceHolder, String USB) {
        this.recordTime = recordTime;
        this.surfaceview = surfaceview;
        this.surfaceHolder = surfaceHolder;
        this.usbPath = USB;
    }

    @Override
    public void run() {
        /** * 开始录像 */
        startRecord();
        /** * 启动定时器，到规定时间recordTime后执行停止录像任务 */
        if(mCamera != null){
//            Timer timer = new Timer();
//            timer.schedule(new TimerThread(), recordTime);
        }

    }

    /** * 获取摄像头实例对象 * * @return */
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            // 打开摄像头错误
            Log.i("info", "打开摄像头错误");
        }
        AppLog.e("open camera right!");
        return c;
    }

    /** * 开始录像 */
    public void startRecord() {
        try {
        AppLog.e("thread录像设置");
        MainActivity.isRecording = 1;
        mediarecorder = new MediaRecorder();// 创建mediarecorder对象
        mCamera = getCameraInstance(); // 解锁camera
        if(mCamera == null) return;
        mCamera.unlock();

        //旧摄像头
//        mediarecorder.setCamera(mCamera); // 设置录制视频源为Camera(相机)
//        mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置录制文件质量，格式，分辨率之类，这个全部包括了
//        mediarecorder.setProfile(CamcorderProfile
//                .get(CamcorderProfile.QUALITY_720P));
//        mediarecorder.setPreviewDisplay(surfaceHolder.getSurface()); // 设置视频文件输出的路径
//        // mediarecorder.setOutputFile("/sdcard/sForm.3gp");
//        mediarecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO)
//                .toString());

        //新摄像头
        mediarecorder.setCamera(mCamera); // 设置录制视频源为Camera(相机)
        mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置录制文件质量，格式，分辨率之类，这个全部包括了
         //Step 3: Set a Camera Parameters
        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        /* Encoding bit rate: 1 * 1024 * 1024*/
        mediarecorder.setVideoEncodingBitRate(1 * 1280 * 720);
        /* Fixed video Size: 640 * 480*/
        mediarecorder.setVideoSize(1280, 720);
        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediarecorder.setPreviewDisplay(surfaceHolder.getSurface()); // 设置视频文件输出的路径
        mediarecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO)
                .toString());

            // 准备录制
            mediarecorder.prepare();
            // 开始录制
            AppLog.e("thread开始录像");
            mediarecorder.start();
            // time.setVisibility(View.VISIBLE);// 设置录制时间显示
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void releaseMediaRecorder() {
        if (mediarecorder != null) {
            // 清除recorder配置
            mediarecorder.reset();
            // 释放recorder对象
            mediarecorder.release();
            mediarecorder = null;
            // 为后续使用锁定摄像头
            mCamera.lock();
        }
    }

    /** * 停止录制 */
    public void stopRecord() {
        System.out.println("--------------");

        if (mediarecorder != null) {
            // 停止录制
            mediarecorder.stop();
            // 释放资源
            mediarecorder.release();
            mediarecorder = null;
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
            CameraPhotoMsg cameraPhotoMsg = new CameraPhotoMsg();
            cameraPhotoMsg.setType("path");
            cameraPhotoMsg.setFilePath(filePath);
            EventBus.getDefault().post(cameraPhotoMsg);
        }
    }

    /** * 定时器 * @author bcaiw * */
    public class TimerThread extends TimerTask {
        /** * 停止录像 */
        @Override
        public void run() {
            try {
                stopRecord();
                this.cancel();
            } catch (Exception e) {
                map.clear();
                map.put("recordingFlag", "false");
                String ac_time = getVedioRecordTime();// 录像时间
                map.put("recordTime", ac_time);
                // sendMsgToHandle(m_msgHandler, iType, map);
            }
        }
    }

    /**
     * 通用方法，接收多线程过来的数据，有可能不仅仅是msg，所以定义map对象
     *
     * @param handle
     * @param iType
     * @param info
     */
    public void sendMsgToHandle(Handler handle, int iType,
                                Map<String, String> info) {
        Message threadMsg = handle.obtainMessage();
        threadMsg.what = iType;
        Bundle threadbundle = new Bundle();
        threadbundle.clear();
        for (Iterator i = info.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            threadbundle.putString(obj.toString(), info.get(obj));
        }
        threadMsg.setData(threadbundle);
        handle.sendMessage(threadMsg);

    }

    /**
     * 计算当前已录像时间，默认值返回0
     *
     * @return
     */
    public String getVedioRecordTime() {
        String result = "0";
        if (startTime != Long.MIN_VALUE && endTime != Long.MIN_VALUE) {
            long tempTime = (endTime - startTime);
            result = String.valueOf(tempTime);
        }
        return result;

    }

            public static void clearTemp(String dirPath){
             File dir = new File(dirPath);//清空文件夹
             File[] files = dir.listFiles();
             if(null != files){
            for (int i = 0; i < files.length; i++) {
             File file = files[i];
            boolean b = file.delete();
             }
             }
            }


    private  File getOutputMediaFile(int type) {


        // 判断SDCard是否存在
        if ("null".equals(usbPath)) {
            return null;
        }
        File mediaStorageDir = new File(
                usbPath + File.separator
                        + "/Video/");
        if(!mediaStorageDir.exists()) mediaStorageDir.mkdirs();
//        clearTemp(smdtManager.smdtGetUSBPath(context, 0) + File.separator
//                + "/Video/");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // 创建媒体文件名
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "VID_" + timestamp + ".mp4");

        filePath = mediaFile.getAbsolutePath();


//        // 判断SDCard是否存在
//        if (!Environment.MEDIA_MOUNTED.equals(Environment
//                .getExternalStorageState())) {
//            Log.d(TAG, "SDCard不存在");
//            return null;
//        }
//
//        File mediaStorageDir = new File(
//                Environment.getExternalStorageDirectory() + File.separator
//                        + "/MyXingCheCamera3GP/TrainVideo/");
//        if(!mediaStorageDir.exists()) mediaStorageDir.mkdirs();
//        clearTemp(Environment.getExternalStorageDirectory() + File.separator
//                + "/MyXingCheCamera3GP/TrainVideo/");
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdir()) {
//                Log.d(TAG, "failed to create directory");
//                return null;
//            }
//        }
//        // 创建媒体文件名
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
//                .format(new Date());
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + "IMG_" + timestamp + ".jpg");
//        } else if (type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + App.getStringUserPreference("name") + "VID_" + timestamp + ".mp4");
//        } else {
//            Log.d(TAG, "文件类型有误");
//            return null;
//        }
//        filePath = mediaFile.getAbsolutePath();
        return mediaFile;
    }

}
