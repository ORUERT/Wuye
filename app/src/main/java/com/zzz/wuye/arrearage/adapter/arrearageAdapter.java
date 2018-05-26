package com.zzz.wuye.arrearage.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.arrearage.model.Arrearage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by oruret on 2018/3/15.
 */

public class arrearageAdapter extends RecyclerView.Adapter{
        private static final int ITEM_VIEW_TYPE_HEADER = 0;
        private static final int ITEM_VIEW_TYPE_ITEM = 1;
        //数据源
        private List<Arrearage> dataList;

        //构造函数
        public arrearageAdapter(List<Arrearage> dataList) {
            this.dataList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
//        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
//            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waithandle, null));
//        } else {
            //对于Body中的item，我们也返回所对应的ViewHolder
            return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_biglist, null));
//        }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (isHeader(position)) {
//            //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改
//            ((HeaderViewHolder)viewHolder).getTextView().setText("This is the Header!!");
//        }else {
//            //其他条目中的逻辑在此
            Arrearage arr = dataList.get(position);
            ((BodyViewHolder)viewHolder).getContentText().setText(arr.getCheckCompany());
            ((BodyViewHolder)viewHolder).getTitleText().setText(arr.getCheckType());
            ((BodyViewHolder)viewHolder).getIdText().setText(String.valueOf(arr.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            ((BodyViewHolder)viewHolder).getDateText().setText(sdf.format(arr.getDate()));
            Log.i("hello",arr.getCheckCompany()+arr.getCheckType()+arr.getId());
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

        /**
         * 给GridView中的条目用的ViewHolder，里面只有一个TextView
         */
        public class BodyViewHolder extends RecyclerView.ViewHolder {
            public TextView getTitleText() {
                return titleText;
            }

            public TextView getIdText() {
                return idText;
            }

            public TextView getContentText() {
                return contentText;
            }

            public TextView getDateText() {
                return dateText;
            }

            private TextView titleText;
            private TextView idText;
            private TextView contentText;
            private TextView dateText;
            public BodyViewHolder(View itemView) {
                super(itemView);
                titleText = (TextView) itemView.findViewById(R.id.payment_name);
//                idText = (TextView) itemView.findViewById(R.id.check_id);
//                contentText = (TextView) itemView.findViewById(R.id.check_content);
//                dateText = (TextView) itemView.findViewById(R.id.check_date);
            }

        }

}
