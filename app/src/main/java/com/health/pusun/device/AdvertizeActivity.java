package com.health.pusun.device;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdvertizeActivity extends AppCompatActivity {

    private Banner banner;
    private VideoView ijkVideoView;
    private int type;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertize);

        ijkVideoView = findViewById(R.id.player);
        banner = findViewById(R.id.banner);
        type = getIntent().getIntExtra("type", 0);
        list = (ArrayList)getIntent().getSerializableExtra("list");
        if(type == 2) {
            banner.setVisibility(View.GONE);
            ijkVideoView.setVisibility(View.VISIBLE);
            ijkVideoView.setUrl(list.get(0),null); //设置视频地址
//            ijkVideoView.setLooping(true);
            ijkVideoView.start();
        }else if(type == 1){
            getBannerList(list);
        }
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
    }

    private void getBannerList(List<String> list) {
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){

            titles.add("广告" + i);
        }
        banner.setBannerTitles(titles).setDelayTime(6000).setImages(list).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    protected void onDestroy() {

        ijkVideoView.release();
        banner.releaseBanner();
        super.onDestroy();

    }
}
