package h04.movement;

import h04.Links;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class MoveStrategyTest {

    @Test
    public void testDeclaration() {
        TypeLink moveStrategyLink = Links.MOVE_STRATEGY_LINK.get();  // implicit test that interface exists
        MethodLink moveLink = Links.MOVE_STRATEGY_MOVE_LINK.get();  // same here + parameters
        assertEquals(void.class, moveLink.returnType().reflection(), emptyContext(), result -> "Return type of method move is not void");
    }
}
