package Game.Handelers;

import Game.Board;
import Game.Callbacks.MessageCallback;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Unit;
import Game.Utils.Position;
import View.Input.InputProvider;

import java.util.List;

public class MoveHandler {
    public static Board gameBoard;
    private static MessageCallback messageCallback;

    // receive a move and a player and commit the movement
    public static void Move(InputProvider input, Player player){
        Position moveTo = joyStick(input, player.GetPosition());
        Enemy target = TargetCondidate(moveTo, player);
        if (target == null){
            player.SetPosition(moveTo);
        }
        else {
            Combat(player, target);
            player.Attack(target);
        }
    }

    // receive a move and an enemy and commit the movement
    public static void Move(InputProvider input, Enemy enemy){
        Position moveTo = joyStick(input, enemy.GetPosition());
        if(IsOnEnemy(moveTo))
            return;
        Player target = TargetCondidate(moveTo, enemy);
        if (target == null){
            enemy.SetPosition(moveTo);
        }
        else {
            Combat(enemy, target);
            enemy.Attack(target);
        }

    }

    // commit a combat between two units
    public static void Combat(Unit unit1, Unit unit2){
        messageCallback.Send(String.format("%s engaged in combat with %d.", unit1.GetName(), unit2.GetName()));
        messageCallback.Send(unit1.Describe());
        messageCallback.Send(unit2.Describe());
    }

    // update the position of the unit and committed move
    private static Position joyStick(InputProvider input, Position pos) {
        Position newPos = pos.Copy();
        switch (input) {
            case Right -> newPos.x++;
            case Left -> newPos.x--;
            case Up -> newPos.y--;
            case Down -> newPos.y++;
        }
        if (!IsValidMove(newPos))
            newPos = pos.Copy();
        return newPos;
    }

    // check if the move is valid in the game
    private static boolean IsValidMove(Position newPos){
        return (newPos.x>= 0 && newPos.x< gameBoard.width && newPos.y>= 0 && newPos.y< gameBoard.height &&
                !gameBoard.walls.containsKey(newPos));
    }

    // check if an enemy move on another enemy
    private static boolean IsOnEnemy(Position newPos){
        boolean isSameCor = false;
        int i = 0;
        List<Enemy> enemies = gameBoard.enemies;
        while (!isSameCor && i < enemies.size() ){
            if (enemies.get(i).GetPosition().equals(newPos))
                isSameCor = true;
            i++;
        }
        return isSameCor;
    }

    // return the first enemy if exist that close to the player
    private static Enemy TargetCondidate(Position newPos, Player player){
        List<Enemy> targets = TargetHandler.CandidateTarget(player,newPos, 0);
        return targets.size() == 0 ? null: targets.get(0);
    }

    // return the flayer if exist that close to the enemy
    private static Player TargetCondidate(Position newPos, Enemy enemy){
        List<Player> targets = TargetHandler.CandidateTarget(enemy,newPos, 0);
        return targets.size() == 0 ? null: targets.get(0);
    }
}
