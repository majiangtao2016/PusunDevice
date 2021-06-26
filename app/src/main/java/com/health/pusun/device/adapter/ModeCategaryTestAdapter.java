package com.health.pusun.device.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.health.pusun.device.ModeSelectActivity;
import com.health.pusun.device.R;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

public class ModeCategaryTestAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final int PAGER_COUNT = 2;
    private List<Fragment> fragments;

    public ModeCategaryTestAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        mContext = context;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ModeSelectActivity.PAGE_ONE:
                fragment = fragments.get(0);
                StatusBarUtil.setColor((Activity) mContext, mContext.getResources().getColor(R.color.transparent), 0);

                break;
            case ModeSelectActivity.PAGE_TWO:
                fragment = fragments.get(1);
                StatusBarUtil.setColor((Activity) mContext, mContext.getResources().getColor(R.color.transparent), 0);

                break;

        }

        return fragment;
    }

}
