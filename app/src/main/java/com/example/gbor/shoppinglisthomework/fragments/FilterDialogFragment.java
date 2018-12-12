package com.example.gbor.shoppinglisthomework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.gbor.shoppinglisthomework.R;
import com.example.gbor.shoppinglisthomework.adapters.ShoppingListAdapter;
import com.example.gbor.shoppinglisthomework.data.ShoppingItem;

public class FilterDialogFragment extends AppCompatDialogFragment {

    private ShoppingListAdapter targetAdapter = null;
    public static final String TAG = "FILTER_DIALOG_FRAGMENT";

    public void setTargetAdapter(ShoppingListAdapter newAdapter) {
        targetAdapter = newAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.filter_dialog, null);


        ArrayAdapter<ShoppingItem.Category> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, ShoppingItem.Category.values());
        final Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerCategory.setAdapter(spinnerAdapter);

        ((CheckBox) view.findViewById(R.id.filterByCategory)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spinnerCategory.setVisibility(isChecked ? View.VISIBLE : View.GONE);

            }
        });

        SeekBar sbMinPrice = view.findViewById(R.id.sbMinPrice);
        SeekBar sbMaxPrice = view.findViewById(R.id.sbMaxPrice);

        ((Button) view.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = view.findViewById(R.id.etName);
                CheckBox cbName = view.findViewById(R.id.cbNameFull);
                SeekBar sbMinPrice = view.findViewById(R.id.sbMinPrice);
                SeekBar sbMaxPrice = view.findViewById(R.id.sbMaxPrice);
                CheckBox filterByCategory = view.findViewById(R.id.filterByCategory);
                Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);

                if (targetAdapter != null) {
                    targetAdapter.applyFilter(new ShoppingListAdapter.ShoppingListFilter(
                            etName.getText().toString().isEmpty() ? null : etName.getText().toString(),
                            cbName.isChecked(),
                            sbMinPrice.getProgress(),
                            sbMaxPrice.getProgress(),
                            filterByCategory.isChecked() ? ((ShoppingItem.Category) spinnerCategory.getSelectedItem()) : null
                    ));
                }

                dismiss();
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }


}
