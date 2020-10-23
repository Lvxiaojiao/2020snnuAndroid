package com.hodo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.hodo.R;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.ChgPwdHttpUtil;
import com.hodo.utils.net.RegHttpUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


@EActivity(R.layout.forgetpwd)
public class ForgetPwdActivity extends AppCompatActivity {

    @ViewById(R.id.pwdGet)
    EditText pwdGetEt;

    @Click(R.id.checkCodeGetBtn)
    void checkCodeGet(){
        String cpwd = pwdGetEt.getText().toString();
        if(CheckUtils.isEmpty(cpwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送验证码
        this.sendCode(this);
       // SentCodeHttpUtil.requestNetForPOSTSetCode(handler, phoneNo);

    }
//    @Click(R.id.getBtn)
//    void getBtnClick() {
//        String phoneNo = phoneNoEt.getText().toString();
//        String cpwd = pwdGetEt.getText().toString();
//        String checkCode = checkCodeGetEt.getText().toString();
//        if(CheckUtils.isEmpty(phoneNo)) {
//            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(CheckUtils.isEmpty(cpwd)) {
//            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(CheckUtils.isEmpty(checkCode)) {
//            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        //post方法登录是否成功
//        ChgPwdHttpUtil.requestNetForPOSTChgPwd(handler, phoneNo,cpwd,checkCode);
//    }

    //创建一个handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isRegsuccess = (boolean) msg.obj;
            if (isRegsuccess) {
                Toast.makeText(getApplicationContext(), "修改密码成功!", Toast.LENGTH_LONG).show();

                SharedPreferences sp=getSharedPreferences("config",0);
                SharedPreferences.Editor editor=sp.edit();
                //把数据进行保存
                editor.remove("userId");
                editor.commit();

                Intent intent = new Intent();
                intent.setClass(ForgetPwdActivity.this, TabActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "修改密码失败!", Toast.LENGTH_LONG).show();
            }
        }
    };

    //验证码通过
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    String cpwd = pwdGetEt.getText().toString();
                    Toast.makeText(getApplicationContext(), "发送成功！", Toast.LENGTH_LONG).show();
                    ChgPwdHttpUtil.requestNetForPOSTChgPwd(handler, phone,cpwd);
                } else{
                    Toast.makeText(getApplicationContext(), "发送失败!", Toast.LENGTH_LONG).show();
                }
            }
        });
        page.show(context);
    }

}
