package com.zzz.wuye.enterPage.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.news.model.Newsitem;
import com.zzz.wuye.news.model._Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by oruret on 2018/3/24.
 */

public class table2Adapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<_Article> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public table2Adapter(List<_Article> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, null);
        view.setOnClickListener(this);
        return new BodyViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        _Article par = dataList.get(position);
//        Log.i("hello",par.getTitle()+par.getContent());
        ((BodyViewHolder) viewHolder).getTitleView().setText(par.getTitle());
        ((BodyViewHolder) viewHolder).getContentView().setText(par.getTitle());
        ((BodyViewHolder) viewHolder).getImageView().setImageResource(R.drawable.ic_fiber_new_black_24dp);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(par.getCreatedAt());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            ((BodyViewHolder) viewHolder).getDateView().setText(sdf.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((BodyViewHolder) viewHolder).itemView.setId(position);

//        }
    }

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener.onItemClickListener(v,(int)v.getId());
    }


    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView contentView;
        private TextView dateView;
        private ImageView imageView;
        public TextView getTitleView() {
            return titleView;
        }

        public TextView getContentView() {
            return contentView;
        }

        public TextView getDateView() {
            return dateView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public BodyViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.item_messagename);
            contentView = (TextView) itemView.findViewById(R.id.item_messagecontent);
            dateView = (TextView) itemView.findViewById(R.id.item_messagedate);
            imageView = (ImageView) itemView.findViewById(R.id.item_userimage);
        }

    }
}
