package com.krito.io.oscar.model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mona Abdallh on 3/20/2018.
 */

public class OscarSqlHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "oscar_customer.db";
    public static final int VERSION = 1;

    public OscarSqlHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserSchema.Table.CRETAE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
