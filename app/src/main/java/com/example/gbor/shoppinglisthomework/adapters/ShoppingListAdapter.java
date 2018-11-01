package com.example.gbor.shoppinglisthomework.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private final List<ShoppingItem> items;


    public interface onEditItemListener {
        void onEditItem(int i);
    }
    private  onEditItemListener editItemListener;


    public ShoppingListAdapter() {
        items = new ArrayList<>();
        ShoppingItem item = new ShoppingItem();
        for(int i = 0; i<5; i++) {
            item = new ShoppingItem();
            item.bought = false;
            item.name = "itemm" + Integer.toString(i);
            item.description = "descr";
            item.price = 1000;
            item.category = ShoppingItem.Category.BOOK;
            items.add(item);
        }
        item.category = ShoppingItem.Category.ELECTRICAL;
        items.add(item);
        items.add(item);
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
    public void onBindViewHolder(@NonNull ShoppingListViewHolder shoppingListViewHolder, final int i) {
        final ShoppingItem item = items.get(i);

        shoppingListViewHolder.tvShoppingItemName.setText(item.name);
        shoppingListViewHolder.tvDescription.setText(item.description);
        shoppingListViewHolder.tvPrice.setText(String.format("%d Ft", item.price));
        shoppingListViewHolder.tvCategory.setText(item.category.toString().toLowerCase());
        shoppingListViewHolder.cbBought.setChecked(item.bought);

        shoppingListViewHolder.cbBought.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.bought = isChecked;
            }
        });

        shoppingListViewHolder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemListener.onEditItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        notifyItemInserted(items.size()-1);
    }

    public void editItem(int i, ShoppingItem editedItem) {
        ShoppingItem shoppingItemToEdit = items.get(i);
        shoppingItemToEdit.name = editedItem.name;
        shoppingItemToEdit.description = editedItem.description;
        shoppingItemToEdit.category = editedItem.category;
        shoppingItemToEdit.price = editedItem.price;
        shoppingItemToEdit.bought = editedItem.bought;
        notifyItemChanged(i);
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
        View btEdit;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShoppingItemName = itemView.findViewById(R.id.tvShoppingItemName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            cbBought = itemView.findViewById(R.id.cbBought);
            btEdit = itemView.findViewById(R.id.btEdit);
        }
    }

    public void setEditItemListener(onEditItemListener editItemListener) {
        this.editItemListener = editItemListener;
    }

    public HashMap<ShoppingItem.Category, Integer> getCategoryPrices() {
        HashMap<ShoppingItem.Category, Integer> categoryPrices = new HashMap<>();
        for (ShoppingItem.Category category : ShoppingItem.Category.values()) {
            categoryPrices.put(category, 0);
        }

        for (ShoppingItem item : items) {
            categoryPrices.put(item.category, categoryPrices.get(item.category) + item.price);
        }

        return categoryPrices;
    }
}
