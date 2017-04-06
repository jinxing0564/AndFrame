package com.vince.andframe.demo.recycler.drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.andframe.base.ui.activity.BaseBizActivity;
import com.vince.andframe.base.ui.drawer.DrawerRecyclerView;
import com.vince.andframe.base.ui.drawer.DrawerItemView;
import com.vince.andframe.base.ui.prompt.ToastUtils;

import java.util.ArrayList;

/**
 * Created by tianweixin on 2017-3-29.
 */

public class DrawerViewActivity extends BaseBizActivity {
    DrawerRecyclerView recyclerView;
    DrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_drawer);
        setTitle("测试RecyclerView抽屉");
        recyclerView = (DrawerRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DrawerAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast("click item = " + position, Toast.LENGTH_SHORT);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DrawerItemDecorator());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class DrawerAdapter extends RecyclerView.Adapter<DrawerHolder> {

        private ArrayList<String> arrayList;

        private OnItemClickListener listener;

        public DrawerAdapter() {
            arrayList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                arrayList.add("ITEMITEMITEMITEMITEM:" + i);
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DrawerHolder holder;
            DrawerItemView drawerItemView = new DrawerItemView(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            drawerItemView.setLayoutParams(params);

            holder = new DrawerHolder(drawerItemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(DrawerHolder holder, final int position) {
            holder.drawerItemView.reset();
            holder.textView.setText(arrayList.get(position));
            holder.drawerItemView.setContentClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public void removeItem(int position) {
            arrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, arrayList.size());
        }
    }

    class DrawerHolder extends RecyclerView.ViewHolder {
        DrawerItemView drawerItemView;
        TextView textView;
        TextView deltView;

        public DrawerHolder(View itemView) {
            super(itemView);
            drawerItemView = (DrawerItemView) itemView;
            textView = new TextView(itemView.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setPadding(20, 80, 20, 80);
            drawerItemView.setContentView(textView, R.color.white);

            deltView = new TextView(itemView.getContext());

            deltView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            deltView.setTextColor(Color.WHITE);
            deltView.setText("删除");
            deltView.setGravity(Gravity.CENTER);
            drawerItemView.setDrawerView(deltView);
            deltView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = drawerItemView.getPosition();
                    adapter.removeItem(pos);
                }
            });

        }
    }

    class DrawerItemDecorator extends RecyclerView.ItemDecoration {
        private int lineHeight = 20;
        Paint paint;

        public DrawerItemDecorator() {
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.color_efefef));
            paint.setAntiAlias(true);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getPaddingRight() + parent.getWidth();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                int top = child.getTop() - lineHeight;
                int bottom = child.getTop();
                c.drawRect(left, top, right, bottom, paint);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = lineHeight;
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
