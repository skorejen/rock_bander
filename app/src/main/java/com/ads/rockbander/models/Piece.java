package com.ads.rockbander.models;

public class Piece {

    private String artist;
    private String song;

    public Piece(String artistName, String songName) {
        this.artist = artistName;
        this.song = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artistName) {
        this.artist = artistName;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String songName) {
        this.song = songName;
    }
}
