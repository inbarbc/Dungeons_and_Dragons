package Game.Tiles.Units;

import Game.Callbacks.MessageCallback;
import Game.GameManager;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Utils.Position;
import Game.Utils.Movement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

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

    public Unit(){}

    public String GetName(){
        return name;
    }

    public Resource GetHealth(){
        return health;
    }

    public int GetAttack(){
        return attack;
    }

    public int GetDefence(){
        return defense;
    }

    public Unit(char tile, String name, int healthCapacity, int attack, int defense){
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }
    public Unit(char tile, String name, int healthCapacity, int attack, int defense, Position position){
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
        this.position = position;
    }

    public Unit(String name, char tile, int hp, int ap, int dp){
        this( tile, name,  hp,  ap,  dp, new Position());
    }

    protected void Initialize(Position position, MessageCallback messageCallback){
        super.Initialize(position);
        this.messageCallback = messageCallback;
    }

    protected int Attack(){
        int result = rnd.nextInt(attack);
        messageCallback.Send(String.format("%s rooled %d attack points.", GetName(), result));
        return result;
    }

    public void Attack(Unit defender){
        this.Attack(defender,this.GetAttackPoints());
    }

    public int GetAttackPoints() {
        return attack;
    }

    public void Attack(Unit defender,int ap){
        int result = rnd.nextInt(ap + 1);
        messageCallback.Send(String.format("%s rooled %d attack points.", GetName(), result));
        Combat(defender);
    }

    public abstract void OnDeath();

    public void Visit(Empty e){
        SwapPosition(e);
    }

    public void Visit(Wall w){
    }

    public abstract void Visit(Player p);

    public abstract void Visit(Enemy e);

    protected void Combat(Unit u){
        messageCallback.Send(String.format("%s engaged in combat with %s.\n%s\n%s", GetName(), u.GetName(), Describe(), u.Describe()));
        int damageDone = Math.max(attack - u.defense, 0);
        u.health.ReduceAmount(damageDone);
        messageCallback.Send(String.format("%s dealt %d damage to %s.", GetName(), damageDone, u.GetName()));
    }

    public int[] Sefence(int ar){
        int [] combatInfo = new int[2];
        combatInfo[0] = rnd.nextInt(GetDefence());
        combatInfo[1] = Math.max(ar - combatInfo[0], 0);
        this.GetHealth().SetAmount((GetHealth().GetAmount() - combatInfo[1]));
        return combatInfo;
    }

    public void Interact(Tile tile){
        tile.Accept(this);
    }

    public String Describe(){
        return String.format("%s\t\tHeath: %s\t\tAttack: %d\t\tDefense: %d", GetName(), GetHealth(), GetAttack(), GetDefence());
    }

    public boolean IsDead(){
        return health.IsDead();
    }

    protected void SwapPosition(Empty t){
        Position p = t.GetPosition();
        t.SetPosition(this.GetPosition());
        this.SetPosition(p);
    }

    protected void SwapPosition(Enemy t){
        Position p = t.GetPosition();
        t.SetPosition(this.GetPosition());
        this.SetPosition(p);
    }

    public abstract void ProcessStep();

    public abstract Unit Copy();

    public void setTile(char tile) {
        this.tile = tile;
    }

    public int[] Defence(int ar){
        int [] combatInfo = new int[2];
        combatInfo[0] = rnd.nextInt(GetDefence());
        combatInfo[1] = Math.max(ar - combatInfo[0], 0);
        this.GetHealth().SetAmount(GetHealth().GetAmount() - combatInfo[1]);
        return combatInfo;
    }

    public abstract void Turn(int tickCount);
}