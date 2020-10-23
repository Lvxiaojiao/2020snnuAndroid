package com.hodo.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hodo.R;
import com.hodo.bean.SessionInfo;
import com.hodo.intface.UsrServiceI;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.URLProtocol;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BitmapCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@EActivity(R.layout.pic_load)
public class ActPicLoadActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE=1;
    private String url="";
    private static  String jsonAll="";

    private String userId="";
    @ViewById(R.id.toolbar_normal)
    Toolbar toolbar_normal;

    @ViewById(R.id.image)
    ImageView image;

    @Click(R.id.uploadBtn)
    void uploadBtn(){
        userId= SessionInfo.getUserId();
        if(!CheckUtils.isEmpty(userId)){
            //在这里跳转到手机系统相册里面
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_REQUEST_CODE);
        } else {
            Intent intent = new Intent();
            intent.setClass(ActPicLoadActivity.this, LoginActivity_.class);
            startActivity(intent);
        }

    }
    @RestService
    UsrServiceI usrService;
    @AfterViews
    void init(){
        //工具栏
        toolbar_normal.setTitle("活动图片上传");
        toolbar_normal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
                finish();
            }
        });

        userId= SessionInfo.getUserId();
        if(!CheckUtils.isEmpty(userId)){
           // getIcon(userId);
        } else {
            Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(ActPicLoadActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        switch (requestCode) {
            case IMAGE_REQUEST_CODE://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径

                        //url=path;

                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        image.setImageBitmap(bitmap);

                        File file = new File(path);

                        String url=URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ URLProtocol.ACTION_PIC_UPLOAD;
                        upImg(url,file);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    private void upImg(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        //File file = new File(Environment.getExternalStorageDirectory()+"/HeadPortrait.jpg");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ActPicLoadActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                jsonAll=jsonObject.get("obj").getAsString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!CheckUtils.isEmpty(jsonAll)){

                            //  广播通知刷新头像
                            Intent intent = new Intent();
                            intent.putExtra("pic",jsonAll);
                            intent.setAction("action.refreshPic");
                            sendBroadcast(intent);

                            finish();
                        } else {
                            Toast.makeText(ActPicLoadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    private void loadImg(final ImageView imageView,String url) {
        OkHttpUtils.get(url)
                .tag(this)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }
}
