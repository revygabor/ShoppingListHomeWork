package com.example.gbor.shoppinglisthomework.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.adapters.ShoppingListAdapter;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;
import com.example.gbor.shoppinglisthomework.touch.ItemTouchHelperCallback;

public class ShoppingListFragment extends Fragment {
    private RecyclerView recyclerview;
    private ShoppingListAdapter shoppingListAdapter = null;

    public interface PieDataSetChangedListener {
        public void onDataSetChanged();
    }
    private PieDataSetChangedListener pieDataSetChangedListener = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shopping_list_main, container, false);

        shoppingListAdapter.setEditItemListener(new ShoppingListAdapter.onEditItemListener() {
            @Override
            public void onEditItem(int i) {
                createItemDialog(shoppingListAdapter.getItem(i), new editShoppingItemDialogResultListener(i));
            }
        });

        recyclerview = rootView.findViewById(R.id.rvShoppingList);
        recyclerview.setAdapter(shoppingListAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));

        rootView.findViewById(R.id.fabAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItemDialog(null, new newShoppingItemDialogResultListener());
            }
        });

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(shoppingListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerview);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
            filterDialogFragment.show(getActivity().getSupportFragmentManager(), FilterDialogFragment.TAG);
            filterDialogFragment.setTargetAdapter(shoppingListAdapter);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void createItemDialog(ShoppingItem item, NewItemDialogFragment.ShoppingItemListener shoppingItemListener) {
        NewItemDialogFragment fr = new NewItemDialogFragment();
        fr.setShoppingItem(item);
        fr.setListener(shoppingItemListener);
        fr.show(getActivity().getSupportFragmentManager(), NewItemDialogFragment.TAG);
    }

    class newShoppingItemDialogResultListener implements NewItemDialogFragment.ShoppingItemListener {
        @Override
        public void onShoppingItemDialogResult(ShoppingItem newItem) {
            shoppingListAdapter.addItem(newItem);
        }
    }

    class editShoppingItemDialogResultListener implements NewItemDialogFragment.ShoppingItemListener {
        private final int i;

        public editShoppingItemDialogResultListener(int i) {
            this.i = i;
        }

        @Override
        public void onShoppingItemDialogResult(ShoppingItem tempItem) {
            shoppingListAdapter.editItem(i, tempItem);
        }
    }

    public void setShoppingListAdapter(ShoppingListAdapter shoppingListAdapter) {
        this.shoppingListAdapter = shoppingListAdapter;
    }

    public void setPieDataSetChangedListener(PieDataSetChangedListener pieDataSetChangedListener) {
        this.pieDataSetChangedListener = pieDataSetChangedListener;
    }

}
