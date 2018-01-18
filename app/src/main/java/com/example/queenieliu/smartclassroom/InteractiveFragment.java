package com.example.queenieliu.smartclassroom;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.queenieliu.smartclassroom.R.drawable.sky;


/**
 * A simple {@link Fragment} subclass.
 */
public class InteractiveFragment extends Fragment {
    Button button ;
    TextView quesTv;
    CardView classCv;
    TextView timeTv,courseTv,classTv,teacherTv,lessonTv,peopleTv;

    public InteractiveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inter_main, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        view.setBackgroundResource(sky);  //



        Bundle bundle = getArguments();
        if (bundle != null) {
            String item = bundle.getString("ques");
            quesTv.setText("I can get there by _____ from Taipei.");
        }



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SocketService socketService= new SocketService();
//                Thread thread = new Thread(socketService.readData);
//                thread.start();
//            }
//        });
    }

    public void init(){
        button=(Button)getView().findViewById(R.id.btn1);
        classCv=(CardView)getView().findViewById(R.id.classCv);
        timeTv=(TextView)getView().findViewById(R.id.timeTv);
        classTv=(TextView)getView().findViewById(R.id.classTv);
        courseTv=(TextView)getView().findViewById(R.id.courseTv);
        peopleTv=(TextView)getView().findViewById(R.id.peopleTv);
        teacherTv=(TextView)getView().findViewById(R.id.teacherTv);
        lessonTv=(TextView)getView().findViewById(R.id.lessonTv);
    }


public void setClass(){
        getView().setBackgroundResource(sky);

        String timeStr="",classStr="",courseStr="",peopleStr="",teacherStr="";
        quesTv=(TextView)getView().findViewById(R.id.quesTv);


        timeTv.setText(timeStr);
        courseTv.setText(courseStr);
        peopleTv.setText(peopleStr);
        teacherTv.setText(teacherStr);
        classTv.setText(classStr);



    }


}
