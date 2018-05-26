package com.zzz.wuye.enterPage.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.model.PhoneNum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by oruret on 2018/4/1.
 */

public class phoneNumberAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<PhoneNum> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public phoneNumberAdapter(List<PhoneNum> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_phonenumber, null);
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
        PhoneNum par = dataList.get(position);
        Log.i("hello", String.valueOf(par.getPhoneNum()));
        ((BodyViewHolder) viewHolder).getNameView().setText(par.getUserName());
        ((BodyViewHolder) viewHolder).getPhoneView().setText(String.valueOf(par.getPhoneNum()));

        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = sdf1.parse(par.getCreatedAt());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            ((BodyViewHolder) viewHolder).getDateView().setText(sdf2.format(dt1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((BodyViewHolder) viewHolder).itemView.setTag(position);

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
        private TextView phoneView;
        private TextView dateView;
        private TextView nameView;
        public BodyViewHolder(View itemView) {
            super(itemView);
            phoneView = (TextView) itemView.findViewById(R.id.phoneNum_text);
            dateView = (TextView) itemView.findViewById(R.id.createDate);
            nameView = (TextView) itemView.findViewById(R.id.phoneName_text);
        }

        public TextView getPhoneView() {
            return phoneView;
        }

        public TextView getDateView() {
            return dateView;
        }

        public TextView getNameView() {
            return nameView;
        }
    }
}
