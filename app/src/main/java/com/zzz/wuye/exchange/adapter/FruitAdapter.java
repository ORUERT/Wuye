package com.zzz.wuye.exchange.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.exchange.model.stuff;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> implements View.OnClickListener{

    private List<stuff> mFruitList;
    private List<Boolean> fruitCheck;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private boolean editorStatus = false;
    private boolean isSelectAll = false;
    private int index = 0;
    private Context context1;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerview;
    @InjectView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @InjectView(R.id.btn_delete)
    Button mBtnDelete;
    @InjectView(R.id.select_all)
    TextView mSelectAll;
    @InjectView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;
    @InjectView(R.id.web_share_edit)
    TextView mBtnEditor;

    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        ImageView fruitCheck;

        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitCheck = (ImageView) view.findViewById(R.id.fruit_check);
        }
    }

    public FruitAdapter(List<stuff> fruitList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        mFruitList = fruitList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public FruitAdapter(List<stuff> fruitList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener,Context context,List<Boolean> fruitCheck) {
        mFruitList = fruitList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
        this.context1 = context;
        this.fruitCheck = fruitCheck;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        view.setOnClickListener(this);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        stuff fruit = mFruitList.get(position);
//        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getTitle());
        holder.fruitView.setTag(position);



        BmobFile image_1 = fruit.getImage().get(0);
        if(image_1 == null){
            holder.fruitImage.setImageResource(R.drawable.calendar);
        }else {

            String now_user_img_url = image_1.getFileUrl();
            String tag= (String) holder.fruitImage.getTag();
            if (!now_user_img_url.equals(tag)){
                holder.fruitImage.setTag(now_user_img_url);
                //设置图片
                ImageLoader.getInstance().displayImage((String) now_user_img_url, holder.fruitImage);
            }

        }



        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.fruitCheck.setVisibility(View.GONE);
        } else {
            holder.fruitCheck.setVisibility(View.VISIBLE);

            if (fruitCheck.get(position)) {
                holder.fruitCheck.setImageResource(R.drawable.ic_check_circle_blue_24dp);
            } else {
                holder.fruitCheck.setImageResource(R.drawable.ic_check_circle_white_24dp)   ;
            }
        }

    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(context1, R.color.white));
        }
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}