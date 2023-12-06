package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_IS_CHECKED = "isChecked";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEXT
                + " TEXT NOT NULL, " + COLUMN_IS_CHECKED + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
//        onCreate(db);
    }

    public Item saveItem(SQLiteDatabase db, String itemText) {
        Item item = new Item(itemText);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, item.getText());
        cv.put(COLUMN_IS_CHECKED, item.getIsCheckedAsInt());

        long result = db.insert(TABLE, null, cv);
        item.setId(result);
        return item;
    }

    public void updateItem(SQLiteDatabase db, Item item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, item.getText());
        cv.put(COLUMN_IS_CHECKED, item.getIsCheckedAsInt());
        System.out.println(item.getId());
        System.out.println(db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + item.getId(), null));
    }

    public void delete(Item item) {
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(item.getId())});
    }

    public SQLiteDatabase db;
}
