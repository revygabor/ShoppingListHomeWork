package com.example.gbor.shoppinglisthomework;

import android.arch.persistence.room.Room;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gbor.shoppinglisthomework.adapters.MainPagerAdapter;
import com.example.gbor.shoppinglisthomework.data.ShoppingListDatabase;

public class MainActivity extends AppCompatActivity {

    public static ShoppingListDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (database == null) {
            database = Room.databaseBuilder(
                    getApplicationContext(),
                    ShoppingListDatabase.class,
                    "shopping-list"
            ).build();
        }

        ViewPager vpMain = findViewById(R.id.vpMain);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

    }
}
