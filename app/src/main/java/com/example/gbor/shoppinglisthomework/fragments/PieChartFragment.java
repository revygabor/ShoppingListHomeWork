package com.example.gbor.shoppinglisthomework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.adapters.ShoppingListAdapter;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PieChartFragment extends Fragment {
    private PieChart chartGoods = null;
    private ShoppingListAdapter shoppingListAdapter = null;
    private ContextCratedListener contextCratedListener = null;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_piechart, container, false);

        chartGoods = rootView.findViewById(R.id.chartGoods);
        chartGoods.getDescription().setText(getString(R.string.chartDescription));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contextCratedListener.onContextCreated();
    }

    public void drawChart() {
        HashMap<ShoppingItem.Category, Integer> categoryPrices = new HashMap<>();

        if (shoppingListAdapter != null) {
            categoryPrices = shoppingListAdapter.getCategoryPrices();
        }

        List<PieEntry> entries = new ArrayList<>();

        for (ShoppingItem.Category cat : categoryPrices.keySet()) {
            int price = categoryPrices.get(cat);
            if (price > 0) {
                entries.add(new PieEntry(price, cat.toString()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.chartLabelDescription));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        chartGoods.setData(data);
        chartGoods.invalidate();
    }

    public void setShoppingListAdapter(ShoppingListAdapter shoppingListAdapter) {
        this.shoppingListAdapter = shoppingListAdapter;
    }

    public interface ContextCratedListener {
        void onContextCreated();
    }

    public void setContextCreatedListener(ContextCratedListener contextCreatedListener) {
        this.contextCratedListener = contextCreatedListener;
    }
}
