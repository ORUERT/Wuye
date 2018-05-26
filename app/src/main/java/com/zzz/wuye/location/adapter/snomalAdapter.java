package com.zzz.wuye.location.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * Created by oruret on 2018/4/15.
 */

public class snomalAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<String> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public snomalAdapter(List<String> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.dataList = dataList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    /**
     * 判断当前position是否处于第一个
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_smalllist, null);
        view.setOnClickListener(this);
        return new BodyViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String par = dataList.get(position);
//        Log.i("hello", String.valueOf(par.getPhoneNum()));
        ((BodyViewHolder) viewHolder).getTitleView().setText(par);
        ((BodyViewHolder) viewHolder).itemView.setTag(position);
//        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        public BodyViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.sitem_title);
        }

        public TextView getTitleView() {
            return titleView;
        }
    }
}
