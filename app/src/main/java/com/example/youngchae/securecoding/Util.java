package com.example.youngchae.securecoding;

import java.io.UnsupportedEncodingException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by youngchae on 2016-08-14.
 */
public class Util {
    private static String skeyString;
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://yyc.applepi.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Aes aes;

    static {
        try {
            aes = new Aes();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
