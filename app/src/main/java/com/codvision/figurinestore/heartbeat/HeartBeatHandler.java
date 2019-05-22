package com.codvision.figurinestore.heartbeat;

import com.codvision.figurinestore.App;
import com.codvision.figurinestore.utils.ConnectUtils;
import com.codvision.figurinestore.utils.HexUtils;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.URLDecoder;


public class HeartBeatHandler extends IoHandlerAdapter {
    private IMessageCallback messageCallback;

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        System.out.println(ConnectUtils.stringNowTime() + " : Use:exceptionCaught" + cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String result = URLDecoder.decode(message.toString(), "UTF-8");
        byte[] data = HexUtils.hexStringToBytes(result);
        String str = new String(data, "gbk");
        App.MESSAGE = str;

        if (null != messageCallback) {
            messageCallback.onReceived(str);
        }

        System.out.println(ConnectUtils.stringNowTime() + " : Use:messageReceived" + str);
        System.out.println(ConnectUtils.stringNowTime() + " : Use:messageReceived" + result);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        String result = URLDecoder.decode(message.toString(), "UTF-8");
        System.out.println(ConnectUtils.stringNowTime() + " : Use:messageSent" + result);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        //System.out.println(ConnectUtils.stringNowTime()+" : 客户端调用sessionClosed");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println(ConnectUtils.stringNowTime() + " : Use:sessionCreated");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        System.out.println(ConnectUtils.stringNowTime() + " : Use:sessionIdle");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println(ConnectUtils.stringNowTime() + " : Use:sessionOpened");
    }

    public void setCallback(IMessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

}
