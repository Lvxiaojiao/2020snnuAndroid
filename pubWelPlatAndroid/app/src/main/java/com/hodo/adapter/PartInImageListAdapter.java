package com.hodo.adapter;

/**
 * Created by gdszm on 2019/3/29.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hodo.R;
import com.hodo.bean.Partin;
import com.hodo.utils.CheckUtils;
import com.hodo.utils.ImgUtil;
import com.hodo.utils.net.URLProtocol;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BitmapCallback;

import java.util.List;

import okhttp3.Response;

/**
 * Created by prize on 2018/4/11.
 */

public class PartInImageListAdapter extends ArrayAdapter<Partin> {
    private int recourceId;
    /*
    ImageListAdapter( Context context,  int resource,  List<ImageListArray> objects)解析
    Context context ：当前类或者当前类的Context上下文
    int resource  ：ListView的一行布局，它将会导入到适配器中与数据自动适配
    List<ImageListArray> objects ：数据的List集合
     */
    public PartInImageListAdapter(Context context, int resource, List<Partin> objects) {
        super(context, resource, objects);
        recourceId = resource;

    }

    @NonNull
    @Override
    /*
    为什么要重写getView？因为适配器中其实自带一个返回布局的方法，
    这个方法可以是自定义适配一行的布局显示，因为我们需要更复杂的布局内容，
    所以我们直接重写它，，不需要在导入一个简单的TextView或者ImageView布局让适配器在写入布局数据。
    所以在recourceId自定义布局id直接导入到getView里面，getView方法不在convertView中获取布局了。
    最后只要返回一个view布局就可以。
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Partin imageListArray = getItem(position); //得到集合中指定位置的一组数据，并且实例化
        View view = LayoutInflater.from(getContext()).inflate(recourceId,parent,false); //用布局裁剪器(又叫布局膨胀器)，将导入的布局裁剪并且放入到当前布局中

        TextView nm = (TextView)view.findViewById(R.id.nm); //从裁剪好的布局里获取TextView布局Id
        nm.setText(imageListArray.getNm());//将当前一组imageListArray类中的TextView内容导入到TextView布局中

        TextView crealname = (TextView)view.findViewById(R.id.crealname); //从裁剪好的布局里获取TextView布局Id
        crealname.setText(imageListArray.getCrealname());//将当前一组imageListArray类中的TextView内容导入到TextView布局中

        TextView tp = (TextView)view.findViewById(R.id.tp); //从裁剪好的布局里获取TextView布局Id
        tp.setText(imageListArray.getTp());//将当前一组imageListArray类中的TextView内容导入到TextView布局中

        TextView dtString = (TextView)view.findViewById(R.id.dtString); //从裁剪好的布局里获取TextView布局Id
        dtString.setText(imageListArray.getDtString());//将当前一组imageListArray类中的TextView内容导入到TextView布局中

        TextView amt = (TextView)view.findViewById(R.id.amt); //从裁剪好的布局里获取TextView布局Id
        if(imageListArray.getAmt()==null || CheckUtils.isEmpty(imageListArray.getAmt())){
            amt.setText("");//将当前一组imageListArray类中的TextView内容导入到TextView布局中
        } else {
            amt.setText(String.valueOf(imageListArray.getAmt()));//将当前一组imageListArray类中的TextView内容导入到TextView布局中
        }
        return view;
    }



    private void loadImg(final ImageView imageView,String url) {
//        String url1 = "https://avatar.csdn.net/5/A/5/3_yzj_0722.jpg";
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


}