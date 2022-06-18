package Game.Tiles.Units.Player;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Tile;

import java.util.List;

public class Rogue extends Player {
    private int currentEnergy;
    private int cost;
    private final int MAX_ENERGY=100;
    private final String ABILITY_NAME="Fan of Knives";
    private final int ABILITY_RANGE=2;

    public Rogue(String name,char tile, int health,int attack,int defence,int cost){
        super(name,health,attack,defence);
        this.cost=cost;
        currentEnergy=MAX_ENERGY;
    }

    @Override
    public void CastAbility(){
        List<Enemy> potenTarget= TargetHandler.CandidateTarget(this,this.GetPosition(),this.ABILITY_RANGE);
        messageCallback.Send(String.format("%s cast %s.",this.GetName(), this.ABILITY_NAME));
        for(Enemy target:potenTarget){
            this.castAbility(target,this.GetAttackPoints());
        }
        this.currentEnergy-=cost;
    }

    public void TryCastAbility(){
        boolean cast=super.TryCastAbility(currentEnergy,cost);
        if(!cast)
            messageCallback.Send(String.format("%s tried to cast %s , but there was not enough energy: %s/%t.",this.GetName(), this.ABILITY_NAME, currentEnergy, cost));
    }
    @Override
    public void LevelUp(){
        super.LevelUp();
        this.currentEnergy=MAX_ENERGY;
        this.attack = (this.GetAttackPoints()+this.GetLevel()*3);
    }
    @Override
    public void Turn(int turnCount){
        super.Turn(turnCount);
        currentEnergy=Math.min(MAX_ENERGY,currentEnergy+10);

    }
    public String Describe() {
        return super.Describe()+"\t\tEnergy: "+currentEnergy+"/"+MAX_ENERGY;
    }

    @Override
    public void ProcessStep() {

    }

    public Rogue Copy()
    {
        return new Rogue(this.GetName(), playerTile, this.GetHealth().GetAmount(),this.GetAttackPoints(),this.GetDefence(),this.cost);
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
