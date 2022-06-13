package com.company;

public abstract class Player extends Unit{

    public static final char PlayerTile = '@';
    protected static final int EXP = 50;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    protected static final int HEALTH_BONUS = 10;

    protected int Experience;
    protected  int PlayerLevel;

    protected PlayerDeathCallback deathCallback;
    protected InputProvider inputProvider;

    protected Player(String name, int healthCapacity, int attack, int defence){
        super(playerTile)
    }

}
