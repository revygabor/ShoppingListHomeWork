package com.example.gbor.shoppinglisthomework.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gbor.shoppinglisthomework.fragments.PieChartFragment;
import com.example.gbor.shoppinglisthomework.fragments.ShoppingListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;
    private final ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter();
    ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
    PieChartFragment pieChartFragment = new PieChartFragment();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        shoppingListFragment.setShoppingListAdapter(shoppingListAdapter);
        pieChartFragment.setShoppingListAdapter(shoppingListAdapter);

        ShoppingListFragment.DataSetChangedListener dataSetChangedListener = new ShoppingListFragment.DataSetChangedListener() {
            @Override
            public void onDataSetChanged() {
                pieChartFragment.drawChart();
            }
        };
        shoppingListAdapter.setDataSetChangedListener(dataSetChangedListener);
        shoppingListFragment.setDataSetChangedListener(dataSetChangedListener);

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
}
