package h04.chesspieces;

import fopbot.Robot;

public class Pawn extends Robot implements ChessPiece {
    private Team team;

    public Pawn(final int x, final int y, final Team team) {
        super(x, y, team == Team.WHITE ? Families.PAWN_WHITE : Families.PAWN_BLACK);
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void moveStrategy(int dx, int dy) {

    }
}
