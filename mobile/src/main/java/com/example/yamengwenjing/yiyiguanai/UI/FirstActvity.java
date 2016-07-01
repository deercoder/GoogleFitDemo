package com.example.yamengwenjing.yiyiguanai.UI;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.yamengwenjing.yiyiguanai.R;

public class FirstActvity extends Activity {


    private SharedPreferences sp;
    boolean finished_setting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_actvity);

        sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
        finished_setting =sp.getBoolean("finished_setting", false);

        
        if(finished_setting){
            gotoMainAct();

        }else{

            gotoSettingTab() ;
            
        }
    }

    private void gotoSettingTab() {
        Intent gotoSettingAct = new Intent(this,settingActvity.class);
       startActivity(gotoSettingAct);
        finish();
    }

    private void gotoMainAct() {
        Intent gotoMainTab = new Intent(this,MainTabActvity.class);
        startActivity(gotoMainTab);
        finish();
    }
}
