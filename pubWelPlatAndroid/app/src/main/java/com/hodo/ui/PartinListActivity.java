package com.hodo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.adapter.PartInImageListAdapter;
import com.hodo.bean.Partin;
import com.hodo.intface.PartinServiceI;
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


@EActivity(R.layout.partin_list)
public class PartinListActivity extends AppCompatActivity {

    private List<Partin> list = new ArrayList<Partin>();

    @Extra("actCid")
    String actCid;

    @Extra("usrCid")
    String usrCid;

    @Extra("tp")
    String tp;

    @ViewById(R.id.ImageListView)
    ListView listView;


    @RestService
    PartinServiceI partinService;

    @AfterViews
    void init(){
        //获取数据
        getPartinList();
    }

    @Background
    void getPartinList(){
        List<Partin> pList = partinService.partinListByA(actCid,usrCid,tp);
        updateView(pList);
    }
    @UiThread
    void updateView(List<Partin> pList){
        if(!CheckUtils.isEmpty(pList) && pList.size()>0){
            list = pList;
            PartInImageListAdapter imageListAdapter = new PartInImageListAdapter(PartinListActivity.this,R.layout.partin_imagelist_item,list);
            listView.setAdapter(imageListAdapter);

        } else {
            Toast.makeText(this, "没有找到符合条件的需求", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }
}
