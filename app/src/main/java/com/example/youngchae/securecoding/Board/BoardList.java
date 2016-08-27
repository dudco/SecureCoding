package com.example.youngchae.securecoding.Board;

/**
 * Created by dudco on 2016. 8. 27..
 */
public class BoardList {
    private String desc;
    private String name;
    private String date;

    public BoardList(String desc, String name, String date) {
        this.desc = desc;
        this.name = name;
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
