package com.mygdx.game.Entities.GameEntities.Enemies;

public class Cucumber extends Enemy {

    public Cucumber(int id) {
        super("cucumber", id, 100, 10, 1f);
    }

    @Override
    public void move() {
        // right now it will not move
    }

    @Override
    public void attack() {
        // right now it will not attack
    }
}
