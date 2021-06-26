package com.health.pusun.device.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.pusun.device.R;

public class FailNoteActivity extends Activity {

    private ImageView close;
    private TextView score_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_note);
        int score = getIntent().getIntExtra("score", 0);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        score_tv = findViewById(R.id.score_tv);
        score_tv.setText(score + "分");

    }
}
