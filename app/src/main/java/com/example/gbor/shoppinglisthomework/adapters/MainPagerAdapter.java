package com.example.gbor.shoppinglisthomework.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gbor.shoppinglisthomework.fragments.PieChartFragment;
import com.example.gbor.shoppinglisthomework.fragments.ShoppingListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;
    private final ShoppingListAdapter shoppingListAdapter;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        shoppingListAdapter = new ShoppingListAdapter();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                shoppingListFragment.setShoppingListAdapter(shoppingListAdapter);
                return shoppingListFragment;
            case 1:
                PieChartFragment pieChartFragment = new PieChartFragment();
                pieChartFragment.setShoppingListAdapter(shoppingListAdapter);
                return pieChartFragment;
            default:
                ShoppingListFragment shoppingListFragment1 = new ShoppingListFragment();
                shoppingListFragment1.setShoppingListAdapter(shoppingListAdapter);
                return shoppingListFragment1;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
