package h04.chesspieces;

import fopbot.Robot;
import fopbot.World;
import kotlin.Triple;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static h04.Links.BISHOP_GET_POSSIBLE_MOVE_FIELDS_LINK;
import static h04.Links.BISHOP_MOVE_STRATEGY_LINK;
import static h04.Links.DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK;
import static h04.Links.DIAGONAL_MOVER_LINK;
import static h04.Links.MOVE_STRATEGY_LINK;
import static h04.Links.MOVE_STRATEGY_MOVE_LINK;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertSame;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.callObject;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class BishopTest {

    @Test
    public void testClassHeader() {
        List<Class<?>> bishopInterfaces = Arrays.asList(Bishop.class.getInterfaces());
        assertTrue(bishopInterfaces.contains(DIAGONAL_MOVER_LINK.get().reflection()), emptyContext(), result ->
            "Class Bishop does not implement interface DiagonalMover");
    }

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

        Bishop bishopInstance = new Bishop(0, 0, Team.WHITE);
        call(() -> BISHOP_MOVE_STRATEGY_LINK.get().invoke(bishopInstance, dx, dy, moveStrategyMock), context, result ->
            "An exception occurred while invoking moveStrategy(int, int, MoveStrategy)");
        assertNotNull(moveInvocation.get(), context, result -> "Method move of MoveStrategy was not called");
        assertSame(bishopInstance, moveInvocation.get().getFirst(), context, result ->
            "Method move of MoveStrategy was called with incorrect first parameter");
        assertEquals(dx, moveInvocation.get().getSecond(), context, result ->
            "Method move of MoveStrategy was called with incorrect second parameter");
        assertEquals(dy, moveInvocation.get().getThird(), context, result ->
            "Method move of MoveStrategy was called with incorrect third parameter");
    }

    @Test
    public void testGetPossibleMoveFields() {
        World.setSize(3, 3);
        World.setDelay(0);
        Point[] points = new Point[0];
        Bishop bishopMock = Mockito.mock(Bishop.class, Mockito.withSettings()
            .useConstructor(0, 0, Team.WHITE)
            .defaultAnswer(invocation -> {
                if (invocation.getMethod().equals(DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK.get().reflection())) {
                    return points;
                } else {
                    return invocation.callRealMethod();
                }
            }));
        Context context = contextBuilder()
            .add("world size", 3)
            .add("x", 0)
            .add("y", 0)
            .build();

        Point[] returnValue = callObject(() -> BISHOP_GET_POSSIBLE_MOVE_FIELDS_LINK.get().invoke(bishopMock), context, result ->
            "An exception occurred while invoking getPossibleMoveFields()");
        assertSame(points, returnValue, context, result -> "Method did not return the result of getPossibleMoveFields()");
    }
}
