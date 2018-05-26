package com.zzz.wuye.personPage.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzz.wuye.R;
import com.zzz.wuye.personPage.adapter.gridlayout1Adapter;
import com.zzz.wuye.personPage.adapter.gridlayout2Adapter;
import com.zzz.wuye.personPage.adapter.gridlayout3Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oruret on 2018/3/9.
 */

public class OrderFragment extends Fragment {
    private List<Pair> dataList1 = new ArrayList<>();
    private List<Pair> dataList2 = new ArrayList<>();
    private List<Pair> dataList3 = new ArrayList<>();
    private gridlayout1Adapter adapter1;
    private gridlayout2Adapter adapter2;
    private gridlayout3Adapter adapter3;
    private RecyclerView home_rv1;
    private RecyclerView home_rv2;
    private RecyclerView home_rv3;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_infopage, null);
            findViewById(rootView);
            initData();
            initView();// 控件初始化
        }
        return rootView;
    }

    public void findViewById(View v){
        home_rv1 = v.findViewById(R.id.order_table1);
        home_rv2 = v.findViewById(R.id.order_table2);
        home_rv3 = v.findViewById(R.id.order_table3);
    }
    public void initView(){
        adapter1 =  new gridlayout1Adapter(dataList1);
        home_rv1.addItemDecoration(new SpaceItemDecoration(30,true,true,true,true));
        home_rv1.setAdapter(adapter1);
        final GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
        home_rv1.setLayoutManager(layoutManager1);

        adapter2 =  new gridlayout2Adapter(dataList2);
//        home_rv1.addItemDecoration(new SpaceItemDecoration(30,true,true,true,true));
        home_rv2.setAdapter(adapter2);
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        home_rv2.setLayoutManager(layoutManager2);

        adapter3 =  new gridlayout3Adapter(dataList3);
//        home_rv3.addItemDecoration(new SpaceItemDecoration(30,true,true,true,true));
        home_rv3.setAdapter(adapter3);
        final GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 4);
        home_rv3.setLayoutManager(layoutManager3);
    }

    public void initData(){
        dataList1.add(new Pair("10","第一个"));
        dataList1.add(new Pair("10","第一个"));
        dataList1.add(new Pair("10","第一个"));
        dataList1.add(new Pair("10","第一个"));

        for(int i = 0 ; i < 6 ; i ++){
            dataList2.add(new Pair("20","第二个"));
        }

        for(int i = 0 ; i < 8 ; i ++){
            dataList3.add(new Pair(R.drawable.calendar,"第三个"));
        }
    }

}
