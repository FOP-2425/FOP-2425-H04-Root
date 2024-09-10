package h04.movement;

import fopbot.Robot;

public class TeleportingMoveStrategy implements MoveStrategy {
    @Override
    public void move(final Robot r, final int dx, final int dy) {
        r.setField(r.getX() + dx, r.getY() + dy);
    }
}
