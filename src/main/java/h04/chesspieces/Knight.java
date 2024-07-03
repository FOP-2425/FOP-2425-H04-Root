package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;
import h04.template.ChessUtils;

import java.awt.Point;

public class Knight extends Robot implements ChessPiece {

    private final Team team;

    public Knight(final int x, final int y, final Team team) {
        super(x, y, team == Team.WHITE ? Families.KNIGHT_WHITE : Families.KNIGHT_BLACK);
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
        int index = 0;
        for (final Point delta : new Point[]{new Point(1, 2), new Point(2, 1)}) {
            for (int i = 0; i < 4; i++) {
                final Point move = new Point(getX() + delta.x, getY() + delta.y);
                if (ChessUtils.isValidCoordinate(move) && ChessUtils.getTeamAt(move) != getTeam()) {
                    possibleMoves[index++] = move;
                }
                //noinspection SuspiciousNameCombination
                delta.setLocation(-delta.y, delta.x);
            }
        }
        return possibleMoves;
    }
}
