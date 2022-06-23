package Game.Tiles.Units;

import Game.Utils.Position;

public class Wall {
    public Position position;
    public char tile = '#';

    public Wall(Position pos) {
        this.position = pos;
    }

    // return the char that represent a wall
    public String toString() {
        return this.tile + "";
    }
}
