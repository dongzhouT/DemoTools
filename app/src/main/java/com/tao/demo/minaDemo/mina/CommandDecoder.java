package com.tao.demo.minaDemo.mina;

import android.util.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shiun
 */
public class CommandDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession is, IoBuffer ib, ProtocolDecoderOutput pdo) throws Exception {
        if(ib.remaining() < 3) {
            return false;
        }
        Log.i("doDecode","");


        Log.i("doDecode begin","ib.remaining()"+ib.remaining());
        ib.mark();		//標記開始點
        Log.i("doDecode begin","ib.remaining()"+ib.remaining());
        byte start = ib.get();
        Log.i("doDecode start", Integer.toHexString(start));
        byte sn = ib.get();
        Log.i("doDecode sn", Integer.toHexString(sn));
        byte dataLen = ib.get();
        Log.i("doDecode len", Integer.toHexString(dataLen));
        int dateLength = dataLen&0xFF;
        Log.i("doDecode dateLength",""+dateLength);


        Log.i("doDecode dateLength","ib.remaining()"+ib.remaining());

        int i=0;
        int size  = ib.remaining();
        byte[] data = new byte[size-2];
        for(i=0;i<size-2;i++)
        {
            data[i] = ib.get();
            Log.i("doDecode data"+i, Integer.toHexString(data[i]));
        }
        Log.i("doDecode data",ByteAndStr16.Bytes2HexString(data));
        if(data[0]!=0x14 && size < dateLength ) {  // data + checksum + '$'
            Log.i("doDecode dateLength","資料不完全"+dateLength);
        	ib.reset();    //若資料不完全，reset資料還原至mark處
            return false;
        }

        byte end = ib.get();
        Log.i("doDecode end", Integer.toHexString(end));
        byte checksum = ib.get();
        Log.i("doDecode CS", Integer.toHexString(checksum));


        
       Command cmd = new Command(dataLen, data, checksum,true);
        pdo.write(cmd);
        return true;
    }
    
}
