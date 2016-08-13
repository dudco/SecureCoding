package com.example.youngchae.securecoding;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout textinput_reg_email;
    TextInputLayout textinput_reg_pass;
    TextInputLayout textinput_reg_repass;
    TextInputLayout textinput_reg_name;
    TextInputLayout textinput_reg_passhint;

    EditText edit_reg_email;
    EditText edit_reg_pass;
    EditText edit_reg_repass;
    EditText edit_reg_name;
    EditText edit_reg_passhint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textinput_reg_email = (TextInputLayout) findViewById(R.id.textinput_reg_email);
        textinput_reg_pass = (TextInputLayout) findViewById(R.id.textinput_reg_pass);
        textinput_reg_repass = (TextInputLayout) findViewById(R.id.textinput_reg_repass);
        textinput_reg_name = (TextInputLayout) findViewById(R.id.textinput_reg_name);
        textinput_reg_passhint = (TextInputLayout) findViewById(R.id.textinput_reg_passhint);

        edit_reg_email = (EditText) findViewById (R.id.edit_reg_email);
        edit_reg_pass = (EditText) findViewById (R.id.edit_reg_pass);
        edit_reg_repass = (EditText) findViewById (R.id.edit_reg_repass);
        edit_reg_name = (EditText) findViewById (R.id.edit_reg_name);
        edit_reg_passhint = (EditText) findViewById (R.id.edit_reg_passhint);

        edit_reg_email.addTextChangedListener(reg_email_watcher);
        edit_reg_pass.addTextChangedListener(reg_pass_watcher);
        edit_reg_repass.addTextChangedListener(reg_repass_watcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reg_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : finish(); break;
            case R.id.done :finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    TextWatcher reg_email_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", charSequence)){
                textinput_reg_email.setErrorEnabled(true);
                textinput_reg_email.setError("올바른 이메일 형식이 아닙니다.");
            }else{
                textinput_reg_email.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    TextWatcher reg_pass_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length() < 10){
                textinput_reg_pass.setErrorEnabled(true);
                textinput_reg_pass.setError("PassWord는 10자 이상이여야 합니다.");
            }else{
                textinput_reg_pass.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    TextWatcher reg_repass_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!charSequence.toString().equals(edit_reg_pass.getText().toString())){
                textinput_reg_repass.setErrorEnabled(true);
                textinput_reg_repass.setError("비밀번호가 같지 않습니다.");
            }else{
                textinput_reg_repass.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
