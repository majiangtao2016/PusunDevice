package com.health.pusun.device;

import android.app.Dialog;
import android.app.smdt.SmdtManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.ShowHelper;

public class ConsoleActivity extends AppCompatActivity implements View.OnClickListener {

    private String password;
    private TextView back;
//    private SmdtManager smdtManager;
    private int digit1, digit2, digitVelo;
    private EditText password_startball;
    private TextView pass_btn;
    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm;
    private Dialog dialog;
    private int categary;   // 模式
    private int pointNum;
    private String selectTitle;
    private TextView point1, point2, point3,point4, point5, point6,point7, point8, point9,point10, point11, point12,point13, point14, point15,point16, point17, point18,
            point19, point20, point21,point22, point23, point24, point25, point26, point27, point28, point29, point30,point31, point32, point33, point34, point35;
    private LinearLayout layout1, layout2,layout3,layout4,layout5,layout6;
    private int showNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        password = getIntent().getStringExtra("password");
        showNum = getIntent().getIntExtra("num", 0);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        password_startball = findViewById(R.id.password);
        pass_btn = findViewById(R.id.pass_btn);
        layout1 = findViewById(R.id.layout1);layout2 = findViewById(R.id.layout2);layout3= findViewById(R.id.layout3);layout4 = findViewById(R.id.layout4);layout5 = findViewById(R.id.layout5);layout6 = findViewById(R.id.layout6);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (null == App.getStringUserPreference("passball") || "".equals(App.getStringUserPreference("passball"))) {
            password_startball.setText("8888");
            App.saveUserPreference("passball", "8888");
        } else password_startball.setText(App.getStringUserPreference("passball"));
        pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password_startball.getText().toString().trim().length() == 4) {
                    ShowHelper.showAlertDialog(ConsoleActivity.this, "开始练球密码已修改为：" + password_startball.getText().toString().trim());
                    App.saveUserPreference("passball", "" + password_startball.getText().toString().trim());
                } else {
                    ShowHelper.toastShort(ConsoleActivity.this, "密码位数必须为四位数字。");
                }
            }
        });
        point1 = findViewById(R.id.point1);point1.setOnClickListener(this);
        point2 = findViewById(R.id.point2);point2.setOnClickListener(this);
        point3 = findViewById(R.id.point3);point3.setOnClickListener(this);
        point4 = findViewById(R.id.point4);point4.setOnClickListener(this);
        point5 = findViewById(R.id.point5);point5.setOnClickListener(this);
        point6 = findViewById(R.id.point6);point6.setOnClickListener(this);
        point7 = findViewById(R.id.point7);point7.setOnClickListener(this);
        point8 = findViewById(R.id.point8);point8.setOnClickListener(this);
        point9 = findViewById(R.id.point9);point9.setOnClickListener(this);
        point10 = findViewById(R.id.point10);point10.setOnClickListener(this);
        point11 = findViewById(R.id.point11);point11.setOnClickListener(this);
        point12 = findViewById(R.id.point12);point12.setOnClickListener(this);
        point13 = findViewById(R.id.point13);point13.setOnClickListener(this);
        point14 = findViewById(R.id.point14);point14.setOnClickListener(this);
        point15 = findViewById(R.id.point15);point15.setOnClickListener(this);
        point16 = findViewById(R.id.point16);point16.setOnClickListener(this);
        point17 = findViewById(R.id.point17);point17.setOnClickListener(this);
        point18 = findViewById(R.id.point18);point18.setOnClickListener(this);
        point19 = findViewById(R.id.point19);point19.setOnClickListener(this);
        point20 = findViewById(R.id.point20);point20.setOnClickListener(this);
        point21 = findViewById(R.id.point21);point21.setOnClickListener(this);
        point22 = findViewById(R.id.point22);point22.setOnClickListener(this);
        point23 = findViewById(R.id.point23);point23.setOnClickListener(this);
        point24 = findViewById(R.id.point24);point24.setOnClickListener(this);
        point25 = findViewById(R.id.point25);point25.setOnClickListener(this);
        point26 = findViewById(R.id.point26);point26.setOnClickListener(this);
        point27 = findViewById(R.id.point27);point27.setOnClickListener(this);
        point28 = findViewById(R.id.point28);point28.setOnClickListener(this);
        point29 = findViewById(R.id.point29);point29.setOnClickListener(this);
        point30 = findViewById(R.id.point30);point30.setOnClickListener(this);
        point31 = findViewById(R.id.point31);point31.setOnClickListener(this);
        point32 = findViewById(R.id.point32);point32.setOnClickListener(this);
        point33 = findViewById(R.id.point33);point33.setOnClickListener(this);
        point34 = findViewById(R.id.point34);point34.setOnClickListener(this);
        point35 = findViewById(R.id.point35);point35.setOnClickListener(this);

//        smdtManager = SmdtManager.create(this);
        if(showNum == 0){
            layout1.setVisibility(View.GONE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.GONE);layout6.setVisibility(View.VISIBLE);
        }
        if(showNum == 1){
            layout1.setVisibility(View.VISIBLE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.VISIBLE);layout6.setVisibility(View.GONE);
            point5.setVisibility(View.GONE);point6.setVisibility(View.GONE);point7.setVisibility(View.GONE);point8.setVisibility(View.GONE);point9.setVisibility(View.GONE);point10.setVisibility(View.GONE);point31.setVisibility(View.GONE); point32.setVisibility(View.GONE); point34.setVisibility(View.GONE); point35.setVisibility(View.GONE);
        }
        if(showNum == 2){
            layout1.setVisibility(View.VISIBLE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.GONE);layout6.setVisibility(View.GONE);
            point1.setVisibility(View.GONE);point2.setVisibility(View.GONE);point3.setVisibility(View.GONE);point4.setVisibility(View.GONE);point7.setVisibility(View.GONE);point8.setVisibility(View.GONE);point9.setVisibility(View.GONE);point10.setVisibility(View.GONE);
        }
        if(showNum == 3){
            layout1.setVisibility(View.VISIBLE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.GONE);layout6.setVisibility(View.GONE);
            point1.setVisibility(View.GONE);point2.setVisibility(View.GONE);point3.setVisibility(View.GONE);point4.setVisibility(View.GONE);point5.setVisibility(View.GONE);point6.setVisibility(View.GONE);
        }
        if(showNum == 4){
            layout1.setVisibility(View.VISIBLE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.VISIBLE);layout6.setVisibility(View.GONE);
            point30.setVisibility(View.GONE); point33.setVisibility(View.GONE);
        }
        if(showNum == 5){
            layout1.setVisibility(View.GONE);layout4.setVisibility(View.GONE);layout5.setVisibility(View.GONE);layout6.setVisibility(View.GONE);
        }
        if(showNum == 6){
            layout1.setVisibility(View.GONE);layout4.setVisibility(View.VISIBLE);layout5.setVisibility(View.GONE);layout6.setVisibility(View.GONE);
        }
    }

    public void showDialog() {
        Intent intent = new Intent(ConsoleActivity.this, PointChangeActivity.class);
        intent.putExtra("categary", 1);
        intent.putExtra("pointNum", pointNum);
        intent.putExtra("title", selectTitle);
        startActivity(intent);

//        if(dialog!= null) dialog.dismiss();
//        dialog = new Dialog(ConsoleActivity.this, R.style.InputDialogStyle);
//        inflate = LayoutInflater.from(ConsoleActivity.this).inflate(R.layout.point_mode_dialog, null);
//        title = inflate.findViewById(R.id.title);
//        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
//        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
//        cancel = (TextView) inflate.findViewById(R.id.cancel);
//        confirm = (TextView) inflate.findViewById(R.id.confirm);
//        title.setText("请选择" + selectTitle + "模式");
//        cancel.setOnClickListener(this);
//        confirm.setOnClickListener(this);
//        categary = 2;
//        choosePhoto.setOnClickListener(this);
//        takePhoto.setOnClickListener(this);
//        dialog.setContentView(inflate);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;
//        dialogWindow.setAttributes(lp);
//        dialog.show();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.point1:
                pointNum = 1;
                selectTitle = point1.getText().toString().trim();
                showDialog();
                break;
            case R.id.point2:
                pointNum = 2;
                selectTitle = point2.getText().toString().trim();
                showDialog();
                break;
            case R.id.point3:
                pointNum = 3;
                selectTitle = point3.getText().toString().trim();
                showDialog();
                break;
            case R.id.point4:
                pointNum = 4;
                selectTitle = point4.getText().toString().trim();
                showDialog();
                break;
            case R.id.point5:
                pointNum = 5;
                selectTitle = point5.getText().toString().trim();
                showDialog();
                break;
            case R.id.point6:
                pointNum = 6;
                selectTitle = point6.getText().toString().trim();
                showDialog();
                break;
            case R.id.point7:
                pointNum = 7;
                selectTitle = point7.getText().toString().trim();
                showDialog();
                break;
            case R.id.point8:
                pointNum = 8;
                selectTitle = point8.getText().toString().trim();
                showDialog();
                break;
            case R.id.point9:
                pointNum = 9;
                selectTitle = point9.getText().toString().trim();
                showDialog();
                break;
            case R.id.point10:
                pointNum = 10;
                selectTitle = point10.getText().toString().trim();
                showDialog();
                break;
            case R.id.point11:
                pointNum = 11;
                selectTitle = point11.getText().toString().trim();
                showDialog();
                break;
            case R.id.point12:
                pointNum = 12;
                selectTitle = point12.getText().toString().trim();
                showDialog();
                break;
            case R.id.point13:
                pointNum = 13;
                selectTitle = point13.getText().toString().trim();
                showDialog();
                break;
            case R.id.point14:
                pointNum = 14;
                selectTitle = point14.getText().toString().trim();
                showDialog();
                break;
            case R.id.point15:
                pointNum = 15;
                selectTitle = point15.getText().toString().trim();
                showDialog();
                break;
            case R.id.point16:
                pointNum = 16;
                selectTitle = point16.getText().toString().trim();
                showDialog();
                break;
            case R.id.point17:
                pointNum = 17;
                selectTitle = point17.getText().toString().trim();
                showDialog();
                break;
            case R.id.point18:
                pointNum = 18;
                selectTitle = point18.getText().toString().trim();
                showDialog();
                break;
            case R.id.point19:
                pointNum = 19;
                selectTitle = point19.getText().toString().trim();
                showDialog();
                break;
            case R.id.point20:
                pointNum = 20;
                selectTitle = point20.getText().toString().trim();
                showDialog();
                break;
            case R.id.point21:
                pointNum = 21;
                selectTitle = point21.getText().toString().trim();
                showDialog();
                break;
            case R.id.point22:
                pointNum = 22;
                selectTitle = point22.getText().toString().trim();
                showDialog();
                break;
            case R.id.point23:
                pointNum = 23;
                selectTitle = point23.getText().toString().trim();
                showDialog();
                break;
            case R.id.point24:
                pointNum = 24;
                selectTitle = point24.getText().toString().trim();
                showDialog();
                break;
            case R.id.point25:
                pointNum = 25;
                selectTitle = point25.getText().toString().trim();
                showDialog();
                break;
            case R.id.point26:
                pointNum = 26;
                selectTitle = point26.getText().toString().trim();
                showDialog();
                break;
            case R.id.point27:
                pointNum = 27;
                selectTitle = point27.getText().toString().trim();
                showDialog();
                break;
            case R.id.point28:
                pointNum = 28;
                selectTitle = point28.getText().toString().trim();
                showDialog();
                break;
            case R.id.point29:
                pointNum = 29;
                selectTitle = point29.getText().toString().trim();
                showDialog();
                break;
            case R.id.point30:
                pointNum = 30;
                selectTitle = point30.getText().toString().trim();
                showDialog();
                break;
            case R.id.point31:
                pointNum = 31;
                selectTitle = point31.getText().toString().trim();
                showDialog();
                break;
            case R.id.point32:
                pointNum = 32;
                selectTitle = point32.getText().toString().trim();
                showDialog();
                break;
            case R.id.point33:
                pointNum = 33;
                selectTitle = point33.getText().toString().trim();
                showDialog();
                break;
            case R.id.point34:
                pointNum = 34;
                selectTitle = point34.getText().toString().trim();
                showDialog();
                break;
            case R.id.point35:
                pointNum = 35;
                selectTitle = point35.getText().toString().trim();
                showDialog();
                break;
            case R.id.choosePhoto:
                //高级
                categary = 0;
                choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_selected));
                choosePhoto.setTextColor(getResources().getColor(R.color.white));
                takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
                takePhoto.setTextColor(getResources().getColor(R.color.mode_blue));
//                sendMsg(1);
//                dialog.dismiss();
                break;
            case R.id.takePhoto:
                categary = 1;
                choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
                choosePhoto.setTextColor(getResources().getColor(R.color.mode_blue));
                takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_selected));
                takePhoto.setTextColor(getResources().getColor(R.color.white));
//                sendMsg(2);
//                dialog.dismiss();
                break;
            case R.id.cancel:
//                Intent intent2 = new Intent(getActivity(), CountDownActivity.class);
//                intent2.putExtra("type", type);
//                startActivity(intent2);
                dialog.dismiss();
                break;
            case R.id.confirm:
                if (categary == 2) ShowHelper.toastShort(ConsoleActivity.this, "请选择一种速度模式！");
                else {
                    Intent intent = new Intent(ConsoleActivity.this, PointChangeActivity.class);
                    intent.putExtra("categary", categary);
                    intent.putExtra("pointNum", pointNum);
                    intent.putExtra("title", selectTitle);
                    startActivity(intent);
                    dialog.dismiss();
                }
        }
    }

}
