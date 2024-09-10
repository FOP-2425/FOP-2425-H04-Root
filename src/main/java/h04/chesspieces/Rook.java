package h04.chesspieces;

import fopbot.Robot;
import h04.movement.MoveStrategy;
import h04.movement.OrthogonalMover;
import h04.template.ChessUtils;

import java.awt.Point;

public class Rook extends Robot implements OrthogonalMover {
    private final Team team;

    public Rook(final int x, final int y, final Team team){
        super(x, y, team == Team.WHITE ? Families.ROOK_WHITE : Families.ROOK_BLACK);
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
        return getOrthogonalMoves();
    }
}
