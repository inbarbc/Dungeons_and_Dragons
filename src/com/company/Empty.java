package com.company;

public class Empty extends Tile {

    final char emptyChar = '.';


    public char ToString(){
        return emptyChar;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
