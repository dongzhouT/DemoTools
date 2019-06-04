package com.tao.demo.minaDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tao.demo.R;
import com.tao.demo.minaDemo.mina.MinaTcpClient;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MinaDemoActivity extends AppCompatActivity implements View.OnClickListener {
    CommandManager mManger;
    MinaTcpClient.Callback mCallback;
    TextView tvLog;

    EditText edtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina_demo);
        initUI();
        initEvent();
        mManger = new CommandManager(this);
        MinaTcpClient.setCallback(mCallback);
    }

    public final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private void initEvent() {
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_write).setOnClickListener(this);
        mCallback = new MinaTcpClient.Callback() {
            @Override
            public void connectedOK() {
                updateLog("connectedOK");
                CommandManager.isConnect = true;
            }

            @Override
            public void connectedFail() {
                updateLog("connectedFail");
                CommandManager.isConnect = false;
            }

            @Override
            public void disconnected() {
                updateLog("disconnected");
            }

            @Override
            public void dataReceived(byte[] data, int len) {
                Log.d(CommandManager.TAG, "sb3==>" + CommonCodeUtil.bytesToHexString(data) + ",len=" + len);
                if (data == null) {
                    return;
                }
                if (data.length == 20) {
                    //读取imei
                    String imei = mManger.getImei(data);
                    updateLog("dataReceive==>" + imei);
                } else if (data.length == 3 && len == 0) {
                    updateLog("dataReceive==>" + "写入成功");
                }
            }

            @Override
            public void clientException(String eMessage) {
                updateLog("clientException==>" + eMessage);
            }

            @Override
            public void sentDataComplete() {
                updateLog("sentDataComplete");
            }

            @Override
            public void setMod() {
                updateLog("setMod");
            }

            @Override
            public void setTime() {
                updateLog("setTime");
            }
        };
    }


    private void initUI() {
        tvLog = findViewById(R.id.tv_getdata);
        edtData = findViewById(R.id.edt_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendData();
                break;
            case R.id.btn_write:
                writeData(edtData.getText().toString());
                break;
            case R.id.btn_clear:
                clearLog();
                break;
        }
    }

    private void writeData(String data) {
        byte[] result = mManger.getWriteData(data);
        mManger.sendData(result);
    }

    private void clearLog() {
        tvLog.setText("");
    }

    //查询
    private void sendData() {
        mManger.queryImei();
    }

    private void updateLog(final String ss) {
        Log.d(CommandManager.TAG, ss);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvLog.append(ss + "\n");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManger.disConnect();
    }

}
