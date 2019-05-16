package com.tao.demo.nfcDemo;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.tao.demo.R;

import java.io.UnsupportedEncodingException;

public class NFCActivity extends AppCompatActivity {
    TextView tvText;
    NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        tvText = findViewById(R.id.tv_text);
        NfcUtils nfcUtils = new NfcUtils(this);
        NfcUtils.NfcInit(this);
        NfcUtils.NfcCheck(this);
//        doNfcRead();
    }

    private void doNfcRead() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            ToastUtils.showShort("该设备不支持NFC");
            return;
        }
        if ((null != mNfcAdapter) && !mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请在系统设置中先启用NFC功能", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            finish();

            return;
        }
    }
//
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//        Log.e("===nfc===", event.toString());
//        String text = ("Beam me up, Android!\n\n" +
//                "Beam Time: " + System.currentTimeMillis());
//        NdefMessage msg = new NdefMessage(
//                new NdefRecord[]{NdefRecord.createMime(
//                        "application/vnd.com.example.android.beam", text.getBytes())
//                        /**
//                         * The Android Application Record (AAR) is commented out. When a device
//                         * receives a push with an AAR in it, the application specified in the AAR
//                         * is guaranteed to run. The AAR overrides the tag dispatch system.
//                         * You can add it back in to guarantee that this
//                         * activity starts when receiving a beamed message. For now, this code
//                         * uses the tag dispatch system.
//                        */
//                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
//                });
//        return msg;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        NfcUtils.mNfcAdapter.enableForegroundDispatch(this,
                NfcUtils.mPendingIntent,
                NfcUtils.mIntentFilter,
                NfcUtils.mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            String str = NfcUtils.readFromTag(intent);
            updateTextview(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void updateTextview(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvText.setText("接收到数据：" + s);
            }
        });

    }
}
