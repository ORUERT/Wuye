package com.zzz.wuye.enterPage.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.R;

import java.util.List;

/**
 * Created by oruret on 2018/3/14.
 */

public class tableAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private List<Pair> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public tableAdapter(List<Pair> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
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
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
//        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
//            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waithandle, null));
//        } else {
            //对于Body中的item，我们也返回所对应的ViewHolder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_eventtable, null);
        view.setOnClickListener(this);
        return new BodyViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (isHeader(position)) {
//            //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改
//            ((HeaderViewHolder)viewHolder).getTextView().setText("This is the Header!!");
//        }else {
//            //其他条目中的逻辑在此
        Pair par = dataList.get(position);
//        Log.i("hello",par);
        ((BodyViewHolder) viewHolder).getTextView2().setText((String)par.first);
        ((BodyViewHolder) viewHolder).getImageView().setImageResource((int)par.second);
        ((BodyViewHolder) viewHolder).itemView.setTag(position);
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
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
//    @Override
//    public int getItemViewType(int position) {
//        //如果是0，就是头，否则则是其他的item
//        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
//    }

    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
//    public class HeaderViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.item_tv);
//        }
//        public TextView getTextView() {
//            return textView;
//        }
//    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private ImageView imageView;
        public BodyViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.table_text);
            imageView = (ImageView) itemView.findViewById(R.id.table_image);
        }
        public TextView getTextView2(){
            return titleView;
        }
        public ImageView getImageView(){
            return imageView;
        }
    }
}
