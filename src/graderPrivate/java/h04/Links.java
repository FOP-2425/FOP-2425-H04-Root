package h04;

import com.google.common.base.Suppliers;
import fopbot.Robot;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.EnumConstantLink;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Links {

    // Packages
    public static final Supplier<PackageLink> BASE_PACKAGE_LINK = Suppliers.memoize(() -> BasicPackageLink.of("h04"));
    public static final Supplier<PackageLink> MOVEMENT_PACKAGE_LINK = Suppliers.memoize(() -> BasicPackageLink.of("h04.movement"));
    public static final Supplier<PackageLink> CHESSPIECES_PACKAGE_LINK = Suppliers.memoize(() -> BasicPackageLink.of("h04.chesspieces"));

    // Interface h04.movement.DiagonalMover
    public static final Supplier<TypeLink> DIAGONAL_MOVER_LINK = getTypeLinkByName(MOVEMENT_PACKAGE_LINK, "DiagonalMover");
    public static final Supplier<MethodLink> DIAGONAL_MOVER_GET_DIAGONAL_MOVES_LINK = getMethodLink(DIAGONAL_MOVER_LINK, "getDiagonalMoves");

    // Interface h04.movement.MoveStrategy
    public static final Supplier<TypeLink> MOVE_STRATEGY_LINK = getTypeLinkByName(MOVEMENT_PACKAGE_LINK, "MoveStrategy");
    public static final Supplier<MethodLink> MOVE_STRATEGY_MOVE_LINK = getMethodLink(MOVE_STRATEGY_LINK, "move", Robot.class, int.class, int.class);

    // Interface h04.movement.OrthogonalMover
    public static final Supplier<TypeLink> ORTHOGONAL_MOVER_LINK = getTypeLinkByName(MOVEMENT_PACKAGE_LINK, "OrthogonalMover");
    public static final Supplier<MethodLink> ORTHOGONAL_MOVER_GET_ORTHOGONAL_MOVES_LINK = getMethodLink(ORTHOGONAL_MOVER_LINK, "getOrthogonalMoves");

    // Class h04.movement.TeleportingMoveStrategy
    public static final Supplier<TypeLink> TELEPORTING_MOVE_STRATEGY_LINK = getTypeLinkByName(MOVEMENT_PACKAGE_LINK, "TeleportingMoveStrategy");
    public static final Supplier<ConstructorLink> TELEPORTING_MOVE_STRATEGY_CONSTRUCTOR_LINK = getConstructorLink(TELEPORTING_MOVE_STRATEGY_LINK);
    public static final Supplier<MethodLink> TELEPORTING_MOVE_STRATEGY_MOVE_LINK = getMethodLink(TELEPORTING_MOVE_STRATEGY_LINK, "move", Robot.class, int.class, int.class);

    // Class h04.movement.WalkingMoveStrategy
    public static final Supplier<TypeLink> WALKING_MOVE_STRATEGY_LINK = getTypeLinkByName(MOVEMENT_PACKAGE_LINK, "WalkingMoveStrategy");
    public static final Supplier<ConstructorLink> WALKING_MOVE_STRATEGY_CONSTRUCTOR_LINK = getConstructorLink(WALKING_MOVE_STRATEGY_LINK);
    public static final Supplier<MethodLink> WALKING_MOVE_STRATEGY_MOVE_LINK = getMethodLink(WALKING_MOVE_STRATEGY_LINK, "move", Robot.class, int.class, int.class);

    // Interface h04.chesspieces.ChessPiece
    public static final Supplier<TypeLink> CHESS_PIECE_LINK = getTypeLinkByName(CHESSPIECES_PACKAGE_LINK, "ChessPiece");
    public static final Supplier<MethodLink> CHESS_PIECE_MOVE_STRATEGY_LINK = getMethodLink(CHESS_PIECE_LINK, "moveStrategy",
            () -> BasicTypeLink.of(int.class), () -> BasicTypeLink.of(int.class), MOVE_STRATEGY_LINK);
    public static final Supplier<MethodLink> CHESS_PIECE_GET_POSSIBLE_MOVE_FIELDS_LINK = getMethodLink(CHESS_PIECE_LINK, "getPossibleMoveFields");

    // Class h04.chesspieces.King
    public static final Supplier<TypeLink> KING_LINK = getTypeLinkByName(CHESSPIECES_PACKAGE_LINK, "King");
    public static final Supplier<MethodLink> KING_MOVE_STRATEGY_LINK = getMethodLink(KING_LINK, "moveStrategy",
            () -> BasicTypeLink.of(int.class), () -> BasicTypeLink.of(int.class), MOVE_STRATEGY_LINK);
    public static final Supplier<MethodLink> KING_GET_POSSIBLE_MOVE_FIELDS_LINK = getMethodLink(KING_LINK, "getPossibleMoveFields");

    private static Supplier<TypeLink> getTypeLinkByName(Supplier<PackageLink> packageLinkSupplier, String name) {
        return Suppliers.memoize(() -> packageLinkSupplier.get().getType(Matcher.of(typeLink -> typeLink.name().equals(name))));
    }

    private static Supplier<FieldLink> getFieldLinkByName(Supplier<TypeLink> owner, String name) {
        return Suppliers.memoize(() -> owner.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals(name))));
    }

    private static Supplier<EnumConstantLink> getEnumConstantLinkByName(Supplier<TypeLink> owner, String name) {
        return Suppliers.memoize(() -> owner.get().getEnumConstant(Matcher.of(enumConstantLink -> enumConstantLink.name().equals(name))));
    }

    @SuppressWarnings("unchecked")
    private static Supplier<ConstructorLink> getConstructorLink(Supplier<TypeLink> owner) {
        return getConstructorLink(owner, new Supplier[0]);
    }

    @SuppressWarnings("unchecked")
    private static Supplier<ConstructorLink> getConstructorLink(Supplier<TypeLink> owner, Class<?>... parameterTypes) {
        return getConstructorLink(owner, Arrays.stream(parameterTypes)
            .map(parameterType -> (Supplier<TypeLink>) () -> BasicTypeLink.of(parameterType))
            .toArray(Supplier[]::new));
    }

    @SafeVarargs
    private static Supplier<ConstructorLink> getConstructorLink(Supplier<TypeLink> owner, Supplier<TypeLink>... parameterTypes) {
        return Suppliers.memoize(() ->
            owner.get().getConstructor(Matcher.of(constructorLink -> {
                List<? extends TypeLink> params = constructorLink.typeList();
                boolean found = params.size() == parameterTypes.length;
                for (int i = 0; found && i < parameterTypes.length; i++) {
                    found = parameterTypes[i].get().equals(params.get(i));
                }
                return found;
            })));
    }

    @SuppressWarnings("unchecked")
    private static Supplier<MethodLink> getMethodLink(Supplier<TypeLink> owner, String name) {
        return getMethodLink(owner, name, new Supplier[0]);
    }

    @SuppressWarnings("unchecked")
    private static Supplier<MethodLink> getMethodLink(Supplier<TypeLink> owner, String name, Class<?>... parameterTypes) {
        return getMethodLink(owner, name, Arrays.stream(parameterTypes)
            .map(parameterType -> (Supplier<TypeLink>) () -> BasicTypeLink.of(parameterType))
            .toArray(Supplier[]::new));
    }

    @SafeVarargs
    private static Supplier<MethodLink> getMethodLink(Supplier<TypeLink> owner, String name, Supplier<TypeLink>... parameterTypes) {
        return Suppliers.memoize(() ->
            owner.get().getMethod(Matcher.of(methodLink -> {
                List<? extends TypeLink> params = methodLink.typeList();
                boolean found = methodLink.name().equals(name) && params.size() == parameterTypes.length;
                for (int i = 0; found && i < parameterTypes.length; i++) {
                    found = parameterTypes[i].get().equals(params.get(i));
                }
                return found;
            })));
    }
}
