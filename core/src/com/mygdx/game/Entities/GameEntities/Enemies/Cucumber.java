package com.mygdx.game.Entities.GameEntities.Enemies;

public class Cucumber extends Enemy {

    public Cucumber(int id) {
        super("cucumber", id, 100, 10, 1f);
    }

    @Override
    public void move() {
        // this will move to the center
        int x = -1 * (int) Math.signum(this.getX());
        int y = -1 * (int) Math.signum(this.getY());

        float threshold = 10;
        if (this.getX() < threshold && this.getX() > -threshold) x = 0;
        if (this.getY() < threshold && this.getY() > -threshold) y = 0;

        // gets the sign of its position, and negates it to get direction to middle
        this.setSpeedVec(x, y);
    }

    @Override
    public void attack() {
        // right now it will not attack
    }
}
