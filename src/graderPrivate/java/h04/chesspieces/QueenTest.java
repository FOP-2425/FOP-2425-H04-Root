package h04.chesspieces;

import fopbot.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static h04.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;

@TestForSubmission
public class QueenTest {

    @Test
    public void testClassHeader() {
        List<Class<?>> queenInterfaces = Arrays.asList(Queen.class.getInterfaces());
        assertTrue(queenInterfaces.contains(ORTHOGONAL_MOVER_LINK.get().reflection()), emptyContext(), result ->
            "Class Queen does not implement interface OrthogonalMover");
        assertTrue(queenInterfaces.contains(DIAGONAL_MOVER_LINK.get().reflection()), emptyContext(), result ->
            "Class Queen does not implement interface DiagonalMover");
    }

    @Test
    public void testGetPossibleMoveFields_Correct() {
        int worldSize = 8;

        for (int x : new int[] {0, worldSize - 1}) {
            for (int y : new int[] {0, worldSize - 1}) {
                World.setSize(worldSize, worldSize);
                World.setDelay(0);
                Queen queenInstance = new Queen(x, y, Team.WHITE);
                Context context = contextBuilder()
                    .add("world size", worldSize)
                    .add("x-coordinate", x)
                    .add("y-coordinate", y)
                    .build();
                Point[] returnValue = callObject(() -> QUEEN_GET_POSSIBLE_MOVE_FIELDS_LINK.get().invoke(queenInstance), context, result ->
                    "An exception occurred while invoking getPossibleMoveFields()");

                assertNotNull(returnValue, context, result -> "getPossibleMoveFields() returned null");
                List<Point> points = Arrays.stream(returnValue).filter(Objects::nonNull).collect(Collectors.toList());
                Point[] vectors = new Point[] {
                    new Point(0, 1),
                    new Point(1, 1),
                    new Point(1, 0),
                    new Point(1, -1),
                    new Point(0, -1),
                    new Point(-1, -1),
                    new Point(-1, 0),
                    new Point(-1, 1),
                };
                for (Point vector : vectors) {
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

    @Test
    public void testGetPossibleMoveFields_Combine() {
        int worldSize = 3;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);

        Point[] orthogonalPoints = new Point[] {new Point(1, 0), new Point(1, 2)};
        Point[] diagonalPoints = new Point[] {new Point(0, 0), new Point(2, 2)};
        Queen queenMock = Mockito.mock(Queen.class, Mockito.withSettings()
            .useConstructor(1, 1, Team.WHITE)
            .defaultAnswer(invocation -> {
                if (invocation.getMethod().equals(ORTHOGONAL_MOVER_GET_ORTHOGONAL_MOVES_LINK.get().reflection())) {
                    return orthogonalPoints;
                } else if (invocation.getMethod().equals(DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK.get().reflection())) {
                    return diagonalPoints;
                } else {
                    return invocation.callRealMethod();
                }
            }));
        Context context = contextBuilder()
            .add("world size", worldSize)
            .add("x-coordinate", queenMock.getX())
            .add("y-coordinate", queenMock.getY())
            .build();
        Point[] returnValue = callObject(() -> QUEEN_GET_POSSIBLE_MOVE_FIELDS_LINK.get().invoke(queenMock), context, result ->
            "An exception occurred while invoking getPossibleMoveFields()");

        assertNotNull(returnValue, context, result -> "getPossibleMoveFields() returned null");
        Set<Point> expectedPoints = Stream.of(orthogonalPoints, diagonalPoints).flatMap(Stream::of).collect(Collectors.toSet());
        Set<Point> actualPoints = Arrays.stream(returnValue).filter(Objects::nonNull).collect(Collectors.toSet());
        assertEquals(expectedPoints, actualPoints, context, result ->
            "Method did not merge results of getOrthogonalMoves() and getDiagonalMoves()");
    }
}
