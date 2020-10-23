package com.hodo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.bean.SessionInfo;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.LoginHttpUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.login)
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.phoneNo)
    EditText phoneNo;
    @ViewById(R.id.pwd)
    EditText  pwd;
    @ViewById(R.id.checkBox)
    CheckBox manageCheckBox;
    @Click(R.id.loginBtn)
    void loginBtnClick(){
            if(!"".equals(phoneNo.getText()) && !"".equals(pwd.getText())) {
                String username = phoneNo.getText().toString();
                String password = pwd.getText().toString();
                //判断是否密码或者用户名为空
                if (TextUtils.isEmpty(username) ) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //post方法登录是否成功
                LoginHttpUtil.requestNetForPOSTLogin(handler, username, password);

            } else {
                Toast.makeText(LoginActivity.this, "用户名不能为空",Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
    }

    @Click(R.id.regBtn)
    void regBtnClick(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegActivity_.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        //取出数据，如果取出的数据时空时，只需把getString("","")第二个参数设置成空字符串就行了，不用在判断
        String userId=sharedPreferences.getString("userId","");
        String userGroup=sharedPreferences.getString("userGroup","");
        //获取勾选的状态
        boolean checkbox=sharedPreferences.getBoolean("checkbox",false);
        if(!CheckUtils.isEmpty(userId)){
        }
     }

    //创建一个handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isloginsuccess = (boolean) msg.obj;
            if (isloginsuccess) {
                if(manageCheckBox.isChecked() && !"4".equals(SessionInfo.getUserGroup())){
                    Toast.makeText(getApplicationContext(), "您不是管理员，无法登录!", Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences sp=getSharedPreferences("config",0);
                SharedPreferences.Editor editor=sp.edit();
                //把数据进行保存
                editor.putString("userId",SessionInfo.getUserId());
                editor.putString("userGroup",SessionInfo.getUserGroup());
                //记住勾选的状态
                editor.putBoolean("checkbox",manageCheckBox.isChecked());
                //提交数据
                editor.commit();


                //用户组   级别（1:学生，2:院级管理员3:校级管理员，4:超级管理员）
                if(manageCheckBox.isChecked() && "4".equals(SessionInfo.getUserGroup())){
                } else {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,  TabActivity.class);
                        startActivity(intent);
                }

            } else {
                Toast.makeText(getApplicationContext(), "登录失败,用户名或者密码错误!", Toast.LENGTH_LONG).show();
            }
        }
    };


}
