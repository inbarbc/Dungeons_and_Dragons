package Game.Tiles.Units;

import Game.Callbacks.MessageCallback;
import Game.Utils.Position;

public abstract class  Tile implements Comparable<Tile> {

    protected char tile;
    protected Position position;

    // this char that represents this Tile
    protected Tile(char tile){
        this.tile = tile;
    }

    // initialize the position of this Tile
    protected void initialize(Position position){
        this.position = position;
    }

    // return the char that represents this Tile
    public char getTile(){
        return tile;
    }

    // return the position of the tile
    public Position getPosition(){
        return position;
    }

    // change the position of the tile
    public void setPosition(Position position){
        this.position = position;
    }

    // return the char of this tile
    public String toString(){
        return String.valueOf(tile);
    }

    //compares tile based on their positions lexigrahicly
    public int compareTo(Tile Other){
        return getPosition().compareTo(Other.getPosition());
    }
}
