package com.example.yamengwenjing.yiyiguanai.UI;



import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yamengwenjing.yiyiguanai.R;
import com.example.yamengwenjing.yiyiguanai.TestActivty;
import com.example.yamengwenjing.yiyiguanai.UI.Fragment.CameraFragment;
import com.example.yamengwenjing.yiyiguanai.UI.Fragment.QAFragment;
import com.example.yamengwenjing.yiyiguanai.UI.Fragment.monitorFragment;

public class MainTabActvity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout mTabMonitor;
    private LinearLayout mTabQA;
    private LinearLayout mTabCamera;

    private Fragment cameraFragment;
    private Fragment monitorFragment;
    private Fragment qaFragment;

//    private ImageView settingButton;

    private TextView titleText;
    private LinearLayout settingButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab_actvity);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        mTabCamera.setOnClickListener(this);
        mTabQA.setOnClickListener(this);
        mTabMonitor.setOnClickListener(this);

        settingButton.setOnClickListener(this);



    }

    private void initView() {

        mTabMonitor = (LinearLayout)findViewById(R.id.id_tab_monitor);
        mTabQA = (LinearLayout)findViewById(R.id.id_tab_qa);
        mTabCamera = (LinearLayout)findViewById(R.id.id_tab_record);

        titleText = (TextView)findViewById(R.id.main_act_title_text);
        settingButton = (LinearLayout)findViewById(R.id.setting_button);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.id_tab_monitor:
                setSelect(0);
                break;
            case R.id.id_tab_qa:
                setSelect(1);
                break;
            case R.id.id_tab_record:
                setSelect(2);
                break;
            case R.id.setting_button:
                gotoSettingAct();
                break;


            default:
                break;
        }

    }

    private void gotoSettingAct() {
            Intent gotoTestAct = new Intent(this, TestActivty.class);
            startActivity(gotoTestAct);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch (i){
            case 0:
            if(monitorFragment ==null){
                monitorFragment = new monitorFragment();
                transaction.add(R.id.id_content,monitorFragment);
            }else{
                transaction.show(monitorFragment);
            }
                titleText.setText("监控");
                break;
            case 1:
                if(qaFragment ==null){
                    qaFragment = new QAFragment();
                    transaction.add(R.id.id_content,qaFragment);
                }else{
                transaction.show(qaFragment);
            }
                titleText.setText("问答");
                break;
            case 2:
                if(cameraFragment ==null){
                    cameraFragment = new CameraFragment();
                    transaction.add(R.id.id_content,cameraFragment);
                }else{
                    transaction.show(cameraFragment);
                }
                titleText.setText("饮食");
                break;
            default:
                break;

        }
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if(monitorFragment !=null){
            transaction.hide(monitorFragment);
        }
        if(qaFragment !=null){
            transaction.hide(qaFragment);
        }
        if(cameraFragment != null){
            transaction.hide(cameraFragment);
        }
    }
}
