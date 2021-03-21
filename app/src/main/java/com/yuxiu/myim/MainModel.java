package com.yuxiu.myim;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yuxiu.myim.base.BaseModel;
import com.yuxiu.myim.base.MyBaseModel;
import com.yuxiu.myim.databinding.ActivityMainBinding;
import com.yuxiu.myim.socket.ClientSocketUtil;
import com.yuxiu.myim.socket.ServerSocketUtil;
import com.yuxiu.myim.utils.NetUtils;

import java.io.PipedInputStream;
import java.net.InetAddress;
import java.net.SocketException;

public class MainModel extends MyBaseModel<ActivityMainBinding> {
    ClientSocketUtil clientSocketUtil;
    ServerSocketUtil serverSocketUtil;
    PipedInputStream cPipe;
    boolean isServer;

    public MainModel(ActivityMainBinding binding, Activity context) {
        super(binding, context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = null;
                try {
                    content = NetUtils.getInnetIp();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                Log.e("ip", content);
                binding.ip.setText("本机ip" + content);
            }
        }).start();
//        binding.etIp.setText("117.101.220.177");
//        binding.etIp.setText("223.104.41.212");
//        binding.etIp.setText("127.0.0.1");
        bindListener(binding.tvStartClient, binding.tvStartServer,binding.tvSend);

    }

    private Handler msghandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String msg = message.getData().getString("msg");
            if (!TextUtils.isEmpty(msg)) {
                binding.msg.setText(binding.msg.getText().toString()
                        + "\n收到消息:" + msg);
            }
            return false;
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_client:
                if(TextUtils.isEmpty(binding.etIp.getText())) {
                    Toast.makeText(context,"请键入正确的ip地址",Toast.LENGTH_LONG).show();
                    return;
                }
                ClientSocketUtil.ip=binding.etIp.getText().toString();
                clientSocketUtil = new ClientSocketUtil(msghandler);
                binding.tvStartServer.setEnabled(false);
                break;
            case R.id.tv_start_server:
                isServer=true;
                serverSocketUtil = new ServerSocketUtil(msghandler);
                binding.tvStartClient.setEnabled(false);
                break;
            case R.id.tv_send:
                if(TextUtils.isEmpty(binding.etMsg.getText())){
                    return;
                }
                if(isServer){
                    serverSocketUtil.getKeepSocketUtil().sendMsg(binding.etMsg.getText().toString());
                }else{
                    clientSocketUtil.sendMsg(binding.etMsg.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serverSocketUtil.getKeepSocketUtil().close();
        serverSocketUtil.close();
        clientSocketUtil.close();
    }
}
