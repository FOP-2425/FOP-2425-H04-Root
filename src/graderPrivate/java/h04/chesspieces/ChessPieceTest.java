package h04.chesspieces;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.awt.Point;

import static h04.Links.CHESS_PIECE_GET_POSSIBLE_MOVE_FIELDS_LINK;
import static h04.Links.CHESS_PIECE_MOVE_STRATEGY_LINK;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class ChessPieceTest {

    @Test
    public void testMoveStrategyDeclaration() {
        MethodLink moveStrategyLink = CHESS_PIECE_MOVE_STRATEGY_LINK.get();  // implicit test that method with identifier + param types exists
        assertEquals(void.class, moveStrategyLink.returnType().reflection(), emptyContext(), result ->
            "Return type of method moveStrategy(int, int, MoveStrategy) is incorrect");
    }

    @Test
    public void testGetPossibleMoveFieldDeclaration() {
        MethodLink getPossibleMoveFieldsLink = CHESS_PIECE_GET_POSSIBLE_MOVE_FIELDS_LINK.get();  // implicit test that method with identifier + param types exists
        assertEquals(Point[].class, getPossibleMoveFieldsLink.returnType().reflection(), emptyContext(), result ->
            "Return type of method getPossibleMoveFields() is incorrect");
    }
}
