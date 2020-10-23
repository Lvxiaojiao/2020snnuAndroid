package com.hodo.ui;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.bean.Json;
import com.hodo.bean.SessionInfo;
import com.hodo.intface.ActServiceI;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.ImgUtil;
import com.hodo.utils.net.URLProtocol;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Calendar;


@EActivity(R.layout.act_pub)
public class ActPubActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE=1;
    private String url="";
    private static  String jsonAll="";
    private Calendar cal;
    private int year,month,day;

    @ViewById(R.id.pic)
    ImageView pic;

    @ViewById(R.id.nm)
    EditText nm;

    @ViewById(R.id.sdate)
    EditText sdate;

    @ViewById(R.id.edate)
    EditText edate;

    @ViewById(R.id.reqAmt)
    EditText reqAmt;

    @ViewById(R.id.reqNum)
    EditText reqNum;

    @ViewById(R.id.instro)
    EditText instro;

    @Click(R.id.pic)
    void pic(){
        Intent intent = new Intent();
        intent.setClass(ActPubActivity.this, ActPicLoadActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.pubBtn)
    void pubBtn(){
        String userId= SessionInfo.getUserId();
        if(!CheckUtils.isEmpty(userId)){
        } else {
            Toast.makeText(this, "请登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(ActPubActivity.this, LoginActivity_.class);
            startActivity(intent);
            return;
        }

        if(!CheckUtils.isEmpty(userId)
                && !CheckUtils.isEmpty(nm.getText())
                && !CheckUtils.isEmpty(sdate.getText())
                && !CheckUtils.isEmpty(edate.getText())
                && !CheckUtils.isEmpty(reqAmt.getText())
                && !CheckUtils.isEmpty(reqNum.getText())
                && !CheckUtils.isEmpty(instro.getText())
        ){
            String nmDb = nm.getText().toString();
            String sdateDb = sdate.getText().toString();
            String edateDb = edate.getText().toString();
            String reqAmtDb = reqAmt.getText().toString();
            String reqNumDb = reqNum.getText().toString();
            String instroDb = instro.getText().toString();
            String picDb = (CheckUtils.isEmpty(pic.getTag()) || pic.getTag()==null)?"":pic.getTag().toString();
            actPubA(userId,
                    nmDb,
                    sdateDb,
                    edateDb,
                    reqAmtDb,
                    reqNumDb,
                    instroDb,
                    picDb);
        } else {
            Toast.makeText(this, "请选择填完整表单", Toast.LENGTH_SHORT).show();
        }
    }

    @RestService
    ActServiceI actService;

    @AfterViews
    void init(){

    }

    @Background
    void actPubA(String userId,
                 String nmDb,
                 String sdateDb,
                 String edateDb,
                 String reqAmtDb,
                 String reqNumDb,
                 String instroDb,
                 String picDb){

        Json j = actService.actPubA(userId,
                nmDb,
                sdateDb,
                edateDb,
                reqAmtDb,
                reqNumDb,
                instroDb,
                picDb);
        updateReq(j);
    }
    @UiThread
    void updateReq(Json j){
        if(!CheckUtils.isEmpty(j) && j.isSuccess()){
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_LONG).show();

            Intent intent3 = new Intent();
            intent3.setClass(ActPubActivity.this, ActMyReqListActivity_.class);
            //intent.putExtra("cls",flg);
            startActivity(intent3);
        } else {
            Toast.makeText(this, j.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshPic");
        ActPubActivity.this.registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        super.onCreate(savedInstanceState);
    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("action.refreshPic"))
            {
                String picNm = intent.getStringExtra("pic");
                pic.setTag(picNm);
                String url = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ URLProtocol.IMG_DIR+"/"+picNm;
                ImgUtil.loadShaderImg(ActPubActivity.this,pic,url,200,200,20);
            }
        }
    };

    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    public void DateClick(View view){
        getDate();
        switch (view.getId()) {
            case R.id.edate:
                OnDateSetListener listener=new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        edate.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(ActPubActivity.this, DatePickerDialog.THEME_HOLO_LIGHT,listener,year,month,day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
            case R.id.sdate:
                OnDateSetListener listener1=new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        sdate.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog1=new DatePickerDialog(ActPubActivity.this, DatePickerDialog.THEME_HOLO_LIGHT,listener1,year,month,day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog1.show();
                break;
        }
    }
}