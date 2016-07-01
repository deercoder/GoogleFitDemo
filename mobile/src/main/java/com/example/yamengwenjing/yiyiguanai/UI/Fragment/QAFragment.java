package com.example.yamengwenjing.yiyiguanai.UI.Fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yamengwenjing.yiyiguanai.Adapter.ChattingAdapter;
import com.example.yamengwenjing.yiyiguanai.Entity.ChattingMessage;
import com.example.yamengwenjing.yiyiguanai.Entity.qaRandomQuestion;
import com.example.yamengwenjing.yiyiguanai.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.

 */
public class QAFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Button sendingButton;
    Button voiceButton;
    EditText inputContent;

    ListView chattingListView;
    List<ChattingMessage> chattingList;
    ChattingAdapter myAdapter;

    Handler mHandler ;
    Context thisContext;

    FragmentManager fm ;
    qaRandomQuestion[] randomQuestionList;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    public QAFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView = inflater.inflate(R.layout.fragment_qa,container,false);
        thisContext = getContext();
        mHandler = new Handler();

        fm = getFragmentManager();

        initView(thisView);
        initData();
        initEvent();
        return thisView;
    }

    private void initData() {
        chattingList = new ArrayList<ChattingMessage>();
        ChattingMessage welcomeMessage = new ChattingMessage();
        welcomeMessage.setDate(new Date());
        welcomeMessage.setMsg("您好，今天您感觉怎么样，有什么要问我的么？");
        welcomeMessage.setType(ChattingMessage.Type.INCOMING);
        chattingList.add(welcomeMessage);
        myAdapter = new ChattingAdapter(thisContext,chattingList);
        chattingListView.setAdapter(myAdapter);

        randomQuestionList= new Gson().fromJson(getStrFromAssets("TestQaJsonFile"), qaRandomQuestion[].class);


    }

    private void initView(View view) {
        sendingButton = (Button) view.findViewById(R.id.qa_fragment_click_button);
        voiceButton = (Button) view.findViewById(R.id.qa_fragment_voice_button);
        inputContent = (EditText)view.findViewById(R.id.qa_fragment_input_editview);
        chattingListView = (ListView)view.findViewById(R.id.qa_fragment_content_listview);

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,"zh");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(thisContext,
                    "NO SPEECH FUNCTION",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void initEvent() {
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();

            }
        });
        sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = inputContent.getText().toString();
                if (toMsg.length() == 0) {
                    Toast.makeText(thisContext, "input is empty", Toast.LENGTH_SHORT).show();
                }
                ChattingMessage toMessage = new ChattingMessage();
                toMessage.setMsg(toMsg);
                toMessage.setDate(new Date());
                toMessage.setType(ChattingMessage.Type.OUTCOMING);
                chattingList.add(toMessage);
                myAdapter.notifyDataSetChanged();
                chattingListView.setSelection(chattingList.size() - 1);
                inputContent.setText("");


                getRandomQuestionDialogFrament();

//                new Thread() {
//                    public void run() {
//                        // 不能用tulingresulet
//                        // 做为message的obj传递，因为那边会强转，所以这里必须处理成chatMessage
//                        // TuLingResult resultBean =
//                        // ResultToBeanFactory.ResultToBean(resultMessage);
////                        ChatMessage resultMessage = HttpUtil.getChatMessage(toMsg);
////                        Message m = Message.obtain();
////                        m.obj = resultMessage;
////                        mHandler.sendMessage(m);
//
//
//                        try {
//                            sleep(3000);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    ChattingMessage fakeMessage = new ChattingMessage();
//                                    fakeMessage.setDate(new Date());
//                                    fakeMessage.setMsg("今天6-12度，多云，适合外出走走");
//                                    fakeMessage.setType(ChattingMessage.Type.INCOMING);
//                                    chattingList.add(fakeMessage);
//                                    myAdapter.notifyDataSetChanged();
//                                }
//                            });
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
                //触发键盘的v是editview，让输入法消失
//                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                        .hideSoftInputFromWindow(inputEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                InputMethodManager imm = (InputMethodManager) thisContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputContent.getWindowToken(), 0);

            }
        });
}

    //这个不会blog住主线程，所以如果真机要这样的话，必须cancel 或者ok 即问题回答以后再调用。
    private void getRandomQuestionDialogFrament() {
        //先是随机数 25%的概率出随机问题，出了随机问题，就问，问完以后要把随机问题从数组中删了
        //如果要随机产生问题，就在这里产生。从抽样问题之中选出随机问题
        // 产生新的alertdialog
        //测试问题的数组是8，所以要余8（数组的长度，数组的长度会变）

        final Random rand = new Random();
        int diceRoll = rand.nextInt(100);
        System.out.println("diceRoll = " + diceRoll);
        if(diceRoll<=99){
            RandomQuestionDialog alertdFragment = new RandomQuestionDialog();
            // Show Alert DialogFragment
            Bundle args = new Bundle();
            args.putString("questionContent",randomQuestionList[diceRoll%randomQuestionList.length].questionBody);
            args.putString("questionAnswerA",randomQuestionList[diceRoll%randomQuestionList.length].secondAnswer);
            args.putString("questionAnswerB",randomQuestionList[diceRoll%randomQuestionList.length].firstAnswer);
            alertdFragment.setArguments(args);
            alertdFragment.show(fm, "Alert Dialog Fragment");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    inputContent.setText(result.get(0));
                }
                break;
            }

        }



    }
    private String getStrFromAssets(String name){
        String strData = null;
        try {
            InputStream inputStream =  getActivity().getAssets().open(name);
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


}
