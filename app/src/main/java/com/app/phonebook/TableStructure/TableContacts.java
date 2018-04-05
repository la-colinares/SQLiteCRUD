package com.app.phonebook.TableStructure;

/**
 * Created by Colinares on 4/6/2018.
 */
public class TableContacts {
    public static final String TABLE_NAME = "contact_table";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "title";
    public static final String COLUMN_NUMBER = "content";
    public static final String COLUMN_AGE = "selected_time";
    public static final String COLUMN_GENDER = "selected_date";
    public static final String COLUMN_DATE_CREATED = "date_created";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NUMBER + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_DATE_CREATED + " TEXT " +
                    ")";

}
