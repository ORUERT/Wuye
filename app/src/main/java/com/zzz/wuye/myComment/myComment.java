package com.zzz.wuye.myComment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zzz.wuye.R;
import com.zzz.wuye.adapter.NewsDetailCommnetsAdapter;
import com.zzz.wuye.entity.Comment;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.view.base.BaseActivity;
import com.zzz.wuye.widgets.ListViewWithScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class myComment extends BaseActivity {
    ListViewWithScrollView recyclerView;
    NewsDetailCommnetsAdapter adapter2;
//    private List<Comment> commentList;
    private List<String> commentList = new ArrayList<>();
    private List<Comment> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
    }


    @Override
    protected void findViewById() {
        recyclerView = findViewById(R.id.favor_table);
    }

    public void initView() {
        //做一些假数据
//    }

        adapter2 = new NewsDetailCommnetsAdapter(this, dataList);
        recyclerView.setAdapter(adapter2);
    }
    public void initData(){
        User currentUser = BmobUser.getCurrentUser(User.class);
//        commentList = currentUser.getComment();
//        for(int i = 0 ; i < commentList.size() ; i ++){
//            BmobQuery<Comment> query = new BmobQuery<Comment>();
//            query.getObject(commentList.get(i), new QueryListener<Comment>() {
//                @Override
//                public void done(Comment object, BmobException e) {
//                    if(e==null){
//                        dataList.add(object);
//                    }else{
//                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                    }
//                }
//
//            });
//        }
    }
}
