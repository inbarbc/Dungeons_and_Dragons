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
        super(name, tile, health, attack, defence);
        this.abilityCooldown = cooldown;
    }

    // cast the ability for a Warrior
    @Override
    public void castAbility() {
        List<Enemy> potenTarget = TargetHandler.candidateTarget(this, this.getPosition(), this.ABILITY_RANGE);
        int healBuff = 10 * getDefence();
        messageCallback.send(String.format("%s cast %s, healing for %d.", this.getName(), this.ABILITY_NAME, healBuff));
        this.getHealth().setAmount(getHealth().getAmount() + healBuff);
        if (potenTarget.size() > 0) {
            int random = new Random().nextInt(potenTarget.size());
            this.castAbility(potenTarget.get(random),(int) (this.getHealth().getAmount() * 0.1));
        }
        this.remainingCooldown = this.abilityCooldown + 1;

    }

    // level up for this Warrior
    @Override
    public void levelUp() {
        super.levelUp();
        remainingCooldown = 0;
        this.getHealth().setPool(getHealth().getPool() + 5 * this.getLevel());
        this.attack = (this.getAttackPoints() + this.getLevel() * 2);
        this.defense = (this.getDefence() + getLevel());

    }

    // this Warrior makes a turn
    @Override
    public void turn(int turnCount) {
        super.turn(turnCount);
        remainingCooldown = Math.max(remainingCooldown - 1, 0);

    }

    // the Warrior describe
    @Override
    public String describe() {
        return super.describe() + "\t\tCooldown: " + remainingCooldown + "/" + abilityCooldown;
    }

    // a copy of the Warrior
    public Warrior copy() {
        return new Warrior(this.getName(), playerTile, this.getHealth().getAmount(), this.getAttackPoints(), this.getDefence(), this.abilityCooldown);
    }

    // the Warrior try to cast his ability
    public void tryCastAbility() {
        boolean cast = super.tryCastAbility(abilityCooldown - remainingCooldown, abilityCooldown);
        if (!cast)
            messageCallback.send(String.format("%s tried to cast %s, but there is a cooldown :%t.",this.getName(), this.ABILITY_NAME, remainingCooldown));
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
