package h04.movement;

import h04.chesspieces.ChessPiece;
import h04.template.ChessUtils;

import java.awt.Point;

public interface DiagonalMover extends ChessPiece {
    default Point[] getDiagonalMoves() {
        return ChessUtils.getAllowedMoves(
            this,
            new Point[]{
                new Point(1, 1),
                new Point(-1, 1),
                new Point(1, -1),
                new Point(-1, -1)
            },
            3
        );
    }
}
