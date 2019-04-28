package com.ads.rockbander;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ads.rockbander.persistance.FeedReaderDatabaseHelper;
import com.ads.rockbander.persistance.SongsContract;

public class AddMySongActivity extends AppCompatActivity {
    SQLiteDatabase db;
    FeedReaderDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_song);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

         dbHelper = new FeedReaderDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addThisSong(View view) {
        EditText etArtist = findViewById(R.id.artist_name_input);
        EditText etSong = findViewById(R.id.song_name_input);

        String artist = etArtist.getText().toString();
        String song = etSong.getText().toString();

        Toast.makeText(this, artist + " - " + song + " has been added to your setlist", Toast.LENGTH_SHORT).show();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SongsContract.SongEntry.COLUMN_ARTIST_NAME, artist);
        contentValues.put(SongsContract.SongEntry.COLUMN_SONG_NAME, song);
        db.insert(SongsContract.SongEntry.TABLE_NAME, null, contentValues);

        etArtist.setText("");
        etSong.setText("");

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        db.close();
        super.onDestroy();
    }
}
