package com.yuxiu.myim.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketUtil implements Runnable {
    public static String ip;
    public static int prot=1040;
    private Socket clientSocket;
    private  DataInputStream dataInputStream= null;
    private  DataOutputStream dataOutputStream = null;
    private Handler handler;
    public ClientSocketUtil(Handler handler){
//            new Thread(this).run();
        this.handler=handler;
        new Thread(this).start();
    }
    public void sendMsg(String  msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("client","客户端发送消息"+msg);
                    dataOutputStream.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("client","客户端发送消息出错");
                    Log.e("client",e.getMessage());
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
        if(clientSocket==null) {
            try {
                InetAddress serverAddr = InetAddress.getByName(ip);
                clientSocket = new Socket(ip, prot);
                if(clientSocket.isConnected()) {
                    Log.e("client","客户端链接成功");
                    dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    while (true) {
                        try {
                            if(dataInputStream!=null) {
                                String str = dataInputStream.readUTF();
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", str);
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else{

                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("client","链接失败");
            }

        }

    }
}
