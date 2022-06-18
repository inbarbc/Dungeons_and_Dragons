package com.company;

import Game.GameManager;

public class Main {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.Start(args[0]);
    }
}
