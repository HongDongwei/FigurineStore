package com.codvision.figurinestore.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junweiliu on 16/6/1.
 */
public class OrderAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据源
     */
    private List<OrderTableRecieve> commodities=new ArrayList<>();


    /**
     * 构造函数
     *
     * @param context
     * @param datas
     */
    public OrderAdapter(Context context, List<OrderTableRecieve> datas) {
        mContext = context;
        commodities = datas;
    }

    @Override
    public int getCount() {
        return commodities.size();
    }

    @Override
    public Object getItem(int i) {
        return commodities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (null == view) {
            vh = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.order_item, null);
            vh.tvName = (TextView) view.findViewById(R.id.tv_order_name);
            vh.tvNum = (TextView) view.findViewById(R.id.tv_order_num);
            vh.tvType = (TextView) view.findViewById(R.id.tv_order_type);
            vh.tvSate = (TextView) view.findViewById(R.id.tv_order_state);
            vh.mHeadImg = (ImageView) view.findViewById(R.id.iv_sghead);
            vh.tvPrice = (TextView) view.findViewById(R.id.tv_order_price);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        OrderTableRecieve bean = (OrderTableRecieve) getItem(position);
        if (null != bean) {
            vh.tvName.setText(bean.getName());
            vh.tvNum.setText(bean.getAmount() + "");
            vh.tvType.setText(bean.getType() + "");
            vh.tvSate.setText(bean.getState() + "");
            vh.tvPrice.setText(bean.getAmount() * Float.parseFloat(bean.getPrice()) + "");
            int resid = mContext.getResources().getIdentifier(bean.getPic1(), "drawable", mContext.getPackageName());
            vh.mHeadImg.setBackgroundResource(resid);
        }
        return view;
    }


    /**
     * vh
     */
    class ViewHolder {
        /**
         * 姓名
         */
        TextView tvName;
        /**
         * 数量
         */
        TextView tvNum;
        /**
         * 类型
         */
        TextView tvType;
        /**
         * 状态
         */

        TextView tvSate;

        /**
         * 价格
         */

        TextView tvPrice;
        /**
         * 头像
         */
        ImageView mHeadImg;
    }
}