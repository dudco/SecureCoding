package com.example.youngchae.securecoding.Data;

/**
 * Created by youngchae on 2016-08-14.
 */
public class BoardData {
    private String name;
    private String desc;
    private String date;

    public BoardData(String name, String desc, String date) {
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

    public String getDate() {
        return date;
    }
}
