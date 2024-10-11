package h04.chesspieces;

import fopbot.Robot;
import fopbot.World;
import kotlin.Triple;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static h04.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class KingTest {

    @Test
    public void testMoveStrategy() {
        World.setSize(3, 3);
        World.setDelay(0);
        AtomicReference<Triple<Robot, Integer, Integer>> moveInvocation = new AtomicReference<>();
        Object moveStrategyMock = Mockito.mock(MOVE_STRATEGY_LINK.get().reflection(), invocation -> {
            if (invocation.getMethod().equals(MOVE_STRATEGY_MOVE_LINK.get().reflection())) {
                moveInvocation.set(new Triple<>(invocation.getArgument(0), invocation.getArgument(1), invocation.getArgument(2)));
            }
            return Mockito.RETURNS_DEFAULTS.answer(invocation);
        });
        int dx = 1;
        int dy = 1;
        Context context = contextBuilder()
            .add("dx", dx)
            .add("dy", dy)
            .add("strategy", moveStrategyMock)
            .build();

        King kingInstance = new King(0, 0, Team.WHITE);
        call(() -> KING_MOVE_STRATEGY_LINK.get().invoke(kingInstance, dx, dy, moveStrategyMock), context, result ->
            "An exception occurred while invoking moveStrategy(int, int, MoveStrategy)");
        assertNotNull(moveInvocation.get(), context, result -> "Method move of MoveStrategy was not called");
        assertSame(kingInstance, moveInvocation.get().getFirst(), context, result ->
            "Method move of MoveStrategy was called with incorrect first parameter");
        assertEquals(dx, moveInvocation.get().getSecond(), context, result ->
            "Method move of MoveStrategy was called with incorrect second parameter");
        assertEquals(dy, moveInvocation.get().getThird(), context, result ->
            "Method move of MoveStrategy was called with incorrect third parameter");
    }

    @Test
    public void testGetPossibleMoveFieldsHeader() {
        MethodLink moveStrategyLink = KING_GET_POSSIBLE_MOVE_FIELDS_LINK.get();  // implicit test that method with identifier + param types exists
        assertEquals(Point[].class, moveStrategyLink.returnType().reflection(), emptyContext(), result ->
            "Return type of method getPossibleMoveFields() is incorrect");
    }

    @Test
    public void testGetPossibleMoveFieldsCorrectAmount() {
        Triple<Context, King, Point[]> invocationResult = invokeGetPossibleMoveFields(5, 2, 2);
        Context context = invocationResult.getFirst();
        Point[] returnValue = invocationResult.getThird();

        assertNotNull(returnValue, context, result -> "Method returned null");
        assertTrue(returnValue.length <= 8, context, result -> "Method returned more than eight possible options");
    }

    @Test
    public void testGetPossibleMoveFieldsExcludesSelf() {
        int x = 2;
        int y = 2;
        Triple<Context, King, Point[]> invocationResult = invokeGetPossibleMoveFields(5, x, y);
        Context context = invocationResult.getFirst();
        Point[] returnValue = invocationResult.getThird();

        Point kingPosition = new Point(x, y);
        assertNotNull(returnValue, context, result -> "Method returned null");
        assertFalse(Arrays.asList(returnValue).contains(kingPosition), context, result ->
            "Method returned a point with the king's current position");
    }

    @Test
    public void testGetPossibleMoveFieldsInWorld() {
        int worldSize = 5;
        Triple<Context, King, Point[]> invocationResult = invokeGetPossibleMoveFields(worldSize, 0, 0);
        Context context = invocationResult.getFirst();
        Point[] returnValue = invocationResult.getThird();

        assertNotNull(returnValue, context, result -> "Method returned null");
        List<Point> pointsOutsideWorld = Arrays.stream(returnValue)
            .filter(Objects::nonNull)  // TODO: not sure if method is allowed to return null elements
            .filter(point -> point.x < 0 || point.x >= worldSize || point.y < 0 || point.y >= worldSize)
            .toList();
        assertEquals(Collections.emptyList(), pointsOutsideWorld, context, result ->
            "Method returned points that were outside the world");
    }

    @Test
    public void testGetPossibleMoveFieldsCorrect() {
        int x = 2;
        int y = 2;
        Triple<Context, King, Point[]> invocationResult = invokeGetPossibleMoveFields(5, x, y);
        Context context = invocationResult.getFirst();
        Point[] returnValue = invocationResult.getThird();

        assertNotNull(returnValue, context, result -> "Method returned null");
        assertEquals(8, returnValue.length, context, result -> "Method did not return eight possible options");
        List<Point> points = Arrays.asList(returnValue);
        int[] deltas = new int[] {-1, 0, 1};
        for (int dx : deltas) {
            for (int dy : deltas) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                assertTrue(points.contains(new Point(x + dx, y + dy)), context, result ->
                    "Method did not return a valid point: (%d|%d)".formatted(x + dx, y + dy));
            }
        }
    }

    private Triple<Context, King, Point[]> invokeGetPossibleMoveFields(int worldSize, int x, int y) {
        World.setSize(worldSize, worldSize);
        World.setDelay(0);
        King kingInstance = new King(x, y, Team.WHITE);
        Context context = contextBuilder()
            .add("world size", worldSize)
            .add("x-coordinate", x)
            .add("y-coordinate", y)
            .build();
        Point[] returnValue = callObject(() -> KING_GET_POSSIBLE_MOVE_FIELDS_LINK.get().invoke(kingInstance), context, result ->
            "An exception occurred while invoking getPossibleMoveFields()");
        return new Triple<>(context, kingInstance, returnValue);
    }
}
