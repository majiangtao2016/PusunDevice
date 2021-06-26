package com.health.pusun.device;

import android.app.smdt.SmdtManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.BasicData;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.RequestDataMsg;

import org.simple.eventbus.EventBus;

public class PointChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView degree1, degree2, velo;
    private Button send;
    private TextView tip_categary;
    private TextView reverse;
    private TextView add_lr, minus_lr, minus_ud, add_ud,minus_ve, add_ve, back;
    private int digit1, digit2, digitVelo;
    private int categary;   // 模式
    private int pointNum;
    private String selectTitle;
    private int num;
//    private int centerValue = 1500;
    private int centerValue = 1260;
    private int degree2Base = 1300;
    private int veloBase = 30;
    private int degree1Show, degree2Show, veloShow;
    private int[] misPos1 = {4,25,1,26,6};
    private int[] misPos2 = {19,29,10,30,21};
    private int[] misPos3 = {13,27,15,28,17};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_change);
        categary = 1;
        pointNum = getIntent().getIntExtra("pointNum", 0);
        selectTitle = getIntent().getStringExtra("title");
        tip_categary = findViewById(R.id.tip_categary);
        if(categary == 0)
        tip_categary.setText(selectTitle + "高级点位调整");
        else tip_categary.setText(selectTitle + "点位调整");
        //点位对应BasicData
        if(categary == 1){
            //初级模式点位对应
            if(pointNum == 1) num = 2;
            if(pointNum == 2) num = 0;
            if(pointNum == 3) num = 8;
            if(pointNum == 4) num = 7;
            if(pointNum == 5) num = 14;
            if(pointNum == 6) num = 12;
            if(pointNum == 7) num = 5;
            if(pointNum == 8) num = 3;
            if(pointNum == 9) num = 22;
            if(pointNum == 10) num = 20;
            if(pointNum == 11) num = 29;
            if(pointNum == 12) num = 30;
            if(pointNum == 13) num = 31;
            if(pointNum == 14) num = 32;
            if(pointNum == 15) num = 33;
            if(pointNum == 16) num = 34;
            if(pointNum == 17) num = 26;
            if(pointNum == 18) num = 25;
            if(pointNum == 19) num = 28;
            if(pointNum == 20) num = 27;
            if(pointNum == 21) num = 6;
            if(pointNum == 22) num = 1;
            if(pointNum == 23) num = 4;
            if(pointNum == 24) num = 21;
            if(pointNum == 25) num = 10;
            if(pointNum == 26) num = 19;
            if(pointNum == 27) num = 17;
            if(pointNum == 28) num = 15;
            if(pointNum == 29) num = 13;
            if(pointNum == 30) num = 9;
            if(pointNum == 31) num = 16;
            if(pointNum == 32) num = 18;
            if(pointNum == 33) num = 11;
            if(pointNum == 34) num = 23;
            if(pointNum == 35) num = 24;
//            if(pointNum == 31) num = 11;
//            if(pointNum == 32) num = 17;
//            if(pointNum == 33) num = 18;
//            if(pointNum == 34) num = 23;
//            if(pointNum == 35) num = 24;

        }else{
            //高级模式点位对应
            if(pointNum == 1) num = 2+35;
            if(pointNum == 2) num = 0+35;
            if(pointNum == 3) num = 8+35;
            if(pointNum == 4) num = 7+35;
            if(pointNum == 5) num = 14+35;
            if(pointNum == 6) num = 12+35;
            if(pointNum == 7) num = 5+35;
            if(pointNum == 8) num = 3+35;
            if(pointNum == 9) num = 22+35;
            if(pointNum == 10) num = 20+35;
            if(pointNum == 11) num = 29+35;
            if(pointNum == 12) num = 30+35;
            if(pointNum == 13) num = 31+35;
            if(pointNum == 14) num = 32+35;
            if(pointNum == 15) num = 33+35;
            if(pointNum == 16) num = 34+35;
            if(pointNum == 17) num = 26+35;
            if(pointNum == 18) num = 25+35;
            if(pointNum == 19) num = 28+35;
            if(pointNum == 20) num = 27+35;
            if(pointNum == 21) num = 6+35;
            if(pointNum == 22) num = 1+35;
            if(pointNum == 23) num = 4+35;
            if(pointNum == 24) num = 21+35;
            if(pointNum == 25) num = 10+35;
            if(pointNum == 26) num = 19+35;
            if(pointNum == 27) num = 17+35;
            if(pointNum == 28) num = 15+35;
            if(pointNum == 29) num = 13+35;
            if(pointNum == 30) num = 9+35;
            if(pointNum == 31) num = 16+35;
            if(pointNum == 32) num = 18+35;
            if(pointNum == 33) num = 11+35;
            if(pointNum == 34) num = 23+35;
            if(pointNum == 35) num = 24+35;
        }
        //setTitle("Loopback test");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        degree1 = findViewById(R.id.degree1);
        degree2 = findViewById(R.id.degree2);
        velo = findViewById(R.id.velo);
        degree1Show = (Math.abs(BasicData.sets[num][0]-centerValue))/30;
        degree2Show = (degree2Base-BasicData.sets[num][1])/15;
        veloShow = (BasicData.sets[num][2]-veloBase)/2;
        degree1.setText(degree1Show + "");
        degree2.setText(degree2Show+"");
        velo.setText("" + veloShow);
        add_lr = findViewById(R.id.add_lr);
        add_lr.setOnClickListener(this);
        add_ud = findViewById(R.id.add_ud);
        add_ud.setOnClickListener(this);
        add_ve = findViewById(R.id.add_ve);
        add_ve.setOnClickListener(this);
        minus_lr = findViewById(R.id.minus_lr);
        minus_lr.setOnClickListener(this);
        minus_ud = findViewById(R.id.minus_ud);
        minus_ud.setOnClickListener(this);
        minus_ve = findViewById(R.id.minus_ve);
        minus_ve.setOnClickListener(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        for(int i = 0; i < BasicData.sets.length; i++){
            if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.sets[i][0] = (short)App.getIntUserPreference("sets" + i + "0");
            if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.sets[i][1] = (short)App.getIntUserPreference("sets" + i + "1");
            if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.sets[i][2] = (short)App.getIntUserPreference("sets" + i + "2");
        }
        reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelper.showAlertDialog(PointChangeActivity.this, "确定要还原为初始设定值吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int in) {
//                        for(int i = 0; i <BasicData.origins.length; i++){
                        App.saveUserPreference("sets" + num + "0", BasicData.origins[num][0]);
                        App.saveUserPreference("sets" + num + "1", BasicData.origins[num][1]);
                        App.saveUserPreference("sets" + num + "2", BasicData.origins[num][2]);
                        if(App.getIntUserPreference("sets" + num + "0") != 0) BasicData.sets[num][0] = (short)App.getIntUserPreference("sets" + num + "0");
                        if(App.getIntUserPreference("sets" + num + "1") != 0) BasicData.sets[num][1] = (short)App.getIntUserPreference("sets" + num + "1");
                        if(App.getIntUserPreference("sets" + num + "2") != 0) BasicData.sets[num][2] = (short)App.getIntUserPreference("sets" + num + "2");
//                        }
                        degree1Show = (Math.abs(BasicData.sets[num][0]-centerValue))/30;
                        degree2Show = (degree2Base-BasicData.sets[num][1])/15;
                        veloShow = (BasicData.sets[num][2]-veloBase)/2;
                        degree1.setText(degree1Show + "");
                        degree2.setText(degree2Show+"");
                        velo.setText("" + veloShow);
                        ShowHelper.toastShort(PointChangeActivity.this, "参数已还原");
//                        digit1 = 0; digit2 = 0; digitVelo = 0;
                    }
                });
            }
        });

        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!degree1.getText().toString().trim().equals("") && !degree2.getText().toString().trim().equals("")&&!velo.getText().toString().trim().equals("")){
                ShowHelper.showProgressDialog(PointChangeActivity.this, "参数修改中...");
//                int lr = BasicData.sets[num][0] + digit1*50;
////                if(lr < 300) lr = 300;  if(lr > 2950) lr = 2950;
////                int ud = BasicData.sets[num][1] - digit2*50;
////                if(ud < 100) ud = 100;  if(ud > 1350) ud = 1350;
////                int ve = BasicData.sets[num][2] + digitVelo;
////                if(ve < 30) ve = 30;  if(ve > 230) ve = 230;
                int lr, ud, ve;
                if(BasicData.sets[num][0] <= centerValue ){
                    lr = centerValue - degree1Show*30;
                }else{
                    lr = centerValue + degree1Show*30;
                }
                if(lr < 50) lr = 50;  if(lr > 2950) lr = 2950;
                ud = degree2Base - degree2Show*15;
                if(ud < 100) ud = 100;  if(ud > 1350) ud = 1350;
                ve = veloBase + veloShow*2;
                if(ve < 30) ve = 30;  if(ve > 230) ve = 230;
                App.saveUserPreference("sets" + num + "0", lr);
                App.saveUserPreference("sets" + num + "1", ud);
                App.saveUserPreference("sets" + num + "2", ve);
                AppLog.e("sets:" + App.getIntUserPreference("sets" + num + "0") + "   " + App.getIntUserPreference("sets" + num + "1") + "  " + App.getIntUserPreference("sets" + num + "2"));
                BasicData.sets[num][0] = (short)lr;
                BasicData.sets[num][1] = (short)ud;
                BasicData.sets[num][2] = (short)ve;
                ShowHelper.toastShort(PointChangeActivity.this, "保存成功！");
                ShowHelper.showProgressDialog(PointChangeActivity.this, "参数修改成功！");
                for(int i = 0; i < misPos1.length; i++){
                    if(num == misPos1[i]){
                        App.saveUserPreference("sets" + misPos1[0] + "2", ve); App.saveUserPreference("sets" + misPos1[1] + "2", ve);App.saveUserPreference("sets" + misPos1[2] + "2", ve);App.saveUserPreference("sets" + misPos1[3] + "2", ve);App.saveUserPreference("sets" + misPos1[4] + "2", ve);
                        BasicData.sets[misPos1[0]][2] = (short)App.getIntUserPreference("sets" + misPos1[0] + "2");
                        BasicData.sets[misPos1[1]][2] = (short)App.getIntUserPreference("sets" + misPos1[1] + "2");
                        BasicData.sets[misPos1[2]][2] = (short)App.getIntUserPreference("sets" + misPos1[2] + "2");
                        BasicData.sets[misPos1[3]][2] = (short)App.getIntUserPreference("sets" + misPos1[3] + "2");
                        BasicData.sets[misPos1[4]][2] = (short)App.getIntUserPreference("sets" + misPos1[4] + "2");
                        App.saveUserPreference("sets" + misPos1[0] + "1", ud); App.saveUserPreference("sets" + misPos1[1] + "1", ud);App.saveUserPreference("sets" + misPos1[2] + "1", ud);App.saveUserPreference("sets" + misPos1[3] + "1", ud);App.saveUserPreference("sets" + misPos1[4] + "1", ud);
                        BasicData.sets[misPos1[0]][1] = (short)App.getIntUserPreference("sets" + misPos1[0] + "1");
                        BasicData.sets[misPos1[1]][1] = (short)App.getIntUserPreference("sets" + misPos1[1] + "1");
                        BasicData.sets[misPos1[2]][1] = (short)App.getIntUserPreference("sets" + misPos1[2] + "1");
                        BasicData.sets[misPos1[3]][1] = (short)App.getIntUserPreference("sets" + misPos1[3] + "1");
                        BasicData.sets[misPos1[4]][1] = (short)App.getIntUserPreference("sets" + misPos1[4] + "1");
//                        if((i == 0) || (i == 4)){
//                            App.saveUserPreference("sets" + misPos1[0] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos1[4] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos1[0]][0] = (short)App.getIntUserPreference("sets" + misPos1[0] + "0");
//                            BasicData.sets[misPos1[4]][0] = (short)App.getIntUserPreference("sets" + misPos1[4] + "0");
//                        }
//                        if((i == 1) || (i == 3 )){
//                            App.saveUserPreference("sets" + misPos1[1] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos1[3] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos1[1]][0] = (short)App.getIntUserPreference("sets" + misPos1[1] + "0");
//                            BasicData.sets[misPos1[3]][0] = (short)App.getIntUserPreference("sets" + misPos1[3] + "0");
//                        }
                        break;
                    }
                }
                for(int i = 0; i < misPos2.length; i++){
                    if(num == misPos2[i]){
                        App.saveUserPreference("sets" + misPos2[0] + "2", ve); App.saveUserPreference("sets" + misPos2[1] + "2", ve);App.saveUserPreference("sets" + misPos2[2] + "2", ve);App.saveUserPreference("sets" + misPos2[3] + "2", ve);App.saveUserPreference("sets" + misPos2[4] + "2", ve);
                        BasicData.sets[misPos2[0]][2] = (short)App.getIntUserPreference("sets" + misPos2[0] + "2");
                        BasicData.sets[misPos2[1]][2] = (short)App.getIntUserPreference("sets" + misPos2[1] + "2");
                        BasicData.sets[misPos2[2]][2] = (short)App.getIntUserPreference("sets" + misPos2[2] + "2");
                        BasicData.sets[misPos2[3]][2] = (short)App.getIntUserPreference("sets" + misPos2[3] + "2");
                        BasicData.sets[misPos2[4]][2] = (short)App.getIntUserPreference("sets" + misPos2[4] + "2");
                        App.saveUserPreference("sets" + misPos2[0] + "1", ud); App.saveUserPreference("sets" + misPos2[1] + "1", ud);App.saveUserPreference("sets" + misPos2[2] + "1", ud);App.saveUserPreference("sets" + misPos2[3] + "1", ud);App.saveUserPreference("sets" + misPos2[4] + "1", ud);
                        BasicData.sets[misPos2[0]][1] = (short)App.getIntUserPreference("sets" + misPos2[0] + "1");
                        BasicData.sets[misPos2[1]][1] = (short)App.getIntUserPreference("sets" + misPos2[1] + "1");
                        BasicData.sets[misPos2[2]][1] = (short)App.getIntUserPreference("sets" + misPos2[2] + "1");
                        BasicData.sets[misPos2[3]][1] = (short)App.getIntUserPreference("sets" + misPos2[3] + "1");
                        BasicData.sets[misPos2[4]][1] = (short)App.getIntUserPreference("sets" + misPos2[4] + "1");
//                        if((i == 0) || (i == 4)){
//                            App.saveUserPreference("sets" + misPos2[0] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos2[4] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos2[0]][0] = (short)App.getIntUserPreference("sets" + misPos2[0] + "0");
//                            BasicData.sets[misPos2[4]][0] = (short)App.getIntUserPreference("sets" + misPos2[4] + "0");
//                        }
//                        if((i == 1) || (i == 3 )){
//                            App.saveUserPreference("sets" + misPos2[1] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos2[3] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos2[1]][0] = (short)App.getIntUserPreference("sets" + misPos2[1] + "0");
//                            BasicData.sets[misPos2[3]][0] = (short)App.getIntUserPreference("sets" + misPos2[3] + "0");
//                        }
                        break;
                    }
                }
                for(int i = 0; i < misPos3.length; i++){
                    if(num == misPos3[i]){
                        App.saveUserPreference("sets" + misPos3[0] + "2", ve); App.saveUserPreference("sets" + misPos3[1] + "2", ve);App.saveUserPreference("sets" + misPos3[2] + "2", ve);App.saveUserPreference("sets" + misPos3[3] + "2", ve);App.saveUserPreference("sets" + misPos3[4] + "2", ve);
                        BasicData.sets[misPos3[0]][2] = (short)App.getIntUserPreference("sets" + misPos3[0] + "2");
                        BasicData.sets[misPos3[1]][2] = (short)App.getIntUserPreference("sets" + misPos3[1] + "2");
                        BasicData.sets[misPos3[2]][2] = (short)App.getIntUserPreference("sets" + misPos3[2] + "2");
                        BasicData.sets[misPos3[3]][2] = (short)App.getIntUserPreference("sets" + misPos3[3] + "2");
                        BasicData.sets[misPos3[4]][2] = (short)App.getIntUserPreference("sets" + misPos3[4] + "2");
                        App.saveUserPreference("sets" + misPos3[0] + "1", ud); App.saveUserPreference("sets" + misPos3[1] + "1", ud);App.saveUserPreference("sets" + misPos3[2] + "1", ud);App.saveUserPreference("sets" + misPos3[3] + "1", ud);App.saveUserPreference("sets" + misPos3[4] + "1", ud);
                        BasicData.sets[misPos3[0]][1] = (short)App.getIntUserPreference("sets" + misPos3[0] + "1");
                        BasicData.sets[misPos3[1]][1] = (short)App.getIntUserPreference("sets" + misPos3[1] + "1");
                        BasicData.sets[misPos3[2]][1] = (short)App.getIntUserPreference("sets" + misPos3[2] + "1");
                        BasicData.sets[misPos3[3]][1] = (short)App.getIntUserPreference("sets" + misPos3[3] + "1");
                        BasicData.sets[misPos3[4]][1] = (short)App.getIntUserPreference("sets" + misPos3[4] + "1");
//                        if((i == 0) || (i == 4)){
//                            App.saveUserPreference("sets" + misPos3[0] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos3[4] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos3[0]][0] = (short)App.getIntUserPreference("sets" + misPos3[0] + "0");
//                            BasicData.sets[misPos3[4]][0] = (short)App.getIntUserPreference("sets" + misPos3[4] + "0");
//                        }
//                        if((i == 1) || (i == 3 )){
//                            App.saveUserPreference("sets" + misPos3[1] + "0", centerValue + degree1Show*30); App.saveUserPreference("sets" + misPos3[3] + "0", centerValue - degree1Show*30);
//                            BasicData.sets[misPos3[1]][0] = (short)App.getIntUserPreference("sets" + misPos3[1] + "0");
//                            BasicData.sets[misPos3[3]][0] = (short)App.getIntUserPreference("sets" + misPos3[3] + "0");
//                        }
                        break;
                    }
                }
//                digit1 = 0; digit2 = 0; digitVelo = 0;
                degree1Show = (Math.abs(BasicData.sets[num][0]-centerValue))/30;
                degree2Show = (degree2Base-BasicData.sets[num][1])/15;
                veloShow = (BasicData.sets[num][2]-veloBase)/2;
                degree1.setText(degree1Show + "");
                degree2.setText(degree2Show+"");
                velo.setText("" + veloShow);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public synchronized void run() {
                        ShowHelper.dismissProgressDialog();
                    }
                }, 1500);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestDataMsg requestDataMsg = new RequestDataMsg();
        EventBus.getDefault().post(requestDataMsg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_lr:
//                 += 1;
//                if(digit1 >= 10) digit1 = 10;
//                degree1.setText("" + digit1);
                degree1Show += 1;
                if(degree1Show >= 50) degree1Show = 50;
                degree1.setText(degree1Show + "");
                break;
            case R.id.minus_lr:
                degree1Show -= 1;
                if(degree1Show <= 0) degree1Show = 1;
                degree1.setText("" + degree1Show);
                break;
            case R.id.add_ud:
//                digit2 += 1;
//                if(digit2 >= 10) digit2 = 10;
//                degree2.setText("" + digit2);
                degree2Show += 1;
                if(degree2Show >= 80) degree1Show = 80;
                degree2.setText(degree2Show + "");
                break;
            case R.id.minus_ud:
//                digit2 -= 1;
//                if(digit2 <= -10) digit2 = -10;
//                degree2.setText("" + digit2);
                degree2Show -= 1;
                if(degree2Show <= 0) degree2Show = 1;
                degree2.setText("" + degree2Show);
                break;
            case R.id.add_ve:
//                digitVelo += 1;
//                if(digitVelo >= 10) digitVelo = 10;
//                velo.setText("" + digitVelo);
                veloShow += 1;
                if(veloShow >= 100) veloShow = 100;
                velo.setText("" + veloShow);
                break;
            case R.id.minus_ve:
                veloShow -= 1;
                if(veloShow <= 0) veloShow = 1;
                velo.setText("" + veloShow);
                break;
        }
    }
}