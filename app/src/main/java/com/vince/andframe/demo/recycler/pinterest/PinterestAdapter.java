package com.vince.andframe.demo.recycler.pinterest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vince.aframe.R;
import com.vince.andframe.base.tools.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by tianweixin on 2017-4-1.
 */

public class PinterestAdapter extends RecyclerView.Adapter<PinterestAdapter.PinterestHolder> {
    private ArrayList<Image> res = new ArrayList<>();

    private int width;

    public PinterestAdapter(Context context) {
        res.clear();
        int width = ScreenUtil.getScreenWidth(context) / 3;
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.color_999999, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimary, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorAccent, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.colorPrimaryDark, (int) (width / 2 + Math.random() * 300)));
        res.add(new Image(R.color.top_title_color, (int) (width / 2 + Math.random() * 300)));

    }

    @Override
    public PinterestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(parent.getContext());

        PinterestHolder holder = new PinterestHolder(iv);
        if (width <= 0) {
            width = parent.getMeasuredWidth() / 3;
        }

        return holder;
    }

    @Override
    public int getItemCount() {
        return res.size();
    }

    @Override
    public void onBindViewHolder(PinterestHolder holder, int position) {
        holder.image.setImageResource(res.get(position).id);
        ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, res.get(position).height);
        }
        lp.width = width;
        lp.height = res.get(position).height;
        holder.image.setLayoutParams(lp);
    }

    class PinterestHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public PinterestHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView;
            int padding = ScreenUtil.dipToPixel(5);
            image.setPadding(padding, padding, padding, padding);

        }
    }

    class Image {
        int id;
        int height;

        public Image(int id, int height) {
            this.id = id;
            this.height = height;
        }
    }
}
