package Game.Tiles.Units.Player;

import Game.Callbacks.MessageCallback;
import Game.Callbacks.PlayerDeathCallback;
import Game.Handelers.MoveHandler;
import Game.Tiles.Units.Actions.Action;
import Game.Tiles.Units.HeroicUnit;
import Game.Tiles.Units.Unit;
import Game.Handelers.InputHandler;
import View.Input.InputProvider;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Utils.Position;
import View.Input.InputQuery;

public abstract class Player extends Unit implements HeroicUnit, InputQuery {

    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    protected static final int HEALTH_BONUS = 10;
    public String abilityName="";

    protected int level;
    protected int experience;

    protected PlayerDeathCallback deathCallback;
    protected InputProvider inputProvider;

    public Player(){}

    public Player(String name, char tile, int health, int attack, int defence) {
        super(name, tile, health, attack, defence);
    }

    protected Player(String name, int healthCapacity, int attack, int defense) {
        super(playerTile, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public Player Initialize(Position position, MessageCallback messageCallback, PlayerDeathCallback deathCallback, InputProvider inputPruvider) {
        super.Initialize(position, messageCallback);
        this.deathCallback = deathCallback;
        this.inputProvider = inputPruvider;
        return this;
    }

    public void Accept(Unit u) {
        u.Visit(this);
    }

    public void Visit(Enemy e) {
        super.Combat(e);
        if (e.IsDead()) {
            SwapPosition(e);
            OnKill(e);
        }
    }

    protected void AbilityDamage(Enemy e, int abilityDamage) {
        int damageDone = Math.max(abilityDamage - e.GetDefence(), 0);
        e.GetHealth().ReduceAmount(damageDone);
        messageCallback.Send(String.format("%s hit %s for %d ability damage.", GetName(), e.GetName(), damageDone));
        if (e.IsDead()) {
            OnKill(e);
        }
    }

    protected void OnKill(Enemy e) {
        int experienceGained = e.GetExprienceValue();
        messageCallback.Send(String.format("%s died. %s gained %d experience.", e.GetName(), GetName(), experienceGained));
        AddExperience(experienceGained);
        e.OnDeath();
    }

    public void OnDeath() {
        messageCallback.Send("You lost.");
        deathCallback.Call();
    }

    protected void AddExperience(int experienceGained) {
        this.experience += experienceGained;
        int nextLevelReq = LevelUpRequirement();
        while (experienceGained >= nextLevelReq) {
            LevelUp();
            experience -= nextLevelReq;
            nextLevelReq = LevelUpRequirement();
        }
    }

    protected void LevelUp() {
        level++;
        int healthGained = GainHealth();
        int attackGained = GainAttack();
        int defenseGained = GainDefense();
        health.AddCapacity(healthGained);
        health.Restore();
        attack += attackGained;
        defense += defenseGained;
        messageCallback.Send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense.", GetName(), GetLevel(), healthGained, attackGained));
    }

    public String ToString() {
        return !IsDead() ? super.ToString() : "x";
    }

    protected int GainHealth() {
        return level * HEALTH_BONUS;
    }

    protected int GainAttack() {
        return level * ATTACK_BONUS;
    }

    protected int GainDefense() {
        return level * DEFENSE_BONUS;
    }

    protected int LevelUpRequirement() {
        return REQ_EXP * DEFENSE_BONUS;
    }

    public int GetLevel(){
        return level;
    }

    public InputProvider GetInputProvider() {
        return inputProvider;
    }

    public int GetExprienceValue() {
        return experience;
    }

    public void Visit(Player p){}

    public String Describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.Describe(), GetLevel(), GetExprienceValue(), LevelUpRequirement());
    }

    public Boolean TryCastAbility(int resource, int cost) {
        if (resource - cost >= 0) {
            CastAbility();
            return true;
        }
        return false;
    }

    public  void castAbility(){}

    public void castAbility(Enemy defender,int ap){
        int [] combatInfo = defender.Defence(ap);
        messageCallback.Send(String.format("%s rolled %d: %d defence points.", defender.GetName(), combatInfo[0]));
        messageCallback.Send(String.format("%s hit %d: %d for %d ability damage.", GetName(), defender.GetName(), combatInfo[1]));
        if(defender.IsDead()) {
            messageCallback.Send(String.format("%s died. %d gained %d experience.", defender.GetName(), this.GetName(), defender.experienceValue));
            experience = (experience+ defender.experienceValue);
        }
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

    public void Move(InputProvider moveDir) {
        MoveHandler.Move(moveDir, this);
    }
}
