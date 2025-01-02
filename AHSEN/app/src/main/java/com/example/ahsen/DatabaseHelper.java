package com.example.ahsen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MotorDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "motorlar";
    public static final String COL_ID = "id";
    public static final String COL_MARKA = "marka";
    public static final String COL_VIDEO_ID = "video_id";
    public static final String COL_ACIKLAMA = "aciklama";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MARKA + " TEXT, " +
                COL_VIDEO_ID + " TEXT, " +
                COL_ACIKLAMA + " TEXT)";
        db.execSQL(createTable);

        // Arora için yeni video ID
        ContentValues values = new ContentValues();
        values.put(COL_MARKA, "ARORA\nCAPPUCINO");
        values.put(COL_VIDEO_ID, "VDcXQ5YxG0o");
        values.put(COL_ACIKLAMA, "Arora AR150-2 modelinin kapsamlı incelemesi. Teknik özellikler, yakıt tüketimi ve sürüş deneyimi hakkında detaylı bilgiler içerir.");
        db.insert(TABLE_NAME, null, values);

        // Mondial için
        values = new ContentValues();
        values.put(COL_MARKA, "MONDİAL\nTORİSMO");
        values.put(COL_VIDEO_ID, "-IgaUNinMFQ");
        values.put(COL_ACIKLAMA, "Mondial 125 MH Drift detaylı inceleme ve test sürüşü videosu.");
        db.insert(TABLE_NAME, null, values);

        // Kuba için
        values = new ContentValues();
        values.put(COL_MARKA, "KUBA\nBLUEBRİD");
        values.put(COL_VIDEO_ID, "-bJLAHdDUR8");
        values.put(COL_ACIKLAMA, "Kuba CR1 modelinin detaylı tanıtımı.");
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getMotorBilgileri(String marka) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COL_MARKA + "=?", 
                new String[]{marka}, null, null, null);
    }
} 