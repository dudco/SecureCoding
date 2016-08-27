package com.example.youngchae.securecoding.Board;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.youngchae.securecoding.Data.BoardData;
import com.example.youngchae.securecoding.Data.UserService;
import com.example.youngchae.securecoding.R;
import com.example.youngchae.securecoding.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBoardActivity extends AppCompatActivity {
    Dialog dialog;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_board, null);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Log.d("dudco", width + "   " + height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)((float)width/1.5), (int)((float)width/3.3));
        view.setLayoutParams(params);

        setContentView(view);

        editText = (EditText) findViewById(R.id.edit_add_board);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dudco", getIntent().getStringExtra("UserName"));
                new upBoardAsynTask().execute(getIntent().getStringExtra("UserName"), editText.getText().toString());
            }
        });
        findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    class upBoardAsynTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(AddBoardActivity.this)
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
                        setResult(RESULT_CANCELED);
                        finish();
                    }else if(response.code() == 200){
                        setResult(RESULT_OK);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<BoardData> call, Throwable t) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
            return null;
        }
    }
}
