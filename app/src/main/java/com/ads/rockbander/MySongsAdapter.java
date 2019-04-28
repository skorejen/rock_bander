package com.ads.rockbander;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ads.rockbander.models.Piece;
import com.ads.rockbander.persistance.FeedReaderDatabaseHelper;
import com.ads.rockbander.persistance.SongsContract;

import java.util.ArrayList;

class MySongsAdapter extends RecyclerView.Adapter<MySongsAdapter.ViewHolder> {

    private ArrayList<Piece> pieces;
    final private OnListItemClickListener mOnListItemClickListener;
    final private OnListItemLongClickListener mOnListItemLongClickListener;
    private int selectedPosition;

    public MySongsAdapter(ArrayList<Piece> pieces, OnListItemClickListener listener, OnListItemLongClickListener longListener) {
        this.pieces = pieces;
        mOnListItemClickListener = listener;
        mOnListItemLongClickListener = longListener;
        selectedPosition = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.rv_my_songs_row, viewGroup, false);
        return new ViewHolder(view);
    }

    public void updatePosition(int i) {
        selectedPosition = i;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvSong.setText(pieces.get(i).getSong());
        viewHolder.tvArtist.setText(pieces.get(i).getArtist());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pieces.remove(i);
                notifyDataSetChanged();
                FeedReaderDatabaseHelper helper = new FeedReaderDatabaseHelper(v.getContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                String selection = SongsContract.SongEntry.COLUMN_ARTIST_NAME + " = ? AND "+ SongsContract.SongEntry.COLUMN_SONG_NAME + " = ?";
                String[] selectionArgs = {viewHolder.tvArtist.getText().toString()+"", viewHolder.tvSong.getText().toString()};
                db.delete(SongsContract.SongEntry.TABLE_NAME, selection, selectionArgs);
                helper.close();
                db.close();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvArtist;
        TextView tvSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArtist = itemView.findViewById(R.id.tv_artist_name);
            tvSong = itemView.findViewById(R.id.tv_song_name);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mOnListItemLongClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public interface OnListItemLongClickListener {
        boolean onItemLongClick(int clickedItemIndex);
    }
}
