package com.example.queenieliu.smartclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.queenieliu.smartclassroom.object.InteractionObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Queenie Liu on 2018/1/26.
 */

public class InteractiveFragment extends android.support.v4.app.Fragment{
    JSONObject jsonObject =null;
    TextView quesTv,submitBtn;

    String interactiveStr;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            try {
                interactiveStr = getArguments().getString("interactive");
                jsonObject = new JSONObject(interactiveStr.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int res=0;
        try {
            res = getActivity().getResources().getIdentifier(jsonObject.getString("type"), "layout", getActivity().getPackageName());
            getActivity().setContentView(res);

            ((BaseActivity)getActivity()).hideToolbar();

            quesTv=(TextView)getActivity().findViewById(R.id.quesTv);
            submitBtn=(Button)getActivity().findViewById(R.id.submitBtn);
            final String[] ansStr = new String[1];
            EditText ansEdt = null;
            InteractionObject subBtn=null;

            InteractionObject interBtn = null;
//            final ArrayList<Button> buttons = new ArrayList<Button>();
            ArrayList<InteractionObject>buttons = new ArrayList<InteractionObject>();


            final InteractionObject interObj = new InteractionObject();
            interObj.setQuestion(jsonObject.getString("ques"));
            interObj.setCorrectAns(jsonObject.getString("ans"));
            interObj.setOption(jsonObject.getJSONArray("opt"));

            quesTv.setText(interObj.getQuestion());



            switch (jsonObject.getString("type")){
                case "inter_multi_choice":
                    int[] BUTTON_IDS = {
                            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12};

                    buttons = setButton(BUTTON_IDS,interBtn,buttons);
                    buttons.get(0).setMultiOption(buttons,interObj.getOption());
                    final ArrayList<InteractionObject> finalButtons = buttons;

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JSONArray jsonArray = finalButtons.get(0).setSelectAns(finalButtons);

                        }
                    });
                    break;

                case "inter_yes_no":
//                    int[] BUTTON_YN={R.id.yesBtn,R.id.noBtn};
//                    setButton(BUTTON_YN,interBtn,buttons);

                    break;

                case "inter_typing":
                    ansEdt=(EditText)getActivity().findViewById(R.id.ansEdt);
                    final EditText finalAnsEdt1 = ansEdt;

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            interObj.setAnswer(finalAnsEdt1.getText().toString());
                            new InteractiveActivity.SendData().execute(interObj);
                            finalAnsEdt1.setText("");
                            Intent intent = new Intent(getActivity(), InteractiveActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        } catch (JSONException e) {
            Log.d("QQQQQQUEENIE",e.getMessage());
        }


        return inflater.inflate(res,container,false);

    }



    public ArrayList<InteractionObject> setButton(int[]BUTTON_IDS, InteractionObject interBtn, ArrayList<InteractionObject> buttons){
            for(int id : BUTTON_IDS) {
                Button button = (Button)getActivity().findViewById(id);
                interBtn=new InteractionObject(button);
                buttons.add(interBtn);
                final InteractionObject finalInterBtn = interBtn;
                interBtn.getButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(finalInterBtn.getPress()==false){
                            finalInterBtn.changeMode();
                        }else{
                            finalInterBtn.changeMode();
                        }
                    }
                });
            }
            return buttons;
    }










}
