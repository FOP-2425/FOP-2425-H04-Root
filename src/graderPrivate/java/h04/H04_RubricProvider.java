package h04

import org.sourcegrade.jagr.api.rubric.*;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H04_RubricProvider implements RubricProvider{

    private static final Criterion H4_1 = Criterion.builder()
        .shortDescription("H4.1.1 | Das Ende kommt zuerst")
        .maxPoints(2)
        .addChildCriteria(
            criterion("Die Könige werden korrekt gespeichert.")
            criterion("Die Methode checkWinCondition() gibt true zurück, wenn ein König geschlagen wurde, ansonsten false")
        )
        .build();

    private static final Criterion H4_2_1 = Criterion.builder()
        .shortDescription("H4.2.1 | MoveStrategy Interface")
        .maxPoints(1)
        .criterion("Das MoveStrategy-Interface ist korrekt implementiert.")
        .build();

    private static final Criterion H4_2_2 = Criterion.builder()
        .shortDescription("H4.2.2 | TeleportingMoveStrategy")
        .maxPoints(2)
        .criterion("Die TeleportingMoveStrategy-Klasse ist korrekt implementiert.")
        .build();

    private static final Criterion H4_2_3 = Criterion.builder()
        .shortDescription("H4.2.3 | WalkingMoveStrategy")
        .maxPoints(3)
            .addChildCriteria(
                criterion("Die WalkingMoveStrategy-Klasse implementiert das MoveStrategy-Interface korrekt.")
                criterion("Der Roboter bewegt sich korrekt.")
                criterion("Der Roboter schaut nach der Bewegung in die richtige Richtung")
            )

        .build();

    private static final Criterion H4_3 = Criterion.builder()
        .shortDescription("H4.3 | ChessPiece Interface")
        .maxPoints(2)
        .addChildCriteria(
            criterion("Das ChessPiece-Interface ist korrekt implementiert.")
            criterion("Die Methoden moveStrategy und getPossibleMoveFields sind korrekt implementiert.")
        )
        .build();

    private static final Criterion H4_4_1 = Criterion.builder()
        .shortDescription("H4.4.1 | King MoveStrategy")
        .maxPoints(2)
        .addChildCriteria(
            criterion("Die Methode moveStrategy ist korrekt implementiert.")
        )
        .build();

    private static final Criterion H4_4_2 = Criterion.builder()
        .shortDescription("H4.4.2 | King getPossibleMoveFields")
        .maxPoints(5)
        .addChildCriteria(
            criterion("Die Methode getPossibleMoveFields gibt ein eindimensionales Array von Point zurück.")
            criterion("Die Methode gibt nicht mehr als die korrekte Anzahl an Feldern zurück.")
            criterion("Die Methode gibt keine Felder zurück, auf denen sich ein eigener König befindet.")
            criterion("Die Methode gibt keine Felder zurück, die außerhalb des Spielfelds liegen.")
            cirterion("Die Methode gibt die korrekten Felder zurück, auf die der König ziehen kann.")
        )
        .build();

    private static final Criterion H4_5_1 = Criterion.builder()
        .shortDescription("H4.5.1 | OrthogonalMover and DiagonalMover")
        .maxPoints(5)
        .addChildCriteria(
            criterion("Die Methode getOrthogonalMoves wird korrekt hinzugefügt.")
            criterion("Die Methode getOrthogonalMoves gibt die korrekten Felder zurück.")
            criterion("Die Methode getDiagonalMoves wird korrekt hinzugefügt.")
            criterion("Die Methode getDiagonalMoves gibt die korrekten Felder zurück.")
            criterion("Die Methoden erweitern korrekt das Interface ChessPiece.")
        )
        .build();

    private static final Criterion H4_5_2 = Criterion.builder()
        .shortDescription("H4.5.2 | Rook and Bishop")
        .maxPoints(5)
        .addChildCriteria(
            criterion("Die Rook-Klasse implementiert das OrthogonalMover-Interface korrekt.")
            criterion("Die Methoden moveStrategy und getPossibleMoveFields der Klasse Rook sind korrekt implementiert.")
            criterion("Die Bishop-Klasse implementiert das DiagonalMover-Interface korrekt.")
            criterion("Die Methoden moveStrategy und getPossibleMoveFields der Klasse Bishop sind korrekt implementiert.")
            criterion("Beide Klassen sind vollständig korrekt implementiert.")
        )
        .build();

    private static final Criterion H4_6 = Criterion.builder()
        .shortDescription("H4.6 | Queen")
        .maxPoints(5)
        .addChildCriteria(
            criterion("Die Queen-Klasse implementiert das OrthogonalMover- und DiagonalMover-Interface korrekt.")
            criterion("Die Methode getPossibleMoveFields gibt die korrekten Felder zurück. (2P) ")
            criterion("Die Rückgaben der Methoden getOrthogonalMoves und getDiagonalMoves werden korrekt kombiniert. (2P)")
        )
        .build();



}
