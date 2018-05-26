package com.zzz.wuye.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzz.wuye.R;

/**
 * Created by oruret on 2018/3/9.
 */

public class test_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infopage, null);
        //找到RecyclerView控件
//        RecyclerView home_rv1 = v.findViewById(R.id.oruert);
////        Button temp = v.findViewById(R.id.hello_Button);
//        //做一些假数据
//        List<Pair> dataList = new ArrayList<>();
//        dataList.add(new Pair("10","第一个"));
//        dataList.add(new Pair("10","第一个"));
//        dataList.add(new Pair("10","第一个"));
//        dataList.add(new Pair("10","第一个"));
//
//        //实例化Adapter并且给RecyclerView设上
//        final tableAdapter adapter = new tableAdapter(dataList);
//        home_rv1.setAdapter(adapter);
//
//        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
//        // 实例化一个GridLayoutManager，列数为3
//        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//
//        //调用以下方法让RecyclerView的第一个条目仅为1列
////        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////            @Override
////            public int getSpanSize(int position) {
////                //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
////                //而如果不是0，则说明不是Header，就占用1列即可
////                return adapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
////            }
////        });
//
//        //把LayoutManager设置给RecyclerView
//        home_rv1.setLayoutManager(layoutManager);

        return v;
    }
}
