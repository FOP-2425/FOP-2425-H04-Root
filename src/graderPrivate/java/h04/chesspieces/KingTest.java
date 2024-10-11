package h04.chesspieces;

import fopbot.Robot;
import fopbot.World;
import kotlin.Triple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.concurrent.atomic.AtomicReference;

import static h04.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class KingTest {

    @BeforeAll
    public static void setup() {
        World.setSize(3, 3);
        World.setDelay(0);
    }

    @Test
    public void testMoveStrategy() {
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
}
