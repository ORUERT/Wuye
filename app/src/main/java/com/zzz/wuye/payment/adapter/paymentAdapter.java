package com.zzz.wuye.payment.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.enterPage.adapter.table2Adapter;
import com.zzz.wuye.news.model._Article;
import com.zzz.wuye.payment.model.bill;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by oruret on 2018/3/14.
 */

public class paymentAdapter extends RecyclerView.Adapter {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private List<bill> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public paymentAdapter(List<bill> dataList, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_payment, null);
        return new BodyViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        bill par = dataList.get(position);
        Log.i("hello",par.getName()+par.getLocate());

        ((BodyViewHolder) viewHolder).getNameView().setText(par.getName());
        ((BodyViewHolder) viewHolder).getLocateView().setText(par.getLocate());
        ((BodyViewHolder) viewHolder).getAccountView().setText(par.getAccount());
        ((BodyViewHolder) viewHolder).mOnRecyclerviewItemClickListener1 = mOnRecyclerviewItemClickListener;

        if(par.isFlag()){
            ((BodyViewHolder) viewHolder).getFlagView().setText("已支付");
//            ((BodyViewHolder) viewHolder).getCancel().setVisibility(View.VISIBLE);
            ((BodyViewHolder) viewHolder).getPay().setVisibility(View.VISIBLE);
        }else {
            ((BodyViewHolder) viewHolder).getFlagView().setText("未支付");
        }
        ((BodyViewHolder) viewHolder).getNumView().setText(String.valueOf(par.getNumber())+"(欠缴费用+延长半年)");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ((BodyViewHolder) viewHolder).getDateView().setText(sdf.format(par.getDate()));

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
    public class BodyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener1;
        private TextView nameView;
        private TextView accountView;
        private TextView locateView;
        private TextView flagView;
        private TextView numView;
        private TextView dateView;
        private Button cancel;
        private Button pay;

        public BodyViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.payment_name);
            accountView = itemView.findViewById(R.id.payment_id);
            locateView = itemView.findViewById(R.id.payment_locade);
            flagView = itemView.findViewById(R.id.payment_check);
            numView = itemView.findViewById(R.id.payment_number);
            dateView = itemView.findViewById(R.id.payment_date);
            cancel = itemView.findViewById(R.id.payment_button1);
            pay = itemView.findViewById(R.id.payment_button2);
            pay.setOnClickListener(this);
        }

        public Button getCancel() {
            return cancel;
        }

        public Button getPay() {
            return pay;
        }
        public TextView getNameView() {
            return nameView;
        }

        public TextView getAccountView() {
            return accountView;
        }

        public TextView getLocateView() {
            return locateView;
        }

        public TextView getFlagView() {
            return flagView;
        }

        public TextView getNumView() {
            return numView;
        }

        public TextView getDateView() {
            return dateView;
        }

        @Override
        public void onClick(View v) {
                Log.i("lick","click");
                mOnRecyclerviewItemClickListener1.onItemClickListener(v,getAdapterPosition());
        }
    }
}
