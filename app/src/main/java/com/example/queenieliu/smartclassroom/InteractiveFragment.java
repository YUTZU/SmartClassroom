package com.example.queenieliu.smartclassroom;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.example.queenieliu.smartclassroom.R.drawable.sky;


/**
1.get course imfo from database
 2.make sure connect to server

 */
public class InteractiveFragment extends Fragment {
    Button submitBtn ;
    TextView quesTv;
    CardView classCv;
    TextView timeTv,courseTv,classTv,teacherTv,lessonTv,peopleTv,connectTv,scoreTv;
    String ServerURL="http://140.115.158.189/ShowCourse.php";
    String TAG ="QQQQQQUEENIE";
    Message message;

    public InteractiveFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.just_test_123, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        new GetData().execute(ServerURL); // 要做判斷 若沒有課程 顯示無課程資訊

        Thread thread = new Thread(readSocket);
        thread.start();
    }

    public Runnable readSocket;
    {
        readSocket = new Runnable() {
            @Override
            public void run() {
                String msg=null;
                SocketState socketState=new SocketState("192.168.1.5",8002);
                Socket socket= null;
                try {
                    socket = socketState.connectServer();
                    while (socketState.isConnect(socket)){
                        msg = socketState.readMsg(socket);
                        message = newHandler.obtainMessage(1,msg);
                        newHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public void setQuess(JSONObject jsonObject){
        try {

            int res = getActivity().getResources().getIdentifier(jsonObject.getString("type"), "layout", getActivity().getPackageName());
            getActivity().setContentView(res);
            quesTv=(TextView)getActivity().findViewById(R.id.quesTv);
            submitBtn=(Button)getActivity().findViewById(R.id.submitBtn);
            final String[] ansStr = new String[1];
            EditText ansEdt = null;
            InteractionObject subBtn=null;

            InteractionObject interBtn = null;
            ArrayList<Button> buttons = new ArrayList<Button>();

            final InteractionObject interObj = new InteractionObject();
            interObj.setQuestion(jsonObject.getString("ques"));
            interObj.setCorrectAns(jsonObject.getString("ans"));
            interObj.setOption(jsonObject.getJSONArray("opt"));

            quesTv.setText(interObj.getQuestion());

            switch (jsonObject.getString("type")){
                case "inter_multi_choice":
                    int[] BUTTON_IDS = {
                            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12};
                    interBtn = setButton(BUTTON_IDS,interBtn,buttons);
                    interBtn.setMultiOption(buttons,interObj.getOption());

                    break;

                case "inter_yes_no":
                    int[] BUTTON_YN={R.id.yesBtn,R.id.noBtn};
                    setButton(BUTTON_YN,interBtn,buttons);

                    break;

                case "inter_typing":
                    ansEdt=(EditText)getActivity().findViewById(R.id.ansEdt);
                    final EditText finalAnsEdt1 = ansEdt;

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            interObj.setAnswer(finalAnsEdt1.getText().toString());
                            new SendData().execute(interObj);
                            finalAnsEdt1.setText("");
                        }
                    });
                    break;
            }


            final EditText finalAnsEdt = ansEdt;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

public InteractionObject setButton(int[]BUTTON_IDS,InteractionObject interBtn,ArrayList<Button> buttons){
    for(int id : BUTTON_IDS) {
        Button button = (Button)getActivity().findViewById(id);
        interBtn=new InteractionObject(button);
        buttons.add(button);

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
    return interBtn;
}


    Handler newHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {

                JSONObject msgObj=new JSONObject(msg.obj.toString());
                String type = msgObj.getString("type");
                Log.d("QQQQQUEENIE",type);

                switch (type){
                    case "connect":
                        connectTv.setText("連線成功");
                        break;
                    default:
                        setQuess(msgObj);
                        break;
                }
            } catch (JSONException e) {
                Log.d("QQQQQUEENIE",e.getMessage());
            }
            super.handleMessage(msg);
        }
    };




     public class SendData extends AsyncTask<InteractionObject, Void, InteractionObject> {
        @Override
        protected InteractionObject doInBackground(InteractionObject... strings) {
            String urlstring = "http://140.115.158.189/SaveRecord.php";

            try {
                Log.d(TAG,strings[0].getQuestion());

                String data = URLEncoder.encode("answer","UTF-8")+"="+URLEncoder.encode(strings[0].getAnswer(),"UTF-8");
                data += "&" + URLEncoder.encode("score","UTF-8")+"="+URLEncoder.encode("3","UTF-8");
                data += "&" + URLEncoder.encode("timetable_id","UTF-8")+"="+URLEncoder.encode("1","UTF-8");
                data += "&" + URLEncoder.encode("student_id","UTF-8")+"="+URLEncoder.encode("4","UTF-8");
                data += "&" + URLEncoder.encode("interation_id","UTF-8")+"="+URLEncoder.encode("5","UTF-8");

                URL url = new URL(urlstring);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setChunkedStreamingMode(0);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(data);
                writer.flush();
                connection.getResponseCode();
                writer.close();
                os.close();


            } catch (MalformedURLException e) {
                Log.d(TAG, e.getMessage());

            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }


    public class GetData extends AsyncTask<String,Void,CourseObject>{
        @Override
        protected CourseObject doInBackground(String... strings) {
            StringBuilder stringBuffer = new StringBuilder();
            Log.d(TAG, "1234");
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    // 讀取網頁內容
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String tempStr;
                    while ((tempStr = bufferedReader.readLine()) != null) {
                        stringBuffer.append(tempStr);
                    }
                }

            } catch (MalformedURLException e) {
                Log.d(TAG, e.getMessage());

            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }
            return ParseJson(stringBuffer.toString());
            //return stringBuffer.toString();
        }

        protected void onPostExecute(CourseObject result) {
            super.onPostExecute(result);
            View view = getView();
            view.setBackgroundResource(sky);
           courseTv.setText(result.getCourse());
           classTv.setText(result.getClassStr());
           lessonTv.setText(result.getLesson());
           timeTv.setText(result.getTime());
           teacherTv.setText(result.getTeacher());
        }
    }

    public CourseObject ParseJson(String data){
        CourseObject courseObject = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<=jsonArray.length();i++ ){
                JSONObject courseObj = jsonArray.getJSONObject(i);
                String teacher  = courseObj.getString("teacher");
                String course = courseObj.getString("course");
                String lesson = courseObj.getString("lesson");
                String classstr= courseObj.getString("class");
                String time=courseObj.getString("time");

                courseObject = new CourseObject(course,classstr,lesson,teacher,time);
                
                Log.d(TAG, teacher);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return courseObject;
    }

    public void init(){
        classCv=(CardView)getView().findViewById(R.id.classCv);
        timeTv=(TextView)getView().findViewById(R.id.timeTv);
        classTv=(TextView)getView().findViewById(R.id.classTv);
        courseTv=(TextView)getView().findViewById(R.id.courseTv);
        peopleTv=(TextView)getView().findViewById(R.id.peopleTv);
        teacherTv=(TextView)getView().findViewById(R.id.teacherTv);
        lessonTv=(TextView)getView().findViewById(R.id.lessonTv);
        connectTv=(TextView)getView().findViewById(R.id.connectTv);
        scoreTv=(TextView)getView().findViewById(R.id.scoreTv);
    }


}
