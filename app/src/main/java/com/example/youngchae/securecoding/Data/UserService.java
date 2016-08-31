package com.example.youngchae.securecoding.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by youngchae on 2016-08-14.
 */
public interface UserService {
    @FormUrlEncoded
    @POST("/auth/login")
    Call<UserData> login(@Field("id") String id, @Field("password") String pw);

    @FormUrlEncoded
    @POST("/auth/register")
    Call<UserData> register(@Field("id") String id, @Field("pw") String pw, @Field("name") String name, @Field("hintid") String hintid, @Field("passhint") String passhint);

    @FormUrlEncoded
    @POST("/findpass")
    Call<UserData> findpw(@Field("id") String id, @Field("passhint") String passhint);

    @FormUrlEncoded
    @POST("/upboard")
    Call<BoardData> upboard(@Field("name") String name, @Field("desc")String desc);

    @FormUrlEncoded
    @POST("/getboard")
    Call<List<BoardData>> getboard(@Field("aaaa") String aaa);

    @FormUrlEncoded
    @POST("/getuserdata")
    Call<UserData> getUserData(@Field("name")String name);
}
