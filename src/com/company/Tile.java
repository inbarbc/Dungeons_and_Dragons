package com.company;

public abstract class  Tile implements Comparable {
    //fields
    private char tile;
    private Position position;

    public char ToString(){
        return '.';
    }
    public void remove(){
        this.tile = Empty.ToString();
    }

    //compares tile based on their positions lexigrahicly
    public int CompareTo(Tile Other){
        if(this.position.getX() > Other.position.getX())
            return 1;
        if(this.position.getX() < Other.position.getX())
            return -1;
        if(position.getY() > Other.position.getY())
            return 1;
        if(position.getY() < Other.position.getY())
            return -1;
        return 0;
    }
}
