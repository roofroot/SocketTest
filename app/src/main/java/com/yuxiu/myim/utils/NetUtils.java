package com.yuxiu.myim.utils;

import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

public class NetUtils {
    public static String getInnetIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
               if (ip.isSiteLocalAddress()
                        &&!ip.isLoopbackAddress()
                        &&ip.getHostAddress().indexOf(":") == -1){// 内网IP
                   localip = ip.getHostAddress();
                   if(localip.contains("192.")){
                       return localip;
                   }

                }

            }
        }


            return localip;
    }

}
