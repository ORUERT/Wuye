package com.zzz.wuye.sellhouse.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.sellhouse.adapter.FruitAdapter;
import com.zzz.wuye.exchange.exchangeadd;
import com.zzz.wuye.exchange.model.stuff;
import com.zzz.wuye.exchange.myexchange;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.sellhouse.model.houseCard;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class mysellhouse extends BaseActivity implements View.OnClickListener{

    private List<houseCard> fruitList = new ArrayList<>();
    private List<Boolean> fruitCheck = new ArrayList<>();
    private ImageView titleback;
    private TextView titleedit;
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private boolean editorStatus = false;
    private boolean isSelectAll = false;
    private int index = 0;
    private TextView titleText;


    Button mBtnDelete;
    TextView mTvSelectNum;
    TextView mSelectAll;

    View mLlMycollectionBottomDialog;




    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            if (editorStatus) {
                boolean isSelect = fruitCheck.get(position);
                if (!isSelect) {
                    index++;
                    fruitCheck.set(position,true);
                    if (index == fruitList.size()) {
                        isSelectAll = true;
                        mSelectAll.setText("取消全选");
                    }
                } else {
                    fruitCheck.set(position,false);
                    index--;
                    isSelectAll = false;
                    mSelectAll.setText("全选");
                }
                setBtnBackground(index);
                mTvSelectNum.setText(String.valueOf(index));
                adapter.notifyDataSetChanged();
            }else {
                //这里的view就是我们点击的view  position就是点击的position
                Intent intent = new Intent(mysellhouse.this,sellhouseadd.class);
                intent.putExtra("houseCard",fruitList.get(position));
                startActivityForResult(intent,123);

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysellhouse);

        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        titleback = findViewById(R.id.title_back);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        titleedit = findViewById(R.id.web_share_text);
        titleText = findViewById(R.id.titleText);


        mBtnDelete = findViewById(R.id.btn_delete);
        mTvSelectNum = findViewById(R.id.tv_select_num);
        mSelectAll = findViewById(R.id.select_all);
        mLlMycollectionBottomDialog = (View) findViewById(R.id.ll_mycollection_bottom_dialog);
    }

    private void initFruits() {
        String bql ="select * from houseCard where uploadId='"+ BmobUser.getCurrentUser(User.class).getObjectId()+"'";
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
                        fruitCheck.clear();
                        for(int i = 0 ;i  < fruitList.size() ;i ++)fruitCheck.add(false);
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
        titleText.setText("我的出租");
        titleback.setOnClickListener(this);
        titleedit.setText("编辑");
        titleedit.setVisibility(View.VISIBLE);
        titleedit.setOnClickListener(this);

        mBtnDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);
//        mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);

//        titleed

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
        adapter = new FruitAdapter(fruitList,onRecyclerviewItemClickListener,this,fruitCheck);
        recyclerView.setAdapter(adapter);
        initFruits();
        houseCard temp = (houseCard)getIntent().getSerializableExtra("house");

        if(temp!=null){

        }
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
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_share_text:
                updataEditMode();
                break;
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.title_back:
                finish();
        }
    }
    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (adapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = fruitList.size(); i < j; i++) {
                fruitCheck.set(i,true);
            }
            index = fruitList.size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = fruitList.size(); i < j; i++) {
                fruitCheck.set(i,false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        adapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }
    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0){
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BmobObject>dele = new ArrayList<BmobObject>();
                final List<Integer>deleindex = new ArrayList<>();
                Log.i("size123",fruitCheck.size()+" "+fruitList.size());
                for (int i = 0 ; i <fruitList.size(); i++) {
                    if (fruitCheck.get(i) == true) {
                        Log.i("fruitCheck", String.valueOf(i)+fruitList.get(i).getHousetitle());
                        houseCard p2 = new houseCard();
                        p2.setObjectId(fruitList.get(i).getObjectId());
                        dele.add(p2);
                        deleindex.add(i);
                    }
                }
                BmobBatch batch =new BmobBatch();

                batch.deleteBatch(dele);
//执行批量操作
                batch.doBatch(new QueryListListener<BatchResult>(){

                    @Override
                    public void done(List<BatchResult> results, BmobException ex) {
                        if(ex==null){
                            Log.i("resultsize", String.valueOf(results.size()));
                            //返回结果的results和上面提交的顺序是一样的，请一一对应
                            for(int i=0;i<results.size();i++){
                                BatchResult result= results.get(i);
                                if(result.isSuccess()){//只有批量添加才返回objectId
//                                    ("第"+i+"个成功："+result.getObjectId()+","+result.getUpdatedAt());
//                                    fruitCheck.remove((int)deleindex.get(i));
//                                    Log.i("deleindex", String.valueOf(deleindex.get(i)));
//                                    fruitList.remove((int)deleindex.get(i));
//                                    index--;
                                    initFruits();
                                }else{
                                    BmobException error= result.getError();
//                                    log("第"+i+"个失败："+error.getErrorCode()+","+error.getMessage());
                                }
                            }
                            for(int i =0 ; i < fruitCheck.size() ;i ++)fruitCheck.set(i,false);
//                            index = 0;
                            mTvSelectNum.setText(String.valueOf(0));
                            setBtnBackground(index);
                            if (fruitList.size() == 0){
                                mLlMycollectionBottomDialog.setVisibility(View.GONE);
                            }
                            for(int i =0 ; i < fruitCheck.size() ; i ++)Log.i("poi",i+ String.valueOf(fruitCheck.get(i)));
                            adapter.notifyDataSetChanged();
                            builder.dismiss();
                        }else{
                            Log.i("bmob","失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                });


            }
        });
    }
    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {

            titleedit.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            titleedit.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        adapter.setEditMode(mEditMode);

    }

    private void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            refreshFruits();
        }
    }
}
