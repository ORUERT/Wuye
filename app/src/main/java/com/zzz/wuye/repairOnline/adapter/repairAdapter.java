package com.zzz.wuye.repairOnline.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.repairOnline.model.repairCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by oruret on 2018/4/2.
 */

public class repairAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<repairCard> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public repairAdapter(List<repairCard> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_biglist, null);
        view.setOnClickListener(this);
        return new BodyViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        repairCard par = dataList.get(position);
//        Log.i("hello",par.getName()+par.getLocate());

        ((BodyViewHolder) viewHolder).getId().setText(par.getObjectId());
        ((BodyViewHolder) viewHolder).getTitle().setText(par.getProjecttitle());
        if(par.isCheck()){
            ((BodyViewHolder) viewHolder).getCheck().setText("已解决");
        }else {
            ((BodyViewHolder) viewHolder).getCheck() .setText("未解决");
        }

        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = sdf1.parse(par.getCreatedAt());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            ((BodyViewHolder) viewHolder).getDate().setText(sdf2.format(dt1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView title;
        private TextView date;
        private TextView check;

        public BodyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.repir_id);
            title = itemView.findViewById(R.id.repir_title);
            date = itemView.findViewById(R.id.repir_date);
            check = itemView.findViewById(R.id.repir_check);
        }

        public TextView getId() {
            return id;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getCheck() {
            return check;
        }
    }
}
