package com.health.pusun.device.train;

import android.app.Dialog;
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
import android.widget.TextView;

import com.health.pusun.device.CountDownActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.vo.eventbusmsg.KeySelectMsg;
import com.health.pusun.device.vo.eventbusmsg.KeyStartMsg;
import com.health.pusun.device.vo.eventbusmsg.SelectTypeMsg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProFragment extends Fragment implements View.OnClickListener{

    private TextView group_ball_1, group_ball_2;
    private TextView[] tvs;
    private int[] tvids;
    private int type = 3001;
    private String selectTitle;

    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm;
    private Dialog dialog;
    private int selNum = 0;
    private int stateMode = 2;

    public ProFragment() {
        // Required empty public constructor
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro, container, false);
        group_ball_1 = view.findViewById(R.id.group_ball_1);
        group_ball_2 = view.findViewById(R.id.group_ball_2);

        group_ball_1 = (TextView) view.findViewById(R.id.group_ball_1);
        group_ball_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = ModeSelectActivity.SUIJISIFANGQIU;
                selectTitle = "随机四方球";
                updateTextViews(group_ball_1.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });

        group_ball_2 = (TextView) view.findViewById(R.id.group_ball_2);
        group_ball_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = ModeSelectActivity.DINGDIANPUQIU;
                selectTitle = "定点扑球";
                updateTextViews(group_ball_2.getId());
                sendTypeMsg(type);
                showDialog();
            }
        });
        tvs = new TextView[]{group_ball_1, group_ball_2};
        tvids = new int[]{3001, 3002};
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
        title.setText("请选择" + selectTitle + "模式");
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
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

    }

}
