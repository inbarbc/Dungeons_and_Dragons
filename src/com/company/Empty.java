package com.company;

public class Empty {
    public Coordinate position;
    public char tile='.';
    public Empty(Coordinate pos){
        this.position = pos;
    }

    @Override
    public String toString() {
        return this.tile+"";
    }
}
