package com.zzz.wuye.news.model;

import java.util.Date;

/**
 * Created by oruret on 2018/3/24.
 */

public class Newsitem {
    String title;
    String content;
    Date date;
    public Newsitem(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
