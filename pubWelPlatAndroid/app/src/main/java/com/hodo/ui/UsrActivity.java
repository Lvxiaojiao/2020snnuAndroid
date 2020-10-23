package com.hodo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.adapter.UsrImageListAdapter;
import com.hodo.bean.Usr;
import com.hodo.intface.UsrServiceI;
import com.hodo.utils.CheckUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.usr)
public class UsrActivity extends AppCompatActivity {

    private List<Usr> list = new ArrayList<Usr>();

    @Extra("cls")
    String cls;

    @Extra("clsName")
    String clsName;

    @Extra("key")
    String key;

    @ViewById(R.id.toolbar_normal)
    Toolbar toolbar_normal;

    @ViewById(R.id.ImageListView)
    ListView listView;


    @RestService
    UsrServiceI usrService;


    @AfterViews
    void init(){
        //工具栏
        if(CheckUtils.isEmpty(clsName)){
            toolbar_normal.setTitle("用户管理");
        } else {
            toolbar_normal.setTitle(clsName);
        }
        toolbar_normal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
                finish();
            }
        });
        //获取数据
        getUsrList();
    }

    @Background
    void getUsrList(){
        List<Usr> usrList = usrService.usrListAllA();
        updateView(usrList);
    }
    @UiThread
    void updateView(List<Usr> usrList){
        if(!CheckUtils.isEmpty(usrList) && usrList.size()>0){
            list = usrList;
//            for (Req q:list){
//                Toast.makeText(ReqListActivity.this,q.getCid(),Toast.LENGTH_LONG).show();
//            }
            //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
            UsrImageListAdapter imageListAdapter = new UsrImageListAdapter(UsrActivity.this,UsrActivity.this,R.layout.usrimagelistviewitem,list);

            listView.setAdapter(imageListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Usr ia=list.get(position);
                    String cid = ia.getCid();
                    String cname = ia.getCname();
                    String cpwd=ia.getCpwd();
                    Intent intent = new Intent();
                    intent.setClass(UsrActivity.this, UsrEditActivity_.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("cname",cname);
                    intent.putExtra("cpwd",cpwd);
                    startActivity(intent);
                }
            });

        } else {
            Toast.makeText(this, "没有找到符合条件的需求", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
