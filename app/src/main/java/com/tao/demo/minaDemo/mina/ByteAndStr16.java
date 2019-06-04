package com.tao.demo.minaDemo.mina;

/**
 * Created by crazydream on 2015/12/15.
 */

public class ByteAndStr16 {
    private final static byte[] hex = "0123456789ABCDEF".getBytes();

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从字节数组到十六进制字符串转换
    public static String Bytes2HexString(byte[] b) {
        byte[] buff = new byte[3 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[3 * i + 1] = hex[b[i] & 0x0f];
            buff[3 * i + 2] = 45;
        }
        String re = new String(buff);
        return re.replace("-", " ");
    }
    // 从字节数组到十六进制字符串转换
    public static String OneBytes2HexString(byte b) {
        byte[] buff = new byte[2];
        //for (int i = 0; i < 1; i++) {
            buff[0] = hex[(b >> 4) & 0x0f];
            buff[1] = hex[b & 0x0f];

        //}
        String re = new String(buff);
        return re;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        hexstr = hexstr.replace(" ", "");
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    public static byte[] reverseHexData(byte[] data){

        byte[] reverseData = new byte[data.length];

        int i=0;
        for(i=0;i<data.length;i++){

            reverseData[i] = data[data.length-1-i];

        }
        return reverseData;
    }

    public static String reverseStringData(String data){

        String reverseData="";


        int i=0;
        int countByte = data.length()/2;

        for(i=0;i<countByte;i++){

            reverseData = reverseData+ data.charAt(countByte*2-2-i*2)+data.charAt(countByte*2-1-i*2);
            if(i+1!=countByte){
                reverseData = reverseData+" ";
            }
        }


        /*byte [] hex1 = HexString2Bytes(data);
        byte [] hex2 = reverseHexData(hex1);*/
        /*int i=0;
        for(i=0;i<data.length()/2;i++){



            reverseData[i] = data[data.length-1-i];

        }*/

        //reverseData = Bytes2HexString(hex2);
        return reverseData;
    }


    public static String Bytes2ASCIIString(byte[] b) {

        String nRcvString;
        StringBuffer tStringBuf = new StringBuffer();
        byte[] tBytes = b;   //实际上是ascii码字符串"123"
        char[] tChars = new char[tBytes.length];

        for(int i=0;i<tBytes.length;i++)
            tChars[i]=(char)tBytes[i];

        tStringBuf.append(tChars);
        nRcvString=tStringBuf.toString();          //nRcvString从tBytes转成了String类
        return nRcvString;
    }

}