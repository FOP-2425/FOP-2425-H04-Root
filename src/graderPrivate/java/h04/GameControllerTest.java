package h04;

import fopbot.Robot;
import fopbot.World;
import h04.chesspieces.King;
import h04.chesspieces.Team;
import h04.template.ChessUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class GameControllerTest {

    @BeforeEach
    public void setup() {
        World.setSize(2, 1);
        World.setDelay(0);
    }

    @Test
    public void testCheckWinConditionCallsChessUtils() {
        King whiteKing = new King(0, 0, Team.WHITE);
        King blackKing = new King(1, 0, Team.BLACK);
        Context context = contextBuilder()
            .add("white king", whiteKing)
            .add("black king", blackKing)
            .build();

        AtomicInteger getKingsCalls = new AtomicInteger(0);
        Answer<?> answer = invocation -> {
            if (invocation.getMethod().equals(ChessUtils.class.getDeclaredMethod("getKings"))) {
                getKingsCalls.incrementAndGet();
            }
            return invocation.callRealMethod();
        };
        try (MockedStatic<ChessUtils> ignored = Mockito.mockStatic(ChessUtils.class, answer)) {
            GameController gameControllerMock = Mockito.mock(GameController.class, Mockito.CALLS_REAL_METHODS);
            call(gameControllerMock::checkWinCondition, context, result -> "An exception occurred while invoking method checkWinCondition");
        }
        assertTrue(getKingsCalls.get() > 0, context, result -> "ChessUtils.getKings() was not called at least once");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testCheckWinCondition(int n) {
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

        GameController gameControllerMock = Mockito.mock(GameController.class, Mockito.CALLS_REAL_METHODS);
        assertCallEquals(turnOffWhiteKing || turnOffBlackKing, gameControllerMock::checkWinCondition, context, result ->
            "Method checkWinCondition returned an incorrect value");
    }
}
