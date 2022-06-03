package com.company;

public class Warrior extends Player{

    private int Experience;
    private int PlayerLevel;
    private int Cooldown;

    public Warrior(String name, Health health, int attack, int defence,int cooldown){

    }

    public int RestCooldown(){
        return 0;
    }
    @Override
    public void CastAbility(){

    }

    @Override
    public String ToString() {
        return null;
    }

    @Override
    public void LevelUp() {

    }

    @Override
    public void GameTick() {

    }
}
