package h04.movement;

import fopbot.Direction;
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
public class WalkingMoveStrategyTest {

    @Test
    public void testClassHeader() {
        TypeLink walkingMoveStrategyLink = WALKING_MOVE_STRATEGY_LINK.get();  // implicit test that class exists
        assertTrue(walkingMoveStrategyLink.interfaces().contains(MOVE_STRATEGY_LINK.get()), emptyContext(), result ->
            "Class does not implement interface MoveStrategy");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 8, 9, 10})
    public void testMove(int n) {  // encoding: 4 bits, lower 2: x-coordinate, upper 2: y-coordinate
        Robot robot = setup();
        int expectedX = n & 0b11;
        int expectedY = n >> 2;
        Context context = invokeMove(robot, n);

        assertEquals(expectedX, robot.getX(), context, result -> "Robot did not walk to correct x-coordinate");
        assertEquals(expectedY, robot.getY(), context, result -> "Robot did not walk to correct y-coordinate");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 8, 9, 10})
    public void testMoveFacesUp(int n) {  // encoding: 4 bits, lower 2: x-coordinate, upper 2: y-coordinate
        Robot robot = setup();
        Context context = invokeMove(robot, n);

        assertEquals(Direction.UP, robot.getDirection(), context, result -> "Robot does not face upwards after calling move");
    }

    private Robot setup() {
        World.setSize(3, 3);
        World.setDelay(0);
        return new Robot(1, 1) {
            @Override
            public void setField(int x, int y) {
                // do nothing
            }

            @Override
            public void setX(int x) {
                // do nothing
            }

            @Override
            public void setY(int y) {
                // do nothing
            }
        };
    }

    private Context invokeMove(Robot robot, int n) {
        int expectedX = n & 0b11;
        int expectedY = n >> 2;
        int dx = expectedX - 1;
        int dy = expectedY - 1;

        Context context = contextBuilder()
            .add("robot", robot)
            .add("dx", dx)
            .add("dy", dy)
            .build();
        Object walkingMoveStrategyInstance = callObject(() -> WALKING_MOVE_STRATEGY_CONSTRUCTOR_LINK.get().invoke(), context, result ->
            "An exception occurred while invoking constructor of WalkingMoveStrategy");
        call(() -> WALKING_MOVE_STRATEGY_MOVE_LINK.get().invoke(walkingMoveStrategyInstance, robot, dx, dy), context, result ->
            "An exception occurred while invoking method move");

        return context;
    }
}
