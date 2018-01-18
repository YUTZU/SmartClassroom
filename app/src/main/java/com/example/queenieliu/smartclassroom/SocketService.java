package com.example.queenieliu.smartclassroom;

import android.app.Service;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Queenie Liu on 2018/1/9.
 */

/*
建立連線
取得是否連線
傳送字串
取得字串
 */

public class SocketService  {

    Socket clientSocket;
    String tmp,msg;

    public SocketService() {
    }

    public Runnable readData;

    {
        readData = new Runnable() {
            @Override
            public void run() {
                String serverIp = "192.168.1.5";
                int serverPort = 8002;
                try {
                    clientSocket = new Socket(serverIp, serverPort);

                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    while (clientSocket.isConnected()) {
                        tmp = br.readLine();
                        if (tmp != null) {
                            msg = tmp;

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public String getMsg(){
        return msg;
    }

}
