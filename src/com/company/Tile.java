package com.company;

public abstract class  Tile implements Comparable<Tile> {
    //fields
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    protected void initialize(Position position){
        this.position = position;
    }

    public char getTile(){
        return tile;
    }

    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = position;
    }

    public abstract void accept(Unit unit);

    @Override
    public String toString(){
        return String.valueOf(tile);
    }
    public void remove(){
        this.tile = Empty.ToString();
    }

    //compares tile based on their positions lexigrahicly
    @Override
    public int compareTo(Tile Other){
        return getPosition().compareTo(Other.getPosition());
    }
}
