package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by Kezia on 11/07/2017.
 */

public class InventoryContract {

    //Empty constructor
    private InventoryContract() {
    }


    public final static class InventoryEntry implements BaseColumns {

        /*
        Table name
         */
        public final static String TABLE_NAME = "Inventory";

        /*
        Unique ID
         */
        public final static String _ID = BaseColumns._ID;

        //Column names

        /*
        Bananas
         */
        public final static String COLUMN_NAME = "name";

        /*
        Apples
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /*
        Pears
         */
        public final static String COLUMN_PRICE = "price";


    }
}

