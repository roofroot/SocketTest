package com.yuxiu.myim.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class KeepSocketUtil implements Runnable {
    private Socket clientSocket;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;
    private Handler handler;

    public KeepSocketUtil(Socket socket, Handler handler) {

            this.handler = handler;
            this.clientSocket = socket;
            new Thread(this).start();

    }

    public void sendMsg(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataOutputStream.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
    public void close(){
        if(clientSocket!=null){
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        if(clientSocket==null){
            return;
        }
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String str = dataInputStream.readUTF();
                Log.e("msg","服务器接受消息"+str);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", str);
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
