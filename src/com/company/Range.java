package com.company;

public class Range {
    public int range(Position p, Position q){
        return (int)Math.floor(Math.sqrt((Math.pow((p.getX()- q.getX()),2)+(Math.pow((p.getY()- q.getY()),2)))));
    }

    public boolean isInRange(Coordinate c2, int range) {
        return range >= this.distance(c2);
    }

    public double distance(Coordinate c2){
        return Math.sqrt(Math.pow((this.x - c2.x), 2) + Math.pow((this.y - c2.y), 2));
    }
}
