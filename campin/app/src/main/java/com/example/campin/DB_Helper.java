package com.example.campin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DB_Helper extends SQLiteOpenHelper {

    public static final String TAG     = DB_Helper.class.getSimpleName();
    public static final String DB_NAME = "campin.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE              = "users";
    public static final String COLUMN_ID               = "_id";
    public static final String COLUMN_NAMA_LENGKAP     = "nama_lengkap";
    public static final String COLUMN_NAMA_PENGGUNA    = "nama_pengguna";
    public static final String COLUMN_NIM              = "nim";
    public static final String COLUMN_NOHP             = "no_hp";
    public static final String COLUMN_EMAIL            = "email";
    public static final String COLUMN_KATA_SANDI       = "kata_sandi";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAMA_LENGKAP + " TEXT,"
            + COLUMN_NAMA_PENGGUNA + " TEXT,"
            + COLUMN_NIM + " INTEGER,"
            + COLUMN_NOHP + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_KATA_SANDI + " TEXT);";

    public DB_Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void addUser(String nama_lengkap, String nama_pengguna, String nim, String no_hp, String email, String kata_sandi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA_LENGKAP, nama_lengkap);
        values.put(COLUMN_NAMA_PENGGUNA, nama_pengguna);
        values.put(COLUMN_NIM, nim);
        values.put(COLUMN_NOHP, no_hp);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_KATA_SANDI, kata_sandi);

        long id = db.insert(USER_TABLE, null, values);

        Log.d(TAG, "Pengguna ditambahkan" + id);

    }

    public boolean getUser(String nama_pengguna, String kata_sandi) {
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE " +
                COLUMN_NAMA_PENGGUNA + " = " + "'"+nama_pengguna+"'" + " AND " + COLUMN_KATA_SANDI + " = " +
                "'"+kata_sandi+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor     = db.rawQuery(selectQuery,null);

        //Pindah ke baris pertama
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            //user put("Nama Pengguna", cursor.getString(1));
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

}
