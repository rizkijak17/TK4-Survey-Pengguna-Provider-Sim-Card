package com.example.simcardapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kk_database";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_KK = "kk";
    public static final String TABLE_SIM = "sim";

    // KK Columns
    public static final String COLUMN_KK_ID = "id";
    public static final String COLUMN_KK_NAME = "name";

    // SIM Columns
    public static final String COLUMN_SIM_ID = "id";
    public static final String COLUMN_SIM_NUMBER = "sim_number";
    public static final String COLUMN_SIM_TYPE = "sim_type";
    public static final String COLUMN_KK_ID_FK = "kk_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createKKTable = "CREATE TABLE " + TABLE_KK + " (" +
                COLUMN_KK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KK_NAME + " TEXT NOT NULL)";

        String createSIMTable = "CREATE TABLE " + TABLE_SIM + " (" +
                COLUMN_SIM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SIM_NUMBER + " TEXT NOT NULL, " +
                COLUMN_SIM_TYPE + " TEXT NOT NULL, " +
                COLUMN_KK_ID_FK + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_KK_ID_FK + ") REFERENCES " + TABLE_KK + "(" + COLUMN_KK_ID + "))";

        db.execSQL(createKKTable);
        db.execSQL(createSIMTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_SIM + " ADD COLUMN " + COLUMN_SIM_TYPE + " TEXT NOT NULL DEFAULT 'Unknown'");
        }
    }

    public long insertKK(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KK_NAME, name);
        return db.insert(TABLE_KK, null, values);
    }

    public Cursor getAllKK() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_KK, null);
    }

    public int deleteKK(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_KK, COLUMN_KK_ID + "=?", new String[]{String.valueOf(id)});
    }

    public boolean updateKK(int kkId, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KK_NAME, newName);
        int rows = db.update(TABLE_KK, values, COLUMN_KK_ID + "=?", new String[]{String.valueOf(kkId)});
        return rows > 0;
    }

    public long insertSIM(String simNumber, String simType, int kkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIM_NUMBER, simNumber);
        values.put(COLUMN_SIM_TYPE, simType); // Insert SIM type
        values.put(COLUMN_KK_ID_FK, kkId);
        return db.insert(TABLE_SIM, null, values);
    }

    public Cursor getSIMsByKK(int kkId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SIM + " WHERE " + COLUMN_KK_ID_FK + "=?", new String[]{String.valueOf(kkId)});
    }

    public boolean updateSIM(int simId, String newSimNumber, String newSimType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIM_NUMBER, newSimNumber);
        values.put(COLUMN_SIM_TYPE, newSimType); // Update SIM type
        int rows = db.update(TABLE_SIM, values, COLUMN_SIM_ID + "=?", new String[]{String.valueOf(simId)});
        return rows > 0;
    }

    public int deleteSIM(int simId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SIM, COLUMN_SIM_ID + "=?", new String[]{String.valueOf(simId)});
    }

    public int deleteSIMByKK(int kkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SIM, COLUMN_KK_ID_FK + "=?", new String[]{String.valueOf(kkId)});
    }
}
