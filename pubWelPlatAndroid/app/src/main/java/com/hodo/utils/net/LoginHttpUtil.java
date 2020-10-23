package com.hodo.utils.net;

import android.os.Handler;
import android.os.Message;

import com.hodo.bean.SessionInfo;
import com.hodo.utils.CheckUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by gdszm on 2018/12/12.
 */

public class LoginHttpUtil {
    //get方式登录
    public static void requestNetForGetLogin(final Handler handler, final String username, final String password) {

        //在子线程中操作网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    //1：url对象
                    URL url = new URL("http://10.0.2.2:8090//secHandBook/user!doNotNeedSession_loginA.do?cname=" + URLEncoder.encode(username)+ "&cpwd=" +  URLEncoder.encode(password) + "");
                    //2;url.openconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //3
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10 * 1000);
                    //4
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = StreamUtil.stremToString(inputStream);
                        System.out.println("=====================服务器返回的信息：：" + result);
                        boolean isLoginsuccess=false;
                        if (result.contains("success")) {
                            isLoginsuccess=true;
                        }
                        Message msg = Message.obtain();
                        msg.obj=isLoginsuccess;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post方式登录
    public static void requestNetForPOSTLogin(final Handler handler,final String username,final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    //1：url对象
                    URL url = new URL(URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ URLProtocol.ACTION_LOGIN);
                    System.out.print(url);
                    //2;url.openconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    //3设置请求参数
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10 * 1000);
                    //请求头的信息
                    String body = "cname=" + URLEncoder.encode(username) + "&cpwd=" + URLEncoder.encode(password);
                    conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
                    conn.setRequestProperty("Cache-Control", "max-age=0");
                    conn.setRequestProperty("Origin", URLProtocol.ROOT+":"+ URLProtocol.PORT);

                    //设置conn可以写请求的内容
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(body.getBytes());

                    //4响应码
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = StreamUtil.stremToString(inputStream);
                        System.out.println("=====================服务器返回的信息：：" + result);
                        JSONObject j = new JSONObject(result);
                        boolean b = j.getBoolean("success");
                        if(b && j.has("obj") && !"null".equals(j.get("obj").toString())) {
                            System.out.println("userId:"+j.get("obj").toString());
                            SessionInfo.setUserId(j.get("obj").toString());
                            SessionInfo.setUserGroup(j.getString("msg"));
                        }
                        boolean isLoginsuccess=b;
                        Message msg = Message.obtain();
                        msg.obj=isLoginsuccess;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}