package Game.Tiles.Units;

import Game.Utils.Position;

import javax.swing.*;

public class Empty{

    public Position position;
    public static char tile='.';

    public Empty(Position pos){
        this.position = pos;
    }

    public static char ToString() {
        return tile;
    }

    public Position GetPosition(){
        return position;
    }

    public void SetPosition(Position position){
        this.position = position;
    }
}
