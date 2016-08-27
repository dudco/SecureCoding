package com.example.youngchae.securecoding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class AddBoardActivity extends AppCompatActivity {

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


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : finish(); break;
            case R.id.done : break;
        }
        return super.onOptionsItemSelected(item);
    }
}
