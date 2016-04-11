package com.hc.android.superjam.data.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 99165 on 2016/4/11.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String DATABASE_NAME = "BookStore.db";

    public static final String TABLE_BOOK = "Book";
    public static final String TABLE_CATEGORY = "Category";

    private static final String CREATE_BOOK = "create table "
            + TABLE_BOOK
            + "( _id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";

    private static final String CREATE_CATEGORY = "create table "
            + TABLE_CATEGORY
            + " (_id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";


    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Database create successede", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGORY);
                break;
        }
    }
}
