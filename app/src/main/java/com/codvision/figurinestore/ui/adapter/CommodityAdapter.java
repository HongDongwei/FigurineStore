package com.codvision.figurinestore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.SanGuoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxy on 2019/5/21 14:54
 * todo
 */
public class CommodityAdapter extends BaseAdapter {
    private Context mContext;

    private List<Commodity> commodities ;

    public CommodityAdapter(Context context,List<Commodity> data) {
        mContext = context;
        commodities = data;
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
        return commodities.get(i).getId();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            vh.img = (ImageView) convertView.findViewById(R.id.img);
            vh.text = (TextView) convertView.findViewById(R.id.text);
            vh.price = (TextView) convertView.findViewById(R.id.price);
            vh.click = (TextView) convertView.findViewById(R.id.click);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        Commodity commodity = commodities.get(position);
        if (null != commodity) {
            int resid=mContext.getResources().getIdentifier(commodity.getPic1(),"drawable", mContext.getPackageName());
            vh.img.setBackgroundResource(resid);
            vh.text.setText(commodity.getName());
            vh.price.setText(commodity.getPrice()+"");
            vh.click.setText(commodity.getChoice()+"");
        }

        return convertView;
    }


    private class ViewHolder {
        ImageView img;
        TextView text;
        TextView price;
        TextView click;
    }

}
