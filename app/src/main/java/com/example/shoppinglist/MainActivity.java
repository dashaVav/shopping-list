package com.example.shoppinglist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ItemAdapter mAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        setContentView(R.layout.main_activity);

        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Item> initialContent = readFromDatabase();
        mAdapter = new ItemAdapter(initialContent, this::showEditItemInput);
        mRecyclerView.setAdapter(mAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> showAddItemInput());
    }

    private ArrayList<Item> readFromDatabase() {
        db = databaseHelper.getReadableDatabase();
        databaseHelper.db = db;
        ArrayList<Item> items = new ArrayList<>();

        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        userCursor.moveToFirst();

        while (!userCursor.isAfterLast()) {
            items.add(new Item(userCursor.getLong(0), userCursor.getString(1), userCursor.getString(2)));
            userCursor.moveToNext();
        }
        userCursor.close();

        return items;
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
                    databaseHelper.updateItem(db, mAdapter.updateItemText(position, editedItemName));
                } else {
                    mAdapter.addItem(databaseHelper.saveItem(db, editedItemName));
                }
            }
        });

        builder.setNegativeButton(R.string.cancel_button, (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }
}