package com.tao.demo.minaDemo.mina;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.util.Log;

/*
 *
 * @author Shiun
 */
public class Command {
    static String tag = "Command";
    static private byte sn = (byte) 0x00;
    private byte dataLen;
    private byte[] data;
    private byte[] wholeData;
    private byte checksum;

    public Command( byte dataLen, byte[] data, byte checksum, boolean isRecv)
    {

        if(isRecv)
        {
            Log.i(tag, "isRecv");
            //處理接收到的封包
            this.dataLen = (byte) (dataLen&0xFF);
            this.data = data;
            //檢查checksum
            byte mChecksum = getCheckSum(this.sn,data);
            //
            /*if(mChecksum == checksum)
            {
                this.checksum = 0x00;
                Log.e("Command","checksum ok");
            }
            else
            {
                this.checksum = (byte)0xFF;
                Log.e("Command","checksum error");
            }*/



        }
        else{

            //處理將發送的封包

            Log.i(tag, "isSend");
            if(sn == 0xff)
            {
                sn = 0x00;
            }
            else
            {
                this.sn++;
            }
            this.dataLen = (byte) (dataLen&0xFF);
            this.data = data;
            this.checksum = getCheckSum(this.sn,data);

        }


        Log.i("CMD", "checksum= " + Integer.toHexString(this.checksum));
        printHex();
    }

    public byte getSn() {
        return sn;
    }

    public byte getDataLen() {
        return dataLen;
    }

    public byte[] getData() {
        return data;
    }

    public byte getChecksum() {
        return checksum;
    }
    
    public void printHex() {
        System.out.printf("%02X ", this.sn);
        System.out.printf("%02X ", this.dataLen);
        for(int i=0;i<this.dataLen;++i) {
            System.out.printf("%02X ", this.data[i]);
        }
        System.out.printf("%02X ", this.checksum);
        System.out.println();
    }

    private byte getCheckSum(byte sn,byte[] cmd)
    {
        byte sum=0x00;
        sum = (byte) ((sum+0x47) % 0x100);
        sum = (byte) ((sum+sn) % 0x100);
        Log.i("","sn="+ Integer.toHexString(sn));
        sum = (byte) ((sum+cmd.length) % 0x100);

        Log.i("","sn="+ Integer.toHexString(sum));
        for(int i=0;i<cmd.length;i++)
        {
            sum = (byte) ((sum+cmd[i]) % 0x100);
            Log.i("","num "+i +"+="+ Integer.toHexString(data[i]));
            Log.i("","sum="+ Integer.toHexString(sum));
        }
        Log.i("","final sum="+ Integer.toHexString(sum));

        Log.i("","checksum="+ Integer.toHexString(0xFF - sum));
        //request = data;
        return (byte)(0xFF - sum);
    }
}
