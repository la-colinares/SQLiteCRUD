package com.app.phonebook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.phonebook.TableStructure.TableContacts;

/**
 * Created by Colinares on 4/6/2018.
 */
public class MyDatabaseFunctions {

    private static Context mContext;
    private static SQLiteDatabase mDb;

    public static void init(Context context) {
        mContext = context;
        mDb = MyDatabase.getInstance(mContext).getWritableDatabase();
    }

    //method for inserting new data.
    public static void insert(String tableName, ContentValues contentValues){
        mDb.insert(tableName, null, contentValues);
    }

    //method for updating data.
    public static boolean update(int id, ContentValues values){

        int result = mDb.update(TableContacts.TABLE_NAME, values, TableContacts.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if(result > 0){
            return true;
        }else {
            return false;
        }
    }

    //method for deleting data.
    public static void delete(long id){
        mDb.delete(TableContacts.TABLE_NAME, TableContacts.COLUMN_ID + " = ?", new String[]{String.valueOf((id))});
    }
}
