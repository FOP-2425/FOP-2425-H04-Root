package h04.movement;

import fopbot.World;
import h04.chesspieces.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static h04.Links.CHESS_PIECE_GET_TEAM_LINK;
import static h04.Links.CHESS_PIECE_GET_X_LINK;
import static h04.Links.CHESS_PIECE_GET_Y_LINK;
import static h04.Links.CHESS_PIECE_LINK;
import static h04.Links.DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK;
import static h04.Links.DIAGONAL_MOVER_LINK;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.callObject;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class DiagonalMoverTest {

    @Test
    public void testClassHeader() {
        TypeLink diagonalMoverLink = DIAGONAL_MOVER_LINK.get();
        assertTrue(diagonalMoverLink.interfaces().contains(CHESS_PIECE_LINK.get()), emptyContext(), result ->
            "Interface DiagonalMover does not extend ChessPiece");
    }

    @Test
    public void testMethodHeader() {
        MethodLink getDiagonalMovesLink = DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK.get();
        assertEquals(Point[].class, getDiagonalMovesLink.returnType().reflection(), emptyContext(), result ->
            "Return type of method getDiagonalMoves() is incorrect");
        assertTrue(getDiagonalMovesLink.reflection().isDefault(), emptyContext(), result ->
            "Method getDiagonalMoves() is not declared default");
    }

    @Test
    public void testGetDiagonalMoves() {
        int worldSize = 8;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);

        for (int x : new int[] {0, worldSize - 1}) {
            for (int y : new int[] {0, worldSize - 1}) {
                Answer<?> answer = invocation -> {
                    if (invocation.getMethod().equals(CHESS_PIECE_GET_X_LINK.get().reflection())) {
                        return x;
                    } else if (invocation.getMethod().equals(CHESS_PIECE_GET_Y_LINK.get().reflection())) {
                        return y;
                    } else if (invocation.getMethod().equals(CHESS_PIECE_GET_TEAM_LINK.get().reflection())) {
                        return Team.WHITE;
                    } else if (invocation.getMethod().equals(DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK.get().reflection())) {
                        return invocation.callRealMethod();
                    } else {
                        return Mockito.RETURNS_DEFAULTS.answer(invocation);
                    }
                };
                Object diagonalMoverInstance = Mockito.mock(DIAGONAL_MOVER_LINK.get().reflection(), answer);
                Context context = contextBuilder()
                    .add("ChessPiece x-coordinate", x)
                    .add("ChessPiece y-coordinate", y)
                    .build();
                Point[] returnValue = callObject(() -> DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK.get().invoke(diagonalMoverInstance), context, result ->
                    "An exception occurred while invoking getDiagonalMoves()");

                assertNotNull(returnValue, context, result -> "getDiagonalMoves() returned null");
                List<Point> points = Arrays.stream(returnValue).filter(Objects::nonNull).collect(Collectors.toList());
                for (Point vector : new Point[] {new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)}) {
                    for (int n = 1; n < worldSize; n++) {
                        if (vector.x * n + x < 0 || vector.x * n + x >= worldSize || vector.y * n + y < 0 || vector.y * n + y >= worldSize) {
                            break;
                        }
                        Point expectedPoint = new Point(vector.x * n + x, vector.y * n + y);
                        assertTrue(points.contains(expectedPoint), context, result ->
                            "Point %s was not found in returned array".formatted(expectedPoint));
                        points.remove(expectedPoint);
                    }
                }
                assertEquals(Collections.emptyList(), points, context, result -> "Returned array contained more points than are valid");
            }
        }
    }
}
