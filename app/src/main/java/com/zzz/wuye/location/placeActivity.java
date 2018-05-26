package com.zzz.wuye.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.location.adapter.placeAdapter;
import com.zzz.wuye.location.model.locationModel;
import com.zzz.wuye.location.view.addLocationActivity;
import com.zzz.wuye.login.LoginActivity;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;
import com.zzz.wuye.register.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;
import static org.litepal.LitePalApplication.getContext;

public class placeActivity extends Activity implements View.OnClickListener {
    List<locationModel> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    Button addButton;

    View first;
    View floor;
    View doornum;
    TextView title;
    ImageView back;
    placeAdapter adapter;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1 = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            switch (v.getId()){
                case R.id.pla_deletebutton:
                    dataList.remove(position);
                    User user = new User();
                    user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
                    user.remove("place");
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                User p = new User();
                                p.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
                                p.addAll("place", dataList);
                                //添加多个Object值
                                p.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.i("bmob","更新成功");
                                            refresh();
                                        }else{
                                            Log.i("bmob","更新失败："+e.getMessage());
                                        }
                                    }

                                });
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                    break;
                case R.id.pla_editbutton:
                    break;
                default:
                    Log.i(dataList.get(0).getMainlocal(),"zxcv");
                    Intent intent = new Intent();
                    intent.putExtra("location",dataList.get(position));

                    setResult(RESULT_OK,intent);
                    finish();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        findViewByid();
//        initData();
        initView();
    }

    public void findViewByid(){
        recyclerView = (RecyclerView) findViewById(R.id.place_table);
        title = (TextView) findViewById(R.id.titleText);
        floor = (View) findViewById(R.id.addlocation_floor);
        doornum = (View) findViewById(R.id.addlocation_doornum);
        first = (View) findViewById(R.id.addlocation_main);
        addButton = (Button) findViewById(R.id.place_add);
        back = (ImageView)findViewById(R.id.title_back);
    }
    public void refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object,BmobException e) {
                if(e==null){
                    if(object.get(0).getPlace() == null){
                        dataList = new ArrayList<>();
                    }else {
                        List<locationModel> place = object.get(0).getPlace();
                        dataList.clear();
                        dataList.addAll(place);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                }
            }
        });
//        if(user.getPlace() == null){
//            dataList = new ArrayList<>();
//        }else {
//            List<locationModel> place = user.getPlace();
//            dataList.clear();
//            dataList.addAll(place);
//            adapter.notifyDataSetChanged();
//        }
    }
    public void initView(){
        title.setText("选择地址");
        back.setOnClickListener(this);
        addButton.setOnClickListener(this);

        adapter = new placeAdapter(dataList,onRecyclerviewItemClickListener1);
        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
//        Log.i("user12345", "hello");
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.place_add:
                Intent intent = new Intent(this,addLocationActivity.class);
                Log.i("here","here");
                startActivityForResult(intent,1);
//                startActivity(intent);
                break;
            case R.id.title_back:
                finish();
                break;
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
