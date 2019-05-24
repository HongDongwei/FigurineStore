package com.codvision.figurinestore.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codvision.figurinestore.App;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.module.bean.Msg;
import com.codvision.figurinestore.utils.CircleTransform;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private final Context context;
    private List<Msg> mmsglist;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftlayout;
        LinearLayout rightlayout;
        TextView leftmsg;
        TextView rightmsg;
        ImageView ivLeft;
        ImageView ivRight;

        public ViewHolder(View itemView) {
            super(itemView);
            leftlayout = itemView.findViewById(R.id.left_layout);
            rightlayout = itemView.findViewById(R.id.right_layout);
            leftmsg = itemView.findViewById(R.id.left_msg);
            rightmsg = itemView.findViewById(R.id.right_msg);
            ivLeft = itemView.findViewById(R.id.iv_user_head_left);
            ivRight = itemView.findViewById(R.id.iv_user_head_right);
        }
    }

    public MsgAdapter(Context context, List<Msg> msglist) {
        this.context = context;
        mmsglist = msglist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int i) {
        Msg msg = mmsglist.get(i);
        if (msg.getType() == Msg.TYPE_RECEIVED) {
            viewholder.leftlayout.setVisibility(View.VISIBLE);
            viewholder.ivLeft.setVisibility(View.VISIBLE);
            viewholder.ivRight.setVisibility(View.GONE);
            Picasso.with(context).load(R.drawable.head2).error(R.drawable.head2).transform(new CircleTransform()).into( viewholder.ivLeft);
            viewholder.rightlayout.setVisibility(View.GONE);
            viewholder.leftmsg.setText(msg.getContent());
        } else if (msg.getType() == Msg.TYPE_SENT) {
            viewholder.leftlayout.setVisibility(View.GONE);
            viewholder.rightlayout.setVisibility(View.VISIBLE);
            viewholder.ivLeft.setVisibility(View.GONE);
            viewholder.ivRight.setVisibility(View.VISIBLE);
            Picasso.with(context).load(R.drawable.head1).error(R.drawable.head1).transform(new CircleTransform()).into( viewholder.ivRight);
            viewholder.rightmsg.setText(msg.getContent());
        }
    }

    private void changeHeadPic(Object src, ImageView imageView) {
        //设置圆形图像
        Glide.with(context).load(src)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mmsglist.size();
    }
}
