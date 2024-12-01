package h04;

import fopbot.Robot;
import fopbot.World;
import h04.chesspieces.King;
import h04.chesspieces.Team;
import h04.template.ChessUtils;
import h04.template.GameControllerTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.Invocation;
import org.tudalgo.algoutils.transform.util.MethodSubstitution;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertCallEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class GameControllerTest {

    private final SubmissionExecutionHandler executionHandler = SubmissionExecutionHandler.getInstance();

    private static Constructor<?> gameControllerConstructor;
    private static Method checkWinConditionMethod;

    @BeforeAll
    public static void setup() {
        try {
            gameControllerConstructor = GameController.class.getDeclaredConstructor();
            checkWinConditionMethod = GameController.class.getDeclaredMethod("checkWinCondition");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setupEnvironment() {
        World.setSize(2, 1);
        World.setDelay(0);
    }

    @AfterEach
    public void tearDown() {
        executionHandler.resetMethodInvocationLogging();
        executionHandler.resetMethodSubstitution();
        executionHandler.resetMethodDelegation();
    }

    @Test
    public void testCheckWinConditionCallsChessUtils() throws ReflectiveOperationException {
        executionHandler.substituteMethod(gameControllerConstructor, new MethodSubstitution() {
            @Override
            public ConstructorInvocation getConstructorInvocation() {
                return new ConstructorInvocation(Type.getInternalName(GameControllerTemplate.class), "()V");
            }

            @Override
            public Object execute(Invocation invocation) {
                return null;
            }
        });
        Method getKingsMethod = ChessUtils.class.getDeclaredMethod("getKings");
        executionHandler.enableMethodInvocationLogging(getKingsMethod);
        executionHandler.disableMethodDelegation(checkWinConditionMethod);

        King whiteKing = new King(0, 0, Team.WHITE);
        King blackKing = new King(1, 0, Team.BLACK);
        Context context = contextBuilder()
            .add("white king", whiteKing)
            .add("black king", blackKing)
            .build();

        call(() -> new GameController().checkWinCondition(), context, result -> "An exception occurred while invoking method checkWinCondition");
        assertTrue(!executionHandler.getInvocationsForMethod(getKingsMethod).isEmpty(), context, result -> "ChessUtils.getKings() was not called at least once");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testCheckWinCondition(int n) {
        executionHandler.substituteMethod(gameControllerConstructor, new MethodSubstitution() {
            @Override
            public ConstructorInvocation getConstructorInvocation() {
                return new ConstructorInvocation(Type.getInternalName(GameControllerTemplate.class), "()V");
            }

            @Override
            public Object execute(Invocation invocation) {
                return null;
            }
        });
        executionHandler.disableMethodDelegation(checkWinConditionMethod);

        boolean turnOffWhiteKing = (n & 1) != 0;
        boolean turnOffBlackKing = (n & 2) != 0;
        King whiteKing = new King(0, 0, Team.WHITE);
        King blackKing = new King(1, 0, Team.BLACK);
        if (turnOffWhiteKing) {
            whiteKing.turnOff();
        }
        if (turnOffBlackKing) {
            blackKing.turnOff();
        }
        Context context = contextBuilder()
            .add("white king", whiteKing)
            .add("black king", blackKing)
            .add("turned off king(s)", Stream.of(whiteKing, blackKing).filter(Robot::isTurnedOff).toList())
            .build();

        assertCallEquals(turnOffWhiteKing || turnOffBlackKing,
            () -> new GameController().checkWinCondition(),
            context,
            result -> "Method checkWinCondition returned an incorrect value");
    }
}
