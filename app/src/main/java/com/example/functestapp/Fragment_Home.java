package com.example.functestapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Create by Yin on 2019/10/28
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class Fragment_Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private TextView logMessage;
    private Button button7;
    public static String message = "";
    public static final String IP = "127.0.0.1";
    public static final int PORT = 3000;
    public Fragment_Home() {
        // Required empty public constructor
    }

//    private static void Log(String txt) {
//        System.out.println(txt);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        logMessage = view.findViewById(R.id.textViewForLog);
        logMessage.setGravity(Gravity.BOTTOM);

        button7 = view.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("TEST", "button onClick");
                new Thread(new Runnable(){
                    @Override
                    public void run() {
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
                            System.out.print("接收的数据为:"+message);
                            logMessage.setText(message);
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        return view;
    }

}
