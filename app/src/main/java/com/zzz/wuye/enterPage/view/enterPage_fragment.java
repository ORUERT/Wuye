package com.zzz.wuye.enterPage.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.enterPage.adapter.table2Adapter;
import com.zzz.wuye.enterPage.adapter.tableAdapter;
import com.zzz.wuye.exchange.exchange;
import com.zzz.wuye.news.NewsDetailActivity;
import com.zzz.wuye.news.model._Article;
import com.zzz.wuye.payment.Payment;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.zzz.wuye.repairOnline.view.repairOnline;
import com.zzz.wuye.report.view.report;
import com.zzz.wuye.sellhouse.view.sellhouse;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by oruret on 2018/3/16.
 */

public class enterPage_fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recycler2View;
    private View rootView;
    private AdPlayBanner adPlayBanner;
    private table2Adapter adapter2;
    private List<_Article> newsList = new ArrayList<>();
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1 = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            switch (position){
                case 0:
                    Intent intent3 = new Intent(getActivity(),sellhouse.class);
                    startActivity(intent3);

                    break;
                case 1:
                    Intent intent2 = new Intent(getActivity(),Payment.class);
                    startActivity(intent2);
                    break;
                case 2:
                    Intent intent5 = new Intent(getActivity(), repairOnline.class);
                    startActivity(intent5);
                    break;
                case 3:
                    Intent intent4 = new Intent(getActivity(),report.class);
                    startActivity(intent4);

                    break;

                case 4:
                    Intent intent1 = new Intent(getActivity(), easyPhoneNum_fragment.class);
                    startActivity(intent1);
                    break;

                case 5:
                    Intent intent = new Intent(getActivity(),exchange.class);
                    startActivity(intent);
                    break;
                case 6:

                case 7:



            }
        }
    };
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener2 = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {


            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("title",newsList.get(position).getTitle());
            intent.putExtra("url",newsList.get(position).getUrl());
            intent.putExtra("date",newsList.get(position).getCreatedAt());
            intent.putExtra("id",newsList.get(position).getObjectId());
            startActivity(intent);
        }
    };
    List<Pair> dataList1 = new ArrayList<>();
    List<AdPageInfo> imageList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_enterpage, null);
            findViewById(rootView);
            initData();
            initView();// 控件初始化
        }
        return rootView;
    }

    public void findViewById(View v){
        recyclerView = v.findViewById(R.id.mainevent);
        recycler2View = v.findViewById(R.id.secendevent);
        adPlayBanner = v.findViewById(R.id.game_banner);
    }
    public void initView() {
        //轮播
        Fresco.initialize(getContext());
        adPlayBanner.setInfoList(imageList).setUp();
        //首页功能适配器
        final tableAdapter adapter = new tableAdapter(dataList1,onRecyclerviewItemClickListener1);
        recyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        //新闻适配器
        adapter2 = new table2Adapter(newsList,onRecyclerviewItemClickListener2);
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
        recycler2View.setLayoutManager(layoutManager2);

        recycler2View.setAdapter(adapter2);

    }
    public void initData(){
        imageList.clear();
        AdPageInfo temp = new AdPageInfo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525973205803&di=5ba6d8facd0b7583bb6f918ebc4f88db&imgtype=0&src=http%3A%2F%2Fimg.ukecy.com%2FImg%2F2016%2F6%2F29%2Fca7aafb8-2e53-42a3-b40e-ab1bc8480113.jpg","",1);
        imageList.add(temp);
        AdPageInfo temp2 = new AdPageInfo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525973328610&di=45b1d0fb351d28aa325e83b0d5fa5d96&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100719%2F5404319_144023160406_2.jpg","",2);
        imageList.add(temp2);
        AdPageInfo temp3 = new AdPageInfo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525973387453&di=9498398a03645d7374566158ae715c77&imgtype=0&src=http%3A%2F%2Fimg18.house365.com%2Fnewcms%2F2017%2F06%2F16%2F149760391259439f48b8fe1.jpg","",3);
        imageList.add(temp3);
//        for(int i = 0 ; i < 3 ; i ++){
//            AdPageInfo temp = new AdPageInfo("","http://osjnd854m.bkt.clouddn.com/pic1_meitu_1.jpg","",i);
//            imageList.add(temp);
//        }

//        for(int i = 0 ; i < 8 ; i ++){
//            dataList1.add("缴费");
//        }
//        dataList1.add(new Pair("社区论坛",R.drawable.ic_aspect_luntan_black_24dp));//3
        dataList1.add(new Pair("房屋出租",R.drawable.ic_account_house_black_24dp));//1
//        dataList1.add(new Pair("欠费缴纳",R.drawable.ic_attach_money_black_24dp));//3
        dataList1.add(new Pair("预缴物业费",R.drawable.ic_wuye_black_24dp));//1
        dataList1.add(new Pair("在线报修",R.drawable.ic_repair_black_24dp));//1
        dataList1.add(new Pair("投诉建议",R.drawable.ic_announcement_black_24dp));//2
        dataList1.add(new Pair("便民电话",R.drawable.ic_perm_phone_msg_black_24dp));//2
        dataList1.add(new Pair("闲置转让",R.drawable.ic_exchange_black_24dp));//1

        //新闻查询
        BmobQuery<_Article> query = new BmobQuery<_Article>();
//执行查询方法
        query.findObjects(new FindListener<_Article>() {
            @Override
            public void done(List<_Article> object, BmobException e) {
                if(e==null){
                    Log.i("news","查询成功：共"+object.size()+"条数据。");
                    for (_Article news : object) {
                        Log.i("news", String.valueOf(news.getCreatedAt()));
                        newsList.add(news);
                        adapter2.notifyDataSetChanged();
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        adPlayBanner.stop();
    }
}
