package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;

import java.awt.Point;

public class King extends Robot implements ChessPiece {

    private final Team team;

    public King(final int x, final int y, final Team team) {
        super(x, y, team == Team.WHITE ? Families.KING_WHITE : Families.KING_BLACK);
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void moveStrategy(int dx, int dy, MoveStrategy strategy) {

    }

    @Override
    public Point[] getPossibleMoveFields() {
        return new Point[0];
    }
}
