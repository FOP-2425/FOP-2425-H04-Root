package h04.movement;

import fopbot.World;
import h04.chesspieces.ChessPiece;
import h04.chesspieces.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.headers.ClassHeader;
import org.tudalgo.algoutils.transform.util.headers.MethodHeader;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertFalse;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.callObject;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class OrthogonalMoverTest {

    private final MethodHeader orthogonalMover_getOrthogonalMoves = MethodHeader.of(OrthogonalMover.class, "getOrthogonalMoves");

    @AfterEach
    public void tearDown() {
        SubmissionExecutionHandler.resetAll();
    }

    @Test
    public void testClassHeader() {
        ClassHeader originalClassHeader = SubmissionExecutionHandler.getOriginalClassHeader(OrthogonalMover.class);
        assertTrue(Arrays.stream(originalClassHeader.interfaces()).anyMatch(s -> s.equals(Type.getInternalName(ChessPiece.class))),
            emptyContext(),
            result -> "Interface h04.movement.OrthogonalMover does not extend interface h04.movement.ChessPiece");
    }

    @Test
    public void testMethodHeader() {
        MethodHeader getOrthogonalMovesMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(OrthogonalMover.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("getOrthogonalMoves") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.getType(Point[].class))))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "OrthogonalMover does not declare method 'getOrthogonalMoves()'"));
        assertTrue(Modifier.isPublic(getOrthogonalMovesMethodHeader.access()), emptyContext(), result ->
            "Method getOrthogonalMoves() is not declared public");
        assertFalse(Modifier.isAbstract(getOrthogonalMovesMethodHeader.access()), emptyContext(), result ->
            "Method getOrthogonalMoves() is not declared default");
    }

    @Test
    public void testGetOrthogonalMoves() {
        SubmissionExecutionHandler.Delegation.disable(orthogonalMover_getOrthogonalMoves);
        int worldSize = 8;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);

        for (int x : new int[] {0, worldSize - 1}) {
            for (int y : new int[] {0, worldSize - 1}) {
                OrthogonalMover orthogonalMoverInstance = new OrthogonalMover() {
                    @Override
                    public Team getTeam() {
                        return Team.WHITE;
                    }

                    @Override
                    public int getX() {
                        return x;
                    }

                    @Override
                    public int getY() {
                        return y;
                    }

                    @Override
                    public boolean isTurnedOff() {
                        return false;
                    }

                    @Override
                    public void turnOff() {

                    }

                    @Override
                    public void moveStrategy(int dx, int dy, MoveStrategy strategy) {

                    }

                    @Override
                    public Point[] getPossibleMoveFields() {
                        return new Point[0];
                    }
                };
                Context context = contextBuilder()
                    .add("ChessPiece x-coordinate", x)
                    .add("ChessPiece y-coordinate", y)
                    .build();
                Point[] returnValue = callObject(orthogonalMoverInstance::getOrthogonalMoves, context, result ->
                    "An exception occurred while invoking getOrthogonalMoves()");

                assertNotNull(returnValue, context, result -> "getOrthogonalMoves() returned null");
                List<Point> points = Arrays.stream(returnValue).filter(Objects::nonNull).collect(Collectors.toList());
                for (Point vector : new Point[] {new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)}) {
                    for (int n = 1; n < worldSize; n++) {
                        if (vector.x * n + x < 0 || vector.x * n + x >= worldSize || vector.y * n + y < 0 || vector.y * n + y >= worldSize) {
                            break;
                        }
                        Point expectedPoint = new Point(vector.x * n + x, vector.y * n + y);
                        assertTrue(points.contains(expectedPoint), context, result ->
                            "Point %s was not found in the returned array".formatted(expectedPoint));
                        points.remove(expectedPoint);
                    }
                }
                assertEquals(Collections.emptyList(), points, context, result -> "Returned array contained more points than are valid");
            }
        }
    }
}
