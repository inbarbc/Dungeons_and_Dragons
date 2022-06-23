package Game.Tiles.Units.Enemies;

import Game.Callbacks.MessageCallback;
import Game.Handelers.InputHandler;
import Game.Handelers.MoveHandler;
import Game.Tiles.Units.HeroicUnit;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Unit;
import Game.Utils.Position;
import View.Input.InputProvider;
import View.Input.InputQuery;

public abstract class Enemy extends Unit implements HeroicUnit, InputQuery {

    public int experienceValue = 0;

    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    protected static final int HEALTH_BONUS = 10;

    public MessageCallback messageCallback;

    public Enemy(String name, char tile, int health, int attack, int defense, Position position) {
        super(tile, name, health, attack, defense,position);
    }
    public Enemy(String name, char tile, int health, int attack, int defense,int experience) {
        this(name, tile, health, attack, defense,experience,new Position());
    }
    public Enemy(String name, char tile, int health, int attack, int defense,int experience,Position position) {
        super(name, tile, health, attack, defense);
        this.experienceValue=experience;
    }

    // the Enemy attack a Player on the board
    public void attack(Player defender) {
        super.attack(defender);
        if (defender.isDead()) {
            messageCallback.send(String.format("%s was killed by %d.", defender.getName(), getName()));
        }
    }

    // return the experience value
    public int getExprienceValue(){
        return experienceValue;
    }

    // make a move for this enemy
    public void move(InputProvider moveDir) {
        MoveHandler.move(moveDir, this);
    }

    // the enemy make a turn
    public void turn(int turnCount) {
    }

    public void SetMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }
}
