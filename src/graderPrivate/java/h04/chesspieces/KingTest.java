package h04.chesspieces;

import fopbot.Robot;
import fopbot.World;
import h04.movement.MoveStrategy;
import kotlin.Triple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.MethodHeader;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertFalse;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertSame;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.callObject;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class KingTest {

    private final SubmissionExecutionHandler executionHandler = SubmissionExecutionHandler.getInstance();

    private static Method moveStrategyMethod;
    private static Method getPossibleMoveFieldsMethod;

    @BeforeAll
    public static void setup() {
        try {
            moveStrategyMethod = King.class.getDeclaredMethod("moveStrategy", int.class, int.class, MoveStrategy.class);
            getPossibleMoveFieldsMethod = King.class.getDeclaredMethod("getPossibleMoveFields");
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
    public void testMoveStrategy() {
        executionHandler.disableMethodDelegation(moveStrategyMethod);

        World.setSize(3, 3);
        World.setDelay(0);
        AtomicReference<Robot> robotRef = new AtomicReference<>();
        AtomicInteger dxInt = new AtomicInteger(-1);
        AtomicInteger dyInt = new AtomicInteger(-1);
        MoveStrategy moveStrategy = (r, dx, dy) -> {
            robotRef.set(r);
            dxInt.set(dx);
            dyInt.set(dy);
        };
        int dx = 1;
        int dy = 1;
        Context context = contextBuilder()
            .add("dx", dx)
            .add("dy", dy)
            .add("strategy", moveStrategy)
            .build();

        King kingInstance = new King(0, 0, Team.WHITE);
        call(() -> kingInstance.moveStrategy(dx, dy, moveStrategy), context, result ->
            "An exception occurred while invoking moveStrategy(int, int, MoveStrategy)");
        assertSame(kingInstance, robotRef.get(), context, result ->
            "Method move of MoveStrategy was called with incorrect first parameter");
        assertEquals(dx, dxInt.get(), context, result ->
            "Method move of MoveStrategy was called with incorrect second parameter");
        assertEquals(dy, dyInt.get(), context, result ->
            "Method move of MoveStrategy was called with incorrect third parameter");
    }

    @Test
    public void testGetPossibleMoveFieldsHeader() {
        MethodHeader getPossibleMoveFieldsMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(King.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("getPossibleMoveFields") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.getType(Point[].class))))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "King does not declare method 'getPossibleMoveFields()'"));
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
        executionHandler.disableMethodDelegation(getPossibleMoveFieldsMethod);
        World.setSize(worldSize, worldSize);
        World.setDelay(0);
        King kingInstance = new King(x, y, Team.WHITE);
        Context context = contextBuilder()
            .add("world size", worldSize)
            .add("x-coordinate", x)
            .add("y-coordinate", y)
            .build();
        Point[] returnValue = callObject(kingInstance::getPossibleMoveFields, context, result ->
            "An exception occurred while invoking getPossibleMoveFields()");
        return new Triple<>(context, kingInstance, returnValue);
    }
}
