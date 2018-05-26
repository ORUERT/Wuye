package com.zzz.wuye.Message.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.Message.model.msg;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * Created by oruret on 2018/4/19.
 */

public class messageAdapter extends RecyclerView.Adapter implements View.OnClickListener {
//数据源
private List<msg> dataList;

//构造函数
        public messageAdapter(List<msg> dataList ) {
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, null);
            view.setOnClickListener(this);
            return new BodyViewHolder(view);
    //        }
            }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (isHeader(position)) {
//            //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改木
//            ((HeaderViewHolder)viewHolder).getTextView().setText("This is the Header!!");
//        }else {
//            //其他条目中的逻辑在此
        msg par = dataList.get(position);
//        Log.i("hello",par.getTitle()+par.getContent());
        ((BodyViewHolder) viewHolder).getTitleView().setText(par.getUsername());
        ((BodyViewHolder) viewHolder).getContentView().setText(par.getContent());
        ((BodyViewHolder) viewHolder).getDateView().setText(par.getUpdatedAt());
        ((BodyViewHolder) viewHolder).getImage().setImageResource(R.drawable.head);
        ((BodyViewHolder) viewHolder).itemView.setId(position);

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
    }

/**
 * 给GridView中的条目用的ViewHolder，里面只有一个TextView
 */
public class BodyViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private TextView contentView;
    private TextView dateView;
    private ImageView image;
    public TextView getTitleView() {
        return titleView;
    }

    public TextView getContentView() {
        return contentView;
    }

    public TextView getDateView() {
        return dateView;
    }

    public ImageView getImage() {
        return image;
    }

    public BodyViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.item_messagename);
        contentView = (TextView) itemView.findViewById(R.id.item_messagecontent);
        dateView = (TextView) itemView.findViewById(R.id.item_messagedate);
        image = (ImageView) itemView.findViewById(R.id.item_userimage);
    }

}
}
