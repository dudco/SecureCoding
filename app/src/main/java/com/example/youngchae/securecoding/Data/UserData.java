package com.example.youngchae.securecoding.Data;

/**
 * Created by youngchae on 2016-08-14.
 */
public class UserData {
    private String _id;
    private String id;
    private String password;
    private String name;
    private String hintid;
    private String passhint;
    private String profile;

    public UserData(String _id, String id, String password, String name, String hintid, String passhint, String profile) {
        this._id = _id;
        this.id = id;
        this.password = password;
        this.name = name;
        this.hintid = hintid;
        this.passhint = passhint;
        this.profile = profile;
    }

    public String get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getHintid() {
        return hintid;
    }
    public String getPasshint() {
        return passhint;
    }

    public String getProfile() {
        return profile;
    }
}
