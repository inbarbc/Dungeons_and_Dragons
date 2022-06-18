package Game.Tiles.Units.Enemies;

import Game.Callbacks.EnemyDeathCallback;
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

    private EnemyDeathCallback enemyDeathCallback;

    public Enemy(){}
    public Enemy(String name, char tile, int hp, int ap, int dp, Position pos) {
        super(tile, name, hp, ap, dp,pos);
    }
    public Enemy(String name, char tile, int hp, int ap, int dp,int xp) {
        this(name, tile, hp, ap, dp,xp,new Position());
    }
    public Enemy(String name, char tile, int hp, int ap, int dp,int xp,Position pos) {
        super(tile, name, hp, ap, dp);
        this.experienceValue=xp;
    }

    public void Attack(Player defender) {
        super.Attack(defender);
        if (defender.IsDead()) {
            messageCallback.Send(String.format("%s was killed by %d.", defender.GetName(), GetName()));
        }
    }

    public int GetExprienceValue(){
        return experienceValue;
    }

    public void Move(InputProvider moveDir) {
        MoveHandler.Move(moveDir, this);
    }

    public void Turn(int turnCount) {
        InputProvider input = InputHandler.InputPlayerGame();
        if (input == InputProvider.CastAbility)
            this.TryCastAbility();
        else
            Move(input);
    }

    private void TryCastAbility() {
    }


    public void SetDeathCallback(EnemyDeathCallback enemyDeathCallback) {
        this.enemyDeathCallback = enemyDeathCallback;
    }
}
