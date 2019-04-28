package com.ads.rockbander.persistance;

import android.provider.BaseColumns;

public final class SongsContract {

    private SongsContract(){}

    public class SongEntry implements BaseColumns {
// table name
        public static final String TABLE_NAME = "songs";
// columns
        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_SONG_NAME = "songName";
        public static final String COLUMN_ARTIST_NAME = "artistName";
    }

}
