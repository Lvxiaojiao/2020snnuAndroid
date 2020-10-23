package com.hodo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hodo.R;
import com.hodo.bean.SessionInfo;
import com.hodo.bean.TabEntity;
import com.hodo.ui.fragement.ActFragment;
import com.hodo.ui.fragement.MyActFragment;
import com.hodo.ui.fragement.MyFragment;
import com.hodo.ui.fragement.MyPartInFragment;
import com.hodo.ui.fragement.MyTodoFragment;
import com.hodo.utils.ViewFindUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabActivity extends AppCompatActivity {
    private Context mContext = this;
    private List<Fragment> mFragments = new ArrayList<>();

    private List<String> titleList = new ArrayList<String>();
    private List<Integer> unselectList = new ArrayList<Integer>();
    private List<Integer> selectList = new ArrayList<Integer>();



    private Object[] mTitles = { };


    private Object[] mIconUnselectIds = {
    };
    private Object[] mIconSelectIds = {

    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        titleList.add("活动大厅");
        unselectList.add(R.mipmap.tab_home_unselect);
        selectList.add(R.mipmap.tab_home_select);
        mFragments.add(ActFragment.getInstance("活动大厅"));
        //用户组别 (0:公益组织，1:志愿者，2:管理员)
        if("1".equals(SessionInfo.getUserGroup())) {
            titleList.add("我的参与");
            unselectList.add(R.mipmap.tab_mypartin_unselect);
            selectList.add(R.mipmap.tab_mypartin_select);
            mFragments.add(MyPartInFragment.getInstance("我的参与"));
        } else  if("2".equals(SessionInfo.getUserGroup())) {
            titleList.add("我的待办");
            unselectList.add(R.mipmap.tab_mytodo_unselect);
            selectList.add(R.mipmap.tab_mytodo_select);
            mFragments.add(MyTodoFragment.getInstance("我的待办"));
        } else  if("0".equals(SessionInfo.getUserGroup())) {
            titleList.add("我的活动");
            unselectList.add(R.mipmap.tab_myact_unselect);
            selectList.add(R.mipmap.tab_myact_select);
            mFragments.add(MyActFragment.getInstance("我的活动"));
        } else {

        }
        titleList.add("设置");
        mTitles=titleList.toArray();
        unselectList.add(R.mipmap.tab_config_unselect);
        mIconUnselectIds=unselectList.toArray();
        selectList.add(R.mipmap.tab_config_select);
        mIconSelectIds=selectList.toArray();
        mFragments.add(MyFragment.newInstance("设置"));



        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i].toString(),Integer.parseInt(mIconSelectIds[i].toString()), Integer.parseInt(mIconUnselectIds[i].toString())));
        }

        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        /** with ViewPager */
        mTabLayout_2 = ViewFindUtils.find(mDecorView, R.id.tl_2);
        tl_2();

        /**
         *红点设置
         */
        //两位数
//        mTabLayout_2.showMsg(0, 55);
//        mTabLayout_2.setMsgMargin(0, -5, 5);

        //三位数
//        mTabLayout_2.showMsg(1, 100);
//        mTabLayout_2.setMsgMargin(1, -5, 5);

        //设置未读消息红点
//        mTabLayout_2.showDot(2);
//        MsgView rtv_2_2 = mTabLayout_2.getMsgView(2);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
//        }

//        //设置未读消息背景
//        mTabLayout_2.showMsg(3, 5);
//        mTabLayout_2.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = mTabLayout_2.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }

        /**
         *红点设置
         */
    }

    Random mRandom = new Random();

    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //当前tab
        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position].toString();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
