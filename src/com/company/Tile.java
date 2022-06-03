package com.company;

public abstract class  Tile {
    //fields
    private char tile;
    private Position position;

    public static char ToString(){
        return '.';
    }
    public void remove(){
        this.tile = Empty.ToString();
    }
}
