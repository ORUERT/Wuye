package com.zzz.wuye.exchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.exchange.adapter.FruitAdapter;
import com.zzz.wuye.exchange.model.stuff;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class search extends BaseActivity {

    TextView search;
    FruitAdapter adapter;
    RecyclerView recyclerView;
    List<stuff> fruitList = new ArrayList<>();
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(search.this,exchangeDetail.class);
            intent.putExtra("stuff",fruitList.get(position));
            startActivityForResult(intent,123);
        }
    };
    @Override
    protected void findViewById() {
        search = findViewById(R.id.search_text);
    }

    @Override
    protected void initView() {
        search.addTextChangedListener(textWatcher);

        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList,onRecyclerviewItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits(final String quest) {
        if(!TextUtils.isEmpty(quest)){
            String bql ="select * from stuff";
            final BmobQuery<stuff> query=new BmobQuery<stuff>();
            //设置查询的SQL语句
            query.setSQL(bql);
            query.doSQLQuery(new SQLQueryListener<stuff>(){

                @Override
                public void done(BmobQueryResult<stuff> result, BmobException e) {
                    if(e ==null){
                        List<stuff> list = (List<stuff>) result.getResults();
                        if(list!=null && list.size()>0){
                            List<stuff> temp = new ArrayList<>();

                            for(int i = 0 ; i < list.size() ; i ++){
                                int s1 = list.get(i).getTitle().indexOf(quest);
                                int s2 = list.get(i).getConnect().indexOf(quest);
                                int s3 = list.get(i).getTelephone().indexOf(quest);
                                if(s1!=-1||s2!=-1||s3!=-1){
                                    temp.add(list.get(i));
                                }
                            }
                            Log.i("size123", String.valueOf(temp.size()));

                            fruitList.clear();
                            fruitList.addAll(temp);
                            adapter.notifyDataSetChanged();
                        }else{
                            Log.i("smile", "查询成功，无数据返回");
                        }
                    }else{
                        Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                    }
                }
            });
        }else {
            fruitList.clear();
            fruitList.addAll(new ArrayList<stuff>());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById();
        initView();
    }

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            Log.i("hellworld","1");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("hellworld","2");

        }

        @Override
        public void afterTextChanged(Editable s) {
//            if(s.length() == 11){
                Log.i("hellworld","3");

                Log.i("stirng",s.toString());
                initFruits(s.toString());

//                String amountYuan = amount_et.getText().toString();//用户选择的金额+元
//                String mobileNum = mobile_et.getText().toString().trim();
//                int tMobile = Utils.getMobileOp(mobileNum);
//
//                String disAmount= getDisAmount(tMobile, amountYuan);
//                amount_discount_tv.setText(disAmount);
            }
//        }
    };
}
