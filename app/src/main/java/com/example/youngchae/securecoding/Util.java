package com.example.youngchae.securecoding;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by youngchae on 2016-08-14.
 */
public class Util {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://yyc.applepi.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
