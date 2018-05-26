package com.zzz.wuye.location.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rey.material.widget.RadioButton;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.enterPage.adapter.phoneNumberAdapter;
import com.zzz.wuye.location.model.locationModel;

import java.util.List;

import static com.zzz.wuye.utils.Constants.handleTextDisplay;

/**
 * Created by oruret on 2018/4/15.
 */

public class placeAdapter extends RecyclerView.Adapter {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private List<locationModel> dataList;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

    //构造函数
    public placeAdapter(List<locationModel> dataList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.dataList = dataList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, null);
//        view.setOnClickListener(this);
       BodyViewHolder myViewHolder = new BodyViewHolder(view);
       myViewHolder.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;

        return myViewHolder;
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        locationModel par = dataList.get(position);
//        Log.i("hello", par.getName() + par.getLocate());

        ((BodyViewHolder) viewHolder).getNameView().setText(handleTextDisplay(par.getConnectname(),10));
        ((BodyViewHolder) viewHolder).getTelephoneView().setText(handleTextDisplay(par.getTelephone(),12));
        ((BodyViewHolder) viewHolder).getMainView().setText(handleTextDisplay(par.getMainlocal(),12));
        ((BodyViewHolder) viewHolder).getFloorView().setText(handleTextDisplay(par.getFloor(),10));
        ((BodyViewHolder) viewHolder).getDoornumView().setText(handleTextDisplay(par.getHousenum(),10));

        if (par.isAutolocation()) {
            ((BodyViewHolder) viewHolder).getDefaultButton().setChecked(true);
        } else {
            ((BodyViewHolder) viewHolder).getDefaultButton().setChecked(false);
        }
        ((BodyViewHolder) viewHolder).itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class BodyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;
        private TextView nameView;
        private TextView telephoneView;
        private TextView mainView;
        private TextView floorView;
        private TextView doornumView;
        private RadioButton defaultButton;
        private Button cancelButton;
        private Button editButton;

        public BodyViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.pla_username);
            telephoneView = itemView.findViewById(R.id.pla_telephone);
            mainView = itemView.findViewById(R.id.pla_main);
            floorView = itemView.findViewById(R.id.pla_floor);
            doornumView = itemView.findViewById(R.id.pla_doornum);
            defaultButton = itemView.findViewById(R.id.pla_default);
            cancelButton = itemView.findViewById(R.id.pla_deletebutton);
            editButton = itemView.findViewById(R.id.pla_editbutton);
            cancelButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getTelephoneView() {
            return telephoneView;
        }

        public TextView getMainView() {
            return mainView;
        }

        public TextView getFloorView() {
            return floorView;
        }

        public TextView getDoornumView() {
            return doornumView;
        }

        public RadioButton getDefaultButton() {
            return defaultButton;
        }

        public Button getCancelButton() {
            return cancelButton;
        }

        public Button getEditButton() {
            return editButton;
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerviewItemClickListener != null) {
                mOnRecyclerviewItemClickListener.onItemClickListener(v,getAdapterPosition());
            }
        }
    }
}