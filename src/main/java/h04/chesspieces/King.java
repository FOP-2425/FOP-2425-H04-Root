package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;
import h04.template.ChessUtils;

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
    public void moveStrategy(final int dx, final int dy, final MoveStrategy strategy) {

    }

    @Override
    public Point[] getPossibleMoveFields() {
        return ChessUtils.getAllowedMoves(
            this,
            new Point[]{
                new Point(1, 0),
                new Point(-1, 0),
                new Point(0, 1),
                new Point(0, -1),
                new Point(1, 1),
                new Point(-1, 1),
                new Point(1, -1),
                new Point(-1, -1)
            },
            1
        );
    }
}
