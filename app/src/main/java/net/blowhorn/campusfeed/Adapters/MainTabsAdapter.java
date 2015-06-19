package net.blowhorn.campusfeed.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.blowhorn.campusfeed.Fragments.AllChannelsFragment;
import net.blowhorn.campusfeed.Fragments.FeedFragment;
import net.blowhorn.campusfeed.Fragments.MyChannelsFragment;
import net.blowhorn.campusfeed.Fragments.MiscellaneousFragment;

/**
 * Created by chinmay on 17/6/15.
 */
public class MainTabsAdapter extends FragmentPagerAdapter {

    private int NO_OF_FRAGMENTS = 4;  //TODO:remove hardcoding

    public MainTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retFragment = null;
        switch (position){
            case 0:
                retFragment = new FeedFragment();
                break;
            case 1:
                retFragment = new MyChannelsFragment();
                break;
            case 2:
                retFragment = new AllChannelsFragment();
                break;
            case 3:
                retFragment = new MiscellaneousFragment();
                break;
        }
        return retFragment;
    }

    @Override
    public int getCount() {
        return NO_OF_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
