package com.example.yamengwenjing.yiyiguanai.UI.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


import com.example.yamengwenjing.yiyiguanai.Entity.qaRandomQuestion;
import com.example.yamengwenjing.yiyiguanai.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomQuestionDialog extends DialogFragment {


    public RandomQuestionDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String questionContent = args.getString("questionContent");
        String answerA = args.getString("questionAnswerA");
        String answerB = args.getString("questionAnswerB");


        return new AlertDialog.Builder(getActivity())
                        // Set Dialog Title
                .setTitle("临时问题")
                        // Set Dialog Message
                .setMessage(questionContent)

                        // Positive button
                .setPositiveButton(answerA, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                })

                        // Negative Button
                .setNegativeButton(answerB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }

}
