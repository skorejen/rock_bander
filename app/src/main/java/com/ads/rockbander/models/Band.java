package com.ads.rockbander.models;

import java.util.ArrayList;

public class Band {

    private String bandName;
    private ArrayList<Piece> pieces;
    private String userUid;

    public Band(){}

    public Band(String bandName, ArrayList<Piece> pieces, String userUid) {
        this.bandName = bandName;
        this.pieces = pieces;
        this.userUid = userUid;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
