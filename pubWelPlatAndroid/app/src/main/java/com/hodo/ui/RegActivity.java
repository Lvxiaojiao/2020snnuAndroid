package com.hodo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.RegHttpUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@EActivity(R.layout.reg)
public class RegActivity extends AppCompatActivity {

    @ViewById(R.id.tel)
    EditText telEt;

    @ViewById(R.id.pwd)
    EditText pwdEt;

    @ViewById(R.id.realName)
    EditText realNameEt;

    @ViewById(R.id.addr)
    EditText addrEt;

    @ViewById(R.id.userGroup)
    Spinner userGroup;

    @Click(R.id.img_return)
    void returnimg(){
        Intent intent = new Intent(RegActivity.this, LoginActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.checkCodeGetBtn)
    void checkCodeGet(){
        String tel = telEt.getText().toString();
        if(CheckUtils.isEmpty(tel)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tel.length()!=11){
            Toast.makeText(this,"手机号位数不够",Toast.LENGTH_SHORT).show();
            return;
        }else if (isMobile(tel)==false){
            Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        String pwd = pwdEt.getText().toString();
        if(CheckUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String realName = realNameEt.getText().toString();
        if(CheckUtils.isEmpty(realName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String addr= addrEt.getText().toString();
        if(CheckUtils.isEmpty(addr)) {
            Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String ug=userGroup.getSelectedItem().toString();
        String u="1";
        if("公益组织".equals(ug)){
            u="0";
        } else if("志愿者".equals(ug)){
            u="1";
        }
        RegHttpUtil.requestNetForPOSTReg(handler, telEt.getText().toString(),realNameEt.getText().toString(),addrEt.getText().toString(),pwdEt.getText().toString(),u);
    }

    public static boolean isMobile(String string){
        Pattern pattern =null;
        Matcher matcher =null;
        boolean b =false;
        pattern = Pattern.compile("^[1][3,5,8][0-9]{9}$");
        matcher = pattern.matcher(string);
        b = matcher.matches();
        return b ;
    }



    //创建一个handler
    Handler handlerSent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isRegsuccess = (boolean) msg.obj;
            if (isRegsuccess) {
                Toast.makeText(getApplicationContext(), "发送成功!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "发送失败!", Toast.LENGTH_LONG).show();
            }
        }
    };
    //创建一个handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                JSONObject j = (JSONObject) msg.obj;
                boolean b = j.getBoolean("success");
                String msgReturn = j.getString("msg");
                if (b) {
                    if(!CheckUtils.isEmpty(msgReturn))
                    //注册成功
                    Toast.makeText(getApplicationContext(),msgReturn, Toast.LENGTH_LONG).show();


                    SharedPreferences sp=getSharedPreferences("config",0);
                    SharedPreferences.Editor editor=sp.edit();
                    //把数据进行保存
                    editor.remove("userId");
                    editor.commit();

                    Intent intent = new Intent();
                    intent.setClass(RegActivity.this, LoginActivity_.class);
                    startActivity(intent);

                } else {
                    if(!CheckUtils.isEmpty(msgReturn))
                    Toast.makeText(getApplicationContext(), msgReturn, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    };
}
