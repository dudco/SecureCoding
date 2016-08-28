package com.example.youngchae.securecoding;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.youngchae.securecoding.Aes256.Aes256Util;
import com.example.youngchae.securecoding.Data.UserData;
import com.example.youngchae.securecoding.Data.UserService;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Aes256Util aes;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        String text = "암호화되지 않은 문자입니다.";
//        String encText = aes256.aesEncode(text);
//        String decText = aes256.aesDecode(encText);

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
        String string = "";
        switch (item.getItemId()){
            case android.R.id.home : finish(); break;
            case R.id.done :
                String email_Enc = "";
                String pass_Enc = "";
                String name_Enc = "";
                String passhint_Enc = "";
                if(edit_reg_pass.getText().toString().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("회원가입 오류")
                            .setMessage("비밀번호에는 반드시 특수문자가 포함되어야 합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }else if(!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", edit_reg_email.getText())){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("회원가입 오류")
                            .setMessage("이메일 형식이 올바르지 않습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }else{

                    try {
                        pass_Enc = Util.aes.RegEncPass(edit_reg_pass.getText().toString());
                        email_Enc = Util.aes.RegEncPass(edit_reg_email.getText().toString());
                        passhint_Enc = Util.aes.RegEncPass(edit_reg_passhint.getText().toString());
                        name_Enc = Util.aes.RegEncPass(edit_reg_name.getText().toString());
                    } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException |
                            BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    new RegAsynTask().execute(
                            email_Enc,
                            pass_Enc ,
                            name_Enc,
                            "1", passhint_Enc);
                }

                break;
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

            if(charSequence.length() < 9){
                textinput_reg_pass.setErrorEnabled(true);
                textinput_reg_pass.setError("PassWord는 9자 이상이여야 합니다.");
            }else{
                if(charSequence.toString().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){

                    textinput_reg_pass.setErrorEnabled(true);
                    textinput_reg_pass.setError("PassWord에는 특수문자가 1개이상 존재해야 합니다.");
                }else{
                    textinput_reg_pass.setErrorEnabled(false);
                }
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

    class RegAsynTask extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(RegisterActivity.this)
                    .setTitle("회원가입")
                    .setMessage("회원가입 중입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dialog.dismiss();
            Util.retrofit.create(UserService.class).register(strings[0], strings[1], strings[2], strings[3], strings[4]).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    dialog.dismiss();
                    if(response.code() == 402){
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("결과")
                                .setMessage("이미 존재하는 ID입니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }else if(response.code() == 200){
                        String name_dec = "";
                        try {
                            name_dec = Util.aes.FindpwDecPass(response.body().getName());
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        }
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("결과")
                                .setMessage("환영합니다 "+name_dec+"님")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }).show();
                    }else{
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("결과")
                                .setMessage("서버오류")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("결과")
                            .setMessage("서버오류")
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
}
