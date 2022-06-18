package Game.Handelers;

import Game.Board;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;
import Game.Utils.Position;

import java.util.ArrayList;
import java.util.List;

public class TargetHandler {
    public static Board gameBoard;
    public static List<Enemy> CandidateTarget(Player player, Position cord, int range){
        List<Enemy> closeEnemy=new ArrayList<>();
        for (Enemy enemy:gameBoard.enemies)
        { if( cord.IsInRange(enemy.GetPosition(),range))

            closeEnemy.add(enemy);
        }
        return closeEnemy;
    }

    public static List<Player> CandidateTarget(Enemy enemy, Position cord, int range){
        List<Player> closePlayer=new ArrayList<>();
        if( cord.IsInRange(gameBoard.GetPlayer().GetPosition(),range))
            closePlayer.add(gameBoard.GetPlayer());
        return closePlayer;
    }


    public static List<Player> CandidateTarget(Enemy enemy, int range){

        return CandidateTarget(enemy,enemy.GetPosition(),range);
    }
    public static List<Enemy> CandidateTarget(Player player, int range){

        return CandidateTarget(player,player.GetPosition(),range);
    }

    /*
    public static List<Unit> CandidateTarget(Position cord, int range){
        List<Unit> closeUnit=new ArrayList<>();
        closeUnit.addAll(CandidateTarget(new Enemy(), cord, range));
        closeUnit.addAll(CandidateTarget(new Player(),cord,range));
        return closeUnit;
    }
     */

}
