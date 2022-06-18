package Game.Tiles.Units;

import Game.Utils.Position;

public abstract class  Tile implements Comparable<Tile> {
    //fields
    protected char tile;
    protected Position position;

    public Tile(){}

    protected Tile(char tile){
        this.tile = tile;
    }

    protected void Initialize(Position position){
        this.position = position;
    }

    public char GetTile(){
        return tile;
    }

    public Position GetPosition(){
        return position;
    }

    public void SetPosition(Position position){
        this.position = position;
    }

    public abstract void Accept(Unit unit);

    public String ToString(){
        return String.valueOf(tile);
    }

    public void Remove(){
        this.tile = Empty.ToString();
    }

    //compares tile based on their positions lexigrahicly
    public int CompareTo(Tile Other){
        return GetPosition().CompareTo(Other.GetPosition());
    }
}
