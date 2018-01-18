package com.example.queenieliu.smartclassroom;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InterfaceAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout dLayout;
    ToggleButton socketBtn;
    Button interBtn,studyBtn,recordBtn,testBtn;
    android.support.v4.app.Fragment frag;
    TextView showTv;
    Socket clientSocket;
    String tmp;
    String serverIp= "192.168.1.5" , msg;
    int serverPort = 8002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);
        setInit();
        setToolbar();
        setNavigationDrawer();

//        socketBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(socketBtn.isChecked()){
//
//                    Thread thread = new Thread(readData);
//                    thread.start();
//            }}
//        });




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
                    StudyFragment fragment=new StudyFragment();
                    TransFragment(fragment);
                    break;
                case R.id.testBtn:
                    TestFragment fragment1=new TestFragment();
                    TransFragment(fragment1);
                    break;
                case R.id.recordBtn:
                    RecordFragment fragment2=new RecordFragment();
                    TransFragment(fragment2);
                    break;
                case R.id.interBtn:
                    InteractiveFragment fragment3=new InteractiveFragment();
                    TransFragment(fragment3);
                    break;
            }
        }
    };


    public void TransFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }



    public Runnable readData;
    {
        readData = new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(serverIp, serverPort);

                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    while (clientSocket.isConnected()) {
                        tmp = br.readLine();
                        if (tmp != null) {
                            Message message;
                            message = mHandler.obtainMessage(1,tmp);
                            mHandler.sendMessage(message);
                        }
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
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        dLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
       // socketBtn=(ToggleButton)findViewById(R.id.socketBtn);
        interBtn=(Button) findViewById(R.id.interBtn);
        studyBtn=(Button)findViewById(R.id.studyBtn);
        testBtn=(Button)findViewById(R.id.testBtn);
        recordBtn=(Button)findViewById(R.id.recordBtn);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void setToolbar(){
        toolbar.setTitle("SmartClassroom");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Digital Learning Theater");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_classroom);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(10.f);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.more:
                dLayout.openDrawer(GravityCompat.START);
                break;

//            case R.id.interactive:
//
//                InteractiveFragment fragment = new InteractiveFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container,fragment);
//                fragmentTransaction.addToBackStack("fragment");
//                Bundle bundle = new Bundle();
//                bundle.putString("ques", tmp);
//                fragment.setArguments(bundle);
//                fragmentTransaction.commit();
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationDrawer(){
        NavigationView navView=findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                frag = null;
                switch (item.getItemId()){
                    case R.id.study:
                        StudyFragment fragment1 = new StudyFragment();
                        TransFragment(fragment1);
                        break;
                    case  R.id.student:
                        frag = new android.support.v4.app.Fragment();
                        break;
                    case  R.id.test:
                        TestFragment fragment = new TestFragment();
                        TransFragment(fragment);
                        break;
                    case  R.id.score:
                        frag = new android.support.v4.app.Fragment();
                        break;
                    case  R.id.network:
                        frag = new android.support.v4.app.Fragment();
                        break;
                    case  R.id.interactive:
                        InteractiveFragment fragment2 = new InteractiveFragment();
                        TransFragment(fragment2);
                        break;
                }
               return false;
            }
        });
    }

}
