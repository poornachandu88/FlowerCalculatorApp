package com.poorna.flowercalculator;


import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataBaseClass extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "flowersData";
    // Flowers table name
    private static final String TABLE_Flowers= "Flowers";

    // Flowers Table Columns names
    private static final String id = "id";
    private static final String customername = "customername";
    private static final String mobileno = "mobileno";
    private static final String dateofpurchase = "dateofpurchase";
    private static final String costofflowers = "costofflowers";
    private static final String quantityofflowers = "quantityofflowers";
    private static final String totalamountofflowers = "totalamountofflowers";


    public SQLiteDataBaseClass(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Flowers_TABLE =
                "CREATE TABLE " + TABLE_Flowers +
                        "("
                        + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + customername + " TEXT,"
                        + mobileno + " TEXT,"
                        + dateofpurchase +" TEXT,"
                        + costofflowers + " REAL,"
                        + quantityofflowers +" REAL,"
                        + totalamountofflowers +" REAL"
                        +")";

        db.execSQL(CREATE_Flowers_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Flowers);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Flowers
    boolean addFlowers(Flowers flower) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(customername, flower.getCustomername());
        values.put(mobileno, flower.getMobileno());
        values.put(dateofpurchase, flower.getDateofpurchase());
        values.put(costofflowers, flower.getCostofflowers());
        values.put(quantityofflowers, flower.getQuantityofflowers());
        values.put(totalamountofflowers, flower.getTotalamountofflowers());

        // Inserting Row
        db.insert(TABLE_Flowers, null, values);
        db.close(); // Closing database connection

        return true;
    }

    // Getting single Flower
    Flowers getFlower(int key_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Flowers, new String[]
                        { id, customername, mobileno,dateofpurchase,costofflowers,quantityofflowers,totalamountofflowers},id + "=?",
                new String[] { String.valueOf(key_id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Flowers flowers = new Flowers(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),
                Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)),Double.parseDouble(cursor.getString(6)));

        // (int id, String customername, String mobileno, String dateofpurchase, String costofflowers, String quantityofflowers, String totalamountofflowers)
        // return flowers
        return flowers;
    }

    // Getting All Countries
    public List<Flowers> getAllFlowers() {
        List<Flowers> flowersList = new ArrayList<Flowers>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Flowers;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Flowers flowers = new Flowers();
                flowers.setId(Integer.parseInt(cursor.getString(0)));
                flowers.setCustomername(cursor.getString(1));
                flowers.setMobileno(cursor.getString(2));
                flowers.setDateofpurchase(cursor.getString(3));
                flowers.setCostofflowers(cursor.getDouble(4));
                flowers.setQuantityofflowers(cursor.getDouble(5));
                flowers.setTotalamountofflowers(cursor.getDouble(6));

                // Adding flowers to list
                flowersList.add(flowers);
            } while (cursor.moveToNext());
        }
                cursor.close();
        // return flowers list
        return flowersList;
    }

    // Updating single flowers
    public int updateFlowers(Flowers flowers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(customername, flowers.getCustomername());
        values.put(mobileno, flowers.getMobileno());
        values.put(dateofpurchase, flowers.getDateofpurchase());
        values.put(costofflowers, flowers.getCostofflowers());
        values.put(quantityofflowers, flowers.getQuantityofflowers());
        values.put(totalamountofflowers, flowers.getTotalamountofflowers());

        // updating row
        return db.update(TABLE_Flowers, values, id + " = ?",
                new String[] { String.valueOf(flowers.getId()) });
    }

    // Deleting single flowers
    public int deleteFlowers(Flowers flowers) {
        SQLiteDatabase db = this.getWritableDatabase();
       // String sql="DELETE FROM Flowers WHERE id=?";


        db.delete(TABLE_Flowers, id + " = ?",
                new String[] { String.valueOf(flowers.getId()) });

      return 1;

    }


    // Deleting all flowers
    public void deleteAllFlowers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Flowers,null,null);
        db.close();
    }

    // Getting flowers Count
    public int getFlowersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Flowers;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public double getTotalcost() {
       // SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery("SELECT SUM("+ totalamountofflowers +") FROM"+ TABLE_Flowers, null);

        String countQuery ="SELECT SUM(totalamountofflowers)FROM Flowers";
        SQLiteDatabase db = this.getReadableDatabase();
        double amount=0;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
           // amount = Integer.parseInt(cursor.getString(0));
            amount= cursor.getInt(0);;
        }
        while (cursor.moveToNext());
        return amount;


    }

}

