package com.tao.demo.minaDemo.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shiun
 */
public class CommandEncoder implements ProtocolEncoder {
    private static int sn;

    @Override
    public void encode(IoSession is, Object o, ProtocolEncoderOutput peo) throws Exception {
        Command command = (Command)o;
        int len = command.getDataLen()&0xFF;
        
        IoBuffer buffer = IoBuffer.allocate(len + 5, false);
        buffer.put((byte)'#');
        buffer.put(command.getSn());
        buffer.put(command.getDataLen());
        buffer.put(command.getData());
        buffer.put((byte)'$');
        buffer.put(command.getChecksum());
        
        buffer.flip();
        peo.write(buffer);
    }

    @Override
    public void dispose(IoSession is) throws Exception {
    }
    
}
