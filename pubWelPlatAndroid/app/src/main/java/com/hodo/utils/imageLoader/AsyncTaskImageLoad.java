package com.hodo.utils.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gdszm on 2018/12/13.
 */

public class AsyncTaskImageLoad extends AsyncTask<String, Integer, Bitmap> {
    private ImageView Image=null;
    public AsyncTaskImageLoad(ImageView img)
    {
        Image=img;
    }
    //运行在子线程中
    protected Bitmap doInBackground(String... params) {
        try
        {
            URL url=new URL(params[0]);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            if(conn.getResponseCode()==200)
            {
                InputStream input=conn.getInputStream();
                Bitmap map= BitmapFactory.decodeStream(input);
                return map;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Bitmap result)
    {
        if(Image!=null && result!=null)
        {
            Image.setImageBitmap(result);
        }

        //super.onPostExecute(result);
    }
}
