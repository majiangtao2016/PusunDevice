package com.health.pusun.device.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.health.pusun.device.vo.VideoModel;

import java.util.List;

/**
 * 连续播放一个列表
 * Created by xinyu on 2017/12/25.
 */

public class ListIjkVideoView implements ListMediaPlayerControl{

//    protected List<VideoModel> mVideoModels;//列表播放数据

    protected int mCurrentVideoPosition = 0;//列表播放时当前播放视频的在List中的位置

    @Override
    public void skipToNext() {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(long pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferedPercentage() {
        return 0;
    }

    @Override
    public void startFullScreen() {

    }

    @Override
    public void stopFullScreen() {

    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void setMute(boolean isMute) {

    }

    @Override
    public boolean isMute() {
        return false;
    }

    @Override
    public void setScreenScaleType(int screenScaleType) {

    }

    @Override
    public void setSpeed(float speed) {

    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public long getTcpSpeed() {
        return 0;
    }

    @Override
    public void replay(boolean resetPosition) {

    }

    @Override
    public void setMirrorRotation(boolean enable) {

    }

    @Override
    public Bitmap doScreenShot() {
        return null;
    }

    @Override
    public int[] getVideoSize() {
        return new int[0];
    }

    @Override
    public void setRotation(float rotation) {

    }

    @Override
    public void startTinyScreen() {

    }

    @Override
    public void stopTinyScreen() {

    }

    @Override
    public boolean isTinyScreen() {
        return false;
    }

//    public ListIjkVideoView(@NonNull Context context) {
//        super(context);
//    }
//
//    public ListIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ListIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    public void onCompletion() {
//        super.onCompletion();
//        skipToNext();
//    }
//
//    /**
//     * 播放下一条视频
//     */
//    private void playNext() {
//        VideoModel videoModel = mVideoModels.get(mCurrentVideoPosition);
//        if (videoModel != null) {
//            mCurrentUrl = videoModel.url;
//            mCurrentPosition = 0;
//            setVideoController(videoModel.controller);
//        }
//    }
//
//    /**
//     * 设置一个列表的视频
//     */
//    public void setVideos(List<VideoModel> videoModels) {
//        this.mVideoModels = videoModels;
//        playNext();
//    }
//
//    /**
//     * 播放下一条视频，可用于跳过广告
//     */
//    @Override
//    public void skipToNext() {
//        mCurrentVideoPosition++;
//        if (mVideoModels != null && mVideoModels.size() > 1) {
//            if (mCurrentVideoPosition >= mVideoModels.size()) return;
//            playNext();
//            addDisplay();
//            startPrepare(true);
//        }
//    }
}
