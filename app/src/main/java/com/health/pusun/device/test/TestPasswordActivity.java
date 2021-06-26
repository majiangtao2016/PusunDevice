package com.health.pusun.device.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.health.pusun.device.ConsoleActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.ParaControlActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.vo.RequestCallVo;

import java.util.HashMap;

public class TestPasswordActivity extends AppCompatActivity {
    private EditText password;
    private Button colse_btn, btn;
    String devNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_password);
//        devNum = getIntent().getStringExtra("url");
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        colse_btn = findViewById(R.id.colse_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(password.getText().toString().trim()) && "12345".equals(password.getText().toString().trim())){
                    Intent intent = new Intent(TestPasswordActivity.this, ConsoleActivity.class);
                    intent.putExtra("password", password.getText().toString().trim());
                    startActivity( intent);
                    finish();
                }else if(!"".equals(password.getText().toString().trim()) && "pusun".equals(password.getText().toString().trim())){
                    Intent intent = new Intent(TestPasswordActivity.this, ParaControlActivity.class);
                    intent.putExtra("password", password.getText().toString().trim());
                    startActivity( intent);
                    finish();
                }
//                checkPassword();
            }
        });
        colse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void checkPassword() {

        if(!"".equals(password.getText().toString().trim())){
            HashMap<String, String> params = new HashMap<>();
            params.put("devNum", devNum);
            params.put("Password",password.getText().toString().trim());
            MyHttpUtils.postAsAync(App.BASE_URL + "/api/IsDevPassword", params, new MyJsonCallbalk() {
                @Override
                public void onError(Exception e, int code) {
                    AppLog.e("okHttpUtil.getAsync failed! request = " + e);
                }
                @Override
                public void onResponse(RequestCallVo requestCallVo) {
                    AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                    if (requestCallVo.getType() == 1) {
                        startActivity( new Intent(TestPasswordActivity.this, ModeSelectTestActivity.class));
                        finish();
                    } else {
                        Toast.makeText(TestPasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
