package com.zzz.wuye.enterPage.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.enterPage.adapter.phoneNumberAdapter;
import com.zzz.wuye.enterPage.model.PhoneNum;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;
import com.zzz.wuye.repairOnline.model.repairCard;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import static org.litepal.LitePalApplication.getContext;

public class easyPhoneNum_fragment extends Activity {
    private RecyclerView recyclerView;
    private List<PhoneNum> dataList = new ArrayList<>();
    private TextView titleText;
    private phoneNumberAdapter adapter;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+dataList.get(position).getPhoneNum()));
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_phone_num_fragment);
        findViewByid();
        initView();
    }

    public void findViewByid(){
        recyclerView = (RecyclerView) findViewById(R.id.phoneNumber_table);
        titleText = (TextView) findViewById(R.id.titleText);
    }

    public void initView(){
        titleText.setText("便民电话");
        adapter = new phoneNumberAdapter(dataList,onRecyclerviewItemClickListener);

        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
//        layoutManager2.setAutoMeasureEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setLayoutManager(layoutManager2);

        recyclerView.setAdapter(adapter);
        initData();
    }

    public void initData(){
        for(int i = 0 ; i < 10 ; i ++){
//            PhoneNum bill = new PhoneNum("孙童","123456789", new java.util.Date());
//            PhoneNum repair = new PhoneNum();
//            repair.setUserName(i+"zzz");
//            repair.setPhoneNum("12332132213");
//            repair.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if(e == null){
//                        Log.i("successful","Bmob保修信息上传成功");
//                    }else{
//                        Log.i("error","Bmob保修信息保存失败"+e);
//                    }
//                }
//            });
            String bql ="select * from PhoneNum";
            BmobQuery<PhoneNum> query=new BmobQuery<PhoneNum>();
//设置查询的SQL语句
            query.setSQL(bql);
            query.doSQLQuery(new SQLQueryListener<PhoneNum>(){

                @Override
                public void done(BmobQueryResult<PhoneNum> result, BmobException e) {
                    if(e ==null){
                        List<PhoneNum> list = (List<PhoneNum>) result.getResults();
                        if(list!=null && list.size()>0){
                            dataList.clear();
                            dataList.addAll(list);
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

    }
}
