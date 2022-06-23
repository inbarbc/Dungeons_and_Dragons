package Game.Tiles.Units.Enemies;

import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Tile;
import Game.Tiles.Units.Unit;
import Game.Utils.Position;

import java.awt.*;
import java.util.List;

public class Trap extends Enemy {

    public int visibilityTime;
    public int invisibilityTime;
    private boolean visible=true;
    public int tickCount=0;
    private final int VISION_RANGE=2;
    private  char VISIBLE_TILE;
    private  char INVISIBLE_TILE='.';

    public Trap(String name, char tile, int health, int attack, int defence, int experience, int visibilityTime, int invisibilityTime, Position position){
        super(name, tile, health, attack, defence,experience,position);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        this.VISIBLE_TILE=tile;
    }
    public Trap(String name, char tile, int health, int attack, int defence, int experience, int visibilityTime, int invisibilityTime){
        this(name, tile, health, attack, defence,experience,visibilityTime,invisibilityTime, new Position());
    }

    // this Trap makes a turn
    public void turn(int turnCount){
        setVisible(tickCount<visibilityTime);
        if(tickCount==(visibilityTime+invisibilityTime))
            tickCount=0;
        else
            tickCount+=1;
        List<Player> closePlayer= TargetHandler.candidateTarget(this, getPosition(), VISION_RANGE);
        if(closePlayer.size()>0)
            this.attack(closePlayer.get(0));
    }

    @Override
    public void onDeath() {}

    @Override
    public void visit(Player p) {}

    @Override
    public void visit(Enemy e) {}

    // a copy of the Trap
    public Unit copy() {
        return new Trap(this.getName(), this.toString().charAt(0), this.getHealth().getAmount(), this.getAttackPoints(), this.getDefence()
                ,this.experienceValue,this.visibilityTime,invisibilityTime);
    }

    // return if the trap is visible
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        setTile(visible ? VISIBLE_TILE : INVISIBLE_TILE);
    }

    // set the tile visibility in this game tick
    public void setTile(char tile) {
        super.setTile(tile);
    }

    @Override
    public void castAbility() {}

    @Override
    public void accept(Unit unit) {}

    @Override
    public char GetInput() {
        return 0;
    }

    @Override
    public int compareTo(Tile o) {
        return 0;
    }
}
