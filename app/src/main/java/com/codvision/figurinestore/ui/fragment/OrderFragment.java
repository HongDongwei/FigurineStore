package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.module.bean.OrderTable;
import com.codvision.figurinestore.module.bean.OrderTableDelete;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;
import com.codvision.figurinestore.module.bean.OrderTableSelect;
import com.codvision.figurinestore.presenter.OrderTableDeletePresenter;
import com.codvision.figurinestore.presenter.OrderTableSelectPresenter;
import com.codvision.figurinestore.presenter.contract.OrderTableDeleteContract;
import com.codvision.figurinestore.presenter.contract.OrderTableSelectContract;
import com.codvision.figurinestore.ui.activity.BuyDetailActivity;
import com.codvision.figurinestore.ui.activity.OrderActivity;
import com.codvision.figurinestore.ui.adapter.OrderAdapter;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment implements OrderTableSelectContract.View, OrderTableDeleteContract.View {

    private View view;
    private OrderTableSelectPresenter orderTableSelectPresenter;
    private OrderTableDeletePresenter orderTableDeletePresenter;
    private OrderAdapter orderAdapter;
    private ListView lvOrder;
    private List<OrderTableRecieve> orderTableRecieveList = new ArrayList<>();
    private TextView tvOn;
    private TextView tvOff;
    private int chocie = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        initView();
        initEvent();
        return view;
    }

    private void initView() {
        lvOrder = view.findViewById(R.id.lv_order);
        tvOn = view.findViewById(R.id.tv_order_state_on);
        tvOff = view.findViewById(R.id.tv_order_state_off);
        orderAdapter = new OrderAdapter(getActivity(), orderTableRecieveList);
        lvOrder.setAdapter(orderAdapter);
        orderTableSelectPresenter = new OrderTableSelectPresenter(this, getActivity());
        orderTableDeletePresenter = new OrderTableDeletePresenter(this, getActivity());
        orderTableSelectPresenter.orderSelect(new OrderTableSelect(SharedPreferenceUtils.getUserId(getActivity())));
        setClick(1, tvOn);
    }

    private void initEvent() {
        tvOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClick(1, tvOn);
                orderTableSelectPresenter.orderSelect(new OrderTableSelect(SharedPreferenceUtils.getUserId(getActivity())));
            }
        });
        tvOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClick(2, tvOff);
                orderTableSelectPresenter.orderSelect(new OrderTableSelect(SharedPreferenceUtils.getUserId(getActivity())));
            }
        });
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               if (chocie == 1) {
                                                   Intent intent = new Intent(getActivity(), OrderActivity.class);
                                                   intent.putExtra("goodId", orderTableRecieveList.get(position).getId());
                                                   Constant.orderNum = orderTableRecieveList.get(position).getAmount();
                                                   startActivity(intent);
                                               }
                                           }
                                       }
        );
        lvOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                orderTableDeletePresenter.orderDelete(new OrderTableDelete(orderTableRecieveList.get(position).getId()));

                return false;
            }
        });
    }

    private void setClick(int chocie, TextView textView) {
        this.chocie = chocie;
        tvOn.setSelected(false);
        tvOff.setSelected(false);
        textView.setSelected(true);
    }

    @Override
    public void orderSelectSuccess(ArrayList<OrderTableRecieve> orderTableRecieveArrayList) {
        orderTableRecieveList.clear();
        for (int i = 0; i < orderTableRecieveArrayList.size(); i++) {
            if (chocie == 1 && "未支付".equals(orderTableRecieveArrayList.get(i).getState())) {
                this.orderTableRecieveList.add(orderTableRecieveArrayList.get(i));
            } else if (chocie == 2 && "已完成".equals(orderTableRecieveArrayList.get(i).getState())) {
                this.orderTableRecieveList.add(orderTableRecieveArrayList.get(i));
            }
        }
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void orderSelectFail(String code, String message) {

    }

    @Override
    public void orderDeleteSuccess() {

    }

    @Override
    public void orderDeleteFail(String code, String message) {

    }
}
