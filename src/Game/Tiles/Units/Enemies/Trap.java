package Game.Tiles.Units.Enemies;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;
import Game.Utils.Position;

import java.awt.*;
import java.util.List;

public class Trap extends Enemy {
    public int visibilityTime=0;
    public int invisibilityTime=0;
    private boolean visible=true;
    public int tickCount=0;
    private final int VISION_RANGE=2;
    private  char VISIBLE_TILE;
    private  char INVISIBLE_TILE='.';

    public Trap(String name, char tile, int hp, int ap, int dp, int xp, int visibilityTime, int invisibilityTime, Position pos){
        super(name, tile, hp, ap, dp,xp,pos);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        this.VISIBLE_TILE=tile;
    }
    public Trap(String name, char tile, int hp, int ap, int dp, int xp, int visibilityTime, int invisibilityTime){
        this(name, tile, hp, ap, dp,xp,visibilityTime,invisibilityTime, new Position());
    }

    public void Turn(int turnCount){
        setVisible(tickCount<visibilityTime);
        if(tickCount==(visibilityTime+invisibilityTime))
            tickCount=0;
        else
            tickCount+=1;
        List<Player> closePlayer= TargetHandler.CandidateTarget(this,VISION_RANGE);
        if(closePlayer.size()>0)
            this.Attack(closePlayer.get(0));
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
        return new Trap(this.GetName(), this.toString().charAt(0), this.GetHealth().GetAmount(), this.GetAttackPoints(), this.GetDefence()
                ,this.experienceValue,this.visibilityTime,invisibilityTime);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        //SetTile(visible ? VISIBLE_TILE : INVISIBLE_TILE);
    }

    public void setTile(char tile) {
        tile = tile;
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
