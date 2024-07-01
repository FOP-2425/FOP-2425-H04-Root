package h04.template;

import fopbot.*;
import fopbot.Robot;
import h04.*;
import h04.chesspieces.ChessPiece;
import h04.chesspieces.King;
import h04.chesspieces.Team;

import java.awt.*;
import java.util.*;

/**
 * A {@link GameControllerTemplate} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public abstract class GameControllerTemplate {
    /**
     * The {@link GameInputHandler} that handles the input of the user.
     */
    private final GameInputHandler inputHandler = new GameInputHandler();

    /**
     * The {@link Robot}s that are controlled by the {@link GameControllerTemplate}.
     */
    protected final ArrayList<Robot> allPieces = new ArrayList<>();

    private Team nextToMove = Team.WHITE;

    private boolean gameOver = false;


    /**
     * Starts the game loop.
     */
    public void startGame() {
        System.out.println("Starting game...");

        while(!gameOver) {
            inputHandler.waitForMove(nextToMove);
            if(checkWinCondition()) stopGame(nextToMove);

            nextToMove = nextToMove.getOpponent();
        }
    }

    public abstract boolean checkWinCondition();

    /**
     * Stops the game loop.
     */
    public void stopGame(Team winner) {
    }

    /**
     * Sets up the game.
     */
    protected void setup() {
        setupWorld();
        setupTheme();
        //this.inputHandler.install();
    }

    public void setupTheme() {
        //noinspection UnstableApiUsage
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .customFieldColorPattern(
                    (cp, p) -> (p.x + p.y) % 2 == 0 ? cp.fieldColorLight() : cp.fieldColorDark()
                )
                .build()
        );
        //noinspection UnstableApiUsage
        World.getGlobalWorld().getGuiPanel().setColorProfile(
            ColorProfile.DEFAULT.toBuilder()
                .backgroundColorDark(Color.BLACK)
                .backgroundColorLight(Color.BLACK)
                .innerBorderColorLight(Color.BLACK)
                .InnerBorderColorDark(Color.BLACK)
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
    }

    public King[] getKings() {
        return allPieces.stream()
            .filter(King.class::isInstance)
            .map(King.class::cast)
            .toArray(King[]::new);
    }

    public ChessPiece getPieceAt(Point p) {
        return (ChessPiece) World.getGlobalWorld().getAllFieldEntities().stream()
            .filter(Robot.class::isInstance)
            .map(Robot.class::cast)
            .filter(piece -> piece.getX() == p.x && piece.getY() == p.y && !piece.isTurnedOff())
            .findFirst().orElse(null);
    }
}
