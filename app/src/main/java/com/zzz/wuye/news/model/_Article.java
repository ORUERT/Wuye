package com.zzz.wuye.news.model;

import com.zzz.wuye.entity.Comment;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by djzhao on 17/05/02.
 */

public class _Article extends BmobObject {

//    private String username;

//    private BmobDate createdAt;

    private String title;
//
//    private String image;

    private String url;

    private List<Comment> comments;

//    public BmobDate getCreateAt() {
//        return createdAt;
//    }
//
//    public void setCreateAt(BmobDate createAt) {
//        this.createdAt = createAt;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getReleaseTime() {
//        return releaseTime;
//    }
//
//    public void setReleaseTime(String releaseTime) {
//        this.releaseTime = releaseTime;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
