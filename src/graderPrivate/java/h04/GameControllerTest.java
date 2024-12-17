package h04;

import fopbot.Robot;
import fopbot.World;
import h04.chesspieces.King;
import h04.chesspieces.Team;
import h04.template.ChessUtils;
import h04.template.GameControllerTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.Invocation;
import org.tudalgo.algoutils.transform.util.headers.MethodHeader;
import org.tudalgo.algoutils.transform.util.MethodSubstitution;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertCallEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class GameControllerTest {

    private final MethodHeader gameControllerConstructor = MethodHeader.of(GameController.class);
    private final MethodHeader gameController_checkWinCondition = MethodHeader.of(GameController.class, "checkWinCondition");
    private final MethodHeader chessUtils_getKings = MethodHeader.of(ChessUtils.class, "getKings");

    @BeforeEach
    public void setupEnvironment() {
        World.setSize(2, 1);
        World.setDelay(0);
    }

    @AfterEach
    public void tearDown() {
        SubmissionExecutionHandler.resetAll();
    }

    @Test
    public void testCheckWinConditionCallsChessUtils() {
        SubmissionExecutionHandler.Substitution.enable(gameControllerConstructor, new MethodSubstitution() {
            @Override
            public ConstructorInvocation getConstructorInvocation() {
                return new ConstructorInvocation(Type.getInternalName(GameControllerTemplate.class), "()V");
            }

            @Override
            public Object execute(Invocation invocation) {
                return null;
            }
        });
        SubmissionExecutionHandler.Logging.enable(chessUtils_getKings);
        SubmissionExecutionHandler.Delegation.disable(gameController_checkWinCondition);

        King whiteKing = new King(0, 0, Team.WHITE);
        King blackKing = new King(1, 0, Team.BLACK);
        Context context = contextBuilder()
            .add("white king", whiteKing)
            .add("black king", blackKing)
            .build();

        call(() -> new GameController().checkWinCondition(), context, result -> "An exception occurred while invoking method checkWinCondition");
        assertTrue(!SubmissionExecutionHandler.Logging.getInvocations(chessUtils_getKings).isEmpty(),
            context,
            result -> "ChessUtils.getKings() was not called at least once");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testCheckWinCondition(int n) {
        SubmissionExecutionHandler.Substitution.enable(gameControllerConstructor, new MethodSubstitution() {
            @Override
            public ConstructorInvocation getConstructorInvocation() {
                return new ConstructorInvocation(Type.getInternalName(GameControllerTemplate.class), "()V");
            }

            @Override
            public Object execute(Invocation invocation) {
                return null;
            }
        });
        SubmissionExecutionHandler.Delegation.disable(gameController_checkWinCondition);

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
