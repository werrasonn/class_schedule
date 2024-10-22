package com.example.classschedule;

import  androidx.fragment.app.FragmentPagerAdapter;
import  androidx.fragment.app.Fragment;
import  androidx.fragment.app.FragmentManager;




import java.util.ArrayList;

/**
 * Created by pi on 10/27/17.
 */

public class schedulePager extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();

    public schedulePager(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    //ADD PAGE
    public void addFragment(Fragment f)
    {
        fragments.add(f);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return  "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";

            default:
                return "Ollo";
        }
    }
}
