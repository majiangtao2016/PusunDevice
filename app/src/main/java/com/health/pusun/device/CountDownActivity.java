package com.health.pusun.device;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.SetFastLMTMsg;
import com.health.pusun.device.vo.eventbusmsg.SetVoltageMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowFaultMsg;
import com.health.pusun.device.vo.eventbusmsg.ShowTwoMinAlertMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CountDownActivity extends AppCompatActivity {

    private ImageView load_gif;
    private int state, type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        EventBus.getDefault().register(this);
        state = getIntent().getIntExtra("state", 0);
        type = getIntent().getIntExtra("type", 0);
        load_gif = (ImageView) findViewById(R.id.load_gif);
        SelectModeMsg selectMsg = new SelectModeMsg();
        selectMsg.setType(100);
        selectMsg.setPosition(1);
        EventBus.getDefault().post(selectMsg);
        //设定振动电压
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                SetVoltageMsg setVoltageMsg = new SetVoltageMsg();
                EventBus.getDefault().post(setVoltageMsg);
            }
        }, 200);
        //调整速度
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
               if((type == 1027)|| (type == 1053) || (type == 1054) || type == 1056 || type == 1055 || type == 1057 || type == 1070 || type ==1015 || type == 3001){
                   SetFastLMTMsg setFastLMTMsg = new SetFastLMTMsg();
                   EventBus.getDefault().post(setFastLMTMsg);
               }
            }
        }, 500);

        //测试动画
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif5));
//                Glide.with(CountDownActivity.this).load(R.drawable.gif5).into(load_gif);
                SelectModeMsg selectMsg = new SelectModeMsg();
                selectMsg.setType(type);
                selectMsg.setPosition(state);
                EventBus.getDefault().post(selectMsg);
            }
        }, 2000);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.gif4).into(load_gif);
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif4));
//            }
//        }, 3000);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.gif3).into(load_gif);
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif3));
//
//            }
//        }, 4000);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.gif2).into(load_gif);
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif2));
//            }
//        }, 5000);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.gif1).into(load_gif);
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif1));
//            }
//        }, 6000);
//        (new Handler()).postDelayed(new Runnable() {
//            @Override
//            public synchronized void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.gif0).into(load_gif);
//                load_gif.setImageDrawable(getResources().getDrawable(R.drawable.gif0));
//            }
//        }, 7000);

        // 加载完成后的处理
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public synchronized void run() {
                if(type == 9999) startActivity(new Intent(CountDownActivity.this, EggPlayActivity.class));
                else{
                    Intent intent = new Intent(CountDownActivity.this, VideoPlayActivity.class);
                    intent.putExtra("state", state);
                    intent.putExtra("type", type);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                Glide.with(CountDownActivity.this).load(R.drawable.load_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(requestListener).into(load_gif);
//            }
//        }, 2000);

    }

    RequestListener requestListener = new RequestListener<Integer, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }
        @Override
        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public synchronized void run() {
                    SelectModeMsg selectMsg = new SelectModeMsg();
                    selectMsg.setType(type);
                    selectMsg.setPosition(state);
                    EventBus.getDefault().post(selectMsg);
                }
            }, 500);
            Observable.just(resource)
                    .flatMap(new Func1<GlideDrawable, Observable<?>>() {
                        @Override
                        public Observable<?> call(GlideDrawable glideDrawable) {
                            int duration = 0;
                            try {
                                GifDrawable gifDrawable = (GifDrawable) glideDrawable;
                                GifDecoder decoder = gifDrawable.getDecoder();
                                for (int i = 0; i < gifDrawable.getFrameCount(); i++) {
                                    duration += decoder.getDelay(i);                            }
                            } catch (Throwable e) {
                            }
                            return Observable.just(null).delay(duration, TimeUnit.MILLISECONDS);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            // 加载完成后的处理
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public synchronized void run() {
                                    if(type == 9999) startActivity(new Intent(CountDownActivity.this, EggPlayActivity.class));
                                    else{
                                        Intent intent = new Intent(CountDownActivity.this, VideoPlayActivity.class);
                                        intent.putExtra("state", state);
                                        intent.putExtra("type", type);
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            }, 1000);

                        }
                    });
            return false;
        }};

    @Subcriber
    private void showTwoMinEvent(ShowTwoMinAlertMsg showTwoMinAlertMsg) {

        if(showTwoMinAlertMsg.tipMsg.equals("two")){
            CountDownActivity.this.finish();
        }

    }
    @Subcriber
    private void dealFaultDataEvent(ShowFaultMsg showFaultMsg) {
        if(showFaultMsg.code != 0)
            finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
