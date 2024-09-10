package h04.movement;


import fopbot.Direction;
import fopbot.Robot;

public class WalkingMoveStrategy implements MoveStrategy {
    @Override
    public void move(final Robot r, int dx, int dy) {
        while (dx != 0) {
            if (dx > 0) {
                turnToDirection(r, Direction.RIGHT);
                r.move();
                dx--;
            } else {
                turnToDirection(r, Direction.LEFT);
                r.move();
                dx++;
            }
        }
        while (dy != 0) {
            if (dy > 0) {
                turnToDirection(r, Direction.UP);
                r.move();
                dy--;
            } else {
                turnToDirection(r, Direction.DOWN);
                r.move();
                dy++;
            }
        }
        turnToDirection(r, Direction.UP);
    }

    private void turnToDirection(final Robot r, final Direction d) {
        while (r.getDirection() != d) {
            r.turnLeft();
        }
    }
}
