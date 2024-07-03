package h04.chesspieces;

import h04.movement.MoveStrategy;

import java.awt.Point;

public interface ChessPiece {
    Team getTeam();
    int getX();
    int getY();
    /**
     * Returns {@code true} if this robot is turned off.
     *
     * @return {@code true} if this robot is turned off
     */
    boolean isTurnedOff();
    void turnOff();
    void moveStrategy(int dx, int dy, MoveStrategy strategy);
    Point[] getPossibleMoveFields();
}
