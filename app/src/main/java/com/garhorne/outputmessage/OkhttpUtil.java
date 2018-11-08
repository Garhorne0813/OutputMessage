package com.garhorne.outputmessage;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Okhttp的封装类
 */

public class OkhttpUtil {

    private Context context;

    public OkhttpUtil(Context context){
        this.context = context;
    }

    public void Connect(String json, String address, Callback callback){
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),json);
            Request request = new Request.Builder().url(address).post(requestBody).build();
            client.newCall(request).enqueue(callback);
        }catch (IllegalArgumentException e1){
            e1.printStackTrace();
            Toast.makeText(context,"服务器连接错误",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
