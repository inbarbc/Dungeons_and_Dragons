package com.company;

public class Range {
    public int range(Position p, Position q){
        return (int)Math.floor(Math.sqrt((Math.pow((p.getX()- q.getX()),2)+(Math.pow((p.getY()- q.getY()),2)))));
    }
}
