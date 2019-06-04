package com.tao.demo.minaDemo;

import android.content.Context;
import android.util.Log;

import com.tao.demo.minaDemo.mina.Command;
import com.tao.demo.minaDemo.mina.MinaTcpClient;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author taodzh
 * create at 2019/5/16
 */
public class CommandManager {
    public static String TAG = "==CommandManager==";
    Context mContext;
    private MinaTcpClient client;
    public static boolean isConnect = false;
    private ExecutorService mService;


    public CommandManager(Context context) {
        this.mContext = context;
        mService = Executors.newCachedThreadPool();
    }

    public void connect() {
        mService.execute(connectRunnable);
    }

    private Runnable connectRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                client.connector.dispose(true);
            }
            client = new MinaTcpClient(mContext);
            client.startConnection();
        }
    };

    public void sendData(byte[] data) {
        mService.execute(new SendDataThread(data));
    }

    public void disConnect() {
        mService.execute(new Runnable() {
            @Override
            public void run() {
                if (client != null) {
                    client.timeout();
                }
            }
        });
    }

    public class SendDataThread implements Runnable {
        byte[] packet;

        public SendDataThread(byte[] packet) {
            this.packet = packet;
        }

        @Override
        public void run() {
            if (!isConnect) {
                connect();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isConnect) {
                Command command = new Command((byte) packet.length, packet, (byte) 0, false);
                client.session.write(command);
                Log.d(TAG, "send success");

            } else {
                Log.d(TAG, "connect fail");
            }

        }
    }

    /**
     * 获取写入数据
     *
     * @param data
     * @return
     */
    public byte[] getWriteData(String data) {
        if (data.length() != 20) {
            return null;
        }
        byte[] cmdPacket = new byte[7];
        cmdPacket[0] = (byte) 0x88;
        cmdPacket[1] = (byte) 0x10;
        cmdPacket[2] = (byte) 0x00;
        cmdPacket[3] = (byte) 0x1E;
        cmdPacket[4] = (byte) 0x00;
        cmdPacket[5] = (byte) 0x0A;
        cmdPacket[6] = (byte) 0x14;
        byte[] dataByte = data.getBytes(StandardCharsets.US_ASCII);
        byte[] result = new byte[27];
        byte[] newResult = new byte[29];
        System.arraycopy(cmdPacket, 0, result, 0, cmdPacket.length);
        System.arraycopy(dataByte, 0, result, cmdPacket.length, dataByte.length);
        System.arraycopy(result, 0, newResult, 0, result.length);

        byte[] crc16 = CommonCodeUtil.getCRC16(result);

        System.arraycopy(crc16, 0, newResult, result.length, crc16.length);
        return newResult;
    }

    /**
     * 下发查询指令
     */
    public void queryImei() {
        byte[] cmdPacket = new byte[8];
        cmdPacket[0] = (byte) 0x88;
        cmdPacket[1] = (byte) 0x03;
        cmdPacket[2] = (byte) 0x00;
        cmdPacket[3] = (byte) 0x1E;
        cmdPacket[4] = (byte) 0x00;
        cmdPacket[5] = (byte) 0x0A;
        cmdPacket[6] = (byte) 0xBA;
        cmdPacket[7] = (byte) 0x92;
        sendData(cmdPacket);
    }

    public String getImei(byte[] packet) {
        String imeiCode = "";
        if (packet == null) {
            return null;
        }
        if (packet.length == 20) {
            imeiCode = new String(packet, StandardCharsets.US_ASCII);
        }
        return imeiCode;
    }
}
