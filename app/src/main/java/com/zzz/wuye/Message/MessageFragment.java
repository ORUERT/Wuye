package com.zzz.wuye.Message;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zzz.wuye.Message.adapter.messageAdapter;
import com.zzz.wuye.Message.model.msg;
import com.zzz.wuye.R;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;
import com.zzz.wuye.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by oruret on 2018/4/12.
 */

public class MessageFragment extends Fragment implements View.OnClickListener  {
    private RecyclerView recyclerView;
    private List<msg> dataList = new ArrayList<>();
    private View rootView;
    private messageAdapter adapter;
//    private List<String> t1 = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_message, null);
            findViewById(rootView);
            initView();// 控件初始化
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        reLoadNews();
    }

    private void reload() {
        BmobQuery<msg> query = new BmobQuery<msg>();
//执行查询方法
        query.findObjects(new FindListener<msg>() {
            @Override
            public void done(List<msg> object, BmobException e) {
                if(e==null){
                    Log.i("news","查询成功：共"+object.size()+"条数据。");
                    for (msg news : object) {
                        Log.i("news", String.valueOf(news.getCreatedAt()));
                        dataList.add(news);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void findViewById(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.message_table);
    }

    public void initView() {
//        mContext = getActivity();
//        foundList.setOnItemClickListener(this);
         adapter = new messageAdapter(dataList);
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
//        layoutManager2.setAutoMeasureEnabled(true);

//        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(adapter);
        reload();
    }
    @Override
    public void onClick(View v) {

    }
}
