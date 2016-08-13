package com.example.youngchae.securecoding;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout contain_main;
    RelativeLayout contain_Logo;
    RelativeLayout contain_Edit;
    RelativeLayout contain_findpw;

    TextInputLayout textinput_emil;
    TextInputLayout textinput_pass;
    TextInputEditText edit_email;
    TextInputEditText edit_pass;

    TextInputLayout textinput_findpw_email;
    TextInputLayout textinput_findpw_passhint;
    TextInputEditText edit_findpw_email;
    TextInputEditText edit_findpw_passhint;

    Button btn_findpw;

    Button btn_login;
    TextView text_FindPass;
    TextView text_reg;
    TextView text_X;
    ImageView image_main;
    ImageView image_textlogo;

    Handler handler = new Handler();

    Animation ani;
    Animation show_ani;

    RelativeLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        image_main = (ImageView) findViewById(R.id.image_main);
        image_textlogo = (ImageView) findViewById(R.id.image_textlogo);

        contain_main = (RelativeLayout) findViewById(R.id.contain_main);
        contain_Logo = (RelativeLayout) findViewById(R.id.contain_logo);
        contain_Edit = (RelativeLayout) findViewById(R.id.contain_edit);
        contain_findpw = (RelativeLayout) findViewById(R.id.contain_findpw);

        textinput_emil = (TextInputLayout) findViewById(R.id.textinput_email);
        textinput_pass = (TextInputLayout) findViewById(R.id.textinput_pass);
        edit_email = (TextInputEditText) findViewById(R.id.edit_email);
        edit_pass = (TextInputEditText) findViewById(R.id.edit_pass);

        textinput_findpw_email = (TextInputLayout) findViewById(R.id.textinput_findpw_email);
        textinput_findpw_passhint = (TextInputLayout) findViewById(R.id.textinput_findpw_passhint);
        edit_findpw_email = (TextInputEditText) findViewById(R.id.edit_findpw_email);
        edit_findpw_passhint = (TextInputEditText) findViewById(R.id.edit_findpw_passhint);
        btn_findpw = (Button) findViewById(R.id.btn_findpw);

        btn_login = (Button) findViewById(R.id.btn_login);
        text_FindPass = (TextView) findViewById(R.id.text_findpw);
        text_reg = (TextView) findViewById(R.id.text_reg);
        text_X = (TextView) findViewById(R.id.text_X);
        ani = AnimationUtils.loadAnimation(this, R.anim.main_logo);
        ani.setDuration(1000);
        show_ani = new AlphaAnimation(0, 1);
        show_ani.setDuration(1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                contain_Logo.startAnimation(ani);
                contain_Edit.startAnimation(show_ani);
                contain_Edit.setVisibility(View.VISIBLE);
            }
        }, 3000);

        //새 계정 만들기
        text_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        text_FindPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dudco", text_FindPass.getWidth()/2+"     "+text_FindPass.getHeight()/2);
                Animator visibleAni = ViewAnimationUtils.createCircularReveal(contain_main, (int) (text_FindPass.getX()+text_FindPass.getWidth()/2), (int) (text_FindPass.getY()+text_FindPass.getHeight()/2), 0, Math.max(contain_main.getWidth(), contain_main.getHeight()));
                visibleAni.setDuration(500);
                contain_Edit.setVisibility(View.GONE);
                contain_findpw.setVisibility(View.VISIBLE);
                image_textlogo.setImageResource(R.drawable.lifeinvader_logo_findpw);
                image_main.setImageResource(R.drawable.mainlog_findpw);
                contain_main.setBackgroundColor(Color.WHITE);
                edit_findpw_email.addTextChangedListener(findpw_email_textwatcher);
                visibleAni.start();
            }
        });
        text_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animator visibleAni = ViewAnimationUtils.createCircularReveal(contain_main, (int) (text_FindPass.getX()+text_FindPass.getWidth()/2), (int) (text_FindPass.getY()+text_FindPass.getHeight()/2), 0, Math.max(contain_main.getWidth(), contain_main.getHeight()));
                visibleAni.setDuration(500);
                contain_Edit.setVisibility(View.VISIBLE);
                contain_findpw.setVisibility(View.GONE);
                image_textlogo.setImageResource(R.drawable.lifeinvader_logo);
                image_main.setImageResource(R.drawable.mainlogo);
                contain_main.setBackgroundColor(Color.parseColor("#00000000"));
                visibleAni.start();
            }
        });

        edit_email.addTextChangedListener(edit_email_textwatcher);

    }
    TextWatcher edit_email_textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", charSequence)){
                textinput_emil.setErrorEnabled(true);
                textinput_emil.setError("올바른 이메일 형식이 아닙니다.");
            }else{
                textinput_emil.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    TextWatcher findpw_email_textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", charSequence)){
                textinput_findpw_email.setErrorEnabled(true);
                textinput_findpw_email.setError("올바른 이메일 형식이 아닙니다.");
            }else{
                textinput_findpw_email.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
