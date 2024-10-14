package h04;

import h04.chesspieces.ChessPieceTest;
import h04.chesspieces.KingTest;
import h04.movement.*;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

@SuppressWarnings("unused")
public class H04_RubricProvider implements RubricProvider {

    private static final Criterion H4_1 = Criterion.builder()
        .shortDescription("H4.1 | Das Ende kommt zuerst")
        .addChildCriteria(
            criterion("Die Könige werden mittels ChessUtils.getKings abgerufen.",
                JUnitTestRef.ofMethod(() -> GameControllerTest.class.getDeclaredMethod("testCheckWinConditionCallsChessUtils"))),
            criterion("Die Methode checkWinCondition() gibt true zurück, wenn ein König geschlagen wurde, ansonsten false.",
                JUnitTestRef.ofMethod(() -> GameControllerTest.class.getDeclaredMethod("testCheckWinCondition", int.class)))
        )
        .build();

    private static final Criterion H4_2_1 = Criterion.builder()
        .shortDescription("H4.2.1 | MoveStrategy Interface")
        .addChildCriteria(
            criterion("Das MoveStrategy-Interface ist korrekt implementiert.",
                JUnitTestRef.ofMethod(() -> MoveStrategyTest.class.getDeclaredMethod("testDeclaration")))
        )
        .build();

    private static final Criterion H4_2_2 = Criterion.builder()
        .shortDescription("H4.2.2 | TeleportingMoveStrategy")
        .addChildCriteria(
            criterion("Die TeleportingMoveStrategy-Klasse ist korrekt deklariert.",
                JUnitTestRef.ofMethod(() -> TeleportingMoveStrategyTest.class.getDeclaredMethod("testClassHeader"))),
            criterion("Methode move(Robot, int, int) ist korrekt implementiert.",
                JUnitTestRef.ofMethod(() -> TeleportingMoveStrategyTest.class.getDeclaredMethod("testMove", int.class)))
        )
        .build();

    private static final Criterion H4_2_3 = Criterion.builder()
        .shortDescription("H4.2.3 | WalkingMoveStrategy")
        .addChildCriteria(
            criterion("Die WalkingMoveStrategy-Klasse implementiert das MoveStrategy-Interface korrekt.",
                JUnitTestRef.ofMethod(() -> WalkingMoveStrategyTest.class.getDeclaredMethod("testClassHeader"))),
            criterion("Der Roboter bewegt sich korrekt.",
                JUnitTestRef.ofMethod(() -> WalkingMoveStrategyTest.class.getDeclaredMethod("testMove", int.class))),
            criterion("Der Roboter schaut nach der Bewegung in die richtige Richtung.",
                JUnitTestRef.ofMethod(() -> WalkingMoveStrategyTest.class.getDeclaredMethod("testMoveFacesUp", int.class)))
        )
        .build();

    private static final Criterion H4_2 = Criterion.builder()
        .shortDescription("H4.2 | MoveStrategy")
        .addChildCriteria(H4_2_1, H4_2_2, H4_2_3)
        .build();

    private static final Criterion H4_3 = Criterion.builder()
        .shortDescription("H4.3 | ChessPiece Interface")
        .addChildCriteria(
            criterion("Methode moveStrategy(int, int, MoveStrategy) ist korrekt deklariert.",
                JUnitTestRef.ofMethod(() -> ChessPieceTest.class.getDeclaredMethod("testMoveStrategyDeclaration"))),
            criterion("Methode getPossibleMoveFields() ist korrekt deklariert.",
                JUnitTestRef.ofMethod(() -> ChessPieceTest.class.getDeclaredMethod("testGetPossibleMoveFieldDeclaration")))
        )
        .build();

    private static final Criterion H4_4_1 = Criterion.builder()
        .shortDescription("H4.4.1 | King MoveStrategy")
        .addChildCriteria(
            criterion("Die Methode moveStrategy ist korrekt implementiert.", 2,
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testMoveStrategy")))
        )
        .build();

    private static final Criterion H4_4_2 = Criterion.builder()
        .shortDescription("H4.4.2 | King getPossibleMoveFields")
        .addChildCriteria(
            criterion("Die Methode getPossibleMoveFields ist korrekt deklariert.",
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testGetPossibleMoveFieldsHeader"))),
            criterion("Die Methode gibt nicht mehr als die korrekte Anzahl an Feldern zurück.",
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testGetPossibleMoveFieldsCorrectAmount"))),
            criterion("Die Methode gibt keine Felder zurück, auf denen sich ein eigener König befindet.",
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testGetPossibleMoveFieldsExcludesSelf"))),
            criterion("Die Methode gibt keine Felder zurück, die außerhalb des Spielfelds liegen.",
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testGetPossibleMoveFieldsInWorld"))),
            criterion("Die Methode gibt die korrekten Felder zurück, auf die der König ziehen kann.",
                JUnitTestRef.ofMethod(() -> KingTest.class.getDeclaredMethod("testGetPossibleMoveFieldsCorrect")))
        )
        .build();

    private static final Criterion H4_4 = Criterion.builder()
        .shortDescription("H4.4 | Schachfiguren-Bewegung I")
        .addChildCriteria(H4_4_1, H4_4_2)
        .build();

    private static final Criterion H4_5_1 = Criterion.builder()
        .shortDescription("H4.5.1 | OrthogonalMover and DiagonalMover")
        .addChildCriteria(
            criterion("Die Methode getOrthogonalMoves wird korrekt hinzugefügt.",
                JUnitTestRef.ofMethod(() -> OrthogonalMoverTest.class.getDeclaredMethod("testMethodHeader"))),
            criterion("Die Methode getOrthogonalMoves gibt die korrekten Felder zurück.",
                JUnitTestRef.ofMethod(() -> OrthogonalMoverTest.class.getDeclaredMethod("testGetOrthogonalMoves"))),
            criterion("Die Methode getDiagonalMoves wird korrekt hinzugefügt.",
                JUnitTestRef.ofMethod(() -> DiagonalMoverTest.class.getDeclaredMethod("testMethodHeader"))),
            criterion("Die Methode getDiagonalMoves gibt die korrekten Felder zurück.",
                JUnitTestRef.ofMethod(() -> DiagonalMoverTest.class.getDeclaredMethod("testGetDiagonalMoves"))),
            criterion("OrthogonalMover und DiagonalMover erweitern beide das Interface ChessPiece.",
                JUnitTestRef.ofMethod(() -> OrthogonalMoverTest.class.getDeclaredMethod("testClassHeader")),
                JUnitTestRef.ofMethod(() -> DiagonalMoverTest.class.getDeclaredMethod("testClassHeader")))
        )
        .build();

    private static final Criterion H4_5_2 = Criterion.builder()
        .shortDescription("H4.5.2 | Rook and Bishop")
        .addChildCriteria(
            criterion("Die Rook-Klasse implementiert das OrthogonalMover-Interface korrekt."),
            criterion("Die Methoden moveStrategy und getPossibleMoveFields der Klasse Rook sind korrekt implementiert."),
            criterion("Die Bishop-Klasse implementiert das DiagonalMover-Interface korrekt."),
            criterion("Die Methoden moveStrategy und getPossibleMoveFields der Klasse Bishop sind korrekt implementiert."),
            criterion("Beide Klassen sind vollständig korrekt implementiert.")
        )
        .build();

    private static final Criterion H4_5 = Criterion.builder()
        .shortDescription("H4.5 | Schachfiguren-Bewegung II")
        .addChildCriteria(H4_5_1, H4_5_2)
        .build();

    private static final Criterion H4_6 = Criterion.builder()
        .shortDescription("H4.6 | Queen")
        .addChildCriteria(
            criterion("Die Queen-Klasse implementiert das OrthogonalMover- und DiagonalMover-Interface korrekt."),
            criterion("Die Methode getPossibleMoveFields gibt die korrekten Felder zurück.", 2),
            criterion("Die Rückgaben der Methoden getOrthogonalMoves und getDiagonalMoves werden korrekt kombiniert.", 2)
        )
        .build();

    @Override
    public Rubric getRubric() {
        return Rubric.builder()
            .title("H04 | <To be named>")  // TODO: replace with name when final
            .addChildCriteria(H4_1, H4_2, H4_3, H4_4, H4_5, H4_6)
            .build();
    }
}
