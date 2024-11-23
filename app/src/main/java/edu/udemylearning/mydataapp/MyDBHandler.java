package edu.udemylearning.mydataapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLOUM_ID = "_id";
    private static final String COLOUM_PRODUCT_NAME = "productname";


    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " +TABLE_PRODUCTS+ " ("
                +COLOUM_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLOUM_PRODUCT_NAME+ " TEXT"
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
        onCreate(db);
    }
    public void addProducts(Products products) {
        if (products == null || products.get_productname() == null || products.get_productname().trim().isEmpty()) {
            Log.e("Database Error", "Product name cannot be null or empty.");
            return;
        }

        SQLiteDatabase db = null;
        try {
            ContentValues values = new ContentValues();
            values.put(COLOUM_PRODUCT_NAME, products.get_productname().trim()); // Trim to avoid unwanted spaces

            db = getWritableDatabase(); // Open database
            long result = db.insert(TABLE_PRODUCTS, null, values); // Perform insert

            if (result == -1) {
                Log.e("Database Error", "Failed to insert product into database.");
            } else {
                Log.i("Database Success", "Product added with ID: " + result);
            }
        } catch (Exception e) {
            Log.e("Database Exception", "Error adding product: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close(); // Close the database connection
            }
        }
    }

    public void deleteProducts(String productname) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLOUM_PRODUCT_NAME + " = ?", new String[] { productname });
    }


    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1 ";
        Cursor c = db.rawQuery(query,null);
        if (c != null && c.moveToFirst()){
            do{
                // Make sure the "productname" column exist
                int index = c.getColumnIndex("productname");
                if(index != -1 && c.getString(index) != null){
                    dbString+=c.getString(index) + "\n";
                }
            }while (c.moveToNext());
        }
        // close the sursor and database
        if(c != null){
            c.close();
        }
        db.close();
        return  dbString;
    }
}
