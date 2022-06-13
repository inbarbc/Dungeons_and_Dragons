package com.company;

import java.util.Objects;

public class Position {
    //fields
    public int x = 0;
    public int y = 0;

    //constructor
    public Position(){    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    public Position copy() {
        return new Position(this.x, this.y);
    }

    @Override
    public String toString() {
        return "x:"+x+", y:"+y;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
