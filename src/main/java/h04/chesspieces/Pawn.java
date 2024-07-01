package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;
import h04.template.ChessUtils;

import java.awt.Point;

public class Pawn extends Robot implements ChessPiece {
    private final Team team;

    boolean firstMove = true;

    public Pawn(final int x, final int y, final Team team) {
        super(x, y, team == Team.WHITE ? Families.PAWN_WHITE : Families.PAWN_BLACK);
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void moveStrategy(final int dx, final int dy, final MoveStrategy strategy) {
        strategy.move(this, dx, dy);
    }

    @Override
    public Point[] getPossibleMoveFields() {
        final Point[] possibleMoves = new Point[4];
        final int direction = team == Team.WHITE ? 1 : -1;
        return null;
    }
}
