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
import com.codvision.figurinestore.module.bean.SanGuoBean;

import java.util.List;

/**
 * Created by junweiliu on 16/6/1.
 */
public class SGAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据源
     */
    private List<Commodity> commodities;


    /**
     * 构造函数
     *
     * @param context
     * @param datas
     */
    public SGAdapter(Context context, List<Commodity> datas) {
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
            view = mInflater.inflate(R.layout.search_item, null);
            vh.tvComName = (TextView) view.findViewById(R.id.tv_com_name);
            vh.tvComPrice = (TextView) view.findViewById(R.id.tv_com_price);
            vh.tvComChoice = (TextView) view.findViewById(R.id.tv_com_choice);
            vh.mHeadImg = (ImageView) view.findViewById(R.id.iv_sghead);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        Commodity bean = (Commodity) getItem(position);
        if (null != bean) {
            vh.tvComName.setText(bean.getName());
            vh.tvComChoice.setText(bean.getChoice() + "");
            vh.tvComPrice.setText(bean.getPrice() + "");
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
        TextView tvComName;
        /**
         * 热度
         */
        TextView tvComChoice;
        /**
         * 价格
         */
        TextView tvComPrice;
        /**
         * 头像
         */
        ImageView mHeadImg;
    }
}