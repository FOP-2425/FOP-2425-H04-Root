package h04.chesspieces;

import fopbot.Robot;
import h04.movement.DiagonalMover;
import h04.movement.MoveStrategy;
import h04.movement.OrthogonalMover;
import h04.template.ChessUtils;

import java.awt.Point;

public class Queen extends Robot implements ChessPiece, OrthogonalMover, DiagonalMover {
    private final Team team;

    public Queen(final int x, final int y, final Team team) {
        super(x, y, team == Team.WHITE ? Families.QUEEN_WHITE : Families.QUEEN_BLACK);
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void moveStrategy(int dx, int dy, MoveStrategy strategy) {
        strategy.move(this, dx, dy);
    }

    @Override
    public Point[] getPossibleMoveFields() {
        return ChessUtils.mergePoints(getOrthogonalMoves(), getDiagonalMoves());
    }
}
