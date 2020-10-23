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
public class ActListActivity extends AppCompatActivity {

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

        //获取数据
        getActList();
    }

    @Background
    void getActList(){
        List<Act> actList = actService.actSearchListA(cls,key);
        updateView(actList);
    }
    @UiThread
    void updateView(List<Act> actList){
        if(!CheckUtils.isEmpty(actList) && actList.size()>0){
            list = actList;
            ActImageListAdapter imageListAdapter = new ActImageListAdapter(ActListActivity.this,R.layout.act_imagelist_item,list);

            listView.setAdapter(imageListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Act ia=list.get(position);
                    String cid = ia.getCid();
                    String usrCid = ia.getUsrCid();
                    Intent intent = new Intent();
                    intent.setClass(ActListActivity.this, ActDetailActivity_.class);
                    intent.putExtra("cid",cid);
                    intent.putExtra("usrCid",usrCid);
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
