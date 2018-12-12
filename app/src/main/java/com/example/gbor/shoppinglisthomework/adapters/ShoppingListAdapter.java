package com.example.gbor.shoppinglisthomework.adapters;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;
import com.example.gbor.shoppinglisthomework.fragments.ShoppingListFragment;
import com.example.gbor.shoppinglisthomework.touch.TouchHelperNotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.gbor.shoppinglisthomework.MainActivity.database;

public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> implements TouchHelperNotifier {

    private final List<ShoppingItem> items;
    private ShoppingListFragment.PieDataSetChangedListener pieDataSetChangedListener = null;
    private boolean dataSetLoaded = false;

    @Override
    public void onItemDismissed(int position) {
        removeItem(position);
    }


    public interface onEditItemListener {
        void onEditItem(int i);
    }

    private onEditItemListener editItemListener;

    ShoppingListAdapter() {
        items = new ArrayList<>();
        loadItemsInBackground();
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.shopping_list_item, viewGroup, false);
        return new ShoppingListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListViewHolder shoppingListViewHolder, final int i) {
        final ShoppingItem item = items.get(i);

        shoppingListViewHolder.tvShoppingItemName.setText(item.name);
        shoppingListViewHolder.tvDescription.setText(item.description);
        shoppingListViewHolder.tvPrice.setText(String.format("%d Ft", item.price));
        shoppingListViewHolder.tvCategory.setText(item.category.toString().toLowerCase());
        shoppingListViewHolder.cbBought.setChecked(item.bought);

        shoppingListViewHolder.cbBought.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.bought = shoppingListViewHolder.cbBought.isChecked();
                ondatabaseItemChanged(item, shoppingListViewHolder.getAdapterPosition());
            }
        });

        shoppingListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemListener.onEditItem(shoppingListViewHolder.getAdapterPosition());
            }
        });

        if (appliedFilter != null) {
            boolean shouldShow = appliedFilter.shouldShow(item);
            shoppingListViewHolder.itemView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
            if (!shouldShow)
                shoppingListViewHolder.itemView.getLayoutParams().height = 0;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        onItemCreated(item);
    }

    public void editItem(int i, ShoppingItem editedItem) {
        ShoppingItem shoppingItemToEdit = items.get(i);
        shoppingItemToEdit.name = editedItem.name;
        shoppingItemToEdit.description = editedItem.description;
        shoppingItemToEdit.category = editedItem.category;
        shoppingItemToEdit.price = editedItem.price;
        shoppingItemToEdit.bought = editedItem.bought;

        ondatabaseItemChanged(shoppingItemToEdit, i);
    }

    private void removeItem(int position) {
        onItemRemoved(position);
    }

    private void loadItems(List<ShoppingItem> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
        dataSetLoaded = true;

        if (pieDataSetChangedListener != null) {
            pieDataSetChangedListener.onDataSetChanged();
        }
    }

    public ShoppingItem getItem(int i) {
        return items.get(i);
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView tvShoppingItemName;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvCategory;
        CheckBox cbBought;
        View itemView;

        ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShoppingItemName = itemView.findViewById(R.id.tvShoppingItemName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            cbBought = itemView.findViewById(R.id.cbBought);
            this.itemView = itemView;
        }
    }

    public HashMap<ShoppingItem.Category, Integer> getCategoryPrices() {
        HashMap<ShoppingItem.Category, Integer> categoryPrices = new HashMap<>();
        for (ShoppingItem.Category category : ShoppingItem.Category.values()) {
            categoryPrices.put(category, 0);
        }

        for (ShoppingItem item : items) {
            if (item.bought) {
                categoryPrices.put(item.category, categoryPrices.get(item.category) + item.price);
            }
        }

        return categoryPrices;
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ShoppingItem>>() {

            @Override
            protected List<ShoppingItem> doInBackground(Void... voids) {
                return database.shoppingItemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<ShoppingItem> shoppingItems) {
                Log.d("Database", "ShoppingItems are successfully loaded");
                loadItems(shoppingItems);
            }
        }.execute();
    }

    public void ondatabaseItemChanged(final ShoppingItem item, final int position) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.shoppingItemDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("Database", "ShoppingItem update was successful");
                notifyItemChanged(position);
                pieDataSetChangedListener.onDataSetChanged();
            }
        }.execute();
    }

    public void onItemCreated(final ShoppingItem newItem) {
        new AsyncTask<Void, Void, ShoppingItem>() {

            @Override
            protected ShoppingItem doInBackground(Void... voids) {
                newItem.id = database.shoppingItemDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(ShoppingItem shoppingItem) {
                Log.d("Database", "New ShoppingItem saved successful");
                notifyItemInserted(items.size() - 1);
                pieDataSetChangedListener.onDataSetChanged();
            }
        }.execute();
    }

    public void onItemRemoved(final int position) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                database.shoppingItemDao().deleteItem(items.get(position));
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("Database", "ShoppingItem delete was successful");
                items.remove(position);
                notifyItemRemoved(position);
                pieDataSetChangedListener.onDataSetChanged();
            }
        }.execute();
    }

    public void setEditItemListener(onEditItemListener editItemListener) {
        this.editItemListener = editItemListener;
    }

    public void setPieDataSetChangedListener(ShoppingListFragment.PieDataSetChangedListener pieDataSetChangedListener) {
        this.pieDataSetChangedListener = pieDataSetChangedListener;
        if (dataSetLoaded) {
            pieDataSetChangedListener.onDataSetChanged();
        }
    }

    public static class ShoppingListFilter {
        String _name;
        boolean nameFull;
        int _minPrice;
        double _maxPrice;
        ShoppingItem.Category _category;

        public ShoppingListFilter(String _name, boolean nameFull, int _minPrice, double _maxPrice, ShoppingItem.Category _category) {
            this._name = _name;
            this.nameFull = nameFull;
            this._minPrice = _minPrice;
            this._maxPrice = _maxPrice;
            this._category = _category;
        }

        boolean shouldShow(ShoppingItem item) {
            return
                    (_name == null || (nameFull ? _name.equals(item.name) : item.name.contains(_name)))
                            && item.price >= _minPrice && item.price <= _maxPrice
                            && (_category == null || _category == item.category);
        }
    }

    private ShoppingListFilter appliedFilter = null;

    public void applyFilter(ShoppingListFilter filter) {
        appliedFilter = filter;
        notifyDataSetChanged();
    }
}
