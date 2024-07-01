package h04.chesspieces;

import java.awt.Color;

public interface ChessPiece {
    Team getTeam();
    void moveStrategy(int dx, int dy);
}
