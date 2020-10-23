package com.hodo.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.bean.Act;
import com.hodo.bean.Json;
import com.hodo.bean.Partin;
import com.hodo.bean.SessionInfo;
import com.hodo.intface.ActServiceI;
import com.hodo.intface.PartinServiceI;
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

import java.util.List;

import okhttp3.Response;


@EActivity(R.layout.act_detail)
public class ActDetailActivity extends AppCompatActivity {

    private String userId;
    private String userGroup;
    private boolean isLogin;
    private View mPopupHeadViewy;//创建一个view
    private PopupWindow mHeadPopupclly;//PopupWindow
    private TextView tetle,textdz;//title,打折
    private TextView textwzdl,textckxq;//我知道了,查看详情

    @Extra("cid")
    String cid;

    @Extra("usrCid")
    String usrCid;

    @Extra("check")
    String check;

    @ViewById(R.id.toolbar_normal)
    Toolbar toolbar_normal;

    @ViewById(R.id.nm)
    TextView nm;

    @ViewById(R.id.pic)
    ImageView pic;

    @ViewById(R.id.instro)
    TextView instro;

    @ViewById(R.id.sdateString)
    TextView sdateString;

    @ViewById(R.id.edateString)
    TextView edateString;

    @ViewById(R.id.reqAmt)
    TextView reqAmt;

    @ViewById(R.id.reqNum)
    TextView reqNum;

    @ViewById(R.id.crealname)
    TextView crealname;

    @ViewById(R.id.status)
    TextView status;

    @RestService
    ActServiceI actService;

    @RestService
    PartinServiceI partinService;

    @ViewById(R.id.rBtn)  Button rBtn;
    @ViewById(R.id.moneyBtn)  Button moneyBtn;
    @ViewById(R.id.passBtn)  Button passBtn;
    @ViewById(R.id.nopassBtn)  Button nopassBtn;
    @ViewById(R.id.reportBtn)  Button reportBtn;
    @ViewById(R.id.donateBtn)  Button donateBtn;

    @Click(R.id.rBtn)
    void rBtn(){
        //报名信息
        Intent intent = new Intent();
        intent.setClass(ActDetailActivity.this, PartinListActivity_.class);
        intent.putExtra("actCid",cid);
        intent.putExtra("tp","报名");
        ActDetailActivity.this.startActivity(intent);
    }

    @Click(R.id.moneyBtn)
    void moneyBtn(){
        //捐款信息
        Intent intent = new Intent();
        intent.setClass(ActDetailActivity.this, PartinListActivity_.class);
        intent.putExtra("actCid",cid);
        intent.putExtra("tp","捐款");
        ActDetailActivity.this.startActivity(intent);
    }
    @Click(R.id.passBtn)

    void passBtn(){
        //通过审核
        pass();
    }

    @Click(R.id.nopassBtn)
    void nopassBtn(){
        //拒绝通过审核
        nopass();
    }

    @Click(R.id.reportBtn)
    void reportBtn(){
        //报名
        //ReportDialog myDialog=new ReportDialog(ActDetailActivity.this,R.style.ReportDialog);
        //myDialog.show();
        checkReport();
    }

    @Click(R.id.donateBtn)
    void donateBtn(){
        final EditText inputServer = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入捐款金额").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String amt = inputServer.getText().toString();
                //捐款
                donate(amt);
            }
        });
        builder.show();


    }

    @AfterViews
    void init(){
        //工具栏
        toolbar_normal.setTitle("返回");
        toolbar_normal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
                finish();
            }
        });
        isLogin=checkLogin();

        //获取数据
        getReqDetail();

        if("2".equals(SessionInfo.getUserGroup())) {
            if("true".equals(check)){
                System.out.println(check);
                passBtn.setVisibility(View.GONE);
                nopassBtn.setVisibility(View.GONE);
                reportBtn.setVisibility(View.GONE);
                donateBtn.setVisibility(View.GONE);
            }else if("false".equals(check)){
                System.out.println(check);
                rBtn.setVisibility(View.GONE);
                moneyBtn.setVisibility(View.GONE);
                reportBtn.setVisibility(View.GONE);
                donateBtn.setVisibility(View.GONE);
            }
        } else  if("1".equals(SessionInfo.getUserGroup())) {
            rBtn.setVisibility(View.GONE);
            moneyBtn.setVisibility(View.GONE);
            passBtn.setVisibility(View.GONE);
            nopassBtn.setVisibility(View.GONE);

        } else  if("0".equals(SessionInfo.getUserGroup())) {

            passBtn.setVisibility(View.GONE);
            nopassBtn.setVisibility(View.GONE);
            reportBtn.setVisibility(View.GONE);
            donateBtn.setVisibility(View.GONE);
        } else {
            rBtn.setVisibility(View.GONE);
            moneyBtn.setVisibility(View.GONE);
            passBtn.setVisibility(View.GONE);
            nopassBtn.setVisibility(View.GONE);
            reportBtn.setVisibility(View.GONE);
            donateBtn.setVisibility(View.GONE);
        }
    }

    @Background
    void getReqDetail(){
        Act act = actService.actDetailA(cid);
        updateAct(act);
    }
    @UiThread
    void updateAct(Act act){
        if(!CheckUtils.isEmpty(act) ){
            String picDb =  CheckUtils.isEmpty(act.getPic())?"":act.getPic();
            String url = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ picDb;
            loadImg(pic,url);

            nm.setText(CheckUtils.isEmpty(act.getNm())?"":act.getNm());
            instro.setText(CheckUtils.isEmpty(act.getInstro())?"":act.getInstro());
            sdateString.setText(CheckUtils.isEmpty(act.getSdateString())?"":act.getSdateString());
            edateString.setText(CheckUtils.isEmpty(act.getEdateString())?"":act.getEdateString());
            reqAmt.setText(CheckUtils.isEmpty(act.getReqAmt())?"":String.valueOf(act.getReqAmt()));
            reqNum.setText(CheckUtils.isEmpty(act.getReqNum())?"":String.valueOf(act.getReqNum()));
            crealname.setText(CheckUtils.isEmpty(act.getCrealname())?"":act.getCrealname());
            status.setText(CheckUtils.isEmpty(act.getStatus())?"":act.getStatus());

            String usrCid=CheckUtils.isEmpty(act.getUsrCid())?"":act.getUsrCid();

        } else {
            Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void pass(){
        Json j = actService.checkA(cid,"通过");
        updatePass(j);
    }
    @UiThread
    void updatePass(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void nopass(){
        Json j = actService.checkA(cid,"未通过");
        updatenoPass(j);
    }
    @UiThread
    void updatenoPass(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void report(){
        Json j = partinService.reportA(cid,userId);
        updateReport(j);
    }
    @UiThread
    void updateReport(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            //返回
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void donate(String amt ){
        Json j = partinService.donateA(cid,userId,Integer.parseInt(amt));
        updateDonate(j);
    }
    @UiThread
    void updateDonate(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            //返回
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
    @UiThread
    void updatepubP(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
            //返回
            finish();
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void checkReport(){
        List<Partin> pList = partinService.partinListByA(cid,userId,"报名");
        searchReport(pList);
    }

    @UiThread
    void searchReport(List<Partin> pList){
        if(!CheckUtils.isEmpty(pList) && pList.size()>0){
            Toast.makeText(this,"您已经报过名，不能再报了。", Toast.LENGTH_SHORT).show();
        }else{
            report();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //设置返回监听事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_normal:
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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

    private void popupHeadWindowcll() {
        mPopupHeadViewy = View.inflate(this,R.layout.report_detail,null);
        tetle = (TextView) mPopupHeadViewy.findViewById(R.id.txt_name);
        textdz = (TextView) mPopupHeadViewy.findViewById(R.id.txt_ID);
        textwzdl = (TextView) mPopupHeadViewy.findViewById(R.id.textwzdl);
        textckxq = (TextView) mPopupHeadViewy.findViewById(R.id.textckxq);
        // 在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        mHeadPopupclly.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mHeadPopupclly.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mHeadPopupclly.setBackgroundDrawable(new BitmapDrawable());
        mHeadPopupclly.setOutsideTouchable(true);
        textwzdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadPopupclly.dismiss();
            }
        });
        textckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadPopupclly.dismiss();
            }
        });
    }

}
