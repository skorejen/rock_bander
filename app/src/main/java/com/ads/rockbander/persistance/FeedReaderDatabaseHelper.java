package com.ads.rockbander.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ads.rockbander.persistance.SongsContract.SongEntry;

import org.jetbrains.annotations.Nullable;

public class FeedReaderDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedReader.db";
    public FeedReaderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qrCreateTable = "CREATE TABLE " + SongEntry.TABLE_NAME + "( " + SongEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                SongEntry.COLUMN_ARTIST_NAME + " TEXT NOT NULL, "+
                SongEntry.COLUMN_SONG_NAME + " TEXT NOT NULL);";
        db.execSQL(qrCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
