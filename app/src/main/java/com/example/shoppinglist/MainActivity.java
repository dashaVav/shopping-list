package com.example.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ItemAdapter(this::showEditItemInput,
                new ItemDatabase(getApplicationContext()));
        mRecyclerView.setAdapter(mAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> showAddItemInput());
    }

    private void showAddItemInput() {
        showItemInput(false, -1);
    }

    private void showEditItemInput(int position) {
        showItemInput(true, position);
    }

    private void showItemInput(boolean isEdit, int position) {
        String title = isEdit ? getString(R.string.edit_item_title)
                : getString(R.string.add_new_item_title);
        String currentItemText = isEdit ? mAdapter.getItemText(position) : "";

        View view = LayoutInflater.from(this).inflate(R.layout.input_item, null);
        EditText editTextNewItem = view.findViewById(R.id.editTextNewItem);
        editTextNewItem.setText(currentItemText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle(title);

        builder.setPositiveButton(R.string.save_button, (dialog, which) -> {
            String editedItemName = editTextNewItem.getText().toString().trim();
            if (!editedItemName.isEmpty()) {
                if (isEdit) {
                    mAdapter.updateItemText(position, editedItemName);
                } else {
                    mAdapter.addItem(editedItemName);
                }
            }
        });

        builder.setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}