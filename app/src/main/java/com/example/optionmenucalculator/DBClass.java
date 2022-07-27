package com.example.optionmenucalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBClass extends SQLiteOpenHelper {

    public DBClass(@Nullable Context context) {
        super(context, "OPERATIONS", null, 1);
        Log.e("DB"," The Database was Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS OPERATIONS(" +
                "OPER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NUM1 TEXT(30)," +
                "OPERATOR_SIGN TEXT(1)," +
                "NUM2 TEXT(30)," +
                "RESULT TEXT(60));");
        Log.e("DB"," The Table was Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    void addOperation(Operations operations){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NUM1",operations.getNum1());
        values.put("OPERATOR_SIGN", String.valueOf(operations.getOperator()));
        values.put("NUM2",operations.getNum2());
        values.put("RESULT",operations.getResult());
        db.insert("OPERATIONS",null,values);
        Log.e("DB"," The Operation was added");
    }
    public ArrayList<Operations> getOperations(){
        ArrayList<Operations> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM OPERATIONS", null);
         while (!cursor.isAfterLast()){
             result.add(
                     new Operations(
                             cursor.getString(1),
                             cursor.getString(3),
                             cursor.getString(2).charAt(0),
                             cursor.getString(4)
                     )
             );
             cursor.moveToNext();
         }
        Log.e("DB ", "The operations was displayed");
         cursor.close();
         db.close();
         return result;
    }
}
