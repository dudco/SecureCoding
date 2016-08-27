package com.example.youngchae.securecoding;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.example.youngchae.securecoding.Aes256.Aes256Util;
import com.example.youngchae.securecoding.Data.BoardData;
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

    AlertDialog dialog;

    Aes256Util aes;
    String asdf;
    SharedPreferences session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = getSharedPreferences("session", MODE_PRIVATE);
        if(!session.getString("name","null").equals("null")){
        }
        try {
            aes = new Aes256Util("a87sda09d08f0a98sd08f00d");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
                Log.d("dudco", text_FindPass.getWidth() / 2 + "     " + text_FindPass.getHeight() / 2);
                Animator visibleAni = ViewAnimationUtils.createCircularReveal(contain_main, (int) (text_FindPass.getX() + text_FindPass.getWidth() / 2), (int) (text_FindPass.getY() + text_FindPass.getHeight() / 2), 0, Math.max(contain_main.getWidth(), contain_main.getHeight()));
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
                Animator visibleAni = ViewAnimationUtils.createCircularReveal(contain_main, (int) (text_FindPass.getX() + text_FindPass.getWidth() / 2), (int) (text_FindPass.getY() + text_FindPass.getHeight() / 2), 0, Math.max(contain_main.getWidth(), contain_main.getHeight()));
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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    asdf = Util.aes.LoginEncPass(edit_pass.getText().toString());
                    Log.d("dudco",asdf);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                new LoginAsynTask().execute(edit_email.getText().toString(), asdf);
            }
        });
        btn_findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FindPWAsynTask().execute(edit_findpw_email.getText().toString(), edit_findpw_passhint.getText().toString());
            }
        });
    }

    TextWatcher edit_email_textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", charSequence)) {
                textinput_emil.setErrorEnabled(true);
                textinput_emil.setError("올바른 이메일 형식이 아닙니다.");
            } else {
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
            if (!Pattern.matches("^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$", charSequence)) {
                textinput_findpw_email.setErrorEnabled(true);
                textinput_findpw_email.setError("올바른 이메일 형식이 아닙니다.");
            } else {
                textinput_findpw_email.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    class LoginAsynTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(LoginActivity.this)
                    .setTitle("로그인")
                    .setMessage("로그인 중입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Util.retrofit.create(UserService.class).login(strings[0], strings[1]).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    dialog.dismiss();
                    if(response.code() == 200){
                        session.edit().putString("name", response.body().getName()).apply();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("로그인")
                                .setMessage("로그인 성공")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        startActivity(new Intent(LoginActivity.this, BoardActivity.class));
                                    }
                                }).show();
                    }else{
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("로그인")
                                .setMessage("아이디 또는 비밀번호가 일치하지 않습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t)
                {
                    dialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("결과")
                            .setMessage("서버 오류")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
            });
            return null;
        }
    }
    class FindPWAsynTask extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(LoginActivity.this)
                    .setTitle("비밀번호 찾기")
                    .setMessage("찾는 중 입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Util.retrofit.create(UserService.class).findpw(strings[0], strings[1]).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    dialog.dismiss();

//                    Log.d("dudco", response.body().getPassword().toString());
                    if(response.code() == 200){
                        if(response!=null) {
                            String string = null;
                            try {
                                string = Util.aes.FindpwDecPass(response.body().getPassword());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            }
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("결과")
                                    .setMessage("당신의 비밀번호는" + string + "입니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(response.code() == 401){
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("결과")
                                .setMessage("PassWord Hint를 다시한번 확인해 주세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }else{
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("결과")
                                .setMessage("ID를 다시한번 확인해 주세요.")
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
                    dialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("결과")
                            .setMessage("서버 오류")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
            });
            return null;
        }
    }
}
