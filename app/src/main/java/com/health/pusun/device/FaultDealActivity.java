package com.health.pusun.device;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dueeeke.videoplayer.player.VideoView;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.views.ListIjkVideoView;
import com.health.pusun.device.vo.eventbusmsg.OpenDoorMsg;

import org.simple.eventbus.EventBus;

import java.io.File;

public class FaultDealActivity extends Activity {

    private TextView close;
    private VideoView ijkVideoView;
    private int state, type;
    private static final String URL_LOCAL = Environment.getExternalStorageDirectory() + File.separator
            + "/MyXingCheCamera3GP/";
//    private Button open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_deal);
        EventBus.getDefault().register(this);
        state = getIntent().getIntExtra("state", 0);
        type = getIntent().getIntExtra("type", 0);
        ijkVideoView = findViewById(R.id.player);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(state != 0 && type != 0){
                    Intent intent = new Intent(FaultDealActivity.this, CountDownActivity.class);
                    intent.putExtra("state", state);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    finish();
                }else{
                    finish();
                }

            }
        });
//        open = findViewById(R.id.open);
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenDoorMsg openDoorMsg = new OpenDoorMsg();
//                EventBus.getDefault().post(openDoorMsg);
//                ShowHelper.toastShort(FaultDealActivity.this, "?????????????????????????????????????????????");
//            }
//        });
//        ijkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
        ijkVideoView.setUrl(Uri.fromFile(new File(URL_LOCAL + "self_solve.mp4")).toString(), null); //??????????????????
//        StandardVideoController controller = new StandardVideoController(this);
        ijkVideoView.setLooping(true);
//        ijkVideoView.setVideoController(controller); //???????????????????????????????????????BaseVideoController
//        controller.setVisibility(View.GONE);
        ijkVideoView.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ijkVideoView.release();
    }
}
