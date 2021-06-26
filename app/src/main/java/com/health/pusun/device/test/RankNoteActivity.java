package com.health.pusun.device.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.health.pusun.device.R;
import com.health.pusun.device.adapter.TraingRecordListRvAdapter;
import com.health.pusun.device.vo.UserRankVo;

import java.util.ArrayList;
import java.util.List;

public class RankNoteActivity extends Activity {

    private TraingRecordListRvAdapter traingRecordListRvAdapter;
    private List<UserRankVo> userRankVos = new ArrayList<>();
    private RecyclerView list_record;
    private int score;
    private String user;
    private ImageView close;
    private TextView scoretv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_note);
        list_record = findViewById(R.id.list_record);
        scoretv = findViewById(R.id.score);
        score = getIntent().getIntExtra("score", 0);
        user = getIntent().getStringExtra("user");
        scoretv.setText("本次挑战得分："+score + "分");
        userRankVos = JSON.parseArray(user, UserRankVo.class);
        traingRecordListRvAdapter = new TraingRecordListRvAdapter(userRankVos, RankNoteActivity.this);
        list_record.setNestedScrollingEnabled(false);
        list_record.setLayoutManager(new GridLayoutManager(RankNoteActivity.this, 1));
        list_record.setAdapter(traingRecordListRvAdapter);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
