package com.hodo.ui.fragement;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hodo.R;
import com.hodo.bean.SessionInfo;
import com.hodo.bean.Usr;
import com.hodo.ui.ActMyReqListActivity_;
import com.hodo.ui.LoginActivity_;
import com.hodo.ui.PartinListActivity_;
import com.hodo.ui.PicLoadActivity_;
import com.hodo.ui.RegActivity_;
import com.hodo.ui.UsrActivity_;
import com.hodo.ui.UsrEditActivity_;
import com.hodo.ui.layout.MyOneLineView;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.ImgUtil;
import com.hodo.utils.net.CommonHttpUtil;
import com.hodo.utils.net.URLProtocol;

public class MyFragment extends BaseFragment implements MyOneLineView.OnRootClickListener, MyOneLineView.OnArrowClickListener  {

    private String userId;
    private String userGroup;

    private LinearLayout llRoot;

    private ImageView mylogo;
    private TextView nameTexView;
    private TextView telTexView;
    private Button editBtn;

    public static MyFragment newInstance(String text){
        MyFragment fragmentCommon=new MyFragment();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshIcon");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        super.onCreate(savedInstanceState);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,container,false);

        Toolbar toolbar_normal = (Toolbar) view.findViewById(R.id.toolbar_normal);
        //toolbar_normal.setTitle("刘涛");
        mylogo = (ImageView) view.findViewById(R.id.icon);
        nameTexView = (TextView) view.findViewById(R.id.name);
        telTexView = (TextView) view.findViewById(R.id.tel);
        editBtn = (Button) view.findViewById(R.id.editBtn);

        mylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PicLoadActivity_.class);
                startActivity(intent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = userId;
                Intent intent = new Intent();
                intent.setClass(getActivity(), UsrEditActivity_.class);
                intent.putExtra("cid",userId);
                getActivity().startActivity(intent);
            }
        });


        llRoot = (LinearLayout)view.findViewById(R.id.ll_root);
        //icon + 文字 + 箭头
        llRoot.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.login, "登录", "", true)
                .setOnRootClickListener(this, 1));

        //icon + 文字 + 箭头
        llRoot.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.reg, "注册", "", true)
                .setOnRootClickListener(this, 2));
        //用户组别  (0:公益组织，1:普通用户，2:管理员)
        if("0".equals(SessionInfo.getUserGroup())) {
            //icon + 文字 + 箭头
            llRoot.addView(new MyOneLineView(getActivity())
                    .initMine(R.mipmap.bag, "我的申请", "", true)
                    .setOnRootClickListener(this, 3));
        }
        if("1".equals(SessionInfo.getUserGroup())) {
            //icon + 文字 + 箭头
            llRoot.addView(new MyOneLineView(getActivity())
                    .initMine(R.mipmap.bag, "我的报名", "", true)
                    .setOnRootClickListener(this, 4));
            //icon + 文字 + 箭头
            llRoot.addView(new MyOneLineView(getActivity())
                    .initMine(R.mipmap.pub, "我的捐款", "", true)
                    .setOnRootClickListener(this, 5));
        }
        //用户组别  (0:公益组织，1:普通用户，2:管理员)
        if("2".equals(SessionInfo.getUserGroup())){
            //icon + 文字 + 箭头
            llRoot.addView(new MyOneLineView(getActivity())
                    .initMine(R.mipmap.addr, "用户管理", "", true)
                    .setOnRootClickListener(this, 6));
        }

        //icon + 文字 + 箭头
        llRoot.addView(new MyOneLineView(getActivity())
                .initMine(R.mipmap.logout, "退出", "", true)
                .setOnRootClickListener(this, 7));
//        //icon + 文字 + 箭头
//        llRoot.addView(new MyOneLineView(getActivity())
//                .initMine(R.drawable.ic_launcher, "笔记本", "", true)
//                .setOnRootClickListener(this, 1));
//
//        //icon + 文字 + 文字 + 箭头
//        llRoot.addView(new MyOneLineView(getActivity())
//                .initMine(R.drawable.ic_launcher, "第二行", "第二行", true)
//                .setOnArrowClickListener(this, 2));
//        //icon + 文字 + 输入框
//        llRoot.addView(new MyOneLineView(getActivity())
//                .initItemWidthEdit(R.drawable.ic_launcher, "第三行", "这是一个输入框")
//                .setRootPaddingTopBottom(20, 20));

        return view;
    }

    @Override
    public void onRootClick(View view) {

        int position = 0;
        switch ((int) view.getTag()) {
            case 1:
                position = 1;
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), LoginActivity_.class);
                startActivity(intent1);
                break;
            case 2:
                position = 2;
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), RegActivity_.class);
                startActivity(intent2);
                break;
            case 3: //我的申请
                position = 3;
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), ActMyReqListActivity_.class);
                //intent.putExtra("cls",flg);
                startActivity(intent3);
                break;

            case 4://我的报名
                position = 4;
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), PartinListActivity_.class);
                intent4.putExtra("usrCid",SessionInfo.getUserId());
                intent4.putExtra("tp","报名");
                startActivity(intent4);
                break;
            case 5://我的捐款
                position = 5;
                Intent intent5= new Intent();
                intent5.setClass(getActivity(), PartinListActivity_.class);
                intent5.putExtra("usrCid",SessionInfo.getUserId());
                intent5.putExtra("tp","捐款");
                startActivity(intent5);
                break;
            case 6:
                position = 6;
                Intent intent6 = new Intent();
                intent6.setClass(getActivity(), UsrActivity_.class);
                //intent.putExtra("cls",flg);
                startActivity(intent6);
                break;
            case 7:
                position = 7;
                SharedPreferences sp=getContext().getSharedPreferences("config",0);
                SharedPreferences.Editor editor=sp.edit();
                //把数据进行保存
                editor.remove("userId");
                editor.remove("userGroup");
                editor.remove("checkbox");
                //提交数据
                editor.commit();

                getActivity().finish();
                System.exit(0);
                break;
        }
        // Toast.makeText(getActivity(), "点击了第" + position + "行", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArrowClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
    }

    @Override
    protected void lazyLoadData() {
        //获取数据
        userId= SessionInfo.getUserId();
        userGroup= SessionInfo.getUserGroup();
        if(!CheckUtils.isEmpty(userId)){
            //获取列表
            CommonHttpUtil.requestNet(handler, URLProtocol.ACTION_GET_USERINO,
                    "cid="+userId);
        } else {
            Toast.makeText(getActivity(), "请登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity_.class);
            startActivity(intent);
        }

//        //在这里加载数据....
//      String url = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+ URLProtocol.ACTION_TAB_MAINCLS;
//      String userId = "";
//        if(CheckUtils.isEmpty(SessionInfo.getUserId())) {
//            //初始化数据
//            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("config",0);
//            //取出数据，如果取出的数据时空时，只需把getString("","")第二个参数设置成空字符串就行了，不用在判断
//            userId=sharedPreferences.getString("userId","");
//        } else {
//            userId = SessionInfo.getUserId();
//        }
//        wv.loadUrl(url+"?cid="+ userId);


    }

    //创建一个handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) msg.obj;
            Boolean isSuccess = jsonObject.getBoolean("success");
            String m = jsonObject.getString("msg");
            if (isSuccess) {
                JSONObject usrObj = jsonObject.getJSONObject("obj");
                Usr usr =  JSONObject.toJavaObject(usrObj,Usr.class);
                if(!CheckUtils.isEmpty(usr)){
                    String name = usr.getCrealname();
                    String tel = usr.getTel();
                    String icon = usr.getIcon();
                    nameTexView.setText(name+"("+usr.getUserGroupName()+")");
                    telTexView.setText(tel);
                    if(!CheckUtils.isEmpty(icon)){
                        String url = URLProtocol.ROOT+":"+ URLProtocol.PORT+"/"+ URLProtocol.PROJECT+"/"+icon;
                        ImgUtil.loadShaderImg(getActivity(),mylogo,url,200,200,20);
                    }
                }
            } else {
                Toast.makeText(getActivity(),m, Toast.LENGTH_LONG).show();
            }
        }
    };

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshIcon"))
            {
                //重新加载数据
                lazyLoadData();
            }
        }
    };
}
