package h04.movement;

import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import static h04.Links.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TeleportingMoveStrategyTest {

    @Test
    public void testClassHeader() {
        TypeLink teleportingMoveStrategyLink = TELEPORTING_MOVE_STRATEGY_LINK.get();  // implicit test that class exists
        assertTrue(teleportingMoveStrategyLink.interfaces().contains(MOVE_STRATEGY_LINK.get()), emptyContext(), result ->
            "Class does not implement interface MoveStrategy");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 8, 9, 10})
    public void testMove(int n) {  // encoding: 4 bits, lower 2: x-coordinate, upper 2: y-coordinate
        Robot robot = setup();
        int expectedX = n & 0b11;
        int expectedY = n >> 2;
        int dx = expectedX - 1;
        int dy = expectedY - 1;

        Context context = contextBuilder()
            .add("robot", robot)
            .add("dx", dx)
            .add("dy", dy)
            .build();
        Object teleportingMoveStrategyInstance = callObject(() -> TELEPORTING_MOVE_STRATEGY_CONSTRUCTOR_LINK.get().invoke(), context, result ->
            "An exception occurred while invoking constructor of TeleportingMoveStrategy");
        call(() -> TELEPORTING_MOVE_STRATEGY_MOVE_LINK.get().invoke(teleportingMoveStrategyInstance, robot, dx, dy), context, result ->
            "An exception occurred while invoking method move");

        assertEquals(expectedX, robot.getX(), context, result -> "Robot was not teleported to correct x-coordinate");
        assertEquals(expectedY, robot.getY(), context, result -> "Robot was not teleported to correct y-coordinate");
    }

    private Robot setup() {
        World.setSize(3, 3);
        World.setDelay(0);
        return new Robot(1, 1) {
            @Override
            public void move() {
                // do nothing
            }
        };
    }
}
