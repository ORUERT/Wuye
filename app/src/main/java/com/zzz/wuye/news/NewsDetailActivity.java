package com.zzz.wuye.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.gson.Gson;
import com.zzz.wuye.R;
import com.zzz.wuye.adapter.NewsDetailCommnetsAdapter;
import com.zzz.wuye.entity.Comment;
import com.zzz.wuye.news.model._Article;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.utils.MyDialogHandler;
import com.zzz.wuye.view.base.BaseActivity;
import com.zzz.wuye.widgets.ListViewWithScrollView;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.BitmapCallback;
//import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;

//import okhttp3.Call;

/**
 * Created by djzhao on 17/05/02.
 */

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    private NewsDetailCommnetsAdapter adapter;
    private _Article newsDetail;
    private List<Comment> mList;

    private String TITLE_NAME = "新鲜事详情";
    private View title_back;
    private TextView titleText;

    private TextView usernameTV;
    private TextView titleTV;
    private TextView releaseTimeTV;
    private ImageView imageIV;
    private TextView contentTV;

    private WebView webView;

    private LinearLayout commentPane;
    private EditText addCommentET;
    private ImageView addCommentIV;
    private boolean isShowCommentPane;


    private ListViewWithScrollView commentsLV;
    private LinearLayout commentLL;
    private LinearLayout favorLL;

    private Context mContext;

    private String replyUsername;
    private String replyUserId;
    private String newsId;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        newsId = getIntent().getIntExtra("newsId");
        setContentView(R.layout.activity_news_detail);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        usernameTV = $(R.id.news_detail_title);
        releaseTimeTV = $(R.id.news_detail_time);
        imageIV = $(R.id.news_detail_image);
        contentTV = $(R.id.news_detail_content);
        titleTV = $(R.id.detail_title);

        webView = findViewById(R.id.web_view);

        commentLL = $(R.id.news_detail_add_comment);
        favorLL = $(R.id.news_detail_add_favor);

        commentPane = $(R.id.news_detail_add_commment_pane);
        addCommentET = $(R.id.news_detail_add_commment_text);
        addCommentIV = $(R.id.news_detail_add_commment_btn);

        commentsLV = $(R.id.news_detail_comment);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        commentLL.setOnClickListener(this);
        favorLL.setOnClickListener(this);
        addCommentIV.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "加载中...");
        refreshData();
    }

    private void refreshData() {
//        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        String createddate = intent.getStringExtra("date");
        newsId = intent.getStringExtra("id");
        usernameTV.setText(title);
        releaseTimeTV.setText(createddate);
        webView.loadUrl(url);
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
        //启用支持Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        BmobQuery<_Article> query = new BmobQuery<_Article>();
        query.getObject(newsId, new QueryListener<_Article>() {

            @Override
            public void done(_Article object, BmobException e) {
                if(e==null){
                    //获得playerName的信息
                    if(object.getComments() == null)mList = new ArrayList<>();
                    else mList = object.getComments();
                    adapter = new NewsDetailCommnetsAdapter(mContext, mList);
                    adapter.setOnCommentButtonClickListner(new NewsDetailCommnetsAdapter.OnCommentButtonClickListner() {
                        @Override
                        public void OnCommentButtonClicked(String replyUser) {
                            commentPane.setVisibility(View.VISIBLE);
                            addCommentET.setHint("回复 " + replyUser + " 的评论");
                            replyUsername = replyUser;
                        }
                    });
                    commentsLV.setAdapter(adapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.news_detail_add_comment:
                showCommemtPane();
                break;
            case R.id.news_detail_add_favor:
//                DisplayToast("click");
                addNewFavor();
                break;
            case R.id.news_detail_add_commment_btn:
                addNewComment();
                break;
        }
    }

    private void showCommemtPane() {
        isShowCommentPane = !isShowCommentPane;
        if (isShowCommentPane) {
            commentPane.setVisibility(View.VISIBLE);
            addCommentET.setHint("发表新评论");
            replyUsername = BmobUser.getCurrentUser(User.class).getNickName();
            replyUserId = BmobUser.getCurrentUser(User.class).getObjectId();
            addCommentET.requestFocus();
        } else {
            commentPane.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addCommentET.getWindowToken(), 0);
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    // 添加新评论
    private void addNewComment() {
        String commentText = addCommentET.getText().toString().trim();
        if (TextUtils.isEmpty(commentText)) {
            DisplayToast("请先输入内容");
            return;
        }else{
                        Comment comment = new Comment();
                        comment.setComment(addCommentET.getText().toString());
                        comment.setUsername(replyUsername);
                        comment.setUserid(replyUserId);
                        _Article p = new _Article();
                        p.setObjectId(newsId);
                        p.add("comments",comment);   //添加单个Object
                        p.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    DisplayToast("评论提交成功");
                                    BmobQuery<_Article> query = new BmobQuery<_Article>();
                                    query.getObject(newsId, new QueryListener<_Article>() {

                                        @Override
                                        public void done(_Article object, BmobException e) {
                                            if(e==null){
                                                //获得playerName的信息
                                                if(object.getComments() == null)mList = new ArrayList<>();
                                                else mList = object.getComments();
                                                adapter = new NewsDetailCommnetsAdapter(mContext, mList);
                                                adapter.setOnCommentButtonClickListner(new NewsDetailCommnetsAdapter.OnCommentButtonClickListner() {
                                                    @Override
                                                    public void OnCommentButtonClicked(String replyUser) {
                                                        commentPane.setVisibility(View.VISIBLE);
                                                        addCommentET.setHint("回复 " + replyUser + " 的评论");
                                                        replyUsername = replyUser;
                                                    }
                                                });
                                                commentsLV.setAdapter(adapter);
                                            }else{
                                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                            }
                                        }

                                    });
                                }else{
                                    Log.i("bmob","更新失败："+e.getMessage());
                                }
                            }

                        });
                        fetchUserInfo();
            commentPane.setVisibility(View.GONE);

        }
    }

    private void addNewFavor() {
        User p = new User();
        p.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
        p.add("favor",newsId);   //添加单个Object=
        p.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    DisplayToast("收藏成功");
                    Log.i("bmob","添加成功");
                }else{
                    Log.i("bmob","添加失败："+e.getMessage());
                }
            }

        });

    }


}
