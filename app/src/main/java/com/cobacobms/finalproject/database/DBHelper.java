package com.cobacobms.finalproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cobacobms.finalproject.entity.Product;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CobacoDB";
    private static final int VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void createTableProducts(SQLiteDatabase db) {
        String query = "CREATE TABLE "
                + Product.class.getSimpleName()
                + "("
                + "ID integer primary key,"
                + "ProductId integer ,"
                + "ProductName varchar(150),"
                + "ProductName2 varchar(150),"
                + "ProductDesc varchar(2000),"
                + "Category varchar(100),"
                + "PicId integer ,"
                + "PicName varchar(1000),"
                + "Rank integer"
                + ")";

        try {
            db.execSQL(query);
        } catch (Exception ex) {
        }
    }
}