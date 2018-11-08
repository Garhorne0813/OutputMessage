package com.garhorne.outputmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_msg;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化view
        initViews();
    }

    private void initViews() {
        et_msg = (EditText)findViewById(R.id.et_msg);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                String msgStr = et_msg.getText().toString();
                if (!msgStr.equals("")){
                    sendMessage(msgStr);
                }else {
                    et_msg.setError("Please input your message");
                }
                break;
            default:
                break;
        }
    }

    private void sendMessage(String m) {
        Message msg = new Message();  //这里是我写的一个类 可以改成其他自定义的类
        msg.setMes(m);
        Gson gson = new Gson();
        String json = gson.toJson(msg);  //toJson传入一个自定义的Message的对象
        Log.d("SendMessage",json);
        OkhttpUtil okhttpUtil = new OkhttpUtil(this);
        okhttpUtil.Connect(json, Constant.URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resp = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //将服务器返回的数据显示出来
                        Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
