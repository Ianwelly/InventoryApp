package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kezia on 11/07/2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    //Name of database
    public final static String DATABASE_NAME = "inventory.db";

    //Database version
    public final static int DATABASE_VERSION = 1;

    //Constructor


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE" +
                InventoryContract.InventoryEntry.TABLE_NAME  + "( "
                + InventoryContract.InventoryEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + InventoryContract.InventoryEntry.COLUMN_NAME + "TEXT NOT NULL"
        + InventoryContract.InventoryEntry.COLUMN_QUANTITY + "INTEGER NOT NULL DEFAULT 0"
                + InventoryContract.InventoryEntry.COLUMN_PRICE + "INTEGER NOT NULL DEFAULT 0 );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
