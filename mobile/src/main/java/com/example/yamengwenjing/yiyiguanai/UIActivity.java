package com.example.yamengwenjing.yiyiguanai;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yamengwenjing.yiyiguanai.Entity.qaRandomQuestion;
import com.example.yamengwenjing.yiyiguanai.Entity.qaReceiveQestionDocs;
import com.example.yamengwenjing.yiyiguanai.UI.Fragment.RandomQuestionDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class UIActivity extends AppCompatActivity {


    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
//        qaJsonFile
        GsonTest03();
    }


    private void GsonTest04() {
        qaRandomQuestion[] foos = new Gson().fromJson(getStrFromAssets("TestQaJsonFile"), qaRandomQuestion[].class);
        System.out.println("name01 = " + foos[0].questionBody);
        System.out.println("name02 = " + foos[1].questionBody);
        System.out.println("numbers = " + foos.length);
        // 这时候想转成List的话调用如下方法
        // List<Foo> foosList = Arrays.asList(foos);
    }


    private void GsonTest03(){
        qaReceiveQestionDocs result = new Gson().fromJson(getStrFromAssets("TestQaJsonFile"),qaReceiveQestionDocs.class);
        System.out.println(result.toString());

    }

    private String getStrFromAssets(String name){
        String strData = null;
        try {
            InputStream inputStream = getAssets().open(name);
            byte buf[] = new byte[1024];

            inputStream.read(buf);
            strData = new String(buf);
            strData = strData.trim();

        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        Log.d("Json data", strData);
        return strData;
    }


    public void gotoAlert(View thisView){

        gotoDilaog();
        testRandom();
        
    }

    private void testRandom() {
        final Random rand = new Random();
        int diceRoll = rand.nextInt(6);
        System.out.println("diceRoll = " + diceRoll);
    }

    private void gotoDilaog(){
        RandomQuestionDialog alertdFragment = new RandomQuestionDialog();
        // Show Alert DialogFragment
        Bundle args = new Bundle();
        args.putString("questionContent","caonima");
        args.putString("questionAnswerA","a");
        args.putString("questionAnswerB","b");
        alertdFragment.setArguments(args);
        alertdFragment.show(fm, "Alert Dialog Fragment");
    }
}
