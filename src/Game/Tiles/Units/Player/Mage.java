package Game.Tiles.Units.Player;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Health;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;

import java.util.List;
import java.util.Random;

public class Mage extends Player {

    private int ManaCost;
    private int CurrentMana;
    private int ManaPool;
    private int SpellPower;
    private int HitCount;
    private int AbilityRange;

    // new Mage(name, tile, health, attack, defence, manaPool, manaCost, spellPower, hitCount, range);
    public Mage(String name,char tile, int health, int attack, int defence, int manaPool, int manaCost, int spellPower, int hitCount, int range){
        super(name,health, attack,defence);
        this.name = "Blizzard";
        this.AbilityRange = range;
        this.ManaPool = manaPool;
        this.CurrentMana = manaPool/4;
        this.ManaCost = manaCost;
        this.SpellPower = spellPower;
        this.HitCount = hitCount;
    }

    @Override
    public String ToString() {
        return null;
    }

    @Override
    public String Describe(){
        return super.Describe() + "\t\tMana: " + this.CurrentMana + "/" + this.ManaPool + "\t\tspellPower: " + this.SpellPower;}

    @Override
    public void LevelUp() {
        super.LevelUp();
        this.ManaPool += 25*this.GetLevel();
        this.CurrentMana = Math.min(this.CurrentMana + this.ManaPool/4, this.ManaPool);
        this.SpellPower += 10*GetLevel();

    }

    public void SetExperience(int experience) {
        this.experience = experience;
        while (this.experience>=50* GetLevel()) {
            int[] saveState=new int[]{this.GetHealth().GetPool(),this.GetAttack(),this.GetDefence(),this.ManaPool, this.SpellPower};
            LevelUp();
            messageCallback.Send(this.GetName() + " reached level " + this.GetLevel() + ": +"+
                    (this.GetHealth().GetPool()-saveState[0])+" Health, +"+(this.GetAttack()-saveState[1])+" Attack, +"+(this.GetDefence()-saveState[2])+" Defense, +"+
                    (this.ManaPool - saveState[3])+" maximum mana, +"+(this.SpellPower - saveState[4]) +" spell power");
        }
    }

    @Override
    public void Turn(int tick){
        super.Turn(tick);
        this.CurrentMana = Math.min(CurrentMana + GetLevel(), ManaPool);
    }

    public void GameTick() {

    }

    @Override
    public void CastAbility(){
        List<Enemy> potenTargets = TargetHandler.CandidateTarget(this,this.GetPosition(), AbilityRange);
        messageCallback.Send(String.format("%s cast %d.", this.GetName(), this.abilityName));
        int hits = 0;
        Random rnd = new Random();
        this.CurrentMana -= ManaCost;
        while(hits < HitCount && 0 < potenTargets.size()){
            Enemy target = potenTargets.get(rnd.nextInt(potenTargets.size()));
            this.castAbility(target, this.SpellPower);
            if(target.IsDead())
                potenTargets.remove(target);
            hits++;
        }
    }

    public void TryCastAbility(){
        boolean cast = this.TryCastAbility(CurrentMana , ManaCost);
        if (!cast){
            messageCallback.Send(String.format("%s tried to cast %d, but there was not enough mana: %d/%s", this.GetName(), this.abilityName,CurrentMana, ManaCost));
        }
    }

    @Override
    public void ProcessStep() {

    }

    @Override
    public Mage Copy(){
        return new Mage(this.GetName(), this.toString().charAt(0),this.GetHealth().GetAmount(), this.GetAttack()
                ,this.GetDefence(),this.ManaPool, this.ManaCost, this.SpellPower,this.HitCount,this.AbilityRange);
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
