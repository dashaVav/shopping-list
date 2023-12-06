package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ItemDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_IS_CHECKED = "isChecked";
    private final SQLiteDatabase db;

    public ItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEXT
                + " TEXT NOT NULL, " + COLUMN_IS_CHECKED + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Item saveItem(String itemText) {
        Item item = new Item(itemText);
        long result = db.insert(TABLE, null, createContentValuesFromItem(item));
        item.setId(result);
        return item;
    }

    private ContentValues createContentValuesFromItem(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, item.getText());
        cv.put(COLUMN_IS_CHECKED, item.getIsCheckedAsInt());
        return cv;
    }

    public void updateItem(Item item) {
        db.update(ItemDatabase.TABLE, createContentValuesFromItem(item),
                ItemDatabase.COLUMN_ID + "=" + item.getId(), null);
    }

    public void delete(Item item) {
        db.delete(ItemDatabase.TABLE, "_id = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public ArrayList<Item> readFromDatabase() {
        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor;
        cursor = db.rawQuery("select * from " + ItemDatabase.TABLE, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            items.add(new Item(cursor.getLong(0), cursor.getString(1),
                    cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();

        return items;
    }
}
