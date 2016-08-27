package com.example.youngchae.securecoding.Data;

import java.util.Date;

/**
 * Created by youngchae on 2016-08-14.
 */
public class BoardData {
    private String name;
    private String desc;
    private Date date;

    public BoardData(String name, String desc, Date date) {
        this.name = name;
        this.desc = desc;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getDate() {
        return date;
    }
}
