package com.lgb.accelerate.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linguobiao on 16/8/19.
 */
public class ViewPagerAdapter extends PagerAdapter {

    List<View> list = new ArrayList<>();

    public void setList(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 初始化position位置的界面
    @Override
    public Object instantiateItem(View v, int position) {
        ((ViewPager) v).addView(list.get(position));

        return list.get(position);
    }

    // 销毁position位置的界面
    @Override
    public void destroyItem(View v, int position, Object arg2) {
        ((ViewPager) v).removeView(list.get(position));

    }

    @Override
    public void finishUpdate(View arg0) {

    }

    @Override
    public void startUpdate(View arg0) {

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
