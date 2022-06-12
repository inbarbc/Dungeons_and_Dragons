package com.company;
import java.util.List;

public class GameBoard extends Game{
    private Player UserCharacter;
    private List<Enemy> Enemies;
    private List<Tile> Tiles; //not sure which is better
    private Tile[][] tiles;

    public GameBoard(Player player){

    }

    public Tile Get(int i, int j){
        return tiles[i][j];
    }

    public void GameTick(){

    }
    public String ToString(){

    }

}
