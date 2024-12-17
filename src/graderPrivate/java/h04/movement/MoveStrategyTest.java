package h04.movement;

import fopbot.Robot;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.transform.SubmissionExecutionHandler;
import org.tudalgo.algoutils.transform.util.headers.ClassHeader;
import org.tudalgo.algoutils.transform.util.headers.MethodHeader;

import java.lang.reflect.Modifier;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class MoveStrategyTest {

    @Test
    public void testDeclaration() {
        ClassHeader originalClassHeader = SubmissionExecutionHandler.getOriginalClassHeader(MoveStrategy.class);
        assertTrue(Modifier.isInterface(originalClassHeader.access()), emptyContext(), result ->
            "Class h04.movement.MoveStrategy is not an interface");

        MethodHeader moveMethodHeader = SubmissionExecutionHandler.getOriginalMethodHeaders(MoveStrategy.class)
            .stream()
            .filter(methodHeader -> methodHeader.name().equals("move") &&
                methodHeader.descriptor().equals(Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(Robot.class), Type.INT_TYPE, Type.INT_TYPE)))
            .findAny()
            .orElseGet(() -> fail(emptyContext(), result -> "MoveStrategy does not declare method 'move(Robot, int, int)'"));
    }
}
