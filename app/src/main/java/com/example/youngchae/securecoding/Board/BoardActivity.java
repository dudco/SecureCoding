package com.example.youngchae.securecoding.Board;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.youngchae.securecoding.Data.BoardData;
import com.example.youngchae.securecoding.Data.UserService;
import com.example.youngchae.securecoding.LoginInfoActivity;
import com.example.youngchae.securecoding.R;
import com.example.youngchae.securecoding.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    Dialog dialog;
    RecyclerView recyclerView;
    ArrayList<BoardData> boardList = new ArrayList<>();
    BoardAdapter adapter;
    ArrayList<BoardList> items = new ArrayList<>();
    String UserName;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        new getBoardAsynTask().execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new BoardAdapter(items);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        UserName = getIntent().getStringExtra("UserName");
        Log.d("dudco", UserName);

        linearLayout = (LinearLayout) findViewById(R.id.linear);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1234){
            new getBoardAsynTask().execute();
            if(resultCode == RESULT_OK){
                Snackbar.make(linearLayout,"게시 성공", Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(linearLayout,"실패",Snackbar.LENGTH_SHORT).show();
            }
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
            Intent intent = new Intent(BoardActivity.this, AddBoardActivity.class);
            intent.putExtra("UserName", UserName);
            startActivityForResult(intent, 1234);
        }
        if(item.getItemId() == R.id.item_reload){
            new getBoardAsynTask().execute();
            Log.d("dudco", items.size()+"");
            adapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.item_login_info){
            Intent intent = new Intent(BoardActivity.this, LoginInfoActivity.class);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                        items.clear();
                        boardList.clear();

                        Log.d("dudco", response.body().toString());
                        boardList.addAll(response.body());

                        for(BoardData board : boardList){
                            SimpleDateFormat sd = new SimpleDateFormat("yyyy년MM월dd일");
                            String date = sd.format(board.getDate());
                            items.add(new BoardList(board.getDesc(), board.getName(), date));
                            Log.d("dudco", board.getDesc());
                        }

                        adapter.notifyDataSetChanged();
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
    protected void onStart() {
        super.onStart();
        new getBoardAsynTask().execute();
        Log.d("dudco", items.size()+"");
        adapter.notifyDataSetChanged();
    }
}
