package h04.chesspieces;

import fopbot.Robot;
import h04.movement.DiagonalMover;
import h04.movement.MoveStrategy;

import java.awt.Point;


public class Bishop extends Robot implements ChessPiece, DiagonalMover {

    private final Team team;

    public Bishop(final int x, final int y, final Team team){
        super(x, y, team == Team.WHITE ? Families.BISHOP_WHITE : Families.BISHOP_BLACK);
        this.team = team;
    }

    @Override
    public Team getTeam() { return team;}

    @Override
    public void moveStrategy(final int dx, final int dy, final MoveStrategy strategy){strategy.move(this, dx, dy);}

    @Override
    public Point[] getPossibleMoveFields() {
        return getDiagonalMoves();
    }
}
