package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract;
import com.example.android.inventoryapp.data.InventoryDbHelper;

/**
 * Displays a list of items that have been entered and stored in the app
 */

public class CatalogActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new InventoryDbHelper(this);
    }@Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_NAME,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_PRICE,};

        Cursor cursor = getContentResolver().query(
                InventoryContract.InventoryEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);                  // The sort order for the returned rows


        TextView displayView = (TextView) findViewById(R.id.text_view_item);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The inventory table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The inventory table contains " + cursor.getCount() + " items.\n\n");
            displayView.append(InventoryContract.InventoryEntry._ID + " - " +
                    InventoryContract.InventoryEntry.COLUMN_NAME + " - " +
                    InventoryContract.InventoryEntry.COLUMN_QUANTITY + " - " +
                    InventoryContract.InventoryEntry.COLUMN_PRICE + "\n");


            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentQuantity = cursor.getString(quantityColumnIndex);
                double currentPrice = cursor.getInt(priceColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentQuantity + " - " +
                        currentPrice));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded item data into the database. For debugging purposes only.
     */
    private void insertItem() {

            // Create a ContentValues object where column names are the keys,
            // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_NAME, "Apple");
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 100);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 7);

            // Insert a new row for Toto into the provider using the ContentResolver.
            // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
            // into the pets database table.
            // Receive the new content URI that will allow us to access Toto's data in the future.
            Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
        }
//        // Gets the database in write mode
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        // Create a ContentValues object where column names are the keys,
//        // and the item's attributes are the values.
//        ContentValues values = new ContentValues();
//        values.put(InventoryContract.InventoryEntry.COLUMN_NAME, "Apple");
//        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 100);
//        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 7);
//
//        // Insert a new row for Apple in the database, returning the ID of that new row.
//        // The first argument for db.insert() is the Inventory table name.
//        // The second argument provides the name of a column in which the framework
//        // can insert NULL in the event that the ContentValues is empty (if
//        // this is set to "null", then the framework will not insert a row when
//        // there are no values).
//        // The third argument is the ContentValues object containing the info for Apples.
//        long newRowId = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
//        Log.v("CatalogActivity", "New Row ID " + newRowId);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertItem();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
