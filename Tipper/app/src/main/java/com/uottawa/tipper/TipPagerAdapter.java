package com.uottawa.tipper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by filipslatinac on 2017-05-18.
 */

public class TipPagerAdapter extends FragmentPagerAdapter {

    public TipPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new customPercentageFragment();

        }

        else {
            return new ratingFragment();
        }

    }

}
