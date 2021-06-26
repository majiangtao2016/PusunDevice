package com.health.pusun.device.train;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.health.pusun.device.CountDownActivity;
import com.health.pusun.device.GamePlayActivity;
import com.health.pusun.device.GameTongguanActivity;
import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.R;
import com.health.pusun.device.application.App;
import com.health.pusun.device.game.GameIntroduceActivity;
import com.health.pusun.device.utils.AppLog;
import com.health.pusun.device.utils.MyHttpUtils;
import com.health.pusun.device.utils.MyJsonCallbalk;
import com.health.pusun.device.utils.ShowHelper;
import com.health.pusun.device.vo.GameLevel;
import com.health.pusun.device.vo.RequestCallVo;
import com.health.pusun.device.vo.ScoreGameInfo;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GameChallengeFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout level_6, level_5, level_4, level_3, level_2, level_1, level_all;
    private TextView[] tvs;
    private int[] tvids;
    private ImageView iv_lock2, iv_lock3,iv_lock4,iv_lock5,iv_lock6;
    private RelativeLayout level_lock2, level_lock3, level_lock4, level_lock5, level_lock6;
    private TextView wei2, wei3,wei4,wei5, wei6;
    private Dialog dialog;
    private View inflate;
    private TextView title;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel, confirm, tongguan;
    private String selectTitle;
    public GameChallengeFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_challenge, container, false);
        level_6 = view.findViewById(R.id.level_6);
        level_6.setOnClickListener(this);
        level_5 = view.findViewById(R.id.level_5);
        level_5.setOnClickListener(this);
        level_4 = view.findViewById(R.id.level_4);
        level_4.setOnClickListener(this);
        level_3 = view.findViewById(R.id.level_3);
        level_3.setOnClickListener(this);
        level_2 = view.findViewById(R.id.level_2);
        level_2.setOnClickListener(this);
        level_1 = view.findViewById(R.id.level_1);
        level_1.setOnClickListener(this);
        level_all = view.findViewById(R.id.level_all);
        level_all.setOnClickListener(this);
        iv_lock2 = view.findViewById(R.id.iv_lock2);
        iv_lock3 = view.findViewById(R.id.iv_lock3);
        iv_lock4 = view.findViewById(R.id.iv_lock4);
        iv_lock5 = view.findViewById(R.id.iv_lock5);
        iv_lock6 = view.findViewById(R.id.iv_lock6);
        wei2 = view.findViewById(R.id.wei2);
        wei3 = view.findViewById(R.id.wei3);
        wei4 = view.findViewById(R.id.wei4);
        wei5 = view.findViewById(R.id.wei5);
        wei6 = view.findViewById(R.id.wei6);
        level_lock2 = view.findViewById(R.id.level_lock2);
        level_lock3 = view.findViewById(R.id.level_lock3);
        level_lock4 = view.findViewById(R.id.level_lock4);
        level_lock5 = view.findViewById(R.id.level_lock5);
        level_lock6 = view.findViewById(R.id.level_lock6);

//        tvs = new TextView[]{level_all, level_1, level_2, level_3, level_4, level_5, level_6};
        tvids = new int[]{2020, 2021, 2022, 2023, 2024, 2025, 2026};
        getCustomerResult();
        return view;
    }

    private void getCustomerResult() {


    }

    @Override
    public void onResume() {
        super.onResume();
        getResult();

    }

    private void getResult() {

        HashMap<String, String> params = new HashMap<>();
        params.put("UserId", ModeSelectActivity.userId);
        MyHttpUtils.postAsAync(App.BASE_URL + "/api/GetRacketTopLevel", params, new MyJsonCallbalk() {
            @Override
            public void onError(Exception e, int code) {
                AppLog.e("okHttpUtil.getAsync failed! request = " + e);
            }
            @Override
            public void onResponse(RequestCallVo requestCallVo) {
                AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
                if (requestCallVo.getType() == 1) {
                    GameLevel gameLevel = JSON.parseObject(requestCallVo.getData().toString(), GameLevel.class);
                     int num = gameLevel.getTopLevel();
                     if(num >= 5){
                         level_6.setClickable(true);
                         wei6.setVisibility(View.GONE);
                         iv_lock6.setVisibility(View.GONE);
                         level_lock6.setVisibility(View.GONE);
                     }
                    if(num >= 4){
                        level_5.setClickable(true);
                        wei5.setVisibility(View.GONE);
                        iv_lock5.setVisibility(View.GONE);
                        level_lock5.setVisibility(View.GONE);
                    }
                    if(num >= 3){
                        level_4.setClickable(true);
                        wei4.setVisibility(View.GONE);
                        iv_lock4.setVisibility(View.GONE);
                        level_lock4.setVisibility(View.GONE);
                    }
                    if(num >= 2){
                        level_3.setClickable(true);
                        wei3.setVisibility(View.GONE);
                        iv_lock3.setVisibility(View.GONE);
                        level_lock3.setVisibility(View.GONE);
                    }
                    if(num >= 1){
                        level_2.setClickable(true);
                        wei2.setVisibility(View.GONE);
                        iv_lock2.setVisibility(View.GONE);
                        level_lock2.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), requestCallVo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.level_1:
                ModeSelectActivity.gameLevel = 1;
                selectTitle = "第一关";
                updateTextViews(level_1.getId());
//                Intent intent = new Intent(getActivity(), GamePlayActivity.class);
//                intent.putExtra("type", 2021);
//                intent.putExtra("state", 2);
//                startActivity(intent);
                showDialog();
                return;
            case R.id.level_2:
                ModeSelectActivity.gameLevel = 2;
                selectTitle = "第二关";
                updateTextViews(level_2.getId());
//                Intent intent2 = new Intent(getActivity(), GamePlayActivity.class);
//                intent2.putExtra("type", 2022);
//                intent2.putExtra("state", 2);
//                startActivity(intent2);
                showDialog();
                return;
            case R.id.level_3:
                ModeSelectActivity.gameLevel = 3;
                selectTitle = "第三关";
                updateTextViews(level_3.getId());
//                Intent intent3 = new Intent(getActivity(), GamePlayActivity.class);
//                intent3.putExtra("type", 2023);
//                intent3.putExtra("state", 2);
//                startActivity(intent3);
                showDialog();
                return;
            case R.id.level_4:
                ModeSelectActivity.gameLevel = 4;
                selectTitle = "第四关";
                updateTextViews(level_4.getId());
//                Intent intent4 = new Intent(getActivity(), GamePlayActivity.class);
//                intent4.putExtra("type", 2024);
//                intent4.putExtra("state", 2);
//                startActivity(intent4);
                showDialog();
                return;
            case R.id.level_5:
                ModeSelectActivity.gameLevel = 5;
                selectTitle = "第五关";
                updateTextViews(level_5.getId());
//                Intent intent5 = new Intent(getActivity(), GamePlayActivity.class);
//                intent5.putExtra("type", 2025);
//                intent5.putExtra("state", 2);
//                startActivity(intent5);
                showDialog();
                return;
            case R.id.level_6:
                ModeSelectActivity.gameLevel = 6;
                selectTitle = "第六关";
                updateTextViews(level_6.getId());
//                Intent intent6 = new Intent(getActivity(), GamePlayActivity.class);
//                intent6.putExtra("type", 2026);
//                intent6.putExtra("state", 2);
//                startActivity(intent6);
                showDialog();
                return;
            case R.id.level_all:
                ModeSelectActivity.gameLevel = 1;
                selectTitle = "通关挑战";
                updateTextViews(level_all.getId());
//                Intent intent7 = new Intent(getActivity(), GameTongguanActivity.class);
//                startActivity(intent7);
                showDialog();
                return;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.confirm:

                if(selectTitle.equals("第一关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2021);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("第二关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2022);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("第三关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2023);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("第四关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2024);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("第五关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2025);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("第六关") ){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra("type", 2026);
                    intent.putExtra("state", 2);
                    startActivity(intent);
                }else if(selectTitle.equals("通关挑战")){
                    Intent intent7 = new Intent(getActivity(), GameTongguanActivity.class);
                    startActivity(intent7);
                }
                dialog.dismiss();
                default:break;
        }

    }

    public void showDialog() {
        if(dialog!= null) dialog.dismiss();
        dialog = new Dialog(getActivity(), R.style.InputDialogStyle);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.single_dialog, null);
        title = inflate.findViewById(R.id.title);
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        cancel = (TextView) inflate.findViewById(R.id.cancel);
        confirm = (TextView) inflate.findViewById(R.id.confirm);
        tongguan = inflate.findViewById(R.id.tongguan);
        if (selectTitle != null && selectTitle.equals("通关挑战"))
            tongguan.setVisibility(View.VISIBLE);
        else  tongguan.setVisibility(View.GONE);
        title.setText("您确定选择" + selectTitle + "吗？");
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }


    private void updateTextViews(int id){
//        for(int i = 0; i < tvs.length; i++){
//            if (id == tvs[i].getId()){
//                tvs[i].setBackground(getResources().getDrawable(R.drawable.corner_button_blue_pressed));
//                tvs[i].setTextColor(Color.WHITE);
//            }else {
//                tvs[i].setBackground(getResources().getDrawable(R.drawable.code_button_bg_default));
//                tvs[i].setTextColor(getResources().getColor(R.color.mode_blue));
//            }
//        }
    }

}
