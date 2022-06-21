package Game;

import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Unit;
import Game.Tiles.Units.Wall;
import Game.Utils.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Board {

    private Player player;
    public int height;
    public int width;
    public List<Enemy> enemies= new ArrayList<Enemy>();
    public HashMap<Position, Wall> walls = new HashMap<Position, Wall>();
    private String[][]board;

    public Player GetPlayer() {
        return player;
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }

    public void BuildBoard(File mapFile, Player player){
        this.player=player;
        BuildBoard(mapFile);
    }

    public void BuildBoard(File mapFile) {
        walls.clear();
        try {
            Scanner mapReader = new Scanner(mapFile);
            int y = 0, x=0;
            Position position = new Position(x, y);
            Unit enemy;
            String data;
            while (mapReader.hasNextLine()){
                data = mapReader.nextLine();

                x = 0;
                for (char ch: data.toCharArray()){
                    if (ch == '@') {
                        player.SetPosition(position);
                    }
                    if (ch == '#') {
                        walls.put(position, new Wall(position));
                    }
                    if (DatabaseUnits.enemyPool.containsKey(ch+"")){
                        enemy = DatabaseUnits.enemyPool.get(ch+"").Copy();
                        enemy.SetPosition(position);
                        enemies.add((Enemy) enemy);
                    }
                    x++;
                }
                y++;
            }
            this.width = x;
            this.height = y;
            this.board = new String[y][x];
        }
        catch (Exception e){}
    }

    public void BuildArray(){
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                board[y][x] = ".";
        board[player.GetPosition().y][player.GetPosition().x] =player.toString();
        for (Unit enemy : this.enemies){
            board[enemy.GetPosition().y][enemy.GetPosition().x] = enemy.toString();
        }
        for (Position wallPos: this.walls.keySet()){
            board[wallPos.y][wallPos.x] = this.walls.get(wallPos).toString();
        }
    }

    public String ArrayToString(){
        String currBoard = "";
        for (String[] line : board) {
            for (String block : line) {
                currBoard += block;
            }
            currBoard += "\n";
        }
        return currBoard;
    }

    public String ToString(){
        BuildArray();
        return ArrayToString();
    }

    public void Remove(Enemy e) {
    }
}
