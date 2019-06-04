package com.tao.demo.minaDemo.mina;

import android.content.Context;
import android.util.Log;

import com.tao.demo.minaDemo.CommandManager;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class MinaTcpClient extends IoHandlerAdapter {
    public static final String SERVERIP = "192.168.4.1"; //your computer IP address
    public static final int SERVERPORT = 2880;
    public static final String tag = "MinaTcpClient";
    Context mContext;
    public IoConnector connector = null;
    public boolean isConnect = false;
    public IoSession session;
    // For TDD test
//	private static String HOST = "192.168.0.131";
//	private static int PORT = 6666;
    // Real AP IP
    //private static String HOST = "192.168.1.10";
    //private static int PORT = 6666;


    public interface Callback {
        void connectedOK();

        void connectedFail();

        void disconnected();

        void dataReceived(byte[] data, int len);

        void clientException(String eMessage);

        void sentDataComplete();

        void setMod();

        void setTime();
    }

    static private Callback callback;


    public MinaTcpClient(Context context) {
        callback = callback;
        connector = new NioSocketConnector();
        mContext = context;
        // 创建一个接收数据过滤器
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(new CommandCodecFactory());
        chain.addLast("WSNCommand", filter);

        connector.setHandler(this);
        connector.setConnectTimeoutMillis(3000); // 連線timeout時間，connFuture
        // timeout 預設1分鐘
        connector.setDefaultRemoteAddress(new InetSocketAddress(SERVERIP, SERVERPORT));
        // startConnection();
    }

    public void startConnection() {
        ConnectFuture connFuture = connector.connect();
        connFuture.addListener(new ConnectListener());
    }

    public void setCurrentTime() {
        callback.setTime();
    }

    private class ConnectListener implements IoFutureListener<ConnectFuture> {

        public void operationComplete(ConnectFuture future) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (future.isConnected()) {
                // get session
                session = future.getSession();
                Log.d(tag, "TCP TCP 客户端启动");
                isConnect = true;
                callback.connectedOK();

            } else {
                Log.d(tag, "TCP 客户端启动失敗");
                isConnect = false;
                callback.connectedFail();
            }
        }
    }


    @Override
    public void messageReceived(IoSession iosession, Object message) throws Exception {
        Log.d(tag, "客户端收到消息");
        Command c = (Command) message;
        c.printHex();

        Log.i("bs_len", c.getDataLen() + "");
        callback.dataReceived(c.getData(), c.getDataLen());

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Log.d(tag, "客户端异常");
        cause.printStackTrace();
        callback.clientException(cause.getMessage());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageSent(IoSession iosession, Object obj) throws Exception {
        Log.d(tag, "客户端消息发送");
        callback.sentDataComplete();
        super.messageSent(iosession, obj);
    }

    @Override
    public void sessionClosed(IoSession iosession) throws Exception {
        Log.d(tag, "客户端会话关闭");
        CommandManager.isConnect = false;
        callback.disconnected();
        super.sessionClosed(iosession);
    }

    @Override
    public void sessionCreated(IoSession iosession) throws Exception {
        Log.d(tag, "客户端会话创建");
        //System.out.println("客户端会话创建");
        super.sessionCreated(iosession);
    }

    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus) throws Exception {
        //System.out.println("客户端会话休眠");
        Log.d(tag, "客户端会话休眠");
        super.sessionIdle(iosession, idlestatus);
    }

    @Override
    public void sessionOpened(IoSession iosession) throws Exception {
        //System.out.println("客户端会话打开");
        Log.d(tag, "客户端会话打开");
        super.sessionOpened(iosession);
    }

    /*public boolean getConnectStatus(){
        return isConnect;
    }
    public void disconnect() {
        if (session != null) {
            // 等待连接断开
            session.close(true).awaitUninterruptibly();
            session = null;
            connector.dispose(true);
        }
    }*/
    static public void setCallback(Callback callback_) {
        Log.i(tag, "setCallback");
        callback = callback_;
        callback.setMod();
    }

    public void timeout() {
        Log.i(tag, "setCallback");
        callback.disconnected();
    }
}

