package com.zzz.wuye.enterPage.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.location.adapter.snomalAdapter;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;

import java.util.List;

/**
 * Created by oruret on 2018/4/16.
 */

public class normalListActivity extends Activity {
    private RecyclerView recyclerView;
    private List<String> dataList;
    private int type;
    private TextView titleText;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent();
            intent.putExtra("data",dataList.get(position));
            setResult(RESULT_OK,intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_list);

        findViewByid();
        initData();
        initView();
    }

    public void findViewByid(){
        recyclerView = (RecyclerView) findViewById(R.id.normal_table);
        titleText = (TextView) findViewById(R.id.titleText);
    }

    public void initView(){
        titleText.setText("物业");
        final snomalAdapter adapter = new snomalAdapter(dataList,onRecyclerviewItemClickListener);

        final GridLayoutManager layoutManager2 = new GridLayoutManager(this, 1);
//        layoutManager2.setAutoMeasureEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setLayoutManager(layoutManager2);

        recyclerView.setAdapter(adapter);
    }

    public void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getInt("num");
        //从intent对象中把封装好的数据取出来
        if(type == 2){
            dataList = bundle.getStringArrayList("floor");
        }else if(type== 3) {
            dataList = bundle.getStringArrayList("door");
        }
    }
}
