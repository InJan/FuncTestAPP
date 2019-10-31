package com.example.functestapp;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
//import java.util.ArrayList;

public class WebCommunication {

//    private static ArrayList<Float> scrollList = new ArrayList<>();
    public static String message = "";
    public static final String IP = "127.0.0.1";
    public static final int PORT = 3000;

    public void getConnection(){
        try {
            Log.e("开始连接","Start");
            Socket socket = new Socket(IP,PORT);
            Log.e("成功连接","success");
            //获取输入流
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            //获取输出流
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            out.writeUTF(userId+" "+userPassword);
            //刷新发送
            out.flush();
            message = in.readUTF();
            System.out.print("接收的数据为:"+in.readUTF());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getData(){
        return message;
    }
}
