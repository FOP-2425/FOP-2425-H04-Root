package h04.template;

import fopbot.World;
import h04.chesspieces.ChessPiece;
import h04.chesspieces.Team;
import org.jetbrains.annotations.Nullable;

import java.awt.Point;

public class ChessUtils {
    /**
     * Returns the team at the given position, or {@code null} if there is no team.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the team at the given position, or {@code null} if there is no team
     */
    public static @Nullable Team getTeamAt(final int x, final int y) {
        return World.getGlobalWorld().getField(x, y).getEntities()
            .stream()
            .filter(ChessPiece.class::isInstance)
            .map(ChessPiece.class::cast)
            .findFirst()
            .map(ChessPiece::getTeam)
            .orElse(null);
    }

    public static Point[] getAllowedMoves(final ChessPiece piece, final Point[] directions, final int maxDistance) {
        final Point[] moves = new Point[directions.length * maxDistance];
        int index = 0;
        for (final var p : directions) {
            for (int i = 1; i <= maxDistance; i++) {
                final var team = getTeamAt(piece.getX() + i * p.x, piece.getY() + i * p.y);
                if (team == null)
                    break;
                moves[index++] = new Point(piece.getX() + i * p.x, piece.getY() + i * p.y);
                if (team == piece.getTeam().getOpponent())
                    break;
            }
        }
        return moves;
    }

    public static Point[] mergePoints(final Point[] a, final Point[] b) {
        final Point[] result = new Point[a.length + b.length];
        var index = 0;
        for (final var p : a) {
            if (p == null)
                break;
            result[index++] = p;
        }
        for (final var p : b) {
            if (p == null)
                break;
            result[index++] = p;
        }
        return result;
    }
}
