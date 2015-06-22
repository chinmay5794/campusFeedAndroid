package net.blowhorn.campusfeed.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.Adapters.MainTabsAdapter;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 11/6/15.
 */
public class HomeActivity extends FragmentActivity implements android.app.ActionBar.TabListener, ViewPager.OnPageChangeListener {

    MainTabsAdapter mTabsAdapter;
    ViewPager mViewPager;
    android.app.ActionBar actionBar;

    private String tabNames[] = {"FEED","FOLLOWED CHANNELS","ALL CHANNELS","SETTINGS"};
    //TODO:change it to icons later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTabsAdapter = new MainTabsAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTabsAdapter);

        actionBar = getActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);


        for (int i = 0; i < 4; i++) { //TODO:remove hardcoded value 4, set icons later!
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabNames[i])
                            .setTabListener(this));
        }

        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
