package com.company;

public class Position {
    //fields
    private int x;
    private int y;

    //constractor
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    //getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int compareTo(Position position) {
        if(getX() > position.getX())
            return 1;
        if(getX() < position.getX())
            return -1;
        if(getY() > position.getY())
            return 1;
        if(getY() < position.getY())
            return -1;
        return 0;
    }
}
