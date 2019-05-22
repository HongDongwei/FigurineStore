package com.codvision.figurinestore.heartbeat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.codvision.figurinestore.App;
import com.codvision.figurinestore.filter.ByteCodecFactory;
import com.codvision.figurinestore.utils.ConnectUtils;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;


public class HeartBeatService extends Service {

    private static final String TAG = "HeartBeatService";
    private NioSocketConnector connector = null;
    private HeartBeatHandler handler = new HeartBeatHandler();//创建handler对象，用于业务逻辑处理
    private HeartBeatThread thread = new HeartBeatThread();
    private IoSession session = null;
    private SocketBinder sockerBinder = new SocketBinder();
    private ConnectFuture future = null;

    public void setMessageCallback(IMessageCallback messageCallback) {
        handler.setCallback(messageCallback);
    }

    @Override
    public void onLowMemory() {
                super.onLowMemory();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind: ");
        if (!thread.isInterrupted()) {
            thread.start();
        }
        return sockerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    public class SocketBinder extends Binder {

        /*返回SocketService 在需要的地方可以通过ServiceConnection获取到SocketService  */
        public HeartBeatService getService() {
            return HeartBeatService.this;
        }
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        //开启单独的线程，因为Service是位于主线程的，为了避免主线程被阻塞
        if (!thread.isInterrupted()) {
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    class HeartBeatThread extends Thread {
        @Override
        public void run() {
            initClientMina(ConnectUtils.HOST, ConnectUtils.PORT);
        }
    }


    /**
     * 初始化客户端MINA
     *
     * @param host
     * @param port
     */
    public void initClientMina(String host, int port) {

        try {
            connector = new NioSocketConnector();
            connector.setHandler(handler);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteCodecFactory()));//添加Filter对象
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println(e2.toString());
        }
        connector.setConnectTimeout(ConnectUtils.TIMEOUT);//设置连接超时时间
        int count = 0;//记录连接次数
        while (true) {
            try {
                count++;
                //执行到这里表示客户端刚刚启动需要连接服务器,第一次连接服务器的话是没有尝试次数限制的，但是随后的断线重连就有次数限制了
                future = connector.connect(new InetSocketAddress(host, port));
                future.awaitUninterruptibly();//一直阻塞,直到连接建立

                session = future.getSession();//获取Session对象
                if (session.isConnected()) {
                    App.session=session;
                    String s = "你好";
                    byte[] b = s.getBytes("gbk");
                    session.write(IoBuffer.wrap(b));
                    break;
                }
            } catch (RuntimeIoException e) {
                System.out.println(ConnectUtils.stringNowTime() + " : 第" + count + "次客户端连接服务器失败，因为" + ConnectUtils.TIMEOUT + "s没有连接成功");
                try {
                    Thread.sleep(2000);//如果本次连接服务器失败，则间隔2s后进行重连操作
                    System.out.println(ConnectUtils.stringNowTime() + " : 开始第" + (count + 1) + "次连接服务器");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //为MINA客户端添加监听器，当Session会话关闭的时候，进行自动重连
        connector.addListener(new HeartBeatListener(connector));
    }


}
