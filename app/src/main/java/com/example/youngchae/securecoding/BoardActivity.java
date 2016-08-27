package com.example.youngchae.securecoding;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.youngchae.securecoding.Data.BoardData;
import com.example.youngchae.securecoding.Data.UserService;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    Dialog dialog;
    EditText editText;
    ArrayList<BoardData> boardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

//        editText = (EditText) findViewById(R.id.edit_board);

//        findViewById(R.id.btn_getboard).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new getBoardAsynTask().execute();
//            }
//        });
//
//        findViewById(R.id.btn_upboard).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new upBoardAsynTask().execute("dudco",editText.getText().toString());
//            }
//        });
    }

    class upBoardAsynTask extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(BoardActivity.this)
                    .setTitle("업로드")
                    .setMessage("업로드 중입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            dialog.dismiss();
            Util.retrofit.create(UserService.class).upboard(params[0], params[1]).enqueue(new Callback<BoardData>() {
                @Override
                public void onResponse(Call<BoardData> call, Response<BoardData> response) {
                    if(response.code() == 400){
                        new AlertDialog.Builder(BoardActivity.this)
                                .setTitle("게시판")
                                .setMessage("오류발생")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }else{
                        new AlertDialog.Builder(BoardActivity.this)
                                .setTitle("게시판")
                                .setMessage("일시 : " + response.body().getDate() + "\n글쓴이 : " + response.body().getName())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                }

                @Override
                public void onFailure(Call<BoardData> call, Throwable t) {

                    new AlertDialog.Builder(BoardActivity.this)
                            .setTitle("게시판")
                            .setMessage("서버 오류")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            });
            return null;
        }
    }
    class getBoardAsynTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(BoardActivity.this)
                    .setTitle("로딩")
                    .setMessage("로딩 중입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (dialog.isShowing())
                dialog.dismiss();
            Util.retrofit.create(UserService.class).getboard("aaa").enqueue(new Callback<List<BoardData>>() {
                @Override
                public void onResponse(Call<List<BoardData>> call, Response<List<BoardData>> response) {
                    if (response.code() == 200) {
                        Log.d("dudco", response.body().toString());
                        boardList = (ArrayList<BoardData>) response.body();
                        for(BoardData board : boardList){
                            Log.d("dudco", board.getDesc());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BoardData>> call, Throwable t) {

                }
            });
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_add_board){
            startActivity(new Intent(BoardActivity.this, AddBoardActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new getBoardAsynTask().execute();
    }
}
