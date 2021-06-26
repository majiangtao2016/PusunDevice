package com.health.pusun.device.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.health.pusun.device.GamePlayActivity;
import com.health.pusun.device.R;

public class GameIntroduceActivity extends AppCompatActivity {

    private RelativeLayout start_ball, back_ball;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_introduce);
        start_ball =  findViewById(R.id.start_ball);
        start_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameIntroduceActivity.this, GamePlayActivity.class));
            }
        });
        back_ball = findViewById(R.id.back_ball);
        back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
