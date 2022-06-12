package com.company;

public class Wall extends Tile{

    final char wallChar = '#';


    public char ToString(){
        return wallChar;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
