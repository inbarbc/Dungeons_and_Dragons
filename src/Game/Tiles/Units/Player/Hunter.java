package Game.Tiles.Units.Player;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;

import java.util.List;

public class Hunter extends Player {

    private int arrowCount = 0;
    private int tickCount = 0;
    private final int ARROW_COST = 1;
    //private final String ABILITY_NAME="Fan of Knives";
    private int ABILITY_RANGE;

    public Hunter(String name, char tile, int health, int attack, int defence, int range) {
        super(name, tile, health, attack, defence);
        this.ABILITY_RANGE = range;
        this.arrowCount = 10 * GetLevel();
    }

    @Override
    public void CastAbility() {
        List<Enemy> potenTarget = TargetHandler.CandidateTarget(this, this.GetPosition(), this.ABILITY_RANGE);
        if (potenTarget.size() > 0) {
            Enemy closetEnemy = potenTarget.get(0);
            for (Enemy target : potenTarget) {
                if (this.Distance(target) < this.Distance(closetEnemy))
                    closetEnemy = target;
            }
            messageCallback.Send(String.format("%s fired an arrow at %s.", this.GetName(), closetEnemy.GetName()));
            this.castAbility(closetEnemy, this.GetAttackPoints());
            this.arrowCount -= ARROW_COST;
        } else
            messageCallback.Send(String.format("%s tried to shoot an arrow but there were no enemies in range.", this.GetName()));
    }

    public double Distance(Unit unit) {
        return this.GetPosition().Distance(unit.GetPosition());
    }

    public void TryCastAbility() {
        boolean cast = super.TryCastAbility(this.arrowCount, ARROW_COST);
        if (!cast)
            messageCallback.Send(String.format("%s tried to shoot an arrow but there were no enemies in range.", this.GetName()));
    }

    @Override
    public void LevelUp() {
        level++;
        super.LevelUp();
        this.arrowCount += 10 * this.GetLevel();
        this.attack = (this.GetAttack() + this.GetLevel() * 2);
        this.defense = (this.GetDefence() + GetLevel());
    }

    @Override
    public void Turn(int turnCount) {
        super.Turn(turnCount);
        if (tickCount == 10) {
            arrowCount += GetLevel();
            tickCount = 0;
        } else
            tickCount += 1;
    }

    public String Describe() {
        return super.Describe() + "\t\tArrows: " + this.arrowCount;
    }

    @Override
    public void ProcessStep() {

    }

    @Override
    public Unit Copy() {
        return null;
    }

    @Override
    public char GetInput() {
        return 0;
    }

    @Override
    public int compareTo(Tile o) {
        return 0;
    }
}
