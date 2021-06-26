package com.health.pusun.device.train;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.health.pusun.device.ConsoleActivity;
import com.health.pusun.device.CountDownActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.application.App;
import com.health.pusun.device.teach.ProgramActivity;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.KeySelectMsg;
import com.health.pusun.device.vo.eventbusmsg.KeyStartMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectTypeMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupTrainFragment extends Fragment implements View.OnClickListener{

    private TextView group_ball_1, group_ball_2, group_ball_3, group_ball_4, group_ball_5, group_ball_6, group_ball_7,suiji_ball_1,suiji_ball_2,suiji_ball_3,suiji_ball_4,suiji_ball_5,group_ball_zidingyi,
            group_ball_8, group_ball_9, group_ball_10,lob_ball, catch_ball, hook_ball, rub_ball, diao_group, put_net, group_ball_diaozhi, group_ball_diaoduizuo, group_ball_diaoduiyou,group_ball_shaduizuo,group_ball_shaduiyou, push_ball;
    private TextView[] tvs;
    private int[] tvids;
    private int type = 1015;
    private String selectTitle;
    private Spinner spinner_nums;
    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm;
    private Dialog dialog;
    private int selNum = 0;
    private int stateMode = 2;
    private  String[] ballnames = {"不限","10","20","30","40","50","60","70","80","90","100"};
    private int[] ballNums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private TextView dianwei4,dianwei5,dianwei6;

    public GroupTrainFragment() {
        // Required empty public constructor
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_train, container, false);
        dianwei4 = view.findViewById(R.id.dianwei4);
        dianwei4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 4);
                startActivity( intent);
            }
        });
        dianwei5 = view.findViewById(R.id.dianwei5);
        dianwei5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 5);
                startActivity( intent);
            }
        });
        dianwei6 = view.findViewById(R.id.dianwei6);
        dianwei6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 6);
                startActivity( intent);
            }
        });
        group_ball_zidingyi = view.findViewById(R.id.group_ball_zidingyi);
        group_ball_zidingyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ModeSelectActivity.ZIDINGYI;
                selectTitle = "自定义组合";
                updateTextViews(group_ball_zidingyi.getId());
                showDialog();
            }
        });
        group_ball_1 = (TextView) view.findViewById(R.id.group_ball_1);
        group_ball_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ModeSelectActivity.MIZIBU;
                selectTitle = "米字步";
                updateTextViews(group_ball_1.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        group_ball_2 = (TextView) view.findViewById(R.id.group_ball_2);
        group_ball_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SHANGSWZUHE;
                selectTitle = "杀上网";
                updateTextViews(group_ball_2.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        diao_group = (TextView) view.findViewById(R.id.diao_group);
        diao_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.GAOYUANDIAOQIU;
                selectTitle = "高远球";
                updateTextViews(diao_group.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
       //put_net, group_ball_diaozhi, group_ball_diaodui, group_ball_shadui
        put_net = (TextView) view.findViewById(R.id.put_net);
        put_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.FANGWANG;
                selectTitle = "放网";
                updateTextViews(put_net.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_diaozhi = (TextView) view.findViewById(R.id.group_ball_diaozhi);
        group_ball_diaozhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DIAOSHANGZHI;
                selectTitle = "吊上网直线";
                updateTextViews(group_ball_diaozhi.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_diaoduizuo = (TextView) view.findViewById(R.id.group_ball_diaoduizuo);
        group_ball_diaoduizuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DIAOSHANGDUIZUO;
                selectTitle = "吊上网左斜线";
                updateTextViews(group_ball_diaoduizuo.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_diaoduiyou = (TextView) view.findViewById(R.id.group_ball_diaoduiyou);
        group_ball_diaoduiyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DIAOSHANGDUIYOU;
                selectTitle = "吊上网右斜线";
                updateTextViews(group_ball_diaoduiyou.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_shaduizuo = (TextView) view.findViewById(R.id.group_ball_shaduizuo);
        group_ball_shaduizuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SHASHANGDUIZUO;
                selectTitle = "杀上网左斜线";
                updateTextViews(group_ball_shaduizuo.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_shaduiyou = (TextView) view.findViewById(R.id.group_ball_shaduiyou);
        group_ball_shaduiyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SHASHANGDUIYOU;
                selectTitle = "杀上网右斜线";
                updateTextViews(group_ball_shaduiyou.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_3 = (TextView) view.findViewById(R.id.group_ball_3);
        group_ball_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.ZUOXIEXIAN;
                selectTitle = "反手被动过渡网前";
                updateTextViews(group_ball_3.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_4 = (TextView) view.findViewById(R.id.group_ball_4);
        group_ball_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.ZUOCHANGJIEFA;
                selectTitle = "左场接发";
                updateTextViews(group_ball_4.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        group_ball_5 = (TextView) view.findViewById(R.id.group_ball_5);
        group_ball_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.YOUCHANGJIEFA;
                selectTitle = "右场接发";
                updateTextViews(group_ball_5.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_6 = (TextView) view.findViewById(R.id.group_ball_6);
        group_ball_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.ZHONGCHANGCEXIANG;
                selectTitle = "半场突击";
                updateTextViews(group_ball_6.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_7 = (TextView) view.findViewById(R.id.group_ball_7);
        group_ball_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DIJIAOCEXIANG;
                selectTitle = "杀球(组合)";
                updateTextViews(group_ball_7.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_8 = (TextView) view.findViewById(R.id.group_ball_8);
        group_ball_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SIFANGQIU;
                selectTitle = "四方球";
                updateTextViews(group_ball_8.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        group_ball_9 = (TextView) view.findViewById(R.id.group_ball_9);
        group_ball_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.CHOUQIUZUHE;
                selectTitle = "抽球(组合)";
                updateTextViews(group_ball_9.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_10 = (TextView) view.findViewById(R.id.group_ball_10);
        group_ball_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJISIFANGQIU;
                selectTitle = "随机四方球";
                updateTextViews(group_ball_10.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        lob_ball = (TextView) view.findViewById(R.id.lob_ball);
        lob_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ModeSelectActivity.TIAOQIU;
                selectTitle = "挑球";
                updateTextViews(lob_ball.getId());
                sendTypeMsg(type);
//                ShowHelper.showAlertDialog(getActivity(), "请分类选择", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        sendMsg(1);
////                        startActivity(new Intent(getActivity(), VideoPlayActivity.class));
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        sendMsg(2);
//                    }
//                });
                showDialog();
            }
        });
        catch_ball = (TextView) view.findViewById(R.id.catch_ball);
        catch_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.PUQIU;
                selectTitle = "扑球";
                updateTextViews(catch_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        hook_ball = (TextView) view.findViewById(R.id.hook_ball);
        hook_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.GOUQIU;
                selectTitle = "勾对角";
                updateTextViews(hook_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        rub_ball = (TextView) view.findViewById(R.id.rub_ball);
        rub_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.CUOQIU;
                selectTitle = "搓球";
                updateTextViews(rub_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        push_ball = (TextView) view.findViewById(R.id.push_ball);
        push_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.TUIQIU;
                selectTitle = "推球";
                updateTextViews(push_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        suiji_ball_1 = (TextView) view.findViewById(R.id.suiji_ball_1);
        suiji_ball_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJI1;
                selectTitle = "前场随机";
                updateTextViews(suiji_ball_1.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        suiji_ball_2 = (TextView) view.findViewById(R.id.suiji_ball_2);
        suiji_ball_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJI2;
                selectTitle = "中前场随机";
                updateTextViews(suiji_ball_2.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        suiji_ball_3 = (TextView) view.findViewById(R.id.suiji_ball_3);
        suiji_ball_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJI3;
                selectTitle = "中场随机";
                updateTextViews(suiji_ball_3.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        suiji_ball_4 = (TextView) view.findViewById(R.id.suiji_ball_4);
        suiji_ball_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJI4;
                selectTitle = "中后场随机";
                updateTextViews(suiji_ball_4.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        suiji_ball_5 = (TextView) view.findViewById(R.id.suiji_ball_5);
        suiji_ball_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SUIJI5;
                selectTitle = "后场随机";
                updateTextViews(suiji_ball_5.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

//        tvs = new TextView[]{group_ball_1, group_ball_8, group_ball_2, group_ball_9, group_ball_6, group_ball_7,group_ball_4, group_ball_5, lob_ball, catch_ball, rub_ball, push_ball, hook_ball, diao_group, put_net, group_ball_diaozhi, group_ball_diaoduizuo,group_ball_diaoduiyou, group_ball_shaduizuo, group_ball_shaduiyou, group_ball_10, suiji_ball_1,suiji_ball_2,suiji_ball_3,suiji_ball_4,suiji_ball_5};
//        tvids = new int[]{1015, 1070, 1027, 1073,1020, 1019, 1071,1072,1001, 1002, 1005, 1006, 1004, 1052, 1003, 1053, 1054, 1055, 1056, 1057, 3001, 1101, 1102, 1103, 1104, 1105};
        tvs = new TextView[]{group_ball_1};
        tvids = new int[]{1015};
        return view;
    }

    public void showDialog() {
        if(dialog!= null) dialog.dismiss();
        dialog = new Dialog(getActivity(), R.style.InputDialogStyle);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.group_dialog, null);
        title = inflate.findViewById(R.id.title);
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        cancel = (TextView) inflate.findViewById(R.id.cancel);
        confirm = (TextView) inflate.findViewById(R.id.confirm);
        spinner_nums = inflate.findViewById(R.id.spinner_nums);
        title.setText("请选择" + selectTitle + "模式");
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        SpinnerAdapter gameKindArray= new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, ballnames);
        spinner_nums.setAdapter(gameKindArray);
        int saveNum = App.getIntUserPreference("ballnum");
        spinner_nums.setSelection(saveNum);
        spinner_nums.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                App.saveUserPreference("ballnum", i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stateMode = 2;
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void updateTextViews(int id){
        for(int i = 0; i < tvs.length; i++){
            if (id == tvs[i].getId()){
                tvs[i].setBackground(getResources().getDrawable(R.drawable.corner_button_blue_pressed));
                tvs[i].setTextColor(Color.WHITE);
            }else {
                tvs[i].setBackground(getResources().getDrawable(R.drawable.code_button_bg_default));
                tvs[i].setTextColor(getResources().getColor(R.color.mode_blue));
            }
        }
    }
    private void sendTypeMsg(int type){

        SelectTypeMsg selectTypeMsg = new SelectTypeMsg();
        selectTypeMsg.type = type;
        EventBus.getDefault().post(selectTypeMsg);

    }
    @Subcriber
    private void dealKeySelectMsg(KeySelectMsg keySelectMsg){

        if(keySelectMsg.state == 1){
           int number = keySelectMsg.num;
            selNum = selNum + number;
            if(selNum < 0){
                selNum = 0;
            }
            if(selNum >= (tvids.length - 1)){
                selNum = tvids.length - 1;
            }
            updateSelectKey(selNum);
            type =tvids[selNum];
        }
    }

    @Subcriber
    private void dealKeyStartMsg(KeyStartMsg keyStartMsg){

        if(keyStartMsg.state == 1){
            Intent intent = new Intent(getActivity(), CountDownActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            dialog.dismiss();
        }
    }

    private void updateSelectKey(int selNum) {

        updateTextViews(tvs[selNum].getId());
        type = tvids[selNum];
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choosePhoto:
                stateMode = 0;
                choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_selected));
                choosePhoto.setTextColor(getResources().getColor(R.color.white));
                takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
                takePhoto.setTextColor(getResources().getColor(R.color.mode_blue));
//                sendMsg(1);
//                dialog.dismiss();
                break;
            case R.id.takePhoto:
                stateMode = 1;
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
                    if(stateMode == 2) ShowHelper.toastShort(getActivity(), "请选择一种速度模式！");
                    else{
                        if(!selectTitle.contains("自定义")){
                            ModeSelectActivity.veloMode = stateMode;
                            Intent intent = new Intent(getActivity(), CountDownActivity.class);
                            intent.putExtra("type", type);
                            startActivity(intent);
                            dialog.dismiss();
                        }else {
                            ModeSelectActivity.veloMode = stateMode;
                            Intent intent = new Intent(getActivity(), ProgramActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
        }
    }
    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};

        public SpinnerAdapter(final Context context, final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(30);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }         // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(30);
            return convertView;
        }
    }
}
