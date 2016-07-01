package com.example.yamengwenjing.yiyiguanai.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yamengwenjing.yiyiguanai.R;
import com.example.yamengwenjing.yiyiguanai.UI.CameraRecord.CameraPreview;
import com.example.yamengwenjing.yiyiguanai.UI.CameraRecord.CameraRecordActivity;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    Button cameraButton ;
    Context thisContext;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView  = inflater.inflate(R.layout.fragment_camera,container,false);
        thisContext = getContext();
//        return inflater.inflate(R.layout.fragment_camera, container, false);
        cameraButton = (Button) thisView.findViewById(R.id.camera_fragment_trigger_camera_button);
        cameraButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToRecord =  new Intent(thisContext, CameraRecordActivity.class);
                startActivity(goToRecord);


            }
        });


        return thisView;
    }


}
