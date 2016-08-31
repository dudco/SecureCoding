package com.example.youngchae.securecoding;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.youngchae.securecoding.Data.UserData;
import com.example.youngchae.securecoding.Data.UserService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInfoActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 100;
    public static final int REQUEST_CROP = 101;
    public static final int REQUEST_ALBUM = 102;

    private String UserName;
    private UserData userData;
    private TextView text_login_name;
    private TextView text_login_id;
    private ImageView imageView;


    Uri contentUri;
    String mCurrentPhotoPath;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info);

        getSupportActionBar().setTitle("로그인 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserName = getIntent().getStringExtra("UserName");


        try {
            new getUserDataAsyn().execute(Util.aes.LoginEncPass(UserName));
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

        text_login_id = (TextView) findViewById(R.id.text_login_id);
        text_login_name = (TextView) findViewById(R.id.text_login_name);

        imageView = (ImageView) findViewById(R.id.image_logininfo);
        final View view = LayoutInflater.from(LoginInfoActivity.this).inflate(R.layout.radio_dialog, null);
        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg);

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginInfoActivity.this); //AlertDialog.Builder 객체 생성
        builder.setTitle("프로필 변경"); //Dialog 제목
        builder.setView(view);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = rg.getCheckedRadioButtonId();
                Log.d("dudco", id + "");
                if(rg.getCheckedRadioButtonId() == R.id.dialog_rb_album){
                    Toast.makeText(LoginInfoActivity.this, "앨범",Toast.LENGTH_SHORT).show();
                }else if(rg.getCheckedRadioButtonId() == R.id.dialog_rb_camera){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1234);
                        } else {
                            dispatchTakePictureIntent();
                        }
                    } else {
                        dispatchTakePictureIntent();
                    }
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class getUserDataAsyn extends AsyncTask<String, Void, Void> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog.Builder(LoginInfoActivity.this)
                    .setTitle("회원정보")
                    .setMessage("회원정보를 가져오는 중입니다...")
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            dialog.dismiss();
            Util.retrofit.create(UserService.class).getUserData(params[0]).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    String name_dec = "", id_dec = "";
                    try {
                        name_dec = Util.aes.FindpwDecPass(response.body().getName());
                        id_dec = Util.aes.FindpwDecPass(response.body().getId());
                    } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException |
                            NoSuchAlgorithmException | InvalidKeyException | BadPaddingException e) {
                        e.printStackTrace();
                    }
                    text_login_name.setText("NAME : " + name_dec);
                    text_login_id.setText("ID : " + id_dec);

                    Picasso.with(LoginInfoActivity.this)
                            .load("http://yyc.applepi.kr/images/" + response.body().getProfile()).into(imageView);
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private Bitmap decodeBitmapFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        canvas.save();

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);

        canvas.restore();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            contentUri = createImageUri();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCurrentPhotoPath = getImagePath(contentUri);

        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(contentUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 200);
        cropIntent.putExtra("aspectY", 200);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 200);
        cropIntent.putExtra("outputY", 200);
        //retrieve data on return
        try {
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, createImageUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(cropIntent, REQUEST_CROP);
    }

    private Uri createImageUri() throws IOException {
        ContentResolver contentResolver = getContentResolver();
        ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, timeStamp);

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }

    private String getImagePath(Uri uri) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ALBUM:
                    contentUri = data.getData();
                    //no break
                case REQUEST_CAMERA:
//                    image.setImageURI(contentUri);
                    cropImage();
                    break;
                case REQUEST_CROP:
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        Bitmap bitmapFromUri = decodeBitmapFromUri(imageUri);
                        Bitmap bitmap = getCircleBitmap(bitmapFromUri);
//                        image.setImageBitmap(bitmap);

                        AQuery aq = new AQuery(this);
                        File file = new File(getImagePath(imageUri));
                        // 서버에 넘겨줄 데이터
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("uploadFile", file);

                        try {
                            params.put("name", Util.aes.LoginEncPass(UserName));
                            Log.d("dudco", Util.aes.LoginEncPass(UserName));
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
                        // 인자 : url, params: post방식 null이면 get방식, 서버에 전달할 데이터타입
                        aq.ajax("http://yyc.applepi.kr/upload", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                            @Override
                            public void callback(String url, JSONObject object,
                                                 AjaxStatus status) {
                                Log.d("dudco", url.toString());
                                Log.d("dudco", status.toString());
                                Log.d("dudco", object.toString());
                            }
                        });
                    }
                    break;
            }
        }
    }
}
