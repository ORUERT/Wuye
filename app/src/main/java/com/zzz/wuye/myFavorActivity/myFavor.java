package com.zzz.wuye.myFavorActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.enterPage.adapter.table2Adapter;
import com.zzz.wuye.news.NewsDetailActivity;
import com.zzz.wuye.news.model._Article;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static org.litepal.LitePalApplication.getContext;

public class myFavor extends BaseActivity {
    RecyclerView recyclerView;
    table2Adapter adapter2;
    private List<String> newsList = new ArrayList<>();
    private List<_Article> dataList = new ArrayList<>();
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener2 = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(getContext(), NewsDetailActivity.class);
            intent.putExtra("title",dataList.get(position).getTitle());
            intent.putExtra("url",dataList.get(position).getUrl());
            intent.putExtra("date",dataList.get(position).getCreatedAt());
            intent.putExtra("id",dataList.get(position).getObjectId());
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favor);
        findViewById();
        initData();
        initView();
    }


    @Override
    protected void findViewById() {
        recyclerView = findViewById(R.id.favor_table);
    }

    public void initView() {
        adapter2 = new table2Adapter(dataList,onRecyclerviewItemClickListener2);
////        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
//        layoutManager2.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(adapter2);

    }
    public void initData(){
        User currentUser = BmobUser.getCurrentUser(User.class);
        newsList = currentUser.getFavor();
        for(int i = 0 ; i < newsList.size() ; i ++){
            BmobQuery<_Article> query = new BmobQuery<_Article>();
            query.getObject(newsList.get(i), new QueryListener<_Article>() {
                @Override
                public void done(_Article object, BmobException e) {
                    if(e==null){
                        dataList.add(object);
                        adapter2.notifyDataSetChanged();
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            });
        }
    }

}
