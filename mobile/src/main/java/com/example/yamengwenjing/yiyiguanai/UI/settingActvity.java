package com.example.yamengwenjing.yiyiguanai.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yamengwenjing.yiyiguanai.R;

public class settingActvity extends Activity {


    private SharedPreferences sp;
    boolean ifSettingFinished = false;

    EditText ageEditText;
    EditText weightEditText;
    EditText heightEditText;
    EditText babyDaysEditText;
    Button submitButton;
    Button confirmButton;
    TextView settingResultTextView;


    int userAge;
    int userWeight;
    int userHeight;
    int babyDays;

    Boolean judgeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_actvity);

        sp = getSharedPreferences("setting", Context.MODE_PRIVATE);


        initialView();
        initialAction();
    }

    private void initialAction() {
        confirmButton.setClickable(false);
        confirmButton.setVisibility(View.INVISIBLE);
       submitButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               JudgeInput();

           }
       });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeState) {
                    StoreInput();
                    GotoMainAct();

                }
            }
        });
    }

    private void GotoMainAct() {
        Intent gotoMainIntent = new Intent(this,MainTabActvity.class);
        startActivity(gotoMainIntent);
        finish();

    }

    private void StoreInput() {
        ifSettingFinished = true;
        SharedPreferences.Editor thisEditor = sp.edit();
        thisEditor.putBoolean("finished_setting",ifSettingFinished);
        thisEditor.putInt("userAge", userAge);
        thisEditor.putInt("userWeight", userWeight);
        thisEditor.putInt("userHeight",userHeight);
        thisEditor.putInt("BabyDays", babyDays);
        thisEditor.commit();
    }

    /*
    判断用户的输入是否合法
    如果合法让confirm button 可使用 ,则让confirmBUTTON可见
     */
    private void JudgeInput() {

        if(ageEditText.getText().length()>1) {
            userAge = Integer.valueOf(ageEditText.getText().toString());
            if (userAge > 60 && userAge < 15) {
                Toast.makeText(this, "请输入正确的年龄，目前输入的是" + userAge, Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this, "请输入正确的年龄，目前输入的是", Toast.LENGTH_SHORT).show();
            return;
        }

        if(heightEditText.getText().length()>1) {
        userHeight =  Integer.valueOf(heightEditText.getText().toString());
        if(userHeight>250  && userHeight<20){
            Toast.makeText(this,"请输入正确的身高，目前输入的是"+userHeight+"厘米",Toast.LENGTH_SHORT).show();
            return;
        }
        }
        else{
            Toast.makeText(this, "请输入正确的身高", Toast.LENGTH_SHORT).show();
            return;
        }

        if(weightEditText.getText().length()>1) {
        userWeight = Integer.valueOf(weightEditText.getText().toString());
        if(userWeight>120  && userWeight<20){
            Toast.makeText(this,"请输入正确的体重，目前输入的是"+userHeight+"公斤",Toast.LENGTH_SHORT).show();
            return;
        }
        }else{
            Toast.makeText(this,"请输入正确的体重",Toast.LENGTH_SHORT).show();
            return;
        }


        if(babyDaysEditText.getText().length()>1) {
        babyDays = Integer.valueOf(babyDaysEditText.getText().toString());
        if(babyDays>400  && babyDays<0){
            Toast.makeText(this,"请输入正确的日期，目前输入的是"+babyDays+"天",Toast.LENGTH_SHORT).show();
            return;
        } }else{
            Toast.makeText(this,"请输入孩子日期",Toast.LENGTH_SHORT).show();
            return;
        }

        settingResultTextView.setText("年龄"+userAge + "身高"+userHeight+"体重"+userWeight+"宝宝日期"+babyDays);

        judgeState = true;
        confirmButton.setClickable(true);
        confirmButton.setVisibility(View.VISIBLE);

    }

    private void initialView() {
        ageEditText = (EditText)findViewById(R.id.age_input_editview);
        weightEditText = (EditText)findViewById(R.id.weight_input_editview);
        heightEditText = (EditText)findViewById(R.id.height_input_editview);
        babyDaysEditText = (EditText)findViewById(R.id.babyday_input_editview);
        submitButton = (Button)findViewById(R.id.setting_submit_button);
        confirmButton = (Button)findViewById(R.id.setting_submit_confirm_button);
        settingResultTextView = (TextView)findViewById(R.id.settingResult);

    }
}
