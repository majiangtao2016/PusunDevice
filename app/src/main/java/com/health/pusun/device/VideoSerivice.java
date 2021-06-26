package com.health.pusun.device;

import android.app.Service;
import android.app.smdt.SmdtManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.CameraTwoStartMsg;
import com.health.pusun.device.vo.eventbusmsg.StopRecordMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

public class VideoSerivice extends Service implements
        SurfaceHolder.Callback{
    private SurfaceView surfaceview;// 视频预览控件
    private LinearLayout lay; // 愿揽控件的
    private SurfaceHolder surfaceHolder; // //和surfaceView相关的
    private Context context;
    private boolean isRecorder = false;
    WindowManager wm;
    LinearLayout relLay;
    CramerThread thread;
    SmdtManager smdtManager;

    /**
     * 检测摄像头硬件 如果应用程序未使用manifest声明对摄像头需求进行特别指明，则应该在运行时检查一下摄像头是否可用
     */
    public boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("","onCreate");
        EventBus.getDefault().register(this);
        smdtManager= SmdtManager.create(this);
        if (!checkCameraHardware(this)) {
            Toast.makeText(this, "摄像头被占用或不存在", Toast.LENGTH_LONG).show();
            return;
        }
        AppLog.e("service is created.");
        // 设置悬浮窗体属性
        // 1.得到WindoeManager对象：
        wm = (WindowManager) getApplicationContext().getSystemService("window");
        // 2.得到WindowManager.LayoutParams对象，为后续设置相关参数做准备：
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        // 3.设置相关的窗口布局参数，要实现悬浮窗口效果，要需要设置的参数有
        // 3.1设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 3.2设置图片格式，效果为背景透明 //wmParams.format = PixelFormat.RGBA_8888;
        wmParams.format = 1;
        // 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 4.// 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 5. 调整悬浮窗口至中间
        wmParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER;
        // 6. 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        // 7.将需要加到悬浮窗口中的View加入到窗口中了：
        // 如果view没有被加入到某个父组件中，则加入WindowManager中
        surfaceview = new SurfaceView(this);
        surfaceHolder = surfaceview.getHolder();
        WindowManager.LayoutParams params_sur = new WindowManager.LayoutParams();
        params_sur.width = 1;
        params_sur.height = 1;
        params_sur.alpha = 255;
        surfaceview.setLayoutParams(params_sur);
        AppLog.e("surfaceview is created.");
        surfaceview.getHolder()
                .setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // surface.getHolder().setFixedSize(800, 1024);
        surfaceview.getHolder().addCallback((SurfaceHolder.Callback) this);

        relLay = new LinearLayout(this);
        WindowManager.LayoutParams params_rel = new WindowManager.LayoutParams();
        params_rel.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params_rel.height = WindowManager.LayoutParams.WRAP_CONTENT;
        relLay.setLayoutParams(params_rel);
        relLay.addView(surfaceview);
        wm.addView(relLay, wmParams); // 创建View

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
                               int arg3) {
        System.out.println("*****");

        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        AppLog.e("surfaceCreated++++++");

        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
        Log.i("process", Thread.currentThread().getName());
        if (!"null".equals(smdtManager.smdtGetUSBPath(context, 0))) {


            // //录像线程，当然也可以在别的地方启动，但是一定要在onCreate方法执行完成以及surfaceHolder被赋值以后启动
            thread = new CramerThread(60000, surfaceview,
                    surfaceHolder, smdtManager.smdtGetUSBPath(context, 0));// 设置录制时间为10s
            thread.start();
            Toast.makeText(VideoSerivice.this, "开始录像", Toast.LENGTH_LONG).show();
        }

    }

    @Subcriber
    private void dealStopRecordEvent(StopRecordMsg stopRecordMsg) {
        try {
            thread.stopRecord();
            Toast.makeText(VideoSerivice.this, "停止录像", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        AppLog.e("surfaceDestroyed *****");
        // surfaceDestroyed的时候同时对象设置为null
        surfaceview = null;
        surfaceHolder = null;
        if(thread.isAlive())thread.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
