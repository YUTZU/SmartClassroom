package com.example.queenieliu.smartclassroom;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

    public StudyFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.study_main, container, false);
        RecyclerView studyRv = (RecyclerView)view.findViewById(R.id.studyRv);
        studyRv.setHasFixedSize(true);
        CustomAdapter adapter = new CustomAdapter((new String[]{"  APPLE","BIRD","CAT","DOG","EGG","FOX","GIRL"}));
        studyRv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager((getActivity()));
        studyRv.setLayoutManager(llm);

        return view;
    }




}
