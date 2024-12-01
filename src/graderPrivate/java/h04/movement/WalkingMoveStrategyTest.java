package h04.movement;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.ClassHeader;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class WalkingMoveStrategyTest {

    private final SubmissionExecutionHandler executionHandler = SubmissionExecutionHandler.getInstance();

    private static Method moveMethod;

    @BeforeAll
    public static void setup() {
        try {
            moveMethod = WalkingMoveStrategy.class.getDeclaredMethod("move", Robot.class, int.class, int.class);
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
        ClassHeader originalClassHeader = SubmissionExecutionHandler.getOriginalClassHeader(WalkingMoveStrategy.class);
        assertTrue(Arrays.stream(originalClassHeader.interfaces()).anyMatch(s -> s.equals(Type.getInternalName(MoveStrategy.class))),
            emptyContext(),
            result -> "Class h04.movement.WalkingMoveStrategy does not implement interface h04.movement.MoveStrategy");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 8, 9, 10})
    public void testMove(int n) {  // encoding: 4 bits, lower 2: x-coordinate, upper 2: y-coordinate
        executionHandler.disableMethodDelegation(moveMethod);
        Robot robot = setupEnvironment();
        int expectedX = n & 0b11;
        int expectedY = n >> 2;
        Context context = invokeMove(robot, n);

        assertEquals(expectedX, robot.getX(), context, result -> "Robot did not walk to correct x-coordinate");
        assertEquals(expectedY, robot.getY(), context, result -> "Robot did not walk to correct y-coordinate");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5, 6, 8, 9, 10})
    public void testMoveFacesUp(int n) {  // encoding: 4 bits, lower 2: x-coordinate, upper 2: y-coordinate
        executionHandler.disableMethodDelegation(moveMethod);
        Robot robot = setupEnvironment();
        Context context = invokeMove(robot, n);

        assertEquals(Direction.UP, robot.getDirection(), context, result -> "Robot does not face upwards after calling move");
    }

    private Robot setupEnvironment() {
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
        WalkingMoveStrategy walkingMoveStrategyInstance = new WalkingMoveStrategy();
        call(() -> walkingMoveStrategyInstance.move(robot, dx, dy), context, result ->
            "An exception occurred while invoking method move");

        return context;
    }
}
