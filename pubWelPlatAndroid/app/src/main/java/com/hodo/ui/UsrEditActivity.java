package com.hodo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.bean.Json;
import com.hodo.bean.SessionInfo;
import com.hodo.bean.Usr;
import com.hodo.intface.UsrServiceI;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.URLProtocol;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BitmapCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import okhttp3.Response;


@EActivity(R.layout.usr_edit)
public class UsrEditActivity extends AppCompatActivity {

    private String userId;
    private String userGroup;
    private boolean isLogin;

    @Extra("cid")
    String cid;

    @ViewById(R.id.toolbar_normal)
    Toolbar toolbar_normal;

    @ViewById(R.id.crealname)
    TextView crealname;

    @ViewById(R.id.icon)
    ImageView icon;

    @ViewById(R.id.cname)
    TextView cname;

    @ViewById(R.id.cpwd)
    TextView cpwd;

    @ViewById(R.id.tel)
    TextView tel;

    @ViewById(R.id.userGroupName)
    TextView userGroupName;

    @RestService
    UsrServiceI usrService;

    @Click(R.id.okBtn)
    void okBtn(){
        //修改

        updUsrCnameAndCpwd(cid,crealname.getText().toString(),cname.getText().toString(),cpwd.getText().toString());

    }


    @AfterViews
    void init(){
        //工具栏
        toolbar_normal.setTitle("用户修改");

        toolbar_normal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
                finish();
            }
        });

        isLogin=checkLogin();

        //获取数据
        getUsrDetail();


    }

    @Background
    void getUsrDetail(){
        Usr usr = usrService.getUserInfoA(cid);
        updateUsrDetail(usr);
    }
    @UiThread
    void updateUsrDetail(Usr usr){
        if(!CheckUtils.isEmpty(usr) ){
                String picDb =  CheckUtils.isEmpty(usr.getIcon())?"":usr.getIcon();
                String url = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ picDb;
                loadImg(icon,url);

                crealname.setText(CheckUtils.isEmpty(usr.getCrealname())?"":usr.getCrealname());
                cname.setText(CheckUtils.isEmpty(usr.getCname())?"":usr.getCname());
//                cpwd.setText(CheckUtils.isEmpty(usr.getCpwd())?"":usr.getCpwd());
                tel.setText(CheckUtils.isEmpty(usr.getTel())?"":usr.getTel());
                userGroupName.setText(CheckUtils.isEmpty(usr.getUserGroupName())?"":usr.getUserGroupName());

        } else {
            Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void updUsrCnameAndCpwd(String cid,String crealname,String cname,String cpwd){
        Json j = usrService.updUsrCnameAndCpwdByCidA(cid,crealname,cname,cpwd);
        updateView(j);
    }
    @UiThread
    void updateView(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    private void loadImg(final ImageView imageView,String url) {
        //String url = "https://avatar.csdn.net/5/A/5/3_yzj_0722.jpg";
        OkHttpUtils.get(url)
                .tag(this)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onSuccess(Bitmap bitmap, okhttp3.Call call, Response response) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    boolean checkLogin(){
        String cid=SessionInfo.getUserId();
        if(!CheckUtils.isEmpty(cid)){
            userId=SessionInfo.getUserId();
            userGroup =SessionInfo.getUserGroup();
            return true;
        } else {
            return false;
        }
    }

    void checkToLogin(){
        if(!isLogin){
            Intent intent = new Intent();
            intent.setClass(UsrEditActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }

}
