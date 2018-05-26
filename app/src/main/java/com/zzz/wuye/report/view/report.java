package com.zzz.wuye.report.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.repairOnline.model.repairCard;
import com.zzz.wuye.report.adapter.reportAdapter;
import com.zzz.wuye.report.addReportActivity;
import com.zzz.wuye.report.model.reportModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class report extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<reportModel> dataList1 = new ArrayList<>();
    private reportAdapter adapter;
    private ImageView edit;

    ImageView addbut;
    ImageView titleback;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new  Intent(report.this,addReportActivity.class);
            intent.putExtra("report",dataList1.get(position));
            intent.putExtra("hello","hello");
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        findViewById();
        initView();
    }
    protected void findViewById(){
        edit = findViewById(R.id.web_share_edit);
        recyclerView = findViewById(R.id.report_table);
        addbut = findViewById(R.id.web_share_edit);
        titleback = findViewById(R.id.title_back);
    }

    protected  void initView(){
        edit.setVisibility(View.VISIBLE);
        titleback.setOnClickListener(this);
//        additem_title.setVisibility(View.GONE);
//        additem_content.setVisibility(View.GONE);
        addbut.setOnClickListener(this);

        adapter = new reportAdapter(dataList1,onRecyclerviewItemClickListener);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));

        recyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        initData();
    }

    protected  void initData(){
        String bql ="select * from reportModel";
        BmobQuery<reportModel> query=new BmobQuery<reportModel>();
//设置查询的SQL语句
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<reportModel>(){

            @Override
            public void done(BmobQueryResult<reportModel> result, BmobException e) {
                if(e ==null){
                    List<reportModel> list = (List<reportModel>) result.getResults();
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
            case R.id.web_share_edit:
                Intent intent = new Intent(report.this,addReportActivity.class);

                startActivityForResult(intent,123);
                break;
            case R.id.title_back:
                finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            initData();
        }
    }
//    editText.setFocusable(false);
//
//editText.setFocusableInTouchMode(false);
}
