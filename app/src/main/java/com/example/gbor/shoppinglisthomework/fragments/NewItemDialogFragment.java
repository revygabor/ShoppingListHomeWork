package com.example.gbor.shoppinglisthomework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;

public class NewItemDialogFragment extends DialogFragment {

    public static final String TAG = "NEW_ITEM_DIALOG_FRAGMENT";
    private EditText etPrice;
    private EditText etItemName;
    private EditText etItemDescription;
    private Spinner spinnerCategory;
    private CheckBox cbAlreadyBought;
    private ShoppingItem shoppingItem;

    public interface ShoppingItemListener {
        void onShoppingItemDialogResult(ShoppingItem item);
    }

    public void setListener(ShoppingItemListener listener) {
        this.listener = listener;
    }

    private ShoppingItemListener listener = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.new_item_dialog, container, false);

        Button btnCancel = rootView.findViewById(R.id.btnCancel);
        Button btnOk = rootView.findViewById(R.id.btnOk);

        etItemName = rootView.findViewById(R.id.etItemName);
        etItemDescription = rootView.findViewById(R.id.etItemDescription);
        spinnerCategory = rootView.findViewById(R.id.spinnerCategory);
        ArrayAdapter<ShoppingItem.Category> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, ShoppingItem.Category.values());
        spinnerCategory.setAdapter(spinnerAdapter);

        etPrice = rootView.findViewById(R.id.etPrice);
        cbAlreadyBought = rootView.findViewById(R.id.cbAlreadyBought);

        if (shoppingItem != null) {
        etItemName.setText(shoppingItem.name);
        etItemDescription.setText(shoppingItem.description);
        spinnerCategory.setSelection(spinnerAdapter.getPosition(shoppingItem.category));
        etPrice.setText(Integer.toString(shoppingItem.price));
        cbAlreadyBought.setChecked(shoppingItem.bought);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingItem tempItem = new ShoppingItem();
                tempItem.name = etItemName.getText().toString();
                tempItem.description = etItemDescription.getText().toString();
                tempItem.category = (ShoppingItem.Category) spinnerCategory.getSelectedItem();
                tempItem.price = etPrice.getText().toString().equals("") ? 0 : Integer.parseInt(etPrice.getText().toString());
                tempItem.bought = cbAlreadyBought.isChecked();

                if (listener != null) {
                    listener.onShoppingItemDialogResult(tempItem);
                }

                dismiss();
            }
        });

        return rootView;
    }

    public void setShoppingItem(ShoppingItem item) {
        this.shoppingItem = item;
    }


}