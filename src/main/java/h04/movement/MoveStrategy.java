package h04.movement;


import fopbot.Robot;

public interface MoveStrategy {
    void move(Robot r, int dx, int dy);
}
