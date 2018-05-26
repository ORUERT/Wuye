package com.zzz.wuye.arrearage;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzz.wuye.R;
import com.zzz.wuye.arrearage.adapter.arrearageAdapter;
import com.zzz.wuye.arrearage.model.Arrearage;
import com.zzz.wuye.arrearage.view.IArrearage;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oruret on 2018/3/15.
 */

public class arrearage_Fragment extends Fragment implements IArrearage {
    private arrearageAdapter adapter;
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Arrearage> dataList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_arrearage, null);
        findViewById(v);
        initData();
        initView();
        return v;
    }

    public void findViewById(View v){
        recyclerView = v.findViewById(R.id.check_table);
    }
    public void initView(){
        //做一些假数据

        //实例化Adapter并且给RecyclerView设上
        final arrearageAdapter adapter = new arrearageAdapter(dataList);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20,true,false,true,false));
        recyclerView.setAdapter(adapter);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);

        //把LayoutManager设置给RecyclerView
        recyclerView.setLayoutManager(layoutManager);
    }
    public void initData(){
        for(int i = 0 ; i < 10 ; i ++){
            Arrearage temp = new Arrearage();
            temp.setId(i);
            temp.setCheckType("helloworld");
            temp.setCheckCompany("oruert");
            temp.setAccountNum(123);
            temp.setBalance(123);
            temp.setDate(new java.util.Date());
//            if(temp == null){
//                Log.i("hello","asdasdasdasd");
//            }
//            Log.i("size", String.valueOf(dataList.size()));
            dataList.add(temp);
        }
    }

}
