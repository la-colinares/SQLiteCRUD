package com.app.phonebook.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.phonebook.TableStructure.TableContacts;

public class MyDatabase extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "Phonebook.sqlite";

    private static MyDatabase mInstance = null;

    public MyDatabase(Context context){
        super(context, DB_NAME, null,DB_VERSION);
    }

    public static MyDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = new MyDatabase(context.getApplicationContext());
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableContacts.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
