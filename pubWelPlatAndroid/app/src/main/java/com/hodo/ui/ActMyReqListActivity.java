package com.hodo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.adapter.ActImageListAdapter;
import com.hodo.bean.Act;
import com.hodo.bean.SessionInfo;
import com.hodo.intface.ActServiceI;
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


@EActivity(R.layout.act_search_list)
public class ActMyReqListActivity extends AppCompatActivity {

    private List<Act> list = new ArrayList<Act>();

    @Extra("cls")
    String cls;

    @Extra("clsName")
    String clsName;

    @Extra("key")
    String key;

    @ViewById(R.id.ImageListView)
    ListView listView;


    @RestService
    ActServiceI actService;


    @AfterViews
    void init(){
        String userId= SessionInfo.getUserId();
        if(!CheckUtils.isEmpty(userId)){
        } else {
            Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(ActMyReqListActivity.this, LoginActivity_.class);
            startActivity(intent);
            return;
        }
        //获取数据
        getActList(userId);
    }

    @Background
    void getActList(String userId){
        List<Act> actList = actService.actMyReqListA(userId);
        updateView(actList);
    }
    @UiThread
    void updateView(List<Act> actList){
        if(!CheckUtils.isEmpty(actList) && actList.size()>0){
            list = actList;
            ActImageListAdapter imageListAdapter = new ActImageListAdapter(ActMyReqListActivity.this,R.layout.act_imagelist_item,list);

            listView.setAdapter(imageListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Act ia=list.get(position);
                    String cid = ia.getCid();
                    String nm = ia.getNm();
                    Intent intent = new Intent();
                    intent.setClass(ActMyReqListActivity.this, ActDetailActivity_.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("nm",nm);
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
