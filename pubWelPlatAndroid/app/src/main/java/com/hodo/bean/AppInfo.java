package com.hodo.bean;

/**
 * Created by gdszm on 2019/3/29.
 */

public class AppInfo {
    private int appImg;
    private String appName;
    private String flg;
    public AppInfo(int appImg, String appName,String flg) {
        this.appImg = appImg;
        this.appName = appName;
        this.flg = flg;
    }

    public int getAppImg() {
        return appImg;
    }

    public void setAppImg(int appImg) {
        this.appImg = appImg;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }
}
