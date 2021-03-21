package com.yuxiu.myim.socket;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketUtil implements Runnable{
    public static int prot=1040;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private KeepSocketUtil keepSocketUtil;

    public KeepSocketUtil getKeepSocketUtil() {
        return keepSocketUtil;
    }

    public void setKeepSocketUtil(KeepSocketUtil keepSocketUtil) {
        this.keepSocketUtil = keepSocketUtil;
    }

    Handler handler;
    public ServerSocketUtil(Handler handler){
        this.handler=handler;
        new Thread(this).start();
    }
    public void close(){
        if(serverSocket!=null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        if(serverSocket==null) {
            try {
                serverSocket = new ServerSocket(prot);
                Log.e("server","服务器启动成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("server","服务器启动失败"+e.getMessage());
            }
        }
        if(serverSocket!=null) {
            while (true) {

                try {
                    this.clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e("server", "有客户端链接失败"+e.getMessage());
                    e.printStackTrace();
                }
                if (clientSocket != null) {
                    Log.e("server", "有客户端请求链接");
                    this.keepSocketUtil = new KeepSocketUtil(clientSocket, handler);
//                    break;
                }
            }
        }



    }
}
