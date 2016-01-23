package com.prodevteam.tastebud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nzechi  on 1/23/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    // Name of the database
    private static final String DATABASE_NAME = "Resturant.db";

    // CUSTOMERS Table and it's columns
    private static final String CUSTOMERS_TABLE = "Customers";
    private static final String CUSTOMER_ID = "customerId";
    private static final String CUSTOMER_FName = "customerFistName";
    private static final String CUSTOMER_LName = "customerLastName";
    private static final String CUSTOMER_Email = "customerEmail";
    private static final String CUSTOMER_PASSWORD = "customerPassword";
    private static final String CUSTOMER_PHOTO = "customerPhoto";

    // Table Create Statement for CUSTOMERS
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + CUSTOMERS_TABLE + " ("+
            CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ CUSTOMER_FName + " varchar(25), "
            + CUSTOMER_LName + " varchar(25) NOT NULL, " + CUSTOMER_Email + " varchar(50) NOT NULL, "
            + CUSTOMER_PASSWORD + " varchar(60) NOT NULL, " + CUSTOMER_PHOTO + " varchar(255)" + ")";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating Table For Customer and potentially all other table to come
        db.execSQL(CREATE_TABLE_CUSTOMERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ CUSTOMERS_TABLE );

        onCreate(db);
    }
}
