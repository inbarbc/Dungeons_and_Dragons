package com.company;

public class Health {
    private int healthPool;
    private int currentHealth;

    public Health(int healthPool,int currentHealth){
        this.healthPool = healthPool;
        this.currentHealth = currentHealth;
    }

    public String ToString(){
        return "Health Pool = " + healthPool + " | " + " Current Health = " + currentHealth;
    }
}
