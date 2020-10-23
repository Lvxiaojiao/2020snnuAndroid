package com.hodo.ui.fragement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hodo.R;
import com.hodo.adapter.ActMyPartInImageListAdapter;
import com.hodo.bean.Act;
import com.hodo.bean.Json;
import com.hodo.bean.SessionInfo;
import com.hodo.ui.ActDetailActivity_;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.net.AsyncUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


@SuppressLint("ValidFragment")
public class MyPartInFragment extends BaseFragment  {
    private String mTitle;
    private WebView wv;
    private List<Act> list = new ArrayList<Act>();
    private ListView listView;

    private String key = "";

    private String crealname  = "";

    public static MyPartInFragment getInstance(String title) {
        MyPartInFragment sf = new MyPartInFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_partin_tab_act, null);

        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        Button searchBtn = (Button) v.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = searchBox.getText().toString();
                if(!CheckUtils.isEmpty(content)){
                    key=content;
                    lazyLoadData();
                } else {
                    key="";
                    lazyLoadData();
                }
            }
        });

        listView = (ListView) v.findViewById(R.id.ImageListView);

        return v;
    }
    @Override
    protected void lazyLoadData() {

        //获取数据
        String  usrCid= SessionInfo.getUserId();
        String userGroup= SessionInfo.getUserGroup();

        RequestParams rp=new RequestParams();
        rp.put("usrCid",usrCid);
        rp.put("key",key);
        rp.put("crealname",crealname);
        AsyncUtil.post("/act/myPartInActListA", rp, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonData = new String(responseBody);
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(jsonData);
                    Json json =JSON.parseObject(jsonData, Json.class);
                    Boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("obj");
                        list = com.alibaba.fastjson.JSONObject.parseArray(jsonArray.toJSONString(), Act.class);
                        for (Act q:list){
                            //Toast.makeText(getContext(),q.getCid(),Toast.LENGTH_LONG).show();
                        }
                        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
                        ActMyPartInImageListAdapter imageListAdapter = new ActMyPartInImageListAdapter(getActivity(),R.layout.act_my_partin_imagelist_item,list);

                        listView.setAdapter(imageListAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Act ia=list.get(position);
                                String cid = ia.getCid();
                                String nm = ia.getNm();
                                String isReport = ia.getIsReport();
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), ActDetailActivity_.class);
                                intent.putExtra("cid",cid);
                                intent.putExtra("nm",nm);
                                intent.putExtra("isReport",isReport);
                                startActivity(intent);
                            }
                        });
                        //loadImg(imageView,"");
                    }else {
                        Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(),"获取符合条件的数据失败",Toast.LENGTH_LONG).show();
                //显示错误提示页面 --没有网络链接，错误等
            }
        });

    }

}