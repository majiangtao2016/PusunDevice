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
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.health.pusun.device.ConsoleActivity;
import com.health.pusun.device.CountDownActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.application.App;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.eventbusmsg.KeySelectMsg;
import com.health.pusun.device.vo.eventbusmsg.KeyStartMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectModeMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectTypeMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleTrainFragment extends Fragment implements View.OnClickListener{

    private TextView lob_ball, catch_ball, drive_ball, smash_back_ball, put_net, hook_ball, rub_ball,push_ball, receive_ball,jiefa1,jiefa2,jiefa3,jiefa4,
            block_ball, smash_ball_middle, split_hang_ball, high_clear_ball, over_lob_ball, attack_ball_middle, drive_ball_back,put_ball,put_ball_back,
            block_ball_back, smash_ball_back, split_hang_ball_back, high_clear_ball_back, group_ball_2, group_ball_3;
    private TextView lob_ball_up, lob_ball_down, catch_ball_up, catch_ball_down, rub_ball_up, rub_ball_down, push_ball_up, push_ball_down, hook_ball_up, hook_ball_down;
    private int type = 1060;
    private int position;
    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm;
    private Spinner spinner_nums;
    private Dialog dialog;
    private int state;   // 标记模式
    private String selectTitle;
    private int selNum = 0;
    private TextView[] tvs;
    private int[] tvids;
    private int stateMode = 2;
    private  String[] ballnames = {"不限","10","20","30","40","50","60","70","80","90","100"};
    private int[] ballNums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private TextView dianwei1, dianwei2, dianwei3;

    public SingleTrainFragment() {
        // Required empty public constructor
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_train, container, false);
        dianwei1 = view.findViewById(R.id.dianwei1);
        dianwei1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 1);
                startActivity( intent);
            }
        });
        dianwei2 = view.findViewById(R.id.dianwei2);
        dianwei2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 2);
                startActivity( intent);
            }
        });
        dianwei3 = view.findViewById(R.id.dianwei3);
        dianwei3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsoleActivity.class);
                intent.putExtra("num", 3);
                startActivity( intent);
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
//                ShowHelper.showAlertDialog(getActivity(), "请分类选择", new Interface.OnClickListener() {
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
        lob_ball_up = (TextView) view.findViewById(R.id.lob_ball_up);
        lob_ball_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.TIAOZHENG;
                selectTitle = "正手挑球";
                updateTextViews(lob_ball_up.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        lob_ball_down = (TextView) view.findViewById(R.id.lob_ball_down);
        lob_ball_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.TIAOFAN;
                selectTitle = "反手挑球";
                updateTextViews(lob_ball_down.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        group_ball_2 = (TextView) view.findViewById(R.id.group_ball_2);
        group_ball_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SHASHANGWANG;
                selectTitle = "正手被动过渡网前";
                updateTextViews(group_ball_2.getId());
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
        catch_ball_up = (TextView) view.findViewById(R.id.catch_ball_up);
        catch_ball_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.PUHENG;
                selectTitle = "正手扑球";
                updateTextViews(catch_ball_up.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        catch_ball_down = (TextView) view.findViewById(R.id.catch_ball_down);
        catch_ball_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.PUFAN;
                selectTitle = "反手扑球";
                updateTextViews(catch_ball_down.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        drive_ball = (TextView) view.findViewById(R.id.drive_ball);
        drive_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.CHOUQIU;
                selectTitle = "正手抽球";
                updateTextViews(drive_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        smash_back_ball = (TextView) view.findViewById(R.id.smash_back_ball);
        smash_back_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.SHAQIU_HOUCHANG;
                selectTitle = "杀球";
                updateTextViews(smash_back_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

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
        hook_ball_up = (TextView) view.findViewById(R.id.hook_ball_up);
        hook_ball_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.GOUZHENG;
                selectTitle = "正手勾对角";
                updateTextViews(hook_ball_up.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        hook_ball_down = (TextView) view.findViewById(R.id.hook_ball_down);
        hook_ball_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.GOUFAN;
                selectTitle = "反手勾对角";
                updateTextViews(hook_ball_down.getId());
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
        rub_ball_up = (TextView) view.findViewById(R.id.rub_ball_up);
        rub_ball_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.CUOZHENG;
                selectTitle = "正手搓球";
                updateTextViews(rub_ball_up.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        rub_ball_down = (TextView) view.findViewById(R.id.rub_ball_down);
        rub_ball_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.CUOFAN;
                selectTitle = "反手搓球";
                updateTextViews(rub_ball_down.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        put_ball = (TextView) view.findViewById(R.id.put_ball);
        put_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.FANGZHENG;
                selectTitle = "正手放网";
                updateTextViews(put_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        put_ball_back = (TextView) view.findViewById(R.id.put_ball_back);
        put_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.FANGFAN;
                selectTitle = "反手放网";
                updateTextViews(put_ball_back.getId());
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
        push_ball_up = (TextView) view.findViewById(R.id.push_ball_up);
        push_ball_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.TUIZHENG;
                selectTitle = "正手推球";
                updateTextViews(push_ball_up.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        push_ball_down = (TextView) view.findViewById(R.id.push_ball_down);
        push_ball_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.TUIFAN;
                selectTitle = "反手推球";
                updateTextViews(push_ball_down.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        jiefa1 = (TextView) view.findViewById(R.id.jiefa1);
        jiefa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.ZUOCHANGJIEFA1;
                selectTitle = "左场接发1";
                updateTextViews(jiefa1.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        jiefa2 = (TextView) view.findViewById(R.id.jiefa2);
//        jiefa2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = ModeSelectActivity.ZUOCHANGJIEFA2;
//                selectTitle = "左场接发2";
//                updateTextViews(jiefa2.getId());
//                sendTypeMsg(type);
//                showDialog();
//            }
//        });
        jiefa3 = (TextView) view.findViewById(R.id.jiefa3);
        jiefa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.YOUCHANGJIEFA1;
                selectTitle = "右场接发1";
                updateTextViews(jiefa3.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        jiefa4 = (TextView) view.findViewById(R.id.jiefa4);
//        jiefa4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = ModeSelectActivity.YOUCHANGJIEFA2;
//                selectTitle = "右场接发2";
//                updateTextViews(jiefa4.getId());
//                sendTypeMsg(type);
//                showDialog();
//            }
//        });
        receive_ball = (TextView) view.findViewById(R.id.receive_ball);
        receive_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.JIEFAQIU;
                selectTitle = "接发球";
                updateTextViews(receive_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
//        drive_ball = (TextView) view.findViewById(R.id.drive_ball);
//        drive_ball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = ModeSelectActivity.CHOUQIU;
//                        showDialog();
//            }
//        });
        attack_ball_middle = view.findViewById(R.id.attack_ball_middle);
//        attack_ball_middle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = ModeSelectActivity.BANCHANGTUJI;
//                selectTitle = "半场突击";
//                updateTextViews(attack_ball_middle.getId());
//                sendTypeMsg(type);
//                showDialog();
//            }
//        });
        drive_ball_back = view.findViewById(R.id.drive_ball_back);
        drive_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DRIVEBALLBACK;
                selectTitle = "反手抽球";
                updateTextViews(drive_ball_back.getId());
                sendTypeMsg(type);
                showDialog();

            }
        });
        block_ball = (TextView) view.findViewById(R.id.block_ball);
        block_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DANGQIU;
                selectTitle = "正手挡球";
                updateTextViews(block_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        block_ball_back = (TextView) view.findViewById(R.id.block_ball_back);
        block_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.BLOCKBALLBACK;
                selectTitle = "反手挡球";
                updateTextViews(block_ball_back.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        smash_ball_middle = (TextView) view.findViewById(R.id.smash_ball_middle);
        smash_ball_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelper.toastShort(getActivity(), "本款设备不提供该功能。");
//                type = ModeSelectActivity.SHAQIU_ZHONGCHANG;
//                selectTitle = "正手接杀";
//                updateTextViews(smash_ball_middle.getId());
//                showDialog();
            }
        });
        smash_ball_back = (TextView) view.findViewById(R.id.smash_ball_back);
        smash_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelper.toastShort(getActivity(), "本款设备不提供该功能。");
//                type = ModeSelectActivity.SMASHBALLBACK;
//                selectTitle = "反手接杀";
//                updateTextViews(smash_ball_back.getId());
//                showDialog();
            }
        });
        split_hang_ball = (TextView) view.findViewById(R.id.split_hang_ball);
        split_hang_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.PIDIAO;
                selectTitle = "正手吊球";
                updateTextViews(split_hang_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        split_hang_ball_back = (TextView) view.findViewById(R.id.split_hang_ball_back);
        split_hang_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.FANSHOUDIAOQIU;
                selectTitle = "反手吊球";
                updateTextViews(split_hang_ball_back.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        high_clear_ball = (TextView) view.findViewById(R.id.high_clear_ball);
        high_clear_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.GAOYUANQIU;
                selectTitle = "正手高远球";
                updateTextViews(high_clear_ball.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        high_clear_ball_back = (TextView) view.findViewById(R.id.high_clear_ball_back);
        high_clear_ball_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.FANSHOUGAOYUANQIU;
                selectTitle = "反手高远球";
                updateTextViews(high_clear_ball_back.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
//        over_lob_ball = (TextView) view.findViewById(R.id.over_lob_ball);
//        over_lob_ball.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type = ModeSelectActivity.PINGGAOQIU;
//                selectTitle = "平高球";
//                updateTextViews(over_lob_ball.getId());
//                showDialog();
//            }
//        });
//        tvs = new TextView[]{lob_ball, catch_ball, drive_ball, smash_back_ball, put_net, hook_ball, rub_ball,push_ball, receive_ball,
//                block_ball, split_hang_ball, high_clear_ball, attack_ball_middle, drive_ball_back, block_ball_back, split_hang_ball_back, high_clear_ball_back, lob_ball_up, lob_ball_down, catch_ball_up, catch_ball_down, rub_ball_up, rub_ball_down, push_ball_up, push_ball_down, hook_ball_up, hook_ball_down};
      tvs = new TextView[]{lob_ball_up, catch_ball_up, rub_ball_up, push_ball_up, lob_ball_down, catch_ball_down, rub_ball_down,push_ball_down, hook_ball_up,hook_ball_down,jiefa1,jiefa2,jiefa3,jiefa4,
                drive_ball, drive_ball_back,split_hang_ball, split_hang_ball_back, high_clear_ball, high_clear_ball_back, smash_back_ball, group_ball_2, group_ball_3, put_ball, put_ball_back};
        tvids = new int[]{1060, 1062, 1064, 1066, 1061, 1063, 1065, 1067, 1068, 1069, 1075, 1076, 1077, 1078, 1008, 1022, 1012, 1026, 1013, 1025, 1011, 1016, 1017, 1050, 1051};
        return view;
    }

    private void sendMsg(int position){
        SelectModeMsg selectMsg = new SelectModeMsg();
        selectMsg.setType(type);
        selectMsg.setPosition(position);
        EventBus.getDefault().post(selectMsg);
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

        if(keySelectMsg.state == 0){
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

        if(keyStartMsg.state == 0){
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


//    public void showDialog() {
//        dialog = new Dialog(getActivity(), R.style.InputDialogStyle);
//        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.categary_dialog, null);
//        title = inflate.findViewById(R.id.title);
//        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
//        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
//        cancel = (TextView) inflate.findViewById(R.id.cancel);
//        confirm = (TextView) inflate.findViewById(R.id.confirm);
//        title.setText("请选择" + selectTitle + "方式");
//        choosePhoto.setOnClickListener(this);
//        takePhoto.setOnClickListener(this);
//        cancel.setOnClickListener(this);
//        confirm.setOnClickListener(this);
//        dialog.setContentView(inflate);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;
//        dialogWindow.setAttributes(lp);
//        resetDialogButton();
//        dialog.show();
//    }

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

//    public void showDialog() {
//        if(dialog!= null) dialog.dismiss();
//        dialog = new Dialog(getActivity(), R.style.InputDialogStyle);
//        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.single_dialog, null);
//        title = inflate.findViewById(R.id.title);
//        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
//        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
//        cancel = (TextView) inflate.findViewById(R.id.cancel);
//        confirm = (TextView) inflate.findViewById(R.id.confirm);
//        title.setText("您确定选择" + selectTitle + "模式吗？");
//        cancel.setOnClickListener(this);
//        confirm.setOnClickListener(this);
//        dialog.setContentView(inflate);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;
//        dialogWindow.setAttributes(lp);
//        dialog.show();
//    }

    private void resetDialogButton(){
        state = 0;
        choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
        takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//
//            case R.id.choosePhoto:
//                state = 1;
//                choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_selected));
//                choosePhoto.setTextColor(getResources().getColor(R.color.white));
//                takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
//                takePhoto.setTextColor(getResources().getColor(R.color.mode_blue));
////                sendMsg(1);
////                dialog.dismiss();
//                break;
//            case R.id.takePhoto:
//                state = 2;
//                choosePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_default));
//                choosePhoto.setTextColor(getResources().getColor(R.color.mode_blue));
//                takePhoto.setBackground(getResources().getDrawable(R.drawable.dialog_button_selected));
//                takePhoto.setTextColor(getResources().getColor(R.color.white));
////                sendMsg(2);
////                dialog.dismiss();
//                break;
//            case R.id.cancel:
//                dialog.dismiss();
//                break;
//            case R.id.confirm:
////                if(state == 0) ShowHelper.toastShort(getActivity(), "请选择一种方式！");
////                if(state == 1 || state == 2){
//                    Intent intent = new Intent(getActivity(), CountDownActivity.class);
//                    intent.putExtra("state", 1);
//                    intent.putExtra("type", type);
//                    startActivity(intent);
//                    dialog.dismiss();
////                }
//            default:break;
//
//        }
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
                if (stateMode == 2) ShowHelper.toastShort(getActivity(), "请选择一种速度模式！");
                else {
                    ModeSelectActivity.veloMode = stateMode;
                    Intent intent = new Intent(getActivity(), CountDownActivity.class);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    dialog.dismiss();
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
