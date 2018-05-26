package com.zzz.wuye.repairOnline.view;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;
//import com.zzz.wuye.repairOnline.adapter.repairAdapter;
import com.zzz.wuye.repairOnline.adapter.repairAdapter;
import com.zzz.wuye.repairOnline.model.repairCard;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import static org.litepal.LitePalApplication.getContext;

public class repairOnline extends Activity implements View.OnClickListener{
    private TextView additem_title;
    private TextView additem_content;
    private ImageView titleback;

    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private repairAdapter adapter;
    private List<repairCard> dataList1 = new ArrayList<>();
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(repairOnline.this,repairDetail.class);
            intent.putExtra("repairCard",dataList1.get(position));
            startActivityForResult(intent,1);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_online);
        findViewById();
        initView();
    }
    protected void findViewById(){
        additem_title = findViewById(R.id.sitem_title);
        additem_content = findViewById(R.id.sitem_content);
        recyclerView = findViewById(R.id.repir_table);
        titleback = findViewById(R.id.title_back);
        constraintLayout = findViewById(R.id.sitem_add);
    }

    protected  void initView(){
        constraintLayout.setOnClickListener(this);
        titleback.setOnClickListener(this);
        additem_title.setText("添加报修信息");
        additem_content.setVisibility(View.GONE);

        adapter = new repairAdapter(dataList1,onRecyclerviewItemClickListener);
        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        refresh();
    }

    protected  void refresh(){
        String bql ="select * from repairCard";
        BmobQuery<repairCard> query=new BmobQuery<repairCard>();
//设置查询的SQL语句
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<repairCard>(){

            @Override
            public void done(BmobQueryResult<repairCard> result, BmobException e) {
                if(e ==null){
                    List<repairCard> list = (List<repairCard>) result.getResults();
                    if(list!=null && list.size()>0){
                        dataList1.clear();
                        dataList1.addAll(list);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sitem_add:
                Intent intent = new Intent(this,repairUploadOnline.class);
                startActivityForResult(intent,1);
                break;
            case R.id.title_back:
                finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            refresh();
        }
    }
}
