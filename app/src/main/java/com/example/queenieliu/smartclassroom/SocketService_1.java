package com.example.queenieliu.smartclassroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Queenie Liu on 2018/1/9.
 */

public class SocketService_1 implements Runnable {


    Socket clientSocket;
    String tmp,msg;

    @Override
    public void run() {


        String serverIp = "140.115.158.189";
        int serverPort =8002;
        try {
            clientSocket = new Socket(serverIp,serverPort);

            BufferedReader br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (clientSocket.isConnected()){
                tmp = br.readLine();
                if(tmp!=null){
                    msg=tmp;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
