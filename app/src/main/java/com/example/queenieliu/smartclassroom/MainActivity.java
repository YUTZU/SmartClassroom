package com.example.queenieliu.smartclassroom;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.queenieliu.smartclassroom.object.SocketState;
import com.example.queenieliu.smartclassroom.DELETE.TTTTestActivity;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends BaseActivity {

    Button interBtn,studyBtn,recordBtn,testBtn;
    String tmp;
    String serverIp= "192.168.1.5" ;
    int serverPort = 8002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        setInit();

        interBtn.setOnClickListener(clickListener);
        testBtn.setOnClickListener(clickListener);
        studyBtn.setOnClickListener(clickListener);
        recordBtn.setOnClickListener(clickListener);

    }


    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.studyBtn:

                    break;
                case R.id.testBtn:
                    Intent intent= new Intent();
                    intent.setClass(MainActivity.this,TTTTestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;
                case R.id.recordBtn:
                    Intent recordIntent= new Intent();
                    recordIntent.setClass(MainActivity.this,TTTTestActivity.class);
                    recordIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(recordIntent);
                    break;

                case R.id.interBtn:
                    Intent interIntent = new Intent();
                    interIntent.setClass(MainActivity.this,InteractiveActivity.class);
                    interIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(interIntent);
                    break;
            }
        }
    };


    public Runnable readData;
    {
        readData = new Runnable() {
            @Override
            public void run() {

                SocketState socketState = new SocketState(serverIp, serverPort);
                Socket socket = null;
                try {
                    socket = socketState.connectServer();
                    if (socketState.isConnect(socket)) {
                        String msg = socketState.readMsg(socket);
                        Log.d("QQQQQUEENIE",msg);
                        Message message;
                        message = mHandler.obtainMessage(1,tmp);
                        mHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String MsgString = (String)msg.obj;
            if(msg.what == 1) {
                Toast.makeText(MainActivity.this,MsgString,Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    public void setInit(){
        interBtn=(Button) findViewById(R.id.interBtn);
        studyBtn=(Button)findViewById(R.id.studyBtn);
        testBtn=(Button)findViewById(R.id.testBtn);
        recordBtn=(Button)findViewById(R.id.recordBtn);
    }

}
