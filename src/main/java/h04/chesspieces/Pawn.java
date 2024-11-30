package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;

import java.awt.Point;

import static h04.template.ChessUtils.getTeamAt;
import static h04.template.ChessUtils.isValidCoordinate;

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
        firstMove = false;
    }

    @Override
    public Point[] getPossibleMoveFields() {
        final Point[] possibleMoves = new Point[4];
        int index = 0;
        final int direction = team == Team.WHITE ? 1 : -1;
        final Point front1 = new Point(getX(), getY() + direction);
        if (isValidCoordinate(front1) && getTeamAt(front1) == null) {
            possibleMoves[index++] = front1;
            final Point front2 = new Point(getX(), getY() + 2 * direction);
            if (firstMove && isValidCoordinate(front2) && getTeamAt(front2) == null) {
                possibleMoves[index++] = front2;
            }
        }
        //attack
        for (final int i : new int[]{-1, 1}) {
            final Point p = new Point(getX() + i, getY() + direction);
            if (isValidCoordinate(p) && getTeamAt(p) == getTeam().getOpponent()) {
                possibleMoves[index++] = p;
            }
        }
        return possibleMoves;
    }
}
