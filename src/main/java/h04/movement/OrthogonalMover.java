package h04.movement;

import h04.chesspieces.ChessPiece;
import h04.template.ChessUtils;

import java.awt.Point;

public interface OrthogonalMover extends ChessPiece {
    default Point[] getOrthogonalMoves() {
        return ChessUtils.getAllowedMoves(
            this,
            new Point[]{
                new Point(1, 0),
                new Point(-1, 0),
                new Point(0, 1),
                new Point(0, -1)
            },
            7
        );
    }
}
