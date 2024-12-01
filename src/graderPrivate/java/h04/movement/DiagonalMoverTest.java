package h04.movement;

import fopbot.World;
import h04.chesspieces.ChessPiece;
import h04.chesspieces.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.ClassHeader;
import org.tudalgo.algoutils.transform.util.MethodHeader;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.lang.reflect.Method;
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
public class DiagonalMoverTest {

    private final SubmissionExecutionHandler executionHandler = SubmissionExecutionHandler.getInstance();

    private static Method getDiagonalMovesMethod;

    @BeforeAll
    public static void setup() {
        try {
            getDiagonalMovesMethod = DiagonalMover.class.getDeclaredMethod("getDiagonalMoves");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        executionHandler.resetMethodInvocationLogging();
        executionHandler.resetMethodSubstitution();
        executionHandler.resetMethodDelegation();
    }

    @Test
    public void testClassHeader() {
        ClassHeader originalClassHeader = SubmissionExecutionHandler.getOriginalClassHeader(DiagonalMover.class);
        assertTrue(Arrays.stream(originalClassHeader.interfaces()).anyMatch(s -> s.equals(Type.getInternalName(ChessPiece.class))),
            emptyContext(),
            result -> "Interface h04.movement.DiagonalMover does not extend interface h04.movement.ChessPiece");
    }

    @Test
    public void testMethodHeader() {
        MethodHeader getDiagonalMovesMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(DiagonalMover.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("getDiagonalMoves") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.getType(Point[].class))))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "DiagonalMover does not declare method 'getDiagonalMoves()'"));
        assertTrue(Modifier.isPublic(getDiagonalMovesMethodHeader.access()), emptyContext(), result ->
            "Method getDiagonalMoves() is not declared public");
        assertFalse(Modifier.isAbstract(getDiagonalMovesMethodHeader.access()), emptyContext(), result ->
            "Method getDiagonalMoves() is not declared default");
    }

    @Test
    public void testGetDiagonalMoves() {
        executionHandler.disableMethodDelegation(getDiagonalMovesMethod);
        int worldSize = 8;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);

        for (int x : new int[] {0, worldSize - 1}) {
            for (int y : new int[] {0, worldSize - 1}) {
                DiagonalMover diagonalMoverInstance = new DiagonalMover() {
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
                Point[] returnValue = callObject(diagonalMoverInstance::getDiagonalMoves, context, result ->
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
