package Game.Tiles.Units.Enemies;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.HeroicUnit;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;
import View.Input.InputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {

    private final int visionRange;
    private final int abilityFrequency;
    private int combatTicks = 0;
    private final InputProvider[] rndArrs ={InputProvider.Wait,InputProvider.Right,InputProvider.Left,InputProvider.Up,InputProvider.Down};

    public Boss(String name, char tile, int hp, int ap, int dp, int xp, int visionRange , int abilityFrequency) {
        super(name, tile, hp, ap, dp, xp);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
    }

    @Override
    public Unit Copy(){
        return new Boss(this.GetName(), this.toString().charAt(0), this.GetHealth().GetAmount(), this.GetAttack(), this.GetDefence()
                ,this.experienceValue,this.visionRange, this.abilityFrequency);
    }

    public void CastAbility(){

    }

    public void CastAbility(Player target) {
        messageCallback.Send(String.format("%s cast special ability.",this.GetName()));
        int[] combatInfo = target.Defence(this.GetAttack());
        messageCallback.Send(String.format("%s rolled %d defence points.",target.GetName(), combatInfo[0]));
        messageCallback.Send(String.format("%s hit %d for %d ability damage.",this.GetName(),target.GetName(), combatInfo[1]));
        if(target.IsDead()){
            messageCallback.Send(String.format("%s was killed by %d.",target.GetName(), this.GetName()));
        }
    }

    @Override
    public void Turn(int turnCount) {
        super.Turn(turnCount);
        List<Player> closePlayer=new ArrayList<>();
        closePlayer= TargetHandler.CandidateTarget(this, GetPosition(), visionRange);
        if(closePlayer.size()>0) {
            if (this.combatTicks == abilityFrequency) {
                this.combatTicks = 0;
                this.CastAbility(closePlayer.get(0));
            } else {
                this.combatTicks += 1;
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
            }
        }
        else
        {
            this.combatTicks = 0;
            this.Move(rndArrs[(new Random().nextInt(5))]) ;
        }
    }

    @Override
    public String Describe(){return super.Describe()+"\t\tVision Range: "+this.visionRange +
            "\t\tAbility load :"+ this.combatTicks +"/"+this.abilityFrequency;
    }

    @Override
    public void Accept(Unit unit) {

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

    @Override
    public char GetInput() {
        return 0;
    }

    @Override
    public int compareTo(Tile o) {
        return 0;
    }
}
