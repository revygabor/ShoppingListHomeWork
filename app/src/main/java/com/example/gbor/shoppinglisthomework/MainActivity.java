package com.example.gbor.shoppinglisthomework;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gbor.shoppinglisthomework.adapters.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vpMain = findViewById(R.id.vpMain);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

    }
}
