package com.example.queenieliu.smartclassroom.object;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Queenie Liu on 2018/1/22.
 */

public class InteractionObject {

    String question,correctAns,type;
    String answer;
    JSONArray option;
    Button button;
    Boolean isPress;

    public InteractionObject( ) {
    }

    public InteractionObject(Button button) {
        this.button = button;
        this.isPress = false;
    }

    public InteractionObject(String question, String correctAns, String type, JSONArray option) {
        this.question = question;
        this.correctAns = correctAns;
        this.type = type;
        this.option = option;
    }

    public void setMultiOption(ArrayList<InteractionObject> buttonObjects, JSONArray array){
        for(int i =0; i<array.length();i++){
            try {
                buttonObjects.get(i).getButton().setText(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public JSONArray setSelectAns(ArrayList<InteractionObject> buttonObjects){
        JSONArray jsonArray=new JSONArray();
        int p=0;
        for(int i =0; i< buttonObjects.size();i++){
            if(buttonObjects.get(i).getPress()==true){
                p++;
                jsonArray.put(buttonObjects.get(i).getButton().getText().toString());
            }
        }
        return jsonArray;
    }

    public void changeMode(){

                if(getPress()==false){
                    getButton().getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    setPress(true);
                }else {
                    getButton().getBackground().clearColorFilter();
                    setPress(false);
                }
    }


    public String getCorrectAns() { return correctAns; }

    public void setCorrectAns(String correctAns) { this.correctAns = correctAns; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }





    public void setType(String type) {
        this.type = type;
    }

    public void setOption(JSONArray option) {
        this.option = option;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setPress(Boolean press) {
        isPress = press;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getType() {
        return type;
    }

    public JSONArray getOption() {
        return option;
    }

    public Button getButton() {
        return button;
    }

    public Boolean getPress() {
        return isPress;
    }
}
