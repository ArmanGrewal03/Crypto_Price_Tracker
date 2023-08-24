package com.example.cryptopricetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*problems:
- app needs to be restarded for watchlist to update
- search bar for all coins messes up the +watchlist stuff
 */
public class MainActivity extends AppCompatActivity implements AllCoinFragment.RefreshInterface {


    private TabLayout tabs;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    private WatchListFrag watchListFragment;

    @Override
    public void refreshAdapterFragmentB(){
        if (watchListFragment != null) {
            watchListFragment.refreshAdapter(); // Call the refreshAdapter method in WatchListFrag
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabs=findViewById(R.id.TABS);
        viewPager2=findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        watchListFragment = (WatchListFrag) getSupportFragmentManager().findFragmentByTag("watchlist_fragment_tag");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabs.getTabAt(position).select();
            }
        });



    }






}
