package h04.chesspieces;

import h04.movement.MoveStrategy;

import java.awt.Point;

public interface ChessPiece {
    Team getTeam();
    int getX();
    int getY();
    void moveStrategy(int dx, int dy, MoveStrategy strategy);
    Point[] getPossibleMoveFields();
}
