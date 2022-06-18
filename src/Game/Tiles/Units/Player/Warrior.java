package Game.Tiles.Units.Player;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown = 0;
    private final String ABILITY_NAME = "Avenger's Shield";
    private final int ABILITY_RANGE = 3;


    public Warrior(String name,char tile, int health, int attack, int defence, int cooldown) {
        super(name, health, attack, defence);
        this.abilityCooldown = cooldown;
    }

    @Override
    public void castAbility() {
        List<Enemy> potenTarget = TargetHandler.CandidateTarget(this, this.GetPosition(), this.ABILITY_RANGE);
        List<String> message = new ArrayList<String>();
        int healBuff = 10 * GetDefence();
        messageCallback.Send(String.format("%s cast %d, healing for %d.", this.GetName(), this.ABILITY_NAME, healBuff));
        this.GetHealth().SetAmount(GetHealth().GetAmount() + healBuff);
        if (potenTarget.size() > 0) {
            int random = new Random().nextInt(potenTarget.size());
            this.castAbility(potenTarget.get(random),(int) (this.GetHealth().GetAmount() * 0.1));
        }
        this.remainingCooldown = this.abilityCooldown + 1;

    }

    @Override
    public void LevelUp() {
        super.LevelUp();
        remainingCooldown = 0;
        this.GetHealth().SetPool(GetHealth().GetPool() + 5 * this.GetLevel());
        this.attack = (this.GetAttackPoints() + this.GetLevel() * 2);
        this.defense = (this.GetDefence() + GetLevel());

    }

    @Override
    public void Turn(int turnCount) {
        super.Turn(turnCount);
        remainingCooldown = Math.max(remainingCooldown - 1, 0);

    }

    @Override
    public String Describe() {
        return super.Describe() + "\t\tCooldown: " + remainingCooldown + "/" + abilityCooldown;
    }

    @Override
    public void ProcessStep() {

    }

    public Warrior Copy() {
        return new Warrior(this.GetName(), playerTile, this.GetHealth().GetAmount(), this.GetAttackPoints(), this.GetDefence(), this.abilityCooldown);
    }

    public void TryCastAbility() {
        boolean cast = super.TryCastAbility(abilityCooldown - remainingCooldown, abilityCooldown);
        if (!cast)
            messageCallback.Send(String.format("%s tried to cast %s, but there is a cooldown :%t.",this.GetName(), this.ABILITY_NAME, remainingCooldown));
    }

    @Override
    public void CastAbility() {

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
