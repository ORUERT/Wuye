package com.zzz.wuye.sellhouse.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.sellhouse.adapter.FruitAdapter;
import com.zzz.wuye.sellhouse.model.houseCard;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class sellhouse extends BaseActivity implements View.OnClickListener {
//    private Fruit[] fruits = {new Fruit("Apple", R.drawable.default_image), new Fruit("Banana", R.drawable.default_image),
//            new Fruit("Orange", R.drawable.default_image), new Fruit("Watermelon", R.drawable.default_image),
//            new Fruit("Pear", R.drawable.default_image), new Fruit("Grape", R.drawable.default_image),
//            new Fruit("Pineapple", R.drawable.default_image), new Fruit("Strawberry", R.drawable.default_image),
//            new Fruit("Cherry", R.drawable.default_image), new Fruit("Mango", R.drawable.default_image)};

    private List<houseCard> fruitList = new ArrayList<>();
    private ImageView titleback;
    private ImageView titleadd;
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;


    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(sellhouse.this,sellhouseDetail.class);
            intent.putExtra("houseCard",fruitList.get(position));
            startActivityForResult(intent,1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellhouse);
//        initFruits();

        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        titleback = findViewById(R.id.title_back);
        titleadd = findViewById(R.id.web_add_btn);
        swipeRefresh = findViewById(R.id.swipe_refresh);
    }

    private void initFruits() {

        String bql ="select * from houseCard";
        BmobQuery<houseCard> query=new BmobQuery<houseCard>();
//设置查询的SQL语句
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<houseCard>(){

            @Override
            public void done(BmobQueryResult<houseCard> result, BmobException e) {
                if(e ==null){
                    List<houseCard> list = (List<houseCard>) result.getResults();
                    if(list!=null && list.size()>0){
                        fruitList.clear();
                        fruitList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }

    @Override
    protected void initView() {
        titleadd.setVisibility(View.VISIBLE);
        titleback.setOnClickListener(this);
        titleadd.setOnClickListener(this);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList,onRecyclerviewItemClickListener);
        recyclerView.setAdapter(adapter);
        initFruits();
    }
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initFruits();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_add_btn:
                Intent intent = new Intent(sellhouse.this,sellhouseadd.class);
                startActivityForResult(intent,123);
            case R.id.title_back:
                finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            refreshFruits();
        }
    }
}
