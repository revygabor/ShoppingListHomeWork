package com.example.gbor.shoppinglisthomework.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gbor.shoppinglisthomework.fragments.PieChartFragment;
import com.example.gbor.shoppinglisthomework.fragments.ShoppingListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter implements PieChartFragment.ContextCratedListener{
    private static final int NUM_PAGES = 2;
    private final ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter();
    private PieChartFragment pieChartFragment = new PieChartFragment();
    private ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
    private final ShoppingListFragment.PieDataSetChangedListener pieDataSetChangedListener;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        shoppingListFragment.setShoppingListAdapter(shoppingListAdapter);
        pieChartFragment.setShoppingListAdapter(shoppingListAdapter);
        pieChartFragment.setContextCreatedListener(this);

        pieDataSetChangedListener = new ShoppingListFragment.PieDataSetChangedListener() {
            @Override
            public void onDataSetChanged() {
                pieChartFragment.drawChart();
            }
        };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return shoppingListFragment;
            case 1:
                return pieChartFragment;
            default:
                return shoppingListFragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public void onContextCreated() {
        shoppingListAdapter.setPieDataSetChangedListener(pieDataSetChangedListener);
        shoppingListFragment.setPieDataSetChangedListener(pieDataSetChangedListener);
    }
}
