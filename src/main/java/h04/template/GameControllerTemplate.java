package h04.template;

import fopbot.ColorProfile;
import fopbot.Robot;
import fopbot.World;
import h04.GameController;
import h04.template.InputHandler;
import h04.chesspieces.Bishop;
import h04.chesspieces.ChessPiece;
import h04.chesspieces.King;
import h04.chesspieces.Knight;
import h04.chesspieces.Pawn;
import h04.chesspieces.Queen;
import h04.chesspieces.Rook;
import h04.chesspieces.Team;
import h04.movement.MoveStrategy;
import h04.movement.TeleportingMoveStrategy;
import h04.movement.WalkingMoveStrategy;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.io.PropertyUtils;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

/**
 * A {@link GameControllerTemplate} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public abstract class GameControllerTemplate {
    /**
     * The {@link InputHandler} that handles the input of the user.
     */
    private final InputHandler inputHandler = new InputHandler((GameController) this);

    /**
     * The {@link Robot}s that are controlled by the {@link GameControllerTemplate}.
     */

    protected Team nextToMove = Team.WHITE;

    protected boolean gameOver = false;

    protected @Nullable ChessPiece selectedPiece;

    protected MoveStrategy moveStrategy = PropertyUtils.getBooleanProperty(
        "h04.properties",
        "USE_TELEPORT_MOVE_STRATEGY"
    ) ? new TeleportingMoveStrategy() : new WalkingMoveStrategy();


    /**
     * Starts the game loop.
     */
    public void startGame() {
        System.out.println("Starting game...");

        while (!gameOver) {
            final var point = inputHandler.getNextInput(nextToMove);
            if (ChessUtils.getTeamAt(point) == nextToMove) {
                // select piece
                selectedPiece = ChessUtils.getPieceAt(point);
                //highlight possible moves
                final var possibleMoves = Objects.requireNonNull(selectedPiece).getPossibleMoveFields();
                ChessUtils.resetFieldColor();
                ChessUtils.colorMoveFields(possibleMoves);
                continue;
            }
            if (selectedPiece != null) {
                // check if piece can move to point
                final var possibleMoves = selectedPiece.getPossibleMoveFields();
                if (Arrays.stream(possibleMoves)
                    .filter(Objects::nonNull)
                    .noneMatch(point::equals)
                ) {
                    System.out.println("Invalid move");
                    // deselect piece
                    selectedPiece = null;
                    ChessUtils.resetFieldColor();
                    continue;
                }
                // valid move selected, move piece
                final var oldPiece = ChessUtils.getPieceAt(point);
                selectedPiece.moveStrategy(
                    point.x - selectedPiece.getX(),
                    point.y - selectedPiece.getY(),
                    moveStrategy
                );
                if (oldPiece != null) {
                    oldPiece.turnOff();
                }
                if (selectedPiece instanceof Pawn && (selectedPiece.getY() == 0 || selectedPiece.getY() == World.getHeight() - 1)) {
                    selectedPiece.turnOff();
                    new Queen(selectedPiece.getX(), selectedPiece.getY(), selectedPiece.getTeam());
                }
                ChessUtils.resetFieldColor();
                if (checkWinCondition()) stopGame(nextToMove);


                nextToMove = nextToMove.getOpponent();
            }
        }
    }

    public abstract boolean checkWinCondition();


    /**
     * Stops the game loop.
     */
    public void stopGame(final Team winner) {
        gameOver = true;
        inputHandler.setStatusText(winner.name() + " wins!");
    }

    /**
     * Sets up the game.
     */
    protected void setup() {
        setupWorld();
        setupTheme();
        setupPieces();
        //this.inputHandler.install();
    }

    public void setupTheme() {
        //noinspection UnstableApiUsage
        World.getGlobalWorld().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .fieldColorLight(Color.decode("#e0ba97"))
                .fieldColorDark(Color.decode("#8d4d2a"))
                .customFieldColorPattern(
                    (cp, p) -> (p.x + p.y) % 2 == 0 ? cp.fieldColorLight() : cp.fieldColorDark()
                )
                .backgroundColorDark(Color.decode("#8d4d2a"))
                .backgroundColorLight(Color.decode("#8d4d2a"))
                .innerBorderColorLight(new Color(0, 0, 0, 0))
                .innerBorderColorDark(new Color(0, 0, 0, 0))
                .fieldBorderThickness(0)
                .outerBorderColorDark(Color.BLACK)
                .outerBorderColorLight(Color.BLACK)
                .build()
        );
    }

    /**
     * Initializes the {@link World} and adds the {@link Robot}s to it.
     */
    public void setupWorld() {
        World.setSize(8, 8);

        World.setDelay(0);
        World.setVisible(true);
        World.getGlobalWorld().setDrawTurnedOffRobots(false);
        inputHandler.install();
    }

    public void setupPieces() {
        for (final int y : new int[]{0, 1, World.getHeight() - 2, World.getHeight() - 1}) {
            for (int x = 0; x < World.getWidth(); x++) {
                if (y == 1 || y == World.getHeight() - 2)
                    new Pawn(x, y, y == 1 ? Team.WHITE : Team.BLACK);
                else if (x == 0 || x == World.getWidth() - 1) {
                    new Rook(x, y, y == 0 ? Team.WHITE : Team.BLACK);
                } else if (x == 1 || x == World.getWidth() - 2) {
                    new Knight(x, y, y == 0 ? Team.WHITE : Team.BLACK);
                } else if (x == 2 || x == World.getWidth() - 3) {
                    new Bishop(x, y, y == 0 ? Team.WHITE : Team.BLACK);
                } else if (x == 3) {
                    new Queen(x, y, y == 0 ? Team.WHITE : Team.BLACK);
                } else if (x == World.getWidth() - 4) {
                    new King(x, y, y == 0 ? Team.WHITE : Team.BLACK);
                }
            }
        }
    }


}
