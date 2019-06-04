package com.tao.demo.minaDemo;

import android.util.Log;

import java.math.BigInteger;

/**
 * Created by alaric on 2018/5/26.
 */
public class CommonCodeUtil {
    static byte[] crc8_tab = {(byte) 0, (byte) 94, (byte) 188, (byte) 226, (byte) 97, (byte) 63, (byte) 221, (byte) 131, (byte) 194, (byte) 156, (byte) 126, (byte) 32, (byte) 163, (byte) 253, (byte) 31, (byte) 65, (byte) 157, (byte) 195, (byte) 33, (byte) 127, (byte) 252, (byte) 162, (byte) 64, (byte) 30, (byte) 95, (byte) 1, (byte) 227, (byte) 189, (byte) 62, (byte) 96, (byte) 130, (byte) 220, (byte) 35, (byte) 125, (byte) 159, (byte) 193, (byte) 66, (byte) 28, (byte) 254, (byte) 160, (byte) 225, (byte) 191, (byte) 93, (byte) 3, (byte) 128, (byte) 222, (byte) 60, (byte) 98, (byte) 190, (byte) 224, (byte) 2, (byte) 92, (byte) 223, (byte) 129, (byte) 99, (byte) 61, (byte) 124, (byte) 34, (byte) 192, (byte) 158, (byte) 29, (byte) 67, (byte) 161, (byte) 255, (byte) 70, (byte) 24,
            (byte) 250, (byte) 164, (byte) 39, (byte) 121, (byte) 155, (byte) 197, (byte) 132, (byte) 218, (byte) 56, (byte) 102, (byte) 229, (byte) 187, (byte) 89, (byte) 7, (byte) 219, (byte) 133, (byte) 103, (byte) 57, (byte) 186, (byte) 228, (byte) 6, (byte) 88, (byte) 25, (byte) 71, (byte) 165, (byte) 251, (byte) 120, (byte) 38, (byte) 196, (byte) 154, (byte) 101, (byte) 59, (byte) 217, (byte) 135, (byte) 4, (byte) 90, (byte) 184, (byte) 230, (byte) 167, (byte) 249, (byte) 27, (byte) 69, (byte) 198, (byte) 152, (byte) 122, (byte) 36, (byte) 248, (byte) 166, (byte) 68, (byte) 26, (byte) 153, (byte) 199, (byte) 37, (byte) 123, (byte) 58, (byte) 100, (byte) 134, (byte) 216, (byte) 91, (byte) 5, (byte) 231, (byte) 185, (byte) 140, (byte) 210, (byte) 48, (byte) 110, (byte) 237,
            (byte) 179, (byte) 81, (byte) 15, (byte) 78, (byte) 16, (byte) 242, (byte) 172, (byte) 47, (byte) 113, (byte) 147, (byte) 205, (byte) 17, (byte) 79, (byte) 173, (byte) 243, (byte) 112, (byte) 46, (byte) 204, (byte) 146, (byte) 211, (byte) 141, (byte) 111, (byte) 49, (byte) 178, (byte) 236, (byte) 14, (byte) 80, (byte) 175, (byte) 241, (byte) 19, (byte) 77, (byte) 206, (byte) 144, (byte) 114, (byte) 44, (byte) 109, (byte) 51, (byte) 209, (byte) 143, (byte) 12, (byte) 82, (byte) 176, (byte) 238, (byte) 50, (byte) 108, (byte) 142, (byte) 208, (byte) 83, (byte) 13, (byte) 239, (byte) 177, (byte) 240, (byte) 174, (byte) 76, (byte) 18, (byte) 145, (byte) 207, (byte) 45, (byte) 115, (byte) 202, (byte) 148, (byte) 118, (byte) 40, (byte) 171, (byte) 245, (byte) 23, (byte) 73, (byte) 8,
            (byte) 86, (byte) 180, (byte) 234, (byte) 105, (byte) 55, (byte) 213, (byte) 139, (byte) 87, (byte) 9, (byte) 235, (byte) 181, (byte) 54, (byte) 104, (byte) 138, (byte) 212, (byte) 149, (byte) 203, (byte) 41, (byte) 119, (byte) 244, (byte) 170, (byte) 72, (byte) 22, (byte) 233, (byte) 183, (byte) 85, (byte) 11, (byte) 136, (byte) 214, (byte) 52, (byte) 106, (byte) 43, (byte) 117, (byte) 151, (byte) 201, (byte) 74, (byte) 20, (byte) 246, (byte) 168, (byte) 116, (byte) 42, (byte) 200, (byte) 150, (byte) 21, (byte) 75, (byte) 169, (byte) 247, (byte) 182, (byte) 232, (byte) 10, (byte) 84, (byte) 215, (byte) 137, (byte) 107, 53};

    /**
     * 计算数组的CRC8校验值
     *
     * @param data 需要计算的数组
     * @return CRC8校验值
     */
    public static byte calcCrc8(byte[] data) {
        return calcCrc8(data, 0, data.length, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data   数据
     * @param offset 起始位置
     * @param len    长度
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len) {
        return calcCrc8(data, offset, len, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data   数据
     * @param offset 起始位置
     * @param len    长度
     * @param preval 之前的校验值
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len, byte preval) {
        byte ret = preval;
        for (int i = offset; i < (offset + len); ++i) {
            ret = crc8_tab[(0x00ff & (ret ^ data[i]))];
        }
        return ret;
    }

    // 测试
    public static void main(String[] args) {
        //035400E5
//        byte crc = CommonCodeUtil.calcCrc8(new byte[]{0x03, 0x54, 0x00});
//        System.out.println("" + Integer.toHexString(0x00ff & crc));
//        System.out.println(hexStringToBytes("0100"));
        //36102 8D06
//        byte[] crc16 = new byte[]{(byte) 0x88, (byte) 0x10, (byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x0A, (byte) 0x14, (byte) 0x04, (byte) 0x00, (byte) 0x04, (byte) 0x01, (byte) 0x04, (byte) 0x02, (byte) 0x04, (byte) 0x03, (byte) 0x04, (byte) 0x04, (byte) 0x04, (byte) 0x05, (byte) 0x04, (byte) 0x06, (byte) 0x04, (byte) 0x07, (byte) 0x04, (byte) 0x08, (byte) 0x04, (byte) 0x09};
        byte[] crc16 = new byte[]{(byte) 0x88, (byte) 0x03, (byte) 0x14, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32};
        byte[] crc162 = new byte[]{(byte) 0x88, (byte) 0x10, (byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x0A, (byte) 0x14, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x31, (byte) 0x32};
        getCRC16(crc162);
        byte[] cmdPacket = new byte[6];
        cmdPacket[0] = (byte) 0x88;
        cmdPacket[1] = (byte) 0x03;
        cmdPacket[2] = (byte) 0x00;
        cmdPacket[3] = (byte) 0x1E;
        cmdPacket[4] = (byte) 0x00;
        cmdPacket[5] = (byte) 0x0A;
//        cmdPacket[6] = (byte) 0xBA;
//        cmdPacket[7] = (byte) 0x92;
        getCRC16(cmdPacket);
    }

    /**
     * 取出byte[]数组中的指定部分 包含startIndex 不包含endIndex
     *
     * @param totalData  byte数组
     * @param startIndex 开始位置
     * @param endIndex   结束位置
     * @return
     */
    public static byte[] getSubData(byte[] totalData, int startIndex, int endIndex) {
        if (endIndex <= startIndex) {
            return null;
        }
        if (endIndex > totalData.length) {
            return null;
        }

        byte[] relust = new byte[endIndex - startIndex];
        System.arraycopy(totalData, startIndex, relust, 0, endIndex - startIndex);
        return relust;
    }

    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1, byte[] byte2) {
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }

    /**
     * byte数组转为HexString
     *
     * @param src byte数组
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     * * @param value 要转换的int值
     *
     * @return byte数组
     */
    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    public static String bytesToString(byte[] src, int offset) {
        if (offset <= 0 || offset > src.length - 1) {
            return new String(src);
        }
        byte[] newSrc = new byte[src.length - offset];
        for (int i = offset, j = 0; i < src.length; i++, j++) {
            newSrc[j] = src[i];
        }
        return new String(newSrc);
    }

    public static byte[] hexStringToBytes(String hexString) {
        return hexStringToBytes(hexString, (byte[]) null);
    }

    public static byte[] hexStringToBytes(String hexString, byte[] defaultBytes) {
//		int hexLen = false;
        int hexLen;
        if (hexString != null && (hexLen = hexString.length()) != 0) {
            if (hexLen == 1) {
                hexString = "0" + hexString;
            }

            int len = hexLen / 2;
            byte[] result = new byte[len];
            char[] achar = hexString.toUpperCase().toCharArray();

            try {
                for (int i = 0; i < len; ++i) {
                    int pos = i * 2;
                    result[i] = (byte) (hexCharToByte(achar[pos]) << 4 | hexCharToByte(achar[pos + 1]));
                }

                return result;
            } catch (Exception var8) {
                return defaultBytes;
            }
        } else {
            return defaultBytes;
        }
    }

    public static byte hexCharToByte(char hexChar) {
        return (byte) "0123456789ABCDEF".indexOf(hexChar);
    }

    /**
     * 从帧数据中解析出数值
     *
     * @param frameString
     * @return
     */
    public static Integer parseDataValue(String frameString, boolean signed) {
        String valuePart = frameString;
        if (signed) {
            return new Integer((new BigInteger(valuePart, 16)).shortValue());
        } else {
            return Integer.parseInt(valuePart, 16);
        }
    }

    /**
     * 计算CRC16校验码
     *
     * @param bytes 字节数组
     * @return {@link String} 校验码
     * @since 1.0
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        byte[] result = new byte[2];
        result[0] = (byte) (CRC & 0x00ff);
        result[1] = (byte) (CRC >> 4 & 0xff);

        System.out.println("crc=" + CRC);
        System.out.println("crc1=" + result[0]);
        System.out.println("crc2=" + result[1]);
        return Integer.toHexString(CRC);
    }

    public static byte[] getCRC16(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        byte[] result = new byte[2];
        result[0] = (byte) (CRC & 0x00ff);
        result[1] = (byte) (CRC >> 8 & 0x00ff);
        System.out.println("crc=" + CRC);
//        Log.d(CommandManager.TAG, "crc1=" + Integer.toHexString(result[0] & 0xff));
//        Log.d(CommandManager.TAG, "crc2=" + Integer.toHexString(result[1] & 0xff));
        System.out.println("crc1=" + Integer.toHexString(result[0] & 0xff));
        System.out.println("crc2=" + Integer.toHexString(result[1] & 0xff));
        return result;
    }

}
