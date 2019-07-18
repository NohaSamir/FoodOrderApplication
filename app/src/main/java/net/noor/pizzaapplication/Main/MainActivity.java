package net.noor.pizzaapplication.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.noor.pizzaapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * Created by nsamir on 6/25/2019.
 */

/**
 * MainActivity Display the Main view
 * with {@link net.noor.pizzaapplication.Menu.MenuFragment}
 * and 3 Empty views {@link EmptyFragment}
 * using {@link MainPagerAdapter} to bind those views
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.nav_view)
    BottomNavigationView navView;


    /**
     * Setup Bottom navigation
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(0);
                    return true;

                case R.id.navigation_home:
                    viewPager.setCurrentItem(1);
                    return true;

                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    /**
     * On Create method bind the view pager using {@link MainPagerAdapter}
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagerAdapter);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

    }
}
