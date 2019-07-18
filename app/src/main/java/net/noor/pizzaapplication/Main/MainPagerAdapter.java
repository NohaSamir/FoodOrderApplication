package net.noor.pizzaapplication.Main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.noor.pizzaapplication.Menu.MenuFragment;
import net.noor.pizzaapplication.R;

/*
 * Created by nsamir on 6/25/2019.
 */

/**
 * Main pager adapter display
 * Menu view using {@link net.noor.pizzaapplication.Menu.MenuFragment}
 * and 3 Empty views using{@link EmptyFragment}
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_OF_PAGES = 4;
    private Context mContext;

    MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EmptyFragment.newInstance(mContext.getString(R.string.dashboard));
            case 1:
                return MenuFragment.newInstance();
            case 2:
                return EmptyFragment.newInstance(mContext.getString(R.string.notification));
            default:
                return EmptyFragment.newInstance(mContext.getString(R.string.profile));
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }
}
