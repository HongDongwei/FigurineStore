package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codvision.figurinestore.App;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.module.bean.Msg;
import com.codvision.figurinestore.ui.adapter.MsgAdapter;

import org.apache.mina.core.buffer.IoBuffer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MessageFragment extends Fragment implements Handler.Callback {

    public static final String TAG = "MessageFragment";
    private View view;
    private EditText inpuText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private ArrayList<Msg> msgArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        initVeiw();
        return view;
    }

    private void initVeiw() {
        inpuText = (EditText) view.findViewById(R.id.input_text);
        send = (Button) view.findViewById(R.id.send);
        msgRecyclerView = view.findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(getActivity(), msgArrayList);
        msgRecyclerView.setAdapter(adapter);
        msgArrayList.clear();
        for (int i = 0; i < App.msgArrayList.size(); i++) {
            msgArrayList.add(App.msgArrayList.get(i));
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inpuText.getText().toString();
                if (!"".equals(content)) {
                    byte[] b = new byte[0];
                    try {
                        b = content.getBytes("gbk");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    App.session.write(IoBuffer.wrap(b));
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgArrayList.add(msg);
                    App.msgArrayList.add(msg);
                    adapter.notifyItemChanged(msgArrayList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgArrayList.size() - 1);
                    inpuText.setText("");
                }
            }
        });
    }


    @Override
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            Msg msg = new Msg(message.obj.toString(), Msg.TYPE_RECEIVED);
            msgArrayList.add(msg);
            App.msgArrayList.add(msg);
            adapter.notifyItemChanged(msgArrayList.size() - 1);
            msgRecyclerView.scrollToPosition(msgArrayList.size() - 1);
            Log.i(TAG, "handleMessage: " + message.obj.toString());
        }
        return true;
    }

}
