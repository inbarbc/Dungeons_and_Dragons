package Game.Tiles.Units;

import Game.Callbacks.MessageCallback;
import Game.GameManager;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Utils.Position;

import java.util.Random;

public abstract class Unit extends Tile {

    private char tile;
    protected static Random rnd = new Random(123);
    protected MessageCallback messageCallback;
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    private Position position = new Position();
    public static GameManager gameManager;

    // return the unit name
    public String getName() {
        return name;
    }

    // return the health of the unit
    public Resource getHealth() {
        return health;
    }

    // return the attack points of the unit
    public int getAttack() {
        return attack;
    }

    // return the defence points of the unit
    public int getDefence() {
        return defense;
    }

    public Unit(String name, char tile, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }

    public Unit(char tile, String name, int healthCapacity, int attack, int defense, Position position) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
        this.position = position;
    }

    // initialize the position of this Tile
    protected void initialize(Position position, MessageCallback messageCallback) {
        super.initialize(position);
        this.messageCallback = messageCallback;
    }

    // make a combat between this Unit and Unit defender
    public void attack(Unit defender) {
        this.attack(defender, this.getAttackPoints());
    }

    // return the attack points of this Unit
    public int getAttackPoints() {
        return attack;
    }

    // make a combat between this Unit and Unit defender
    public void attack(Unit defender, int attackPoints) {
        int result = rnd.nextInt(attackPoints + 1);
        messageCallback.send(String.format("%s rooled %d attack points.", getName(), result));
        combat(defender);
    }

    // abstract method
    public abstract void onDeath();

    // if the unit is an empty - it is swap the positions
    public void visit(Empty e) {
        swapPosition(e);
    }

    // if the unit is a wall - it is not moving
    public void visit(Wall w) {
    }

    // abstract method - if the Unit is a player
    public abstract void visit(Player p);

    // abstract method - if the Unit is an enemy
    public abstract void visit(Enemy e);

    // print the combat between this Unit and Unit u
    protected void combat(Unit u) {
        messageCallback.send(String.format("%s engaged in combat with %s.\n%s\n%s", getName(), u.getName(), describe(), u.describe()));
        int damageDone = Math.max(attack - u.defense, 0);
        u.health.reduceAmount(damageDone);
        messageCallback.send(String.format("%s dealt %d damage to %s.", getName(), damageDone, u.getName()));
    }

    // return a string that describe the unit
    public String describe() {
        return String.format("%s\t\tHeath: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().toString(), getAttack(), getDefence());
    }

    // return if the unit is dead
    public boolean isDead() {
        return health.isDead();
    }

    // swap the position between this Unit and an empty Tile
    protected void swapPosition(Empty t) {
        Position p = t.getPosition();
        t.setPosition(this.getPosition());
        this.setPosition(p);
    }

    // swap the position between this Unit and an enemy
    protected void swapPosition(Enemy t) {
        Position p = t.getPosition();
        t.setPosition(this.getPosition());
        this.setPosition(p);
    }

    // abstract method
    public abstract Unit copy();

    // set the char of this Tile
    public void setTile(char tile) {
        this.tile = tile;
    }

    public int[] defence(int ar) {
        int[] combatInfo = new int[2];
        combatInfo[0] = rnd.nextInt(getDefence());
        combatInfo[1] = Math.max(ar - combatInfo[0], 0);
        this.getHealth().setAmount(getHealth().getAmount() - combatInfo[1]);
        return combatInfo;
    }

    // abstract method of a turn
    public abstract void turn(int tickCount);
}