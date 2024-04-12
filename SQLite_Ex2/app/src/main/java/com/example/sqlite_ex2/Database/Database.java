package com.example.sqlite_ex2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "products.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Product";
    public static final String COL_CODE = "ProductCode";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";

    //Constructor
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR(50), " + COL_PRICE + " REAL ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    //SELECT
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(sql, null);

    }

    //INSERT UPDATE DELTE
    public void execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public int getNumberOfRows(){
        Cursor cursor = queryData("SELECT * FROM " + TBL_NAME);
        int numberOfRows = cursor.getCount();
        cursor.close();

        return numberOfRows;
    }

    public void createSampleData(){
        if (getNumberOfRows() == 0){
            try{
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Heineken', 19000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Tiger', 18000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Sapporo', 22000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Blanc 1664', 24000)");
            }catch (Exception e){
                Log.e("Error", e.getMessage().toString());
            }
        }
    }
}
