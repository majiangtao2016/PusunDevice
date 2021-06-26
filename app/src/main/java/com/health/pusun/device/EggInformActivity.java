package com.health.pusun.device;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.pusun.device.vo.eventbusmsg.EggGameScoreMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;

import org.simple.eventbus.EventBus;

public class EggInformActivity extends Activity {
    private TextView close, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_inform);
        EventBus.getDefault().register(this);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EggInformActivity.this, CountDownActivity.class);
                intent.putExtra("state", 3);
                intent.putExtra("type", 9999);
                startActivity(intent);
                finish();
            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EggGameScoreMsg eggGameScoreMsg = new EggGameScoreMsg();
                eggGameScoreMsg.score = -1;
                EventBus.getDefault().post(eggGameScoreMsg);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
