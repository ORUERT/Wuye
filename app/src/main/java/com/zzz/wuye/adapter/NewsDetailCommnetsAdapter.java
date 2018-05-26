package com.zzz.wuye.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.entity.Comment;
import com.zzz.wuye.register.model.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;

/**
 * Created by djzhao on 17/05/02.
 */

public class NewsDetailCommnetsAdapter extends BaseAdapter {

    private Context mContext;

    private List<Comment> mList;

    private LayoutInflater inflater;

    private BmobFile now_user_img;
    OnCommentButtonClickListner onCommentButtonClickListner;

    public NewsDetailCommnetsAdapter(Context mContext, List<Comment> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_news_detail_comment, null);

            viewholder = new ViewHolder();
            viewholder.username = (TextView) convertView.findViewById(R.id.news_detail_comment_username);
            viewholder.commentTime = (TextView) convertView.findViewById(R.id.news_detail_comment_time);
//            viewholder.replyUser = (TextView) convertView.findViewById(R.id.news_detail_comment_reply_user);
//            viewholder.replyContainer = (LinearLayout) convertView.findViewById(R.id.news_detail_reply_info);
            viewholder.content = (TextView) convertView.findViewById(R.id.news_detail_commment_content);
            viewholder.image = (CircleImageView) convertView.findViewById(R.id.news_detail_image);
//            viewholder.addComment = (ImageView) convertView.findViewById(R.id.news_detail_comment_add_reply);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Comment comment = mList.get(position);
        viewholder.username.setText(comment.getUsername());

        viewholder.commentTime.setText(comment.getUpdatedAt());
        viewholder.content.setText(comment.getComment());

        if(TextUtils.isEmpty(comment.getUserid())){
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereEqualTo("objectId",comment.getUserid());
//            query.findObjects(new FindListener<GameScore>() {
//                @Override
//                public void done(List<GameScore> object, BmobException e) {
//                    if(e==null){
//                        toast("查询成功：共"+object.size()+"条数据。");
//                        for (GameScore gameScore : object) {
//                            //获得playerName的信息
//                            gameScore.getPlayerName();
//                            //获得数据的objectId信息
//                            gameScore.getObjectId();
//                            //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
//                            gameScore.getCreatedAt();
//                        }
//                    }else{
//                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                    }
//                }
//            });
//        Log.i("userid",comment.getUserid());

            query.getObject(BmobUser.getCurrentUser(User.class).getObjectId(), new QueryListener<User>() {
                @Override
                public void done(User object, BmobException e) {
                    if(e==null){
                        now_user_img = object.getImage();
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            });
        }
        if(now_user_img == null){
            viewholder.image.setImageResource(R.drawable.calendar);
        }else {
            String now_user_img_url = now_user_img.getFileUrl();
            // 用户头像实例化
            ImageLoader.getInstance().displayImage((String) now_user_img_url, viewholder.image);
        }
//        viewholder.addComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doButtonClickAction(mList.get(position).getUsername());
//            }
//        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView username;
        private TextView commentTime;
        private CircleImageView image;
//        private TextView replyUser;
        private TextView content;
//        private ImageView addComment;
//        private LinearLayout replyContainer;
    }

public interface OnCommentButtonClickListner {
    public void OnCommentButtonClicked(String replyUser);
}

public void setOnCommentButtonClickListner(OnCommentButtonClickListner onCommentButtonClickListner) {
    this.onCommentButtonClickListner = onCommentButtonClickListner;
}

public void doButtonClickAction(String replyUser) {
    if (onCommentButtonClickListner != null) {
        onCommentButtonClickListner.OnCommentButtonClicked(replyUser);
    }
}
}
