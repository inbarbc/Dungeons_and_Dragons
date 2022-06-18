package Game.Tiles.Units.Enemies;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;
import View.Input.InputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends Enemy {
    private int visionRange = 0;
    private InputProvider rndArrs[] = {InputProvider.Wait, InputProvider.Right, InputProvider.Left, InputProvider.Up, InputProvider.Down};

    public Monster(String name, char tile, int hp, int ap, int dp, int xp, int visionRange) {
        super(name, tile, hp, ap, dp, xp);
        this.visionRange = visionRange;
    }

    public void Turn(int turnCount) {
        super.Turn(turnCount);
        List<Player> closePlayer = new ArrayList<>();
        closePlayer = TargetHandler.CandidateTarget(this, visionRange);
        if (closePlayer.size() > 0) {
            int dx = this.GetPosition().x - closePlayer.get(0).GetPosition().x;
            int dy = this.GetPosition().y - closePlayer.get(0).GetPosition().y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    this.Move(InputProvider.Left);
                else
                    this.Move(InputProvider.Right);
            } else {
                if (dy > 0)
                    this.Move(InputProvider.Up);
                else
                    this.Move(InputProvider.Down);
            }
        } else {
            this.Move(rndArrs[(new Random().nextInt(5))]);
        }
    }

    @Override
    public void OnDeath() {

    }

    @Override
    public void Visit(Player p) {

    }

    @Override
    public void Visit(Enemy e) {

    }

    @Override
    public void ProcessStep() {

    }

    public Unit Copy() {
        return new Monster(this.GetName(), this.toString().charAt(0), this.GetHealth().GetAmount(), this.GetAttackPoints(), this.GetDefence()
                , this.experienceValue, this.visionRange);
    }

    public String Description() {
        return super.Describe() + "\t\tVision Range: " + this.visionRange;
    }

    @Override
    public void CastAbility() {

    }

    @Override
    public void Accept(Unit unit) {

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
