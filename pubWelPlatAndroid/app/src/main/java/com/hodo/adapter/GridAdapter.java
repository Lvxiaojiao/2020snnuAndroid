package com.hodo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hodo.R;
import com.hodo.bean.AppInfo;

import java.util.List;

/**
 * Created by gdszm on 2019/3/29.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<AppInfo> appList;
    private ImageView imageView;
    private TextView textView;

    public GridAdapter(Context context,List<AppInfo> appList){
        this.context = context;
        this.appList = appList;
    }
    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo app = appList.get(position);
        convertView = View.inflate(context, R.layout.grid_item,null);
        imageView = (ImageView) convertView.findViewById(R.id.appImg);
        textView = (TextView) convertView.findViewById(R.id.appName);
        imageView.setImageResource(app.getAppImg());
        textView.setText(app.getAppName());
        return convertView;
    }
}