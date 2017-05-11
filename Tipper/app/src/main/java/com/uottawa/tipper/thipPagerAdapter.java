package com.uottawa.tipper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by filipslatinac on 2017-05-10.
 */

public class thipPagerAdapter extends FragmentPagerAdapter {

    public thipPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BillAmntFragment();
        }
        else if (position == 1){
            return new TipAmntFragment();
        }

        else {
            return new NumberOfPeopleFragment();
        }
    }


}