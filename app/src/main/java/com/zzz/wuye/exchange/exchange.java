package com.zzz.wuye.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.api.AlipayClient;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.exchange.adapter.FruitAdapter;
import com.zzz.wuye.exchange.model.stuff;
import com.zzz.wuye.sellhouse.model.houseCard;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class exchange extends BaseActivity implements View.OnClickListener {
    private List<stuff> fruitList = new ArrayList<>();
    private ImageView titleback;
    private ImageView titleadd;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
//    @InjectView(R.id.titleText)
    TextView titletext;
    private FloatingActionButton flo;

    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(exchange.this,exchangeDetail.class);
            intent.putExtra("stuff",fruitList.get(position));
            startActivityForResult(intent,123);
        }
    };
    @Override
    protected void findViewById() {
        titleback = findViewById(R.id.title_back);
        titleadd = findViewById(R.id.web_add_btn);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        flo = findViewById(R.id.fab);
        titletext = findViewById(R.id.titleText);
    }

    @Override
    protected void initView() {
        titleadd.setVisibility(View.VISIBLE);
        flo.setOnClickListener(this);
        titleback.setOnClickListener(this);
        titleadd.setOnClickListener(this);

        titletext.setText("旧物置换");
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList,onRecyclerviewItemClickListener);
        recyclerView.setAdapter(adapter);
        initFruits();
    }

    private void initFruits() {
        String bql ="select * from stuff";
        BmobQuery<stuff> query=new BmobQuery<stuff>();
//设置查询的SQL语句
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<stuff>(){

            @Override
            public void done(BmobQueryResult<stuff> result, BmobException e) {
                if(e ==null){
                    List<stuff> list = (List<stuff>) result.getResults();
                    if(list!=null && list.size()>0){
                        fruitList.clear();
                        fruitList.addAll(list);
//                        Log.i("fruit", String.valueOf(fruitList.size()));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_add_btn:
//                AlipayClient
                Intent intent = new Intent(exchange.this,exchangeadd.class);
                startActivityForResult(intent,123);
                break;
            case R.id.fab:
                Intent intent1 = new Intent(exchange.this,search.class);
                startActivity(intent1);
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
