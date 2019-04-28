package com.ads.rockbander;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ads.rockbander.models.Piece;
import com.ads.rockbander.persistance.FeedReaderDatabaseHelper;
import com.ads.rockbander.persistance.SongsContract;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MySongsActivity extends AppCompatActivity implements MySongsAdapter.OnListItemClickListener, MySongsAdapter.OnListItemLongClickListener {

    RecyclerView listMySongs;
    RecyclerView.Adapter mySongsAdapter;
    private FeedReaderDatabaseHelper helper;
    private ArrayList<Piece> pieces;
    private SQLiteDatabase db;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_songs);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_song);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddMySongActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listMySongs = findViewById(R.id.rv_my_songs);
        listMySongs.hasFixedSize();
        listMySongs.setLayoutManager(new LinearLayoutManager(this));



    }
    @Override
    protected void onStart() {
        super.onStart();

        helper = new FeedReaderDatabaseHelper(this);
        db = helper.getReadableDatabase(); // create database connection
        pieces = getPieces();
        mySongsAdapter = new MySongsAdapter(pieces, this, this);
        listMySongs.setAdapter(mySongsAdapter);
        listMySongs.addItemDecoration(new DividerItemDecoration(listMySongs.getContext(), DividerItemDecoration.VERTICAL));
//        pieces.add(new Piece("Green Day", "Basket Case"));
//        pieces.add(new Piece("Sum 41", "Motivation"));
    }



    private ArrayList<Piece> getPieces() {
        ArrayList<Piece> retArr = new ArrayList<>();

        String[] projection = {SongsContract.SongEntry.COLUMN_ARTIST_NAME, SongsContract.SongEntry.COLUMN_SONG_NAME};
        Cursor cursor = db.query(SongsContract.SongEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(SongsContract.SongEntry.ID);
        int artistColumnIndex = cursor.getColumnIndex(SongsContract.SongEntry.COLUMN_ARTIST_NAME);
        int songColumnIndex = cursor.getColumnIndex(SongsContract.SongEntry.COLUMN_SONG_NAME);


        while(cursor.moveToNext()){
            String artistName = cursor.getString(artistColumnIndex);
            String songName = cursor.getString(songColumnIndex);
            retArr.add(new Piece(artistName,songName));
        }
        cursor.close();

        return retArr;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String query = pieces.get(clickedItemIndex).getArtist() + " - " + pieces.get(clickedItemIndex).getSong() + " tabs";
        String escapedQuery = null;
        try {
            escapedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int clickedItemIndex) {

        return true;
    }

    @Override
    protected void onDestroy() {
        helper.close();
        db.close();
        super.onDestroy();
    }
}
