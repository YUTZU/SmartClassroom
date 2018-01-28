package com.example.queenieliu.smartclassroom.object;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Queenie Liu on 2018/1/21.
 */

public class SocketState   {

    String host;
    int port;
    Socket socket = null;
    DataInputStream input = null;
    DataOutputStream output =null;
    String msg=null,tmp;
    BufferedReader reader;
    BufferedWriter writer;

    public SocketState(String host,int port) {
        this.host=host;
        this.port=port;

    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket connectServer() throws IOException {
        socket=new Socket(host,port);
        return socket;
    }




    public Boolean isConnect(Socket socket){
        Boolean connect=null;
        if(socket.isConnected()){
            connect=true;
        }else{
            connect=false;
        }
        return connect;
    }

    public String readMsg(Socket socket) {

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected()){

                tmp=reader.readLine();
                if(tmp!=null){
                    msg=tmp;
                }
                return msg;
            }
        } catch (IOException e) {
            Log.d("QQQQQUEENIE",e.getMessage());
        }
        return msg;
    }





    public void closeSocket(){

    }

}
