package com.health.pusun.device;

import android.app.smdt.SmdtManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.BasicData;
import com.health.pusun.device.utils.ShowHelper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParaControlActivity extends SerialPortActivity {

    EditText mReception;
    private EditText degree1, degree2, velo, degree1test, degree2test, velotest;
    private Button send, open, send_pressure, send_pressure_back, sendtest;
    private EditText high, middle, low, high_back, middle_back, low_back;
    private Spinner spinner;
    private static int num;
    private TextView reverse;
    private String password;
    private TextView qian, hou;
    private LinearLayout qianlay, houlay, test;
    private TextView mTextView;
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x01;
    private ArrayList<String> mImagePaths;
    private Button adpic, advid;
    private EditText on, stop, on_fast, stop_fast;
    private Button send_duration, send_duration_fast;
//    private SmdtManager smdtManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_control);
        password = getIntent().getStringExtra("password");
        //setTitle("Loopback test");
        mReception = (EditText) findViewById(R.id.EditTextReception);
        qian = findViewById(R.id.qian);
        hou = findViewById(R.id.hou);
        qianlay = findViewById(R.id.qianlay);
        houlay = findViewById(R.id.houlay);
        degree1 = findViewById(R.id.degree1);
        degree2 = findViewById(R.id.degree2);
        velo = findViewById(R.id.velo);
        test = findViewById(R.id.test);
        degree1test = findViewById(R.id.degree1test);
        degree2test = findViewById(R.id.degree2test);
        velotest = findViewById(R.id.velotest);
        high = findViewById(R.id.high);
        middle = findViewById(R.id.middle);
        low = findViewById(R.id.low);
        high_back = findViewById(R.id.high_back);
        middle_back = findViewById(R.id.middle_back);
        low_back = findViewById(R.id.low_back);
        reverse = findViewById(R.id.reverse);
//        mTextView = findViewById(R.id.tv_select_images);
//        smdtManager = SmdtManager.create(this);
        adpic = findViewById(R.id.adpic);
        advid = findViewById(R.id.advid);
        on = findViewById(R.id.on);
        stop = findViewById(R.id.stop);
        send_duration = findViewById(R.id.send_duration);
        on.setHint("" + App.getIntUserPreference("on"));
        stop.setHint("" + App.getIntUserPreference("stop"));
        on_fast = findViewById(R.id.on_fast);
        stop_fast = findViewById(R.id.stop_fast);
        send_duration_fast = findViewById(R.id.send_duration_fast);
        on_fast.setHint("" + App.getIntUserPreference("onfast"));
        stop_fast.setHint("" + App.getIntUserPreference("stopfast"));
        send_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!on.getText().toString().trim().equals(""))&& (!stop.getText().toString().trim().equals(""))) {
                    short a1 = Short.parseShort(on.getText().toString().trim());
                    short a2 = Short.parseShort(stop.getText().toString().trim());
                    byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) 0x01, (byte) 0xA5};
                    try {
                        mOutputStream.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    App.saveUserPreference("on", a1);
                    App.saveUserPreference("stop", a2);
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全两个参数！");
                }
            }
        });
        send_duration_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!on_fast.getText().toString().trim().equals(""))&& (!stop_fast.getText().toString().trim().equals(""))) {
                    short a1 = Short.parseShort(on_fast.getText().toString().trim());
                    short a2 = Short.parseShort(stop_fast.getText().toString().trim());
                    byte[] buf = new byte[]{(byte) 0xAA, (byte) 0x76, (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) (a1), (byte) (a2), (byte) 0x01, (byte) 0xA5};
                    try {
                        mOutputStream.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    App.saveUserPreference("onfast", a1);
                    App.saveUserPreference("stopfast", a2);
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全两个参数！");
                }
            }
        });
//        adpic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ("null".equals(smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0)) || null == smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0)) {
//                    ShowHelper.toastShort(ParaControlActivity.this, "未插入U盘");
//                }
//                List<String> s = getPicFilesAllName(smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0));
//                if(s == null || s.size() == 0 ) ShowHelper.toastShort(ParaControlActivity.this, "U盘根目录无图片广告资源");
//                else{
//                    Intent intent = new Intent(ParaControlActivity.this, AdvertizeActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type", 1);
//                    bundle.putSerializable("list", (Serializable)s);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//        advid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ("null".equals(smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0))|| null == smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0)) {
//                    ShowHelper.toastShort(ParaControlActivity.this, "未插入U盘");
//                }
//                List<String> s = getVideoFilesAllName(smdtManager.smdtGetUSBPath(ParaControlActivity.this, 0));
//                if(s == null || s.size() == 0 ) ShowHelper.toastShort(ParaControlActivity.this, "U盘根目录无mp4视频广告资源");
//                else{
//                    Intent intent = new Intent(ParaControlActivity.this, AdvertizeActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type", 2);
//                    bundle.putSerializable("list", (Serializable)s);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });

        for(int i = 0; i < BasicData.sets.length; i++){
            if(App.getIntUserPreference("sets" + i + "0") != 0) BasicData.sets[i][0] = (short)App.getIntUserPreference("sets" + i + "0");
            if(App.getIntUserPreference("sets" + i + "1") != 0) BasicData.sets[i][1] = (short)App.getIntUserPreference("sets" + i + "1");
            if(App.getIntUserPreference("sets" + i + "2") != 0) BasicData.sets[i][2] = (short)App.getIntUserPreference("sets" + i + "2");
        }
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(ParaControlActivity.this,android.R.layout.simple_spinner_item, BasicData.names);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameKindArray);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num = i;
                degree1.setText("" + BasicData.sets[i][0]);
                degree2.setText("" + BasicData.sets[i][1]);
                velo.setText("" + BasicData.sets[i][2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelper.showAlertDialog(ParaControlActivity.this, "确定要把当前模式还原为初始设定值吗？", new DialogInterface.OnClickListener() {
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
                        degree1.setText("" + BasicData.sets[num][0]);
                        degree2.setText("" + BasicData.sets[num][1]);
                        velo.setText("" + BasicData.sets[num][2]);
                        ShowHelper.toastShort(ParaControlActivity.this, "参数已还原");
                    }
                });
            }
        });

        EditText Emission = (EditText) findViewById(R.id.EditTextEmission);
//        Emission.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Log.e("send----", "1122");
//                    mOutputStream.write("123456".getBytes());
//                    mOutputStream.write('\n');
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Emission.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                int i;
//                CharSequence t = v.getText();
//                char[] text = new char[t.length()];
//                for (i = 0; i < t.length(); i++) {
//                    text[i] = t.charAt(i);
//                }
//                try {
//                    mOutputStream.write(new String(text).getBytes());
//                    mOutputStream.write('\n');
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
        send_pressure = findViewById(R.id.send_pressure);
        send_pressure_back = findViewById(R.id.send_pressure_back);
        send = findViewById(R.id.send);
        sendtest = findViewById(R.id.sendtest);
        open = findViewById(R.id.open);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!degree1.getText().toString().trim().equals("") && !degree2.getText().toString().trim().equals("")&&!velo.getText().toString().trim().equals("")){
                    App.saveUserPreference("sets" + num + "0", Short.parseShort(degree1.getText().toString().trim()));
                    App.saveUserPreference("sets" + num + "1", Short.parseShort(degree2.getText().toString().trim()));
                    App.saveUserPreference("sets" + num + "2", Short.parseShort(velo.getText().toString().trim()));
                    BasicData.sets[num][0] = Short.parseShort(degree1.getText().toString().trim());
                    BasicData.sets[num][1] = Short.parseShort(degree2.getText().toString().trim());
                    BasicData.sets[num][2] = Short.parseShort(velo.getText().toString().trim());
                    ShowHelper.toastShort(ParaControlActivity.this, "保存成功！");
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全三个参数！");
                }

//                if(!degree1.getText().toString().trim().equals("") && !degree2.getText().toString().trim().equals("")&&!velo.getText().toString().trim().equals("")){
//                    short a1 = Short.parseShort(degree1.getText().toString().trim());
//                    short a2 = Short.parseShort(degree2.getText().toString().trim());
//                    short a3 = Short.parseShort(velo.getText().toString().trim());
//                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x55, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
//                    AppLog.e("degree1:" + degree1.getText().toString().trim() + " degree2:" + degree2.getText().toString().trim() + " velo:" + velo.getText().toString().trim());
//                    try {
//                        mOutputStream.write(buf);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全三个参数！");
//                }

            }
        });
        sendtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!degree1.getText().toString().trim().equals("") && !degree2.getText().toString().trim().equals("")&&!velo.getText().toString().trim().equals("")){
//                    App.saveUserPreference("sets" + num + "0", Short.parseShort(degree1.getText().toString().trim()));
//                    App.saveUserPreference("sets" + num + "1", Short.parseShort(degree2.getText().toString().trim()));
//                    App.saveUserPreference("sets" + num + "2", Short.parseShort(velo.getText().toString().trim()));
//                    BasicData.sets[num][0] = Short.parseShort(degree1.getText().toString().trim());
//                    BasicData.sets[num][1] = Short.parseShort(degree2.getText().toString().trim());
//                    BasicData.sets[num][2] = Short.parseShort(velo.getText().toString().trim());
//                    ShowHelper.toastShort(ParaControlActivity.this, "保存成功！");
//                }else{
//                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全三个参数！");
//                }

                if(!degree1test.getText().toString().trim().equals("") && !degree2test.getText().toString().trim().equals("")&&!velotest.getText().toString().trim().equals("")){
                    short a1 = Short.parseShort(degree1test.getText().toString().trim());
                    short a2 = Short.parseShort(degree2test.getText().toString().trim());
                    short a3 = Short.parseShort(velotest.getText().toString().trim());
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x55, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                    AppLog.e("degree1test:" + degree1test.getText().toString().trim() + " degree2test:" + degree2test.getText().toString().trim() + " velotest:" + velotest.getText().toString().trim());
                    try {
                        mOutputStream.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全三个参数！");
                }

            }
        });
        high.setHint("" + App.getIntUserPreference("high"));
        middle.setHint("" + App.getIntUserPreference("middle"));
        low.setHint("" + App.getIntUserPreference("low"));
        send_pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!high.getText().toString().trim().equals("") && !middle.getText().toString().trim().equals("")&&!low.getText().toString().trim().equals("")){
                    short a1 = Short.parseShort(high.getText().toString().trim());
                    short a2 = Short.parseShort(middle.getText().toString().trim());
                    short a3 = 1000;
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                    AppLog.e("high:" + high.getText().toString().trim() + " middle:" + middle.getText().toString().trim() + " low:" + low.getText().toString().trim());
                    try {
                        mOutputStream.write(buf);
                        App.saveUserPreference("high", a1);
                        App.saveUserPreference("middle", a2);
                        App.saveUserPreference("low", a3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全二个参数！");
                }

            }
        });
        high_back.setHint("" + App.getIntUserPreference("highback"));
        middle_back.setHint("" + App.getIntUserPreference("middleback"));
        low_back.setHint("" + App.getIntUserPreference("lowback"));
        send_pressure_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!high_back.getText().toString().trim().equals("") && !middle_back.getText().toString().trim().equals("")&&!low_back.getText().toString().trim().equals("")){
                    short a1 = Short.parseShort(high_back.getText().toString().trim());
                    short a2 = Short.parseShort(middle_back.getText().toString().trim());
                    short a3 = Short.parseShort(low_back.getText().toString().trim());
                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x58, (byte)(a1 >> 8), (byte)(a1 >> 0),  (byte)(a2 >> 8), (byte)(a2 >> 0), (byte)(a3 >> 8), (byte)(a3 >> 0), (byte)0x01, (byte)0xA5};
                    AppLog.e("high:" + high_back.getText().toString().trim() + " middle:" + middle_back.getText().toString().trim() + " low:" + low_back.getText().toString().trim());
                    try {
                        mOutputStream.write(buf);
                        App.saveUserPreference("highback", a1);
                        App.saveUserPreference("middleback", a2);
                        App.saveUserPreference("lowback", a3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ShowHelper.toastShort(ParaControlActivity.this, "请输入完全三个参数！");
                }

            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        startActivity(new Intent(ParaControlActivity.this, PeopleAlertActivity.class));
                    }
                }, 500);


//                    byte[] buf = new byte[]{(byte)0xAA, (byte)0x56, (byte)(0x01), (byte)(0x01),  (byte)(0x01), (byte)(0x01), (byte)(0x01), (byte)(0x01), (byte)0x01, (byte)0xA5};
//                    AppLog.e("opendoor!");
//                try {
//                    mOutputStream.write(buf);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });

        if(password.equals("12345")){
            qian.setVisibility(View.GONE);hou.setVisibility(View.GONE);qianlay.setVisibility(View.GONE); test.setVisibility(View.GONE);
            houlay.setVisibility(View.GONE);send_pressure.setVisibility(View.GONE);send_pressure_back.setVisibility(View.GONE);sendtest.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (mReception != null) {
                    mReception.append(new String(buffer, 0, size));
                }
            }
        });
    }
    public static List<String> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    public static List<String> getPicFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if(!files[i].isDirectory() && (files[i].getAbsolutePath().toString().contains("jpg")|| files[i].getAbsolutePath().toString().contains("png")))
                s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    public static List<String> getVideoFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if(!files[i].isDirectory() && (files[i].getAbsolutePath().toString().contains("mp4")))
                s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("当前选中图片路径：\n\n");
//            for (int i = 0; i < mImagePaths.size(); i++) {
//                stringBuffer.append(mImagePaths.get(i) + "\n\n");
//            }
////            mTextView.setText(stringBuffer.toString());
//        }
    }
}
