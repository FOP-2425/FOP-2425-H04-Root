package h04.chesspieces;

import h04.movement.MoveStrategy;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.MethodHeader;

import java.awt.Point;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class ChessPieceTest {

    @Test
    public void testMoveStrategyDeclaration() {
        MethodHeader moveStrategyMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(ChessPiece.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("moveStrategy") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE, Type.INT_TYPE, Type.getType(MoveStrategy.class))))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "ChessPiece does not declare method 'moveStrategy(int, int, MoveStrategy)'"));
    }

    @Test
    public void testGetPossibleMoveFieldDeclaration() {
        MethodHeader getPossibleMoveFieldsMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(ChessPiece.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("getPossibleMoveFields") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.getType(Point[].class))))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "ChessPiece does not declare method 'getPossibleMoveFields()'"));
    }
}
